package model.htmlParser;

import model.htmlParser.parser.Parser;
import model.htmlParser.parser.dom.DomComment;
import model.htmlParser.parser.dom.DomDocument;
import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomNode;
import model.htmlParser.parser.dom.DomText;
//TODO setState(enum States) -> ......................

public class Main {
    public static void main(String[] args) {
        String textBlock = """
                <!DOCTYPE html>
                       <html lang="en">
                       <head>
                           <meta charset="UTF-8">
                           <!-- This is a comment for testing -->
                           <title>Another Test Document</title>
                           <link rel="stylesheet" href="styles2.css">
                       </head>
                       <body>
                           <header>
                               <h1>Another Tokenizer Test</h1>
                               <hr>
                           </header>  \s
                           <main>
                               <!-- A comment before the table -->
                               <table border="1" data-custom="tableTest">
                                   <tr>
                                       <th>Header 1</th>
                                       <th>Header 2</th>
                                   </tr>
                                   <tr>
                                       <td>Cell 1.1</td>
                                       <td>Cell 1.2</td>
                                   </tr>
                                   <tr>
                                       <td>Cell 2.1</td>
                                       <td>Cell 2.2</td>
                                   </tr>
                               </table>
               \s
                               <p>This is some <b>bold text</b> and a <br> line break.</p>

                               <img src="example.png" alt="Example image" width="400" height="300">
               \s
                               <p>End of <i>content</i>.</p>
                           </main>
               \s
                           <footer>
                               <p>&copy; 2023 Another Test. All rights reserved.</p>
                           </footer>
                       </body>
                       </html>
        \s""";

        Parser parser = new Parser(textBlock);
        DomDocument doc = parser.getDomDocument();

        printDomTree(doc, 0);
    }

    private static void printDomTree(DomNode node, int indent) {
        String prefix = " ".repeat(indent * 2);
        if (node instanceof DomDocument doc) {
            System.out.println(prefix + "Document (doctype=" + doc.getDoctypeName() + ")");
        } else if (node instanceof DomElement el) {
            System.out.println(prefix + "Element: <" + el.getTagName() + "> attrs=" + el.getAttributes());
        } else if (node instanceof DomText textNode) {
            String t = textNode.getText().replaceAll("\\s+", " ").trim();
            System.out.println(prefix + "Text: \"" + t + "\"");
        } else if (node instanceof DomComment commentNode) {
            System.out.println(prefix + "Comment: \"" + commentNode.getComment() + "\"");
        }

        for (DomNode child : node.getChildren()) {
            printDomTree(child, indent + 1);
        }
    }
}