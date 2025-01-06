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
        boolean isIgnored =
                c == '\t' || c == '\r' || c == '\f' || c == '\n' || Character.isWhitespace(c);

        if (tokenizer.isEndOfFile()) {
            //todo???????????????????????????????
        } else if (isIgnored) {
            return;
        } else if (c == '\'') {
            tokenizer.setState(AttributeValueSingleQuotedState.getInstance());
        } else if (c == '\"') {
            tokenizer.setState(AttributeValueDoubleQuotedState.getInstance());
        } else if (c == '>') {
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentTagToken();
        }
    }
}

