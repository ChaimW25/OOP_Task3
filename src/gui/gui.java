package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import api.*;
import apiImplementations.DWGAlgo;
import apiImplementations.Geo;
import apiImplementations.Node;

public class gui extends JFrame implements ActionListener, MouseListener {
    DirectedWeightedGraph graph;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    //Screen width
    int width = (int) screen.getWidth();
    //Screen height
    int height = (int) screen.getHeight();


    JPanel jPanel;

    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem save;
    private JMenuItem load;

    private JMenu graphMethods;
    private JMenuItem getNode;
    private JMenuItem getEdge;
    private JMenuItem addNode;
    private JMenuItem connect;
    private JMenuItem removeNode;
    private JMenuItem removeEdge;

    private JMenu algoMethods;
    private JMenuItem isConnected;
    private JMenuItem shortestPathDist;
    private JMenuItem shortestPath;
    private JMenuItem center;
    private JMenuItem tsp;//need to do

    private JMenu statistics;
    private JMenuItem nodeSize;
    private JMenuItem edgeSize;
    private JMenuItem getMC;

    //constructor
    public gui(DirectedWeightedGraph graph){
        this.graph = graph;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, width, height);
        this.setSize(width, height);
        this.setResizable(false);
        this.setBackground(Color.black);

        scale();
        buildMenu();
        JPanel();

    }

    private void buildMenu() {

        file = new JMenu("File");
        save = new JMenuItem("Save");
        save.addActionListener(this);
        load = new JMenuItem("Load");
        load.addActionListener(this);

        graphMethods = new JMenu("Graph methods");
        getNode = new JMenuItem("Get Node");
        getNode.addActionListener(this);
        getEdge = new JMenuItem("Get Edge");
        getEdge.addActionListener(this);
        addNode = new JMenuItem("Add Node");
        addNode.addActionListener(this);
        connect = new JMenuItem("Connect Nodes");
        connect.addActionListener(this);
        removeNode = new JMenuItem("Remove Node");
        removeNode.addActionListener(this);
        removeEdge = new JMenuItem("Remove Edge");
        removeEdge.addActionListener(this);


        statistics = new JMenu("Graph statistics");
        nodeSize = new JMenuItem("Size Of Nodes");
        nodeSize.addActionListener(this);
        edgeSize = new JMenuItem("Size Of Edges");
        edgeSize.addActionListener(this);
        getMC = new JMenuItem("number of changes");
        getMC.addActionListener(this);

        algoMethods = new JMenu("Algorithm methods");
        isConnected = new JMenuItem("Is Connected");
        isConnected.addActionListener(this);
        shortestPathDist = new JMenuItem("Shortest Path Dist");
        shortestPathDist.addActionListener(this);
        shortestPath = new JMenuItem("Shortest Path");
        shortestPath.addActionListener(this);
        center = new JMenuItem("Center");
        center.addActionListener(this);
        tsp = new JMenuItem("TSP");
        tsp.addActionListener(this);


        file.add(save);
        file.add(load);

        graphMethods.add(getNode);
        graphMethods.add(getEdge);
        graphMethods.add(addNode);
        graphMethods.add(connect);
        graphMethods.add(removeNode);
        graphMethods.add(removeEdge);

        statistics.add(nodeSize);
        statistics.add(edgeSize);
        statistics.add(getMC);

        algoMethods.add(isConnected);
        algoMethods.add(shortestPathDist);
        algoMethods.add(shortestPath);
        algoMethods.add(center);
        algoMethods.add(tsp);

        menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(graphMethods);
        menuBar.add(algoMethods);
        menuBar.add(statistics);
        setJMenuBar(menuBar);
    }

    //scale the nodes.
    private void scale(){

        double maxX = this.maxX();
        double minX = this.minX();
        double maxY = this.maxY();
        double minY = this.minY();

        Iterator<NodeData> nodeIter = this.graph.nodeIter();
        while (nodeIter.hasNext()) {
            Node node = (Node) nodeIter.next();
            Geo pos = (Geo) node.getLocation();

            double newX = (width * (pos.x() - minX)) / (maxX - minX) + width * 0.04;
            double newY = (height * (maxY - pos.y())) / (maxY - minY) + height * 0.2;
            Geo geo = new Geo(newX * 0.7+","+ (height - newY * 0.7)+","+ 0);
            node.setLocation(geo);
        }
    }

    private double minX (){
        Iterator<NodeData> nodeIter = graph.nodeIter();
        double minX = Integer.MAX_VALUE;
        while (nodeIter.hasNext()){
            NodeData temp = nodeIter.next();
            if (temp.getLocation().x() < minX){
                minX = temp.getLocation().x();
            }
        }
        return minX;
    }

    private double minY (){
        Iterator<NodeData> nodeIter = graph.nodeIter();
        double minY = Integer.MAX_VALUE;
        while (nodeIter.hasNext()){
            NodeData temp = nodeIter.next();
            if (temp.getLocation().y() < minY){
                minY = temp.getLocation().y();
            }
        }
        return minY;
    }

    private double maxY (){
        Iterator<NodeData> nodeIter = graph.nodeIter();
        double maxY = Integer.MIN_VALUE;
        while (nodeIter.hasNext()){
            NodeData temp = nodeIter.next();
            if (temp.getLocation().y() > maxY){
                maxY = temp.getLocation().y();
            }
        }
        return maxY;
    }

    private double maxX (){
        Iterator<NodeData> nodeIter = graph.nodeIter();
        double maxX = Integer.MIN_VALUE;
        while (nodeIter.hasNext()){
            NodeData temp = nodeIter.next();
            if (temp.getLocation().x() > maxX){
                maxX = temp.getLocation().x();
            }
        }
        return maxX;
    }

    private void JPanel(){
        jPanel = new JPanel();
        jPanel.setBounds(0,0,width, height);
        jPanel.setBackground(Color.BLACK);
        this.add(jPanel);
        this.getContentPane().setLayout(null);
    }

    public void drawNode(NodeData node, Graphics2D g) {
        GeoLocation loc = node.getLocation();
        g.setColor(Color.PINK);
        g.setFont(new Font("Ariel", Font.PLAIN, 13));
        g.drawString("" + node.getKey(), (int) loc.x() , (int) (loc.y() - 10));
        g.setColor(Color.MAGENTA);
        g.fillOval((int) loc.x() - 6, (int) loc.y() - 6, 6 * 2, 6 * 2);
    }

    private void drawEdge(EdgeData edge, Graphics g) {
        Geo src = (Geo) graph.getNode(edge.getSrc()).getLocation();
        Geo dest = (Geo) graph.getNode(edge.getDest()).getLocation();
        myGui.Arrow arrow = new myGui.Arrow((int) src.x(), (int) src.y(), (int) dest.x(), (int) dest.y(), Color.WHITE, 1);
        arrow.draw(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintComponents(g);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.setFont(new Font("Ariel", Font.PLAIN, 10));
        Graphics2D g2D = (Graphics2D) g;
        Iterator<EdgeData> iterE = graph.edgeIter();
        while (iterE.hasNext()) {
            drawEdge(iterE.next(), g2D);
        }
        Iterator<NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData n = nodeIter.next();
            drawNode(n, g2D);
        }
    }

//כפתורים
    @Override
    public void actionPerformed(ActionEvent e) {

        DirectedWeightedGraphAlgorithms graphAlgo = new DWGAlgo();
        graphAlgo.init(graph);

        if (e.getSource() == nodeSize)
        {
            String message = "Size Of The Nodes In The Graph is: " + graphAlgo.getGraph().nodeSize();
            JOptionPane.showMessageDialog(new JFrame(), message, "Size Of Nodes", JOptionPane.PLAIN_MESSAGE);
        }

        else if(e.getSource() == edgeSize)
        {
            String message = "Size Of The Edges In The Graph is: " + graphAlgo.getGraph().edgeSize();
            JOptionPane.showMessageDialog(new JFrame(), message, "Size Of Edges", JOptionPane.PLAIN_MESSAGE);
        }

        else if(e.getSource() == getMC)
        {
            String message = "The number of changes In The Graph is: " + graphAlgo.getGraph().getMC();
            JOptionPane.showMessageDialog(new JFrame(), message, "Number Of Changes", JOptionPane.PLAIN_MESSAGE);
        }

        else if(e.getSource() == isConnected)
        {
            String message;
            if(graphAlgo.isConnected()) {
                message = "The Graph Is Connected";
            }
            else {
                message = "The Graph Isn't Connected";
            }
            JOptionPane.showMessageDialog(new JFrame(), message, "Is The Graph Connected", JOptionPane.PLAIN_MESSAGE);
        }

        else if(e.getSource() == center)
        {
            String message = "The Center Node In The Graph is: " + graphAlgo.center().getKey();
            JOptionPane.showMessageDialog(new JFrame(), message, "Center In Graph", JOptionPane.PLAIN_MESSAGE);
        }

        else if (e.getSource() == shortestPathDist)
        {
            while (true) {
                int src = Integer.parseInt(JOptionPane.showInputDialog( "Enter src node:"));
                int dest = Integer.parseInt(JOptionPane.showInputDialog( "Enter dest node:"));

                if (graph.getNode(dest) != null && graph.getNode(src) != null) {
                    String message = "The Shortest Path Dist In The Graph is: " + graphAlgo.shortestPathDist(src, dest);
                    JOptionPane.showMessageDialog(new JFrame(), message, "Shortest Path Dist", JOptionPane.PLAIN_MESSAGE);
                    break;
                } else
                    JOptionPane.showMessageDialog(new JFrame(), "Wrong inputs, try again", JOptionPane.INPUT_VALUE_PROPERTY, JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == shortestPath)
        {
            while (true) {
                int src = Integer.parseInt(JOptionPane.showInputDialog( "Enter src node:"));
                int dest = Integer.parseInt(JOptionPane.showInputDialog( "Enter dest node:"));
                if (graph.getNode(dest) != null && graph.getNode(src) != null) {
                    StringBuilder ans = new StringBuilder("The shortest path is: ");
                    for (NodeData n : graphAlgo.shortestPath(src, dest)) {
                        ans.append("->").append(n.getKey());
                    }
                    JOptionPane.showMessageDialog(new JFrame(), ans, "Path", JOptionPane.PLAIN_MESSAGE);
                    break;
                } else
                    JOptionPane.showMessageDialog(new JFrame(), "Wrong inputs, try again", JOptionPane.INPUT_VALUE_PROPERTY, 0);
            }
        }

        if (e.getSource() == tsp)
        {
            while (true) {
                String input = JOptionPane.showInputDialog("Enter a list of nodes keys (for example:2,4,6):");
                String[] inputArr = input.split(",");
                List<NodeData> cities = new ArrayList<>();
                int id;
                NodeData node;
                for (String s : inputArr) {
                    id = Integer.parseInt(s);
                    node = graphAlgo.getGraph().getNode(id);
                    if (node == null) {
                        JOptionPane.showMessageDialog(new JFrame(), "Wrong inputs, try again", JOptionPane.INPUT_VALUE_PROPERTY, 0);
                        return;
                    }
                    cities.add(node);
                }
                StringBuilder ans = new StringBuilder("The TSP path is: ");
                for (NodeData n : graphAlgo.tsp(cities)) {
                    ans.append("->").append(n.getKey());
                }
                JOptionPane.showMessageDialog(new JFrame(), ans, "TSP", JOptionPane.PLAIN_MESSAGE);
                break;
            }
        }

        else if (e.getSource() == getNode)
        {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter node id to get:"));
            if (graph.getNode(id) != null) {
                String message = "Key: " + id + "\nLocation: " + graph.getNode(id).getLocation();
                JOptionPane.showMessageDialog(new JFrame(), message, "get Node", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(), "Wrong inputs, try again", JOptionPane.INPUT_VALUE_PROPERTY, JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getSource() == getEdge)
        {
            int src = Integer.parseInt(JOptionPane.showInputDialog( "Enter src node:"));
            int dest = Integer.parseInt(JOptionPane.showInputDialog( "Enter dest node:"));
            if (graph.getNode(dest) != null && graph.getNode(src) != null && graph.getEdge(src, dest) != null) {
                String message = "Src: " + src + "\nDest: " + dest + "\nWeight:" + graph.getEdge(src, dest).getWeight();
                JOptionPane.showMessageDialog(new JFrame(), message, "get Edge", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(), "Wrong inputs, try again", JOptionPane.INPUT_VALUE_PROPERTY, JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getSource() == connect)
        {
            while (true) {
                int src = Integer.parseInt(JOptionPane.showInputDialog( "Enter src node:"));
                int dest = Integer.parseInt(JOptionPane.showInputDialog( "Enter dest node:"));
                double weight = Double.parseDouble(JOptionPane.showInputDialog( "Enter weight:"));

                if ( weight > 0 && graph.getNode(dest) != null && graph.getNode(src) != null) {
                    graph.connect(src, dest, weight);
                    graphAlgo.init(graph);
                    repaint();
                    break;
                } else
                    JOptionPane.showMessageDialog(new JFrame(), "Wrong inputs, try again", JOptionPane.INPUT_VALUE_PROPERTY, JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getSource() == addNode)
        {
            JOptionPane.showMessageDialog(new JFrame(), "Qlik to creat a node", "Qlik", JOptionPane.PLAIN_MESSAGE);
            this.addMouseListener(this);
        }

        else if (e.getSource() == removeNode)
        {
            while (true) {
                int nodeToRemove = Integer.parseInt(JOptionPane.showInputDialog("Enter node id to remove:"));
                if (graph.getNode(nodeToRemove) != null) {
                    graph.removeNode(nodeToRemove);
                    graphAlgo.init(graph);
                    repaint();
                    break;
                } else
                    JOptionPane.showMessageDialog(new JFrame(), "Wrong input, try again", JOptionPane.INPUT_VALUE_PROPERTY, JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getSource() == removeEdge) {
            while (true) {
                int src = Integer.parseInt(JOptionPane.showInputDialog("Enter src node:"));
                int dest = Integer.parseInt(JOptionPane.showInputDialog("Enter dest node:"));
                if (graph.getNode(src) != null && graph.getNode(dest) != null) {
                    graph.removeEdge(src, dest);
                    repaint();
                    break;
                } else
                    JOptionPane.showMessageDialog(new JFrame(), "Wrong inputs, try again", JOptionPane.INPUT_VALUE_PROPERTY, JOptionPane.ERROR_MESSAGE);
            }

        }

        else if (e.getSource() == load){
            String name = JOptionPane.showInputDialog("Enter the name of the file:");
            if (graphAlgo.load(name)){
                graph = graphAlgo.getGraph();
                scale();
                repaint();
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(), "Error, try again", JOptionPane.INPUT_VALUE_PROPERTY, JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getSource() == save){
            String name = JOptionPane.showInputDialog("Enter the name of the file:");
//            String path = System.getProperty("user.dir") + "\\data\\";
//            if (graphAlgo.save(path + name + ".json")){
            if (graphAlgo.save(  name + ".json")){
                    JOptionPane.showMessageDialog(new JFrame(), "The file saved", "save", JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(new JFrame(), "Error, try again", JOptionPane.INPUT_VALUE_PROPERTY, JOptionPane.ERROR_MESSAGE);
                }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String pos = e.getX()+","+e.getY()+","+0.0;
        NodeData node = new Node(graph.nodeSize(), pos);
        graph.addNode(node);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
