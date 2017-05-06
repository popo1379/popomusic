package com.popomusic.listener;

import android.os.Message;
import android.os.RemoteException;
import android.support.v4.view.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.popo.popomusic.R;
import com.popomusic.MyApplication;
import com.popomusic.activity.PlayMusicActivity;
import com.popomusic.bean.Constant;
import com.popomusic.util.LogUtils;

import java.lang.ref.WeakReference;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by dingmouren on 2017/2/13.
 *  PlayingActivity  ViewPager切换页面的监听
 */

public class MyOnPageChangeListeger implements ViewPager.OnPageChangeListener{
    private static final String TAG = MyOnPageChangeListeger.class.getName();

    private WeakReference<PlayMusicActivity> weakActivity;

    public MyOnPageChangeListeger(PlayMusicActivity activity) {
        weakActivity = new WeakReference<PlayMusicActivity>(activity);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {//arg1:当前页面的位置，也就是position;     arg2:当前页面偏移的百分比;     arg3当前页面偏移的像素位置
        PlayMusicActivity activity = weakActivity.get();
        if (null != activity) {
            activity.mPositionOffset = positionOffset;
            if (position == 0) {//解决第一次进入的时候没有显示模糊效果
                Glide.with(MyApplication.mContext)//底部的模糊效果
                        .load(activity.mList.get(position).getAlbumpic_big())
                        .bitmapTransform(new BlurTransformation(activity, 99))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .crossFade()
                        .into(activity.mImgBg);
                //首次进入获取正在播放歌曲的信息
                activity.songNamePlaying = activity.mList.get(0).getSongname();
                activity.singerNamePlaying = activity.mList.get(0).getSingername();
                activity.showIsLike();
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        PlayMusicActivity activity = weakActivity.get();
        if (null != activity) {
            activity.mPositionPlaying = position;
            activity.songNamePlaying = activity.mList.get(activity.mPositionPlaying).getSongname();
            activity.singerNamePlaying = activity.mList.get(activity.mPositionPlaying).getSingername();
            LogUtils.e(TAG, activity.songNamePlaying + "--------" + activity.singerNamePlaying);
            activity.showIsLike();
            if (2 == activity.mState && 0 < activity.mPositionOffset) {
                Message msgToService = Message.obtain();
                msgToService.arg1 = position;
                msgToService.what = Constant.PLAYING_ACTIVITY_PLAYING_POSITION;
                if (null != activity.mServiceMessenger) {
                    try {
                        activity.mServiceMessenger.send(msgToService);
                        LogUtils.e(TAG, "onPageSelected--postion:" + position + " 发送播放上/下一首歌曲的消息");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
            if (position < activity.mList.size()) {
                Glide.with(activity)//底部的模糊效果
                        .load(activity.mList.get(position).getAlbumpic_big() == null ? R.mipmap.bg2 : activity.mList.get(position).getAlbumpic_big())
                        .bitmapTransform(new BlurTransformation(activity, 99))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .crossFade()
                        .into(activity.mImgBg);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {//state==1表示正在滑动，state==2表示滑动完毕，state==0表示没有动作
       LogUtils.e(TAG, "onPageScrollStateChanged--state:" + state);
        PlayMusicActivity activity = weakActivity.get();
        if (null != activity) {
            activity.mState = state;
        }
    }
}