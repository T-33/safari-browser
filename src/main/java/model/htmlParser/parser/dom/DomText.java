package model.htmlParser.parser.dom;

/**
 * DOM-текстовый узел.
 */
public class DomText extends DomNode {
    private String text;

    public DomText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}