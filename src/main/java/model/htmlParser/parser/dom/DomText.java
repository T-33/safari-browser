package model.htmlParser.parser.dom;

public class DomText extends DomNode {
    private final String text;

    public DomText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}