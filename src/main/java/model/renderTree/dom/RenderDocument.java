package model.renderTree.dom;

import model.htmlParser.parser.dom.DomDocument;

public class RenderDocument extends RenderNode {
    public RenderDocument(DomDocument document) {
        super(document);
    }

    @Override
    public void render() {
        for (RenderNode child : children) {
            child.render();
        }
    }
}