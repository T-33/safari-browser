package model.htmlParser.parser.dom;

import java.util.ArrayList;
import java.util.List;

public abstract class DomNode {
    protected List<DomNode> children = new ArrayList<>();

    public void addChild(DomNode child) {
        if (child != null) {
            children.add(child);
        }
    }

    public List<DomNode> getChildren() {
        return children;
    }
}