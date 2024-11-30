package gui.mini.compiler.Compiler;

import java.util.*;

public class Parser {
    List<Token> tokens;
    int position;
    TokenType currentToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    public void consume() {
        position++;
    }

    public void initParser() {
        forType();

    }

    void forType() {
        if (tokens.get(position).getTokenType() == TokenType.EOF) {
            System.out.println("End");
        }
        currentToken = tokens.get(position).getTokenType();
        if (currentToken == TokenType.CHAR_TYPE || currentToken == TokenType.STRING_TYPE
                || currentToken == TokenType.FLOAT || currentToken == TokenType.DOUBLE
                || currentToken == TokenType.BOOLEAN || currentToken == TokenType.INT) {
            System.out.println("Passed" + currentToken);
            consume();
            forType();
        }
        forIdent();

    }

    void forIdent() {
        if (currentToken == TokenType.INDENTIFIER) {

            System.out.println("Passed" + currentToken);
            consume();
            forType();
        }
        forAssignOP();

    }

    void forAssignOP() {
        if (currentToken == TokenType.ASSIGN_OP) {

            System.out.println("Passed" + currentToken);
            consume();
            forType();
        }

        forVal();
    }

    void forVal() {
        if (currentToken == TokenType.NUMBER || currentToken == TokenType.STRING || currentToken == TokenType.CHAR) {
            System.out.println("Passed" + currentToken);
            consume();
            forType();
        }
        forDeli();

    }

    void forDeli() {

        if (currentToken == TokenType.SEMICOLON || currentToken == TokenType.INDENTIFIER) {
            System.out.println("Passed" + currentToken);
            consume();
            forType();
        }

    }
}
