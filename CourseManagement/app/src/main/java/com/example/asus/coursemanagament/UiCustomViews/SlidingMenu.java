package com.example.asus.coursemanagament.UiCustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 侧滑菜单
 */
public class SlidingMenu extends HorizontalScrollView{
    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuRightPadding = 50;//单位为dp
    private boolean once=false;
    private int mMenuWidth;
    private boolean isOpen;


    //没有自定义属性的时候使用
    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        //将50dp装换为像素值,dp转换为px
        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,context.getResources()
                .getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!once){
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding - 170;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //通过设置偏移量，将menu隐藏
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed) {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch(action){
            case MotionEvent.ACTION_UP:
                //隐藏在左边的宽度
                int scrollX = getScrollX();
                if(scrollX >= mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);
                    isOpen = false;
                }
                else{
                    this.smoothScrollTo(0,0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }
    //打开侧滑菜单方法
    public void openMenu(){
        if(isOpen) {
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }
    public void closeMenu(){
        if(!isOpen){
            return;
        }
        this.smoothScrollTo(mMenuWidth,0);
        isOpen = false;
    }
    //切换菜单
    public void toggle(){
        if(isOpen){
            closeMenu();
        }
        else{
            openMenu();
        }
    }

}
