package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;
import model.htmlParser.tokenizer.tokens.TagToken;

import java.util.HashMap;

public class TagOpenState implements TokenizerState {

    private static TagOpenState INSTANCE;

    private TagOpenState() {
    }

    public static TagOpenState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TagOpenState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.isEndOfFile()) {
            tokenizer.emitCharacterToken('<');
        } else if (c == '/') {
            tokenizer.setState(EndTagOpenState.getInstance());
        } else if (Character.isAlphabetic(c)) {

            tokenizer.createTagToken(new TagToken("", false, false, new HashMap<>()));
            tokenizer.setState(TagNameState.getInstance());
            tokenizer.reconsume();

        } else {
            tokenizer.emitCharacterToken('<');
            tokenizer.setState(DataState.getInstance());
            tokenizer.reconsume();
        }
    }
}
