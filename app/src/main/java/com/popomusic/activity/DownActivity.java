package com.popomusic.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popo.popomusic.R;
import com.popomusic.bean.Constant;
import com.popomusic.musicService.MediaPlayerService;
import com.popomusic.util.LogUtils;

import butterknife.BindView;

/**
 * Created by popo on 2017/5/2 0002.
 */
public class DownActivity extends BaseActivity{
    private Messenger mServiceMessenger;
    @BindView(R.id.down_recycler)
    RecyclerView recyclerView;

    public int setLayoutResourceID() {
        return R.layout.activity_down;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        bindService(new Intent(this, MediaPlayerService.class), mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {


    }
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceMessenger = new Messenger(iBinder);
            //连接到服务
            if (null != mServiceMessenger) {
                Message msgToService = Message.obtain();
                msgToService.replyTo = mMessengerClient;
                msgToService.what = Constant.MAIN_ACTIVITY;
                try {
                    mServiceMessenger.send(msgToService);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    Messenger mMessengerClient = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msgFromService) {
            switch (msgFromService.what) {
                case Constant.MEDIA_PLAYER_SERVICE_IS_PLAYING://正在播放
                    LogUtils.d("DownActivity","服务管理者接收到了信息！正在播放");
                    if (1 == msgFromService.arg1) {
                    } else if (0 == msgFromService.arg1) {
                    }
                    break;
            }
            super.handleMessage(msgFromService);
        }
    });
    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
//        MyApplication.getRefWatcher().watch(this);
    }
}