package model.cssParser.parser.dom;

public record Selector(String value) {
    public Selector(String value) {
        this.value = (value == null) ? "" : value.trim();
    }
}