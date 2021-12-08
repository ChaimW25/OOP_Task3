import Implementations.DWGraph;
import Implementations.Geo;
import Implementations.Node;
import api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DWGraphTest {
//    private static DirectedWeightedGraph g;
//
//    @BeforeEach
//    void setUp() {
//        g = new DWGraph();
//    }
//
//    @Test
//    void getNode() {
//        Node n = new Node(0, "0,0,0");
//        g.addNode(n);
//        assertEquals(0, g.getNode(0).getKey());
//        assertEquals("", g.getNode(0).getInfo());
//        assertEquals(-1, g.getNode(0).getTag());
//        NodeData n1 = g.getNode(0);
//        n.setTag(7);
//        assertEquals(7, n1.getTag());
//        assertEquals(n, n1);
//        assertSame(n, n1);
//    }
//
//    @Test
//    void getEdge() {
//        assertNull(g.getEdge(0, 1));
//        g.addNode(new Node(0, "0,0,0"));
//        g.addNode(new Node(1, "0,0,0"));
//        g.connect(0, 1, 1.5);
//        assertNotNull(g.getEdge(0, 1));
//        assertNull(g.getEdge(1, 0));
//        assertEquals(1.5, g.getEdge(0, 1).getWeight());
//        g.removeEdge(0, 1);
//        assertNull(g.getEdge(0, 1));
//        g.connect(0, 1, 2.5);
//        g.connect(1, 0, 3.5);
//        assertEquals(3.5, g.getEdge(1, 0).getWeight());
//        g.addNode(new Node(2, "0,0,0"));
//        assertNull(g.getEdge(0, 2));
//        g.connect(0, 2, 1.5);
//        g.connect(2, 0, 2.5);
//        assertEquals(1.5, g.getEdge(0, 2).getWeight());
//        assertEquals(2.5, g.getEdge(2, 0).getWeight());
//        assertEquals(0, g.getEdge(0, 2).getSrc());
//        assertEquals(2, g.getEdge(0, 2).getDest());
//    }
//
//    @Test
//    void addNode() {
//        assertEquals(0, g.nodeSize());
//        for (int i = 0; i < 123; i++) {
//            g.addNode(new Node(i, "0,0,0"));
//        }
//        assertEquals(123, g.nodeSize());
//        for (int i = 0; i < 123; i++) {
//            assertEquals(i, g.getNode(i).getKey());
//        }
//    }
//
//    @Test
//    void connect() {
//        g.addNode(new Node(0, "0,0,0"));
//        g.addNode(new Node(1, "0,0,0"));
//        g.addNode(new Node(2, "0,0,0"));
//        g.connect(1, 2, 0.1);
//        assertNotNull(g.getEdge(1, 2));
//        assertNull(g.getEdge(2, 1));
//        g.connect(1, 2, 0.2);
//        g.connect(1, 2, 0.3);
//        assertEquals(0.3, g.getEdge(1, 2).getWeight());
//    }

//    @Test
//    void getV() {
//        assertNotNull(g.getV());
//        Collection<node_data> c = g.getV();
//        for (int i = 0; i < 20; i++) {
//            g.addNode(new NodeData(i));
//            assertTrue(c.contains(g.getNode(i)));
//        }
//    }
//
//    @Test
//    void getE() {
//        assertNull(g.getE(0));
//        g.addNode(new NodeData(0));
//        assertNotNull(g.getE(0));
//        Collection<edge_data> c = g.getE(0);
//        assertEquals(0, c.size());
//        for (int i = 1; i < 20; i++) {
//            g.addNode(new NodeData(i));
//            g.connect(i, 0, 0.2);
//            assertFalse(c.contains(g.getEdge(i, 0)));
//            g.connect(0, i, 0.3);
//            assertTrue(c.contains(g.getEdge(0, i)));
//        }
//    }

//    @Test
//    void removeNode() {
//        g.addNode(new Node(0, "0,0,0"));
//        g.addNode(new Node(1, "0,0,0"));
//        g.addNode(new Node(2, "0,0,0"));
//        assertEquals(3, g.nodeSize());
//        g.removeNode(0);
//        g.removeNode(0);
//        assertEquals(2, g.nodeSize());
//        NodeData rem = null;
//        Iterator<NodeData> iterGraph = g.nodeIter();
//        while (iterGraph.hasNext()){
//            NodeData temp = iterGraph.next();
//            if(temp.getKey() == 0) rem = temp;
//        }
//        assertNull(rem);
//    }
//
////    @Test
////    void removeEdge() {
////        g.addNode(new NodeData(0));
////        g.addNode(new NodeData(1));
////        g.addNode(new NodeData(2));
////        g.connect(0, 1, 1);
////        g.connect(0, 1, 1);
////        g.connect(0, 2, 1);
////        g.connect(2, 1, 1);
////        g.removeEdge(1, 5);
////        g.removeEdge(1, 2);
////        assertEquals(3, g.edgeSize());
////    }
//
////    @Test
////    void nodeSize() {
////        int r1 = nextRnd(0, 100);
////        for (int i = 0; i < r1; i++) {
////            g.addNode(new NodeData(i));
////        }
////        assertEquals(r1, g.nodeSize());
////        int r2 = nextRnd(0, r1);
////        for (int i = 0; i < r2; i++) {
////            g.removeNode(i);
////        }
////        assertEquals(r1 - r2, g.nodeSize());
////    }
//
//    @Test
//    void edgeSize() {
//        int n = nextRnd(10, 100), e = nextRnd(n, n * 3);
//        for (int i = 0; i < n; i++) {
//            g.addNode(new Node(i, "0,0,0"));
//        }
//        while (g.edgeSize() < e) {
//            int a = nextRnd(0, n);
//            int b = nextRnd(0, n);
//            g.connect(a, b, 5);
//        }
//        assertEquals(e, g.edgeSize());
//        int r = nextRnd(0, e);
//        while (g.edgeSize() > e - r) {
//            g.removeEdge(nextRnd(0, e), nextRnd(0, e));
//        }
//        assertEquals(e - r, g.edgeSize());
//    }
//
//    @Test
//    void equal() {
//        DirectedWeightedGraph g1 = new DWGraph();
//        DirectedWeightedGraph g2 = new DWGraph();
//        assertEquals(g2, g1);
//        for (int i = 0; i < 6; i++) {
//            g1.addNode(new Node(i, "0,0,0"));
//            g2.addNode(new Node(i,"0,0,0"));
//        }
//        assertEquals(g2, g1);
//        g1.connect(0, 1, 5);
//        g2.connect(0, 1, 5);
//        assertEquals(g2, g1);
//    }
//
/////////////////
//
//    static int nextRnd(int min, int max) {
//        double v = nextRnd(0.0 + min, 0.0 + max);
//        return (int) v;
//    }
//
//    private static double nextRnd(double min, double max) {
//        Random rnd = new Random();
//        double d = rnd.nextDouble();
//        double dx = max - min;
//        double ans = d * dx + min;
//        DecimalFormat df = new DecimalFormat("####0.00");
//        return Double.parseDouble(df.format(ans));
//    }
////    Geo first = new Geo("0.0 , 0.0 ,0.0" );
////    Geo second = new Geo("0.0 , 1.0 ,0.0" );
////    Geo third = new Geo("1.0 , 1.0 ,0.0" );
////    Node n0 =new Node(0,"first");
////    Node n1 =new Node(1,"second");
////    Node n2 =new Node(2,"third");
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

//    @Test
//    void nodeIter() {
////        try{
////            DWGraph graph = startGraph();
////            graph.addNode(n0);
////            graph.addNode(n1);
////            Iterator<NodeData> it =graph.nodeIter();
////            int counter=0;
////
////            while(it.hasNext()) {
////                it.next();
////                counter++;
////            }
////            assertEquals(2,counter);
////            graph.connect(0,1,0.8);
////        }
////        catch (RuntimeException e ){
////            assertEquals(RuntimeException.class,e.getClass());
////        }
////
//    }
//
//    @Test
//    void edgeIter() {
////        try {
//            DWGraph graph = startGraph();
//            if (graph.getMC() == 0) {
//                Iterator<EdgeData> iter = graph.edgeIter();
//                for (int i = 0; i < 4; i++) {
//                    EdgeData tempIter = iter.next();
//                    assertEquals(i, tempIter.getSrc());
//                    if (i < 3) {
//                        assertEquals(i + 1, tempIter.getDest());
//                    }
//                }
//            }
////        } catch (RuntimeException e) {
////            assertEquals(RuntimeException.class, e.getClass());
////        }
//        }
//
//    @Test
//    void testEdgeIter() {
////        try{
////            DWGraph graph = startGraph();
////            graph.addNode(n0);
////            graph.addNode(n1);
////            graph.addNode(n2);
////            graph.connect(2,1,0.8);
////            graph.connect(1,2,2.1);
////            graph.connect(0,1,0.8);
////            graph.connect(1,0,2.1);
////            graph.connect(2,0,0.8);
////            graph.connect(0,2,2.1);
////            Iterator<EdgeData> it =graph.edgeIter(0);
////            int counter=0;
////
////            while(it.hasNext()) {
////                it.next();
////                counter++;
////            }
////            assertEquals(6,counter);
////            graph.removeNode(0);
////        }
////        catch (RuntimeException e ){
////            assertEquals(RuntimeException.class,e.getClass());
////        }
//
//    }
//
//
    @Test
    void removeNode() {
        DWGraph graph = startGraph();
        assertEquals(4, graph.nodeSize());
//        graph.removeNode(0);
//        assertEquals(3, graph.nodeSize());
//        assertEquals(2, graph.edgeSize());
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