package model.htmlParser.tokenizer;

import model.htmlParser.tokenizer.tokens.CharacterToken;
import model.htmlParser.tokenizer.tokens.CommentToken;
import model.htmlParser.tokenizer.tokens.DoctypeToken;
import model.htmlParser.tokenizer.tokens.EndOfFileToken;
import model.htmlParser.tokenizer.tokens.TagToken;

import model.htmlParser.parser.Parser;

import model.htmlParser.tokenizer.states.DataState;
import model.htmlParser.tokenizer.states.TokenizerState;

import java.util.HashMap;

/**
 * Produces tokens from html input.
 * Tokens are saved in tokenizer until they are ready to be emitted.
 * Then they are given to parser to be handled and inserted into syntax tree.
 *
 * Tokenizer class is based on <a href="https://html.spec.whatwg.org/multipage/parsing.html#tokenization">algorithm </a> from HTML specification.
 *
 * Tokenizer is a state machine. Initial state - Data {@link model.htmlParser.tokenizer.states.DataState}.
 * Each state implements TokenizerState interface {@link model.htmlParser.tokenizer.states.TokenizerState};
 *
 * # Example Usage
 * <pre>
 *     {@code
 * Parser parser = new Parser(input);
 * tokenizer = new Tokenizer(parser, input);
 * tokenizer.tokenize();
 * }
 * </pre>
 *
 */
public class Tokenizer {
    private final Parser parser;

    private String input;
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

    /**
     * Start tokenizing process.
     */
    public void tokenize() {
        while (true) {
            if(position >= input.length()) {
                break;
            }

            state.handleChar(this, input.charAt(position));
            position += 1;
        }
    }

    /**
     * Changes current tokenizer state to specified state.
     * @param state - new tokenizer state.
     */
    public void setState(TokenizerState state) {
        this.state = state;
    }

    /**
     * Checks if document at current position starts with specified string, case-sensitive.
     *
     * <p>
     *     Example usage
     * </p>
     * <pre>{@code
     *
     * "doctype......." - current tokenizer position
     * startsWith("DOCTYPE");
     * startsWith("doctype");  => only this returns true;
     * startsWith("DoCTYPE");
     *
     *  }</pre>
     * @param string - checked string.
     * @return true if document at current position starts with specified string, case-insensitive.
     */
    public boolean startsWith(String string) {
        return input.substring(position).startsWith(string);
    }

    /**
     * Checks if document at current position starts with specified string, case-insensitive.
     *
     * <p>
     *     Example usage
     * </p>
     * <pre>{@code
     *
     * "dOcTyPe......." - current tokenizer position
     * startsWith("DOCTYPE");
     * startsWith("doctype");  => all return true;
     * startsWith("DoCTYPE");
     *
     *  }</pre>
     * @param string - checked string.
     * @return true if document at current position starts with specified string, case-insensitive.
     */
    public boolean startsWithCaseInsensitive(String string) {
        String lowerCaseInput = input.substring(position).toLowerCase();
        return lowerCaseInput.startsWith(string.toLowerCase());
    }

    /**
     * Moves tokenizer position one char forward.
     */
    public void skipCharacter() {
        position += 1;
    }

    /**
     * Reconsumes current character.
     */
    public void reconsume() {
        position -= 1;
    }

    /**
     * Creates new tag token with empty tag name, self-closing and end token flags set to false and empty attributes list.
     */
    public void createTagToken() {
        currentTagToken = new TagToken("", false, false, new HashMap<>());
    }

    /**
     * Creates new empty comment token.
     */
    public void createCommentToken() {
        currentCommentToken = new CommentToken("");
    }

    /**
     * Creates new Doctype token with empty doctype name, with empty public identifier, system identifier and forceQuirksFlag set off.
     */
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

    /**
     * Creates new character token for specified character and handles token to parser.
     * @param c - token of that char would be handled by parser.
     */
    public void emitCharacterToken(char c) {
        CharacterToken characterToken = new CharacterToken(String.valueOf(c));
        parser.onCharacterToken(characterToken);
    }

    /**
     * Handles current tag token to parser.
     */
    public void emitCurrentTagToken() {
        currentTagToken.appendNewAttribute();
        parser.onTagToken(currentTagToken);
    }

    /**
     * Handles current comment token to parser.
     */
    public void emitCurrentCommentToken() {
        parser.onCommentToken(currentCommentToken);
    }

    /**
     * Handles current doctype token to parser.
     */
    public void emitCurrentDoctypeToken() {
        parser.onDoctypeToken(currentDoctypeToken);
    }

    /**
     * Handles end of file token to parser.
     * Signalising that tokeniser reached end of document.
     */
    public void emitEndOfFileToken() {
        parser.onEndOfFileToken(new EndOfFileToken());
    }

    public boolean isEndOfFile() {
        return position == input.length();
    }
}
