package deltix.luminary.jetbrains.annotators;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import deltix.luminary.jetbrains.LuminarySyntaxHighlighter;
import deltix.luminary.jetbrains.psi.TypeElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniil Yarmalkevich
 * Date: 10/25/2019
 */
public class LuminaryAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof TypeElement) {
            Annotation annotation = annotationHolder.createInfoAnnotation(psiElement, null);
            annotation.setTextAttributes(LuminarySyntaxHighlighter.KEYWORD);
        }
    }
}
