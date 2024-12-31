package model.HTMLParser.Tokenizer.States;

import Tokenizer.Tokenizer;

public class DataState implements  TokenizerState{

    private static DataState INSTANCE;

    private DataState(){}

    public static DataState getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DataState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if(c == '<') {
            tokenizer.setState(TagOpenState.getInstance());
        } else {
            tokenizer.emitCharacterToken(c);
        }
    }
}
