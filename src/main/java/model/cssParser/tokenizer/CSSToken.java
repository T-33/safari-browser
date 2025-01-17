package model.cssParser.tokenizer;

public record CSSToken(CSSTokenType type, String value) {
    public CSSToken(CSSTokenType type, String value) {
        this.type = type;
        this.value = (value == null) ? "" : value;
    }
}