package com.popomusic.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.popo.popomusic.R;
import com.popomusic.MyApplication;
import com.popomusic.activity.PlayMusicActivity;
import com.popomusic.adapter.LocalMusicAdapter;
import com.popomusic.bean.Constant;
import com.popomusic.bean.MusicBean;
import com.popomusic.bean.MusicBeanDao;
import com.popomusic.util.UIcollector;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/10 0010.
 */
public class CollectMusicFragment extends BaseFragment {

    RecyclerView mRecycler;
    LocalMusicAdapter mAdapter;
    @Override
    protected View initView() {
        view = View.inflate(UIcollector.getActivity(), R.layout.fragment_collectmusic, null);
        mRecycler=(RecyclerView)view.findViewById(R.id.recycler_love);
        return view;
    }

    @Override
    protected void initData() {
        mAdapter=new LocalMusicAdapter(this.getActivity());

        List<MusicBean> list = MyApplication.getDaoSession().getMusicBeanDao().queryBuilder().where(MusicBeanDao.Properties.IsCollected.eq(true)).list();

        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener((view1, position) -> playSong(position));
        mRecycler.setAdapter(mAdapter);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(UIcollector.getContext()));
    }

    private void playSong(int position){
        Intent intent = new Intent(this.getActivity(), PlayMusicActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("flag", Constant.MUSIC_Like);
        startActivity(intent);
    }
}
