package model.renderTree;

import model.htmlParser.parser.dom.DomComment;
import model.htmlParser.parser.dom.DomDocument;
import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomNode;
import model.htmlParser.parser.dom.DomText;
import model.renderTree.dom.RenderDocument;
import model.renderTree.dom.RenderElement;
import model.renderTree.dom.RenderNode;
import model.renderTree.dom.RenderNodeFactory;

public final class RenderTreeBuilder {
    private final RenderNodeFactory factory;

    public RenderTreeBuilder(RenderNodeFactory factory) {
        this.factory = factory;
    }

    public RenderNode build(DomNode node) {
        if (node == null) {
            return null;
        }
        if (node instanceof DomDocument doc) {
            RenderDocument renderDoc = factory.createRenderDocument(doc);
            doc.getChildren().forEach(child -> {
                RenderNode childNode = build(child);
                if (childNode != null) {
                    renderDoc.getChildren().add(childNode);
                }
            });
            return renderDoc;
        }
        if (node instanceof DomElement el) {
            RenderElement renderEl = factory.createRenderElement(el);
            el.getChildren().forEach(child -> {
                RenderNode childNode = build(child);
                if (childNode != null) {
                    renderEl.getChildren().add(childNode);
                }
            });
            return renderEl;
        }
        if (node instanceof DomText text) {
            return factory.createRenderText(text);
        }
        if (node instanceof DomComment comment) {
            return factory.createRenderComment(comment);
        }
        return null;
    }
}
