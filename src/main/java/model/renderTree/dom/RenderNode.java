package model.renderTree.dom;

import model.htmlParser.parser.dom.DomNode;
import java.util.ArrayList;
import java.util.List;

public abstract class RenderNode {
    protected final DomNode domNode;
    protected final List<RenderNode> children = new ArrayList<>();

    private int x;
    private int y;
    private int width;
    private int height;

    protected RenderNode(DomNode domNode) {
        this.domNode = domNode;
    }

    public DomNode getDomNode() {
        return domNode;
    }

    public List<RenderNode> getChildren() {
        return children;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void render();
}
