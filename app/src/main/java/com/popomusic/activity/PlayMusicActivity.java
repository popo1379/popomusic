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
import android.support.design.widget.Snackbar;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.popo.popomusic.R;
import com.popomusic.MyApplication;
import com.popomusic.adapter.PlayPagerAdapter;
import com.popomusic.bean.Constant;
import com.popomusic.bean.MusicBean;
import com.popomusic.bean.MusicBeanDao;

import com.popomusic.listener.MyOnPageChangeListeger;
import com.popomusic.listener.MyOnSeekBarChangeListeger;
import com.popomusic.musicService.MediaPlayerService;
import com.popomusic.util.LogUtils;
import com.popomusic.util.SPUtil;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by popo on 2017/4/7 0007.
 */
public class PlayMusicActivity extends BaseActivity {

    private static final String TAG = PlayMusicActivity.class.getName();
    public Messenger mServiceMessenger;//来自服务端的Messenger
    public float mPercent;//进度的百分比
    //  public AlbumFragmentAdapater mAlbumFragmentAdapater;//专辑图片的适配器
    public int mPosition;//传递过来的的歌曲的位置
    public int mPositionPlaying;//正在播放的歌曲的位置
    public String flag;//歌曲集合的类型
    public int currentTime;//实时当前进度
    public int duration;//歌曲的总进度
    public float mPositionOffset;//viewpager滑动的百分比
    public int mState;//viewpager的滑动状态
    public List<MusicBean> mList = new ArrayList<>();
    public int enterX;//传递过来的x坐标，是点击View的中心点的x坐标，揭露动画
    public int enterY;//传递过来的y坐标，是点击View的中心点的y坐标，揭露动画
    public String shareSongName;
    public String shareSingerName;
    public String shareUrl;
    public String shareContent;
    public String songNamePlaying;
    public String singerNamePlaying;
    public MusicBean beanToCollected;//要被收藏的bean
    private Messenger mPlaygingClientMessenger;
     private MyHandler myHandler;
    private PlayPagerAdapter  mAdapater;
    private MyOnSeekBarChangeListeger myOnSeekBarChangeListeger;//seekbar的监听
    public MyOnPageChangeListeger myOnPageChangeListeger;//页面切换监听

    @BindView(R.id.seek_bar) public SeekBar mSeekBar;
    //    @BindView(R.id.tv_song_name)TextView mTvSongName;
//    @BindView(R.id.tv_singer)TextView mTvSinger;
    @BindView(R.id.album_viewpager)ViewPager mAlbumViewPager;
    @BindView(R.id.btn_playorpause)ImageButton mBtnPlay;
    @BindView(R.id.btn_single)ImageButton mPlayMode;
    @BindView(R.id.img_bg) public ImageView mImgBg;
    @BindView(R.id.contanier_play_activity)PercentRelativeLayout mRootLayout;
    @BindView(R.id.btn_like)ImageButton mBtnLike;
    @BindView(R.id.btn_share)ImageButton mBtnShare;
    @BindView(R.id.tv_category)TextView mTvCategory;
    @BindView(R.id.line_playing)View mLine;
    @BindView(R.id.btn_next)ImageButton nextbtn;

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_musicplayer;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        Log.e(TAG, "init");
        bindService(new Intent(getApplicationContext(), MediaPlayerService.class), mServiceConnection, BIND_AUTO_CREATE);
        myOnPageChangeListeger = new MyOnPageChangeListeger(this);
        myOnSeekBarChangeListeger = new MyOnSeekBarChangeListeger(this);
        myHandler = new MyHandler(this);
        mPlaygingClientMessenger = new Messenger(myHandler);
      }

    @Override
    public void initView() {
        //进度条的监听
        mSeekBar.setOnSeekBarChangeListener(myOnSeekBarChangeListeger);

        //滑动播放上/下一首歌曲的监听,实际上传递过去的是歌曲的position
        mAlbumViewPager.addOnPageChangeListener(myOnPageChangeListeger);

          mAdapater = new PlayPagerAdapter(getSupportFragmentManager());
        mAlbumViewPager.setAdapter(mAdapater);
        mAlbumViewPager.setOffscreenPageLimit(6);

        //滑动播放上/下一首歌曲的监听,实际上传递过去的是歌曲的position
        mAlbumViewPager.addOnPageChangeListener(myOnPageChangeListeger);

    }

    @OnClick({R.id.btn_playorpause, R.id.btn_single,R.id.btn_share,R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_playorpause://播放or暂停
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
                break;
            case R.id.btn_single://顺序播放还是单曲循环
                Message msgToServceSingle = Message.obtain();
                msgToServceSingle.what = Constant.PLAYING_ACTIVITY_SINGLE;
                try {
                    mServiceMessenger.send(msgToServceSingle);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_share://暂时代替下载按钮
                Message msgToServceDown = Message.obtain();
                LogUtils.d(TAG,"下载当前音乐发送至service");
                msgToServceDown.what = Constant.DOWN_MUSIC;
                try {
                    mServiceMessenger.send(msgToServceDown);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_next:
                Message msgtoNext=Message.obtain();
                msgtoNext.what=Constant.PLAYING_ACTIVITY_NEXT;
                try {
                    mServiceMessenger.send(msgtoNext);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void initData() {

    }

    /**
     * 修改播放模式的UI
     */
    private void updatePlayMode() {
        int playMode = (int) SPUtil.get(MyApplication.mContext, Constant.SP_PLAY_MODE, 0);
        if (0 == playMode) {
            mPlayMode.setImageResource(R.mipmap.order_mode);
        } else if (1 == playMode) {
            mPlayMode.setImageResource(R.mipmap.single_mode);
        }
    }


    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            Log.e(TAG, "onServiceConnected is ok");
            mServiceMessenger = new Messenger(iBinder);
            //用于在服务端初始化来自客户端的Messenger对象,连接成功的时候，就进行初始化
            if (null != mServiceMessenger) {
                Message msgToService = Message.obtain();
                msgToService.replyTo = mPlaygingClientMessenger;
                msgToService.what = Constant.PLAYING_ACTIVITY;
                if (0 != currentTime) {//当前进度不是0，就更新MediaPlayerService的当前进度
                    msgToService.arg1 = currentTime;
                }
                try {
                    mServiceMessenger.send(msgToService);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            //连接成功的时候，
            mPosition = getIntent().getIntExtra("position", 0);
            flag = getIntent().getStringExtra("flag");
            LogUtils.e(TAG, "传递过来的positon:" + mPosition + " flag:" + flag);
            if (null != mServiceMessenger && null != flag) {
                Message msgToService = Message.obtain();
                msgToService.arg1 = mPosition;
                mList.clear();
                if (flag.equals(Constant.MUSIC_LOCAL)) {
                    mList.addAll(MyApplication.getDaoSession().getMusicBeanDao().queryBuilder().where(MusicBeanDao.Properties.Type.eq(Constant.MUSIC_LOCAL)).list());
                    SPUtil.put(PlayMusicActivity.this, Constant.CATEGOTY, 1);
                     mTvCategory.setText("本地音乐");
                    mLine.setVisibility(View.VISIBLE);
                } else if (flag.equals(Constant.MAIN_RANDOM)) {
                    mList.addAll(MyApplication.getDaoSession().getMusicBeanDao().loadAll());
                    SPUtil.put(PlayMusicActivity.this, Constant.CATEGOTY, 2);
                    mTvCategory.setText("随心听");
                     mLine.setVisibility(View.VISIBLE);
                    Collections.shuffle(mList);
                } else if (flag.equals(Constant.MUSIC_KOREA)) {
                    mList.addAll(MyApplication.getDaoSession().getMusicBeanDao().queryBuilder().where(MusicBeanDao.Properties.Type.eq(Constant.MUSIC_KOREA)).list());
                    SPUtil.put(PlayMusicActivity.this, Constant.CATEGOTY, 3);
                      mTvCategory.setText("韩国");
                      mLine.setVisibility(View.VISIBLE);
                } else if (flag.equals(Constant.MUSIC_ROCK)) {
                    mList.addAll(MyApplication.getDaoSession().getMusicBeanDao().queryBuilder().where(MusicBeanDao.Properties.Type.eq(Constant.MUSIC_ROCK)).list());
                    SPUtil.put(PlayMusicActivity.this, Constant.CATEGOTY, 4);
                      mTvCategory.setText("摇滚");
                     mLine.setVisibility(View.VISIBLE);
                } else if (flag.equals(Constant.MUSIC_VOLKSLIED)) {
                    mList.addAll(MyApplication.getDaoSession().getMusicBeanDao().queryBuilder().where(MusicBeanDao.Properties.Type.eq(Constant.MUSIC_VOLKSLIED)).list());
                    SPUtil.put(PlayMusicActivity.this, Constant.CATEGOTY, 5);
                      mTvCategory.setText("民谣");
                     mLine.setVisibility(View.VISIBLE);
                } else if (flag.equals(Constant.MUSIC_SEARCH)) {
                    mList.addAll((List<MusicBean>) getIntent().getSerializableExtra(Constant.SEARCH_ACTIVITY_DATA_KEY));
                    SPUtil.put(PlayMusicActivity.this, Constant.CATEGOTY, 6);
                     mLine.setVisibility(View.INVISIBLE);
                     mTvCategory.setText("");
                } else if (flag.equals(Constant.MUSIC_Like)) {
                    mList.addAll(MyApplication.getDaoSession().getMusicBeanDao().queryBuilder().where(MusicBeanDao.Properties.IsCollected.eq(true)).list());
                    SPUtil.put(PlayMusicActivity.this, Constant.MUSIC_Like, 7);
                    mLine.setVisibility(View.VISIBLE);
                     mTvCategory.setText("My Love");
                }
                if (null != mList) {
                    //更新专辑图片
                       mAdapater.addList(mList);
                       mAdapater.notifyDataSetChanged();
                    //显示是否收藏了这首歌曲
                       showIsLike();
                    //传递歌曲集合数据
                    Bundle songsData = new Bundle();
                    songsData.putSerializable(Constant.PLAYING_ACTIVITY_DATA_KEY, (Serializable) mList);
                    msgToService.setData(songsData);
                    msgToService.what = Constant.PLAYING_ACTIVITY_INIT;
                    try {
                        mServiceMessenger.send(msgToService);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils.e(TAG, "onServiceDisconnected");
        }

    };

    static class MyHandler extends Handler {
        private WeakReference<PlayMusicActivity> weakActivity;

        public MyHandler(PlayMusicActivity activity) {
            weakActivity = new WeakReference<PlayMusicActivity>(activity);
        }

        @Override
        public void handleMessage(Message msgFromService) {
            PlayMusicActivity activity = weakActivity.get();
            if (null == activity) return;
            switch (msgFromService.what) {
                case Constant.MEDIA_PLAYER_SERVICE_PROGRESS://更新进度条
                    activity.currentTime = msgFromService.arg1;
                    activity.duration = msgFromService.arg2;
                    if (0 == activity.duration) break;
                      activity.mSeekBar.setProgress(activity.currentTime * 100 / activity.duration);
                    break;
                case Constant.MEDIA_PLAYER_SERVICE_SONG_PLAYING:

                    Bundle bundle = msgFromService.getData();
                    activity.mList.clear();
                    activity.mList.addAll((List<MusicBean>) bundle.getSerializable(Constant.MEDIA_PLAYER_SERVICE_MODEL_PLAYING));
                    if (null != activity.mList && 0 < activity.mList.size()) {
                        activity.mAdapater.addList(activity.mList);
                        activity.mAdapater.notifyDataSetChanged();
                        activity.mAlbumViewPager.setCurrentItem(msgFromService.arg1, false);
                    }
                    break;
                case Constant.MEDIA_PLAYER_SERVICE_IS_PLAYING:
                    LogUtils.d(TAG,"playactivity收到了来自service的信息：是否播放音乐更新UI");
                        if (1 == msgFromService.arg1) {//正在播放
                            LogUtils.d(TAG, "play按钮触发");
                            activity.mBtnPlay.setImageResource(R.mipmap.playing_pause);
                        } else {
                            LogUtils.d(TAG, "pause按钮触发");
                            activity.mBtnPlay.setImageResource(R.mipmap.playing_play);
                        }

                             break;

                          case Constant.PLAYING_ACTIVITY_PLAY_MODE://显示播放器的播放模式
                          activity.updatePlayMode();
                             break;
                           case Constant.MEDIA_PLAYER_SERVICE_UPDATE_SONG://播放完成自动播放下一首时，更新正在播放UI
                                int positionPlaying = msgFromService.arg1;
                               activity.mAlbumViewPager.setCurrentItem(positionPlaying, false);
                              LogUtils.e(TAG, "更新正在播放的UI");

                    }
                    super.handleMessage(msgFromService);
            }
        }


    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);

        super.onDestroy();
//        MyApplication.getRefWatcher().watch(this);
    }

    /**
     * 显示是否收藏歌曲
     */
    public void showIsLike() {
        LogUtils.e(TAG,"showLike:" + songNamePlaying +"--"+ singerNamePlaying);
        if (null == songNamePlaying || null == singerNamePlaying || null == mList) return;
        List<MusicBean> list = MyApplication.getDaoSession().getMusicBeanDao().queryBuilder()
                .where(MusicBeanDao.Properties.Singername.eq(singerNamePlaying),
                        MusicBeanDao.Properties.Songname.eq(songNamePlaying)).list();
        if (null != list && 0 <list.size()){
            boolean isCollected = list.get(0).getIsCollected();
            if (isCollected){
                Glide.with(MyApplication.mContext).load(R.mipmap.collected).crossFade().into(mBtnLike);
            }else {
                Glide.with(MyApplication.mContext).load(R.mipmap.no_collected).crossFade().into(mBtnLike);
            }
        }
    }

    /**
     * 收藏歌曲
     */
    private void collect() {
        if (null == mList) return;
        LogUtils.e(TAG,"collected:"+songNamePlaying+"--"+ singerNamePlaying);
        MusicBean newBean = null;
        boolean isCollected = false;
        List<MusicBean> listCollected = MyApplication.getDaoSession().getMusicBeanDao().queryBuilder()
                .where(MusicBeanDao.Properties.Singername.eq(singerNamePlaying),
                        MusicBeanDao.Properties.Songname.eq(songNamePlaying)).list();
        if (null != listCollected && 0 < listCollected.size()) {
            beanToCollected = listCollected.get(0);
            isCollected = beanToCollected.getIsCollected();
        }else {
            newBean = new MusicBean();
            newBean.setSongname(mList.get(mPositionPlaying).getSongname());
            newBean.setSingername(mList.get(mPositionPlaying).getSingername());
            newBean.setAlbumpic_small(mList.get(mPositionPlaying).getAlbumpic_small());
            newBean.setAlbumpic_big(mList.get(mPositionPlaying).getAlbumpic_big());
            newBean.setSeconds(mList.get(mPositionPlaying).getSeconds());
            newBean.setUrl(mList.get(mPositionPlaying).getUrl());
            newBean.setIsCollected(mList.get(mPositionPlaying).getIsCollected());
        }

        if (!isCollected){
            LogUtils.e(TAG,"收藏歌曲");
            Glide.with(MyApplication.mContext).load(R.mipmap.collected).crossFade().into(mBtnLike);
            if (null != beanToCollected) {
                beanToCollected.setIsCollected(true);
                MyApplication.getDaoSession().getMusicBeanDao().update(beanToCollected);
            }
            if (null != newBean){
                newBean.setIsCollected(true);
                MyApplication.getDaoSession().getMusicBeanDao().insertOrReplace(newBean);
            }
            Snackbar.make(mRootLayout,"收藏成功",Snackbar.LENGTH_SHORT).show();
        }else {
            LogUtils.e(TAG,"取消收藏");
            Glide.with(MyApplication.mContext).load(R.mipmap.no_collected).crossFade().into(mBtnLike);
            if (null != beanToCollected) {
                beanToCollected.setIsCollected(false);
                MyApplication.getDaoSession().getMusicBeanDao().update(beanToCollected);
            }
            Snackbar.make(mRootLayout,"取消收藏",Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 分享功能
     */
    private void share() {
        if (null != mList) {
            shareSongName = mList.get(mPositionPlaying).getSongname();
            shareSingerName = mList.get(mPositionPlaying).getSingername();
            shareUrl = mList.get(mPositionPlaying).getUrl();
            shareContent = shareSongName  + "--" + shareSingerName + "\n" + shareUrl;
        }
        if ("".equals(shareContent)) {
            Snackbar.make(mRootLayout, "分享失败", Snackbar.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, shareContent);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "分享到"));
        }
    }
}