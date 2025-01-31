package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class CommentLessThanSignBangDashDashState implements TokenizerState {

    private static CommentLessThanSignBangDashDashState INSTANCE;

    private CommentLessThanSignBangDashDashState() {
    }

    public static CommentLessThanSignBangDashDashState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommentLessThanSignBangDashDashState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        tokenizer.setState(CommentEndState.getInstance());
        tokenizer.reconsume();
    }
}


