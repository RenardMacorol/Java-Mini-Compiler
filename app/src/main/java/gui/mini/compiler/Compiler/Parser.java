package gui.mini.compiler.Compiler;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int position = 0;
    private final StringBuilder results = new StringBuilder();

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String getResults() {
        return results.toString();
    }

    public void initParser() {
        results.append("Starting Syntax Analysis...\n");
        while (position < tokens.size()) {
            if (tokens.get(position).getTokenType() == TokenType.EOF) {
                results.append("End of File Reached.\n");
                break;
            }
            parseStatement();
        }
        results.append("Syntax Analysis Completed.\n");
    }

    private void parseStatement() {
        if (isDataType()) {
            parseDeclaration();
        } else {
            error("Expected a data type but found: " + currentToken());
        }
    }

    private void parseDeclaration() {
        consume(); // Consume the data type
        if (isIdentifier()) {
            results.append("Passed: Identifier (").append(currentToken()).append(")\n");
            consume(); // Consume the identifier

            if (isAssignOperator()) {
                results.append("Passed: Assignment Operator (").append(currentToken()).append(")\n");
                consume(); // Consume the assignment operator

                parseValue(); // Parse the value assigned
            }

            if (isSemicolon()) {
                results.append("Passed: Semicolon\n");
                consume(); // Consume the semicolon
            } else {
                error("Expected a semicolon but found: " + currentToken());
            }
        } else {
            error("Expected an identifier but found: " + currentToken());
        }
    }

    private void parseValue() {
        if (isValue()) {
            results.append("Passed: Value (").append(currentToken()).append(")\n");
            consume();
        } else {
            error("Expected a value but found: " + currentToken());
        }
    }

    private boolean isDataType() {
        return match(TokenType.INT, TokenType.FLOAT, TokenType.DOUBLE, TokenType.CHAR_TYPE,
                TokenType.STRING_TYPE, TokenType.BOOLEAN, TokenType.BYTE, TokenType.SHORT);
    }

    private boolean isIdentifier() {
        return match(TokenType.INDENTIFIER); //Cannot resolve symbol 'IDENTIFIER'
    }

    private boolean isAssignOperator() {
        return match(TokenType.ASSIGN_OP);
    }

    private boolean isValue() {
        return match(TokenType.NUMBER, TokenType.STRING, TokenType.CHAR, TokenType.BOOLEAN_LITERAL);
    }

    private boolean isSemicolon() {
        return match(TokenType.SEMICOLON);
    }

    private boolean match(TokenType... types) {
        if (position < tokens.size()) {
            for (TokenType type : types) {
                if (tokens.get(position).getTokenType() == type) {
                    return true;
                }
            }
        }
        return false;
    }

    private TokenType currentToken() {
        return position < tokens.size() ? tokens.get(position).getTokenType() : null;
    }

    private void consume() {
        if (position < tokens.size()) {
            position++;
        }
    }

    private void error(String message) {
        results.append("Syntax Error: ").append(message).append(" at line ")
                .append(tokens.get(position).getLine()).append("\n");
        position++;
    }

}
