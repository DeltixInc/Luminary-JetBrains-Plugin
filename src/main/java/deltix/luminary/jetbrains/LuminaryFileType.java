package deltix.luminary.jetbrains;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Daniil Yarmalkevich
 * Date: 10/7/2019
 */
public class LuminaryFileType extends LanguageFileType {

    public static final LuminaryFileType INSTANCE = new LuminaryFileType();
    public static final Icon ICON = IconLoader.getIcon("/deltix/luminary/jetbrains/deltix-icon.png");

    private LuminaryFileType() {
        super(Luminary.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Luminary file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Luminary file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "lux";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return ICON;
    }
}
