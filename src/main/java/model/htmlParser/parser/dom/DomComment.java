package model.htmlParser.parser.dom;

public class DomComment extends DomNode {
    private final String comment;

    public DomComment(String comment) {
        this.comment = comment;
    }
}