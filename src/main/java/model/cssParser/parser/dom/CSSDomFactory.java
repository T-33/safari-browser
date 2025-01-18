package model.cssParser.parser.dom;

public class CSSDomFactory {
    public StyleSheet createStyleSheet() {
        return new StyleSheet();
    }

    public CSSRule createRule() {
        return new CSSRule();
    }

    public Selector createSelector(String selectorText) {
        return new Selector(selectorText);
    }

    public Declaration createDeclaration(String property, String value) {
        return new Declaration(property, value);
    }
}