package model.htmlParser.parser.dom;

public class DomComment extends DomNode {
    private String comment;

    public DomComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}