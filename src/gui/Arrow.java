package myGui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;


public class Arrow {
    private static final Polygon ARROW_HEAD = new Polygon();

    static {
        ARROW_HEAD.addPoint(0, 0);
        ARROW_HEAD.addPoint(-5, -10);
        ARROW_HEAD.addPoint(5, -10);
    }
    private final int x;
    private final int y;
    private final int endX;
    private final int endY;
    private final Color color;
    private final int thickness;

    public Arrow(int x, int y, int x2, int y2, Color color, int thickness) {
        super();
        this.x = x;
        this.y = y;
        this.endX = x2;
        this.endY = y2;
        this.color = color;
        this.thickness = thickness;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Calculate the angle of the arrow.
        double angle = Math.atan2(endY - y, endX - x);

        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));

        // Draw the line. Crops 10 pixels at the tip so the tip doesn't get thick.
        g2.drawLine(x, y, (int) (endX - 10 * Math.cos(angle)), (int) (endY - 10 * Math.sin(angle)));

        // Gets the original AffineTransform.
        AffineTransform tx1 = g2.getTransform();

        // Creates a copy of AffineTransform.
        AffineTransform tx2 = (AffineTransform) tx1.clone();

        // Translates and rotates the new AffineTransform.
        tx2.translate(endX, endY);
        tx2.rotate(angle - Math.PI / 2);

        // Draw the tip with the AffineTransform translated and rotated.
        g2.setTransform(tx2);
        g2.fill(ARROW_HEAD);

        // Restores the original AffineTransform.
        g2.setTransform(tx1);
    }
}
