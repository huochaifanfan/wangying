package com.wytianxiatuan.wytianxia.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/9/8 0008.
 */
public class MyDecoration extends RecyclerView.ItemDecoration {
    private int mOrientation;
    private Paint paint;
    private int decorationHeight;
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    public MyDecoration(Context context, int color, int orientation, int decorationHeight) {
        setOrientation(orientation);
        this.decorationHeight = decorationHeight;
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, color));
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST){
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c,parent,state);
        if (mOrientation == VERTICAL_LIST){
            drawVerticalLine(c, parent, state);
        }else {
            drawHorizontalLine(c, parent, state);
        }
    }

    /**
     * 横向
     * @param c
     * @param parent
     * @param state
     */
    private void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        for (int i =0;i <childCount ;i++){
            final View child = parent.getChildAt(i);
//            int position = parent.getChildAdapterPosition(child);
//            if (position == childCount -1) continue;
            /**获取布局参数*/
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            /**
             * 根据子视图的位置 & 间隔区域，设置矩形（分割线）的2个顶点坐标(左上 & 右下)
             * 矩形左上顶点 = (ItemView的左边界,ItemView的下边界), ItemView的左边界 = RecyclerView 的左边界 + paddingLeft距离 后的位置
             */
            final int left = parent.getPaddingLeft();
            /**
             * 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
             *ItemView的右边界 = RecyclerView 的右边界减去 paddingRight 后的坐标位置
             */
            final int right = parent.getWidth() - parent.getPaddingRight();
            /**ItemView的下边界：ItemView 的 bottom坐标 + 距离RecyclerView底部距离 +translationY*/
            final int top = child.getBottom() + layoutParams.bottomMargin;
            /**绘制分割线的下边界 = ItemView的下边界+分割线的高度*/
            final int bottom = top + decorationHeight;
            c.drawRect(left,top,right,bottom,paint);
        }
    }
    private void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);
            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            int top = parent.getPaddingTop();
            int bottom = parent.getHeight() - parent.getPaddingBottom();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + decorationHeight;
            c.drawRect(left,top,right,bottom,paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect,view,parent,state);
        int position = parent.getChildAdapterPosition(view);
        int count = parent.getAdapter().getItemCount();
        if (mOrientation == HORIZONTAL_LIST) {
            if (position != count-1){
                outRect.set(0, 0, 0, decorationHeight);
            }
        } else {
            outRect.set(0, 0, decorationHeight, 0);
        }
    }
}
