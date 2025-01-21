package model.layoutengine;

import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomText;
import model.layoutengine.layoutboxes.BoxType;
import model.layoutengine.layoutboxes.LayoutBox;
import model.layoutengine.layoutboxes.LayoutTextBox;
import model.renderTree.dom.RenderNode;
import model.renderTree.dom.RenderText;

//TODO add layoutBox field to each renderElement
//TODO exclude head and doctype from rendering
//TODO make default display value in parser INLINE
//TODO if child is INLINE then parent should be inline too

public class LayoutEngine {
    /**
     * creates layout tree without calculating coordinates and size of boxes.
     *
     * some chunks are not yet done. For example every child of inline element must be inline
     * and inline boxes that have block siblings should be put inside anonymous block container.
     * @param renderNode
     * @param displayProperty - either "block" or "inline", case-insensitive.
     *                          if anything else then defaults to "inline"
     * @return layout tree.
     */
    public static LayoutBox buildLayoutTree(RenderNode renderNode) {

        /**
         * if element doesn't have display property, display property is set to block.
         * only RenderElement has display styles -> only RenderElement has display property.
         * @see model.renderTree.dom.RenderElement;
         */
         boolean isBlock = true;

        //questionable naming: renderNode and renderElement what is the difference?
        if(renderNode.getDomNode() instanceof DomElement domElement) {
            String displayProperty =  domElement.getDisplayProperty();
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

        //TODO RenderText cannot have children why it inherits children property
        // =============================
        // solved partially: it turns out that it is incorrect for inline element to have block children and it should be handled at DOM level.
        /**
         * If we are dealing with RenderText then there is no need to loop through its children, because it has none.
         * We need to create LayoutTextBox for each individual word of the text for proper line wrapping.
         */
            for (RenderNode childNode : renderNode.getChildren()) {
                if(childNode instanceof RenderText renderTextElement) {
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
