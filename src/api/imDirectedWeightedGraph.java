package api;
import java.util.Iterator;
import java.util.HashMap;


public class imDirectedWeightedGraph implements DirectedWeightedGraph {
    //A HashMap that holds all the vertices in the graph.
    // The key is the ID of the vertex and the value is the vertex itself (the NodeData).
    HashMap<Integer, NodeData> nodeList = new HashMap<>();

    HashMap<Integer, HashMap<Integer, EdgeData>> edgeList = new HashMap<Integer, HashMap<Integer, EdgeData>>();
    HashMap<Integer, EdgeData> innerMap = new HashMap<>();
    innerMap.put("innerKey", new Value());

    int edgeCounter=0;
    int _mcCounter=0;

    public  imDirectedWeightedGraph(){

        edgeCounter=
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
        EdgeData newEdge = new imEdge();
        HashMap innerMap = edgeList.get(src);
        if (innerMap==null) {
            innerMap = new HashMap<Integer,EdgeData>();
            edgeList.put(src,innerMap);
        }
        innerMap.put(dest,newEdge);
        _mcCounter++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
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
}
