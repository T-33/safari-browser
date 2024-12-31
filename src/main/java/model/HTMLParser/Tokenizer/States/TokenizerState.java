package model.HTMLParser.Tokenizer.States;

import Tokenizer.Tokenizer;

public interface TokenizerState {
    void handleChar(Tokenizer tokenizer, char c);
}
