import api.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DWGraphTest {
    Geo first = new Geo("0.0 , 0.0 ,0.0" );
    Geo second = new Geo("0.0 , 1.0 ,0.0" );
    Geo third = new Geo("1.0 , 1.0 ,0.0" );
    Node n0 =new Node(0,"first");
    Node n1 =new Node(1,"second");
    Node n2 =new Node(2,"third");
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


    @Test
    void getNode() {
        DWGraph graph = startGraph();
        assertEquals(0, graph.getNode(0).getKey());
    }

    @Test
    void getEdge() {
        DWGraph graph = startGraph();
        assertEquals(3, graph.getEdge(2,3).getWeight());
    }

    @Test
    void addNode() {
        DWGraph graph = startGraph();
        assertEquals(4, graph.nodeSize());
        NodeData node4 = new Node(4, "1.0,2.0,0.0");
        graph.addNode(node4);
        assertEquals(5, graph.nodeSize());
        graph.addNode(node4); //add exist node.
        assertEquals(5, graph.nodeSize());
    }

    @Test
    void connect() {
        DWGraph graph = startGraph();
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
//        try{
//            DWGraph graph = startGraph();
//            graph.addNode(n0);
//            graph.addNode(n1);
//            Iterator<NodeData> it =graph.nodeIter();
//            int counter=0;
//
//            while(it.hasNext()) {
//                it.next();
//                counter++;
//            }
//            assertEquals(2,counter);
//            graph.connect(0,1,0.8);
//        }
//        catch (RuntimeException e ){
//            assertEquals(RuntimeException.class,e.getClass());
//        }
//
    }

    @Test
    void edgeIter() {
//        try {
            DWGraph graph = startGraph();
            if (graph.getMC() == 0) {
                Iterator<EdgeData> iter = graph.edgeIter();
                for (int i = 0; i < 4; i++) {
                    EdgeData tempIter = iter.next();
                    assertEquals(i, tempIter.getSrc());
                    if (i < 3) {
                        assertEquals(i + 1, tempIter.getDest());
                    }
                }
            }
//        } catch (RuntimeException e) {
//            assertEquals(RuntimeException.class, e.getClass());
//        }
        }

    @Test
    void testEdgeIter() {
//        try{
//            DWGraph graph = startGraph();
//            graph.addNode(n0);
//            graph.addNode(n1);
//            graph.addNode(n2);
//            graph.connect(2,1,0.8);
//            graph.connect(1,2,2.1);
//            graph.connect(0,1,0.8);
//            graph.connect(1,0,2.1);
//            graph.connect(2,0,0.8);
//            graph.connect(0,2,2.1);
//            Iterator<EdgeData> it =graph.edgeIter(0);
//            int counter=0;
//
//            while(it.hasNext()) {
//                it.next();
//                counter++;
//            }
//            assertEquals(6,counter);
//            graph.removeNode(0);
//        }
//        catch (RuntimeException e ){
//            assertEquals(RuntimeException.class,e.getClass());
//        }

    }


    @Test
    void removeNode() {
        DWGraph graph = startGraph();
        assertEquals(4, graph.nodeSize());
        graph.removeNode(0);
        assertEquals(3, graph.nodeSize());
        assertEquals(2, graph.edgeSize());
        //remove node that not exist:
        assertNull(graph.removeNode(0));
        assertNull(graph.removeNode(7));
    }

    @Test
    void removeEdge() {
        DWGraph graph = startGraph();
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
        DWGraph graph = startGraph();
        assertEquals(4,  graph.nodeSize());
        NodeData node4 = new Node(4, "1.0,2.0,0.0");
        graph.addNode(node4);
        assertEquals(5, graph.nodeSize());
    }

    @Test
    void edgeSize() {
        DWGraph graph = startGraph();
        assertEquals(4, graph.edgeSize());
        graph.connect(0, 2, 1);
        graph.connect(0, 3, 1);
        assertEquals(6, graph.edgeSize());
        graph.removeNode(0);
        assertEquals(2, graph.edgeSize());

    }

    @Test
    void getMC() {
        DWGraph graph = startGraph();
        assertEquals(8, graph.getMC());
        //Test for connect of edge between node that not exists:
        graph.connect(1,7,4);
        assertEquals(8, graph.getMC());
        graph.removeNode(0);
        assertEquals(11, graph.getMC());
    }
}