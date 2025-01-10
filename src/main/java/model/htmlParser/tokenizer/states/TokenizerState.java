package model.htmlParser.tokenizer.states;

import model.htmlParser.tokenizer.Tokenizer;

/**
 * Each tokenizer state implements this interface.
 *
 * I didn't consider writing specification for each state needed,
 * because I was implementing algorithm from official HTML specification
 * without any significant changes.
 * Read more about <a href="https://html.spec.whatwg.org/multipage/parsing.html#tokenization">algorithm </a>.
 */
public interface TokenizerState {
    void handleChar(Tokenizer tokenizer, char c);
}
