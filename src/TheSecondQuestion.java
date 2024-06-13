import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class TheSecondQuestion extends JFrame {
    private boolean isGeneralTreeDisplayed;
    private GeneralTreePanel generalTreePanel;
    private GeneralTree generalTree;
    private BinaryTreePanel binaryTreePanel;
    private BinaryTree binaryTree;
    private JTextArea textArea;
    private JLabel treeLabel;

    public TheSecondQuestion() {
        super("Represent trees graphically");
        setSize(1200, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        isGeneralTreeDisplayed = true;

        generalTree = GeneralTree.buildTreeFromFile("generalTreeFormat.txt");
        generalTreePanel = new GeneralTreePanel(generalTree.root);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(generalTreePanel);
        scrollPane.setBounds(425, 125, 740, 400);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setUnitIncrement(8);
        scrollPane.getVerticalScrollBar().setValue(600);
        scrollPane.getHorizontalScrollBar().setValue(225);

        add(scrollPane);

        JLabel enterLabel = new JLabel("Enter Your General Tree:");
        enterLabel.setFont(new Font("Calibri Light", Font.PLAIN, 22));
        enterLabel.setForeground(Color.DARK_GRAY);
        enterLabel.setBounds(75, 85, 500, 50);
        add(enterLabel);

        textArea = new JTextArea(" Enter general Tree, line by line: Father -> children, children...");
        textArea.setFont(new Font("Calibri Light", Font.PLAIN, 18));
        textArea.setForeground(Color.lightGray);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setBounds(75, 140, 250, 200);
        textArea.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (textArea.getText().equals(" Enter general Tree, line by line: Father -> children, children...")) {
                    textArea.setText("");
                    textArea.setForeground(Color.darkGray);
                }
            }

            public void focusLost(FocusEvent evt) {
                if (textArea.getText().isEmpty()) {
                    textArea.setForeground(Color.lightGray);
                    textArea.setText(" Enter general Tree, line by line: Father -> children, children...");
                }
            }
        });
        add(textAreaScrollPane);

        JButton updateButton = new JButton("Update Tree"){
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
        updateButton.setBorderPainted(false);
        updateButton.setFocusPainted(false);
        updateButton.setContentAreaFilled(false);
        updateButton.setBorder(null);
        updateButton.setBounds(75, 400, 250, 40);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textArea.getText().equals(" Enter general Tree, line by line: Father -> children, children...")){
                    String text = textArea.getText();
                    Scanner scanner = new Scanner(text);
                    GeneralTree temp = new GeneralTree();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        GeneralTree.buildTreeFromString(temp, line);
                    }
                    generalTree = temp;
                    generalTreePanel.updateTree(generalTree.root);
                    generalTreePanel.repaint();
                    scanner.close();
                    isGeneralTreeDisplayed = true;
                    scrollPane.setViewportView(generalTreePanel);
                    treeLabel.setText("<html><font color='#153448'>|</font> " + " General Tree:");
                }
            }
        });
        add(updateButton);

        JButton transformationButton = new JButton("transformation"){
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
        transformationButton.setBorderPainted(false);
        transformationButton.setFocusPainted(false);
        transformationButton.setContentAreaFilled(false);
        transformationButton.setBorder(null);
        transformationButton.setBounds(75, 460, 250, 40);
        transformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGeneralTreeDisplayed) {
                    isGeneralTreeDisplayed = false;

                    binaryTree = new BinaryTree(GeneralTree.convertToBinaryTree(generalTree.root));
                    binaryTreePanel = new BinaryTreePanel(binaryTree.root);
                    scrollPane.setViewportView(binaryTreePanel);
                    treeLabel.setText("<html><font color='#153448'>|</font> " + " Binary Tree:");

                    int verticalOffset = (scrollPane.getViewport().getViewSize().height - scrollPane.getViewport().getHeight()) / 2;
                    int horizontalOffset = (scrollPane.getViewport().getViewSize().width - scrollPane.getViewport().getWidth()) / 2;
                    scrollPane.getVerticalScrollBar().setValue(verticalOffset);
                    scrollPane.getHorizontalScrollBar().setValue(horizontalOffset);
                } else {
                    isGeneralTreeDisplayed = true;
                    generalTree = new GeneralTree(BinaryTree.convertToGeneralTreePrivate(binaryTree.root));
                    generalTreePanel = new GeneralTreePanel(generalTree.root);
                    scrollPane.setViewportView(generalTreePanel);
                    treeLabel.setText("<html><font color='#153448'>|</font> " + " General Tree:");

                    int verticalOffset = (scrollPane.getViewport().getViewSize().height - scrollPane.getViewport().getHeight()) / 2;
                    int horizontalOffset = (scrollPane.getViewport().getViewSize().width - scrollPane.getViewport().getWidth()) / 2;
                    scrollPane.getVerticalScrollBar().setValue(verticalOffset);
                    scrollPane.getHorizontalScrollBar().setValue(horizontalOffset);
                }
            }

        });
        add(transformationButton);

        JButton exportButton = new JButton("Export"){
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
        exportButton.setBorderPainted(false);
        exportButton.setFocusPainted(false);
        exportButton.setContentAreaFilled(false);
        exportButton.setBorder(null);
        exportButton.setBounds(75, 520, 120, 40);
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralTree.exportTheTreeToAFileAsAFormat(generalTree.root);
            }
        });
        add(exportButton);

        JButton importButton = new JButton("Import"){
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
        importButton.setBorderPainted(false);
        importButton.setFocusPainted(false);
        importButton.setContentAreaFilled(false);
        importButton.setBorder(null);
        importButton.setBounds(205, 520, 120, 40);
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGeneralTreeDisplayed = true;
                generalTree = GeneralTree.buildTreeFromFile("generalTreeFormat.txt");
                generalTreePanel.updateTree(generalTree.root);
                scrollPane.setViewportView(generalTreePanel);
                treeLabel.setText("<html><font color='#153448'>|</font> " + " General Tree:");
            }
        });
        add(importButton);

        treeLabel = new JLabel("<html><font color='#153448'>|</font> " + " General Tree:");
        treeLabel.setFont(new Font("Arial", Font.BOLD, 35));
        treeLabel.setForeground(Color.decode("#27374D"));
        treeLabel.setBounds(425, 40, 500, 50);
        add(treeLabel);

        JPanel background1 = new JPanel();
        background1.setBackground(Color.decode("#DDE6ED"));
        background1.setBounds(50,75,300, 300);
        add(background1);

        JPanel background2 = new JPanel();
        background2.setBackground(Color.decode("#526D82"));
        background2.setBounds(0,0,400, 650);
        add(background2);

        JPanel background3 = new JPanel();
        background3.setBackground(Color.decode("#EEEEEE"));
        background3.setBounds(0,0,1200, 650);
        add(background3);

        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                SwingUtilities.invokeLater(() -> getRootPane().requestFocusInWindow());
            }
        });
    }
}
