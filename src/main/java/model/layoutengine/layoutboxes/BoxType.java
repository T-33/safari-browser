package model.layoutengine.layoutboxes;

/**
 * Represents different display properties of LayoutBoxes.
 * Layout boxes calculate its size and position depending on box type;
 * @see model.layoutengine.layoutboxes.LayoutBox
 * @see model.layoutengine.layoutboxes.LineBox
 */
public enum BoxType {
    BLOCK,
    INLINE,
    LINE
}
