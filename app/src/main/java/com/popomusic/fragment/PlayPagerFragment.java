package com.popomusic.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popo.popomusic.R;
import com.popomusic.MyApplication;
import com.popomusic.bean.MusicBean;
import com.popomusic.util.LogUtils;
import com.popomusic.util.UIcollector;

import butterknife.BindView;

/**
 * Created by popo on 2017/4/13 0013.
 */
public class PlayPagerFragment extends BaseFragment {

    private static final String TAG = PlayPagerFragment.class.getName();
    private static final String BEAN = "bean";
    @BindView(R.id.img_album)
    ImageView mImgAlbum;
  TextView mTvLyric;
 TextView mSongName;
   TextView mSingerName;
    private MusicBean mBean;

    public static PlayPagerFragment newInstance(MusicBean bean){
        PlayPagerFragment fragment = new PlayPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BEAN,bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initView() {
        if (view == null) {
            view = View.inflate(UIcollector.getActivity(), R.layout.fragment_cover, null);
            mBean = (MusicBean) getArguments().getSerializable(BEAN);
            LogUtils.d("PlayPagerFragment","Songname:"+mBean.getSongname());

            mSongName=(TextView)view.findViewById(R.id.tv_song_name);
            mSongName.setText(mBean.getSongname());

            mSingerName=(TextView)view.findViewById(R.id.tv_singer);
            mSingerName.setText(mBean.getSingername());
        }
        return view;
    }

        @Override
        protected void initData() {
            if (null != mBean) {
                Glide.with(MyApplication.mContext).load(mBean.getAlbumpic_big()).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).into(mImgAlbum);
            }
        }

            public void onDestroy() {
                super.onDestroy();
            }
    }





























