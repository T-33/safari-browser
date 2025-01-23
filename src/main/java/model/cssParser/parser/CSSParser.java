package model.cssParser.parser;

import model.cssParser.parser.dom.CSSDomFactory;
import model.cssParser.parser.dom.StyleSheet;
import model.cssParser.parser.dom.CSSRule;
import model.cssParser.parser.dom.Declaration;

import model.cssParser.tokenizer.CSSLexer;
import model.cssParser.tokenizer.CSSToken;
import model.cssParser.tokenizer.CSSTokenFactory;
import model.cssParser.tokenizer.CSSTokenType;

import java.util.ArrayList;
import java.util.List;

public final class CSSParser {
    private final CSSDomFactory domFactory;
    private final CSSTokenFactory tokenFactory;
    private final String input;
    private List<CSSToken> tokens;
    private int currentIndex = 0;
    private StyleSheet styleSheet;

    public CSSParser(String cssInput, CSSDomFactory domFactory, CSSTokenFactory tokenFactory) {
        this.input = (cssInput == null) ? "" : cssInput;
        this.domFactory = domFactory;
        this.tokenFactory = tokenFactory;
        tokenize();
        parse();
    }

    public StyleSheet getStyleSheet() {
        return styleSheet;
    }

    private void tokenize() {
        CSSLexer lexer = new CSSLexer(input, tokenFactory);
        tokens = lexer.getTokens();
    }

    private void parse() {
        styleSheet = domFactory.createStyleSheet();
        while (!isEnd()) {
            skipUseless();
            if (peekType() == CSSTokenType.AT_KEYWORD) {
                consume();
                parseAtRule();
                continue;
            }

            if (isEnd()) break;
            int oldIndex = currentIndex;

            CSSRule rule = parseRule();
            if (rule != null) {
                styleSheet.getRules().add(rule);
            }
            if (currentIndex == oldIndex) {
                consume();
            }
        }
    }

    private CSSRule parseRule() {
        CSSRule rule = domFactory.createRule();
        parseSelectors(rule);
        skipUseless();
        if (match(CSSTokenType.OPEN_CURLY)) {
            parseDeclarations(rule);
            match(CSSTokenType.CLOSE_CURLY);
        }
        return rule;
    }

    private void parseAtRule() {
        while (!isEnd()) {
            if (peekType() == CSSTokenType.OPEN_CURLY) {
                consume();
                skipBlock();
                break;
            }
            consume();
        }
    }

    private void skipBlock() {
        int depth = 1;
        while (!isEnd() && depth > 0) {
            if (peekType() == CSSTokenType.OPEN_CURLY) {
                depth++;
            } else if (peekType() == CSSTokenType.CLOSE_CURLY) {
                depth--;
            }
            consume();
        }
    }


    private void parseSelectors(CSSRule rule) {
        List<String> selParts = new ArrayList<>();
        while (!isEnd()) {
            skipUseless();
            CSSTokenType t = peekType();
            if (t == CSSTokenType.OPEN_CURLY || t == CSSTokenType.EOF) {
                break;
            }
            if (t == CSSTokenType.COMMA) {
                consume();
                addSelectorToRule(rule, selParts);
                selParts.clear();
                continue;
            }
            if (t == CSSTokenType.SEMICOLON || t == CSSTokenType.CLOSE_CURLY) {
                break;
            }
            selParts.add(consume().value());
        }
        addSelectorToRule(rule, selParts);
    }

    private void parseDeclarations(CSSRule rule) {
        while (!isEnd()) {
            skipUseless();
            if (peekType() == CSSTokenType.CLOSE_CURLY || peekType() == CSSTokenType.EOF) {
                break;
            }

            String property = parsePropertyName();
            skipUseless();
            match(CSSTokenType.COLON);
            skipUseless();

            String value = parseValue();
            Declaration decl = domFactory.createDeclaration(property, value);
            rule.getDeclarations().add(decl);

            skipUseless();
            match(CSSTokenType.SEMICOLON);
        }
    }

    private String parsePropertyName() {
        StringBuilder sb = new StringBuilder();
        while (!isEnd()) {
            CSSTokenType t = peekType();
            if (t == CSSTokenType.COLON || t == CSSTokenType.SEMICOLON ||
                    t == CSSTokenType.OPEN_CURLY || t == CSSTokenType.CLOSE_CURLY || t == CSSTokenType.EOF) {
                break;
            }
            sb.append(consume().value());
        }
        return sb.toString().trim();
    }

    private String parseValue() {
        StringBuilder sb = new StringBuilder();
        while (!isEnd()) {
            CSSTokenType t = peekType();
            if (t == CSSTokenType.SEMICOLON || t == CSSTokenType.CLOSE_CURLY || t == CSSTokenType.EOF) {
                break;
            }
            sb.append(consume().value()).append(" ");
        }
        return sb.toString().trim();
    }

    private void addSelectorToRule(CSSRule rule, List<String> selParts) {
        if (selParts.isEmpty()) {
            return;
        }
        String joined = String.join("", selParts).trim();
        if (!joined.isEmpty()) {
            rule.getSelectors().add(domFactory.createSelector(joined));
        }
    }

    private void skipUseless() {
        while (!isEnd()) {
            CSSTokenType t = peekType();
            if (t == CSSTokenType.WHITESPACE ||
                    t == CSSTokenType.CDO ||
                    t == CSSTokenType.CDC ||
                    t == CSSTokenType.BAD_STRING ||
                    t == CSSTokenType.BAD_URL) {
                consume();
            } else {
                break;
            }
        }
    }

    private CSSToken consume() {
        if (isEnd()) {
            return tokenFactory.create(CSSTokenType.EOF, "");
        }
        return tokens.get(currentIndex++);
    }

    private CSSTokenType peekType() {
        if (isEnd()) {
            return CSSTokenType.EOF;
        }
        return tokens.get(currentIndex).type();
    }

    private boolean match(CSSTokenType type) {
        if (!isEnd() && peekType() == type) {
            consume();
            return true;
        }
        return false;
    }

    private boolean isEnd() {
        return currentIndex >= tokens.size() ||
                tokens.get(currentIndex).type() == CSSTokenType.EOF;
    }
}