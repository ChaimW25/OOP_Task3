package gui;
import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import apiImplementations.Node;

import javax.swing.*;
import java.awt.*;

import java.util.Iterator;


public class Panel extends JPanel {
    private double xMin;
    private double yMin;
    private double xMax;
    private double yMax;
    private double center;
    private double xUnit;
    private double yUnit;


    private DirectedWeightedGraph graph;

    /**
     *     constructor. the DWGraph for init.
     */
    public Panel(DirectedWeightedGraph graph) {
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        init(graph);
    }
    public void init(DirectedWeightedGraph graph) {
        this.graph = graph;
        findEdge();
    }

    /**
     * find edge to use for the draw
     */
    private void findEdge() {
        Iterator<NodeData> n = graph.nodeIter();
        NodeData node = n.next();
        xMin = node.getLocation().x();
        yMin = node.getLocation().y();
        xMax = node.getLocation().x();
        yMax = node.getLocation().y();
        while (n.hasNext()) {
            node = n.next();
            xMin = Math.min(xMin, node.getLocation().x());
            yMin = Math.min(yMin, node.getLocation().y());

            xMax = Math.max(xMax, node.getLocation().x());
            yMax = Math.max(yMax, node.getLocation().y());
        }

    }

    /**
     * draw the components edges and nodes(verticals)
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        xUnit = this.getWidth() / Math.abs(xMax - xMin) * 0.75;
        yUnit = this.getHeight() / Math.abs(yMax - yMin) * 0.75 ;
        //drawArrowLine(g, 20, 20, 200, 200, 30, 7);
        //drawLines(g);
        drawEdges(g);
        drawNodes(g);
    }

    /**
     * draw nodes (verticals) on the panel
     * @param g
     */
    public void drawNodes(Graphics g) {
        Iterator<NodeData> iter = graph.nodeIter();
        while (iter.hasNext()) {
            NodeData node = iter.next();
            // draw the node
            int x = (int) ((node.getLocation().x() - xMin) * xUnit);
            int y = (int) ((node.getLocation().y() - yMin) * yUnit);
            g.setColor(Color.WHITE);
            g.fillOval(x-1, y-1, 30, 30);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Ariel", Font.PLAIN, 15));
            g.drawString("" + node.getKey(), x + 5, y + 15);
        }
    }

    /**
     * draw the Edge by using arrow and lines
     * @param g- the graphics component
     */

    public void drawEdges(Graphics g) {
        Iterator<EdgeData> iter = graph.edgeIter();
        while (iter.hasNext()) {
            EdgeData edge = iter.next();

            double srcX = graph.getNode(edge.getSrc()).getLocation().x();
            srcX = ((srcX - xMin) * xUnit) + 12;
            double srcY = graph.getNode(edge.getSrc()).getLocation().y();
            srcY = ((srcY - yMin) * yUnit) + 12;

            double destX = graph.getNode(edge.getDest()).getLocation().x();
            destX = ((destX - xMin) * xUnit) + 12;
            double destY = graph.getNode(edge.getDest()).getLocation().y();
            destY = ((destY - yMin) * yUnit) + 12;

            g.setColor(Color.PINK);
            drawArrowLine(g, (int) srcX, (int) srcY, (int) destX, (int) destY, 30, 7);

            
            String weightString = edge.getWeight() + "";
            try {
                weightString = weightString.substring(0,weightString.indexOf(".")+3);
            } catch(Exception e) {
                e.printStackTrace();
            }
            g.setColor(Color.PINK);
            g.setFont(new Font("Ariel", Font.PLAIN, 10));
            g.drawString(weightString, (int)(srcX*0.25 + destX*0.75),(int)(srcY*0.25 + destY*0.75));
        }
    }
    /**
     * Draw an arrow line between two points.
     *
     * @param g - the graphic component.
     * @param x1 x-pos of first point.
     * @param y1 y-pos of first point.
     * @param x2 x-pos of second point.
     * @param y2 y-pos of second point.
     * @param d   width of the arrow.
     * @param h   height of the arrow.
     */
    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    public void addNode(int key, int x, int y) {
        y -= 50;
        x-=5;
        double newX = (x-12)/ xUnit + xMin;
        double newY = (y-12)/ yUnit + yMin;
        graph.addNode(new Node(key,newX+","+newY+",0"));
        repaint();
    }

    
}
