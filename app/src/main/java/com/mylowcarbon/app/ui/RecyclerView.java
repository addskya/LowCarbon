package com.mylowcarbon.app.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.mylowcarbon.app.R;

/**
 * Created by Orange on 18-4-11.
 * Email:addskya@163.com
 */

public class RecyclerView extends android.support.v7.widget.RecyclerView {

    public RecyclerView(Context context) {
        this(context,null);
    }

    public RecyclerView(Context context,
                        @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerView);
        initItemDecoration(a);
        a.recycle();
    }


    private void initItemDecoration(TypedArray a) {
        Drawable divider = a.getDrawable(R.styleable.RecyclerView_divider_h);
        if (divider == null) {
            return;
        }

        DividerDecoration itemDecoration = new DividerDecoration(LinearLayoutManager.VERTICAL);
        itemDecoration.setDivider(divider);
        addItemDecoration(itemDecoration);
    }

    class DividerDecoration extends RecyclerView.ItemDecoration {

        static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
        static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

        private Drawable mDivider;
        private int mPaddingLeft;
        private int mPaddingRight;

        private int mOrientation;


        public DividerDecoration() {
            this(VERTICAL_LIST);
        }

        public DividerDecoration(int orientation) {
            setOrientation(orientation);
        }

        public void setDivider(@NonNull Drawable divider){
            mDivider = divider;
        }


        public void setPaddingLeft(int paddingLeft){
            mPaddingLeft = paddingLeft;
        }

        public void setPaddingRight(int paddingRight){
            mPaddingRight = paddingRight;
        }

        public void setOrientation(int orientation) {
            if (orientation != HORIZONTAL_LIST
                    && orientation != VERTICAL_LIST) {
                throw new IllegalArgumentException("invalid orientation");
            }
            mOrientation = orientation;
        }

        @Override
        public void onDraw(Canvas c, android.support.v7.widget.RecyclerView parent) {
            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        public void drawVertical(Canvas c, android.support.v7.widget.RecyclerView parent) {
            final int left = parent.getPaddingLeft() + mPaddingLeft;
            final int right = parent.getWidth() - parent.getPaddingRight() - mPaddingRight;

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + getDividerHeight();
                if (mDivider != null){
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }

        }

        public void drawHorizontal(Canvas c, android.support.v7.widget.RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin - mPaddingLeft;
                final int right = left + getDividerHeight() - mPaddingRight;
                if(mDivider != null){
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition,
                                   android.support.v7.widget.RecyclerView parent) {
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0, 0, 0, getDividerHeight());
            } else {
                outRect.set(0, 0, getDividerWidth(), 0);
            }
        }

        private int getDividerWidth(){
            return mDivider == null ? 0 : mDivider.getIntrinsicWidth();
        }

        private int getDividerHeight(){
            return mDivider == null ? 0: mDivider.getIntrinsicHeight();
        }

    }
}
