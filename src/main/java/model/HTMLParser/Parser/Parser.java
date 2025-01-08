package model.htmlParser.parser;

import model.htmlParser.tokenizer.Tokenizer;
import model.htmlParser.tokenizer.tokens.CharacterToken;
import model.htmlParser.tokenizer.tokens.CommentToken;
import model.htmlParser.tokenizer.tokens.DoctypeToken;
import model.htmlParser.tokenizer.tokens.EndOfFileToken;
import model.htmlParser.tokenizer.tokens.TagToken;

import java.util.Map;

public class Parser {
    private final Tokenizer tokenizer;

    public Parser(String input) {
        this.tokenizer = new Tokenizer(this, input);
        tokenizer.tokenize();
    }

    public void onCharacterToken(CharacterToken characterToken) {
        System.out.println(characterToken.getData());
    }

    public void onTagToken(TagToken tagToken) {
        System.out.println(tagToken.isEndToken() ? "endTag" : "openTag");
        System.out.println("isSelfClosing: " + tagToken.isSelfClosing());
        System.out.println("<" + (tagToken.isEndToken() ? "/" : "")+ tagToken.getTagName() + ">");

        for (Map.Entry<String, String> entry : tagToken.getAttributes().entrySet()) {
            System.out.println("attrName: " + entry.getKey());
            System.out.println("value: " + entry.getValue());
        }
    }

    public void onEndOfFileToken(EndOfFileToken endOfFileToken) {
        System.out.println("eof");
    }

    public void onCommentToken(CommentToken currentCommentToken) {
        System.out.println("Comment: " + currentCommentToken.getData());
    }

    public void onDoctypeToken(DoctypeToken doctypeToken) {
        System.out.println("Doctype");
        System.out.println("name" + doctypeToken.getName());
        System.out.println("pubID" + doctypeToken.getPublicIdentifier());
        System.out.println("sysID" + doctypeToken.getSystemIdentifier());
    }
}
