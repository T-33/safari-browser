package model.htmlParser.tokenizer.tokens;

public class CommentToken {
    private final String data;

    public CommentToken(String data) {
        this.data =  data;
    }

    public String getData() {
        return data;
    }
}
