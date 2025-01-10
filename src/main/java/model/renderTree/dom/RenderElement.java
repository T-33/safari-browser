package model.renderTree.dom;

import model.htmlParser.parser.dom.DomElement;

public class RenderElement extends RenderNode {
    public RenderElement(DomElement element) {
        super(element);
    }

    @Override
    public void render() {
        DomElement el = (DomElement) domNode;
        System.out.println("Rendering Element: <" + el.getTagName() + ">");
        for (RenderNode child : children) {
            child.render();
        }
    }
}
