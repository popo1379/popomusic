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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.popo.popomusic.R;
import com.popomusic.bean.Constant;
import com.popomusic.fragment.BaseFragment;
import com.popomusic.fragment.CeshiFragment;
import com.popomusic.fragment.CollectMusicFragment;
import com.popomusic.fragment.CollectVideoFragment;
import com.popomusic.fragment.VideoPagerFragment;
import com.popomusic.musicService.MediaPlayerService;
import com.popomusic.util.LogUtils;

import org.androidannotations.internal.core.handler.BeanHandler;
import org.androidannotations.internal.core.model.AndroidRes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/10 0010.
 */
public class CollectActivity extends BaseActivity {
    @BindView(R.id.recycler_lovevideo)
    RelativeLayout re_lovevideo;
    @BindView(R.id.recycler_lovemusic)
    RelativeLayout re_lovemusic;
    @BindView(R.id.fl_collect)
    FrameLayout frameLayout;
    @BindView(R.id.text_music)
    TextView tv_music;
    @BindView(R.id.text_video)
    TextView tv_video;
    private Messenger mServiceMessenger;

    public int setLayoutResourceID() {
        return R.layout.activity_collect;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        bindService(new Intent(this, MediaPlayerService.class), mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void initView() {
    replaceFragment(new CollectMusicFragment());
        tv_music.setTextSize(20);
        tv_music.setTextColor(this.getResources().getColor(R.color.redtext));

    }

    @OnClick({R.id.recycler_lovevideo,R.id.recycler_lovemusic})
    public void onClick(View view) {
        switch (view.getId()) {
          case  R.id.recycler_lovevideo:
              replaceFragment(new CollectVideoFragment());
              tv_video.setTextSize(20);
              tv_video.setTextColor(this.getResources().getColor(R.color.redtext));
              tv_music.setTextSize(15);
              tv_music.setTextColor(this.getResources().getColor(R.color.playing_activity_song_bac));

              break;
            case  R.id.recycler_lovemusic:
                replaceFragment(new CollectMusicFragment());
                tv_video.setTextSize(15);
                tv_video.setTextColor(this.getResources().getColor(R.color.playing_activity_song_bac));
                tv_music.setTextSize(20);
                tv_music.setTextColor(this.getResources().getColor(R.color.redtext));
                break;
        }
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
                msgToService.what = Constant.COLLECTED_ACTIVITY;
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
                    LogUtils.d("CollectActivity","服务管理者接收到了信息！正在播放");
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


    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_collect, fragment).commit();
    }
}
