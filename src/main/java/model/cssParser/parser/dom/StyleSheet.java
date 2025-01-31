package model.cssParser.parser.dom;

import java.util.ArrayList;
import java.util.List;

public class StyleSheet {
    private final List<CSSRule> rules = new ArrayList<>();

    public List<CSSRule> getRules() {
        return rules;
    }
}