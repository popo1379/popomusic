package com.popomusic.activity;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popo.popomusic.R;
import com.popomusic.adapter.LocalMusicAdapter;
import com.popomusic.bean.Constant;
import com.popomusic.bean.MusicBean;
import com.popomusic.data.LocalMusicData;
import com.popomusic.musicService.MediaPlayerService;
import com.popomusic.presenter.LocalMusicPresenter;
import com.popomusic.util.LogUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by popo on 2017/4/6 0006.
 */
public class LocalMusicActivity extends BaseActivity implements LocalMusicData.View {
    private RecyclerView recyclerView;
    private LocalMusicPresenter mPresenter;
    private LocalMusicAdapter mAdapter;
    private Messenger mServiceMessenger;
    Messenger mMessengerClient;
    private MyHandler myHandler;
    private LinearLayoutManager mLayoutManager;
    public int currentTime;//实时当前进度
    public int duration;//歌曲的总进度
    @BindView(R.id.play_pause)ImageView mBtnPlay;
    @BindView(R.id.title)TextView title;
    @BindView(R.id.artist)TextView artist;
    @BindView(R.id.nowplayingcard)ImageView playcard;
    @BindView(R.id.song_progress_normal)ProgressBar progressBar;
    @BindView(R.id.topContainer)RelativeLayout relativeLayout;
    @BindView(R.id.content)LinearLayout linearLayout;
    @Override
    public void init(Bundle savedInstanceState) {
        bindService(new Intent(this, MediaPlayerService.class),mServiceConnection,BIND_AUTO_CREATE);
        myHandler = new MyHandler(this);
        mMessengerClient = new Messenger(myHandler);
    }

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_localmusic;
    }
    @Override
    public void initView() {
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        mAdapter=new LocalMusicAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        LogUtils.d("LocalMusicActivity","initView()");
        mBtnPlay.setImageResource(R.mipmap.bar_puase);
    }
    @Override
    public void initData() {
        mPresenter = new LocalMusicPresenter((LocalMusicData.View) this);
        mPresenter.requestData();
    }

    @Override
    public void setData(List<MusicBean> list){
        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new LocalMusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                playSong(position);
            }
        });

       mBtnPlay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (null != mServiceMessenger) {
                   Message msgToServicePlay = Message.obtain();
                   msgToServicePlay.arg1 = 0x40001;//表示这个暂停是由点击按钮造成的，
                   msgToServicePlay.what = Constant.PLAYING_ACTIVITY_PLAY;
                   try {
                       mServiceMessenger.send(msgToServicePlay);
                   } catch (RemoteException e) {
                       e.printStackTrace();
                   }
               }
           }
       });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LocalMusicActivity.this,PlayMusicActivity.class));

            }
        });

    }

    @Override
    public void setRefresh(boolean refresh) {

    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceMessenger = new Messenger(iBinder);
            //连接到服务

            if (null != mServiceMessenger){
                Message msgToService = Message.obtain();
                msgToService.replyTo = mMessengerClient;
                msgToService.what = Constant.LOCAL_MUSIC_ACTIVITY;
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

    /**
     * 播放被点击的歌曲
     * @param position
     */
    private void playSong(int position){
        Intent intent = new Intent(this, PlayMusicActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("flag",Constant.MUSIC_LOCAL);
        LogUtils.e("LocalActivity","点击本地的一首音乐");
        startActivity(intent);
    }




    static class MyHandler extends Handler {
        private WeakReference<LocalMusicActivity> weakActivity;
        public MyHandler(LocalMusicActivity activity) {
            weakActivity = new WeakReference<LocalMusicActivity>(activity);
        }

        @Override
        public void handleMessage(Message msgFromService) {
            LocalMusicActivity activity = weakActivity.get();
            if (null == activity) return;
            switch (msgFromService.what){
                case Constant.MEDIA_PLAYER_SERVICE_SONG_PLAYING://通过Bundle传递对象,显示正在播放的歌曲
                   LogUtils.e("LocalActivity","收到消息了");
                    Bundle bundle = msgFromService.getData();
                    activity.mAdapter.showPlaying((MusicBean) bundle.getSerializable(Constant.MEDIA_PLAYER_SERVICE_MODEL_PLAYING));
                    activity.mAdapter.notifyDataSetChanged();
                    MusicBean musicBean=(MusicBean) bundle.getSerializable(Constant.MEDIA_PLAYER_SERVICE_MODEL_PLAYING);
                    activity.title.setText(musicBean.getSongname());
                    activity.artist.setText(musicBean.getSingername());
                    Glide.with(activity).load(musicBean.getAlbumpic_big()).into(activity.playcard);

                    break;
                case Constant.MEDIA_PLAYER_SERVICE_IS_PLAYING:
                    LogUtils.d("LocalMusicActivity","收到了来自service的信息：是否更新UI");
                    if (1 == msgFromService.arg1) {//正在播放
                        LogUtils.d("LocalMusicActivity", "play按钮触发");
                        activity.mBtnPlay.setImageResource(R.mipmap.bar_puase);

                    } else {
                        LogUtils.d("LocalMusicActivity", "pause按钮触发");
                        activity.mBtnPlay.setImageResource(R.mipmap.bar_play);
                    }

                    break;
                case Constant.MEDIA_PLAYER_SERVICE_PROGRESS:
                //    LogUtils.d("LocalMusicActivity", "进度条开始工作");
                    activity.currentTime = msgFromService.arg1;
                    activity.duration = msgFromService.arg2;
                    if (0 == activity.duration) break;
                    activity.progressBar.setProgress(activity.currentTime * 100 / activity.duration);
                    break;
            }
            super.handleMessage(msgFromService);
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        if (null != myHandler){
            myHandler.removeCallbacksAndMessages(null);
            myHandler = null;
        }
        super.onDestroy();
//        MyApplication.getRefWatcher().watch(this);
    }

}
