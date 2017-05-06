package com.popomusic.listener;

import android.os.Message;
import android.os.RemoteException;
import android.widget.SeekBar;


import com.popomusic.activity.PlayMusicActivity;
import com.popomusic.bean.Constant;

import java.lang.ref.WeakReference;

/**
 * Created by dingmouren on 2017/2/13.
 */

public class MyOnSeekBarChangeListeger implements SeekBar.OnSeekBarChangeListener {
    private WeakReference< PlayMusicActivity> weakActivity;
    public MyOnSeekBarChangeListeger( PlayMusicActivity activity) {
        weakActivity = new WeakReference<PlayMusicActivity>(activity);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
       PlayMusicActivity activity = weakActivity.get();
        if (fromUser && null != activity) {//判断来自用户的滑动
            activity.mPercent = (float) progress * 100 / (float) activity.mSeekBar.getMax();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //用户松开SeekBar，通知MediaPlayerService更新播放器的进度，解决拖动过程中卡顿的问题
        PlayMusicActivity activity = weakActivity.get();
        if (null != activity) {
            Message msgToMediaPlayerService = Message.obtain();
            msgToMediaPlayerService.what = Constant.PLAYING_ACTIVITY_CUSTOM_PROGRESS;
            msgToMediaPlayerService.arg1 = (int) activity.mPercent;
            try {
                activity.mServiceMessenger.send(msgToMediaPlayerService);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
