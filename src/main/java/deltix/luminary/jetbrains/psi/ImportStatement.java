package deltix.luminary.jetbrains.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.psi.IdentifierDefSubtree;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniil Yarmalkevich
 * Date: 10/14/2019
 */
public class ImportStatement extends IdentifierDefSubtree {
    public ImportStatement(@NotNull ASTNode node, @NotNull IElementType idElementType) {
        super(node, idElementType);
    }
}
