package model.HTMLParser.Tokenizer.Tokens;

import java.util.Map;

public class TagToken {
    private final StringBuilder tagName;
    private boolean isSelfClosing;
    private final Map<String, String> attributes;

    private StringBuilder newAttributeName;
    private StringBuilder newAttributeValue;

    public TagToken(String tagName, boolean isSelfClosing, Map<String, String> attributes) {
        this.tagName = new StringBuilder(tagName);
        this.newAttributeName = new StringBuilder();
        this.newAttributeValue = new StringBuilder();
        this.isSelfClosing = isSelfClosing;
        this.attributes = attributes;
    }

    public String getTagName() {
        return tagName.toString();
    }

    public boolean isSelfClosing() {
        return isSelfClosing;
    }

    public void setSelfClosing(boolean isSelfClosing) {
        this.isSelfClosing = isSelfClosing;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void startNewAttribute() {
        newAttributeName.setLength(0);
        newAttributeValue.setLength(0);
    }

    public void appendAttributeName(char c) {
        newAttributeName.append(c);
    }

    public void appendAttributeValue(char c) {
        newAttributeValue.append(c);
    }

    public void appendCharName(char c) {
        tagName.append(c);
    }

    public void appendNewAttribute() {
        attributes.put(newAttributeName.toString(), newAttributeValue.toString());
    }
}
