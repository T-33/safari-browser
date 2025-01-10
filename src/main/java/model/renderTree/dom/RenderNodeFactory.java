package model.renderTree.dom;

import model.htmlParser.parser.dom.DomComment;
import model.htmlParser.parser.dom.DomDocument;
import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomText;

public final class RenderNodeFactory {

    public RenderDocument createRenderDocument(DomDocument document) {
        return new RenderDocument(document);
    }

    public RenderElement createRenderElement(DomElement element) {
        return new RenderElement(element);
    }

    public RenderText createRenderText(DomText text) {
        return new RenderText(text);
    }

    public RenderNode createRenderComment(DomComment comment) {
        return null;
    }
}
