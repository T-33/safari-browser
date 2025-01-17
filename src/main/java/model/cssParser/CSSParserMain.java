package model.cssParser;

import model.cssParser.parser.dom.CSSDomFactory;
import model.cssParser.parser.dom.StyleSheet;
import model.cssParser.parser.dom.CSSRule;
import model.cssParser.parser.dom.Selector;
import model.cssParser.parser.dom.Declaration;
import model.cssParser.tokenizer.CSSTokenFactory;
import model.cssParser.parser.CSSParser;

public class CSSParserMain {
    public static void main(String[] args) {
        String css = """
                html, body {
                  margin: 0;
                  padding: 0;
                }
                    
                h1 {
                  color: #333;
                  display: block;
                }
                
                .myClass {
                  display: inline;
                  background: yellow;
                }
                
                b {
                  display: inline;
                  font-weight: bold;
                }
            """;

        CSSDomFactory domFactory = new CSSDomFactory();
        CSSTokenFactory tokenFactory = new CSSTokenFactory();

        CSSParser parser = new CSSParser(css, domFactory, tokenFactory);
        StyleSheet styleSheet = parser.getStyleSheet();

        for (CSSRule rule : styleSheet.getRules()) {
            System.out.println("Rule:");
            for (Selector sel : rule.getSelectors()) {
                System.out.println("  Selector: " + sel.value());
            }
            for (Declaration decl : rule.getDeclarations()) {
                System.out.println("  " + decl.property() + ": " + decl.value());
            }
        }
    }
}