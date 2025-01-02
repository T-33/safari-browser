package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class AttributeValueUnquotedState implements TokenizerState {

    private static AttributeValueUnquotedState INSTANCE;

    private AttributeValueUnquotedState() {}

    public static AttributeValueUnquotedState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AttributeValueUnquotedState();
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
            tokenizer.getCurrentTagToken().appendAttributeValue(c);
        }
    }
}
