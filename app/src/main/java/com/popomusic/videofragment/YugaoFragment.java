package com.popomusic.videofragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popo.popomusic.R;
import com.popomusic.adapter.VideoAdapter;
import com.popomusic.data.VideoData;
import com.popomusic.presenter.VideoPresenter;
import com.popomusic.util.LogUtils;
import com.popomusic.videoModel.ItemList;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by popo on 2017/6/20 0020.
 */
public class YugaoFragment extends Fragment implements VideoData.View,SwipeRefreshLayout.OnRefreshListener {
    RecyclerView listView;
    VideoAdapter videoadapter;
    VideoPresenter mPresent;
    SwipeRefreshLayout srfLayout;
    private static int id = 28;
    protected boolean isNull = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_pager, container, false);
        listView = (RecyclerView) view.findViewById(R.id.list);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        List<ItemList> lists = new ArrayList<ItemList>();
        videoadapter = new VideoAdapter(lists);
        listView.setAdapter(videoadapter);

        mPresent = new VideoPresenter((VideoData.View) this);

        srfLayout = (SwipeRefreshLayout) view.findViewById(R.id.srf_layout);
        srfLayout.setOnRefreshListener(this);
        srfLayout.post(() -> onRefresh());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected void initData() {
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));

        List<ItemList> lists = new ArrayList<ItemList>();

        mPresent = new VideoPresenter((VideoData.View) this);
        videoadapter = new VideoAdapter(lists);
        listView.setAdapter(videoadapter);

        srfLayout.setOnRefreshListener(this);
        srfLayout.post(() -> onRefresh());
    }


    @Override
    public void setData(List<ItemList> list) {
        if (list.isEmpty()) {
            isNull = true;
        } else {
            LogUtils.d("VideoPagerFragment", "数据测试" + list.get(0).data.playUrl);
            videoadapter.addAll(list);
        }

    }

    @Override
    public void onRefresh() {
        videoadapter.removeAll();
        mPresent.requestData(id);
        LogUtils.d("VideoPagerFragment-刷新", "id=" + id);
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


    public void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
    }
}
