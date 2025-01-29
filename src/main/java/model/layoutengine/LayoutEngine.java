package model.layoutengine;

import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomText;
import model.layoutengine.layoutboxes.BoxType;
import model.layoutengine.layoutboxes.LayoutBox;
import model.layoutengine.layoutboxes.LayoutTextBox;
import model.renderTree.dom.RenderNode;
import model.renderTree.dom.RenderText;

/**
 * Build tree of layout boxes for render tree, and assigns each render tree element its separate box.
 * RenderText text is divided into words for line-wrapping purposes.
 * <p>
 * Technically all elements inside inline elements are laid out as inline elements so this problem can be considered solved.
 */
public class LayoutEngine {
    /**
     * creates layout tree without calculating coordinates and size of boxes.
     *
     * @param renderNode
     * @return layout tree.
     * @see model.renderTree.dom.RenderElement;
     */
    public static LayoutBox buildLayoutTree(RenderNode renderNode) {

        boolean isBlock = true;

        if (renderNode.getDomNode() instanceof DomElement domElement) {
            String displayProperty = domElement.getDisplayProperty();
            isBlock = displayProperty.equals("block");
        }

        LayoutBox rootBox;

        if (renderNode instanceof RenderText textNode) {
            DomText domTextElement = (DomText) textNode.getDomNode();
            String text = domTextElement.getText();

            rootBox = new LayoutTextBox(text);
        } else {
            rootBox = new LayoutBox(isBlock ? BoxType.BLOCK : BoxType.INLINE);
        }

        /**
         * If we are dealing with RenderText then there is no need to loop through its children, because it has none.
         * We need to create LayoutTextBox for each individual word of the text for proper line wrapping.
         */
        for (RenderNode childNode : renderNode.getChildren()) {
            if (childNode instanceof RenderText renderTextElement) {
                DomText domTextElement = (DomText) renderTextElement.getDomNode();
                String text = domTextElement.getText();

                String[] words = text.split(" ");
                for (String word : words) {
                    LayoutTextBox wordBox = new LayoutTextBox(word);
                    rootBox.getChildren().add(wordBox);
                }
            } else {
                rootBox.getChildren().add(buildLayoutTree(childNode));
            }
        }
        renderNode.setLayoutBox(rootBox);

        return rootBox;
    }
}
