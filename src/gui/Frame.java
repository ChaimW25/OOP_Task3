package gui;


import api.DirectedWeightedGraphAlgorithms;
import gui.graphMethods.*;
import gui.algoMethods.*;

import javax.swing.*;
import java.awt.event.*;


import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Frame extends JFrame implements ActionListener, MouseListener {
    private Panel pan;
    private JMenuBar menB;



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


    private boolean needToAddNode = false;
    private DirectedWeightedGraphAlgorithms graphAlgo;

    public Frame(DirectedWeightedGraphAlgorithms ans) {
        super();
        graphAlgo = ans;

        pan = new Panel(ans.getGraph());
        // this.setLayout(new BorderLayout());
        buildBar();
        this.add(pan);
        this.addMouseListener(this);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
    }

    private void buildBar() {

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

        menB = new JMenuBar();
        menB.add(file);
        menB.add(graphMethods);
        menB.add(algoMethods);
        menB.add(statistics);
        setJMenuBar(menB);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getNode) {
            new GetNode(graphAlgo.getGraph());
        }
        else if(e.getSource() == getEdge) {
            new GetEdge(graphAlgo.getGraph());
        }
        else if(e.getSource() == addNode) {
            needToAddNode = true;
        }
        else if(e.getSource() == connect) {
            new Connect(graphAlgo.getGraph(), pan);
        }
        else if(e.getSource() == removeNode) {
            new RemoveNode(graphAlgo.getGraph(), pan);
        }
        else if(e.getSource() == removeEdge) {
            new RemoveEdge(graphAlgo.getGraph(), pan);
        }
        else if (e.getSource() == nodeSize) {
            String message = "Size Of The Nodes In The Graph is: " + graphAlgo.getGraph().nodeSize();
            JOptionPane.showMessageDialog(new JFrame(), message, "Size Of Nodes", JOptionPane.DEFAULT_OPTION);
        }
        else if(e.getSource() == edgeSize) {
            String message = "Size Of The Edges In The Graph is: " + graphAlgo.getGraph().edgeSize();
            JOptionPane.showMessageDialog(new JFrame(), message, "Size Of Edges", JOptionPane.DEFAULT_OPTION);
        }

        else if(e.getSource() == getMC) {
            String message = "The number of changes In The Graph is: " + graphAlgo.getGraph().getMC();
            JOptionPane.showMessageDialog(new JFrame(), message, "Number Of Changes", JOptionPane.DEFAULT_OPTION);
        }

        else if(e.getSource() == isConnected) {
            String message;
            if(graphAlgo.isConnected()) {
                message = "The Graph Is Connected";
            }
            else {
                message = "The Graph Isn't Connected";
            }
            JOptionPane.showMessageDialog(new JFrame(), message, "Is The Graph Connected", JOptionPane.DEFAULT_OPTION);
        }
        else if(e.getSource() == shortestPathDist) {
            new ShortestPathDist(graphAlgo);
        }
        else if(e.getSource() == shortestPath) {
            new ShortestPath(graphAlgo);
        }
        else if(e.getSource() == center) {
            String message = "The Center Node In The Graph is: " + graphAlgo.center().getKey();
            JOptionPane.showMessageDialog(new JFrame(), message, "Center In Graph", JOptionPane.DEFAULT_OPTION);
        }
        else if(e.getSource() == tsp) {
            new TSP(graphAlgo);
        }
        else if(e.getSource() == save) {
            new Save(graphAlgo);
        }
        else if(e.getSource() == load) {
            new Load(graphAlgo, pan);
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (needToAddNode) {
            new AddNode(graphAlgo.getGraph(), pan, e.getX(),e.getY());
            needToAddNode = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
