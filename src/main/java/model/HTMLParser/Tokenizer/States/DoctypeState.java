package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class DoctypeState implements TokenizerState {

    private static DoctypeState INSTANCE;

    private DoctypeState() {
    }

    public static DoctypeState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DoctypeState();
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
        } else {
            tokenizer.setState(AttributeValueUnquotedState.getInstance());
            tokenizer.reconsume();
        }
    }
}


