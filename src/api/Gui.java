package api;

import java.awt.*;
import java.util.Iterator;

public class Gui {

     DWGAlgo _algo;

    public Gui(DWGAlgo algo){
        _algo=algo;
    }

    public void show(){
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(Color.gray);
        StdDraw.setScale(-10.0,10.0);

//        StdDraw.point(5.0,3.0);
//        StdDraw.line(0.0,1.0,2.0,7.8);
        ////init the scale with the size of the min points
        Iterator<NodeData> nodeIter = _algo.getGraph().nodeIter();
        while(nodeIter.hasNext()) {
            NodeData currNode = nodeIter.next();
            StdDraw.circle(currNode.getLocation().x(),currNode.getLocation().y(), 0.01);//taking the x,y of the node, and constant rad-0.01
        }
        Iterator<EdgeData> edgeIter = _algo.getGraph().edgeIter();
        while(edgeIter.hasNext()) {
            EdgeData currEdge = edgeIter.next();
            double srcX =_algo.getGraph().getNode(currEdge.getSrc()).getLocation().x();
            double srcY =_algo.getGraph().getNode(currEdge.getSrc()).getLocation().y();
            double destX =_algo.getGraph().getNode(currEdge.getDest()).getLocation().x();
            double destY =_algo.getGraph().getNode(currEdge.getDest()).getLocation().y();

            StdDraw.line(srcX,srcY,destX,destY);
            //////////StdDraw.line();///add a narrow sign
        }


    }

    public static void main(String[] args) {
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

        DWGAlgo graph2 = new DWGAlgo();
        graph2.init(graph);
        Gui gui = new Gui(graph2);
        gui.show();

        //load
        //save
        //edit
        //runAlgo

    }

}
