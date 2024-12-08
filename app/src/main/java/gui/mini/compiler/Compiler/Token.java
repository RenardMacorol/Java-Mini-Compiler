package gui.mini.compiler.Compiler;

public class Token {
    final TokenType tokenType;
    final String lexeme;
    final int line; // Add a line field

    Token(TokenType tokenType, String lexeme, int line) { // Update constructor
        this.tokenType = tokenType;
        this.lexeme = lexeme;
        this.line = line;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() { // Add a getter for the line
        return line;
    }

    @Override
    public String toString() {
        return "Type: " + tokenType + " Lexeme: " + lexeme + " Line: " + line;
    }
}
