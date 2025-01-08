package model.htmlParser;

import model.htmlParser.parser.Parser;
import model.htmlParser.tokenizer.Tokenizer;
//TODO setState(enum States) -> ......................

public class Main {
    public static void main(String[] args) {
        String textBlock = """
                <!DOCTYPE html>
                       <html lang="en">
                       <head>
                           <meta charset="UTF-8">
                           <meta name="viewport" content="width=device-width, initial-scale=1.0">
                           <title>Tokenizer Test Case</title>
                           <link rel="stylesheet" href="styles.css">
                       </head>
                       <body>
                           <header>
                               <h1>Welcome to the Tokenizer Test</h1>
                               <nav>
                                   <ul>
                                       <li><a href="#section1">Section 1</a></li>
                                       <li><a href="#section2">Section 2</a></li>
                                   </ul>
                               </nav>
                           </header>
                
                           <main>
                               <section id="section1">
                                   <h2>Section 1</h2>
                                   <p>This is a paragraph with <strong>bold</strong> and <em>italic</em> text.</p>
                                   <img src="image.jpg" alt="Sample Image" width="300" height="200">
                               </section>
                
                               <section id="section2">
                                   <h2>Section 2</h2>
                                   <p>Here is a list:</p>
                                   <ul>
                                       <li>Item 1</li>
                                       <li>Item 2</li>
                                       <li>Item 3</li>
                                   </ul>
                               </section>
                           </main>
                
                           <footer>
                               <p>Copyright 2023 Tokenizer Test. All rights reserved.</p>
                           </footer>
                       </body>
                       </html>
         """;

        Parser parser = new Parser(textBlock);
    }
}