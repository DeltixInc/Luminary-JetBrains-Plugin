package deltix.luminary.jetbrains;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniil Yarmalkevich
 * Date: 10/11/2019
 */
public class LuminaryFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(LuminaryFileType.INSTANCE, "lux");
    }
}
