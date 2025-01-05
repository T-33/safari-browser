package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class CommentEndState implements TokenizerState {

    private static CommentEndState INSTANCE;

    private CommentEndState() {
    }

    public static CommentEndState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommentEndState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.isEndOfFile()) {
            tokenizer.emitCurrentCommentToken();
            tokenizer.emitEndOfFileToken();
        } else if (c == '!') {
            tokenizer.setState(CommentEndBangState.getInstance());
        } else if (c == '-') {
            tokenizer.getCurrentCommentToken().appendData('-');
        } else if (c == '>') {
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentCommentToken();
        } else {
            tokenizer.getCurrentCommentToken().appendData('-');
            tokenizer.getCurrentCommentToken().appendData('-');
            tokenizer.setState(CommentState.getInstance());
            tokenizer.reconsume();
        }
    }
}


