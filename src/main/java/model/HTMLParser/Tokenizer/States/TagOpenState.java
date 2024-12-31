package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;
import model.HTMLParser.Tokenizer.Tokens.TagToken;

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
        } else if (Character.isAlphabetic(c)) {

            tokenizer.createTagToken(new TagToken("", false, new HashMap<>()));
            tokenizer.setState(TagNameState.getInstance());
            tokenizer.reconsume();

        } else {
            tokenizer.emitCharacterToken(c);
        }
    }
}
