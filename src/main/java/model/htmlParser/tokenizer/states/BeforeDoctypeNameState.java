package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class BeforeDoctypeNameState implements TokenizerState {

    private static BeforeDoctypeNameState INSTANCE;

    private BeforeDoctypeNameState() {
    }

    public static BeforeDoctypeNameState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BeforeDoctypeNameState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        boolean isIgnored =
                c == '\t' || c == '\r' || c == '\f' || c == '\n' || Character.isWhitespace(c);

        boolean isAfterAttributeName =
                tokenizer.isEndOfFile() || c == '/' || c == '>';

        boolean isAlphabeticUppercase = String.valueOf(c).matches("^[A-Z]+$");


        if (!isIgnored) {
            if (isAlphabeticUppercase) {
                tokenizer.createDoctypeToken();
                tokenizer.getCurrentDoctypeToken().appendName(Character.toLowerCase(c));
                tokenizer.setState(DoctypeNameState.getInstance());
            } else if (tokenizer.isEndOfFile()) {
                tokenizer.createDoctypeToken();
                tokenizer.getCurrentDoctypeToken().setForceQuirksFlag(true);
                tokenizer.setState(DataState.getInstance());
                tokenizer.emitCurrentDoctypeToken();
            } else {
                tokenizer.createDoctypeToken();
                tokenizer.getCurrentDoctypeToken().appendName(c);
                tokenizer.setState(DoctypeNameState.getInstance());
            }
        }
    }
}



