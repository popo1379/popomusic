package com.popomusic.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.support.v4.view.ViewPager;
import com.popo.popomusic.R;
import com.popomusic.ui.ViewPagerIndicate;
import com.popomusic.util.UIcollector;
import com.popomusic.videofragment.GameFragment;
import com.popomusic.videofragment.GaoxiaoFragment;
import com.popomusic.videofragment.GuanggaoFragment;
import com.popomusic.videofragment.JiluFragment;
import com.popomusic.videofragment.JinjiFragment;
import com.popomusic.videofragment.JuqingFragment;
import com.popomusic.videofragment.KepuFragment;
import com.popomusic.videofragment.LifeFragment;
import com.popomusic.videofragment.MengchongFragment;
import com.popomusic.videofragment.MusicXFragment;
import com.popomusic.videofragment.YugaoFragment;
import com.popomusic.videofragment.ZongyiFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
public class VideoHomeFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.indicate)
    ViewPagerIndicate tabPageIndicator;
    private ArrayList<Fragment> fragmentList;


    @Override
    protected View initView() {
        view = View.inflate(UIcollector.getActivity(), R.layout.fragment_videohome, null);
        return view;
    }

    @Override
    protected void initData() {
        initViewPagerIndicate();

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new JinjiFragment());
        fragmentList.add(new GaoxiaoFragment());
        fragmentList.add(new GameFragment());
        fragmentList.add(new KepuFragment());
        fragmentList.add(new MengchongFragment());
        fragmentList.add(new LifeFragment());
        fragmentList.add(new MusicXFragment());
        fragmentList.add(new ZongyiFragment());
        fragmentList.add(new YugaoFragment());
        fragmentList.add(new GuanggaoFragment());
        fragmentList.add(new JiluFragment());
        fragmentList.add(new JuqingFragment());

        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
    }
        //初始化顶部滑动
        private void initViewPagerIndicate() {
            int[] mTextColors = {0xFFA0A0A0, 0xFF000000};
            int mUnderlineColor = 0xFF168EFF;
            String[] mTitles = new String[] {
                    "锦集", "搞笑", "游戏", "科普","萌宠","生活","音乐","综艺","预告","广告","记录","剧情"
            };
            tabPageIndicator.resetText(R.layout.viewpagerindicate_item, mTitles, mTextColors);
            //设置下划线粗细和颜色
            tabPageIndicator.resetUnderline(4, mUnderlineColor);
            tabPageIndicator.resetViewPager(viewPager);

            //设置初始化完成
            tabPageIndicator.setOk();
        }
    }
















