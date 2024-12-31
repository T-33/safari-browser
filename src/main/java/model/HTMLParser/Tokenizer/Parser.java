package model.HTMLParser.Tokenizer;

import Tokenizer.Tokenizer;
import Tokenizer.Tokens.CharacterToken;
import Tokenizer.Tokens.EndOfFileToken;
import Tokenizer.Tokens.TagToken;

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
