package deltix.luminary.jetbrains;

import com.intellij.codeInsight.daemon.JavaExpectedHighlightingData;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import deltix.luminary.grammar.LuminaryLexer;
import deltix.luminary.grammar.LuminaryParser;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * A highlighter is really just a mapping from token type to
 * some text attributes using {@link #getTokenHighlights(IElementType)}.
 * The reason that it returns an array, TextAttributesKey[], is
 * that you might want to mix the attributes of a few known highlighters.
 * A {@link TextAttributesKey} is just a name for that a theme
 * or IDE skin can set. For example, {@link com.intellij.openapi.editor.DefaultLanguageHighlighterColors#KEYWORD}
 * is the key that maps to what identifiers look like in the editor.
 * To change it, see dialog: Editor > Colors & Fonts > Language Defaults.
 * <p>
 * From <a href="http://www.jetbrains.org/intellij/sdk/docs/reference_guide/custom_language_support/syntax_highlighting_and_error_highlighting.html">doc</a>:
 * "The mapping of the TextAttributesKey to specific attributes used
 * in an editor is defined by the EditorColorsScheme class, and can
 * be configured by the user if the plugin provides an appropriate
 * configuration interface.
 * ...
 * The syntax highlighter returns the {@link TextAttributesKey}
 * instances for each token type which needs special highlighting.
 * For highlighting lexer errors, the standard TextAttributesKey
 * for bad characters HighlighterColors.BAD_CHARACTER can be used."
 */
public class LuminarySyntaxHighlighter extends SyntaxHighlighterBase {
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
    public static final TextAttributesKey ID =
            createTextAttributesKey("SAMPLE_ID", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey("SAMPLE_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey STRING =
            createTextAttributesKey("SAMPLE_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey LINE_COMMENT =
            createTextAttributesKey("SAMPLE_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BLOCK_COMMENT =
            createTextAttributesKey("SAMPLE_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey DOC_COMMENT =
            createTextAttributesKey("SAMPLE_DOC_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT);
    public static final TextAttributesKey NUMBER =
            createTextAttributesKey("LUMINARY_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey TYPE =
            createTextAttributesKey("LUMINARY_TYPE", DefaultLanguageHighlighterColors.CLASS_NAME);

    static {
        PSIElementTypeFactory.defineLanguageIElementTypes(Luminary.INSTANCE,
                LuminaryParser.tokenNames,
                LuminaryParser.ruleNames);
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        LuminaryLexer lexer = new LuminaryLexer(null);
        return new ANTLRLexerAdaptor(Luminary.INSTANCE, lexer);
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (!(tokenType instanceof TokenIElementType)) return EMPTY_KEYS;
        TokenIElementType myType = (TokenIElementType) tokenType;
        int ttype = myType.getANTLRTokenType();
        TextAttributesKey attrKey;
        switch (ttype) {
            case LuminaryLexer.AS:
            case LuminaryLexer.CLASS:
            case LuminaryLexer.CONST:
            case LuminaryLexer.DECORATOR:
            case LuminaryLexer.ENUM:
            case LuminaryLexer.FALSE:
            case LuminaryLexer.GET:
            case LuminaryLexer.IMPORT:
            case LuminaryLexer.INTERFACE:
//            case LuminaryLexer.LIST_TYPE:
            case LuminaryLexer.NAMESPACE:
            case LuminaryLexer.NULL:
            case LuminaryLexer.OPTION:
            case LuminaryLexer.OVERRIDE:
            case LuminaryLexer.SET:
//            case LuminaryLexer.SIMPLE_TYPE:
            case LuminaryLexer.TRUE:
            case LuminaryLexer.TYPEOF:
            case LuminaryLexer.VOID:
            case LuminaryLexer.FINAL:
                attrKey = KEYWORD;
                break;
            case LuminaryLexer.CHAR_LITERAL:
            case LuminaryLexer.STRING_LITERAL:
                attrKey = STRING;
                break;
            case LuminaryLexer.INTEGRAL_LITERAL:
            case LuminaryLexer.BINARY_FLOATING_POINT_LITERAL:
            case LuminaryLexer.DECIMAL_FLOATING_POINT_LITERAL:
                attrKey = NUMBER;
                break;
            case LuminaryLexer.SINGLE_LINE_COMMENT:
                attrKey = LINE_COMMENT;
                break;
            case LuminaryLexer.MULTI_LINE_COMMENT:
                attrKey = BLOCK_COMMENT;
                break;
            case LuminaryLexer.DOCUMENTATION_COMMENTS:
                attrKey = DOC_COMMENT;
                break;
            default:
                return EMPTY_KEYS;
        }
        return new TextAttributesKey[]{attrKey};
    }
}
