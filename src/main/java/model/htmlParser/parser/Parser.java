package model.htmlParser.parser;

import model.htmlParser.parser.dom.DomComment;
import model.htmlParser.parser.dom.DomDocument;
import model.htmlParser.parser.dom.DomElement;
import model.htmlParser.parser.dom.DomNodeFactory;
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
    private final DomDocument document;
    private final Stack<DomElement> elementStack;
    private final StringBuilder textBuffer;
    private final DomNodeFactory domNodeFactory;

    private static final String EMPTY = "";

    public Parser(String input) {
        document = new DomDocument();
        elementStack = new Stack<>();
        textBuffer = new StringBuilder();
        domNodeFactory = new DomNodeFactory();
        tokenizer = new Tokenizer(this, input);
        tokenizer.tokenize();
    }

    public DomDocument getDomDocument() {
        return document;
    }

    public void onCharacterToken(CharacterToken token) {
        textBuffer.append(token.getData());
    }

    public void onTagToken(TagToken token) {
        if (!token.isEndToken() && ParserUtils.isVoidElement(token.getTagName())) {
            token.setSelfClosing(true);
        }
        flushText();
        if (token.isEndToken()) {
            handleEnd(token);
        } else {
            handleStartOrSelfClosing(token);
        }
    }

    public void onCommentToken(CommentToken token) {
        flushText();
        DomComment commentNode = domNodeFactory.createDomComment(token.getData());
        addNode(commentNode);
    }

    public void onDoctypeToken(DoctypeToken token) {
        flushText();
        document.setDoctype(token.getName(), token.getPublicIdentifier(), token.getSystemIdentifier());
    }

    public void onEndOfFileToken(EndOfFileToken token) {
        flushText();
    }

    private void handleStartOrSelfClosing(TagToken token) {
        DomElement element = domNodeFactory.createDomElement(token.getTagName());
        copyAttrs(token, element);
        addNode(element);
        if (!token.isSelfClosing()) {
            elementStack.push(element);
        }
    }

    private void handleEnd(TagToken token) {
        if (elementStack.isEmpty()) {
            return;
        }
        DomElement top = elementStack.peek();
        if (top.getTagName().equalsIgnoreCase(token.getTagName())) {
            elementStack.pop();
        } else {
            elementStack.pop();
        }
    }

    private void flushText() {
        if (textBuffer.isEmpty()) {
            return;
        }
        String text = textBuffer.toString();
        textBuffer.setLength(0);
        if (text.trim().isEmpty()) {
            return;
        }
        DomText textNode = domNodeFactory.createDomText(text);

        if (!elementStack.isEmpty()) {
            elementStack.peek().addChild(textNode);
        } else {
            document.addChild(textNode);
        }
    }

    private void copyAttrs(TagToken token, DomElement element) {
        Map<String, String> rawAttrs = token.getAttributes();
        if (rawAttrs == null || rawAttrs.isEmpty()) {
            return;
        }
        Map<String, String> filtered = new HashMap<>();
        for (Map.Entry<String, String> e : rawAttrs.entrySet()) {
            String name = (e.getKey() == null) ? EMPTY : e.getKey().trim();
            String value = (e.getValue() == null) ? EMPTY : e.getValue().trim();
            if (!name.isEmpty()) {
                filtered.put(name, value);
            }
        }
        element.setAttributes(filtered);
    }

    private void addNode(DomComment commentNode) {
        if (elementStack.isEmpty()) {
            document.addChild(commentNode);
            return;
        }
        elementStack.peek().addChild(commentNode);
    }

    private void addNode(DomElement element) {
        if (elementStack.isEmpty()) {
            document.addChild(element);
            return;
        }
        elementStack.peek().addChild(element);
    }
}
