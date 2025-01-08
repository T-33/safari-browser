package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

public interface TokenizerState {
    void handleChar(Tokenizer tokenizer, char c);
}
