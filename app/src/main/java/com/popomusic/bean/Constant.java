package com.popomusic.bean;

/**
 * Created by dingmouren on 2017/1/19.
 */

public class Constant {
    public static final String NOTIFICATION_INTENT_ACTION = "notification_intent_action";
    //PlayingActivity的message.what的属性
    public static final int PLAYING_ACTIVITY_PLAY = 0x10001;
    public static final int PLAYING_ACTIVITY_NEXT = 0x10002;
    public static final int PLAYING_ACTIVITY_SINGLE = 0x10003;
    public static final int PLAYING_ACTIVITY = 0x10004;//初始化PlayingActivity的Messenger对象的标识
    public static final int PLAYING_ACTIVITY_CUSTOM_PROGRESS = 0x10005;//用户拖动进度条时，更新音乐播放器的播放进度
    public static final int PLAYING_ACTIVITY_INIT = 0x10006;//向播放器传递的歌曲集合数据,进行初始化
    public static final int PLAYING_ACTIVITY_PLAYING_POSITION = 0x10007;//播放歌曲的位置
    public static final int PLAYING_ACTIVITY_PLAY_MODE = 0x10008;//播放模式
    public static final String PLAYING_ACTIVITY_DATA_KEY = "playing_activity_data_key";//向播放器传递的歌曲集合数据的key



    //下载
    public static final int DOWN_MUSIC=0*10009;


    //MediaPlayerService的message.what的属性值
    public static final int MEDIA_PLAYER_SERVICE_PROGRESS = 0x20001;
    public static final int MEDIA_PLAYER_SERVICE_SONG_PLAYING = 0x20002;
    public static final int MEDIA_PLAYER_SERVICE_IS_PLAYING = 0x20003;//播放器是否在播放音乐，用于修改PlayingActivity的UI
    public static final int MEDIA_PLAYER_SERVICE_UPDATE_SONG = 0x20004;//通知PalyingActivity跟换专辑图片  歌曲信息等
    public static final String MEDIA_PLAYER_SERVICE_MODEL_PLAYING = "song_playing";//服务端正在播放的歌曲
    //LocalMusicActivity
    public static final int LOCAL_MUSIC_ACTIVITY = 0x30001;
    //JKActivity
    public static final int JK_MUSIC_ACTIVITY = 0x50001;
    //RockActivity
    public static final int ROCK_MUSIC_ACTIVITY = 0x60001;
    //VolksliedActivity
    public static final int VOLKSLIED_MUSIC_ACTIVITY = 0x70001;
    //MainActivity
    public static final int MAIN_ACTIVITY = 0x80001;
    //CollectedActivity
    public static final int COLLECTED_ACTIVITY = 0x90001;
    //LockActivity
    public static final int LOCK_ACTIVITY = 0x100001;
    public static final int LOCK_ACTIVITY_PRE = 0x100002;
    public static final int LOCK_ACTIVITY_NEXT = 0x100003;
    public static final int LOCK_ACTIVITY_PLAY = 0x100004;
    //QQMusicApi相关
    public static final String QQ_MUSIC_APP_ID = "35348";
    public static final String QQ_MUSIC_SIGN = "e2849f7d954e42719b8ca65fdc60a883";
    public static final String QQ_MUSIC_BASE_URL = "http://route.showapi.com/";
    //歌曲类型的type
    public static final String MUSIC_WEST = "3";//欧美
    public static final String MUSIC_INLAND = "5";//内地
    public static final String MUSIC_HONGKANG = "6";//港台
    public static final String MUSIC_KOREA = "5";//韩国
    public static final String MUSIC_JAPAN = "17";//日本
    public static final String MUSIC_VOLKSLIED = "18";//民谣
    public static final String MUSIC_ROCK = "19";//摇滚
    public static final String MUSIC_SALES = "23";//销量
    public static final String MUSIC_HOT = "26";//热歌
    public static final String MUSIC_LOCAL = "27";//本地音乐
    public static final String MUSIC_SEARCH =  "28";//搜索到的音乐
    public static final String MUSIC_Like = "29";//收藏的音乐


    //SharedPrefrence键值
    public static final String SP_PLAY_MODE = "sp_play_mode";//0表示顺序播放，1表示单曲循环

    public static final String MAIN_RANDOM = "main_random";

    //PlayingActivity左上角显示的类型
    public static final String CATEGOTY = "categoty";

    //搜索歌曲使用的Bundle的key
    public static final String SEARCH_ACTIVITY_DATA_KEY = "search_activity_data_key";

    public static final String HEADER_IMG_PATH = "header_img_path";

    public static final String USER_NAME = "user_name";

    //锁屏
    public static final String NOTIFY_SCREEN_OFF = "notify_screen_off";

    public static final String KEY = "aex02165daudxbmtrsul63c";




}
