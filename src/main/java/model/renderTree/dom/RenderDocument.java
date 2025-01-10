package model.renderTree.dom;

import model.htmlParser.parser.dom.DomDocument;

public class RenderDocument extends RenderNode {
    public RenderDocument(DomDocument document) {
        super(document);
    }

    @Override
    public void render() {
        System.out.println("Rendering Document (doctype="
                + ((DomDocument) domNode).getDoctypeName() + ") at "
                + getX() + "," + getY()
                + " size " + getWidth() + "x" + getHeight());
        for (RenderNode child : children) {
            child.render();
        }
    }
}
