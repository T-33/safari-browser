package model.renderTree.dom;

import model.htmlParser.parser.dom.DomElement;

public class RenderElement extends RenderNode {
    public RenderElement(DomElement element) {
        super(element);
    }

    @Override
    public void render() {
        for (RenderNode child : children) {
            child.render();
        }
    }
}