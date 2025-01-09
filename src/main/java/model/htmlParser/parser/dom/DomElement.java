package model.htmlParser.parser.dom;

import java.util.HashMap;
import java.util.Map;

/**
 * DOM-элемент (тэг).
 */
public class DomElement extends DomNode {
    private String tagName;
    private Map<String, String> attributes;

    public DomElement(String tagName) {
        this.tagName = tagName;
        this.attributes = new HashMap<>();
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = (attributes == null) ? new HashMap<>() : attributes;
    }
}
