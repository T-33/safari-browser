package model.htmlParser;

import model.htmlParser.parser.Parser;
import model.htmlParser.parser.dom.DomDocument;
import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomNode;
import model.htmlParser.parser.dom.DomText;
//TODO setState(enum States) -> ......................

public class Main {
    public static void main(String[] args) {
        String html = """
        <!DOCTYPE html>
        <html>
        <head>
            <title>Example of html-page</title>
        </head>
        <body>
            <div class="container">
                <span>Hello World!</span>
                <img src="logo.png"/>
            </div>
            <div>
                <p>Text <b>bold</b></p>
                <input type="text" placeholder="Some text..."/>
            </div>
        </body>
        </html>
        """;

        Parser parser = new Parser(html);
        DomDocument doc = parser.getDomDocument();

        printDomTree(doc, 0);
    }

    private static void printDomTree(DomNode node, int indent) {
        String prefix = " ".repeat(indent * 2);
        if (node instanceof DomElement e) {
            System.out.println(prefix + "Element: <" + e.getTagName() + "> attributes=" + e.getAttributes());
        } else if (node instanceof DomText t) {
            System.out.println(prefix + "Text: \"" + t.getText() + "\"");
        } else if (node instanceof DomDocument) {
            System.out.println(prefix + "Document");
        }

        for (DomNode child : node.getChildren()) {
            printDomTree(child, indent + 1);
        }
    }
}