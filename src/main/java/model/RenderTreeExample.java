package model;

import model.baseproperties.BaseProperties;
import model.baseproperties.PageRenderArea;
import model.htmlParser.parser.Parser;
import model.htmlParser.parser.dom.DomDocument;
import model.htmlParser.parser.dom.DomText;
import model.layoutengine.layoutboxes.LayoutBox;
import model.layoutengine.LayoutEngine;
import model.layoutengine.layoutboxes.LayoutTextBox;
import model.renderTree.RenderTreeBuilder;
import model.renderTree.dom.RenderNode;
import model.renderTree.dom.RenderNodeFactory;
import model.renderTree.dom.RenderText;

import java.awt.*;
import java.awt.image.BufferedImage;

public class
RenderTreeExample {
    public static void main(String[] args) {
        //setup page render area;
       PageRenderArea pageRenderArea = BaseProperties.getPageRenderArea();
        pageRenderArea.setHeight(600);
        pageRenderArea.setWidth(800);
        pageRenderArea.setX(0);
        pageRenderArea.setY(100);

        String textBlock = """
                <!DOCTYPE html>
                <html lang="en">
                <head></head>
                <body>
                    <main>
                       \s
                        <span>Nothing beats</span>
                        <span>Larry Bird</span>
                        <div>BIG ass div element</div>
                        <span>I love Boston Celtics</span>
                       \s
                    </main>
                </body>
                </html>""";

        Parser parser = new Parser(textBlock);
        DomDocument doc = parser.getDomDocument();

        RenderNodeFactory factory = new RenderNodeFactory();
        RenderTreeBuilder builder = new RenderTreeBuilder(factory);
        RenderNode root = builder.build(doc).getChildren().get(0);

        LayoutBox rootBox = LayoutEngine.buildLayoutTree(root);

        printLayout(rootBox, 0);

//        if (root != null) {
//            root.render();
//        }
//
//        printText(root);
//
//        LayoutBox rootLayout = LayoutEngine.layout(root, 800, 600, 0, 0);
//
//        printLayout(root, rootLayout, 0);
//
//        JFrame frame = new JFrame("layout");
//        frame.setSize(800, 600);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setLayout(new BorderLayout());
//
//        CustomCanvas canvas = new CustomCanvas(root, rootLayout);
//
//        frame.add(canvas, BorderLayout.CENTER);
//        frame.setVisible(true);
    }

    private static void printLayout(LayoutBox box, int nestingLevel) {
        System.out.print("\t".repeat(nestingLevel));
        System.out.print(box);
        if(box instanceof LayoutTextBox textBox) {
            System.out.print(" TEXT " + textBox.getText());
        }
        System.out.println("\n");

        for(LayoutBox childBox : box.getChildren()) {
            printLayout(childBox, nestingLevel + 1);
        }
    }

    private static void printText(RenderNode node) {
        for (RenderNode child : node.getChildren()) {
            printText(child);
        }
        if (node instanceof RenderText textNode) {
            DomText domTextElement = (DomText) textNode.getDomNode();
            System.out.println(domTextElement.getText());
            System.out.println("Size: " + calculateTextNodeWidthPixels(textNode) + "px");
        }
    }

    private static int calculateTextNodeWidthPixels(RenderText node) {
        //temporary buffered image object to access Graphics2D
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        String defaultFont = BaseProperties.getBaseFontName();
        int defaultFontSize = BaseProperties.getBaseFontSize();
        //todo improve questionable naming

        Font font = new Font(defaultFont,  Font.PLAIN, defaultFontSize);
        FontMetrics fontMetrics = g2.getFontMetrics();
        g2.dispose();

        DomText domTextElement = (DomText) node.getDomNode();
        String text = domTextElement.getText();

        int textLength = fontMetrics.stringWidth(text);

        return textLength;
    }
}