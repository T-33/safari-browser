package model.htmlParser.tokenizer.tokens;

/**
 * Token representing HTML DOCTYPE declaration tag.
 */
public class DoctypeToken {
    private final StringBuilder name;
    private final StringBuilder publicIdentifier;
    private final StringBuilder systemIdentifier;
    private  boolean forceQuirksFlag;

    /**
     * Creates new DOCTYPE declaration token.
     * @param name - new token's name. Cannot be set to anything outside of constructor, only can be appended chars.
     * @param publicIdentifier - formal name of document type declaration(DTD).
     * @param systemIdentifier - url or path to document type declaration(DTD).
     * @param forceQuirksFlag - if true tokenizer parses document in force quirks mode.
     */
    public DoctypeToken(String name, String publicIdentifier, String systemIdentifier, boolean forceQuirksFlag) {
        this.name = new StringBuilder(name);
        this.publicIdentifier = new StringBuilder(publicIdentifier) ;
        this.systemIdentifier = new StringBuilder(systemIdentifier) ;
        this.forceQuirksFlag = forceQuirksFlag;
    }

    /**
     * Returns doctype declaration's name.
     * @return doctype declaration's name(html or xml etc.)
     */
    public String getName() {
        return name.toString();
    }

    /**
     * Returns formal name of document type declaration(DTD).
     * @return formal name of document type declaration(DTD).
     */
    public String getPublicIdentifier() {
        return publicIdentifier.toString();
    }

    /**
     * Returns url or path to document type declaration(DTD).
     * @return url or path to document type declaration(DTD)
     */
    public String getSystemIdentifier() {
        return systemIdentifier.toString();
    }

    /**
     * Appends specified char to the end of document type declaration name.
     * @param c - char that will be appended.
     */
    public void appendName(char c) {
        name.append(c);
    }

    /**
     * Sets force quirks flag to on/off.
     * @param forceQuirksFlag - new flag value.
     */
    public void setForceQuirksFlag(boolean forceQuirksFlag) {
        this.forceQuirksFlag = forceQuirksFlag;
    }

}
