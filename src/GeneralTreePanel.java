import java.awt.*;
import javax.swing.*;

public class GeneralTreePanel extends JPanel {
    private NodeG root;

    public GeneralTreePanel(NodeG root) {
        this.root = root;
        setPreferredSize(new Dimension(1200, 650));
        setBackground(Color.decode("#DDE6ED"));
    }

    public void updateTree(NodeG root) {
        this.root = root;
        repaint();
    }

    private int calculateDepth(NodeG node) {
        if (node == null || node.children == null) return 1;
        int maxDepth = 0;
        for (NodeG child : node.children) {
            int depth = calculateDepth(child);
            if (depth > maxDepth) maxDepth = depth;
        }
        return maxDepth + 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int depth = calculateDepth(root);
        drawTree(g, getWidth() / 2 , getHeight() / 3, root, 50, 12 * ((int) Math.pow(2, depth+1)));
    }

    private void drawTree(Graphics g, int x, int y, NodeG node, int offsetY, int offsetX) {
        if (node == null) return;

        int circleDiameter = 40;

        g.setColor(Color.decode("#27374D"));
        g.fillOval(x - circleDiameter / 2, y - circleDiameter / 2, circleDiameter, circleDiameter);
        g.setColor(Color.decode("#EEEDEB"));
        g.drawString(String.valueOf(node.name), x - 4, y + 5);

        if (node.children != null) {
            int numChildren = node.children.size();
            int startX = x - ((numChildren-1) * offsetX) / 2;
            int currentX = startX;
            for (NodeG child : node.children) {
                g.setColor(Color.decode("#1F2544"));
                g.drawLine(x, y + circleDiameter / 2, currentX + circleDiameter / 2, y + offsetY - circleDiameter / 2);
                drawTree(g, currentX + circleDiameter / 2, y + offsetY, child, offsetY, offsetX / 2);
                currentX += offsetX;
            }
        }
    }
}
