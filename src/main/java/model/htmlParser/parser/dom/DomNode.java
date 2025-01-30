package model.htmlParser.parser.dom;

import java.util.ArrayList;
import java.util.List;

public abstract class DomNode {
    protected List<DomNode> children = new ArrayList<>();
    private DomNode parent;

    public void addChild(DomNode child) {
        if (child != null) {
            child.setParent(this);
            children.add(child);
        }
    }

    public List<DomNode> getChildren() {
        return children;
    }

    public DomNode getParent() {
        return parent;
    }

    private void setParent(DomNode parent) {
        this.parent = parent;
    }
}