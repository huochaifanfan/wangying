package com.wytianxiatuan.wytianxia.overrideview.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.AccordionTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.BackgroundToForegroundTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.CubeInTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.CubeOutTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.DefaultTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.DepthPageTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.FlipHorizontalTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.FlipVerticalTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.ForegroundToBackgroundTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.RotateDownTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.RotateUpTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.ScaleInOutTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.ScaleYTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.StackTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.TabletTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.ZoomInTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.ZoomOutSlideTransformer;
import com.wytianxiatuan.wytianxia.overrideview.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
    public static Class<? extends PageTransformer> ScalY = ScaleYTransformer.class;
}
