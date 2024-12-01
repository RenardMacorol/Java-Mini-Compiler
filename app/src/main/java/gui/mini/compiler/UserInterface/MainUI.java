package gui.mini.compiler.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainUI {
    private JFrame mainFrame;         // Main application frame
    private JPanel buttonPanel, textPanel; // Panels for buttons and text areas
    private JButton openFileButton, lexicalAnalysisButton, syntaxAnalysisButton, semanticAnalysisButton, clearButton;
    private JTextArea resultArea, codeArea; // Areas to display file content and analysis results
    private JFileChooser fileChooser;       // File chooser dialog
    private File openedFile;                // Currently selected file

    public MainUI() {
        openUI(); // Initialize and display the UI
    }

    private void openUI() {
        mainFrame = new JFrame("Final Project - Mini Compiler");
        mainFrame.setSize(1024, 450);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(false); // Prevent resizing for consistent layout
        mainFrame.setLocationRelativeTo(null); // Center the window on screen

        // Initialize and add the two main panels
        initializeButtonPanel();
        initializeTextPanel();

        // Set button states (disable some buttons initially)
        setInitialButtonStates();

        // Add panels to the frame
        mainFrame.add(buttonPanel, BorderLayout.WEST);
        mainFrame.add(textPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true); // Display the frame
    }

    /**
     * Initializes the left panel with buttons
     * All buttons are the same size and aligned vertically.
     */
    private void initializeButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column, with gaps
        buttonPanel.setPreferredSize(new Dimension(200, 768)); // Fixed width for the panel
        buttonPanel.setBackground(new Color(102, 153, 102)); // Soft green background

        // Create buttons with consistent size
        openFileButton = createButton("Open File");
        lexicalAnalysisButton = createButton("Lexical Analysis");
        syntaxAnalysisButton = createButton("Syntax Analysis");
        semanticAnalysisButton = createButton("Semantic Analysis");
        clearButton = createButton("Clear");

        // Set button actions
        openFileButton.addActionListener(this::handleOpenFile);
        lexicalAnalysisButton.addActionListener(this::handleLexicalAnalysis);
        syntaxAnalysisButton.addActionListener(this::handleSyntaxAnalysis);
        semanticAnalysisButton.addActionListener(this::handleSemanticAnalysis);
        clearButton.addActionListener(this::handleClear);

        // Add buttons to the panel
        buttonPanel.add(openFileButton);
        buttonPanel.add(lexicalAnalysisButton);
        buttonPanel.add(syntaxAnalysisButton);
        buttonPanel.add(semanticAnalysisButton);
        buttonPanel.add(clearButton);
    }

    /**
     * Initializes the main text panel
     * This contains two text areas: one for the code and one for the analysis results.
     */
    private void initializeTextPanel() {
        textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.setBackground(new Color(230, 240, 230)); // Light greenish-gray background

        // Code Text Area
        codeArea = new JTextArea();
        codeArea.setBorder(BorderFactory.createTitledBorder("Code"));
        codeArea.setEditable(false);
        codeArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        codeArea.setBackground(new Color(245, 255, 245)); // Very light green background
        JScrollPane codeScrollPane = new JScrollPane(codeArea);

        // Result Text Area
        resultArea = new JTextArea();
        resultArea.setBorder(BorderFactory.createTitledBorder("Result"));
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setBackground(new Color(245, 255, 245)); // Very light green background
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        // Split panel to divide the two text areas
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, codeScrollPane, resultScrollPane);
        splitPane.setDividerLocation(100); // Set the initial divider position

        textPanel.add(splitPane, BorderLayout.CENTER); // Add split pane to the text panel
    }

    /**
     * Creates a button with consistent styling and size
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(192, 255, 192)); // Light green background
        button.setForeground(Color.BLACK);

        // Add hover effect for visual feedback
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(153, 255, 153)); // Darker green
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(192, 255, 192)); // Original color
            }
        });
        return button;
    }

    /**
     * Disables buttons that should not be used initially
     */
    private void setInitialButtonStates() {
        lexicalAnalysisButton.setEnabled(false);
        syntaxAnalysisButton.setEnabled(false);
        semanticAnalysisButton.setEnabled(false);
    }

    /**
     * Opens a file and displays its contents in the Code Text Area
     */
    private void handleOpenFile(ActionEvent e) {
        fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(mainFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            openedFile = fileChooser.getSelectedFile();
            displayFileContents();
            lexicalAnalysisButton.setEnabled(true); // Enable Lexical Analysis button
        }
    }

    /**
     * Displays the contents of the selected file in the Code Text Area
     */
    private void displayFileContents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(openedFile))) {
            codeArea.setText(""); // Clear previous contents
            String line;
            while ((line = reader.readLine()) != null) {
                codeArea.append(line + "\n"); // Append each line
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles Lexical Analysis
     */
    private void handleLexicalAnalysis(ActionEvent e) {
        resultArea.append("Performing Lexical Analysis...\n");
        lexicalAnalysisButton.setEnabled(false); // Disable current button
        syntaxAnalysisButton.setEnabled(true);  // Enable next button
    }

    /**
     * Handles Syntax Analysis
     */
    private void handleSyntaxAnalysis(ActionEvent e) {
        resultArea.append("Performing Syntax Analysis...\n");
        syntaxAnalysisButton.setEnabled(false); // Disable current button
        semanticAnalysisButton.setEnabled(true); // Enable next button
    }

    /**
     * Handles Semantic Analysis
     */
    private void handleSemanticAnalysis(ActionEvent e) {
        resultArea.append("Performing Semantic Analysis...\n");
        semanticAnalysisButton.setEnabled(false); // Disable current button
    }

    /**
     * Clears all areas and resets button states
     */
    private void handleClear(ActionEvent e) {
        codeArea.setText(""); // Clear code area
        resultArea.setText(""); // Clear result area
        setInitialButtonStates(); // Reset button states
    }
}
