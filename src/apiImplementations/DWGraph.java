package apiImplementations;

import java.util.*;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import com.google.gson.*;
import java.lang.reflect.Type;


public class DWGraph implements DirectedWeightedGraph {

    //hashmap of all the nodes.
    private HashMap<Integer, NodeData> Nodes;
    // a map of out edges from the node .
    private HashMap<Integer, HashMap<Integer, EdgeData>> Edges;
    // a map of incoming edges to the node .
    private HashMap<Integer, HashMap<Integer, Integer>> destToSrc;

    private int _nodeCounter =0;//counting the nodes in the graph
    private int _edgeCounter =0;//counting the edges in the graph
    private int _mcCounter=0;//counting the changes in the graph

    public DWGraph(){
        Nodes = new HashMap<>();
        Edges = new HashMap<>();
        destToSrc = new HashMap<>();
        _mcCounter=0;
    }

    @Override
    public NodeData getNode(int key) {
        if (Nodes.containsKey(key))
            return Nodes.get(key);
        return null;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        if (Edges.containsKey(src)) {
            if (Edges.get(src).containsKey(dest)) {
                return Edges.get(src).get(dest);
            }
        }
        return null;
    }

    /**
     * In this method we add the node to the hashmap, then, in addition we create a new hashmap
     * of edges from this node
     * @param n- the node we add
     */
    @Override
    public void addNode(NodeData n) {
        if (!Nodes.containsKey(n.getKey())){
            Nodes.put(n.getKey(), n); //add n to Nodes.
            HashMap<Integer, EdgeData> edges = new HashMap<>();
            Edges.put(n.getKey(), edges);
            destToSrc.put(n.getKey(), new HashMap<>());
            _nodeCounter++;
            _mcCounter++;
        }
    }

    /**
     * In this method we connect between 2 nodes. first, we check that the nodes exists in
     * the graph, then we check: if there's another edge between the nodes-> we overload it
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (src != dest && getNode(src) != null && getNode(dest) != null ) {
            Edge newEdge = new Edge(src, dest, w);
            if (Edges.get(src).containsKey(dest)) _edgeCounter--;
            Edges.get(src).put(dest, newEdge);
            destToSrc.get(dest).put(src, 0);
            _edgeCounter++;
            _mcCounter++;
        }
    }

    /**
     * The iterator loop over the values of all the nodes
     * @return the nodes value.
     */
    @Override
    public Iterator<NodeData> nodeIter() {
//        if(_mcCounter!=0){
//            new RuntimeException("the graph was changed!").printStackTrace();
//        }
        return Nodes.values().iterator();
    }

    /**
     * This iterator loop over all the edges. it starts with a loop over the nodes
     * and then loops over the edges go out from the nodes
     * @return- the edgeData
     */
    @Override
    public Iterator<EdgeData> edgeIter() {
//        try {
            List<EdgeData> ans = new ArrayList<>();
            for (NodeData n : Nodes.values()) {
                ans.addAll(Edges.get(n.getKey()).values());
            }
//            if(_mcCounter!=0){throw new RuntimeException();}
            return ans.iterator();

        }
//            catch (RuntimeException e){
//                new RuntimeException("the graph was changed!").printStackTrace();
//                throw e;
//        }


    /**
     * This method loop over all the edges that fo out from the current node
     * @param node_id- the current node
     * @return- the edges that going out
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
//        try{
//            if(_mcCounter!=0){throw new RuntimeException();}
//            return ans.iterator();
//
//        }
//
//        catch (RuntimeException e){
//            new RuntimeException("the graph was changed!").printStackTrace();
//            throw e;
//
//        }
        return Edges.get(node_id).values().iterator();
    }

    /**
     * This method remove a node from the graph and then remove all the edges that going to/from
     * this node.
     * @param key- the key of the node to remove
     * @return- the NodeData we removed
     */
    @Override
    public NodeData removeNode(int key) {
        if (getNode(key) == null || Edges.get(key) == null){
            return null;
        }

        //update edgeCounter according to the number of edges coming out of it.
        Iterator<EdgeData> iterGraph = edgeIter(key);
        while (iterGraph.hasNext()) {
            iterGraph.next();
            _edgeCounter--;
            _mcCounter++;
        }

        //update edgeCounter according to the number of edges coming in of it.
        for (int e : destToSrc.get(key).keySet()){
            _edgeCounter--;
            _mcCounter++;
        }

        NodeData removeNode = Nodes.get(key);
        Nodes.remove(key);
        Edges.remove(key);
        destToSrc.remove(key);
        _mcCounter++;
        _nodeCounter--;
        return removeNode;
    }

    /**
     * This method remove the edge from the given src to the given dest
     * @param src- the index of the src edge
     * @param dest- the index of the dest edge
     * @return- the edgeData we removed
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (Edges.get(src)==null) {
            return null;
        }
        if (Edges.get(src).get(dest)==null) {
            return null;
        }
        EdgeData deleteEdge = Edges.get(src).get(dest);
        Edges.get(src).remove(dest);
        destToSrc.get(dest).remove(src);
        _edgeCounter--;
        _mcCounter++;
        return deleteEdge;
    }


    @Override
    public int nodeSize() {
        return _nodeCounter;
    }

    @Override
    public int edgeSize() {
        return _edgeCounter;
    }

    @Override
    public int getMC() {
        return _mcCounter;
    }


    public void resetMC() {
         _mcCounter=0;
    }

    public String toString() {
        return "Edges:" + Edges.toString() + ", Nodes:" + Nodes.toString();
    }

    /**
     * This class helps to read from/to Json files.a
     * deserialize- converting json file to objeccts.
     * serialize- converting objects into a json file.
     */
    public static class DWGraph_DSJson implements JsonSerializer<DWGraph>, JsonDeserializer<DWGraph> {

        Node.NodeDataJson nodeJson = new Node.NodeDataJson();
        Edge.EdgeDataJson edgeJson = new Edge.EdgeDataJson();

        /**
         * This method reads the nodes and edges and add them to the graph object
         * @param jsonElement
         * @param type
         * @param jsonDeserializationContext
         * @return
         * @throws JsonParseException
         */
        @Override
        public DWGraph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            DWGraph graph = new DWGraph();
            JsonArray nodes=jsonElement.getAsJsonObject().get("Nodes").getAsJsonArray();
            JsonArray edges=jsonElement.getAsJsonObject().get("Edges").getAsJsonArray();
            //loop over the nodes and add them to the graph
            for(JsonElement je: nodes)
            {
                graph.addNode(nodeJson.deserialize(je,type,jsonDeserializationContext));
            }
            //loop over the edges and add them to the graph
            for(JsonElement je: edges)
            {
                int s=je.getAsJsonObject().get("src").getAsInt();
                int d=je.getAsJsonObject().get("dest").getAsInt();
                double w=je.getAsJsonObject().get("w").getAsDouble();
                graph.connect(s,d,w);
            }
            graph.resetMC();
            return graph;
        }

        /**
         * This method convert object to json file. it creates json arrays and push it into
         * a json file
         * @param graph
         * @param type
         * @param jsonSerializationContext
         * @return
         */
        @Override
        public JsonElement serialize(DWGraph graph, Type type, JsonSerializationContext jsonSerializationContext)
        {
            JsonObject graphJson=new JsonObject();
            JsonArray nodes=new JsonArray();
            JsonArray edges=new JsonArray();
            Iterator<NodeData> nodeIter = graph.nodeIter();
            while (nodeIter.hasNext()){
                nodes.add(nodeJson.serialize(nodeIter.next(), type, jsonSerializationContext));
            }
            Iterator<EdgeData> edgeIter = graph.edgeIter();
            while (edgeIter.hasNext()){
                edges.add(edgeJson.serialize(edgeIter.next(),type,jsonSerializationContext));
            }
            graphJson.add("Edges",edges);
            graphJson.add("Nodes",nodes);
            return graphJson;
        }
    }
}
