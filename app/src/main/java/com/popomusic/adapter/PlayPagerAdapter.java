package com.popomusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.popomusic.bean.MusicBean;
import com.popomusic.fragment.PlayPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by popo on 2017/4/13 0013.
 */
public class PlayPagerAdapter extends FragmentStatePagerAdapter{
    private List<MusicBean> mList = new ArrayList<>();
    public PlayPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addList(List<MusicBean> list){
        mList.clear();
        this.mList.addAll(list);
    }
    @Override
    public Fragment getItem(int position) {
        return PlayPagerFragment.newInstance(mList.get(position));
    }

    @Override
    public int getCount() {
        return mList == null ? 0 :mList.size() ;
    }

}
