package model.HTMLParser.Tokenizer;

import model.HTMLParser.Parser.Parser;
import model.HTMLParser.Tokenizer.States.DataState;
import model.HTMLParser.Tokenizer.States.TokenizerState;
import model.HTMLParser.Tokenizer.Tokens.CharacterToken;
import model.HTMLParser.Tokenizer.Tokens.EndOfFileToken;
import model.HTMLParser.Tokenizer.Tokens.TagToken;

public class Tokenizer {
    private final Parser parser;
    private final String input;
    private int position;

    private TokenizerState state;

    private TagToken currentTagToken;

    public Tokenizer(Parser parser, String input) {
        this.parser = parser;
        this.input = input;
        this.position = 0;
        this.state = DataState.getInstance();
    }

    public void tokenize() {
        while (true) {
            if(position >= input.length()) {
                parser.onEndOfFileToken(new EndOfFileToken());
                break;
            }

            state.handleChar(this, input.charAt(position));
            position += 1;
        }
    }

    public void setState(TokenizerState state) {
        this.state = state;
    }

    public void reconsume() {
        position -= 1;
    }

    public void createTagToken(TagToken tagToken) {
        this.currentTagToken = tagToken;
    }

    public TagToken getCurrentTagToken() {
        return currentTagToken;
    }

    public void emitCharacterToken(char c) {
        CharacterToken characterToken = new CharacterToken(String.valueOf(c));
        parser.onCharacterToken(characterToken);
    }

    public void emitCurrentTagToken() {
        currentTagToken.appendNewAttribute();
        parser.onTagToken(currentTagToken);
    }

    public void emitEndOfFileToken() {
        parser.onEndOfFileToken(new EndOfFileToken());
    }

    public boolean isEndOfFile() {
        return position == input.length();
    }


}
