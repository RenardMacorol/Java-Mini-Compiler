package gui.mini.compiler.Compiler;

import java.util.List;

public class SemanticAnalyzer {
    private List<Token> tokens;
    private int position;
    private StringBuilder results;

    public SemanticAnalyzer(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
        this.results = new StringBuilder();
    }

    // Public method to return results
    public String getResults() {
        return results.toString();
    }

    // Start semantic analysis
    public void initSemanticAnalysis() {
        results.append("Starting Semantic Analysis...\n");
        while (position < tokens.size()) {
            Token token = tokens.get(position);
            System.out.println("Processing token: " + token); // Debugging statement
            if (token.getTokenType() == TokenType.EOF) {
                results.append("End of File Reached.\n");
                break;
            }
            analyzeStatement();
        }
        results.append("Semantic Analysis Completed.\n");
    }


    // Analyze each statement
    private void analyzeStatement() {
        if (isDataType()) {
            analyzeDeclaration();
        } else {
            error("Expected a data type but found: " + currentToken());
            consume();
        }
    }

    // Analyze a declaration (e.g., "int x = 10;")
    private void analyzeDeclaration() {
        Token typeToken = consume(); // Consume the data type
        Token identifierToken = null;

        if (isIdentifier()) {
            identifierToken = consume(); // Consume the identifier
        } else {
            error("Expected an identifier but found: " + currentToken());
            return;
        }

        if (isAssignOperator()) {
            consume(); // Consume the assignment operator

            Token valueToken = currentToken();
            if (isValue()) {
                if (!isTypeCompatible(typeToken, valueToken)) {
                    error("Type mismatch: Cannot assign " + valueToken.getTokenType() +
                            " to " + typeToken.getTokenType());
                } else {
                    results.append("Passed: Type compatibility check for ")
                            .append(identifierToken.getLexeme())
                            .append("\n");
                }
                consume(); // Consume the value
            } else {
                error("Expected a value but found: " + currentToken());
            }
        }

        if (isSemicolon()) {
            consume(); // Consume the semicolon
        } else {
            error("Expected a semicolon but found: " + currentToken());
        }
    }


    // Check if types are compatible
    private boolean isTypeCompatible(Token typeToken, Token valueToken) {
        switch (typeToken.getTokenType()) {
            case INT:
                return valueToken.getTokenType() == TokenType.NUMBER &&
                        !valueToken.getLexeme().contains(".");
            case FLOAT:
            case DOUBLE:
                return valueToken.getTokenType() == TokenType.NUMBER &&
                        valueToken.getLexeme().contains(".");
            case CHAR_TYPE:
                return valueToken.getTokenType() == TokenType.CHAR;
            case STRING_TYPE:
                return valueToken.getTokenType() == TokenType.STRING;
            case BOOLEAN:
                return valueToken.getTokenType() == TokenType.BOOLEAN_LITERAL;
            case BYTE:
                return valueToken.getTokenType() == TokenType.NUMBER;
            case SHORT:
                return valueToken.getTokenType() == TokenType.NUMBER;
            default:
                return false;
        }
    }

    // Utility Methods
    private boolean isDataType() {
        return match(TokenType.INT, TokenType.FLOAT, TokenType.DOUBLE, TokenType.CHAR_TYPE,
                TokenType.STRING_TYPE, TokenType.BOOLEAN, TokenType.BYTE, TokenType.SHORT);
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

    private Token currentToken() {
        return position < tokens.size() ? tokens.get(position) : null;
    }

    private Token consume() {
        if (position < tokens.size()) {
            return tokens.get(position++);
        }
        return null;
    }

    private void error(String message) {
        results.append("Semantic Error: ").append(message).append("\n");
    }
}
