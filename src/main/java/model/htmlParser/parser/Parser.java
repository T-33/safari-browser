package model.htmlParser.parser;

import model.htmlParser.parser.dom.DomDocument;
import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomText;
import model.htmlParser.tokenizer.Tokenizer;
import model.htmlParser.tokenizer.tokens.CharacterToken;
import model.htmlParser.tokenizer.tokens.EndOfFileToken;
import model.htmlParser.tokenizer.tokens.TagToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Главный парсер, который опирается на токенайзер и
 * формирует DomDocument (DOM-дерево).
 * <p>
 * Принцип работы (упрощённо):
 *  1) При открывающем теге создаём DomElement, добавляем его к текущему контейнеру
 *     и (если тег не самозакрывающий) помещаем на стек.
 *  2) При текстовом токене создаём DomText и добавляем его к текущему элементу (вершина стека).
 *  3) При закрывающем теге снимаем элемент со стека.
 *  4) По завершении возвращаем DomDocument.
 */
public class Parser {

    private final Tokenizer tokenizer;
    private final DomDocument domDocument;
    private final Stack<DomElement> elementStack;

    /**
     * Временный буфер для накопления текстовых символов.
     */
    private final StringBuilder textBuffer;

    /**
     * @param input - HTML-строка для парсинга
     */
    public Parser(String input) {
        this.domDocument = new DomDocument();
        this.elementStack = new Stack<>();

        this.textBuffer = new StringBuilder();

        this.tokenizer = new Tokenizer(this, input);
        tokenizer.tokenize();
    }

    public DomDocument getDomDocument() {
        return domDocument;
    }

    /**
     * Обработчик CharacterToken, вызывается из Tokenizer
     */
    public void onCharacterToken(CharacterToken characterToken) {
        textBuffer.append(characterToken.getData());
    }

    /**
     * Обработчик TagToken, вызывается из Tokenizer
     */
    public void onTagToken(TagToken tagToken) {
        flushTextBuffer();

        if (tagToken.isEndToken()) {
            handleEndTag(tagToken);
        } else {
            handleStartOrSelfClosingTag(tagToken);
        }
    }

    /**
     * Обработчик EndOfFileToken, вызывается из Tokenizer
     */
    public void onEndOfFileToken(EndOfFileToken endOfFileToken) {
        flushTextBuffer();
        System.out.println("Parsing finished (EOF).");
    }

    /**
     * Обрабатываем открывающий или самозакрывающийся тег.
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

    private void handleEndTag(TagToken tagToken) {
        if (!elementStack.isEmpty()) {
            DomElement topElement = elementStack.peek();
            if (topElement.getTagName().equalsIgnoreCase(tagToken.getTagName())) {
                elementStack.pop();
            } else {
                elementStack.pop();
            }
        }
    }

    /**
     * Сбрасывает накопленный текст из textBuffer одним DomText в текущий контейнер
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

    private void copyAttributes(TagToken tagToken, DomElement element) {
        Map<String, String> rawAttrs = tagToken.getAttributes();
        if (rawAttrs == null || rawAttrs.isEmpty()) {
            return;
        }
        Map<String, String> filteredAttrs = new HashMap<>();

        for (Map.Entry<String, String> entry : rawAttrs.entrySet()) {
            String attrName = entry.getKey() != null ? entry.getKey().trim() : "";
            String attrValue = entry.getValue() != null ? entry.getValue().trim() : "";
            if (!attrName.isEmpty()) {
                filteredAttrs.put(attrName, attrValue);
            }
        }
        element.setAttributes(filteredAttrs);
    }
}
