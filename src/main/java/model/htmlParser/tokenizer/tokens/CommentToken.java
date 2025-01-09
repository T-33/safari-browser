package model.htmlParser.tokenizer.tokens;

public class CommentToken {
    private final StringBuilder data;

    public CommentToken(String data) {
        this.data =  new StringBuilder(data);
    }

    public void appendData(char c) {
        data.append(c);
    }

    public String getData() {
        return data.toString();
    }
}
