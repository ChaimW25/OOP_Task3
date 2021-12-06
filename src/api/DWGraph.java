package api;

import java.util.*;
import com.google.gson.*;
import java.lang.reflect.Type;


public class DWGraph implements DirectedWeightedGraph {
    //A HashMap that holds all the vertices in the graph.
    // The key is the ID of the vertex and the value is the vertex itself (the NodeData).
    private HashMap<Integer, NodeData> Nodes;          //hashmap of all the nodes.
    private HashMap<Integer, HashMap<Integer, EdgeData>> Edges;   //every node has hashmap of his going out edges.
    //    private HashMap<Integer, HashMap<Integer, Double>> destToSrc; // a map of incoming edges to the node .
    private HashMap<Integer, HashMap<Integer, Integer>> destToSrc; // a map of incoming edges to the node .
    private int _nodeCounter =0;
    private int _edgeCounter =0;
    private int _mcCounter=0;

    public DWGraph(){
        Nodes = new HashMap<>();
        Edges = new HashMap<>();
        destToSrc = new HashMap<>();
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

    @Override
    public Iterator<NodeData> nodeIter() {
        return Nodes.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        List<EdgeData> ans  = new ArrayList<>();
        for (NodeData n : Nodes.values()){
            ans.addAll(Edges.get(n.getKey()).values());
        }
        return ans.iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return Edges.get(node_id).values().iterator();
    }

    @Override
    public NodeData removeNode(int key) {
        if (getNode(key) == null || Edges.get(key) == null){
            return null;
        }

        HashMap<Integer, EdgeData> edgesOut = Edges.get(key); // make map for move on edge

        for( int e : Edges.get(key).keySet()){
//            Edges.get(key).remove(e);
            _edgeCounter--;
            _mcCounter++;
        }

        //remove node's in edges
        for (int e : destToSrc.get(key).keySet()){
//            Edges.get(e).remove(key);
            _edgeCounter--;
            _mcCounter++;
        }
        NodeData removeNode = Nodes.get(key);
        Nodes.remove(key);
        destToSrc.remove(key);
        _mcCounter++;
        _nodeCounter--;
        return removeNode;
    }

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

    public String toString() {
        return "Edges:" + Edges.toString() + ", Nodes:" + Nodes.toString();
    }

    public static class DWGraph_DSJson implements JsonSerializer<DirectedWeightedGraph>, JsonDeserializer<DirectedWeightedGraph> {

        Node.NodeDataJson nodeJson = new Node.NodeDataJson();
        Edge.EdgeDataJson edgeJson = new Edge.EdgeDataJson();

        @Override
        public DirectedWeightedGraph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            DirectedWeightedGraph graph = new DWGraph();
            JsonArray nodes=jsonElement.getAsJsonObject().get("Nodes").getAsJsonArray();
            JsonArray edges=jsonElement.getAsJsonObject().get("Edges").getAsJsonArray();
            for(JsonElement je: nodes)
            {
                graph.addNode(nodeJson.deserialize(je,type,jsonDeserializationContext));
            }

            for(JsonElement je: edges)
            {
                int s=je.getAsJsonObject().get("src").getAsInt();
                int d=je.getAsJsonObject().get("dest").getAsInt();
                double w=je.getAsJsonObject().get("w").getAsDouble();
                graph.connect(s,d,w);
            }
            return graph;
        }

        @Override
        public JsonElement serialize(DirectedWeightedGraph graph, Type type, JsonSerializationContext jsonSerializationContext)
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
