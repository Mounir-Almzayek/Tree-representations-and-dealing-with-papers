import java.io.*;
import java.util.*;

public class BinaryTree {
    NodeB root;

    public BinaryTree(NodeB root) {
        this.root = root;
    }

    private static void inorder(NodeB root, BufferedWriter writer) throws IOException {
        if (root == null)
            return;

        if (root.left != null || root.right != null)
            writer.write("(");
        inorder(root.left, writer);
        writer.write(root.key.toString());
        inorder(root.right, writer);

        if (root.left != null || root.right != null)
            writer.write(")");
    }

    static public void exportTheTreeToAFileAsAFormat(NodeB root){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("BinaryTreeFormat.txt"))) {
            inorder(root, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  ((A[20,10]|(B[20,10]|C[30,10]))-(D[30,50]|(E[40,30]-F[40,20])))
    public static NodeB readNodeFromExpression(String expression) {
        if (expression.isEmpty())
            return null;

        Stack<Character> stack = new Stack<>();
        Stack<NodeB> nodeBStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (currentChar == '(') {
                stack.push(currentChar);
            } else if (currentChar == ')') {
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                    if (nodeBStack.size() >= 2) {
                        NodeB right = nodeBStack.pop();
                        NodeB operator = nodeBStack.pop();
                        NodeB left = nodeBStack.pop();
                        operator.left = left;
                        operator.right = right;
                        nodeBStack.push(operator);
                    }
                } else {
                    System.err.println("Error: Mismatched parenthesis.");
                    return null;
                }
            } else {
                if (currentChar != '|' && currentChar != '-') {
                    char nodeName = currentChar;
                    int value1 = 0, value2 = 0;
                    if (expression.charAt(i + 1) == '[') {
                        int j = i + 2;
                        StringBuilder valueBuilder = new StringBuilder();
                        while (expression.charAt(j) != ',') {
                            valueBuilder.append(expression.charAt(j));
                            j++;
                        }
                        value1 = Integer.parseInt(valueBuilder.toString());

                        valueBuilder.setLength(0);
                        j++;
                        while (expression.charAt(j) != ']') {
                            valueBuilder.append(expression.charAt(j));
                            j++;
                        }
                        value2 = Integer.parseInt(valueBuilder.toString());

                        i = j;
                    }
                    nodeBStack.push(new NodeB(new Rectangles(nodeName, value1, value2)));
                } else {
                    nodeBStack.push(new NodeB(new Rectangles(currentChar)));
                }
            }
        }
        return nodeBStack.isEmpty() ? null : nodeBStack.pop();
    }

    public static NodeB importTheTreeFromAFileByAFormat(String filename) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readNodeFromExpression(sb.toString());
    }

    public static char[][] dfs(NodeB node){
        char arr[][] = new char[200][200];
        if(node.right==null && node.left==null){
            arr = new char[node.key.length][node.key.width];
            for(int i = 0; i < node.key.length; i++){
                for (int j = 0; j < node.key.width; j++){
                    if(i == 0 || i == node.key.length-1) arr[i][j] = '-';
                    else if(j == 0 || j == node.key.width-1 ) arr[i][j] = '|';
                    else arr[i][j] = ' ';
                }
            }
            arr[node.key.length/2][node.key.width/2]= node.key.name;
        }else{
            char left[][] = dfs(node.left);
            char right[][] = dfs(node.right);
            if(node.key.name=='|'){
                arr = new char[left.length][left[0].length+right[0].length];
                for(int i = 0; i < left.length; i++){
                    for(int j = 0; j < left[0].length; j++){
                        arr[i][j] = left[i][j];
                    }
                }
                for(int i = 0; i < right.length; i++){
                    for(int j = 0; j < right[0].length; j++){
                        arr[i][j+left[0].length] = right[i][j];
                    }
                }
            }else if(node.key.name=='-'){
                arr = new char[left.length+right.length][left[0].length];
                for(int i = 0; i < left.length; i++){
                    for(int j = 0; j < left[0].length; j++){
                        arr[i][j] = left[i][j];
                    }
                }
                for(int i = 0; i < right.length; i++){
                    for(int j = 0; j < right[0].length; j++){
                        arr[i+left.length][j] = right[i][j];
                    }
                }
            }
        }
        return arr;
    }

    public static void exportTheTreeToAFileAsADraw(NodeB root){
        char[][] arr = BinaryTree.dfs(root);
        try(PrintWriter writer = new PrintWriter(new File("Draw.txt"))) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    if (j != arr[i].length-1)
                        writer.print(arr[i][j] + " ");
                    else
                        writer.print(arr[i][j]);
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static char[][] readArrayFromFile(String filename) {
        char[][] arr;
        String line;
        int col = 0;
        int row = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while ((line = reader.readLine()) != null) {
                row++;
                col = line.length();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        arr = new char[row][col];

        row = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while ((line = reader.readLine()) != null) {
                arr[row] = line.toCharArray();
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    private static NodeB buildTreeFromMatrix(char[][] matrix) {

        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 1; i < rows-1; i++) {
            boolean isHorizontalLine = true;
            for (int j = 0; j < cols; j+=2) {
                if (matrix[i][j] != '-') {
                    isHorizontalLine = false;
                    break;
                }
            }
            if (isHorizontalLine) {
                char[][] upperHalf = new char[i + 1][cols];
                char[][] lowerHalf = new char[rows - i - 1][cols];

                for (int k = 0; k <= i; k++) {
                    upperHalf[k] = matrix[k];
                }
                for (int k = i + 1; k < rows; k++) {
                    lowerHalf[k - i - 1] = matrix[k];
                }

                NodeB leftChild = buildTreeFromMatrix(upperHalf);
                NodeB rightChild = buildTreeFromMatrix(lowerHalf);
                return new NodeB(new Rectangles('-'), leftChild, rightChild);
            }
        }

        for (int j = 1; j < cols-1; j++) {
            boolean isVerticalLine = true;
            for (int i = 1; i < rows-1; i++) {
                if (matrix[i][j] != '|') {
                    isVerticalLine = false;
                    break;
                }
            }
            if (isVerticalLine) {
                char[][] leftHalf = new char[rows][j+1];
                char[][] rightHalf = new char[rows][cols - j - 2];

                for (int k = 0; k < rows; k++) {
                    for (int l = 0; l <= j; l++) {
                        leftHalf[k][l] = matrix[k][l];
                    }
                    for (int l = j + 2; l <cols; l++) {
                        rightHalf[k][l - j - 2] = matrix[k][l];
                    }
                }

                NodeB leftChild = buildTreeFromMatrix(leftHalf);
                NodeB rightChild = buildTreeFromMatrix(rightHalf);
                return new NodeB(new Rectangles('|'), leftChild, rightChild);
            }
        }

        return createRectangleNode(matrix);
    }

    public static NodeB importTreeFromDrawFile(String filename) {
        char[][] arr = readArrayFromFile(filename);
        System.out.println("=========== The rectangles are discovered ===========");
        return buildTreeFromMatrix(arr);
    }

    private static NodeB createRectangleNode(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        char name = ' ';

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != ' ' && matrix[i][j] != '-' && matrix[i][j] != '|') {
                    name = matrix[i][j];
                    break;
                }
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println(name + "["+ rows + "," + (cols+1)/2 +']');
        System.out.println();
        return new NodeB(new Rectangles(name, rows, (cols+1)/2));
    }

    public static boolean rectanglePacking(List<Rectangles> rectangles) {
        if (rectangles.size() == 1) {
            return true;
        }

        boolean found = false;

        for (int i = 0; i < rectangles.size() - 1; i++) {
            for (int j = i + 1; j < rectangles.size(); j++) {
                Rectangles rect1 = rectangles.get(i);
                Rectangles rect2 = rectangles.get(j);
                if (rect1.getWidth() == rect2.getWidth()) {
                    Rectangles newRect = new Rectangles(rect1.getWidth(), rect1.getLength() + rect2.getLength());
                    List<Rectangles> newRectangles = new ArrayList<>(rectangles);
                    newRectangles.add(newRect);
                    newRectangles.remove(rect1);
                    newRectangles.remove(rect2);
                    if (rectanglePacking(newRectangles)) {
                        found = true;
                        break;
                    }
                }
            }
            if (found) break;
        }

        if (!found) {
            for (int i = 0; i < rectangles.size() - 1; i++) {
                for (int j = i + 1; j < rectangles.size(); j++) {
                    Rectangles rect1 = rectangles.get(i);
                    Rectangles rect2 = rectangles.get(j);
                    if (rect1.getLength() == rect2.getLength()) {
                        Rectangles newRect = new Rectangles(rect1.getWidth() + rect2.getWidth(), rect1.getLength());
                        List<Rectangles> newRectangles = new ArrayList<>(rectangles);
                        newRectangles.add(newRect);
                        newRectangles.remove(rect1);
                        newRectangles.remove(rect2);
                        if (rectanglePacking(newRectangles)) {
                            found = true;
                            break;
                        }
                    }
                }
                if (found) break;
            }
        }

        return found;
    }

    public static int longestSublistLength(List<Rectangles> rectangles) {
        int maxLength = 0;
        List<List<Rectangles>> allSublists = generateAllSublists(rectangles);
        for (List<Rectangles> sub : allSublists) {
            if (rectanglePacking(new ArrayList<>(sub))) {
                maxLength = Math.max(maxLength, sub.size());
            }
        }
        return maxLength;
    }

    private static ArrayList<List<Rectangles>> generateAllSublists(List<Rectangles> rectangles) {
        ArrayList<List<Rectangles>> result = new ArrayList<>();
        int n = rectangles.size();
        for (int i = 0; i < (1 << n); i++) {
            List<Rectangles> sublist = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    sublist.add(rectangles.get(j));
                }
            }
            result.add(sublist);
        }
        return result;
    }

    public static NodeB createSymmetricTree(NodeB root) {
        if (root == null) {
            return null;
        }

        NodeB newRoot = new NodeB(swapRectangleDimensions(root.key));

        if (root.left != null) {
            newRoot.right = createSymmetricTree(root.left);
        }

        if (root.right != null) {
            newRoot.left = createSymmetricTree(root.right);
        }
        if(root.key.name == '-'){
            NodeB temp = newRoot.left;
            newRoot.left = newRoot.right;
            newRoot.right=temp;
        }

        return newRoot;
    }

    private static Rectangles swapRectangleDimensions(Rectangles rect) {
        if (rect.name == '|' || rect.name == '-') {
            return new Rectangles(rect.name == '|' ? '-' : '|');
        } else {
            return new Rectangles(rect.name, rect.getLength(), rect.getWidth());
        }
    }

    public static NodeG convertToGeneralTreePrivate(NodeB root) {
        if (root == null) {
            return null;
        }

        NodeG generalRoot = new NodeG(root.key.name);

        NodeB currentBinaryNode = root.right;
        while (currentBinaryNode != null) {
            NodeG child = convertToGeneralTreePrivate(currentBinaryNode);
            if (child != null) {
                generalRoot.children.add(child);
            }
            currentBinaryNode = currentBinaryNode.left;
        }

        return generalRoot;
    }
}