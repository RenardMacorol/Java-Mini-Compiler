package gui.mini.compiler.Compiler;

import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int position;
    private StringBuilder results; // Collect results for output

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
        this.results = new StringBuilder();
    }

    // Public method to return results
    public String getResults() {
        return results.toString();
    }

    // Start parsing process
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

    // Parse a full statement
    private void parseStatement() {
        if (isDataType()) {
            parseDeclaration();
        } else {
            error("Expected a data type but found: " + currentToken());
        }
    }

    // Parse a declaration (e.g., "int x = 10;")
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

    // Parse a value (e.g., a number, string, or character)
    private void parseValue() {
        if (isValue()) {
            results.append("Passed: Value (").append(currentToken()).append(")\n");
            consume();
        } else {
            error("Expected a value but found: " + currentToken());
        }
    }

    // Utility Methods
    private boolean isDataType() {
        return match(TokenType.INT, TokenType.FLOAT, TokenType.DOUBLE, TokenType.CHAR_TYPE,
                TokenType.STRING_TYPE, TokenType.BOOLEAN);
    }

    private boolean isIdentifier() {
        return match(TokenType.INDENTIFIER);
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
        results.append("Syntax Error: ").append(message).append("\n");
        position++; // Skip the problematic token to attempt recovery
    }
}
