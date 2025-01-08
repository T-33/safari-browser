package model.htmlParser.tokenizer.tokens;

public class CharacterToken {
    private final String data;

    public CharacterToken(String data) {
        this.data =  data;
    }

    public String getData() {
        return data;
    }
}
