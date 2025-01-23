package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class AttributeValueSingleQuotedState implements TokenizerState {

    private static AttributeValueSingleQuotedState INSTANCE;
    private static final String ATTRIBUTE_VALUE = "Appended attribute value c: ";

    private AttributeValueSingleQuotedState() {
    }

    public static AttributeValueSingleQuotedState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AttributeValueSingleQuotedState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.isEndOfFile()) {
            tokenizer.emitEndOfFileToken();
        } else if (c == '\'') {
            tokenizer.setState(AfterAttributeValueState.getInstance());
        } else {
            tokenizer.getCurrentTagToken().appendAttributeValue(c);
            System.out.println(ATTRIBUTE_VALUE + c);
        }
    }
}
