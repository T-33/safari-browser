package model.renderTree;

import model.renderTree.dom.RenderNodeFactory;

public class RenderTreeBuilderFactory {
    private final RenderNodeFactory renderNodeFactory;

    public RenderTreeBuilderFactory(RenderNodeFactory factory) {
        this.renderNodeFactory = factory;
    }
    public RenderTreeBuilder createBuilder() {
        return new RenderTreeBuilder(renderNodeFactory);
    }
}