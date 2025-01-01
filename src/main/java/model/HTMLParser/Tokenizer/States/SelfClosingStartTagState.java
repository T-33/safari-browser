package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;
import model.HTMLParser.Tokenizer.Tokens.TagToken;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class SelfClosingStartTagState implements TokenizerState {

    private static SelfClosingStartTagState INSTANCE;

    private SelfClosingStartTagState() {
    }

    public static SelfClosingStartTagState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SelfClosingStartTagState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.isEndOfFile()) {
            tokenizer.emitEndOfFileToken();
        }else if (c == '>') {
            tokenizer.getCurrentTagToken().setSelfClosing(true);
            tokenizer.setState(DataState.getInstance());
            tokenizer.emitCurrentTagToken();
        } else {
            tokenizer.setState(BeforeAttributeNameState.getInstance());
            tokenizer.reconsume();
        }
    }
}

