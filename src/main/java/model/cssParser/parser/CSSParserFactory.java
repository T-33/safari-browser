package model.cssParser.parser;

import model.cssParser.parser.dom.CSSOMFactory;
import model.cssParser.tokenizer.CSSTokenFactory;

public final class CSSParserFactory {
    private final CSSOMFactory CSSOMFactory;
    private final CSSTokenFactory tokenFactory;

    public CSSParserFactory(CSSOMFactory CSSOMFactory, CSSTokenFactory tokenFactory) {
        this.CSSOMFactory = CSSOMFactory;
        this.tokenFactory = tokenFactory;
    }

    public CSSParser createParser(String cssInput) {
        return new CSSParser(cssInput, CSSOMFactory, tokenFactory);
    }
}