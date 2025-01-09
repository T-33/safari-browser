package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class DoctypeState implements TokenizerState {

    private static DoctypeState INSTANCE;

    private DoctypeState() {
    }

    public static DoctypeState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DoctypeState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        boolean isBeforeDoctype =
                c == '\t' || c == '\r' || c == '\f' || c == '\n' || Character.isWhitespace(c);

        if (tokenizer.isEndOfFile()) {
            tokenizer.createDoctypeToken();
            tokenizer.getCurrentDoctypeToken().setForceQuirksFlag(true);
            tokenizer.emitCurrentDoctypeToken();
            tokenizer.emitEndOfFileToken();
        } else if (isBeforeDoctype) {
            tokenizer.setState(BeforeDoctypeNameState.getInstance());
        } else {
            tokenizer.setState(BeforeDoctypeNameState.getInstance());
            tokenizer.reconsume();
        }
    }
}


