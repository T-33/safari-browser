package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class CommentLessThanSignBangState implements TokenizerState {

    private static CommentLessThanSignBangState INSTANCE;

    private CommentLessThanSignBangState() {
    }

    public static CommentLessThanSignBangState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommentLessThanSignBangState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (c == '-') {
            tokenizer.setState(CommentLessThanSignBangDashState.getInstance());
        } else {
            tokenizer.setState(CommentState.getInstance());
            tokenizer.reconsume();
        }
    }
}


