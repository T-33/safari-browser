package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

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
                c == '\t' || c == '\r' || c == '/' || c == '>' || c == '\f' || c == '\n' || Character.isWhitespace(c);

        if (isAfterAttributeName || tokenizer.isEndOfFile()) {
            tokenizer.setState(AfterAtt);
        } else if(c == '=') {
            tokenizer.setState(BeforeAttributeValueState.getInstance());
        } else if (String.valueOf(c).matches("^[A-Z]+$")) {
            //todo maybe find proper Character::method for uppercase chars
            tokenizer.getCurrentTagToken().appendAttributeName(Character.toLowerCase(c));
        } else {
            tokenizer.getCurrentTagToken().appendAttributeName(c);
        }
    }
}
