package model.HTMLParser.Parser.dom;

/**
 * DOM-текстовый узел.
 * Например, текст между <div> и </div>.
 */
public class DomText extends DomNode {
    private final String text;

    public DomText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
