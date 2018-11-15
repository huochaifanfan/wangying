package com.wytianxiatuan.wytianxia.overrideview.banner.transformer;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class ScaleYTransformer implements PageTransformer {
    private static final float MIN_SCALE = 0.85F;
    @Override
    public void transformPage(View page, float position) {
        if(position < -1){
            page.setScaleY(MIN_SCALE);
        }else if(position<= 1){
            //
            float scale = Math.max(MIN_SCALE,1 - Math.abs(position));
            page.setScaleY(scale);
            /*page.setScaleX(scale);
            if(position<0){
                page.setTranslationX(width * (1 - scale) /2);
            }else{
                page.setTranslationX(-width * (1 - scale) /2);
            }*/

        }else{
            page.setScaleY(MIN_SCALE);
        }
    }
}
