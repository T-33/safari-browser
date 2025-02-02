package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class BeforeAttributeNameState implements TokenizerState {

    private static BeforeAttributeNameState INSTANCE;

    private BeforeAttributeNameState() {
    }

    public static BeforeAttributeNameState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BeforeAttributeNameState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        boolean isIgnored =
                c == '\t' || c == '\r' || c == '\f' || c == '\n' || Character.isWhitespace(c);

        boolean isAfterAttributeName =
                tokenizer.isEndOfFile() || c == '/' || c == '>';

        if (!isIgnored) {
            if (isAfterAttributeName) {
                tokenizer.setState(AfterAttributeNameState.getInstance());
                tokenizer.reconsume();
            } else if (c == '=') {
                tokenizer.getCurrentTagToken().startNewAttribute();
                tokenizer.getCurrentTagToken().appendAttributeName(c);
                tokenizer.setState(AttributeNameState.getInstance());
            } else {
                tokenizer.getCurrentTagToken().startNewAttribute();
                tokenizer.setState(AttributeNameState.getInstance());
                tokenizer.reconsume();
            }
        }
    }
}
