import api.DWGAlgo;
import api.DWGraph;
import api.Node;
import api.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGAlgoTest {

    public static DWGraph startGraph() {
        //creat a DWGraph:
        DWGraph graph = new DWGraph();
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

        return graph;
    }

    public static DWGAlgo startAlgo(){
        DWGraph g = startGraph();
        DWGAlgo graph = new DWGAlgo();
        graph.init(g);
        return graph;
    }

    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() {
    }

    @Test
    void shortestPathDist() {
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
    }

    @Test
    void load() {
    }

    @Test
    void BFS() {
        DWGAlgo graph = startAlgo();
        NodeData node4 = new Node(4, "1.0,2.0,0.0");
        graph.getGraph().addNode(node4);
        for (int i = 0; i < 5; i++){
            assertEquals(0, graph.getGraph().getNode(i).getTag());
        }
        graph.BFS(graph.getGraph().getNode(0));
        for (int i = 0; i < 4; i++){
            assertEquals(1, graph.getGraph().getNode(i).getTag());
        }
        assertEquals(0, graph.getGraph().getNode(4).getTag());
    }

    @Test
    void transpose(){
        DWGAlgo g = startAlgo();
        DWGraph graph = startGraph();
        DWGraph transposeGraph = g.transpose(graph);
        assertEquals(graph.getEdge(0,1).getWeight(), transposeGraph.getEdge(1,0).getWeight());
        for (int i = 0; i < 4; i++){
            assertEquals(graph.getNode(i).getKey(), transposeGraph.getNode(i).getKey());
        }
//        assertEquals(graph.getEdge(0,1).getWeight(), transposeGraph.getEdge(1,0).getWeight());
    }
}