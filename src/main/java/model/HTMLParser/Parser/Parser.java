package model.htmlParser.parser;

import model.htmlParser.parser.dom.DomComment;
import model.htmlParser.parser.dom.DomDocument;
import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomText;
import model.htmlParser.tokenizer.Tokenizer;
import model.htmlParser.tokenizer.tokens.CharacterToken;
import model.htmlParser.tokenizer.tokens.CommentToken;
import model.htmlParser.tokenizer.tokens.DoctypeToken;
import model.htmlParser.tokenizer.tokens.EndOfFileToken;
import model.htmlParser.tokenizer.tokens.TagToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Parser {

    private final Tokenizer tokenizer;
    private final DomDocument domDocument;
    private final Stack<DomElement> elementStack;
    private final StringBuilder textBuffer;

    public Parser(String input) {
        this.domDocument = new DomDocument();
        this.elementStack = new Stack<>();
        this.textBuffer = new StringBuilder();

        this.tokenizer = new Tokenizer(this, input);
        this.tokenizer.tokenize();
    }

    /**
     * Возвращаем результат парсинга (DomDocument).
     */
    public DomDocument getDomDocument() {
        return domDocument;
    }

    /**
     * Вызывается из токенайзера для каждого символа текста.
     */
    public void onCharacterToken(CharacterToken characterToken) {
        textBuffer.append(characterToken.getData());
    }

    /**
     * Вызывается из токенайзера, когда встречается TagToken.
     */
    public void onTagToken(TagToken tagToken) {
        if (!tagToken.isEndToken()) {
            if (ParserUtils.isVoidElement(tagToken.getTagName())) {
                tagToken.setSelfClosing(true);
            }
        }

        flushTextBuffer();
        if (tagToken.isEndToken()) {
            handleEndTag(tagToken);
        } else {
            handleStartOrSelfClosingTag(tagToken);
        }
    }

    public void onCommentToken(CommentToken commentToken) {
        flushTextBuffer();

        System.out.println("Comment token: " + commentToken.getData());

        DomComment commentNode = new DomComment(commentToken.getData());
        if (elementStack.isEmpty()) {
            domDocument.addChild(commentNode);
        } else {
            elementStack.peek().addChild(commentNode);
        }
    }

    public void onDoctypeToken(DoctypeToken doctypeToken) {
        flushTextBuffer();

        domDocument.setDoctype(
                doctypeToken.getName(),
                doctypeToken.getPublicIdentifier(),
                doctypeToken.getSystemIdentifier()
        );

        System.out.println("Doctype token: " + doctypeToken.getName()
                + ", publicId=" + doctypeToken.getPublicIdentifier()
                + ", systemId=" + doctypeToken.getSystemIdentifier());
    }

    /**
     * Вызывается из токенайзера, когда достигнут конец файла (EOF).
     */
    public void onEndOfFileToken(EndOfFileToken endOfFileToken) {
        flushTextBuffer();
        System.out.println("Parsing finished (EOF).");
    }

    /**
     * Обрабатываем открывающий/самозакрывающийся тег.
     */
    private void handleStartOrSelfClosingTag(TagToken tagToken) {
        DomElement element = new DomElement(tagToken.getTagName());
        copyAttributes(tagToken, element);

        if (elementStack.isEmpty()) {
            domDocument.addChild(element);
        } else {
            elementStack.peek().addChild(element);
        }

        if (!tagToken.isSelfClosing()) {
            elementStack.push(element);
        }
    }

    /**
     * Обрабатываем закрывающий тег
     */
    private void handleEndTag(TagToken tagToken) {
        if (elementStack.isEmpty()) {
            return;
        }

        DomElement topElement = elementStack.peek();
        if (topElement.getTagName().equalsIgnoreCase(tagToken.getTagName())) {
            elementStack.pop();
        } else {
            elementStack.pop();
        }
    }

    /**
     * Сбрасываем накопленный в textBuffer текст, создаём DomText
     */
    private void flushTextBuffer() {
        if (textBuffer.isEmpty()) {
            return;
        }
        String text = textBuffer.toString();
        textBuffer.setLength(0);

        if (text.trim().isEmpty()) {
            return;
        }

        DomText textNode = new DomText(text);
        if (elementStack.isEmpty()) {
            domDocument.addChild(textNode);
        } else {
            elementStack.peek().addChild(textNode);
        }
    }

    /**
     * Копируем атрибуты из TagToken в DomElement, убирая пустые ключи и т. д.
     */
    private void copyAttributes(TagToken tagToken, DomElement element) {
        Map<String, String> rawAttrs = tagToken.getAttributes();
        if (rawAttrs == null || rawAttrs.isEmpty()) {
            return;
        }
        Map<String, String> filtered = new HashMap<>();
        for (Map.Entry<String, String> e : rawAttrs.entrySet()) {
            String name = e.getKey() != null ? e.getKey().trim() : "";
            String value = e.getValue() != null ? e.getValue().trim() : "";
            if (!name.isEmpty()) {
                filtered.put(name, value);
            }
        }
        element.setAttributes(filtered);
    }
}