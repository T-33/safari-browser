package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;
import model.HTMLParser.Tokenizer.Tokens.TagToken;

import java.util.HashMap;

public class EndTagOpenState implements TokenizerState {

    private static EndTagOpenState INSTANCE;

    private EndTagOpenState() {
    }

    public static EndTagOpenState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EndTagOpenState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.isEndOfFile()) {
            tokenizer.emitCharacterToken('<');
            tokenizer.emitCharacterToken('/');
            tokenizer.emitEndOfFileToken();
        } else if (Character.isAlphabetic(c)) {
            tokenizer.createTagToken();
            tokenizer.getCurrentTagToken().setIsEndToken(true);
            tokenizer.setState(TagNameState.getInstance());
            tokenizer.reconsume();
        } else if (c == '>') {
            tokenizer.setState(DataState.getInstance());
        } else {
            tokenizer.emitCharacterToken('<');
            tokenizer.setState(DataState.getInstance());
            tokenizer.reconsume();
        }
    }
}
