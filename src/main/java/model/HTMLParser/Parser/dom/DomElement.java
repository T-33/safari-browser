package model.HTMLParser.Parser.dom;

import java.util.HashMap;
import java.util.Map;

/**
 * DOM-элемент (тэг, например <div>).
 */
public class DomElement extends DomNode {
    private final String tagName;
    private Map<String, String> attributes = new HashMap<>();

    public DomElement(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        if (attributes == null) {
            return;
        }
        this.attributes = attributes;
    }
}
