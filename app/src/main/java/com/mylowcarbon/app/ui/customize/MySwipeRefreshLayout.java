//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mylowcarbon.app.ui.customize;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.LayoutRes;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;

import com.scu.miomin.shswiperefresh.R.styleable;
import com.scu.miomin.shswiperefresh.core.SHGuidanceView;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;
/**
 * 添加自动刷新功能
 */
public class MySwipeRefreshLayout extends FrameLayout implements NestedScrollingParent {
    private NestedScrollingParentHelper parentHelper;
    private SHSwipeRefreshLayout.SHSOnRefreshListener onRefreshListener;
    public static final int NOT_OVER_TRIGGER_POINT = 1;
    public static final int OVER_TRIGGER_POINT = 2;
    public static final int START = 3;
    private SHGuidanceView headerView = new SHGuidanceView(this.getContext());
    private SHGuidanceView footerView = new SHGuidanceView(this.getContext());
    private View mTargetView;
    private static final int GUIDANCE_VIEW_HEIGHT = 80;
    private static final int ACTION_PULL_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;
    private boolean mPullRefreshEnable = true;
    private boolean mPullLoadEnable = true;
    private volatile boolean mRefreshing = false;
    private float guidanceViewHeight = 0.0F;
    private float guidanceViewFlowHeight = 0.0F;
    private int mCurrentAction = -1;
    private boolean isConfirm = false;
    private int mGuidanceViewBgColor;
    private int mGuidanceViewTextColor;
    private int mProgressBgColor;
    private int mProgressColor;
    private String mRefreshDefaulText = "";
    private String mLoadDefaulText = "";
    private Context context;
    public MySwipeRefreshLayout(Context context) {
        super(context);
        this.initAttrs(context, (AttributeSet)null);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initAttrs(context, attrs);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initAttrs(context, attrs);
    }

    @TargetApi(21)
    public MySwipeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        this.context = context;
        if(this.getChildCount() > 1) {
            throw new RuntimeException("WXSwipeLayout should not have more than one child");
        } else {
            this.parentHelper = new NestedScrollingParentHelper(this);
            this.guidanceViewHeight = this.dipToPx(context, 80.0F);
            this.guidanceViewFlowHeight = this.guidanceViewHeight * 1.5F;
            if(!this.isInEditMode() || attrs != null) {
                TypedArray ta = context.obtainStyledAttributes(attrs, styleable.SHSwipeRefreshLayout);
                Resources resources = context.getResources();
                int resId = ta.getResourceId(styleable.SHSwipeRefreshLayout_guidance_view_bg_color, -1);
                if(resId == -1) {
                    this.mGuidanceViewBgColor = ta.getColor(styleable.SHSwipeRefreshLayout_guidance_view_bg_color, -1);
                } else {
                    this.mGuidanceViewBgColor = resources.getColor(resId);
                }

                resId = ta.getResourceId(styleable.SHSwipeRefreshLayout_guidance_text_color, -1);
                if(resId == -1) {
                    this.mGuidanceViewTextColor = ta.getColor(styleable.SHSwipeRefreshLayout_guidance_text_color, -16777216);
                } else {
                    this.mGuidanceViewTextColor = resources.getColor(resId);
                }

                resId = ta.getResourceId(styleable.SHSwipeRefreshLayout_progress_bg_color, -1);
                if(resId == -1) {
                    this.mProgressBgColor = ta.getColor(styleable.SHSwipeRefreshLayout_progress_bg_color, -1);
                } else {
                    this.mProgressBgColor = resources.getColor(resId);
                }

                resId = ta.getResourceId(styleable.SHSwipeRefreshLayout_progress_bar_color, -1);
                if(resId == -1) {
                    this.mProgressColor = ta.getColor(styleable.SHSwipeRefreshLayout_progress_bar_color, -65536);
                } else {
                    this.mProgressColor = resources.getColor(resId);
                }

                resId = ta.getResourceId(styleable.SHSwipeRefreshLayout_refresh_text, -1);
                if(resId == -1) {
                    this.mRefreshDefaulText = ta.getString(styleable.SHSwipeRefreshLayout_refresh_text);
                } else {
                    this.mRefreshDefaulText = resources.getString(resId);
                }

                resId = ta.getResourceId(styleable.SHSwipeRefreshLayout_load_text, -1);
                if(resId == -1) {
                    this.mLoadDefaulText = ta.getString(styleable.SHSwipeRefreshLayout_load_text);
                } else {
                    this.mLoadDefaulText = resources.getString(resId);
                }

            }
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mTargetView = this.getChildAt(0);
        this.setGuidanceView();
    }

    private void setGuidanceView() {
        LayoutParams lp = new LayoutParams(-1, 0);
        this.headerView.setStartEndTrim(0.0F, 0.75F);
        this.headerView.setText(this.mRefreshDefaulText);
        this.headerView.setTextColor(this.mGuidanceViewTextColor);
        this.headerView.setBackgroundColor(this.mGuidanceViewBgColor);
        this.headerView.setProgressBgColor(this.mProgressBgColor);
        this.headerView.setProgressColor(this.mProgressColor);
        this.addView(this.headerView, lp);
        lp = new LayoutParams(-1, 0);
        lp.gravity = 80;
        this.footerView.setStartEndTrim(0.5F, 1.25F);
        this.footerView.setText(this.mLoadDefaulText);
        this.footerView.setTextColor(this.mGuidanceViewTextColor);
        this.footerView.setBackgroundColor(this.mGuidanceViewBgColor);
        this.footerView.setProgressBgColor(this.mProgressBgColor);
        this.footerView.setProgressColor(this.mProgressColor);
        this.addView(this.footerView, lp);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !this.mPullRefreshEnable && !this.mPullLoadEnable?false:super.onInterceptTouchEvent(ev);
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    public void onNestedScrollAccepted(View child, View target, int axes) {
        this.parentHelper.onNestedScrollAccepted(child, target, axes);
    }

    public void onStopNestedScroll(View child) {
        this.parentHelper.onStopNestedScroll(child);
        this.handlerAction();
    }

    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        if(this.mPullRefreshEnable || this.mPullLoadEnable) {
            if(Math.abs(dy) <= 200) {
                if(!this.isConfirm) {
                    if(dy < 0 && !this.canChildScrollUp()) {
                        this.mCurrentAction = 0;
                        this.isConfirm = true;
                    } else if(dy > 0 && !this.canChildScrollDown()) {
                        this.mCurrentAction = 1;
                        this.isConfirm = true;
                    }
                }

                if(this.moveGuidanceView((float)(-dy))) {
                    consumed[1] += dy;
                }

            }
        }
    }

    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    public int getNestedScrollAxes() {
        return this.parentHelper.getNestedScrollAxes();
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    private boolean moveGuidanceView(float distanceY) {
        if(this.mRefreshing) {
            return false;
        } else {
            LayoutParams lp;
            if(!this.canChildScrollUp() && this.mPullRefreshEnable && this.mCurrentAction == 0) {
                lp = (LayoutParams)this.headerView.getLayoutParams();
                lp.height = (int)((float)lp.height + distanceY);
                if(lp.height < 0) {
                    lp.height = 0;
                }

                if((float)lp.height > this.guidanceViewFlowHeight) {
                    lp.height = (int)this.guidanceViewFlowHeight;
                }

                if(this.onRefreshListener != null) {
                    if((float)lp.height >= this.guidanceViewHeight) {
                        this.onRefreshListener.onRefreshPulStateChange((float)lp.height / this.guidanceViewHeight, 2);
                    } else {
                        this.onRefreshListener.onRefreshPulStateChange((float)lp.height / this.guidanceViewHeight, 1);
                    }
                }

                if(lp.height == 0) {
                    this.isConfirm = false;
                    Log.e("test", "11*****************moveGuidanceView  lp.height == 0 "   );

                    this.mCurrentAction = -1;
                }

                this.headerView.setLayoutParams(lp);
                this.headerView.setProgressRotation((float)lp.height / this.guidanceViewFlowHeight);
                this.moveTargetView((float)lp.height);
                return true;
            } else if(!this.canChildScrollDown() && this.mPullLoadEnable && this.mCurrentAction == 1) {
                lp = (LayoutParams)this.footerView.getLayoutParams();
                lp.height = (int)((float)lp.height - distanceY);
                if(lp.height < 0) {
                    lp.height = 0;
                }

                if((float)lp.height > this.guidanceViewFlowHeight) {
                    lp.height = (int)this.guidanceViewFlowHeight;
                }

                if(this.onRefreshListener != null) {
                    if((float)lp.height >= this.guidanceViewHeight) {
                        this.onRefreshListener.onLoadmorePullStateChange((float)lp.height / this.guidanceViewHeight, 2);
                    } else {
                        this.onRefreshListener.onLoadmorePullStateChange((float)lp.height / this.guidanceViewHeight, 1);
                    }
                }

                if(lp.height == 0) {
                    this.isConfirm = false;
                    Log.e("test", "22*****************moveGuidanceView  lp.height == 0 "   );

                    this.mCurrentAction = -1;
                }

                this.footerView.setLayoutParams(lp);
                this.footerView.setProgressRotation((float)lp.height / this.guidanceViewFlowHeight);
                this.moveTargetView((float)(-lp.height));
                return true;
            } else {
                return false;
            }
        }
    }

    private void moveTargetView(float h) {
        if (this.mTargetView!=null)
            this.mTargetView.setTranslationY(h);
    }

    private void handlerAction() {
        if(!this.isRefreshing()) {
            this.isConfirm = false;
            LayoutParams lp;
            if(this.mPullRefreshEnable && this.mCurrentAction == 0) {
                lp = (LayoutParams)this.headerView.getLayoutParams();
                if((float)lp.height >= this.guidanceViewHeight) {
                    this.startRefresh(lp.height);
                    if(this.onRefreshListener != null) {
                        this.onRefreshListener.onRefreshPulStateChange(1.0F, 3);
                    }
                } else if(lp.height > 0) {
                    this.resetHeaderView(lp.height);
                } else {
                    this.resetRefreshState();
                }
            }

            if(this.mPullLoadEnable && this.mCurrentAction == 1) {
                lp = (LayoutParams)this.footerView.getLayoutParams();
                if((float)lp.height >= this.guidanceViewHeight) {
                    this.startLoadmore(lp.height);
                    if(this.onRefreshListener != null) {
                        this.onRefreshListener.onLoadmorePullStateChange(1.0F, 3);
                    }
                } else if(lp.height > 0) {
                    this.resetFootView(lp.height);
                } else {
                    this.resetLoadmoreState();
                }
            }

        }
    }


    public void startRefresh() {
        if (mRefreshing){
            return;
        }
        this.mCurrentAction = 0 ;

        this.mRefreshing = true;
        final float height = this.dipToPx(context, 80.0F) * 1.1f;
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{height , this.guidanceViewHeight});
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams)MySwipeRefreshLayout.this.headerView.getLayoutParams();
                if (lp!=null){
                    lp.height = (int)((Float)animation.getAnimatedValue()).floatValue();
                    MySwipeRefreshLayout.this.headerView.setLayoutParams(lp);
                    MySwipeRefreshLayout.this.moveTargetView((float)lp.height);
                } else {
                    MySwipeRefreshLayout.this.moveTargetView(height);
                }


            }
        });
        animator.addListener(new WXRefreshAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                MySwipeRefreshLayout.this.headerView.startAnimation();
                if(MySwipeRefreshLayout.this.onRefreshListener != null) {
                    MySwipeRefreshLayout.this.onRefreshListener.onRefresh();
                }

            }
        });
        animator.setDuration(300L);
        animator.start();
    }
    private void startRefresh(int headerViewHeight) {
        this.mRefreshing = true;
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{(float)headerViewHeight, this.guidanceViewHeight});
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams)MySwipeRefreshLayout.this.headerView.getLayoutParams();
                lp.height = (int)((Float)animation.getAnimatedValue()).floatValue();
                MySwipeRefreshLayout.this.headerView.setLayoutParams(lp);
                MySwipeRefreshLayout.this.moveTargetView((float)lp.height);

            }
        });
        animator.addListener(new WXRefreshAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                MySwipeRefreshLayout.this.headerView.startAnimation();
                if(MySwipeRefreshLayout.this.onRefreshListener != null) {
                    MySwipeRefreshLayout.this.onRefreshListener.onRefresh();
                }

            }
        });
        animator.setDuration(300L);
        animator.start();
    }

    private void resetHeaderView(int headerViewHeight) {
        this.headerView.stopAnimation();
        this.headerView.setStartEndTrim(0.0F, 0.75F);
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{(float)headerViewHeight, 0.0F});
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams)MySwipeRefreshLayout.this.headerView.getLayoutParams();
                lp.height = (int)((Float)animation.getAnimatedValue()).floatValue();
                MySwipeRefreshLayout.this.headerView.setLayoutParams(lp);
                MySwipeRefreshLayout.this.moveTargetView((float)lp.height);
            }
        });
        animator.addListener(new WXRefreshAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                MySwipeRefreshLayout.this.resetRefreshState();
            }
        });
        animator.setDuration(300L);
        animator.start();
    }

    private void resetRefreshState() {

        this.mRefreshing = false;
        this.isConfirm = false;
        this.mCurrentAction = -1;
    }

    public void startLoadmore(int headerViewHeight) {
        this.mRefreshing = true;
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{(float)headerViewHeight, this.guidanceViewHeight});
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams)MySwipeRefreshLayout.this.footerView.getLayoutParams();
                lp.height = (int)((Float)animation.getAnimatedValue()).floatValue();
                MySwipeRefreshLayout.this.footerView.setLayoutParams(lp);
                MySwipeRefreshLayout.this.moveTargetView((float)(-lp.height));
            }
        });
        animator.addListener(new WXRefreshAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                MySwipeRefreshLayout.this.footerView.startAnimation();
                if(MySwipeRefreshLayout.this.onRefreshListener != null) {
                    MySwipeRefreshLayout.this.onRefreshListener.onLoading();
                }

            }
        });
        animator.setDuration(300L);
        animator.start();
    }

    private void resetFootView(int headerViewHeight) {
        this.footerView.stopAnimation();
        this.footerView.setStartEndTrim(0.5F, 1.25F);
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{(float)headerViewHeight, 0.0F});
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams)MySwipeRefreshLayout.this.footerView.getLayoutParams();
                lp.height = (int)((Float)animation.getAnimatedValue()).floatValue();
                MySwipeRefreshLayout.this.footerView.setLayoutParams(lp);
                MySwipeRefreshLayout.this.moveTargetView((float)(-lp.height));
            }
        });
        animator.addListener(new WXRefreshAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                MySwipeRefreshLayout.this.resetLoadmoreState();
            }
        });
        animator.setDuration(300L);
        animator.start();
    }

    private void resetLoadmoreState() {

        this.mRefreshing = false;
        this.isConfirm = false;
        this.mCurrentAction = -1;
    }

    public boolean canChildScrollUp() {
        if(this.mTargetView == null) {
            return false;
        } else if(VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTargetView, -1);
        } else if(this.mTargetView instanceof AbsListView) {
            AbsListView absListView = (AbsListView)this.mTargetView;
            return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
        } else {
            return ViewCompat.canScrollVertically(this.mTargetView, -1) || this.mTargetView.getScrollY() > 0;
        }
    }

    public boolean canChildScrollDown() {
        if(this.mTargetView == null) {
            return false;
        } else if(VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTargetView, 1);
        } else if(this.mTargetView instanceof AbsListView) {
            AbsListView absListView = (AbsListView)this.mTargetView;
            if(absListView.getChildCount() <= 0) {
                return false;
            } else {
                int lastChildBottom = absListView.getChildAt(absListView.getChildCount() - 1).getBottom();
                return absListView.getLastVisiblePosition() == ((ListAdapter)absListView.getAdapter()).getCount() - 1 && lastChildBottom <= absListView.getMeasuredHeight();
            }
        } else {
            return ViewCompat.canScrollVertically(this.mTargetView, 1) || this.mTargetView.getScrollY() > 0;
        }
    }

    public float dipToPx(Context context, float value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(1, value, metrics);
    }

    public void setOnRefreshListener(SHSwipeRefreshLayout.SHSOnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void finishRefresh() {

        if(this.mCurrentAction == 0) {
            this.resetHeaderView(this.headerView == null?0:this.headerView.getMeasuredHeight());
        }

    }

    public void finishLoadmore() {
        if(this.mCurrentAction == 1) {
            this.resetFootView(this.footerView == null?0:this.footerView.getMeasuredHeight());
        }

    }

    public boolean isLoadmoreEnable() {
        return this.mPullLoadEnable;
    }

    public void setLoadmoreEnable(boolean mPullLoadEnable) {
        this.mPullLoadEnable = mPullLoadEnable;
    }

    public boolean isRefreshEnable() {
        return this.mPullRefreshEnable;
    }

    public void setRefreshEnable(boolean mPullRefreshEnable) {
        this.mPullRefreshEnable = mPullRefreshEnable;
    }

    public boolean isRefreshing() {
        return this.mRefreshing;
    }

    public void setHeaderView(@LayoutRes int layoutResID) {
        this.headerView.setGuidanceView(layoutResID);
    }

    public void setHeaderView(View view) {
        this.headerView.setGuidanceView(view);
    }

    public void setFooterView(@LayoutRes int layoutResID) {
        this.footerView.setGuidanceView(layoutResID);
    }

    public void setFooterView(View view) {
        this.footerView.setGuidanceView(view);
    }

    public void setRefreshViewText(String text) {
        this.headerView.setText(text);
    }

    public void setLoaderViewText(String text) {
        this.footerView.setText(text);
    }

    static class WXRefreshAnimatorListener implements AnimatorListener {
        WXRefreshAnimatorListener() {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    public interface SHSOnRefreshListener {
        void onRefresh();

        void onLoading();

        void onRefreshPulStateChange(float var1, int var2);

        void onLoadmorePullStateChange(float var1, int var2);
    }
}
