package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class SelfClosingStartTagState implements TokenizerState {

    private static SelfClosingStartTagState INSTANCE;

    private SelfClosingStartTagState() {
    }

    public static SelfClosingStartTagState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SelfClosingStartTagState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.isEndOfFile()) {
            tokenizer.emitEndOfFileToken();
        }else if (c == '>') {
            tokenizer.getCurrentTagToken().setSelfClosing(true);
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentTagToken();
        } else {
            tokenizer.setState(BeforeAttributeNameState.getInstance());
            tokenizer.reconsume();
        }
    }
}

