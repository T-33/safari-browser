package model.htmlParser.parser.dom;

import java.util.HashMap;
import java.util.Map;

public class DomElement extends DomNode {
    private final String tagName;
    private Map<String, String> attributes;

    public DomElement(String tagName) {
        this.tagName = tagName;
        this.attributes = new HashMap<>();
    }

    public String getTagName() {
        return tagName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attrs) {
        attributes = (attrs == null) ? new HashMap<>() : attrs;
    }
}