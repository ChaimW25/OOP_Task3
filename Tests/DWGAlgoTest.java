import apiImplementations.DWGAlgo;
import apiImplementations.DWGraph;
import apiImplementations.Node;
import api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGAlgoTest {

    private static DirectedWeightedGraph graph;
    private static DWGAlgo graphAlgo;

    @BeforeEach
    void startGraph() {

        //creat a DWGAlgo:
        graph = new DWGraph();
        graphAlgo = new DWGAlgo();
        //creat NodeData:
        NodeData node0 = new Node(0, "1.0,2.0,0.0");
        NodeData node1 = new Node(1, "2.0,3.0,0.0");
        NodeData node2 = new Node(2, "3.0,4.0,0.0");
        NodeData node3 = new Node(3, "4.0,5.0,0.0");
        //add the nodes to graph:
        graph.addNode(node0);
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        //connect between the nodes:
        graph.connect(0,1,1);
        graph.connect(1,2,2);
        graph.connect(2,3,3);
        graph.connect(3,0,4);

        graphAlgo.init(graph);
    }
//
//    @Test
//    void init() {
//    }
//
//    @Test
//    void getGraph() {
//    }
//
//    @Test
//    void copy() {
//    }
//
    @Test
    void isConnected() {
        assertTrue(graphAlgo.isConnected());
        graphAlgo.getGraph().removeEdge(2,3);
        assertFalse(graphAlgo.isConnected());
        graph.removeEdge(3,0);
        graph.connect(2,1,9);
        graph.connect(3,2,9);
        graph.connect(0,3,8);
        assertFalse(graphAlgo.isConnected());
    }

    @Test
    void shortestPathDist() {
        DirectedWeightedGraph graph = new DWGraph();
        for (int i = 1; i < 9; i++) {
            graph.addNode(new Node(i, "0,0,0"));
        }
        //https://upload.wikimedia.org/wikipedia/commons/5/5c/Scc.png
        graph.connect(1, 2, 1);
        graph.connect(2, 5, 1);
        graph.connect(2, 6, 1);
        graph.connect(2, 3, 1);
        graph.connect(3, 4, 1);
        graph.connect(3, 7, 1);
        graph.connect(4, 3, 1);
        graph.connect(4, 8, 1);
        graph.connect(5, 1, 1);
        graph.connect(5, 6, 1);
        graph.connect(6, 7, 1);
        graph.connect(7, 6, 1);
        graph.connect(8, 7, 1);
        graph.connect(8, 4, 1);

        graphAlgo.init(graph);
        assertEquals(4, graphAlgo.shortestPathDist(1, 8), "is fuction shortestPathDist not working properly");
        List<NodeData> sl = new ArrayList<>();
        sl.add(graph.getNode(1));
        sl.add(graph.getNode(2));
        sl.add(graph.getNode(3));
        sl.add(graph.getNode(4));
        sl.add(graph.getNode(8));
        assertEquals(sl, graphAlgo.shortestPath(1, 8), "is fuction shortestPathDist not working properly");
    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
        String path = "G1.json";
        DirectedWeightedGraphAlgorithms ga = new DWGAlgo();
        DirectedWeightedGraph g = new DWGraph();
        ga.init(g);
        for (int i = 0; i < 5; i++) {
            g.addNode(new Node(i, "0,0,0"));
        }
        g.connect(0, 1, 5.265);
        g.connect(1, 0, 8.965);
        g.connect(3, 4, 12.54);
        g.connect(2, 4, 1.1);
        g.connect(0, 4, 3.2);
        ga.save(path);
    }

    @Test
    void load() {
        String path = System.getProperty("user.dir") + "\\data\\";
        DirectedWeightedGraphAlgorithms ga = new DWGAlgo();
        for (int i = 1; i <= 3; i++) {
            ga.load(path + "G" + i + ".json");
            System.out.println(ga.getGraph() + "\n");
        }
    }

    @Test
    void BFS() {
        NodeData node4 = new Node(4, "1.0,2.0,0.0");
        graphAlgo.getGraph().addNode(node4);
        for (int i = 0; i < 5; i++){
            assertEquals(0, graphAlgo.getGraph().getNode(i).getTag());
        }
        graphAlgo.BFS(graphAlgo.getGraph().getNode(0), graphAlgo.getGraph());
        for (int i = 0; i < 4; i++){
            assertEquals(1, graphAlgo.getGraph().getNode(i).getTag());
        }
        //test that the new node not connect to any node
        assertEquals(0, graphAlgo.getGraph().getNode(4).getTag());
        graphAlgo.getGraph().removeEdge(2,3);
        graphAlgo.resetTag();
        graphAlgo.BFS(graphAlgo.getGraph().getNode(0), graphAlgo.getGraph());
        assertEquals(0, graphAlgo.getGraph().getNode(3).getTag());

    }

    @Test
    void transpose(){

        DirectedWeightedGraph transposeGraph = graphAlgo.transpose(graph);
        assertEquals(graph.getEdge(0,1).getWeight(), transposeGraph.getEdge(1,0).getWeight());
        for (int i = 0; i < 4; i++){
            assertEquals(graph.getNode(i).getKey(), transposeGraph.getNode(i).getKey());
        }
        assertEquals(graph.getEdge(0,1).getWeight(), transposeGraph.getEdge(1,0).getWeight());
    }

    @Test
    void resetTag(){
        graphAlgo.isConnected();
        graphAlgo.resetTag();
        for (int i = 0; i < 4; i++) {
            assertEquals(0, graphAlgo.getGraph().getNode(i).getTag());
        }
    }

//    @Test
//    void tsp() {
//        String path = System.getProperty("user.dir") + "\\data\\";
//        DirectedWeightedGraphAlgorithms graph = new DWGAlgo();
//        graph.load(path + "G" + 3 + ".json");
//        //DWGraph g =  graph.getGraph();
//        List<NodeData> n = new ArrayList<>();
//        n.add(g.getNode(0));
//        n.add(g.getNode(1));
//        n.add(g.getNode(2));
//        n.add(g.getNode(3));
//        n.add(g.getNode(9));
//        n = graph.tsp(n);
//        List<NodeData> ans = new ArrayList<>();
//        ans.add(g.getNode(1));
//        ans.add(g.getNode(9));
//        ans.add(g.getNode(2));
//        ans.add(g.getNode(3));
//        ans.add(g.getNode(0));
//        for (int i = 0; i < ans.size(); i++) {
//            assertEquals(ans.get(i).getKey(), n.get(i).getKey());
//        }
//    }

    @Test
    void save_load() {
        String path = System.getProperty("user.dir") + "\\data\\";
        DirectedWeightedGraphAlgorithms ga = new DWGAlgo();
        for (int i = 1; i <= 3; i++) {
            ga.load(path + "G" + i + ".json");
            ga.save(path + "B" + i + ".json");
            System.out.println(ga.getGraph());
            Path G = Paths.get(path + "G" + i + ".json");
            Path B = Paths.get(path + "B" + i + ".json");
            String strG = "";
            String strB = "";
            try {
                strG = new String(Files.readAllBytes(G));
                strB = new String(Files.readAllBytes(B));
            } catch (IOException e) {
                e.printStackTrace();
                fail("fail to read file!");
            }
//            if (!strG.equals(strB)) fail("not same file: G" + i + " != B" + i);
//            assertEquals(strG, strB);
        }
    }
}