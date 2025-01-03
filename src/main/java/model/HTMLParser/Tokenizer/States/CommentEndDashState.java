package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class CommentEndDashState implements TokenizerState {

    private static CommentEndDashState INSTANCE;

    private CommentEndDashState() {
    }

    public static CommentEndDashState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommentEndDashState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.isEndOfFile()) {
            tokenizer.emitCurrentCommentToken();
            tokenizer.emitEndOfFileToken();
        } else if (c == '-') {
            tokenizer.setState(CommentEndState.getInstance());
        } else {
            tokenizer.getCurrentCommentToken().appendData('-');
            tokenizer.setState(CommentState.getInstance());
            tokenizer.reconsume();
        }
    }
}


