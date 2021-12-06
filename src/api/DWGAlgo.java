package api;

import java.util.*;

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
        //if one of the nodes don't exist.
        if (graph.getNode(src) == null || graph.getNode(dest) == null)
            return -1;
        //if src and dest are the same node.
        if (src == dest)
            return 0;
        //else:
        dijkstra(src, dest);
        return graph.getNode(dest).getWeight();
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData> shortestPath = new ArrayList<>();
        //if src and dest are the same node:
        if (src == dest) {
            shortestPath.add(graph.getNode(src));
            return shortestPath;
        }
        dijkstra(src, dest);
        //if no such path:
        if (graph.getNode(dest).getWeight() == -1)
            return null;
        int temp = dest;
        shortestPath.add(graph.getNode(temp));
        while (temp != src){
            temp = graph.getNode(temp).getTag();
            shortestPath.add(graph.getNode(temp));
        }
        Collections.reverse(shortestPath);
        return shortestPath;
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

    /*
    dijkstra's algorithm is an algorithm for finding the shortest paths
    between nodes in a graph. We used this algorithm to calculate the functions
    shortestPathDist and shortestPath.
     */
    private void dijkstra(int src, int dest) {
        initTIW();
        PriorityQueue<NodeData> queue = new PriorityQueue<>(new weightComp());
        graph.getNode(src).setWeight(0);
        queue.add(graph.getNode(src));
        while (!queue.isEmpty()) {
            NodeData u = queue.poll();
            if (u.getInfo() == null) {       //true if we didn't visit this node
                u.setInfo("v");
                if (u.getKey() == dest)     //when we get to dest node
                    return;
                Iterator<EdgeData> edgeIter = graph.edgeIter(u.getKey());
                while (edgeIter.hasNext())  //iteration on all edges coming out of u.
                {
                    EdgeData tempEdge = edgeIter.next();
                    NodeData tempNode = graph.getNode(tempEdge.getDest());

                    if (tempNode.getInfo() == null) {                //true if we didn't visit this node
                        double w = tempEdge.getWeight() + u.getWeight();
                        if (tempNode.getWeight() != -1) {
                            if (w < tempNode.getWeight()) {             //if the new weight is less then the exist
                                tempNode.setWeight(w);
                                tempNode.setTag(u.getKey());
                            }
                        } else {                                    //if it's first time we reach to this node
                            tempNode.setWeight(w);
                            tempNode.setTag(u.getKey());
                        }
                        queue.add(tempNode);
                    }
                }
            }
        }
    }

    private static class weightComp implements Comparator<NodeData> {
        @Override
        public int compare(NodeData node1, NodeData node2) {
            if (node1.getWeight() == node2.getWeight())
                return 0;
            if (node1.getWeight() > node2.getWeight())
                return 1;
            return -1;
        }
    }

    public void initTIW(){
        Iterator<NodeData> iterGraph = graph.nodeIter();
        while (iterGraph.hasNext()){
            NodeData tempNode = iterGraph.next();
            tempNode.setTag(-1);
            tempNode.setInfo(null);
            tempNode.setWeight(-1);
        }
    }
}
