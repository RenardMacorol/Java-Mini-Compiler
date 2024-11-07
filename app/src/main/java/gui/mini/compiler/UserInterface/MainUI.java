package gui.mini.compiler.UserInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainUI {
    private JFrame mainFrame;
    private JPanel textPanel;
    private JPanel buttonPanel;
    private JButton openFileButton;
    private JButton lexicalAnalysisButton;
    private JButton syntaxAnalysisButton;
    private JButton semanticAnalysisButton;
    private JButton clearButton;
    private JTextArea resultArea;
    private JTextArea codeArea;
    private int buttonWidth = 30;
    private int buttonHeight = 30;

    public MainUI() {
        openUI();
    }

    private void openUI() {
        mainFrame = new JFrame();
        mainFrame.setSize(1024, 768);
        mainFrame.setTitle("Java Mini-Compiler 1");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.RED);
        buttonPanel.setBounds(0, 0, 250, 1024);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttons();

        textPanel = new JPanel();
        textPanel.setBackground(Color.BLUE);
        textPanel.setBounds(500, 0, 50, 1024);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        // textAreaPanel();

        mainFrame.add(buttonPanel);
        // mainFrame.add(textPanel);
    }

    private void textAreaPanel() {
        resultArea = new JTextArea(20, 20);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        // codeArea = new JTextArea();
        // codeArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(resultArea);
        // textPanel.add(codeArea);
    }

    private void buttons() {
        openFileButton = new JButton();
        openFileButton.setText("Open File");
        openFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        openFileButton.setSize(buttonWidth, buttonHeight);

        lexicalAnalysisButton = new JButton();
        lexicalAnalysisButton.setText("Lexical Analysis");
        lexicalAnalysisButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        lexicalAnalysisButton.setSize(buttonWidth, buttonHeight);

        syntaxAnalysisButton = new JButton();
        syntaxAnalysisButton.setText("Syntax Analysis");
        syntaxAnalysisButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        syntaxAnalysisButton.setSize(buttonWidth, buttonHeight);

        semanticAnalysisButton = new JButton();
        semanticAnalysisButton.setText("Semantics Analysis");
        semanticAnalysisButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        semanticAnalysisButton.setSize(buttonWidth, buttonHeight);

        clearButton = new JButton();
        clearButton.setText("Clear");
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearButton.setSize(buttonWidth, buttonHeight);

        buttonPanel.add(Box.createRigidArea(new Dimension(15, 30)));
        buttonPanel.add(openFileButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        buttonPanel.add(lexicalAnalysisButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        buttonPanel.add(syntaxAnalysisButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        buttonPanel.add(semanticAnalysisButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        buttonPanel.add(clearButton);
    }

}
