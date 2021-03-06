package com.popomusic.fragment;

import android.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.popo.popomusic.R;
import com.popomusic.activity.MainActivty;
import com.popomusic.adapter.PicAdapter;
import com.popomusic.data.PicData;
import com.popomusic.data.VideoData;
import com.popomusic.picBean.Contentlist;
import com.popomusic.presenter.PicPresent;
import com.popomusic.ui.MyItemDecoration;
import com.popomusic.util.LogUtils;
import com.popomusic.util.UIcollector;
import com.popomusic.videoModel.ItemList;

import org.androidannotations.internal.core.handler.BeanHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by popo on 2017/5/17 0017.
 */
public class HomeFragment extends BaseFragment implements PicData.View,SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.pic_recyc)
    RecyclerView recyclerView;
    PicPresent picPresent;
    @BindView(R.id.pic_swipe)
    SwipeRefreshLayout srfLayout;
    PicAdapter madapter;
    List<Contentlist> list=new ArrayList<Contentlist>();
    FragmentManager fm;
    PicXXfragment picXXfragment;
    @Override
    protected View initView() {
        view = View.inflate(UIcollector.getActivity(), R.layout.fragment_pic, null);
        return view;
    }
    @Override
    protected void initData() {
        recyclerView.setHasFixedSize(true); // 设置固定大小
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new MyItemDecoration(this.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // 默认动画

        madapter=new PicAdapter(list);
        recyclerView.setAdapter(madapter);

        picPresent=new PicPresent((PicData.View) this);
        picPresent.requestData();
        srfLayout.setOnRefreshListener(this);
        srfLayout.post(() -> onRefresh());

        fm = getActivity().getFragmentManager();
        picXXfragment=new PicXXfragment();
        madapter.setOnItemClickListener((view1, position) -> {
            if (list.get(position).getList().get(0).getBig()==null){
                Toast("数据源故障");
            }
            else ((MainActivty)getActivity()).setPicUrl(list.get(position).getList().get(0).getBig());
            ((MainActivty)getActivity()).replaceFragment( picXXfragment);
        });
    }

    @Override
    public void onRefresh() {
        madapter.removeAll();
        picPresent.requestData();
        madapter.notifyDataSetChanged();
    }

    @Override
    public void setData(List<Contentlist> list){
        madapter.addAll(list);
        this.list=list;
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











}

