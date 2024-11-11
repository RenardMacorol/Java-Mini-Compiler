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

        tokens.add(new Token(TokenType.EOF, "AOT"));

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
            case ' ':
            case '\r':
            case '\n':
            case '\t':
            default:
                if (isDigit(c)) {
                    number();

                } else if (isLetter(c)) {
                    letter();
                } else {
                    System.out.println("Unexpected Character" + c);
                }
        }

    }

    private boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isDelimiter(char c) {
        return c >= ';';
    }

    private void letter() {
        while (isLetter(input.charAt(current)) && !isAtEnd()) {
            advance();
        }
        String base = input.substring(start, current);
        // INT, STRING_TYPE, CHAR, DOUBLE, BOOLEAN,
        switch (base) {
            case "int":

                tokens.add(new Token(TokenType.INT, base));
                break;

            case "String":

                tokens.add(new Token(TokenType.STRING_TYPE, base));
                break;
            case "char":

                tokens.add(new Token(TokenType.CHAR, base));
                break;
            case "double":

                tokens.add(new Token(TokenType.DOUBLE, base));
                break;
            case "float":

                tokens.add(new Token(TokenType.FLOAT, base));
                break;

            case "boolean":

                tokens.add(new Token(TokenType.BOOLEAN, base));
                break;
            default:
                tokens.add(new Token(TokenType.INDENTIFIER, base));
                break;

        }

    }

    private void number() {
        while (isDigit(peek()))
            advance();
        if (peek() == '.' || isDelimiter(peek()) || peek() == 'f') {
            advance();

            while (isDigit(peek()))
                advance();

        }

        tokens.add(new Token(TokenType.NUMBER, input.substring(start, current)));
    }

    private char peek() {
        if (current + 1 >= input.length())
            return '\0';
        return input.charAt(current + 1);

    }

    private char peekCurrent() {
        if (isAtEnd())
            return '\0';
        return input.charAt(current);
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
