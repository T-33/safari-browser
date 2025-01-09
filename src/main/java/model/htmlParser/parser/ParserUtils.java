package model.htmlParser.parser;

import java.util.Set;

public class ParserUtils {

    private static final Set<String> VOID_ELEMENTS = Set.of(
            ParserConstants.AREA,
            ParserConstants.BASE,
            ParserConstants.BR,
            ParserConstants.COL,
            ParserConstants.EMBED,
            ParserConstants.HR,
            ParserConstants.IMG,
            ParserConstants.INPUT,
            ParserConstants.LINK,
            ParserConstants.META,
            ParserConstants.PARAM,
            ParserConstants.SOURCE,
            ParserConstants.TRACK,
            ParserConstants.WBR
    );

    public static boolean isVoidElement(String tagName) {
        return VOID_ELEMENTS.contains(tagName.toLowerCase());
    }
}
