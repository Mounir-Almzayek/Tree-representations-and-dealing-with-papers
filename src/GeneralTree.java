import java.io.*;
import java.util.ArrayList;

public class GeneralTree {
    NodeG root;

    public GeneralTree(NodeG root) {
        this.root = root;
    }

    public GeneralTree() {
    }
    static public void exportTheTreeToAFileAsAFormat(NodeG root){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("generalTreeFormat.txt"))) {
            writeTheTree(root,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private void writeTheTree(NodeG root, BufferedWriter writer) throws IOException {
        if (root == null||root.children==null) {
            return;
        }

        writer.write(root.name + "->");
        for (int i = 0; i < root.children.size(); i++) {
            writer.write(root.children.get(i).name);
            if (i < root.children.size() - 1) {
                writer.write(",");
            }
        }
        writer.write("\n");

        for (NodeG child : root.children) {
            writeTheTree(child, writer);
        }
    }

    public static GeneralTree buildTreeFromFile(String filePath) {
        GeneralTree tree = new GeneralTree();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buildTreeFromString(tree, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static void buildTreeFromString(GeneralTree tree, String line) {
        if (line == null || line.isEmpty()) {
            return;
        }

        String[] parts = line.split("->");

        char parentName = parts[0].trim().charAt(0);
        String[] childrenNames = parts[1].split(",");

        if (tree.root == null) {
            tree.root = new NodeG(parentName);
        }

        NodeG parentNode = findOrCreateNode(tree.root, parentName);

        if (parentNode.children == null) {
            parentNode.children = new ArrayList<>();
        }

        for (String childName : childrenNames) {
            NodeG childNode = new NodeG(childName.trim().charAt(0));
            parentNode.children.add(childNode);
        }
    }

    private static NodeG findOrCreateNode(NodeG root, char nodeName) {
        if (root == null) {
            return new NodeG(nodeName);
        }
        if (root.name == nodeName) {
            return root;
        }
        if (root.children == null) {
            root.children = new ArrayList<>();
            return null;
        }
        for (NodeG child : root.children) {
            NodeG found = findOrCreateNode(child, nodeName);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
    public static NodeB convertToBinaryTree(NodeG root) {
        if (root == null) {
            return null;
        }

        NodeB binaryRoot = new NodeB(new Rectangles(root.name));

        if (!root.children.isEmpty()) {
            binaryRoot.right = convertToBinaryTree(root.children.get(root.children.size()-1));
        }

        NodeB currentBinaryNode = binaryRoot.right;
        for (int i = root.children.size()-2; i >= 0; i--) {
            currentBinaryNode.left = convertToBinaryTree(root.children.get(i));
            currentBinaryNode = currentBinaryNode.left;
        }
        return binaryRoot;
    }
}
