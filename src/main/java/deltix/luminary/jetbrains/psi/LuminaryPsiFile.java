package deltix.luminary.jetbrains.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import deltix.luminary.jetbrains.Luminary;
import deltix.luminary.jetbrains.LuminaryFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Daniil Yarmalkevich
 * Date: 10/8/2019
 */
public class LuminaryPsiFile extends PsiFileBase {
    public LuminaryPsiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, Luminary.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return LuminaryFileType.INSTANCE;
    }

    @Nullable
    @Override
    public Icon getIcon(int flags) {
        return LuminaryFileType.ICON;
    }
}
