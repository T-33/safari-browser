package model;

import model.cssParser.parser.CSSParserFactory;
import model.cssParser.parser.dom.CSSOMFactory;
import model.cssParser.tokenizer.CSSTokenFactory;
import model.htmlParser.parser.ParserFactory;
import model.layoutengine.LayoutEngine;
import model.renderTree.RenderTreeBuilderFactory;
import model.renderTree.StyleResolver;
import model.renderTree.dom.RenderNodeFactory;

public class EngineFactory {
    public static Engine createEngine() {
        RenderNodeFactory renderNodeFactory = new RenderNodeFactory();
        CSSOMFactory CSSOMFactory = new CSSOMFactory();
        CSSTokenFactory cssTokenFactory = new CSSTokenFactory();
        LayoutEngine layoutEngine = new LayoutEngine();

        ParserFactory parserFactory = new ParserFactory();
        CSSParserFactory cssParserFactory = new CSSParserFactory(CSSOMFactory, cssTokenFactory);
        RenderTreeBuilderFactory builderFactory = new RenderTreeBuilderFactory(renderNodeFactory);
        StyleResolver styleResolver = new StyleResolver();

        return new Engine(parserFactory, cssParserFactory, builderFactory, styleResolver, layoutEngine);
    }
}