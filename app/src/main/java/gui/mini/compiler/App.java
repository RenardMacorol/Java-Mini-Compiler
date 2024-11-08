/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package gui.mini.compiler;

import java.util.List;

import gui.mini.compiler.Compiler.Lexer;
import gui.mini.compiler.Compiler.Token;

public class App {
    public static void main(String[] args) {
        String exampleInput = "int number = 1; float flnumber = 1.80;  char ch = '2' ; String ch = 'Name';";
        System.out.println(exampleInput);
        Lexer lexer = new Lexer(exampleInput);
        List<Token> tokens = lexer.getTokens();

        for (Token token : tokens) {
            System.out.println(token.toString());
        }
        // MainUI mainUI = new MainUI();
    }
}
