package model.HTMLParser.Tokenizer.States;

import model.HTMLParser.Tokenizer.Tokenizer;
import model.HTMLParser.Tokenizer.Tokens.TagToken;

import java.util.HashMap;

public class MarkupDeclarationOpenState implements TokenizerState {

    private static MarkupDeclarationOpenState INSTANCE;

    private MarkupDeclarationOpenState() {
    }

    public static MarkupDeclarationOpenState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MarkupDeclarationOpenState();
        }

        return INSTANCE;
    }

    @Override
    public void handleChar(Tokenizer tokenizer, char c) {
        if (tokenizer.startsWith("--")) {
            tokenizer.skipCharacter();
            tokenizer.skipCharacter();
            tokenizer.createCommentToken();
            tokenizer.setState(CommentStartState.getInstance());
        } else if (tokenizer.startsWithCaseInsensitive("doctype")) {
            //skips word "doctype"
            // todo is this^^^^ comment useful^^^?
            for (int i = 0; i < "doctype".length(); i++) {
                tokenizer.skipCharacter();
                tokenizer.setState(DoctypeState.getInstance());
            }
        } else {
            tokenizer.createCommentToken();
            tokenizer.setState(BogusCommentState.getInstance());
            //todo guide says not to consume anything
        }
    }
}
