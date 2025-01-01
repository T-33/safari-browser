package model.HTMLParser;

import model.HTMLParser.Parser.Parser;
//TODO setState(enum States) -> ......................

public class Main {
    public static void main(String[] args) {
        String textBlock = """
                <html>
                    <body>
                        <h1>Title</h1>
                        <div class="job">
                            <p>Hello <em>world</em>!</p>
                        </div>
                    </body>
                </html>
         """;

        Parser parser = new Parser("<h/>");
    }
}