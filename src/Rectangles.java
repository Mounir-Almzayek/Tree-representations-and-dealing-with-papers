public class Rectangles {
    char name;
    int width;
    int length;

    public Rectangles(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public Rectangles(char name) {
        this.name = name;
    }

    public Rectangles(char name, int width, int length) {
        this.name = name;
        this.width = width;
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        if(name != '|' && name != '-'){
            return name +"[" + width + "," + length + "]";
        }
        return name + "";
    }
    public static Rectangles parseRectangle(String line) {
        line = line.trim();
        if (line.isEmpty()) {
            return null;
        }
        int startWidth = line.indexOf('[') + 1;
        int endWidth = line.indexOf(',', startWidth);
        int endLength = line.indexOf(']', endWidth);

        if (startWidth == -1 || endWidth == -1 || endLength == -1) {
            return null;
        }

        char name = line.charAt(0);
        int width = Integer.parseInt(line.substring(startWidth, endWidth).trim());
        int length = Integer.parseInt(line.substring(endWidth + 1, endLength).trim());

        return new Rectangles(name, width, length);
    }


}
