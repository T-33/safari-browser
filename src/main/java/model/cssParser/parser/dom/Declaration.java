package model.cssParser.parser.dom;

public record Declaration(String property, String value) {
    public Declaration(String property, String value) {
        this.property = (property == null) ? "" : property.trim();
        this.value = (value == null) ? "" : value.trim();
    }
}