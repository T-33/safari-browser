package model.htmlParser.tokenizer;

import model.htmlParser.tokenizer.tokens.CharacterToken;
import model.htmlParser.tokenizer.tokens.CommentToken;
import model.htmlParser.tokenizer.tokens.DoctypeToken;
import model.htmlParser.parser.Parser;
import model.htmlParser.tokenizer.states.DataState;
import model.htmlParser.tokenizer.states.TokenizerState;
import model.htmlParser.tokenizer.tokens.EndOfFileToken;
import model.htmlParser.tokenizer.tokens.TagToken;

import java.util.HashMap;

//TODO Добавить тип onEndOfFileToken к состояниям чтобы реагировали . В текущем виде немного плачевно.

public class Tokenizer {
    private final Parser parser;
    private final String input;
    private int position;

    private TokenizerState state;

    private TagToken currentTagToken;
    private CommentToken currentCommentToken;
    private DoctypeToken currentDoctypeToken;

    public Tokenizer(Parser parser, String input) {
        this.parser = parser;
        this.input = input;
        this.position = 0;

        this.state = DataState.getInstance();
    }

    public void tokenize() {
        while (true) {
            if(position >= input.length()) {
                break;
            }

            state.handleChar(this, input.charAt(position));
            position += 1;
        }
    }

    public void setState(TokenizerState state) {
        this.state = state;
    }

    public boolean startsWith(String string) {
        return input.substring(position).startsWith(string);
    }

    public boolean startsWithCaseInsensitive(String string) {
        String lowerCaseInput = input.substring(position).toLowerCase();
        return lowerCaseInput.startsWith(string.toLowerCase());
    }

    /**
     * todo Questionable naming.
     * todo rework probably.
     */
    public void skipCharacter() {
        position += 1;
    }

    public void reconsume() {
        position -= 1;
    }

    /**
     * todo either hand parameters to create token inside method or change name to setToken
     */
    public void createTagToken() {
        currentTagToken = new TagToken("", false, false, new HashMap<>());
    }

    public void createCommentToken() {
        currentCommentToken = new CommentToken("");
    }

    public void createDoctypeToken() {
        currentDoctypeToken = new DoctypeToken("", "", "", false);
    }

    public TagToken getCurrentTagToken() {
        return currentTagToken;
    }

    public CommentToken getCurrentCommentToken() {
        return currentCommentToken;
    }

    public DoctypeToken getCurrentDoctypeToken() {
        return currentDoctypeToken;
    }

    public void emitCharacterToken(char c) {
        CharacterToken characterToken = new CharacterToken(String.valueOf(c));
        parser.onCharacterToken(characterToken);
    }

    public void emitCurrentTagToken() {
        currentTagToken.appendNewAttribute();
        parser.onTagToken(currentTagToken);
    }

    public void emitCurrentCommentToken() {
        parser.onCommentToken(currentCommentToken);
    }

    public void emitCurrentDoctypeToken() {
        parser.onDoctypeToken(currentDoctypeToken);
    }

    public void emitEndOfFileToken() {
        parser.onEndOfFileToken(new EndOfFileToken());
    }

    public boolean isEndOfFile() {
        return position == input.length();
    }
}
