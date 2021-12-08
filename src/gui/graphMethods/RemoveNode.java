package gui.graphMethods;

import javax.swing.*;

import api.DirectedWeightedGraph;
import gui.Panel;

import java.awt.event.*;
import java.awt.*;

public class RemoveNode extends JFrame implements ActionListener {
    private JTextField inputKey;
    private JButton button;
    private JLabel textKey;

    private DirectedWeightedGraph graph;
    private Panel panel;

    // default constructor
    public RemoveNode(DirectedWeightedGraph graph, Panel panel) {
        // create a new frame to store text field and button
        super("Remove Node");
        this.graph = graph;
        this.panel = panel;
        // create a label to display text
        textKey = new JLabel("Key:");
        // create a new button
        button = new JButton("Enter");
        // addActionListener to button
        button.addActionListener(this);
        // create a object of JTextField with 16 columns
        inputKey = new JTextField(8);
        inputKey.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    closeWindow();
                }
            }
        });

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();
        // add buttons and textfield to panel
        p.add(textKey);
        p.add(inputKey);
        p.add(button);

        //p.setPreferredSize(new Dimension(125, 100));
        // add panel to frame
        add(p);

        // set the size of frame
        pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setResizable(true);
        setVisible(true);
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Ent")) {
            closeWindow();
        }
    }

    private void closeWindow() {
        // set the text of the label to the text of the field
        setVisible(false);
        try {
            int key = Integer.parseInt(inputKey.getText());
            if (graph.removeNode(key) == null) {
                String message = "Node Not Found";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
            }
            panel.repaint();
        }
        catch (Exception e) {
            String message = "Error";
            JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.dispose();
    }
}
