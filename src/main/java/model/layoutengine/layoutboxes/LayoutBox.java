package model.layoutengine.layoutboxes;

import model.baseproperties.BaseProperties;
import model.htmlParser.parser.dom.DomText;
import model.renderTree.dom.RenderText;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class LayoutBox {
    private float x;
    private float y;
    private float width;
    private float height;
    private List<LayoutBox> children;
    private BoxType boxType;

    public LayoutBox(float x, float y, float width, float height, List<LayoutBox> children, BoxType boxType) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.children = children;
        this.boxType = boxType;
    }

    /**
     * dimensions and coordinates set to zero.
     * @param boxType
     */
    public LayoutBox(BoxType boxType) {
        this(0, 0, 0, 0, new ArrayList<>(), boxType);
    }

    /**
     * Calculates blocks position and size.
     * If layout box has no children then its height and width remain 0;
     * @param containingBox parent of box. If null, then
     */
    public void layout(LayoutBox containingBox) {
        final boolean isRoot = containingBox == null;

        if(!isRoot) {

        } else {
            final int renderAreaWidth = BaseProperties.getPageRenderArea().getWidth();
            final int renderAreaHeight = BaseProperties.getPageRenderArea().getHeight();

            setWidth(renderAreaWidth);
            setHeight(renderAreaHeight);
        }

        for(LayoutBox childBox: getChildren()) {
            childBox.layout(this);
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public List<LayoutBox> getChildren() {
        return children;
    }

    public void setChildren(List<LayoutBox> children) {
        this.children = new ArrayList<>(children);
    }

    public BoxType getBoxType() {
        return this.boxType;
    }

    @Override
    public String toString() {
        return String.format("LayoutBox(x=%.1f, y=%.1f, width=%.1f, height=%.1f)", x, y, width, height) + boxType;
    }
}