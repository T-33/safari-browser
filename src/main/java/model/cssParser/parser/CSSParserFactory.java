package model.cssParser.parser;

import model.cssParser.parser.dom.CSSDomFactory;
import model.cssParser.tokenizer.CSSTokenFactory;

public final class CSSParserFactory {
    private final CSSDomFactory cssDomFactory;
    private final CSSTokenFactory tokenFactory;

    public CSSParserFactory(CSSDomFactory cssDomFactory, CSSTokenFactory tokenFactory) {
        this.cssDomFactory = cssDomFactory;
        this.tokenFactory = tokenFactory;
    }

    public CSSParser createParser(String cssInput) {
        return new CSSParser(cssInput, cssDomFactory, tokenFactory);
    }
}