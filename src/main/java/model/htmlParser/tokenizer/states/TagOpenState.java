package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

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
        } else if (c == '!') {
            tokenizer.setState(MarkupDeclarationOpenState.getInstance());
        } else if (c == '/') {
            tokenizer.setState(EndTagOpenState.getInstance());
        } else if (Character.isAlphabetic(c)) {

            tokenizer.createTagToken();
            tokenizer.setState(TagNameState.getInstance());
            tokenizer.reconsume();

        } else {
            tokenizer.emitCharacterToken('<');
            tokenizer.setState(DataState.getInstance());
            tokenizer.reconsume();
        }
    }
}
