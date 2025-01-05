package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class CommentStartDashState implements TokenizerState {

    private static CommentStartDashState INSTANCE;

    private CommentStartDashState() {
    }

    public static CommentStartDashState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommentStartDashState();
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
            tokenizer.getCurrentCommentToken().appendData('-');
            tokenizer.setState(CommentState.getInstance());
            tokenizer.reconsume();
        }
    }
}


