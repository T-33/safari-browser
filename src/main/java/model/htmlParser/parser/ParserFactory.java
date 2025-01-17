package model.htmlParser.parser;

public final class ParserFactory {
    public Parser createParser(String htmlInput) {
        return new Parser(htmlInput);
    }
}