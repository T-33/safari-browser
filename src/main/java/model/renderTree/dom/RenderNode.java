package model.renderTree.dom;

import model.htmlParser.parser.dom.DomNode;
import model.layoutengine.layoutboxes.BoxType;
import model.layoutengine.layoutboxes.LayoutBox;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderNode {
    protected DomNode domNode;
    protected final List<RenderNode> children = new ArrayList<>();
    protected LayoutBox layoutBox;

    protected RenderNode(DomNode domNode) {
        this.domNode = domNode;
        this.layoutBox = new LayoutBox(BoxType.BLOCK);
    }

    public DomNode getDomNode() {
        return domNode;
    }

    public List<RenderNode> getChildren() {
        return children;
    }

    public void setLayoutBox(LayoutBox layoutBox) {
        this.layoutBox = layoutBox;
    }

    public abstract void render();
}