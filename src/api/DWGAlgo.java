package api;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DWGAlgo implements DirectedWeightedGraphAlgorithms{

    DirectedWeightedGraph graph;

    public DWGAlgo(){
        graph = new DWGraph();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        graph = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return null;
    }

    @Override
    public boolean isConnected() {
        resetTag();
        if (graph != null) {
            DirectedWeightedGraph transposeGraph = transpose(graph);
//            System.out.println("graph:" +graph);
//            System.out.println("Tgraph:" +transposeGraph);
            Iterator<NodeData> iter = graph.nodeIter();
//            if (iter.hasNext()){
            NodeData firstNode = iter.next();
            BFS(firstNode, graph);
            Iterator<NodeData> iterSecond = transposeGraph.nodeIter();
            NodeData firstNode2 = iterSecond.next();
            BFS(firstNode2, transposeGraph);
//            }
            Iterator<NodeData> iterGraph = graph.nodeIter();
            while (iterGraph.hasNext()){
                if(iterGraph.next().getTag() == 0){
                    return false;
                }
            }
            Iterator<NodeData> iterTranspose = transposeGraph.nodeIter();
            while (iterTranspose.hasNext()){
                if(iterTranspose.next().getTag() == 0){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }

    public String toString(){
        return graph.toString();
    }

    public void resetTag(){
        Iterator<NodeData> iterGraph = graph.nodeIter();
        while (iterGraph.hasNext()){
            iterGraph.next().setTag(0);
            }
    }

    // prints BFS traversal from a given source s
    public void BFS(NodeData s, DirectedWeightedGraph g) {
        resetTag();
        LinkedList<NodeData> queue = new LinkedList<>();

        // Mark the current node as visited and enqueue it
        s.setTag(1);
        queue.add(s);

        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            NodeData curr = queue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<EdgeData> i = g.edgeIter(curr.getKey());
            while (i.hasNext())
            {
                NodeData n = g.getNode(i.next().getDest());
                if (n.getTag() != 1)
                {
                    n.setTag(1);
                    queue.add(n);
                }
            }
        }
    }

    public DirectedWeightedGraph transpose(DirectedWeightedGraph g){
        DirectedWeightedGraph transposeG = new DWGraph();
        Iterator<NodeData> nodeIter = g.nodeIter();
        while (nodeIter.hasNext()) {
            transposeG.addNode(nodeIter.next());
        }
        Iterator<EdgeData> edgeIter = g.edgeIter();
        while (edgeIter.hasNext()) {
            EdgeData tempIter = edgeIter.next();
            transposeG.connect(tempIter.getDest(), tempIter.getSrc(), tempIter.getWeight());
            }
        return transposeG;
    }
}
