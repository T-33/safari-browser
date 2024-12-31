package model.HTMLParser.Tokenizer.States;

import Tokenizer.Tokenizer;

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

        if (tokenizer.isEndOfFile()) {
            //todo
        } else if (!isIgnored) {
            tokenizer.getCurrentTagToken().startNewAttribute();
            tokenizer.setState(AttributeNameState.getInstance());
            tokenizer.reconsume();
        }
    }
}
