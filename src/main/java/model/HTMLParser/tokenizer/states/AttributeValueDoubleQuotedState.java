package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class AttributeValueDoubleQuotedState implements TokenizerState {

    private static AttributeValueDoubleQuotedState INSTANCE;

    private AttributeValueDoubleQuotedState() {}

    public static AttributeValueDoubleQuotedState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AttributeValueDoubleQuotedState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.isEndOfFile()) {
            tokenizer.emitEndOfFileToken();
        } else if (c == '\"') {
            tokenizer.setState(AfterAttributeValueState.getInstance());
        } else {
            tokenizer.getCurrentTagToken().appendAttributeValue(c);
        }
    }
}
