package deltix.luminary.jetbrains;

import com.intellij.lang.Language;

/**
 * @author Daniil Yarmalkevich
 * Date: 10/7/2019
 */
public class Luminary extends Language {

    public static final Luminary INSTANCE = new Luminary();

    private Luminary() {
        super("Luminary");
    }


}
