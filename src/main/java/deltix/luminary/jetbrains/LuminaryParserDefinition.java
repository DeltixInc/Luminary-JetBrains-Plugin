package deltix.luminary.jetbrains;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import deltix.luminary.grammar.LuminaryLexer;
import deltix.luminary.grammar.LuminaryParser;
import deltix.luminary.jetbrains.psi.*;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Daniil Yarmalkevich
 * Date: 10/8/2019
 */
public class LuminaryParserDefinition implements ParserDefinition {

    public static final IFileElementType FILE = new IFileElementType(Luminary.INSTANCE);

    public static TokenIElementType ID;

    static {
        PSIElementTypeFactory.defineLanguageIElementTypes(Luminary.INSTANCE,
                LuminaryParser.tokenNames,
                LuminaryParser.ruleNames);
        List<TokenIElementType> tokenIElementTypes =
                PSIElementTypeFactory.getTokenIElementTypes(Luminary.INSTANCE);
        ID = tokenIElementTypes.get(LuminaryLexer.IDENTIFIER);
    }

    public static final TokenSet WHITESPACE =
            PSIElementTypeFactory.createTokenSet(
                    Luminary.INSTANCE,
                    LuminaryLexer.WHITESPACE,
                    LuminaryLexer.NEW_LINE);

    public static final TokenSet COMMENTS =
            PSIElementTypeFactory.createTokenSet(
                    Luminary.INSTANCE,
                    LuminaryLexer.SINGLE_LINE_COMMENT,
                    LuminaryLexer.MULTI_LINE_COMMENT,
                    LuminaryLexer.DOCUMENTATION_COMMENTS);

    public static final TokenSet STRING =
            PSIElementTypeFactory.createTokenSet(
                    Luminary.INSTANCE,
                    LuminaryLexer.STRING_LITERAL,
                    LuminaryLexer.CHAR_LITERAL);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        final LuminaryLexer lexer = new LuminaryLexer(null);
        return new ANTLRLexerAdaptor(Luminary.INSTANCE, lexer);
    }

    @Override
    public PsiParser createParser(Project project) {
        final LuminaryParser parser = new LuminaryParser(null);
        return new ANTLRParserAdaptor(Luminary.INSTANCE, parser) {

            @Override
            protected ParseTree parse(Parser parser, IElementType root) {
                return ((LuminaryParser) parser).protocol();
            }
        };
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return STRING;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        IElementType elementType = node.getElementType();
        if (!(elementType instanceof RuleIElementType)) {
            return new ANTLRPsiNode(node);
        }
        RuleIElementType ruleElType = (RuleIElementType) elementType;
        switch (ruleElType.getRuleIndex()) {
            case LuminaryParser.RULE_classDefinition :
                return new ClassSubTree(node, elementType);
            case LuminaryParser.RULE_interfaceDefinition:
                return new InterfaceDefinition(node, elementType);
            case LuminaryParser.RULE_decoratorDefinition:
                return new DecoratorDefinition(node, elementType);
            case LuminaryParser.RULE_classProperty:
                return new ClassPropertySubtree(node, elementType);
            case LuminaryParser.RULE_interfaceProperty:
                return new InterfacePropertySubtree(node, elementType);
            case LuminaryParser.RULE_decoratorProperty:
                return new DecoratorPropertySubtree(node, elementType);
            case LuminaryParser.RULE_namespace:
                return new NamespaceDefinition(node, elementType);
            case LuminaryParser.RULE_importStatement:
                return new ImportStatement(node, elementType);
            case LuminaryParser.RULE_option:
                return new OptionStatement(node, elementType);
            case LuminaryParser.RULE_type:
                return new TypeElement(node);
//            case LuminaryParser.RULE_builtinType:
//                return new BuiltinTypePsi(node);
            default:
                return new ANTLRPsiNode(node);
        }
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new LuminaryPsiFile(viewProvider);
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return WHITESPACE;
    }
}
