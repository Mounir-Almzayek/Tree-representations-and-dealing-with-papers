import java.util.ArrayList;

public class NodeG {
    char name;
    ArrayList <NodeG> children;

    public NodeG(char name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public NodeG(char name, ArrayList<NodeG> children) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public NodeG() {
    }

}
