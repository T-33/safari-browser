package model;

import model.cssParser.parser.CSSParserFactory;
import model.cssParser.parser.dom.CSSDomFactory;
import model.cssParser.tokenizer.CSSTokenFactory;
import model.htmlParser.parser.ParserFactory;
import model.renderTree.RenderTreeBuilderFactory;
import model.renderTree.StyleResolver;
import model.renderTree.dom.RenderNodeFactory;

public class EngineFactory {
    public static Engine createEngine() {
        RenderNodeFactory renderNodeFactory = new RenderNodeFactory();
        CSSDomFactory cssDomFactory = new CSSDomFactory();
        CSSTokenFactory cssTokenFactory = new CSSTokenFactory();

        ParserFactory parserFactory = new ParserFactory();
        CSSParserFactory cssParserFactory = new CSSParserFactory(cssDomFactory, cssTokenFactory);
        RenderTreeBuilderFactory builderFactory = new RenderTreeBuilderFactory(renderNodeFactory);
        StyleResolver styleResolver = new StyleResolver();

        return new Engine(parserFactory, cssParserFactory, builderFactory, styleResolver);
    }
}

