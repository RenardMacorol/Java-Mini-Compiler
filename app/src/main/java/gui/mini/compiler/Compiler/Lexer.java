package gui.mini.compiler.Compiler;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

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
        tokens.add(new Token(TokenType.EOF, "", line));

    }

    private char advance() {
        return input.charAt(current++);
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case ';' -> addToken(TokenType.SEMICOLON);
            case '.' -> addToken(TokenType.DOT);
            case '=' -> addToken(TokenType.ASSIGN_OP);
            case '+' -> addToken(TokenType.ADD);
            case '-' -> addToken(TokenType.SUB);
            case '*' -> addToken(TokenType.MUL);
            case '/' -> addToken(TokenType.DIV);
            case ',' -> addToken(TokenType.COMMA);
            case '"' -> string();
            case '\'' -> charCheck();
            case ' ', '\r', '\t' -> {
            } // Ignore whitespace
            case '\n' -> line++; // Track line numbers
            default -> {
                if (isDigit(c)) {
                    number();
                } else if (isLetter(c)) {
                    identifierOrKeyword();
                } else {
                    addToken(TokenType.UNKOWN, c + "");
                    System.err.println("Unexpected character '" + c + "' at line " + line);
                }
            }
        }
    }

    private boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void identifierOrKeyword() {
        while ((isLetter(peek()) || isDigit(peek())) && !isAtEnd()) {
            advance();
        }
        String lexeme = input.substring(start, current);

        TokenType type = switch (lexeme) {
            case "byte" -> TokenType.BYTE;
            case "short" -> TokenType.SHORT;
            case "int" -> TokenType.INT;
            case "String" -> TokenType.STRING_TYPE;
            case "char" -> TokenType.CHAR_TYPE;
            case "double" -> TokenType.DOUBLE;
            case "float" -> TokenType.FLOAT;
            case "boolean" -> TokenType.BOOLEAN;
            case "true", "false" -> TokenType.BOOLEAN_LITERAL;
            default -> TokenType.INDENTIFIER;
        };
        addToken(type, lexeme);
    }

    private void number() {
        while (isDigit(peek()))
            advance();

        if (peek() == '.' && isDigit(peekNext())) {
            advance(); // Consume '.'
            while (isDigit(peek()))
                advance();
        }

        if (peek() == 'f' || peek() == 'F') {
            advance(); // Handle floats
            addToken(TokenType.NUMBER, input.substring(start, current) + " (float)");
        } else {
            addToken(TokenType.NUMBER, input.substring(start, current));
        }
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n')
                line++;
            advance();
        }
        if (isAtEnd()) {
            System.err.println("Unterminated string at line " + line);
            return;
        }
        advance(); // Consume closing "
        addToken(TokenType.STRING, input.substring(start + 1, current - 1));
    }

    private void charCheck() {
        if (isAtEnd() || input.charAt(current) == '\n') {
            System.err.println("Unterminated char at line " + line);
            return;
        }
        char value = input.charAt(current);
        advance(); // Consume the character inside the quotes

        if (isAtEnd() || input.charAt(current) != '\'') {
            System.err.println("Unterminated char at line " + line);
            return;
        }
        advance(); // Consume closing single quote
        addToken(TokenType.CHAR, "'" + value + "'");
    }

    private char peek() {
        return isAtEnd() ? '\0' : input.charAt(current);
    }

    private char peekNext() {
        return (current + 1 >= input.length()) ? '\0' : input.charAt(current + 1);
    }

    private void addToken(TokenType type) {
        addToken(type, input.substring(start, current));
    }

    private void addToken(TokenType type, String lexeme) {
        tokens.add(new Token(type, lexeme, line));
    }
}
