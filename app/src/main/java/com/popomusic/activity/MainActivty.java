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
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popo.popomusic.R;
import com.popomusic.bean.Constant;
import com.popomusic.fragment.MeFragment;
import com.popomusic.fragment.HomeFragment;
import com.popomusic.fragment.MusicFragment;
import com.popomusic.fragment.VideoHomeFragment;
import com.popomusic.fragment.VideoPagerFragment;
import com.popomusic.musicService.MediaPlayerService;
import com.popomusic.util.LogUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

/**
 * Created by popo on 2017/4/6 0006.
 */
public class MainActivty extends BaseActivity {
    private Toolbar toolbar;
    private BottomBar bottomBar;
    private Messenger mServiceMessenger;
    @BindView(R.id.fab_music)
    FloatingActionButton mFabMusic;
    String picUrl;
    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        bindService(new Intent(this, MediaPlayerService.class), mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("泡泡影音");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public void initListener() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab1:
                        replaceFragment(new MusicFragment());
                        break;
                    case R.id.tab2:
                        replaceFragment(new VideoHomeFragment());
                        break;
                    case R.id.tab3:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.tab4:
                        replaceFragment(new MeFragment());

                }
            }
        });

        mFabMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d("MainActivity", "mFabMusic to startActivity");
                startActivity(new Intent(MainActivty.this, PlayMusicActivity.class));
            }
        });
        mFabMusic.setVisibility(View.GONE);

    }

    @Override
    public void initData() {

    }


    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commit();
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
                    LogUtils.d("MianActivity", "服务管理者接收到了信息！正在播放");
                    if (1 == msgFromService.arg1) {
                        mFabMusic.setVisibility(View.VISIBLE);
                        //  mFabMusic.setImageResource(R.mipmap.play);
                        Glide.with(MainActivty.this).load(R.mipmap.playing).asGif().diskCacheStrategy(DiskCacheStrategy.NONE).into(mFabMusic);
                    } else if (0 == msgFromService.arg1) {
                        mFabMusic.setVisibility(View.GONE);
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



    public String getPicUrl(){
        return picUrl;
    }

    public void setPicUrl(String picUrl){
        this.picUrl=picUrl;
    }


}