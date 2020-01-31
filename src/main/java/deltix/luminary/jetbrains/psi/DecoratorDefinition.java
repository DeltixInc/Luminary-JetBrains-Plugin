package deltix.luminary.jetbrains.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.IElementType;
import deltix.luminary.jetbrains.Luminary;
import org.antlr.intellij.adaptor.SymtabUtils;
import org.antlr.intellij.adaptor.psi.IdentifierDefSubtree;
import org.antlr.intellij.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Daniil Yarmalkevich
 * Date: 10/9/2019
 */
public class DecoratorDefinition extends IdentifierDefSubtree implements ScopeNode {
    public DecoratorDefinition(@NotNull ASTNode node, @NotNull IElementType idElementType) {
        super(node, idElementType);
    }

    @Nullable
    @Override
    public PsiElement resolve(PsiNamedElement element) {
        return SymtabUtils.resolve(this, Luminary.INSTANCE,
                element, "/protocol/typeDefinition/decoratorDefinition/IDENTIFIER");
    }
}
