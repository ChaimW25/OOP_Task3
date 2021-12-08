package api;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;




public class MyFrame extends JFrame implements ActionListener{

    JMenuBar menuBar;
    JMenu loadMenu;
    JMenu saveMenu;
    JMenu editMenu;
    JMenu runMenu;
    //items for load
    JMenuItem loadItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    //items for edit
    JMenuItem addItem;
    JMenuItem removeItem;
    //items for run
    JMenuItem shortestPathItem;
    JMenuItem tspItem;

    //ImageIcon loadIcon;
    //ImageIcon saveIcon;
    //ImageIcon exitIcon;

    MyFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,500);
        this.setLayout(new FlowLayout());

        //loadIcon = new ImageIcon("load.png");
        //saveIcon = new ImageIcon("save.png");
        //exitIcon = new ImageIcon("exit.png");

        menuBar = new JMenuBar();

        loadMenu = new JMenu("load");
        saveMenu = new JMenu("save");
        editMenu = new JMenu("edit");
        runMenu = new JMenu("run");

        loadItem = new JMenuItem("G1");
        saveItem = new JMenuItem("G2");
        exitItem = new JMenuItem("G3");

        addItem = new JMenuItem("add");
        removeItem = new JMenuItem("remove");

        shortestPathItem = new JMenuItem("shortestPath");
        tspItem = new JMenuItem("tsp");



        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        //loadItem.setIcon(loadIcon);
        //saveItem.setIcon(saveIcon);
        //exitItem.setIcon(exitIcon);

        loadMenu.setMnemonic(KeyEvent.VK_F); // Alt + f for file
        saveMenu.setMnemonic(KeyEvent.VK_E); // Alt + e for edit
        editMenu.setMnemonic(KeyEvent.VK_H); // Alt + h for help
        loadItem.setMnemonic(KeyEvent.VK_L); // l for load
        saveItem.setMnemonic(KeyEvent.VK_S); // s for save
        exitItem.setMnemonic(KeyEvent.VK_E); // e for exit

        loadMenu.add(loadItem);
        loadMenu.add(saveItem);
        loadMenu.add(exitItem);
       // renMenu.add
        runMenu.add(shortestPathItem);
        runMenu.add(tspItem);
       // editMenu.add()
        editMenu.add(addItem);
        editMenu.add(removeItem);

        menuBar.add(loadMenu);
        menuBar.add(saveMenu);
        menuBar.add(editMenu);
        menuBar.add(runMenu);


        this.setJMenuBar(menuBar);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==loadItem) {
            System.out.println("*beep boop* you loaded the file");
        }
        if(e.getSource()==saveItem) {
            System.out.println("*beep boop* you saved a file");
        }
        if(e.getSource()==exitItem) {
            System.exit(0);
        }
    }

    //public class Main{

        public static void main(String[] args) {

            new MyFrame();
        }
  //  }
}