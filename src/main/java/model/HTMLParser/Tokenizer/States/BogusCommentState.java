package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class BogusCommentState implements TokenizerState {

    private static BogusCommentState INSTANCE;

    private BogusCommentState() {
    }

    public static BogusCommentState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BogusCommentState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.isEndOfFile()) {
            tokenizer.emitCurrentCommentToken();
            tokenizer.emitEndOfFileToken();
        } else if (c == '>') {
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentCommentToken();
        } else {
            tokenizer.getCurrentCommentToken().appendData(c);
        }
    }
}

