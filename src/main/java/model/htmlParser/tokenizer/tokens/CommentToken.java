package model.htmlParser.tokenizer.tokens;

/**
 * Token representing HTML comment.
 */
public class CommentToken {
    private final StringBuilder data;

    /**
     * Creates new comment token with specified data.
     * @param data - comment's data, cannot be set upon creation, only appended.
     */
    public CommentToken(String data) {
        this.data =  new StringBuilder(data);
    }

    /**
     * Appends specified char to the end of comment's data.
     * @param c - appended char.
     */
    public void appendData(char c) {
        data.append(c);
    }

    /**
     * Returns current comment's data.
     * @return comment's data.
     */
    public String getData() {
        return data.toString();
    }
}
