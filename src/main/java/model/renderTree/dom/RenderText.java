package model.renderTree.dom;

import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomNode;
import model.htmlParser.parser.dom.DomText;
import model.layoutengine.layoutboxes.LayoutTextBox;

public class RenderText extends RenderNode {
    public RenderText(DomText text) {
        super(text);
    }

    public void setDomNode(DomNode domNode) {
        this.domNode = domNode;
    }

    @Override
    public void render() {
        if (domNode instanceof DomText text) {
            DomElement parentElement = null;
            if (domNode.getParent() instanceof DomElement element) {
                parentElement = element;
            }

            LayoutTextBox textBox = new LayoutTextBox(text.getText(), parentElement);
            setLayoutBox(textBox);
        }
    }
}