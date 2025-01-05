package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class CommentEndBangState implements TokenizerState {

    private static CommentEndBangState INSTANCE;

    private CommentEndBangState() {
    }

    public static CommentEndBangState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommentEndBangState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.isEndOfFile()) {
            tokenizer.emitCurrentCommentToken();
            tokenizer.emitEndOfFileToken();
        } else if (c == '-') {
            tokenizer.getCurrentCommentToken().appendData('-');
            tokenizer.getCurrentCommentToken().appendData('-');
            tokenizer.getCurrentCommentToken().appendData('!');
            tokenizer.setState(CommentEndDashState.getInstance());
        } else if (c == '>') {
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentCommentToken();
        } else {
            tokenizer.getCurrentCommentToken().appendData('-');
            tokenizer.getCurrentCommentToken().appendData('-');
            tokenizer.getCurrentCommentToken().appendData('!');
            tokenizer.setState(CommentState.getInstance());
            tokenizer.reconsume();
        }
    }
}


