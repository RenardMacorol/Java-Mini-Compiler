package gui.mini.compiler.Compiler;

public class Token {
    Tokentype tokentype;
    String lexeme;

    Token(Tokentype tokenType, String lexeme) {
        this.tokentype = tokenType;
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return ("Token Type: " + tokentype + " Value: " + lexeme);
    }
}
