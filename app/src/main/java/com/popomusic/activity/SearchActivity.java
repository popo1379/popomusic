package com.popomusic.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daidingkang.FlowLayout;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.popo.popomusic.R;
import com.popomusic.MyApplication;
import com.popomusic.adapter.SearchAdapter;
import com.popomusic.bean.Constant;
import com.popomusic.bean.MusicBean;
import com.popomusic.bean.SearchBean;
import com.popomusic.bean.SearchNameBean;
import com.popomusic.data.SearchData;
import com.popomusic.presenter.SearchPresenter;
import com.popomusic.util.LogUtils;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by popo1379 on 2017/4/20 0020.
 */
public class SearchActivity extends BaseActivity implements SearchData.View {
    private static final String TAG = SearchActivity.class.getName();
    private List<SearchNameBean> searchNameBeanList;
    @BindView(R.id.search_bar)
    public MaterialSearchBar mSearchBar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    @BindView(R.id.progressbar)
    public ProgressBar mProgressBar;
    @BindView(R.id.flowlayout)
    FlowLayout flowLayout;
    @BindView(R.id.line)
    LinearLayout linearLayout;
    @BindView(R.id.empty_button)
    Button empty_button;

    private SearchAdapter mAdapter;
    public SearchPresenter mPresenter;
    public MusicBean mMusicBean;
    public List<MusicBean> mList;
    private MyOnSearchActionListener myOnSearchActionListener;

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        myOnSearchActionListener = new MyOnSearchActionListener(this);
    }

    @Override
    public void initView() {
        mSearchBar.enableSearch();//输入状态
        mAdapter = new SearchAdapter(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(mAdapter);
        mPresenter = new SearchPresenter((SearchData.View) this);
        initHistoryTag();

    }


    @Override
    public void initListener() {

        mAdapter.setOnItemClickListener(bean -> playSong(bean));

        mSearchBar.setOnSearchActionListener(myOnSearchActionListener);

        //清空搜索历史按钮
        empty_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.getDaoSession1().getSearchNameBeanDao().deleteAll();
                linearLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void initData() {
        //mAdapter.setList(queryAllData());
    }

    @Override
    public void setData(List<SearchBean> list) {
        LogUtils.e(TAG, "setData--" + list.toString());
        if (0 == list.size()) {
            mTvEmpty.setVisibility(android.view.View.VISIBLE);
        } else {
            mTvEmpty.setVisibility(android.view.View.GONE);
        }
        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(android.view.View.INVISIBLE);
       linearLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * 播放被点击的歌曲
     */
    private void playSong(SearchBean bean) {
        if (null != bean) {
            mMusicBean = new MusicBean();
            mList = new ArrayList<>();
            mMusicBean.setUrl(bean.getM4a());
            mMusicBean.setSeconds(0);
            mMusicBean.setAlbumpic_big(bean.getAlbumpic_big());
            mMusicBean.setAlbumpic_small(bean.getAlbumpic_small());
            mMusicBean.setSongname(bean.getSongname());
            mMusicBean.setSingername(bean.getSingername());
            mList.add(mMusicBean);
            if (null != mList && 0 < mList.size()) {
                Intent intent = new Intent(this, PlayMusicActivity.class);
                intent.putExtra(Constant.SEARCH_ACTIVITY_DATA_KEY, (Serializable) mList);
                intent.putExtra("flag", Constant.MUSIC_SEARCH);
                startActivity(intent);
                LogUtils.d(TAG,"点击查找到的歌曲");
                new Handler().postDelayed(() -> finish(), 800);
            }
        }
    }

    /**
     * 历史搜索
     */
    private void initHistoryTag() {
        searchNameBeanList= MyApplication.getDaoSession1().getSearchNameBeanDao().loadAll();
        if (MyApplication.getDaoSession1().getSearchNameBeanDao() != null && searchNameBeanList != null && searchNameBeanList.size() != 0) {
            List<String> historyList = new ArrayList<>();
            for (SearchNameBean searchHistory : searchNameBeanList) {
                historyList.add(searchHistory.getSearchname());
            }
            linearLayout.setVisibility(View.VISIBLE);
            flowLayout.setListData(historyList);
            flowLayout.setOnTagClickListener(new FlowLayout.OnTagClickListener() {
                @Override
                public void TagClick(String text) {
                    // MyApplication.toastor.showToast(text);
                    mPresenter.requestData(text);
                }
            });
        }else {
            linearLayout.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 搜索栏监听类
     */
    public class MyOnSearchActionListener implements MaterialSearchBar.OnSearchActionListener {
        private WeakReference<SearchActivity> weakActivity;
        private InputMethodManager inputMethodManager;

        public MyOnSearchActionListener(SearchActivity activity) {
            weakActivity = new WeakReference<SearchActivity>(activity);
            inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        }

        @Override
        public void onSearchStateChanged(boolean b) {
            SearchActivity activity = weakActivity.get();
            if (null == activity) return;
            if (TextUtils.isEmpty(activity.mSearchBar.getText().trim())) {
                activity.finish();
            }
        }

        @Override
        public void onSearchConfirmed(CharSequence charSequence) {
            SearchActivity activity = weakActivity.get();
            if (null == activity) return;
            activity.mProgressBar.setVisibility(android.view.View.VISIBLE);
            //activity.flowLayout.setVisibility(View.INVISIBLE);
            activity.mPresenter.requestData(String.valueOf(charSequence));
            //输入的内容添加至数据库
           insertData(charSequence);

            if (null != inputMethodManager) {
                inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
            }
        }

        @Override
        public void onButtonClicked(int i) {
        }
        //输入的内容添加至数据库
        private void insertData(CharSequence charSequence) {
            SearchNameBean searchNameBean=new SearchNameBean(null,String.valueOf(charSequence));
            MyApplication.getDaoSession1().getSearchNameBeanDao().insert(searchNameBean);
        }
        //全部查询
        private List<SearchNameBean> queryAllData(CharSequence charSequence){
            List<SearchNameBean> searchNameBeanList= MyApplication.getDaoSession1().getSearchNameBeanDao().loadAll();
            return searchNameBeanList;
        }
    }
}