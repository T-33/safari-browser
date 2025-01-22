package model;

import model.cssParser.parser.CSSParserFactory;
import model.cssParser.parser.dom.CSSDomFactory;
import model.cssParser.tokenizer.CSSTokenFactory;
import model.htmlParser.parser.ParserFactory;
import model.renderTree.RenderTreeBuilderFactory;
import model.renderTree.StyleResolver;
import model.renderTree.dom.RenderNodeFactory;
import view.Canvas;

public class Main {
    private Canvas canvas;
    private static Engine engine;
    private static Network network;
    private static NavigationModel navigationModel;
    private String currentUrl;
    private String rawHTML;

    public Main(Canvas canvas){
        this.canvas = canvas;
        this.network = new Network();
        this.navigationModel = new NavigationModel();
        //todo
//        this.engine = new Engine();
    }



    public void loadPage(){
        currentUrl = canvas.getUrlField().getText();

        try {
            rawHTML = network.getPage(currentUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //todo
        //parser - render
        //layout
        //painting

    }
    public static void main(String[] args) {
        ParserFactory parserFactory = new ParserFactory();
        CSSDomFactory cssDomFactory = new CSSDomFactory();
        CSSTokenFactory tokenFactory = new CSSTokenFactory();
        CSSParserFactory cssParserFactory = new CSSParserFactory(cssDomFactory, tokenFactory);
        RenderNodeFactory renderNodeFactory = new RenderNodeFactory();
        RenderTreeBuilderFactory builderFactory = new RenderTreeBuilderFactory(renderNodeFactory);

        StyleResolver styleResolver = new StyleResolver();

        Engine engine = new Engine(parserFactory, cssParserFactory, builderFactory, styleResolver);

        String htmlInput = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Test Document</title>
                </head>
                <body>
                    <h1>Title H1</h1>
                    <p>This is a <b>bold</b> text in a paragraph.</p>
                    <div class="myClass">This is a div</div>
                </body>
                </html>
                """;

        String cssInput = """
                html, body {
                  margin: 0;
                  padding: 0;
                }
                h1 {
                  display: block;
                  color: #333;
                }
                .myClass {
                  display: inline;
                  background: yellow;
                }
                """;

        engine.renderPage(htmlInput, cssInput);
    }
}