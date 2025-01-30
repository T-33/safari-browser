package model.layoutengine.layoutboxes;

import model.baseproperties.BaseProperties;
import model.baseproperties.PageRenderArea;
import model.renderTree.dom.RenderNode;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents area taken by render element.
 *
 * @see model.renderTree
 */
public class LayoutBox {
    private final Rectangle contentArea;
    private final List<LayoutBox> children;
    private final BoxType boxType;
    private RenderNode renderNode;
    private BufferedImage image;

    /**
     * Block elements process inline elements using lineBoxes;
     */
    private final List<LayoutBox> lineBoxes;
    private LineBox currentLine;

    public LayoutBox(int x, int y, int width, int height, List<LayoutBox> children, BoxType boxType) {
        contentArea = new Rectangle(x, y, width, height);
        this.children = children;
        this.boxType = boxType;
        lineBoxes = new ArrayList<>();
    }

    /**
     * dimensions and coordinates are set to zero by default.
     *
     * @param boxType
     */
    public LayoutBox(BoxType boxType) {
        this(0, 0, 0, 0, new ArrayList<>(), boxType);
    }

    /**
     * Calculates blocks position and size.
     * If layout box has no children then its height and width remain 0;
     * At current level of layout's complexity and due to lack of time inline elements are not laid out,
     * rather block parent extracts text elements and lays them as its own children.
     *
     * @param containingBox parent of box. If null, then
     */
    public void layout(LayoutBox containingBox) {
        if (boxType == BoxType.BLOCK) {
            layoutBlock(containingBox);
        }
    }

    /**
     * CSS properties are essentially useless, because layout has no access to styles.
     * Blocks are laid out as if height, width are set to "auto", and as if margin and padding are set to 0.
     *
     * @param containingBox
     */
    public void layoutBlock(LayoutBox containingBox) {
        calculateBlockWidth(containingBox);
        calculateBlockPosition(containingBox);
        layoutBlockChildren();
        calculateBlockHeight();
    }

    /**
     * Block element takes up its parent's width or render area width.
     * Unless size was specified during layout tree construction(in our case only for image elements).
     * @param containingBox
     */
    public void calculateBlockWidth(LayoutBox containingBox) {
        final boolean isRoot = containingBox == null;

        if (isRoot) {
            final int renderAreaWidth = BaseProperties.getPageRenderArea().getWidth();
            setWidth(renderAreaWidth);
        } else {
            if(getWidth() == 0) {
                setWidth(containingBox.getWidth());
            }
        }
    }

    /**
     * Intentionally left empty, because height property is already calculated during layout of children,
     * and since css properties affecting layout are not used.{@link LayoutBox::layoutBlockChildren}
     */
    public void calculateBlockHeight() {
    }

    /**
     * Block is appended to the bottom to the left of its parent container.
     * If there is no parent container, it is positioned in the left-top corner of page render area.
     *
     * @param containingBox
     */
    public void calculateBlockPosition(LayoutBox containingBox) {
        final boolean isRoot = containingBox == null;

        if (isRoot) {
            setX(PageRenderArea.getX());
            setY(PageRenderArea.getY());
        } else {
            setX(containingBox.getX());
            setY(containingBox.getHeight() + containingBox.getY());
        }
    }

    /**
     * Text elements are grouped into line. If text element doesn't fit, new line is created and process repeats.
     * Inline children are flattened into list of text elements that are processed as block's children.
     * Child block's and line's height are added to block's height.
     */
    public void layoutBlockChildren() {
        for (LayoutBox child : children) {
            if (child instanceof LayoutTextBox) {
                child.layout(this);
                if (currentLine == null) {
                    currentLine = new LineBox();
                    currentLine.setWidth(getWidth());
                    currentLine.setX(getX());
                    currentLine.setY(getY() + getHeight());
                }
                final boolean fitsInCurrentLine = currentLine.sumOfChildrenWidth() + child.getWidth() <= currentLine.getWidth();

                if (!fitsInCurrentLine) {
                    finalizeCurrentLine();
                    createNewLine();
                }
                currentLine.addChild(child);
            } else if (child.getBoxType() == BoxType.INLINE) {
                layoutInlineBox(child);
            } else if (child.getBoxType() == BoxType.BLOCK){
                finalizeCurrentLine();
                child.layout(this);

                setHeight(getHeight() + child.getHeight());
            }
        }
        finalizeCurrentLine();
    }

    /**
     * Creates new line box, positioned at the bottom of block parent.
     */
    public void createNewLine() {
        currentLine = new LineBox();
        currentLine.setWidth(getWidth());
        currentLine.setY(getY() + getHeight());
    }

    /**
     * If current line exists, layouts it and adds to list of lines.
     * Sets current line to null
     */
    public void finalizeCurrentLine() {
        if (currentLine != null) {
            currentLine.layout(this);
            setHeight(getHeight() + currentLine.getHeight());
            lineBoxes.add(currentLine);
            currentLine = null;
        }
    }

    /**
     * Extracts text elements from inline element and lays them using lines;
     * Inline element itself is not laid out, only children text elements are laid out.
     * @param inlineBox
     */
    public void layoutInlineBox(LayoutBox inlineBox) {
        List<LayoutTextBox> extractedTextElements = extractTextElements(inlineBox, new ArrayList<>());

        for (LayoutBox child : extractedTextElements) {

            child.layout(this);
            if (currentLine == null) {
                createNewLine();
            }

            final boolean fitsInCurrentLine = currentLine.sumOfChildrenWidth() + child.getWidth() <= currentLine.getWidth();

            if (fitsInCurrentLine) {
                currentLine.getChildren().add(child);
            } else {
                finalizeCurrentLine();
                createNewLine();
                currentLine.addChild(child);
            }
        }
    }


    /**
     * Extracts all children of LayoutTextBox class from provided layoutBox.
     * Searches depth-first.
     *
     * @param layoutBox
     * @param textList
     * @return
     */
    private static List<LayoutTextBox> extractTextElements(LayoutBox layoutBox, List<LayoutTextBox> textList) {

        if (layoutBox instanceof LayoutTextBox textBox) {
            textList.add(textBox);
        }

        for (LayoutBox child : layoutBox.getChildren()) {
            extractTextElements(child, textList);
        }

        return textList;
    }

    public RenderNode getRenderNode() {
        return renderNode;
    }

    public void setRenderNode(RenderNode renderNode) {
        this.renderNode = renderNode;
    }

    public void setImage(BufferedImage img) {
        this.image = img;
    }

    public int getX() {
        return (int) contentArea.getX();
    }

    public void setX(int x) {
        contentArea.x = x;
    }

    public int getY() {
        return (int) contentArea.getY();
    }

    public void setY(int y) {
        contentArea.y = y;
    }

    public int getWidth() {
        return contentArea.width;
    }

    public void setWidth(int width) {
        contentArea.width = width;
    }

    public int getHeight() {
        return contentArea.height;
    }

    public void setHeight(int height) {
        contentArea.height = height;
    }

    public List<LayoutBox> getChildren() {
        return children;
    }

    public void addChild(LayoutBox child) {
        children.add(child);
    }

    public BoxType getBoxType() {
        return this.boxType;
    }

    @Override
    public String toString() {
        return String.format("LayoutBox(x=%d, y=%d, width=%d, height=%d)", getX(), getY(), getWidth(), getHeight()) + boxType;
    }
}