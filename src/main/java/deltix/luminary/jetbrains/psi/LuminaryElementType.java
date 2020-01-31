package deltix.luminary.jetbrains.psi;

import com.intellij.psi.tree.IElementType;
import deltix.luminary.jetbrains.Luminary;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniil Yarmalkevich
 * Date: 10/7/2019
 */
public class LuminaryElementType extends IElementType {
    public LuminaryElementType(@NotNull @NonNls String debugName) {
        super(debugName, Luminary.INSTANCE);
    }
}
