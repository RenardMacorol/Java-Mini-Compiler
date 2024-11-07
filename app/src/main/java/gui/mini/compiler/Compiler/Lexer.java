package gui.mini.compiler.Compiler;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    String input;
    List<Token> tokens;
    int currBuff = 0;
    char currChar;
    private boolean ifFloat;

    public Lexer(String input) {
        this.input = input;
        inputReader();
    }

    private void reader() {
        currBuff++;
    }

    private void inputReader() {
        tokens = new ArrayList<>();
        while (currBuff <= input.length() - 1) {
            Token newToken;
            currChar = input.charAt(currBuff);
            if (Character.isLetter(currChar)) {
                newToken = inspectLetter();
                tokens.add(newToken);
            }
            if (Character.isDigit(currChar)) {
                newToken = inspectNumber();
                tokens.add(newToken);
            }
            if (!Character.isWhitespace(currChar)) {

                switch (currChar) {
                    case '=':
                        tokens.add(new Token(Tokentype.ASSIGN_OP, "="));
                        break;

                    case ';':
                        tokens.add(new Token(Tokentype.DELIMITER, ";"));
                        break;
                    case '\'':

                        tokens.add(new Token(Tokentype.S_QOUTE, "'"));
                        break;
                    case '\"':

                        tokens.add(new Token(Tokentype.D_QOUTE, "\""));
                        break;
                    default:
                        System.out.println("Invalid Token" + currChar);
                        break;
                }
            }
            reader();
        }
        reader();
    }

    private Token inspectLetter() {
        StringBuilder word = new StringBuilder();
        while (Character.isLetter(currChar) && currBuff < input.length()
                && currChar != '\'' && currChar != '\"' && currChar != ';') {
            currChar = input.charAt(currBuff);

            word.append(currChar);
            reader();
        }
        currBuff--;
        return tokenGenerator(word.toString().trim());
    }

    private Token tokenGenerator(String word) {
        word = word.trim();
        switch (word) {
            case "int":
                return new Token(Tokentype.INT, word);
            case "float":
                return new Token(Tokentype.FLOAT, word);
            case "double":
                return new Token(Tokentype.DOUBLE, word);
            case "String":
                return new Token(Tokentype.STRING, word);
            case "char":
                return new Token(Tokentype.CHAR, word);
            case "boolean":
                return new Token(Tokentype.BOOLEAN, word);
            default:
                return new Token(Tokentype.IDENTIFIER, word);
        }

    }

    private Token inspectNumber() {

        StringBuilder number = new StringBuilder();
        while (Character.isDigit(currChar) && currBuff < input.length()
                && currChar != '\'' && currChar != '\"' && currChar != ';') {
            currChar = input.charAt(currBuff);
            if (currChar == '.') {
                tokens.add(new Token(Tokentype.DOT, "."));
                currBuff--;
                inspectNumber();
            } else {
                number.append(currChar);
            }
            reader();
        }
        return new Token(Tokentype.NUM_VAL, number.toString().trim());
    }

    public List<Token> getTokens() {
        return tokens;
    }

}
