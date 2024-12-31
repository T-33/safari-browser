package model.HTMLParser.Tokenizer.Tokens;

public class DoctypeToken {
    private final String name;
    private final String publicIdentifier;
    private final String systemIdentifier;
    private final boolean forceQuirksFlag;

    public DoctypeToken(String name, String publicIdentifier, String systemIdentifier, boolean forceQuirksFlag) {
        this.name = name;
        this.publicIdentifier = publicIdentifier;
        this.systemIdentifier = systemIdentifier;
        this.forceQuirksFlag = forceQuirksFlag;
    }

    public String getName() {
        return name;
    }

    public String getPublicIdentifier() {
        return publicIdentifier;
    }

    public String getSystemIdentifier() {
        return systemIdentifier;
    }

    public boolean isForceQuirksFlag() {
        return forceQuirksFlag;
    }
}
