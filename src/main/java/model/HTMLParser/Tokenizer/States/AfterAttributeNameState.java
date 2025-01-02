package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class AfterAttributeNameState implements TokenizerState {

    private static AfterAttributeNameState INSTANCE;

    private AfterAttributeNameState() {
    }

    public static AfterAttributeNameState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AfterAttributeNameState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        boolean isIgnored =
                c == '\t' || c == '\r' || c == '\f' || c == '\n' || Character.isWhitespace(c);

        if (!isIgnored) {
            if (tokenizer.isEndOfFile()) {
                tokenizer.emitEndOfFileToken();
            } else if (c == '=') {
                tokenizer.setState(BeforeAttributeValueState.getInstance());
            } else if (c == '/') {
                tokenizer.setState(SelfClosingStartTagState.getInstance());
            } else if (c == '>') {
                tokenizer.setState(DataState.getInstance());
                tokenizer.emitCurrentTagToken();
            } else {
                tokenizer.getCurrentTagToken().startNewAttribute();
                tokenizer.setState(AttributeNameState.getInstance());
            }
        }
    }
}

