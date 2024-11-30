package gui.mini.compiler.Compiler;

public class Token {
    final TokenType tokenType;
    final String lexeme;

    Token(TokenType tokenType, String lexeme) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "Type: " + tokenType + " Lexeme: " + lexeme;
    }

}
