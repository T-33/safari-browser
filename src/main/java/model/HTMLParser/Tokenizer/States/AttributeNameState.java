package model.HTMLParser.Tokenizer.States;

import Tokenizer.Tokenizer;

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
        System.out.println(c);
        if (tokenizer.isEndOfFile()) {
            //todo work
        } else if (String.valueOf(c).matches("^[A-Z]+$")) {
            //todo maybe find proper Character::method for uppercase chars
            tokenizer.getCurrentTagToken().appendAttributeName(Character.toLowerCase(c));
        } else {
            tokenizer.getCurrentTagToken().appendAttributeName(c);
        }
    }
}
