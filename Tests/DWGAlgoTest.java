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
import java.util.Arrays;
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

    @Test
    void init() {
        graph = new DWGraph();
        graphAlgo = new DWGAlgo();
        assertFalse(graphAlgo.getGraph().nodeIter().hasNext());
        assertFalse(graphAlgo.getGraph().edgeIter().hasNext());
        graphAlgo.init(graph);
        assertNotNull(graphAlgo.getGraph());
        assertEquals(graph, graphAlgo.getGraph());
    }

    @Test
    void getGraph() {
        graphAlgo = new DWGAlgo();
        graphAlgo.init(graph);
        DirectedWeightedGraph g = graphAlgo.getGraph();
        assertEquals(g, graph);
        assertSame(g, graph);
    }

@Test
void copy() {
    DirectedWeightedGraph copyGraph = graphAlgo.copy();
    System.out.println(copyGraph);
    assertEquals(graph.edgeSize(), copyGraph.edgeSize());
    assertEquals(graph.nodeSize(), copyGraph.nodeSize());
    assertEquals(graph.toString(), copyGraph.toString());
    assertNotSame(graph, copyGraph);
    graph.getNode(2).setTag(5);
    copyGraph.getNode(2).setTag(4);
    assertEquals(5, graph.getNode(2).getTag());
    assertEquals(4, copyGraph.getNode(2).getTag());
    graph.connect(1, 3, 1.5);
    assertEquals(1.5, graph.getEdge(1, 3).getWeight());
    assertNull(copyGraph.getEdge(1, 3));
    copyGraph.connect(0, 2, 1.9);
    assertNull(graph.getEdge(0, 2));
    assertEquals(1.9, copyGraph.getEdge(0, 2).getWeight());
}
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
    }

    @Test
    void center() {
        String path = System.getProperty("user.dir") + "\\data\\";
        //test to find the center in G1:
        DirectedWeightedGraphAlgorithms graphG1 = new DWGAlgo();
        graphG1.load(path + "G1.json");
        assertEquals(8, graphG1.center().getKey());
        //test to find the center in G1:
        DirectedWeightedGraphAlgorithms graphG2 = new DWGAlgo();
        graphG2.load(path + "G2.json");
        assertEquals(0, graphG2.center().getKey());
        //test to find the center in G1:
        DirectedWeightedGraphAlgorithms graphG3 = new DWGAlgo();
        graphG3.load(path + "G3.json");
        assertEquals(40, graphG3.center().getKey());
    }

    @Test
    void tsp() {
        List<NodeData> n = new ArrayList<>();
        n.add(graphAlgo.getGraph().getNode(2));
        n.add(graphAlgo.getGraph().getNode(0));
        n.add(graphAlgo.getGraph().getNode(3));
        n.add(graphAlgo.getGraph().getNode(1));
        n = graphAlgo.tsp(n);
        System.out.println(n);
        List<NodeData> ans = new ArrayList<>();
        ans.add(graphAlgo.getGraph().getNode(2));
        ans.add(graphAlgo.getGraph().getNode(3));
        ans.add(graphAlgo.getGraph().getNode(0));
        ans.add(graphAlgo.getGraph().getNode(1));
        System.out.println(ans);
        for (int i = 0; i < ans.size(); i++) {
            assertEquals(ans.get(i).getKey(), n.get(i).getKey());
        }
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
    void save_load(){
        String path = System.getProperty("user.dir") + "\\data\\";
        DirectedWeightedGraphAlgorithms ga = new DWGAlgo();
        for (int i = 1; i <= 3; i++) {
            ga.load(path + "G" + i + ".json");
            ga.save(path + "B" + i + ".json");
            Path G = Paths.get(path + "G" + i + ".json");
            Path B = Paths.get(path + "B" + i + ".json");
            try {
                Arrays.equals(Files.readAllBytes(G), Files.readAllBytes(B));
                System.out.println("file G" + i + " and file B" + i + " equals!!");
            } catch (IOException e) {
                fail("fail to read file!");
            }
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
        graphAlgo.resetTag(graph);
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
        graphAlgo.resetTag(graph);
        for (int i = 0; i < 4; i++) {
            assertEquals(0, graphAlgo.getGraph().getNode(i).getTag());
        }
    }

//    @Test
//    void json1000(){
//        //test to find the center in G1:
//        String path = System.getProperty("user.dir") + "\\data\\";
//        DirectedWeightedGraphAlgorithms graph1000 = new DWGAlgo();
//        graph1000.load(path + "1000Nodes.json");
//        assertEquals(362, graph1000.center().getKey());
//        assertTrue(graph1000.isConnected());
//    }

//    @Test
//    void json10000(){
        //test to find the center in G1:
//        String path = System.getProperty("user.dir") + "\\data\\";
//        DirectedWeightedGraphAlgorithms graph10000 = new DWGAlgo();
//        graph10000.load(path + "10000Nodes.json");
//        assertEquals(3846, graph10000.center().getKey());
//        assertTrue(graph10000.isConnected());
//    }
//
//    @Test
//    void json100000() {
        //test to find the center in G1:
//        String path = System.getProperty("user.dir") + "\\data\\";
//        DirectedWeightedGraphAlgorithms graph100000 = new DWGAlgo();
//        graph100000.load(path + "100000Nodes.json");
//        assertEquals(3846, graph100000.center().getKey());
//        assertTrue(graph100000.isConnected());
//    }

}
