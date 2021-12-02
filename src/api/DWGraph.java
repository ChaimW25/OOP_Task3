package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;


public class DWGraph implements DirectedWeightedGraph {
    //A HashMap that holds all the vertices in the graph.
    // The key is the ID of the vertex and the value is the vertex itself (the NodeData).
    private HashMap<Integer, NodeData> Nodes;          //hashmap of all the nodes.
    private HashMap<Integer, HashMap<Integer, EdgeData>> Edges;   //every node has hashmap of his going out edges.
    private HashMap<Integer, HashMap<Integer, Double>> destToSrc; // a map of incoming edges to the node .
    int nodeCounter=0;
    int edgeCounter=0;
    int _mcCounter=0;

    public DWGraph(){
        Nodes = new HashMap<>();
        Edges = new HashMap<>();
        destToSrc = new HashMap<>();
    }
    @Override
    public NodeData getNode(int key)
    {
        return nodeList.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return edgeList.get(src).get(dest);
    }

    @Override
    public void addNode(NodeData n) {
        nodeList.put(n.getKey(), n);
        _mcCounter++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        EdgeData newEdge = new Edge();
        HashMap innerMap = Edges.get(src);
        if (innerMap==null) {
            innerMap = new HashMap<Integer,EdgeData>();
            edgeList.put(src,innerMap);
        }
        innerMap.put(dest,newEdge);
        _mcCounter++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return Nodes.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        //dest is a problem!!!!!!!!!!!!!!
        _mcCounter++;
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        HashMap innerMap = edgeList.get(src);

        if (innerMap.get(dest)==null) {
            return null;
        }
        EdgeData deleteEdge = (EdgeData) innerMap.get(dest);
        innerMap.remove(dest);
        _mcCounter++;
        return deleteEdge;
    }


    @Override
    public int nodeSize() {
        return nodeList.size();
    }

    @Override
    public int edgeSize() {
        return edgeCounter;
    }

    @Override
    public int getMC() {
        return _mcCounter;
    }

    public String toString() {
        {
            return "Edges:" + Edges.toString() + ", Nodes:" + Nodes.toString();
        }

//
//        public class DWGraph_DSJson implements JsonSerializer<DirectedWeightedGraph>, JsonDeserializer<DirectedWeightedGraph> {
//
//            Node.NodeDataJson nodeJson = new Node.NodeDataJson();
//            Edge.EdgeDataJson edgeJson = new Edge.EdgeDataJson();
//
//            @Override
//            public DirectedWeightedGraph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                DirectedWeightedGraph graph = new DWGraph();
//                JsonArray nodes = jsonElement.getAsJsonObject().get("Nodes").getAsJsonArray();
//                JsonArray edges = jsonElement.getAsJsonObject().get("Edges").getAsJsonArray();
//                for (JsonElement je : nodes) {
//                    graph.addNode(nodeJson.deserialize(je, type, jsonDeserializationContext));
//                }
//
//                for (JsonElement je : edges) {
//                    int s = je.getAsJsonObject().get("src").getAsInt();
//                    int d = je.getAsJsonObject().get("dest").getAsInt();
//                    double w = je.getAsJsonObject().get("w").getAsDouble();
//                    graph.connect(s, d, w);
//                }
//                return graph;
//            }
//
//            @Override
//            public JsonElement serialize(DWGraph graph, Type type, JsonSerializationContext jsonSerializationContext) {
//                JsonObject graphJson = new JsonObject();
//                JsonArray nodes = new JsonArray();
//                JsonArray edges = new JsonArray();
//                for (n : graph.getNodesList().values()) {
//                    nodes.add(nodeJson.serialize(n, type, jsonSerializationContext));
//                    for (EdgeData e : DWGraph.) {
//                        edges.add(edgeJson.serialize(e, type, jsonSerializationContext));
//                    }
//                }
//                graphJson.add("Edges", edges);
//                graphJson.add("Nodes", nodes);
//                return graphJson;
//            }
//        }
//    }}
    }