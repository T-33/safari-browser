package model.layout;

import model.renderTree.dom.RenderNode;

public final class LayoutEngine {
    private final int containerWidth;
    private final int containerHeight;

    public LayoutEngine(int containerWidth, int containerHeight) {
        this.containerWidth = containerWidth;
        this.containerHeight = containerHeight;
    }

    public void applyLayout(RenderNode root) {
        if (root == null) {
            return;
        }
        layoutRecursively(root, LayoutConstants.ZERO, LayoutConstants.ZERO, containerWidth);
    }

    private void layoutRecursively(RenderNode node, int posX, int posY, int availableWidth) {
        node.setX(posX);
        node.setY(posY);
        int nodeHeight = LayoutConstants.DEFAULT_HEIGHT;
        node.setWidth(availableWidth);
        node.setHeight(nodeHeight);
        int cursorY = posY + nodeHeight + LayoutConstants.MARGIN;
        for (RenderNode child : node.getChildren()) {
            layoutRecursively(child, posX, cursorY, availableWidth);
            cursorY = child.getY() + child.getHeight() + LayoutConstants.MARGIN;
        }
    }
}
