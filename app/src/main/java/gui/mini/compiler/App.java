package gui.mini.compiler;

import gui.mini.compiler.Compiler.*;
import gui.mini.compiler.UserInterface.MainUI;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // Launch the graphical user interface
        new MainUI();

        // Console-based Lexer and Parser logic
        String input = "int x = 0; \n" +
                "char gender = 'M';" +
                "String name = \"Renard\";" +
                "double y = 1.03;\n" +
                "float xy = 1.03f;\n" +
                "boolean istrue = true;\n";
        System.out.println("Input code:");
        System.out.println(input);

        // Perform Lexical Analysis
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.getTokens();

        System.out.println("\nTokens generated:");
        for (Token token : tokens) {
            System.out.println(token.toString());
        }

        // Perform Syntax Analysis
        Parser parser = new Parser(tokens);
        parser.initParser();
    }
}
