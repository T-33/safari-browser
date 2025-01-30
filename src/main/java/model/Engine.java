package model;

import model.cssParser.parser.CSSParserFactory;
import model.htmlParser.parser.Parser;
import model.htmlParser.parser.ParserFactory;
import model.htmlParser.parser.dom.DomDocument;
import model.cssParser.parser.dom.StyleSheet;
import model.cssParser.parser.CSSParser;
import model.layoutengine.LayoutEngine;
import model.layoutengine.layoutboxes.LayoutBox;
import model.renderTree.RenderTreeBuilder;
import model.renderTree.RenderTreeBuilderFactory;
import model.renderTree.StyleResolver;
import model.renderTree.dom.RenderNode;

import javax.swing.*;
import java.awt.*;

public final class Engine {
    private final ParserFactory parserFactory;
    private final CSSParserFactory cssParserFactory;
    private final RenderTreeBuilderFactory builderFactory;
    private final StyleResolver styleResolver;

    public Engine(
            ParserFactory parserFactory,
            CSSParserFactory cssParserFactory,
            RenderTreeBuilderFactory builderFactory,
            StyleResolver styleResolver
    ) {
        this.parserFactory = parserFactory;
        this.cssParserFactory = cssParserFactory;
        this.builderFactory = builderFactory;
        this.styleResolver = styleResolver;
    }

    public void renderPage(String htmlInput, String cssInput) {
        Parser htmlParser = parserFactory.createParser(htmlInput);
        DomDocument doc = htmlParser.getDomDocument();

        CSSParser cssParser = cssParserFactory.createParser(cssInput);
        StyleSheet styleSheet = cssParser.getStyleSheet();

        StyleResolver.applyStyles(doc, styleSheet);

        RenderTreeBuilder builder = builderFactory.createBuilder();
        RenderNode root = builder.build(doc);

        LayoutBox rootBox = LayoutEngine.buildLayoutTree(root);
        rootBox.layout(null);

        root.render();
        CustomCanvas canvas = new CustomCanvas(rootBox);

        JFrame frame = new JFrame("Canvas");
        frame.setLayout(new BorderLayout());
        frame.add(canvas);
        frame.setSize(1800, 1200);
        frame.setVisible(true);
    }

}