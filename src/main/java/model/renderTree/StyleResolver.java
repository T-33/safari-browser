package model.renderTree;

import model.htmlParser.parser.dom.DomDocument;
import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomNode;
import model.cssParser.parser.dom.StyleSheet;
import model.cssParser.parser.dom.CSSRule;
import model.cssParser.parser.dom.Selector;
import model.cssParser.parser.dom.Declaration;

import java.util.ArrayList;
import java.util.List;

public final class StyleResolver {
    private static final String ATTRIBUTE_PROPERTY = "class";
    private static final String SELECTOR_START = ".";

    public StyleResolver() {}

    public static void applyStyles(DomDocument doc, StyleSheet styleSheet) {
        List<DomElement> elements = new ArrayList<>();
        collectElements(doc, elements);

        for (DomElement el : elements) {
            for (CSSRule rule : styleSheet.getRules()) {
                for (Selector sel : rule.getSelectors()) {
                    if (matches(el, sel)) {
                        for (Declaration decl : rule.getDeclarations()) {
                            el.setStyleProperty(decl.property(), decl.value());
                        }
                    }
                }
            }
        }
    }

    private static void collectElements(DomNode node, List<DomElement> out) {
        if (node instanceof DomElement el) {
            out.add(el);
        }
        for (DomNode child : node.getChildren()) {
            collectElements(child, out);
        }
    }

    private static boolean matches(DomElement el, Selector sel) {
        String selectorText = sel.value().trim();
        if (selectorText.equalsIgnoreCase(el.getTagName())) {
            return true;
        }
        if (selectorText.startsWith(SELECTOR_START)) {
            String cssClass = selectorText.substring(1);
            String classAttr = el.getAttributes().get(ATTRIBUTE_PROPERTY);
            if (classAttr != null) {
                String[] classes = classAttr.split("\\s+");
                for (String c : classes) {
                    if (cssClass.equalsIgnoreCase(c)) {
                        return true;
                    }
                }
            }
            if (classAttr == null || classAttr.isEmpty()) {
                return false;
            }

        }
        return false;
    }
}