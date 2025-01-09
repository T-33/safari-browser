package model.htmlParser.tokenizer.tokens;

public class DoctypeToken {
    private final StringBuilder name;
    private final StringBuilder publicIdentifier;
    private final StringBuilder systemIdentifier;
    private  boolean forceQuirksFlag;

    public DoctypeToken(String name, String publicIdentifier, String systemIdentifier, boolean forceQuirksFlag) {
        this.name = new StringBuilder(name);
        this.publicIdentifier = new StringBuilder(publicIdentifier) ;
        this.systemIdentifier = new StringBuilder(systemIdentifier) ;
        this.forceQuirksFlag = forceQuirksFlag;
    }

    public String getName() {
        return name.toString();
    }

    public String getPublicIdentifier() {
        return publicIdentifier.toString();
    }

    public String getSystemIdentifier() {
        return systemIdentifier.toString();
    }

    public void appendName(char c) {
        name.append(c);
    }

    public void appendPublicIndentifier(char c) {
        publicIdentifier.append(c);
    }

    public void appendSystemIdentifier(char c) {
        systemIdentifier.append(c);
    }


    public boolean isForceQuirksFlag() {
        return forceQuirksFlag;
    }

    public void setForceQuirksFlag(boolean forceQuirksFlag) {
        this.forceQuirksFlag = forceQuirksFlag;
    }
}
