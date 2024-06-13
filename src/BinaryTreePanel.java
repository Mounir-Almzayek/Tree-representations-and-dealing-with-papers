import javax.swing.*;
import java.awt.*;

public class BinaryTreePanel extends JPanel {
    private NodeB root;

    public BinaryTreePanel(NodeB root) {
        this.root = root;
        setPreferredSize(new Dimension(3000, 500));
        setBackground(Color.decode("#DDE6ED"));

    }

    public void updateTree(String treeString) {
        BinaryTree binaryTree = new BinaryTree(BinaryTree.readNodeFromExpression(treeString));
        this.root = binaryTree.root;
        repaint();
    }

    public void updateTree(NodeB root) {
        this.root = root;
        repaint();
    }

    private int calculateDepth(NodeB nodeB) {
        if (nodeB == null) return 0;
        int leftDepth = calculateDepth(nodeB.left);
        int rightDepth = calculateDepth(nodeB.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int depth = calculateDepth(root);
        drawTree(g, getWidth() / 2, getHeight()/3, root, 50, 6*((int)Math.pow(2, depth)));
    }

    private void drawTree(Graphics g, int x, int y, NodeB nodeB, int offsetY, int offsetX) {
        if (nodeB == null) return;

        int circleDiameter = 40;

        g.setColor(Color.decode("#27374D"));
        g.fillOval(x - circleDiameter / 2, y - circleDiameter / 2, circleDiameter, circleDiameter);
        g.setColor(Color.decode("#EEEDEB"));
        g.drawString(String.valueOf(nodeB.key.name), x - 4, y + 5);

        if (nodeB.left != null) {
            g.setColor(Color.decode("#1F2544"));
            g.drawLine(x, y + circleDiameter / 2, x - offsetX, y + offsetY - circleDiameter / 2);
            drawTree(g, x - offsetX, y + offsetY, nodeB.left, offsetY , offsetX /2);
        }

        if (nodeB.right != null) {
            g.setColor(Color.decode("#1F2544"));
            g.drawLine(x, y + circleDiameter / 2, x + offsetX, y + offsetY - circleDiameter / 2);
            drawTree(g, x + offsetX, y + offsetY, nodeB.right, offsetY , offsetX /2);
        }
    }
}
