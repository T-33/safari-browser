package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class AfterAttributeValueState implements TokenizerState {

    private static AfterAttributeValueState INSTANCE;

    private AfterAttributeValueState() {
    }

    public static AfterAttributeValueState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AfterAttributeValueState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        boolean isBeforeAttributeName =
                c == '\t' || c == '\r' || c == '\f' || c == '\n' || Character.isWhitespace(c);

        if (tokenizer.isEndOfFile()) {
            tokenizer.emitEndOfFileToken();
        } else if (isBeforeAttributeName) {
            tokenizer.setState(BeforeAttributeNameState.getInstance());
        } else if (c == '>') {
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentTagToken();
        } else {
            //missing whitespace between attributes
            tokenizer.setState(BeforeAttributeNameState.getInstance());
            tokenizer.reconsume();
        }
    }
}
