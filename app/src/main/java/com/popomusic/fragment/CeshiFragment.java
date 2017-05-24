package com.popomusic.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.popo.popomusic.R;
import com.popomusic.ui.ViewPagerIndicate;
import com.popomusic.util.LogUtils;
import com.popomusic.util.UIcollector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

/**
 * Created by popo on 2017/4/24 0024.
 */
public class CeshiFragment extends BaseFragment {


    @Override
    protected View initView() {
        view = View.inflate(UIcollector.getActivity(), R.layout.fragment_me, null);

        return view;
    }
    @Override
    protected void initData() {




    }


    }


