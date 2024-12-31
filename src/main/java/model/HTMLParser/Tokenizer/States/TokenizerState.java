package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;

public interface TokenizerState {
    void handleChar(Tokenizer tokenizer, char c);
}
