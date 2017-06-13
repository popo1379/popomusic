package com.popomusic.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.popo.popomusic.R;
import com.popomusic.MyApplication;
import com.popomusic.adapter.VideoCollectAdapter;
import com.popomusic.util.UIcollector;
import com.popomusic.videoModel.VideoBean;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/10 0010.
 */
public class CollectVideoFragment extends BaseFragment {
    @BindView(R.id.recycler_lovevideo)
    RecyclerView mRecycler;
    List<VideoBean> mlist;
    VideoCollectAdapter mAdapter;
    @Override
    protected View initView() {
        view = View.inflate(UIcollector.getActivity(), R.layout.fragment_collectvideo, null);
        return view;
    }
    @Override
    protected void initData() {

    mlist= MyApplication.getVideoDaoSession().getVideoBeanDao().loadAll();
        if (null!= mlist){
        mAdapter=new VideoCollectAdapter(mlist);
        mRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(mAdapter);}
        else {
            Toast("这里啥也没有，赶快收藏你心爱的视频吧！");
        }
    }







}
