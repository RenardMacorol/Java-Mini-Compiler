package gui.mini.compiler.Compiler;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;

    public Lexer(String input) {
        this.input = input;
        scanTokens();
    }

    public List<Token> getTokens() {
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= input.length();
    }

    private void scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "EOF"));
    }

    private char advance() {
        return input.charAt(current++);
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case ';':
                tokens.add(new Token(TokenType.SEMICOLON, ";"));
                break;

            case '.':
                tokens.add(new Token(TokenType.DOT, "."));
                break;

            case '=':
                tokens.add(new Token(TokenType.ASSIGN_OP, "="));
                break;

            case '+':
                tokens.add(new Token(TokenType.ADD, "+"));
                break;

            case '-':
                tokens.add(new Token(TokenType.SUB, "-"));
                break;

            case '*':
                tokens.add(new Token(TokenType.MUL, "*"));
                break;

            case '/':
                tokens.add(new Token(TokenType.DIV, "/"));
                break;

            case ',':
                tokens.add(new Token(TokenType.COMMA, ","));
                break;

            case '"':
                string();
                break;

            case '\'':
                charCheck();
                break;

            case ' ':
            case '\r':
            case '\n':
            case '\t':
                // Ignore whitespace
                break;

            default:
                if (isDigit(c)) {
                    number();
                } else if (isLetter(c)) {
                    letterOrKeyword();
                } else {
                    System.out.println("Unexpected Character: " + c);
                }
        }
    }

    private void charCheck() {
        while (peekCurrent() != '\'' && !isAtEnd()) {
            advance();
        }
        if (isAtEnd()) {
            System.out.println("Unterminated Char");
        }
        advance();
        String value = input.substring(start + 1, current - 1);
        tokens.add(new Token(TokenType.CHAR, value));
    }

    private boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void letterOrKeyword() {
        while ((isLetter(peekCurrent()) || isDigit(peekCurrent())) && !isAtEnd()) {
            advance();
        }
        String lexeme = input.substring(start, current);

        // Handle keywords, including boolean literals
        switch (lexeme) {
            case "int":
                tokens.add(new Token(TokenType.INT, lexeme));
                break;
            case "String":
                tokens.add(new Token(TokenType.STRING_TYPE, lexeme));
                break;
            case "char":
                tokens.add(new Token(TokenType.CHAR_TYPE, lexeme));
                break;
            case "double":
                tokens.add(new Token(TokenType.DOUBLE, lexeme));
                break;
            case "float":
                tokens.add(new Token(TokenType.FLOAT, lexeme));
                break;
            case "boolean":
                tokens.add(new Token(TokenType.BOOLEAN, lexeme));
                break;
            case "true":
            case "false":
                tokens.add(new Token(TokenType.BOOLEAN_LITERAL, lexeme));
                break;
            default:
                tokens.add(new Token(TokenType.INDENTIFIER, lexeme));
                break;
        }
    }

    private void number() {
        while (isDigit(peekCurrent())) {
            advance();
        }

        // Handle decimal numbers
        if (peekCurrent() == '.' && isDigit(peekNext())) {
            advance(); // Consume '.'

            while (isDigit(peekCurrent())) {
                advance();
            }
        }

        // Handle float suffix 'f' or 'F'
        if (peekCurrent() == 'f' || peekCurrent() == 'F') {
            advance();
            tokens.add(new Token(TokenType.NUMBER, input.substring(start, current) + " (float)"));
        } else {
            tokens.add(new Token(TokenType.NUMBER, input.substring(start, current)));
        }
    }

    private char peekCurrent() {
        if (isAtEnd()) return '\0';
        return input.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= input.length()) return '\0';
        return input.charAt(current + 1);
    }

    private void string() {
        while (peekCurrent() != '"' && !isAtEnd()) {
            advance();
        }
        if (isAtEnd()) {
            System.out.println("Unterminated String");
        }
        advance();
        String value = input.substring(start + 1, current - 1);
        tokens.add(new Token(TokenType.STRING, value));
    }
}
