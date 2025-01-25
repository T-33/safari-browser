package model.layoutengine.layoutboxes;

import model.baseproperties.BaseProperties;

import java.util.List;
import java.util.ArrayList;

public class LayoutBox {
    private float x;
    private float y;
    private float width;
    private float height;
    private List<LayoutBox> children;
    private BoxType boxType;

    /**
     * Block elements process inline elements using lineBoxes;
     */
    private List<LayoutBox> lineBoxes;
    private LayoutBox currentLine;

    public LayoutBox(float x, float y, float width, float height, List<LayoutBox> children, BoxType boxType) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.children = children;
        this.boxType = boxType;
        lineBoxes = new ArrayList<>();
    }

    private void createNewLineBox(LayoutBox containingBox) {
        boolean currentLineExists = currentLine != null;
        if (currentLineExists) {
            lineBoxes.add(currentLine);
        }
        currentLine = new LayoutBox(BoxType.LINE);
        currentLine.setX(containingBox.getWidth());
        currentLine.setWidth(containingBox.getWidth());
    }

    /**
     * dimensions and coordinates set to zero.
     *
     * @param boxType
     */
    public LayoutBox(BoxType boxType) {
        this(0, 0, 0, 0, new ArrayList<>(), boxType);
    }

    /**
     * Calculates blocks position and size.
     * If layout box has no children then its height and width remain 0;
     *
     * @param containingBox parent of box. If null, then
     */
    public void layout(LayoutBox containingBox) {

        if (boxType == BoxType.BLOCK) {
            layoutBlock(containingBox);
        } else if(boxType == BoxType.INLINE) {
//            layoutInline(containingBox);
        } else {
            layoutLine(containingBox);
        }
    }

    /**
     * css properties are essentially useless, because layout has no access to styles.
     * Blocks are layed out as if height, width, margin and padding are set to "auto".
     *
     * @param containingBox
     */
    public void layoutBlock(LayoutBox containingBox) {
        calculateBlockWidth(containingBox);
        calculateBlockPosition(containingBox);
        layoutBlockChildren();
        calculateBlockHeight();
    }

    public void layoutInline(LayoutBox containingBox) {
//        calculateInlinePosition(containingBox);
        layoutInlineChildren();
        calculateInlineWidth(containingBox);
        calculateInlineHeight(containingBox);
    }

    /**
     * Line's width and position are set upon creation and height is determined during layout.
     * All children are supposed to be in line before layout.
     * Children must fit inside of line, that sum of their width is less or equal to line width;
     * Children in a line are laid out horizontally.
     *
     * Line children are always text nodes;
     * @param containingBox
     */
    public void layoutLine(LayoutBox containingBox) {
        float currentX = x;
        for (LayoutBox child :  children) {
            child.layout(containingBox);
            child.setX(currentX);
            child.setY(y);
            currentX += child.getWidth();

            height = Math.max(height, child.getHeight());
        }
    }

    public void calculateBlockWidth(LayoutBox containingBox) {
        final boolean isRoot = containingBox == null;

        if (isRoot) {
            final int renderAreaWidth = BaseProperties.getPageRenderArea().getWidth();
            this.width = renderAreaWidth;
        } else {
            this.width = containingBox.getWidth();
        }
    }

    /**
     * inline container cannot be bigger than its parent
     *
     * @param containingBox
     */
    public void calculateInlineWidth(LayoutBox containingBox) {
        final boolean isBlockChild = containingBox.getBoxType() == BoxType.BLOCK;
        final boolean isWiderThanParent = width > containingBox.getWidth();

        final boolean cropInline = isBlockChild && isWiderThanParent;

        if (cropInline) {
            width = containingBox.getWidth();
        }
    }

    /**
     * Intentionally left empty, because height property is set to auto by default. {@link LayoutBox::layout}
     */
    public void calculateBlockHeight() {
    }

    public void calculateBlockPosition(LayoutBox containingBox) {
        final boolean isRoot = containingBox == null;

        if (isRoot) {
            x = 0;
            y = 0;
        } else {
            x = containingBox.getX();
            y = containingBox.getHeight() + containingBox.getY();
        }
    }

    public void layoutBlockChildren() {
        //increments with each new children to current line
        //used for lines of inline elements
        float currentLineWidth = 0;
        for (LayoutBox child : children) {

            child.layout(this);

            if(child instanceof LayoutTextBox textBox) {
                System.out.println("found child " + textBox.getText());
                if(currentLine == null) {
                    currentLine = new LayoutBox(BoxType.LINE);
                    currentLine.setWidth(width);
                    currentLine.setY(y + height);
                }
                //TODO FIX technically parent container doesn't impact layout
                // assumed that inline elements contain only inline elements or text


                if(currentLineWidth + child.getWidth() <= currentLine.getWidth()) {
                    currentLine.getChildren().add(child);
                    currentLineWidth += child.getWidth();
                } else {
                    //finalize currentLine

                    //TODO technically line box doesn't need parent element to layout
                    // FIX====Э
                    currentLine.layout(this);
                    height += currentLine.getHeight();

                    lineBoxes.add(currentLine);
                    currentLine = null;
                    currentLineWidth = 0;

                    //create new
                    currentLine = new LayoutBox(BoxType.LINE);
                    currentLine.setWidth(width);
                    currentLine.setY(y + height);
                    currentLine.getChildren().add(child);
                }

            } else if(child.getBoxType() == BoxType.INLINE) {


                //BLOCK
            } else {
                height += child.getHeight();
            }
        }

        //finalize currentLine after iteration over block children
        if(currentLine != null) {
            currentLine.layout(this);
            height += currentLine.getHeight();

            lineBoxes.add(currentLine);
            currentLine = null;
        }
    }

    /**
     * Extracts text elements from inline element and lays them using lines;
     * @param inlineBox
     */
    public void layoutInlineBox(LayoutBox inlineBox, float currentY) {

    }


    private static List<LayoutTextBox> extractTextElements(LayoutBox layoutBox, List<LayoutTextBox> textList) {

        if ( layoutBox instanceof  LayoutTextBox textBox) {
            textList.add(textBox);
        }

        for(LayoutBox child : layoutBox.getChildren()) {
            extractTextElements(child, textList);
        }

        return textList;
    }


    /**
     * Intentionally left empty, because height property is set to auto by default. {@link LayoutBox::layout}
     */
    public void calculateInlineHeight(LayoutBox containingBox) {
        float maxChildHeight = 0;
        for (LayoutBox child : children) {
            if(child.getHeight() > maxChildHeight) {
                maxChildHeight = child.getHeight();
            }
        }
        height = maxChildHeight;
    }

    public void calculateInlinePosition(LayoutBox containingBox) {
        final boolean isRoot = containingBox == null;

        if (isRoot) {
            x = 0;
            y = 0;
        } else {
            x = containingBox.getX();
            y = containingBox.getHeight() + containingBox.getY();
        }
    }

    public void layoutInlineChildren() {
        for (LayoutBox child : children) {
            child.layout(this);
            width += child.width;
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