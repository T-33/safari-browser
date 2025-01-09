package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class CommentLessThanSignBangDashState implements TokenizerState {

    private static CommentLessThanSignBangDashState INSTANCE;

    private CommentLessThanSignBangDashState() {
    }

    public static CommentLessThanSignBangDashState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommentLessThanSignBangDashState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (c == '-') {
            tokenizer.setState(CommentLessThanSignBangDashDashState.getInstance());
        } else {
            tokenizer.setState(CommentEndDashState.getInstance());
            tokenizer.reconsume();
        }
    }
}


