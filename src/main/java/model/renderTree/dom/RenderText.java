package model.renderTree.dom;

import model.htmlParser.parser.dom.DomText;

public class RenderText extends RenderNode {
    public RenderText(DomText text) {
        super(text);
    }

    @Override
    public void render() {
        DomText t = (DomText) domNode;
        String value = t.getText().replaceAll("\\s+", " ").trim();
        System.out.println("Rendering Text: \"" + value + "\"");
    }
}
