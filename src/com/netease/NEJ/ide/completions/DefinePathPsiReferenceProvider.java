package com.netease.NEJ.ide.completions;

import com.intellij.lang.javascript.psi.JSArgumentList;
import com.intellij.lang.javascript.psi.JSArrayLiteralExpression;
import com.intellij.lang.javascript.psi.JSCallExpression;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

/**
 * Created by abnerzheng on 2017/2/16.
 */
public class DefinePathPsiReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        //todo 加入NEJ使能按钮

        String path = element.getText();

        if (!isDefineCall(element)){
            return PsiReference.EMPTY_ARRAY; //当不是Define调用的时候，直接返回
        }
        //todo 加入是不是define call
        // 这里应该要返回一个PsiReference数组，因为对于一个string来说，他的每个段都是一个路径，所以要注意
        int startIndex = path.lastIndexOf("/") +1;
        PsiReference ref = new DefinePathReference(element, new TextRange(startIndex, path.length() -1));
        return new PsiReference[]{ref};
    }

    private boolean isDefineCall(PsiElement element) {
        if(element.getParent() instanceof JSArrayLiteralExpression){}
        PsiElement prevEl = element.getParent();
        if (prevEl != null && prevEl instanceof JSArrayLiteralExpression){
            prevEl = prevEl.getParent();
            if(prevEl != null && prevEl instanceof JSArgumentList){
                prevEl = prevEl.getParent();
                if(prevEl != null && prevEl instanceof JSCallExpression){
                    String methodExpr = ((JSCallExpression) prevEl).getMethodExpression().getText();
                    return methodExpr.contains("define");
                }
            }
        }

        return false;
    }


}
