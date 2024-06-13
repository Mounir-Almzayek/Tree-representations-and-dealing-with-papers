public class NodeB {

    Rectangles key;
    NodeB left;
    NodeB right;

    public NodeB(Rectangles key, NodeB left, NodeB right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    public NodeB(Rectangles key) {
        this.key = key;
    }

    public NodeB() {
    }
}
