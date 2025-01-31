package model.htmlParser.parser.dom;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DomElement extends DomNode {
    private final String tagName;
    private Map<String, String> attributes;
    private final Map<String, String> computedStyle = new HashMap<>();
    public static final String DISPLAY_STYLE = "display";
    public static final String INLINE_STYLE = "inline";
    public static final String IMG = "img";

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

    public String getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = (attributes == null) ? new HashMap<>() : attributes;
    }

    public void setStyleProperty(String prop, String value) {
        if (prop == null || prop.isBlank()) return;
        computedStyle.put(prop.toLowerCase(), value);
    }

    public String getStyleProperty(String prop) {
        if (prop == null) return null;
        return computedStyle.get(prop.toLowerCase());
    }

    public String getDisplayProperty() {
        String disp = getStyleProperty(DISPLAY_STYLE);
        if (disp == null || disp.isBlank()) {
            disp = INLINE_STYLE;
        }
        return disp.toLowerCase();
    }

    public Map<String, String> getComputedStyle() {
        return Collections.unmodifiableMap(computedStyle);
    }

    public boolean isImage() {
        return IMG.equalsIgnoreCase(tagName);
    }
}