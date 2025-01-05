package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public class AfterDoctypeNameState implements TokenizerState {

    private static AfterDoctypeNameState INSTANCE;

    private AfterDoctypeNameState() {
    }

    public static AfterDoctypeNameState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AfterDoctypeNameState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        boolean isIgnored =
                c == '\t' || c == '\r' || c == '\f' || c == '\n' || Character.isWhitespace(c);

        boolean isAlphabeticUppercase = String.valueOf(c).matches("^[A-Z]+$");

        if (!isIgnored) {
            if (tokenizer.isEndOfFile()) {

                tokenizer.getCurrentDoctypeToken().setForceQuirksFlag(true);
                tokenizer.setState(DataState.getInstance());
                tokenizer.emitCurrentDoctypeToken();
                tokenizer.emitEndOfFileToken();
            } else if (c == '>') {
                tokenizer.setState(DataState.getInstance());
                tokenizer.emitCurrentDoctypeToken();
            } else {
                tokenizer.getCurrentDoctypeToken().appendName(c);
            }
        }

        //todo public system etc
    }
}



