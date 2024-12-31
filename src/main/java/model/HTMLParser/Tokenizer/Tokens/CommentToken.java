package model.HTMLParser.Tokenizer.Tokens;

public class CommentToken {
    private final String data;

    public CommentToken(String data) {
        this.data =  data;
    }

    public String getData() {
        return data;
    }
}
