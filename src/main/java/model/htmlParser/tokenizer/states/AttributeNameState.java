package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class AttributeNameState implements TokenizerState {

    private static AttributeNameState INSTANCE;

    private AttributeNameState() {
    }

    public static AttributeNameState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AttributeNameState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        boolean isAfterAttributeName =
                c == '\t' || c == '\r' || c == '/' || c == '>' ||
                c == '\f' || c == '\n' || Character.isWhitespace(c);

        boolean isAlphabeticUppercase = String.valueOf(c).matches("^[A-Z]+$");

        if (isAfterAttributeName || tokenizer.isEndOfFile()) {
            tokenizer.setState(AfterAttributeNameState.getInstance());
            tokenizer.reconsume();
        } else if(c == '=') {
            tokenizer.setState(BeforeAttributeValueState.getInstance());
        } else if (isAlphabeticUppercase) {
            tokenizer.getCurrentTagToken().appendAttributeName(Character.toLowerCase(c));
        } else {
            tokenizer.getCurrentTagToken().appendAttributeName(c);
        }
    }
}
