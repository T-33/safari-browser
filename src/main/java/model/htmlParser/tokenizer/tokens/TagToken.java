package model.htmlParser.tokenizer.tokens;

import java.util.Map;

/**
 * Token representing singular HTML tag.
 */
public class TagToken {
    private final StringBuilder tagName;
    private boolean isSelfClosing;
    private boolean isEndToken;
    private final Map<String, String> attributes;

    private final StringBuilder newAttributeName;
    private final StringBuilder newAttributeValue;

    /**
     * Creates new tag token.
     * @param tagName - new token's name. Cannot be set to anything outside of constructor, only can be appended chars.
     * @param isSelfClosing - if this html tag have closing tag. E.g. img
     * @param isEndToken - if this html tag is closing tag. E.g. /div
     * @param attributes - map of attribute name-value pairs.
     */
    public TagToken(String tagName, boolean isSelfClosing, boolean isEndToken, Map<String, String> attributes) {
        this.tagName = new StringBuilder(tagName);
        this.newAttributeName = new StringBuilder();
        this.newAttributeValue = new StringBuilder();
        this.isSelfClosing = isSelfClosing;
        this.isEndToken = isEndToken;
        this.attributes = attributes;
    }

    /**
     * Returns tag name.
     * @return tag name, like div, img etc..
     */
    public String getTagName() {
        return tagName.toString();
    }

    /**
     * Is tag self closing?
     * @return true if tag is self-closing.
     */
    public boolean isSelfClosing() {
        return isSelfClosing;
    }

    /**
     * Is tag closed?
     * @return true if tag is closed.
     */
    public boolean isEndToken() {
        return isEndToken;
    }

    /**
     * Sets self-closing flag to the one specified.
     * @param isSelfClosing - new self-closing value.
     */
    public void setSelfClosing(boolean isSelfClosing) {
        this.isSelfClosing = isSelfClosing;
    }

    /**
     * Sets is end tag flag to the one specified.
     * @param isEndToken - new end token value.
     */
    public void setIsEndToken(boolean isEndToken) {
        this.isEndToken = isEndToken;
    }

    /**
     * Map of tag's attributes
     * @return map of tag's attributes;
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Starts construction of new tag's attribute.
     * Appends previously built attribute(if exists) to list of attributes.
     */
    public void startNewAttribute() {
        boolean isEmptyAttributeName = newAttributeName.toString().equals("");
        if(!isEmptyAttributeName) {
            appendNewAttribute();
        }
        newAttributeName.setLength(0);
        newAttributeValue.setLength(0);
    }

    /**
     * Appends specified char to end of tag's name.
     * @param c - char that will be appended.
     */
    public void appendName(char c) {
        tagName.append(c);
    }

    /**
     * Appends specified char to the end of currently built attribute's name.
     * @param c - char that will be appended.
     */
    public void appendAttributeName(char c) {
        newAttributeName.append(c);
    }

    /**
     * Appends specified char to the end of currently built attribute's value.
     * @param c - char that will be appended.
     */
    public void appendAttributeValue(char c) {
        newAttributeValue.append(c);
    }

    /**
     * Appends currently built attribute to list of tag's attributes.
     * If new attribute's name already exists on the list of tag's attributes, then new attribute will be omitted.
     */
    public void appendNewAttribute() {
        if(!attributes.containsKey(newAttributeName.toString())) {
            attributes.put(newAttributeName.toString(), newAttributeValue.toString());
        }
    }
}
