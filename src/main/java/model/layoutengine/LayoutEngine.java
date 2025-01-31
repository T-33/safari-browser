package model.layoutengine;

import model.Model;
import model.baseproperties.BaseProperties;
import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomText;
import model.layoutengine.layoutboxes.BoxType;
import model.layoutengine.layoutboxes.LayoutBox;
import model.layoutengine.layoutboxes.LayoutTextBox;
import model.renderTree.dom.RenderNode;
import model.renderTree.dom.RenderText;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Build tree of layout boxes for render tree, and assigns each render tree element its separate box.
 * RenderText text is divided into words for line-wrapping purposes.
 * <p>
 * Technically all elements inside inline elements are laid out as inline elements so this problem can be considered solved.
 */
public class LayoutEngine {

    private static final Model model = Model.getInstance();

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
            //ignore styles and scripts
            final List<String> ignoredTags = List.of("style", "script", "head");
            if(ignoredTags.contains(domElement.getTagName())) {
                // inline elements with no children are not rendered.
                return new LayoutBox(BoxType.INLINE);
            }

            String displayProperty = domElement.getDisplayProperty();
            isBlock = displayProperty.equals("block");

            if (domElement.isImage()) {
                LayoutBox imageBox = new LayoutBox(BoxType.BLOCK);

                String src = domElement.getAttribute("src");
                if (src == null || src.isEmpty()) {
                    imageBox.setRenderNode(renderNode);
                    return imageBox;
                }

                BufferedImage image = model.fetchImage(src);

                if (image != null) {
                    imageBox.setImage(image);

                    String widthAttr = domElement.getAttribute("width");
                    String heightAttr = domElement.getAttribute("height");

                    if (widthAttr != null && heightAttr != null) {
                        int width = Integer.parseInt(widthAttr);
                        int height = Integer.parseInt(heightAttr);
                        imageBox.setWidth(width);
                        imageBox.setHeight(height);
                    } else {
                        imageBox.setWidth(BaseProperties.DEFAULT_IMAGE_WIDTH);
                        imageBox.setHeight(BaseProperties.DEFAULT_IMAGE_HEIGHT);
                    }

                    imageBox.setRenderNode(renderNode);
                    return imageBox;
                }
            }
        }

        LayoutBox rootBox;

        if (renderNode instanceof RenderText textNode) {
            DomText domTextElement = (DomText) textNode.getDomNode();
            DomElement parentElement = null;

            if (domTextElement.getParent() instanceof DomElement element) {
                parentElement = element;
            }

            rootBox = new LayoutTextBox(domTextElement.getText(), parentElement);
        } else {
            rootBox = new LayoutBox(isBlock ? BoxType.BLOCK : BoxType.INLINE);
        }

        rootBox.setRenderNode(renderNode);

        for (RenderNode childNode : renderNode.getChildren()) {
            rootBox.getChildren().add(buildLayoutTree(childNode));
        }

        renderNode.setLayoutBox(rootBox);
        return rootBox;
    }
}