package model.renderTree.dom;

import model.htmlParser.parser.dom.DomNode;
import java.util.ArrayList;
import java.util.List;

public abstract class RenderNode {
    protected final DomNode domNode;
    protected final List<RenderNode> children = new ArrayList<>();

    protected RenderNode(DomNode domNode) {
        this.domNode = domNode;
    }

    public DomNode getDomNode() {
        return domNode;
    }

    public List<RenderNode> getChildren() {
        return children;
    }

    public abstract void render();
}