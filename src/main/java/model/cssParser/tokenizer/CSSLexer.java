package model.cssParser.tokenizer;

import java.util.ArrayList;
import java.util.List;

import static model.cssParser.tokenizer.CSSLexerConstants.*;

public final class CSSLexer {
    private final String input;
    private final CSSTokenFactory factory;
    private final List<CSSToken> tokens = new ArrayList<>();
    private int index = 0;
    private final int length;

    public CSSLexer(String source, CSSTokenFactory tokenFactory) {
        input = (source == null) ? "" : source;
        factory = tokenFactory;
        length = input.length();
        lex();
    }

    public List<CSSToken> getTokens() {
        return tokens;
    }

    private void lex() {
        while (!isEOF()) {
            if (tryMatchCDOorCDC()) {
                continue;
            }
            char c = peekChar();
            if (isWhitespace(c)) {
                consumeWhitespace();
            } else if (c == SLASH.charAt(0) && maybeCommentStart()) {
                skipComment();
            } else if (isStringQuote(c)) {
                consumeString();
            } else if (c == HASH.charAt(0)) {
                consumeHash();
            } else if (c == AT.charAt(0)) {
                consumeAtKeyword();
            } else if (isNumberStart()) {
                consumeNumberLike();
            } else if (isIdentStart(c)) {
                consumeIdentOrFunction();
            } else {
                consumeDelimOrSpecial();
            }
        }
        tokens.add(factory.create(CSSTokenType.EOF, ""));
    }

    private boolean tryMatchCDOorCDC() {
        if (startsWith(CDO_SEQ)) {
            addToken(CSSTokenType.CDO, CDO_SEQ);
            advanceN(CDO_SEQ.length());
            return true;
        }
        if (startsWith(CDC_SEQ)) {
            addToken(CSSTokenType.CDC, CDC_SEQ);
            advanceN(CDC_SEQ.length());
            return true;
        }
        return false;
    }

    private boolean startsWith(String seq) {
        if (index + seq.length() > length) {
            return false;
        }
        return input.startsWith(seq, index);
    }

    private boolean maybeCommentStart() {
        return (index + 1 < length) && input.charAt(index + 1) == STAR.charAt(0);
    }

    private void skipComment() {
        advance();
        advance();
        while (!isEOF()) {
            if (peekChar() == STAR.charAt(0) && nextCharIs(SLASH.charAt(0))) {
                advance();
                advance();
                break;
            }
            advance();
        }
    }

    private boolean isStringQuote(char c) {
        return (c == DOUBLE_QUOTE.charAt(0) || c == SINGLE_QUOTE.charAt(0));
    }

    private void consumeString() {
        char quote = peekChar();
        advance();
        int start = index;
        while (!isEOF()) {
            char c = peekChar();
            if (c == quote) {
                String val = input.substring(start, index);
                advance();
                addToken(CSSTokenType.STRING, val);
                return;
            }
            if (c == NEWLINE || c == CARRIAGE_RETURN) {
                String val = input.substring(start, index);
                addToken(CSSTokenType.BAD_STRING, val);
                return;
            }
            advance();
        }
        String val = input.substring(start, index);
        addToken(CSSTokenType.BAD_STRING, val);
    }

    private void consumeHash() {
        advance();
        int start = index;
        while (!isEOF() && isNameChar(peekChar())) {
            advance();
        }
        String val = input.substring(start, index);
        addToken(CSSTokenType.HASH, val);
    }

    private void consumeAtKeyword() {
        advance();
        int start = index;
        while (!isEOF() && isNameChar(peekChar())) {
            advance();
        }
        String val = input.substring(start, index);
        addToken(CSSTokenType.AT_KEYWORD, val);
    }

    private boolean isNumberStart() {
        char c = peekChar();
        if (c == PLUS.charAt(0) || c == MINUS.charAt(0)) {
            if (index + 1 < length) {
                char next = input.charAt(index + 1);
                return isDigit(next) || next == DOT.charAt(0);
            }
        } else return isDigit(c) || c == DOT.charAt(0);
        return false;
    }

    private void consumeNumberLike() {
        int signStart = index;
        if (peekChar() == PLUS.charAt(0) || peekChar() == MINUS.charAt(0)) {
            advance();
        }
        boolean foundDigitOrDot = false;
        int numStart = index;
        while (!isEOF() && (isDigit(peekChar()) || peekChar() == DOT.charAt(0))) {
            foundDigitOrDot = true;
            advance();
        }
        if (!foundDigitOrDot) {
            addToken(CSSTokenType.DELIM, input.substring(signStart, index));
            return;
        }
        String numPart = input.substring(numStart, index);
        String signPart = input.substring(signStart, numStart);
        String fullNum = signPart + numPart;

        if (!isEOF() && peekChar() == PERCENT.charAt(0)) {
            advance();
            addToken(CSSTokenType.PERCENTAGE, fullNum);
            return;
        }

        if (!isEOF() && isIdentStart(peekChar())) {
            int dimStart = index;
            while (!isEOF() && isNameChar(peekChar())) {
                advance();
            }
            String dimension = input.substring(dimStart, index);
            addToken(CSSTokenType.DIMENSION, fullNum + dimension);
        } else {
            addToken(CSSTokenType.NUMBER, fullNum);
        }
    }

    private void consumeIdentOrFunction() {
        int start = index;
        do {
            advance();
        } while (!isEOF() && isNameChar(peekChar()));
        String val = input.substring(start, index);
        if (!isEOF() && peekChar() == LEFT_PAREN.charAt(0)) {
            advance();
            addToken(CSSTokenType.FUNCTION, val);
        } else {
            addToken(CSSTokenType.IDENT, val);
        }
    }

    private void consumeDelimOrSpecial() {
        char c = peekChar();
        switch (c) {
            case '(' : addToken(CSSTokenType.OPEN_PAREN, LEFT_PAREN); break;
            case ')': addToken(CSSTokenType.CLOSE_PAREN, RIGHT_PAREN); break;
            case '[': addToken(CSSTokenType.OPEN_BRACKET, LEFT_BRACKET); break;
            case ']': addToken(CSSTokenType.CLOSE_BRACKET, RIGHT_BRACKET); break;
            case '{': addToken(CSSTokenType.OPEN_CURLY, LEFT_BRACE); break;
            case '}': addToken(CSSTokenType.CLOSE_CURLY, RIGHT_BRACE); break;
            case ':': addToken(CSSTokenType.COLON, COLON); break;
            case ';': addToken(CSSTokenType.SEMICOLON, SEMICOLON); break;
            case ',': addToken(CSSTokenType.COMMA, COMMA); break;
            default:
                addToken(CSSTokenType.DELIM, String.valueOf(c));
        }
        advance();
    }

    private void consumeWhitespace() {
        int start = index;
        while (!isEOF() && isWhitespace(peekChar())) {
            advance();
        }
        String val = input.substring(start, index);
        addToken(CSSTokenType.WHITESPACE, val);
    }

    private boolean isEOF() {
        return index >= length;
    }

    private char peekChar() {
        if (isEOF()) {
            return EOF_CHAR;
        }
        return input.charAt(index);
    }

    private void advance() {
        index++;
    }

    private void advanceN(int n) {
        index += n;
    }

    private boolean nextCharIs(char ch) {
        if (isEOF()) {
            return false;
        }
        return (index + 1 < length) && input.charAt(index + 1) == ch;
    }

    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    private boolean isWhitespace(char c) {
        return c == SPACE || c == NEWLINE || c == TAB || c == CARRIAGE_RETURN || c == FORM_FEED;
    }

    private boolean isIdentStart(char c) {
        return isLetter(c) || c == UNDERSCORE.charAt(0) || c > 0x7F;
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isNameChar(char c) {
        return isIdentStart(c) || isDigit(c) || c == MINUS.charAt(0) || c == UNDERSCORE.charAt(0);
    }

    private void addToken(CSSTokenType type, String value) {
        tokens.add(factory.create(type, value));
    }
}
