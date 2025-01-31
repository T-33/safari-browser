package model.htmlParser.parser.dom;

public class DomDocument extends DomNode {
    private String doctypeName;
    private String publicId;
    private String systemId;

    public void setDoctype(String name, String pubId, String sysId) {
        doctypeName = name;
        publicId = pubId;
        systemId = sysId;
    }
}