package gui.mini.compiler.Compiler;

public enum TokenType {
    // Single Character Tokens
    SEMICOLON, DOT, COMMA,
    ADD, SUB, MUL, DIV, ASSIGN_OP,

    // Literal
    INDENTIFIER, STRING, NUMBER, CHAR, BOOLEAN_LITERAL,

    // Base Type
    INT, STRING_TYPE, CHAR_TYPE, FLOAT, DOUBLE, BOOLEAN,

    // End of the file
    EOF
}
