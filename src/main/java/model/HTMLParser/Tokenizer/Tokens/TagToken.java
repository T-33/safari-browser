package model.HTMLParser.Tokenizer.Tokens;

import java.util.Map;

public class TagToken {
    private final StringBuilder tagName;
    private boolean isSelfClosing;
    private boolean isEndToken;
    private final Map<String, String> attributes;

    private StringBuilder newAttributeName;
    private StringBuilder newAttributeValue;

    public TagToken(String tagName, boolean isSelfClosing, boolean isEndToken, Map<String, String> attributes) {
        this.tagName = new StringBuilder(tagName);
        this.newAttributeName = new StringBuilder();
        this.newAttributeValue = new StringBuilder();
        this.isSelfClosing = isSelfClosing;
        this.isEndToken = isEndToken;
        this.attributes = attributes;
    }

    public String getTagName() {
        return tagName.toString();
    }

    public boolean isSelfClosing() {
        return isSelfClosing;
    }

    public boolean isEndToken() {
        return isEndToken;
    }

    public void setSelfClosing(boolean isSelfClosing) {
        this.isSelfClosing = isSelfClosing;
    }

    public void setIsEndToken(boolean isEndToken) {
        this.isEndToken = isEndToken;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void startNewAttribute() {
        //initially attribute name and attribute value are empty strings
        //empty attribute name is invalid, emptry attribute value is valid
        boolean isEmptyAttributeName = newAttributeName.toString().equals("");
        if(!isEmptyAttributeName) {
            appendNewAttribute();
        }
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
