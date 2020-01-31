package deltix.luminary.jetbrains.psi;

import com.intellij.lang.ASTNode;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniil Yarmalkevich
 * Date: 10/25/2019
 */
public class BuiltinTypePsi extends ANTLRPsiNode {
    public BuiltinTypePsi(@NotNull ASTNode node) {
        super(node);
    }
}
