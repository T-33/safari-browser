package model.HTMLParser.Parser;

import model.HTMLParser.Tokenizer.Tokenizer;
import model.HTMLParser.Tokenizer.Tokens.CharacterToken;
import model.HTMLParser.Tokenizer.Tokens.CommentToken;
import model.HTMLParser.Tokenizer.Tokens.EndOfFileToken;
import model.HTMLParser.Tokenizer.Tokens.TagToken;

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
}
