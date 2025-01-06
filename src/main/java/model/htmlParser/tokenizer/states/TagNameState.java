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

        if (tokenizer.isEndOfFile()) {
            tokenizer.emitCharacterToken('<');
        } else if (isEscapeOrWhiteSpaceCharacter) {
            tokenizer.setState(BeforeAttributeNameState.getInstance());
            System.out.println("set to before attribute name state " + c);
        } else if (c == '>') {
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentTagToken();
        } else if (String.valueOf(c).matches("^[A-Z]+$")) {
            tokenizer.getCurrentTagToken().appendCharName(Character.toLowerCase(c));
        } else {
            tokenizer.getCurrentTagToken().appendCharName(c);
        }
    }
}
