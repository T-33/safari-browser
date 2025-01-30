package model;
import model.renderTree.dom.RenderText;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import model.Network.HttpResponse;
import model.Network.Network;


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
import model.layoutengine.LayoutEngine;
import model.layoutengine.layoutboxes.BoxType;
import model.layoutengine.layoutboxes.LayoutBox;
import model.layoutengine.layoutboxes.LayoutTextBox;
import model.renderTree.RenderTreeBuilder;
import model.renderTree.RenderTreeBuilderFactory;
import model.renderTree.StyleResolver;
import model.renderTree.dom.RenderNode;
import model.renderTree.dom.RenderNodeFactory;
import view.Canvas;

public class Main {
    private Canvas canvas;
    private static Engine engine;
    private static Network network;
    private static EngineFactory engineFactory;
    private static HttpResponse httpResponse;
    private String currentUrl;
    private String rawHTML;
    private String rawCSS;

    public Main(Canvas canvas){
        this.canvas = canvas;
        this.network = new Network();
        engineFactory = new EngineFactory();
        this.engine = engineFactory.createEngine();
    }



    public void loadPage(){
        currentUrl = canvas.getUrlField().getText();
        httpResponse = network.getPage(currentUrl);
        rawHTML = httpResponse.getHtml();
        rawCSS = httpResponse.getCss();


        engine.renderPage(rawHTML, rawCSS);
        //todo
        //layout
        //painting

    }

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
                <body>
                    <h1>Title H1</h1>
                    
                    <p>This is a <b>bold bold</b> text in a paragraph shitty.
                    <img width="100" height="100" src="cool.png">
                    <img width="100" height="100" src="cool.png">
                    <img width="100" height="100" src="cool.png">
                    </p>
                    
                </body>
                """;

        String cssInput = """
                html, body, div, p, h1, h2, h3, img {
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
        CustomCanvas canvas = new CustomCanvas(rootBox);

        JFrame frame = new JFrame("Canvas");
        frame.setLayout(new BorderLayout());
        frame.add(canvas);
        frame.setSize(1800, 1200);
        frame.setVisible(true);

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