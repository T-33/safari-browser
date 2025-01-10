package model.renderTree.dom;

import model.htmlParser.parser.dom.DomDocument;

public class RenderDocument extends RenderNode {
    public RenderDocument(DomDocument document) {
        super(document);
    }

    @Override
    public void render() {
        DomDocument doc = (DomDocument) domNode;
        System.out.println("Rendering Document (doctype=" + doc.getDoctypeName() + ")");
        for (RenderNode child : children) {
            child.render();
        }
    }
}
