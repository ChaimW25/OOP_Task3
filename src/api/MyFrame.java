//package api;
//import java.awt.FlowLayout;
//import java.awt.event.*;
//import javax.swing.*;
//
//
//public class MyFrame extends JFrame implements ActionListener{
//
//    JMenuBar menuBar;
//    JMenu loadMenu;
//    JMenu saveMenu;
//    JMenu editMenu;
//    JMenu runMenu;
//    //items for load
//    JMenuItem loadItem;
//    JMenuItem saveItem;
//    JMenuItem exitItem;
//    //items for edit
//    JMenuItem addItem;
//    JMenuItem removeItem;
//    //items for run
//    JMenuItem shortestPathItem;
//    JMenuItem tspItem;
//    DWGAlgo _algo;
//
//    //ImageIcon loadIcon;
//    //ImageIcon saveIcon;
//    //ImageIcon exitIcon;
//
//    MyFrame(){
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setSize(600,500);
//        this.setLayout(new FlowLayout());
//
//        //loadIcon = new ImageIcon("load.png");
//        //saveIcon = new ImageIcon("save.png");
//        //exitIcon = new ImageIcon("exit.png");
//
//        menuBar = new JMenuBar();
//
//        loadMenu = new JMenu("load");
//        saveMenu = new JMenu("save");
//        editMenu = new JMenu("edit");
//        runMenu = new JMenu("run");
//
//        loadItem = new JMenuItem("G1");
//        saveItem = new JMenuItem("G2");
//        exitItem = new JMenuItem("G3");
//
//        addItem = new JMenuItem("add");
//        removeItem = new JMenuItem("remove");
//
//        shortestPathItem = new JMenuItem("shortestPath");
//        tspItem = new JMenuItem("tsp");
//
//
//
//        loadItem.addActionListener(this);
//        saveItem.addActionListener(this);
//        exitItem.addActionListener(this);
//
//        //loadItem.setIcon(loadIcon);
//        //saveItem.setIcon(saveIcon);
//        //exitItem.setIcon(exitIcon);
//
//        loadMenu.setMnemonic(KeyEvent.VK_F); // Alt + f for file
//        saveMenu.setMnemonic(KeyEvent.VK_E); // Alt + e for edit
//        editMenu.setMnemonic(KeyEvent.VK_H); // Alt + h for help
//        loadItem.setMnemonic(KeyEvent.VK_L); // l for load
//        saveItem.setMnemonic(KeyEvent.VK_S); // s for save
//        exitItem.setMnemonic(KeyEvent.VK_E); // e for exit
//
//        loadMenu.add(loadItem);
//        loadMenu.add(saveItem);
//        loadMenu.add(exitItem);
//       // renMenu.add
//        runMenu.add(shortestPathItem);
//        runMenu.add(tspItem);
//       // editMenu.add()
//        editMenu.add(addItem);
//        editMenu.add(removeItem);
//
//        menuBar.add(loadMenu);
//        menuBar.add(saveMenu);
//        menuBar.add(editMenu);
//        menuBar.add(runMenu);
//
//
//        this.setJMenuBar(menuBar);
//
//        this.setVisible(true);
//
//    }
//    public void set_ar(DWGAlgo algo) {
//        _algo = algo;
//        updateFrame();
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//        if(e.getSource()==loadItem) {
//            System.out.println("*beep boop* you loaded the file");
//        }
//        if(e.getSource()==saveItem) {
//            System.out.println("*beep boop* you saved a file");
//        }
//        if(e.getSource()==exitItem) {
//            System.exit(0);
//        }
//    }
//
//
//    protected void nodeIcon(Graphics g, int radius, GeoLocation fp) {
//        g.setColor(new Color(0x000099));
//        g.fillOval((int) fp.x() - radius, (int) fp.y() - radius, 2 * radius, 2 * radius);
//    }
//
//    private void drawGraph(Graphics g) {
//    DirectedWeightedGraph graph = _algo.getGraph();
//         while (graph.nodeIter().hasNext()) {
//            drawNode(graph.nodeIter().next(), g);
//        while (graph.edgeIter().hasNext()) {
//            drawEdge(graph.edgeIter().next(), g);
//        }
//    }
//}
//
//    private void drawNode(NodeData n, Graphics g) {
//        int radius = 6;
//        GeoLocation pos = n.getLocation();
//        GeoLocation fp = _w2f.world2frame(pos);
//        nodeIcon(g, radius, fp);
//        g.setColor(Color.BLACK);
//        g.drawString("" + n.getKey(), (int) fp.x(), (int) fp.y() - 2 * radius);
//    }
//
//    private void drawEdge(EdgeData e, Graphics g) {
//        // get location info
//        DirectedWeightedGraph gg = _algo.getGraph();
//        GeoLocation s = gg.getNode(e.getSrc()).getLocation();
//        GeoLocation d = gg.getNode(e.getDest()).getLocation();
//        GeoLocation s0 = _w2f.world2frame(s);
//        GeoLocation d0 = _w2f.world2frame(d);
//
//        // draw edge line
//        g.setColor(new Color(0x000099));
////        g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setStroke(new BasicStroke(2));
//        g2.draw(new Line2D.Float((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y()));
//
//        // print weight
//        g.setColor(Color.black);
//        g.setFont(new Font("Courier", Font.PLAIN, 13));
//        String t = String.format("%.2f", e.getWeight());
//        int x = (int) ((s0.x() + d0.x()) / 2);
//        int y = (int) ((s0.y() + d0.y()) / 2) - 3;
//        if (e.getSrc() < e.getDest()) y += 15;
////        g.drawString(t, x, y);
//    }
//
//
////        private void drawEdge(edge_data e, Graphics g) {
//
//
//
//        public static void main(String[] args) {
//
//            new MyFrame();
//        }
//  //  }
//}