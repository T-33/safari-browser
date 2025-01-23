package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class DoctypeNameState implements TokenizerState {

    private static DoctypeNameState INSTANCE;

    private DoctypeNameState() {
    }

    public static DoctypeNameState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DoctypeNameState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        boolean isAfterDoctypeName =
                c == '\t' || c == '\r' || c == '\f' || c == '\n' || Character.isWhitespace(c);

        boolean isAlphabeticUppercase = String.valueOf(c).matches("^[A-Z]+$");

        if (isAfterDoctypeName) {
            tokenizer.setState(AfterDoctypeNameState.getInstance());
        } else if (isAlphabeticUppercase) {
            tokenizer.getCurrentDoctypeToken().appendName(Character.toLowerCase(c));
        } else if (tokenizer.isEndOfFile()) {
            tokenizer.createDoctypeToken();
            tokenizer.getCurrentDoctypeToken().setForceQuirksFlag(true);
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentDoctypeToken();
        } else if (c == '>') {
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentDoctypeToken();
        } else {
            tokenizer.getCurrentDoctypeToken().appendName(c);
        }
    }
}



