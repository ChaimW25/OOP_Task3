package apiImplementations;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class DWGAlgo implements DirectedWeightedGraphAlgorithms {

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
        if (graph == null){
            return null;
        }
        DirectedWeightedGraph copyGraph = new DWGraph();
        //copy all the nodes:
        Iterator<NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData tempNode =nodeIter.next();
            copyGraph.addNode(new Node(tempNode.getKey(), tempNode.getLocation().toString()));
        }
        //copy all the edges:
        Iterator<EdgeData> edgeIter = graph.edgeIter();
        while (edgeIter.hasNext()) {
            EdgeData tempEdge =edgeIter.next();
            copyGraph.connect(tempEdge.getSrc(), tempEdge.getDest(), tempEdge.getWeight());
        }
        return copyGraph;
    }

    /**
     * This method loop over the nodes in the graph by first: using BFS algorithm
     * to check if the graph is connected.
     * see: https://en.wikipedia.org/wiki/Breadth-first_search
     * second, we use the transpose method to change the narrow direction in directed weighted
     * grpah. At last we use the BFS algorithm to check if the graph is connected again
     * @return- true if the graph isConnected, else- return false
     */
    @Override
    public boolean isConnected() {
        resetTag();
        if (graph != null) {
            DirectedWeightedGraph transposeGraph = transpose(graph);

            Iterator<NodeData> iter = graph.nodeIter();
            NodeData firstNode = iter.next();
            BFS(firstNode, graph);//first we check BFS algorithm

            Iterator<NodeData> iterSecond = transposeGraph.nodeIter();//transpose
            NodeData firstNode2 = iterSecond.next();
            BFS(firstNode2, transposeGraph);//another try of BFS from the same node

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

    /**
     * This method finds the shortest path between 2 nodes, using the dijkstra algorithm
     * to find it.  see: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm.
     * @param src - start node
     * @param dest - end (target) node
     * @return- the double represents the distance from src to dest
     */
    @Override
        public double shortestPathDist(int src, int dest) {
        //if one of the nodes don't exist.
        if (graph.getNode(src) == null || graph.getNode(dest) == null)
            return -1;
        //if src and dest are the same node.
        if (src == dest)
            return 0;
        //else- use dijkstra helper function
        dijkstra(src, dest);
        return graph.getNode(dest).getWeight();
    }

    /**
     * This method similar to the previous method but instead return the distance between src
     * and dest it returns the path between them.
     * @param src - start node
     * @param dest - end (target) node
     * @return- list of nodes that represents the shortest path from src to dest
     */
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

    /**
     *
     * @return
     */
    @Override
    public NodeData center() {
        NodeData center = null;
        if (isConnected()) {
            double minWeight = Double.MAX_VALUE;
            Iterator<NodeData> nodeIter = graph.nodeIter();
            while (nodeIter.hasNext()) {
                NodeData tempNode = nodeIter.next();
                dijkstra(tempNode.getKey(), -1);
                double maxWeight = 0;
                Iterator<NodeData> secondNodeIter = graph.nodeIter();
                while (secondNodeIter.hasNext()) {
                    NodeData secondTempNode = secondNodeIter.next();
                    if (secondTempNode.getWeight() > maxWeight){
                        maxWeight = secondTempNode.getWeight();
                    }
                }
                if (maxWeight < minWeight){
                    minWeight = maxWeight;
                    center = tempNode;
                }
            }
        }
        return center;
    }

    /**
     * This method solve Travelling Salesman Problem. we loop over list of chosen nodes and
     * choose every time the next node by greedy algorithm (we choose the next node with
     * the shortest path).
     * @param cities- the graph nodes list
     * @return- the list of nodes for Travelling Salesman Problem
     *
     *
     * attention---- the input -list of nodes and not all nodes
     */

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        List<NodeData> ans = new ArrayList<>();
        List<NodeData> citiesSet = new ArrayList<>();
        double minDist;
        double tempDist;

        for (NodeData city : cities) {
            if (!citiesSet.contains(city)) {
                citiesSet.add(city);
            }
        }

        NodeData currNode = citiesSet.remove(0);
        ans.add(currNode);
        NodeData nodeToAdd = null;
        while (citiesSet.size() > 0){
            minDist = Double.MAX_VALUE;
            for ( NodeData n : citiesSet){
               tempDist =  shortestPathDist(currNode.getKey(), n.getKey());
               if (tempDist < minDist){
                   minDist = tempDist;
                   nodeToAdd = n;
               }
            }
            ans.add(nodeToAdd);
            citiesSet.remove(nodeToAdd);
            currNode = nodeToAdd;
        }
        return ans;
    }

    /**
     * This method save the given graph into a json file using gson
     * @param file - the file name (may include a relative path).
     * @return- true if the file saved successfully, else- return false
     */
    @Override
    public boolean save(String file) {
        try {
            GsonBuilder gson =new GsonBuilder();
            gson.registerTypeAdapter(DWGraph.class, new DWGraph.DWGraph_DSJson());
            Gson g= gson.create();
            PrintWriter gFile=new PrintWriter(new File(file));
            gFile.write(g.toJson(graph));
            gFile.close();
            return true;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("can't write the graph to a file ");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method loads a json file to graph using gson
     * @param file - file name of JSON file
     * @return
     */
    @Override
    public boolean load(String file) {

        try {
            GsonBuilder builder=new GsonBuilder();
            builder.registerTypeAdapter(DWGraph.class, new DWGraph.DWGraph_DSJson());
            Gson gson=builder.create();
            BufferedReader gFile = new BufferedReader(new FileReader(file));
            DWGraph g=gson.fromJson(gFile,DWGraph.class);
            init(g);
            return true;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("can't read the graph from the file");
            e.printStackTrace();
        }
        return false;    }

    public String toString(){
        return graph.toString();
    }

    public void resetTag(){
        Iterator<NodeData> iterGraph = graph.nodeIter();
        while (iterGraph.hasNext()){
            iterGraph.next().setTag(0);
            }
    }

    /**
     * This function implement the BFS algorithm:Breadth-first search.
     * the function loop over the graph nodes and check that it's in the same Binding component
     * @param s- the node for begining
     * @param g- the graph for check
     */
    // prints BFS traversal from a given source s
    public void BFS(NodeData s, DirectedWeightedGraph g) {
        resetTag();
        LinkedList<NodeData> queue = new LinkedList<>();
        // Mark the current node as visited and enqueue it
        s.setTag(1);
        queue.add(s);

        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            NodeData curr = queue.poll();
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<EdgeData> i = g.edgeIter(curr.getKey());
            while (i.hasNext()) {
                NodeData n = g.getNode(i.next().getDest());
                if (n.getTag() != 1) {
                    n.setTag(1);
                    queue.add(n);
                }
            }
        }
    }

    /**
     * This method make transpose for all the edges in the graph. it creates a new
     * graph and add the edges in the opposite direction.
     * @param g
     * @return
     */

    public DirectedWeightedGraph transpose(DirectedWeightedGraph g){
        DirectedWeightedGraph transposeG = new DWGraph();
        Iterator<NodeData> nodeIter = g.nodeIter();
        while (nodeIter.hasNext()) {
            transposeG.addNode(nodeIter.next());
        }
        //using a loop to change all the edges direction
        Iterator<EdgeData> edgeIter = g.edgeIter();
        while (edgeIter.hasNext()) {
            EdgeData tempIter = edgeIter.next();
            transposeG.connect(tempIter.getDest(), tempIter.getSrc(), tempIter.getWeight());
            }
        return transposeG;
    }

    /**
    dijkstra's algorithm is an algorithm for finding the shortest paths
    between nodes in a graph. We used this algorithm to calculate the functions
    shortestPathDist and shortestPath.
     see:   https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
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

    //comparator for  weight
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

    //helper function which init for the dijkstra function
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
