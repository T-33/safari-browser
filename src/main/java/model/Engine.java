package model;

import model.baseproperties.BaseProperties;
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

public final class Engine {
    private final ParserFactory parserFactory;
    private final CSSParserFactory cssParserFactory;
    private final RenderTreeBuilderFactory builderFactory;
    private final StyleResolver styleResolver;
    private final LayoutEngine layoutEngine;

    public Engine(
            ParserFactory parserFactory,
            CSSParserFactory cssParserFactory,
            RenderTreeBuilderFactory builderFactory,
            StyleResolver styleResolver, LayoutEngine layoutEngine
    ) {
        this.parserFactory = parserFactory;
        this.cssParserFactory = cssParserFactory;
        this.builderFactory = builderFactory;
        this.styleResolver = styleResolver;
        this.layoutEngine = new LayoutEngine();
    }

    public LayoutBox renderPage(String htmlInput, String cssInput, view.Canvas canvas) {
        if (canvas == null) {
            return null;
        }

        Parser htmlParser = parserFactory.createParser(htmlInput);
        DomDocument doc = htmlParser.getDomDocument();

        //connect default css with fetched to get rid of incorrect display properties
        CSSParser cssParser = cssParserFactory.createParser(cssInput + " " + BaseProperties.DEFAULT_CSS);
        StyleSheet styleSheet = cssParser.getStyleSheet();

        StyleResolver.applyStyles(doc, styleSheet);

        RenderTreeBuilder builder = builderFactory.createBuilder();
        RenderNode root = builder.build(doc);

        LayoutBox rootBox = LayoutEngine.buildLayoutTree(root);
        rootBox.layout(null);

        root.render();

        if (canvas == null) {
            return null;
        }

        canvas.drawPage(rootBox);
        return rootBox;
    }
}