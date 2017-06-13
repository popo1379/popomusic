package com.popomusic.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.popomusic.fragment.CeshiFragment;
import com.popomusic.fragment.VideoPagerFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/10 0010.
 * 因需求将该控件简单修改，不再服务ViewPager
 */
public class ViewPagerIndicate extends HorizontalScrollView implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private ViewPager mViewPager;
    //onMeasure是否准备完成
    private boolean isMeasureOk;
    //是否可以绘制下划线
    private boolean isDrawOK;
    //HorizontalScrollView只能有1个子View（这里用线性布局）
    private LinearLayout mWapper;
    //标签
    private ArrayList<TextView> mTextViews;
    //标签正常颜色和高亮颜色
    private int mTextNormalColor, mTextHighlightColor;
    //总宽度
    private int mTotalWidth;
    //1个标签的宽和高
    private int mTabWidth, mTabHeight;
    private Paint mPaint;
    //下划线的位置
    private float mTranslateX;

    private VideoPagerFragment videoPagerFragment;

    public ViewPagerIndicate(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mPaint = new Paint();
        isMeasureOk = isDrawOK = false;
    }

    /**
     * 重置下划线的粗细和颜色
     * @param size 下划线粗细
     * @param color 下划线颜色
     */
    public void resetUnderline(int size, int color) {
        mPaint.setStrokeWidth((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, size, mContext.getResources().getDisplayMetrics()));
        mPaint.setColor(color);
    }

    /**
     * 重置标签的布局样式、标题和颜色
     * @param tabId 布局样式id
     * @param titles 标题集合
     * @param colors 颜色集合
     */
    public void resetText(int tabId, String[] titles, int[] colors) {
        mTextNormalColor = colors[0];
        mTextHighlightColor = colors[1];
        mTextViews = new ArrayList<TextView>();
        for (int i = 0; i < titles.length; i++) {
            TextView tv = (TextView) mInflater.inflate(tabId, null);
            tv.setText(titles[i]);
            tv.setTag(i);
            tv.setOnClickListener(this);
            mTextViews.add(tv);
        }
        setTextHighlight(0);
    }

    /**
     * 高亮对应位置的标签颜色
     * @param pos 对应位置
     */
    private void setTextHighlight(int pos) {
        for (int i = 0; i < mTextViews.size(); i++) {
            if (i == pos)
                mTextViews.get(pos).setTextColor(mTextHighlightColor);
            else
                mTextViews.get(i).setTextColor(mTextNormalColor);
        }
    }

    /**
     * 之所以设置此方法，主要是用于网络获取数据，而不是使用静态文本；
     * 当网络请求成功，手动调用该方法完成初始化
     */
    public void setOk() {
        isMeasureOk = true;
        isDrawOK = true;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //必须放在super.onMeasure的前面，否则后面无法获得子View的宽高
        if (isMeasureOk) {
            removeAllViews();
            mWapper = new LinearLayout(mContext);
            for (TextView tv : mTextViews) {
                mWapper.addView(tv);
            }
            addView(mWapper);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (isMeasureOk) {
            if (mTextViews.size() > 0) {
                mTotalWidth = getMeasuredWidth();
                TextView tv = (TextView) mWapper.getChildAt(0);
                mTabWidth = tv.getMeasuredWidth();
                mTabHeight = tv.getMeasuredHeight();
            }
            isMeasureOk = false;
        }
    }

    /**
     * 设置下划线的位置
     * @param pos ViewPager对应的索引位置
     * @param posOffset ViewPager滑动百分比
     */
    private void drawUnderline(int pos, float posOffset) {
        mTranslateX = pos * mTabWidth + posOffset * mTabWidth;
        invalidate();
    }

    /**
     * 设置下划线的位置
     * @param pos ViewPager对应的索引位置
     */
    private void drawUnderline(int pos) {
        mTranslateX = pos * mTabWidth;
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isDrawOK) {
            //改变下划线的位置
            canvas.translate(mTranslateX, 0);
            //绘制下划线
            canvas.drawLine(0, mTabHeight, mTabWidth, mTabHeight, mPaint);
        }
    }

    /**
     * 重置ViewPager
     */
    public void resetViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /*ViewPager某项被选中*/
            @Override
            public void onPageSelected(int position) {
                //高亮该项文字
                setTextHighlight(position);
            }

            /*ViewPager从某项滑动到另一项*/
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                drawUnderline(position, positionOffset);
                //实现标签与下划线一起滚动的效果
                scrollTo((int) ((position + positionOffset - 1) * mTabWidth), 0);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });
    }

    /**
     * 标签点击事件监听
     */
    @Override
    public void onClick(View v) {
        videoPagerFragment=new VideoPagerFragment();
        int pos = (Integer) v.getTag();
        // 修改：
        videoPagerFragment.indicateClickEvent(pos);

        //让当前标签总是显示在第二个位置
        smoothScrollTo((pos - 1) * mTabWidth, 0);
        drawUnderline(pos);
     //   mViewPager.setCurrentItem(pos, false);

        //高亮该项文字
        setTextHighlight(pos);
    }

}