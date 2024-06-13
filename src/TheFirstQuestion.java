import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class TheFirstQuestion extends JFrame {
    TheFirstQuestion(){
        super("Represent trees graphically");
        setSize(1200, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        BinaryTree binaryTree = new BinaryTree(BinaryTree.importTheTreeFromAFileByAFormat("BinaryTreeFormat.txt"));
        BinaryTreePanel binaryTreePanel = new BinaryTreePanel(binaryTree.root);

        JLabel treeLabel = new JLabel("<html><font color='#153448'>|</font> " + " Tree Representation:");
        treeLabel.setFont(new Font("Arial", Font.BOLD, 35));
        treeLabel.setForeground(Color.decode("#27374D"));
        treeLabel.setBounds(500, 50, 500, 50);
        add(treeLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(binaryTreePanel);
        scrollPane.setBounds(500, 125, 640, 350);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setUnitIncrement(8);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.getVerticalScrollBar().setValue(72);
        scrollPane.getHorizontalScrollBar().setValue(1177);
        add(scrollPane);

        JTextField treeTextField = new JTextField("Enter the text Representation:");
        treeTextField.setForeground(Color.lightGray);
        treeTextField.setBounds(575, 490, 450, 40);
        treeTextField.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        treeTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (treeTextField.getText().equals("Enter the text Representation:")) {
                    treeTextField.setText("");
                    treeTextField.setForeground(Color.darkGray);
                }
            }

            public void focusLost(FocusEvent evt) {
                if (treeTextField.getText().isEmpty()) {
                    treeTextField.setForeground(Color.lightGray);
                    treeTextField.setText("Enter the text Representation:");
                }
            }
        });
        add(treeTextField);

        ImageIcon refresh = new ImageIcon("icons\\icons8-refresh-30.png");
        JButton updateButton = new JButton(refresh);
        updateButton.setBackground(Color.decode("#9DB2BF"));
        updateButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.white));
        updateButton.setBounds(1025, 490, 50, 40);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String treeString = treeTextField.getText().trim();
                if (!treeString.isEmpty() && !treeString.equals("Enter the text Representation:")) {
                    binaryTree.root = BinaryTree.readNodeFromExpression(treeString);
                    binaryTreePanel.updateTree(treeString);
                }
            }
        });
        add(updateButton);

        ImageIcon transformation = new ImageIcon("icons\\icons8-rotate-page-clockwise-30.png");
        JButton transformationButton = new JButton(transformation);
        transformationButton.setBackground(Color.decode("#DDE6ED"));
        transformationButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.white));
        transformationButton.setBounds(800, 545, 40, 40);
        transformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                binaryTree.root = BinaryTree.createSymmetricTree(binaryTree.root);
                char arr[][] = BinaryTree.dfs(binaryTree.root);
                System.out.println();
                System.out.println("=========== rotation ===========");
                System.out.println();
                for (int i =0 ; i <arr.length; i++){
                    for (int j =0 ; j <arr[0].length; j++){
                        System.out.print(arr[i][j]);
                    }
                    System.out.println();
                }
                binaryTreePanel.updateTree(binaryTree.root);
            }
        });
        add(transformationButton);

        JButton textExportButton = new JButton("Export Text"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int arcDiameter = 10;
                RoundRectangle2D roundRect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcDiameter, arcDiameter);

                if (getModel().isArmed()) {
                    g2.setColor(Color.decode("#9DB2BF"));
                } else if (getModel().isRollover()) {
                    g2.setColor(Color.decode("#DDE6ED"));
                } else {
                    g2.setColor(Color.decode("#9DB2BF"));
                }

                g2.fill(roundRect);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Agency FB", Font.BOLD, 18));

                FontMetrics fm = g.getFontMetrics();
                String text = getText();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();

                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() - textHeight) / 2 + fm.getAscent();

                g2.drawString(text, x , y + 2);

                g2.dispose();
            }
        };
        textExportButton.setBorderPainted(false);
        textExportButton.setFocusPainted(false);
        textExportButton.setContentAreaFilled(false);
        textExportButton.setBorder(null);
        textExportButton.setBounds(875, 550, 100, 30);
        textExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BinaryTree.exportTheTreeToAFileAsAFormat(binaryTree.root);
            }
        });
        add(textExportButton);

        JButton drawExportButton = new JButton("Export Draw"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int arcDiameter = 10;
                RoundRectangle2D roundRect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcDiameter, arcDiameter);

                if (getModel().isArmed()) {
                    g2.setColor(Color.decode("#9DB2BF"));
                } else if (getModel().isRollover()) {
                    g2.setColor(Color.decode("#DDE6ED"));
                } else {
                    g2.setColor(Color.decode("#9DB2BF"));
                }

                g2.fill(roundRect);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Agency FB", Font.BOLD, 18));

                FontMetrics fm = g.getFontMetrics();
                String text = getText();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();

                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() - textHeight) / 2 + fm.getAscent();

                g2.drawString(text, x , y + 2);

                g2.dispose();
            }
        };
        drawExportButton.setBorderPainted(false);
        drawExportButton.setFocusPainted(false);
        drawExportButton.setContentAreaFilled(false);
        drawExportButton.setBorder(null);
        drawExportButton.setBounds(1000, 550, 100, 30);
        drawExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BinaryTree.exportTheTreeToAFileAsADraw(binaryTree.root);
            }
        });
        add(drawExportButton);

        JButton textImportButton = new JButton("Import Text"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int arcDiameter = 10;
                RoundRectangle2D roundRect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcDiameter, arcDiameter);

                if (getModel().isArmed()) {
                    g2.setColor(Color.decode("#9DB2BF"));
                } else if (getModel().isRollover()) {
                    g2.setColor(Color.decode("#DDE6ED"));
                } else {
                    g2.setColor(Color.decode("#9DB2BF"));
                }

                g2.fill(roundRect);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Agency FB", Font.BOLD, 18));

                FontMetrics fm = g.getFontMetrics();
                String text = getText();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();

                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() - textHeight) / 2 + fm.getAscent();

                g2.drawString(text, x, y + 2);

                g2.dispose();
            }
        };
        textImportButton.setBorderPainted(false);
        textImportButton.setFocusPainted(false);
        textImportButton.setContentAreaFilled(false);
        textImportButton.setBorder(null);
        textImportButton.setBounds(665, 550, 100, 30);
        textImportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                binaryTree.root = BinaryTree.importTheTreeFromAFileByAFormat("BinaryTreeFormat.txt");
                binaryTreePanel.updateTree(binaryTree.root);
            }
        });
        add(textImportButton);

        JButton drawImportButton = new JButton("Import Draw"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int arcDiameter = 10;
                RoundRectangle2D roundRect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcDiameter, arcDiameter);

                if (getModel().isArmed()) {
                    g2.setColor(Color.decode("#9DB2BF"));
                } else if (getModel().isRollover()) {
                    g2.setColor(Color.decode("#DDE6ED"));
                } else {
                    g2.setColor(Color.decode("#9DB2BF"));
                }

                g2.fill(roundRect);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Agency FB", Font.BOLD, 18));

                FontMetrics fm = g.getFontMetrics();
                String text = getText();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();

                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() - textHeight) / 2 + fm.getAscent();

                g2.drawString(text, x, y + 2);

                g2.dispose();
            }
        };
        drawImportButton.setBorderPainted(false);
        drawImportButton.setFocusPainted(false);
        drawImportButton.setContentAreaFilled(false);
        drawImportButton.setBorder(null);
        drawImportButton.setBounds(540, 550, 100, 30);
        drawImportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                binaryTree.root = BinaryTree.importTreeFromDrawFile("Draw.txt");
                binaryTreePanel.updateTree(binaryTree.root);
            }
        });
        add(drawImportButton);

        JTextArea rectanglesArea = new JTextArea("Enter a group of rectangles with their dimensions to check if they can be glued together to form a complete large rectangle.");
        rectanglesArea.setFont(new Font("Calibri Light", Font.PLAIN, 22));
        rectanglesArea.setForeground(Color.DARK_GRAY);
        rectanglesArea.setBackground(Color.decode("#DDE6ED"));
        rectanglesArea.setEditable(false);
        rectanglesArea.setLineWrap(true);
        rectanglesArea.setWrapStyleWord(true);
        rectanglesArea.setBounds(75, 100, 300, 125);
        add(rectanglesArea);

        JTextArea textArea = new JTextArea(" Enter the rectangles in this pattern, line by line: A[width,length]");
        textArea.setFont(new Font("Calibri Light", Font.PLAIN, 18));
        textArea.setForeground(Color.lightGray);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setBounds(75, 240, 300, 150);
        textArea.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (textArea.getText().equals(" Enter the rectangles in this pattern, line by line: A[width,length]")) {
                    textArea.setText("");
                    textArea.setForeground(Color.darkGray);
                }
            }

            public void focusLost(FocusEvent evt) {
                if (textArea.getText().isEmpty()) {
                    textArea.setForeground(Color.lightGray);
                    textArea.setText(" Enter the rectangles in this pattern, line by line: A[width,length]");
                }
            }
        });
        add(textAreaScrollPane);

        JLabel resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Calibri Light", Font.PLAIN, 18));
        resultLabel.setBounds(75, 410, 500, 50);
        add(resultLabel);

        JButton insertRectanglesButton = new JButton("Check"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int arcDiameter = 10;
                RoundRectangle2D roundRect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcDiameter, arcDiameter);

                if (getModel().isArmed()) {
                    g2.setColor(Color.decode("#9DB2BF"));
                } else if (getModel().isRollover()) {
                    g2.setColor(Color.decode("#DDE6ED"));
                } else {
                    g2.setColor(Color.decode("#9DB2BF"));
                }

                g2.fill(roundRect);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Agency FB", Font.BOLD, 22));

                FontMetrics fm = g.getFontMetrics();
                String text = getText();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();

                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() - textHeight) / 2 + fm.getAscent();

                g2.drawString(text, x - 6, y + 2);

                g2.dispose();
            }
        };
        insertRectanglesButton.setBorderPainted(false);
        insertRectanglesButton.setFocusPainted(false);
        insertRectanglesButton.setContentAreaFilled(false);
        insertRectanglesButton.setBorder(null);
        insertRectanglesButton.setBounds(100, 530, 250, 40);
        insertRectanglesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] lines = textArea.getText().split("\n");
                ArrayList<Rectangles> rectanglesList = new ArrayList<>();
                boolean can = true;
                for (String line : lines) {
                    if(isValidExpression(line)){
                        Rectangles rectangle = Rectangles.parseRectangle(line);
                        if (rectangle != null) {
                            rectanglesList.add(rectangle);
                        }
                    }else{
                        can = false;
                    }
                }
                if (can){
                    if (BinaryTree.rectanglePacking(rectanglesList)) {
                        resultLabel.setText("You can successfully form a rectangle!");
                        resultLabel.setForeground(Color.decode("#0A6847"));
                    }else{
                        resultLabel.setText("You can do this with just "+ BinaryTree.longestSublistLength(rectanglesList) +" rectangles");
                        resultLabel.setForeground(Color.decode("#C40C0C"));
                    }
                }else {
                    resultLabel.setText("Input is not possible!");
                    resultLabel.setForeground(Color.decode("#C40C0C"));
                }
            }
        });
        add(insertRectanglesButton);

        JPanel rectanglesPanel = new JPanel();
        rectanglesPanel.setBackground(Color.decode("#DDE6ED"));
        rectanglesPanel.setBounds(50,75,350, 430);
        add(rectanglesPanel);

        JPanel background1 = new JPanel();
        background1.setBackground(Color.decode("#526D82"));
        background1.setBounds(0,0,450, 650);
        add(background1);

        JPanel background2 = new JPanel();
        background2.setBackground(Color.decode("#EEEEEE"));
        background2.setBounds(0,0,1200, 650);
        add(background2);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                SwingUtilities.invokeLater(() -> getRootPane().requestFocusInWindow());
            }
        });
    }

    private static boolean isValidExpression(String line) {
        line = line.trim();
        if (line.isEmpty() || line.length() < 5) {
            return false;
        }

        int openingBracket = line.indexOf('[');
        int closingBracket = line.indexOf(']');
        if (openingBracket == -1 || closingBracket == -1 || closingBracket <= openingBracket) {
            return false;
        }

        int commaIndex = line.indexOf(',', openingBracket);
        if (commaIndex == -1 || commaIndex >= closingBracket) {
            return false;
        }

        char name = line.charAt(0);
        if (!Character.isLetter(name)) {
            return false;
        }

        try {
            int width = Integer.parseInt(line.substring(openingBracket + 1, commaIndex).trim());
            int length = Integer.parseInt(line.substring(commaIndex + 1, closingBracket).trim());
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}


