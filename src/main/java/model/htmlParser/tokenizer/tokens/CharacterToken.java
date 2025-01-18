package model.htmlParser.tokenizer.tokens;

/**
 * Token representing singular character.
 */
public class CharacterToken {
    private final String data;

    /**
     * Creates new character token with specified data.
     * @param data - char, that token represents. Cannot be changed upon creation.
     */
    public CharacterToken(String data) {
        this.data =  data;
    }

    /**
     * Returns char, that token represents.
     * @return char that token represents.
     */
    public String getData() {
        return data;
    }
}
