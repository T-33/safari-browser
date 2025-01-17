package model.cssParser.tokenizer;

public final class CSSLexerConstants {
    private CSSLexerConstants() {}

    public static final char EOF_CHAR = '\uffff';

    public static final String DOUBLE_QUOTE = "\"";
    public static final String SINGLE_QUOTE = "'";
    public static final String LEFT_PAREN = "(";
    public static final String RIGHT_PAREN = ")";
    public static final String LEFT_BRACKET = "[";
    public static final String RIGHT_BRACKET = "]";
    public static final String LEFT_BRACE = "{";
    public static final String RIGHT_BRACE = "}";
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String STAR = "*";
    public static final String DOT = ".";
    public static final String SLASH = "/";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String HASH = "#";
    public static final String AT = "@";
    public static final String PERCENT = "%";
    public static final String UNDERSCORE = "_";

    public static final char NEWLINE = '\n';
    public static final char CARRIAGE_RETURN = '\r';
    public static final char TAB = '\t';
    public static final char FORM_FEED = '\f';
    public static final char SPACE = ' ';

    public static final String CDO_SEQ = "<!--";
    public static final String CDC_SEQ = "-->";
}