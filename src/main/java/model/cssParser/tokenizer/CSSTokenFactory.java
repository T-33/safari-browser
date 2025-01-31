package model.cssParser.tokenizer;

public class CSSTokenFactory {
    public CSSToken create(CSSTokenType type, String value) {
        return new CSSToken(type, value);
    }
}