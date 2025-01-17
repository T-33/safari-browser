package model.cssParser.parser.dom;

import java.util.ArrayList;
import java.util.List;

public class CSSRule {
    private final List<Selector> selectors = new ArrayList<>();
    private final List<Declaration> declarations = new ArrayList<>();

    public List<Selector> getSelectors() {
        return selectors;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }
}
