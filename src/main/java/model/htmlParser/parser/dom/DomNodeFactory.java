package model.htmlParser.parser.dom;

public final class  DomNodeFactory {

    public DomComment createDomComment(String comment) {
        return new DomComment(comment);
    }

    public DomText createDomText(String text) {
        return new DomText(text);
    }

    public DomElement createDomElement(String tagName) {
        return new DomElement(tagName);
    }
}