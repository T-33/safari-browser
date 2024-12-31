package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class AfterAttributeValueQuotedState implements TokenizerState {

    private static AfterAttributeValueQuotedState INSTANCE;

    private AfterAttributeValueQuotedState() {}

    public static AfterAttributeValueQuotedState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AfterAttributeValueQuotedState();
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
