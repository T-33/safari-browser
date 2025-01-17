package model.htmlParser;

import model.htmlParser.parser.Parser;
import model.htmlParser.parser.dom.DomDocument;
import model.renderTree.dom.RenderNode;
import model.renderTree.RenderTreeBuilder;
import model.renderTree.dom.RenderNodeFactory;
//TODO setState(enum States) -> ......................

public class HtmlParser {
    public static void main(String[] args) {
        String textBlock = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>Test Document</title>
            </head>
            <body>
                <h1>Title H1</h1>
                <p>This is a <b>bold</b> text in a paragraph.</p>
                <div class="myClass">This is a div with class "myClass"</div>
            </body>
            </html>
        \s""";

        Parser parser = new Parser(textBlock);
        DomDocument doc = parser.getDomDocument();

        RenderNodeFactory factory = new RenderNodeFactory();
        RenderTreeBuilder builder = new RenderTreeBuilder(factory);

        RenderNode root = builder.build(doc);

        if (root != null) {
            root.render();
        }
    }
}