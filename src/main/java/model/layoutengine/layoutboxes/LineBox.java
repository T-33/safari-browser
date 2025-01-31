package model.layoutengine.layoutboxes;

/**
 * Helper class for correct layout of text elements' position in block boxes.
 * Doesn't get into layout tree.
 */
public class LineBox extends LayoutBox {
    public LineBox () {
        super(BoxType.LINE);
    }

    /**
     * Line's width and position are set upon creation and height is determined during layout.
     * All children are supposed to be in line before layout.
     * Children must fit inside of line, that sum of their width is less or equal to line width;
     * Children in a line are laid out horizontally.
     * <p>
     * Line children are always text nodes;
     * On current level of layout's complexity doesn't need parent box to layout, will need in the future(most probably).
     *
     */
    @Override
    public void layout(LayoutBox containingBox) {
        int currentX = getX();
        for (LayoutBox child : getChildren()) {
            child.layout(containingBox);
            child.setX(currentX);
            child.setY(getY());
            currentX += child.getWidth();

            final float lineHeight = getHeight();
            setHeight(Math.max((int) lineHeight, child.getHeight()));
        }
    }

    public void layoutChildren() {
        for(LayoutBox child : getChildren()) {
            child.layout(this);
        }
    }

    /**
     * Assumed that children are text boxes.
     * @return
     */
    public float sumOfChildrenWidth() {
        layoutChildren();

        float sum = 0;

        for(LayoutBox child : getChildren()) {
            sum += child.getWidth();
        }
        return sum;
    }
}
