package model.htmlParser.parser.dom;

/**
 * Корневой DOM-объект.
 * Обычно содержит в себе один корневой элемент (html),
 * но может быть и несколько узлов верхнего уровня.
 */
public class DomDocument extends DomNode {
    private String doctypeName;
    private String publicId;
    private String systemId;

    public void setDoctype(String name, String pubId, String sysId) {
        this.doctypeName = name;
        this.publicId = pubId;
        this.systemId = sysId;
    }

    public String getDoctypeName() {
        return doctypeName;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getSystemId() {
        return systemId;
    }
}