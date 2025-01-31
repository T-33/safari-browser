package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class BeforeAttributeValueState implements TokenizerState {

    private static BeforeAttributeValueState INSTANCE;

    private BeforeAttributeValueState() {
    }

    public static BeforeAttributeValueState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BeforeAttributeValueState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (c == '\'') {
            tokenizer.setState(AttributeValueSingleQuotedState.getInstance());
        } else if (c == '\"') {
            tokenizer.setState(AttributeValueDoubleQuotedState.getInstance());
        } else if (c == '>') {
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentTagToken();
        } else {
            tokenizer.setState(AttributeValueUnquotedState.getInstance());
            tokenizer.reconsume();
        }
    }
}

