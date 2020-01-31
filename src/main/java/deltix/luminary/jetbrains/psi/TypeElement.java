package deltix.luminary.jetbrains.psi;

import com.intellij.lang.ASTNode;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniil Yarmalkevich
 * Date: 10/16/2019
 */
public class TypeElement extends ANTLRPsiNode {
    public TypeElement(@NotNull ASTNode node) {
        super(node);
    }
}
