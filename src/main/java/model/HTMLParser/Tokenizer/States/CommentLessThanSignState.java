package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class CommentLessThanSignState implements TokenizerState {

    private static CommentLessThanSignState INSTANCE;

    private CommentLessThanSignState() {
    }

    public static CommentLessThanSignState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommentLessThanSignState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (c == '!') {
            tokenizer.getCurrentCommentToken().appendData('!');
            tokenizer.setState(CommentLessThanSignBangState.getInstance());
        } else if (c == '<') {
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentCommentToken();
        } else {
            tokenizer.setState(CommentState.getInstance());
            tokenizer.reconsume();
        }
    }
}


