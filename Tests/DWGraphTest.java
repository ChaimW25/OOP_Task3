import api.*;
import apiImplementations.DWGraph;
import apiImplementations.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DWGraphTest {

    private static DirectedWeightedGraph graph;

    @BeforeEach
    void startGraph() {

        //creat a DWGraph:
        graph = new DWGraph();
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
    }

    @Test
    void getNode() {
        assertEquals(0, graph.getNode(0).getKey());
    }

    @Test
    void getEdge() {
        assertEquals(3, graph.getEdge(2,3).getWeight());
    }

    @Test
    void addNode() {
        assertEquals(4, graph.nodeSize());
        NodeData node4 = new Node(4, "1.0,2.0,0.0");
        graph.addNode(node4);
        assertEquals(5, graph.nodeSize());
        graph.addNode(node4); //add exist node.
        assertEquals(5, graph.nodeSize());
    }

    @Test
    void connect() {
        assertEquals(4,  graph.edgeSize());
        graph.connect(1,3,3);
        assertEquals(5,  graph.edgeSize());
        assertEquals(3,  graph.getEdge(1,3).getWeight());
        //Test for connect of edge that already exists:
        graph.connect(1,3,4);
        assertEquals(5,  graph.edgeSize());
        assertEquals(4,  graph.getEdge(1,3).getWeight());
        //Test for connect of edge between node that not exists:
        graph.connect(1,7,4);
        assertEquals(5,  graph.edgeSize());
    }

        @Test
    void nodeIter() {
        Iterator<NodeData> iter = graph.nodeIter();
        for (int i = 0; i < 4; i++) {
            NodeData tempIter = iter.next();
            assertEquals(i, tempIter.getKey());
        }
        Iterator<NodeData> secondIter = graph.nodeIter();
        graph.removeNode(0);
        try{
            NodeData e = secondIter.next();
            fail("Expected an RuntimeException to be thrown");
        }
        catch (RuntimeException e){
            System.out.println("the test succeed!");
        }
    }

    @Test
    void edgeIter() {
        Iterator<EdgeData> iter = graph.edgeIter();
        for (int i = 0; i < 4; i++) {
            EdgeData tempIter = iter.next();
            assertEquals(i, tempIter.getSrc());
            if (i < 3) {
                assertEquals(i + 1, tempIter.getDest());
            }
        }
        Iterator<EdgeData> secondIter = graph.edgeIter();
    }

    @Test
    void testEdgeIter() {
        Iterator<EdgeData> iter = graph.edgeIter(0);
        iter.next();
        assertFalse(iter.hasNext());
        Iterator<EdgeData> secondIter = graph.edgeIter(0);
        graph.connect(0,2,8.9);
        try{
            EdgeData e = secondIter.next();
            fail("Expect1ed an RuntimeException to be thrown");
        }
        catch (RuntimeException e){
            System.out.println("the test succeed!");
        }
    }


    @Test
    void removeNode() {
        assertEquals(4, graph.nodeSize());
        graph.connect(0,2, 0);
        graph.connect(0,3, 0);
        assertEquals(6, graph.edgeSize());
        graph.removeNode(0);
        assertEquals(2, graph.edgeSize());
        //remove node that not exist:
        assertNull(graph.removeNode(0));
        assertNull(graph.removeNode(7));
    }

    @Test
    void removeEdge() {
        assertEquals(4,  graph.edgeSize());
        //remove exist edge:
        graph.removeEdge(0,1);
        assertEquals(3,  graph.edgeSize());
        //remove edge that not exist:
        assertNull(graph.removeEdge(0, 1));
        assertEquals(3,  graph.edgeSize());
        assertNull(graph.removeEdge(2, 1));
        Iterator<EdgeData> iter = graph.edgeIter();
        int i = 1;
        while (iter.hasNext()){
            EdgeData edge = iter.next();
            assertEquals(i, edge.getSrc());
            i++;
        }
    }

    @Test
    void nodeSize() {
        assertEquals(4,  graph.nodeSize());
        NodeData node4 = new Node(4, "1.0,2.0,0.0");
        graph.addNode(node4);
        assertEquals(5, graph.nodeSize());
    }

    @Test
    void edgeSize() {
        assertEquals(4, graph.edgeSize());
        graph.connect(0, 2, 1);
        graph.connect(0, 3, 1);
        assertEquals(6, graph.edgeSize());
        graph.removeNode(0);
        assertEquals(2, graph.edgeSize());

    }

    @Test
    void getMC() {
        assertEquals(8, graph.getMC());
        //Test for connect of edge between node that not exists:
        graph.connect(1,7,4);
        assertEquals(8, graph.getMC());
        graph.removeNode(0);
        assertEquals(9, graph.getMC());
    }
}
