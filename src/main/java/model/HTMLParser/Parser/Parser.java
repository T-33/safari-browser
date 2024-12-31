package model.HTMLParser.Parser;

import model.HTMLParser.Tokenizer.Tokenizer;
import model.HTMLParser.Tokenizer.Tokens.CharacterToken;
import model.HTMLParser.Tokenizer.Tokens.EndOfFileToken;
import model.HTMLParser.Tokenizer.Tokens.TagToken;

public class Parser {
    private final Tokenizer tokenizer;

    public Parser(String input) {
        this.tokenizer = new Tokenizer(this, input);
        tokenizer.tokenize();
    }

    public void onCharacterToken(CharacterToken characterToken) {
        System.out.println(characterToken.getData());
    }

    public void onOpenTagToken(TagToken tagToken) {
        System.out.println("<" + tagToken.getTagName() + ">");
    }

    public void onEndOfFileToken(EndOfFileToken endOfFileToken) {
        System.out.println("eof");
    }
}
