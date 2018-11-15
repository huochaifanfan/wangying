package com.wytianxiatuan.wytianxia.overrideview.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2017/8/23 0023.
 */
public class CustomerGridView extends GridView {
    public CustomerGridView(Context context) {
        super(context);
    }

    public CustomerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
