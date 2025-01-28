package model;

import model.baseproperties.BaseProperties;
import model.cssParser.parser.CSSParser;
import model.cssParser.parser.CSSParserFactory;
import model.cssParser.parser.dom.CSSDomFactory;
import model.cssParser.parser.dom.StyleSheet;
import model.cssParser.tokenizer.CSSTokenFactory;
import model.htmlParser.parser.Parser;
import model.htmlParser.parser.ParserFactory;
import model.htmlParser.parser.dom.DomDocument;
import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomText;
import model.layoutengine.LayoutEngine;
import model.layoutengine.layoutboxes.BoxType;
import model.layoutengine.layoutboxes.LayoutBox;
import model.layoutengine.layoutboxes.LayoutTextBox;
import model.renderTree.RenderTreeBuilder;
import model.renderTree.RenderTreeBuilderFactory;
import model.renderTree.StyleResolver;
import model.renderTree.dom.RenderNode;
import model.renderTree.dom.RenderNodeFactory;
import model.renderTree.dom.RenderText;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ParserFactory parserFactory = new ParserFactory();
        CSSDomFactory cssDomFactory = new CSSDomFactory();
        CSSTokenFactory tokenFactory = new CSSTokenFactory();
        CSSParserFactory cssParserFactory = new CSSParserFactory(cssDomFactory, tokenFactory);
        RenderNodeFactory renderNodeFactory = new RenderNodeFactory();
        RenderTreeBuilderFactory builderFactory = new RenderTreeBuilderFactory(renderNodeFactory);

        StyleResolver styleResolver = new StyleResolver();

        Engine engine = new Engine(parserFactory, cssParserFactory, builderFactory, styleResolver);

        String htmlInput = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Test Document</title>
                    <span>Span Text</span>
                    
                </head>
                <body>
                    <h1>Title H1</h1>
                    
                    <p>This is a <b>bold bold</b> text in a paragraph shitty.
                    <span>Lorem ipsum dolor sit amet,<p>BLOCK IN INLINE</p> consectetur adipiscing elit. Sed do eiusmod tempor 
                    incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud 
                    exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure 
                    dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
                    Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit
                     anim id est laborum.</span>
                    
                
                    </p>
                    <div class="myClass">This is a div</div>
                </body>
                </html>
                """;

        String cssInput = """
                html, body, div, p, h1, h2, h3 {
                    display: block;
                }
                .myClass {
                  display: inline;
                  background: yellow;
                }
                """;

        Parser htmlParser = parserFactory.createParser(htmlInput);
        DomDocument doc = htmlParser.getDomDocument();

        CSSParser cssParser = cssParserFactory.createParser(cssInput);
        StyleSheet styleSheet = cssParser.getStyleSheet();

        StyleResolver.applyStyles(doc, styleSheet);

        RenderTreeBuilder builder = builderFactory.createBuilder();
        RenderNode root = builder.build(doc);

//        if (root != null) {
//            root.render();
//        }

        LayoutBox rootBox = LayoutEngine.buildLayoutTree(root);
        rootBox.layout(null);
        printLayout(rootBox, 0);

        root.render();
    }
    private static void printRenderWithLayout( RenderNode root, int nestingLevel) {
        if(root.getDomNode() instanceof DomElement domElement) {
            String tagName = domElement.getTagName();
            System.out.println("====================================");
            System.out.println(tagName.toUpperCase());
        }
        printLayout(root.getLayoutBox(), nestingLevel);

        for(RenderNode child : root.getChildren()) {
            printRenderWithLayout(child, nestingLevel + 1);
        }
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

    private static List<LayoutTextBox> extractText(LayoutBox layoutBox, List<LayoutTextBox> textList) {

        if ( layoutBox instanceof  LayoutTextBox textBox) {
            textList.add(textBox);
        }

        for(LayoutBox child : layoutBox.getChildren()) {
            extractText(child, textList);
        }

        return textList;
    }
}