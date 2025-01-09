package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class CommentStartState implements TokenizerState {

    private static CommentStartState INSTANCE;

    private CommentStartState() {
    }

    public static CommentStartState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommentStartState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (c == '-') {
            tokenizer.setState(CommentStartDashState.getInstance());
        } else if (c == '>') {
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentCommentToken();
        } else {
            tokenizer.setState(CommentState.getInstance());
            tokenizer.reconsume();
        }
    }
}


