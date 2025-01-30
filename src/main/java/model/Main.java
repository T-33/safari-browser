package model;

import model.Network.Network;

import model.cssParser.parser.CSSParserFactory;
import model.cssParser.parser.dom.CSSOMFactory;
import model.cssParser.tokenizer.CSSTokenFactory;
import model.htmlParser.parser.ParserFactory;
import model.renderTree.RenderTreeBuilderFactory;
import model.renderTree.StyleResolver;
import model.renderTree.dom.RenderNodeFactory;
import view.Canvas;

public class Main {
    private final Canvas canvas;
    private final Engine engine;
    private final Network network;
//    private final EngineFactory engineFactory;

    public Main(Canvas canvas){
        this.canvas = canvas;
        network = new Network();
        engine = EngineFactory.createEngine();
    }

    public static void main(String[] args) {
        ParserFactory parserFactory = new ParserFactory();
        CSSOMFactory CSSOMFactory = new CSSOMFactory();
        CSSTokenFactory tokenFactory = new CSSTokenFactory();
        CSSParserFactory cssParserFactory = new CSSParserFactory(CSSOMFactory, tokenFactory);
        RenderNodeFactory renderNodeFactory = new RenderNodeFactory();
        RenderTreeBuilderFactory builderFactory = new RenderTreeBuilderFactory(renderNodeFactory);

        StyleResolver styleResolver = new StyleResolver();

        Engine engine = new Engine(parserFactory, cssParserFactory, builderFactory, styleResolver);
        String htmlInput = """
            <body>
                <h1 class="header">Styled Header</h1>
                <div class="content">
                    <p class="text">This is a paragraph with some <b>bold</b> text.</p>
                    <p class="text shadow">This is another paragraph with shadow text.</p>
                    <p class="text">This is a paragraph with transparency.</p>
                </div>
                <div class="content">
                    <p class="text">This is a paragraph with some <b>bold</b> text.</p>
                    <p class="text shadow">This is another paragraph with shadow text.</p>
                    <p class="text">This is a paragraph with transparency.</p>
                </div>
                <div class="content">
                    <p class="text">This is a paragraph with some <b>bold</b> text.</p>
                    <p class="text shadow">This is another paragraph with shadow text.</p>
                    <p class="text">This is a paragraph with transparency.</p>
                </div>
                <div class="box">
                    <p>Box content with padding and rounded corners.</p>
                </div>
                <a href="http://www.google.com">Наш сайт</a>
                <img width="200" height="150" class="rounded" src="https://i.pinimg.com/236x/71/1b/53/711b5384406f643d21f52e3bc1eeb391.jpg">
                <img width="150" height="150" class="shadowed" src="https://i.pinimg.com/236x/71/1b/53/711b5384406f643d21f52e3bc1eeb391.jpg">
                <img src="https://jcup.ru/images/zakat-v-webm.jpg">
            </body>
        """;

        String cssInput = """
            html, body, div, p, h1, h2, h3, img {
                display: block;
            }
            div {
                color: black;
                background-color: red;
            }
            p {
                font-size: 12px;
                color: blue;
                font-family: "Arial";
                font-weight: bold;
                font-style: italic;
                text-align: center;
            }
        """;

//       engine.renderPage(htmlInput, cssInput);
    }
}