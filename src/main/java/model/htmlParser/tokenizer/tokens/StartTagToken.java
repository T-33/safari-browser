package model.htmlParser.tokenizer.tokens;

import java.util.Map;

public class StartTagToken {
    private final StringBuilder tagName;
    private final boolean isSelfClosing;
    private final Map<String, String> attributes;

    public StartTagToken(String tagName, boolean isSelfClosing, Map<String, String> attributes) {
        this.tagName =  new StringBuilder(tagName);
        this.isSelfClosing = isSelfClosing;
        this.attributes = attributes;
    }

    public String getTagName() {
        return tagName.toString();
    }

    public boolean isSelfClosing() {
        return isSelfClosing;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void appendCharName(char c) {
        tagName.append(c);
    }
}
