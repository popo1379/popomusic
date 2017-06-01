package com.popomusic.fragment;

import android.app.FragmentManager;
import android.icu.text.DateFormat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.popo.popomusic.R;

import com.popomusic.adapter.VideoAdapter;

import com.popomusic.data.VideoData;
import com.popomusic.presenter.VideoPresenter;
import com.popomusic.ui.ViewPagerIndicate;
import com.popomusic.util.LogUtils;
import com.popomusic.util.UIcollector;
import com.popomusic.videoModel.ItemList;
import java.util.ArrayList;

import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by popo on 2017/5/16 0016.
 */
public class VideoPagerFragment extends BaseFragment implements VideoData.View,SwipeRefreshLayout.OnRefreshListener{
    VideoAdapter videoadapter;
    private static int id=34;
    protected boolean isNull = false;

    @BindView(R.id.list)
    RecyclerView listView;
    @BindView(R.id.indicate)
    ViewPagerIndicate tabPageIndicator;
    VideoPresenter mPresent;
    @BindView(R.id.srf_layout)
    SwipeRefreshLayout srfLayout;

    @Override
    protected View initView() {
        view = View.inflate(UIcollector.getActivity(), R.layout.fragment_video_pager, null);
        return view;
    }
    @Override
    protected void initData() {
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));

        List<ItemList> lists=new ArrayList<ItemList>();

        mPresent=new VideoPresenter((VideoData.View) this);
     videoadapter=new VideoAdapter(lists);
        listView.setAdapter(videoadapter);
        initViewPagerIndicate();
        srfLayout.setOnRefreshListener(this);
        srfLayout.post(() -> onRefresh());
    }
    @Override
    public void setData(List<ItemList>list){
        if (list.isEmpty()) {
            isNull = true;
        }else {
            LogUtils.d("VideoPagerFragment","数据测试"+list.get(0).data.playUrl);
            videoadapter.addAll(list);
        }

    }
    @Override
    public void onRefresh() {
        videoadapter.removeAll();
        mPresent.requestData(id);
        LogUtils.d("VideoPagerFragment-刷新","id="+id);
    }
    //刷新开关：开
    @Override
    public void showProgress() {
        if (!srfLayout.isRefreshing()) {
            srfLayout.setRefreshing(true);
        }
    }

    //刷新开关：关
    @Override
    public void hideProgress() {
        if (srfLayout.isRefreshing()) {
            srfLayout.setRefreshing(false);
        }
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
        //设置初始化完成
        tabPageIndicator.setOk();
    }

    public  void indicateClickEvent(int pos){
        switch (pos){
            case 0:
                this.id=34;
                break;
            case 1:
                this.id=28;

                break;
            case 2:
                this.id=30;
                break;
            case 3:
                this.id=32;
                break;
            case 4:
                this.id=26;
                break;
            case 5:
                id = 36;
                break;
            case 6:
                this.id=20;
                break;
            case 7:
                this.id=38;
                break;
            case 8:
                this.id=8;
                break;
            case 9:
                this.id=14;
                break;
            case 10:
                this.id=22;
                break;
            case 11:
                this.id=12;
                break;
        }

    }

    public void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
    }
}
