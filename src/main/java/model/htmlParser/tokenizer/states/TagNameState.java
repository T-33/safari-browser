package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public class TagNameState implements TokenizerState {

    private static TagNameState INSTANCE;

    private TagNameState() {
    }

    public static TagNameState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TagNameState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        //todo double check
        boolean isEscapeOrWhiteSpaceCharacter =
               c == '\t' ||  c == '\r' || c == '\f' || c == '\n' || Character.isWhitespace(c);

        boolean isAlphabeticUppercase = String.valueOf(c).matches("^[A-Z]+$");

        if (tokenizer.isEndOfFile()) {
            tokenizer.emitCharacterToken('<');
        } else if (isEscapeOrWhiteSpaceCharacter) {
            tokenizer.setState(BeforeAttributeNameState.getInstance());
        } else if (c == '/') {
            tokenizer.setState(SelfClosingStartTagState.getInstance());
        } else if (c == '>') {
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentTagToken();
        } else if (isAlphabeticUppercase) {
            tokenizer.getCurrentTagToken().appendName(Character.toLowerCase(c));
        } else {
            tokenizer.getCurrentTagToken().appendName(c);
        }
    }
}
