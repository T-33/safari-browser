package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class CommentState implements TokenizerState {

    private static CommentState INSTANCE;

    private CommentState() {
    }

    public static CommentState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommentState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.isEndOfFile()) {
            tokenizer.emitCurrentCommentToken();
            tokenizer.emitEndOfFileToken();
        } else if (c == '-') {
            tokenizer.setState(CommentEndDashState.getInstance());
        } else if (c == '<') {
            tokenizer.getCurrentCommentToken().appendData(c);
            tokenizer.setState(CommentLessThanSignState.getInstance());
        } else {
            tokenizer.getCurrentCommentToken().appendData(c);
        }
    }
}


