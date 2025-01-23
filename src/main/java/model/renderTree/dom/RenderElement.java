package model.renderTree.dom;

import model.htmlParser.parser.dom.DomElement;

public class RenderElement extends RenderNode {
    public RenderElement(DomElement element) {
        super(element);
    }

    @Override
    public void render() {
        DomElement el = (DomElement) domNode;
        String display = el.getDisplayProperty();
        System.out.println("Rendering Element: <" + el.getTagName() + "> display="
                + display);
        for (RenderNode child : children) {
            child.render();
        }
    }
}