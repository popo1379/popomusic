package com.popomusic.presenter;

import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.Toast;

import com.popomusic.MyApplication;
import com.popomusic.activity.LocalMusicActivity;
import com.popomusic.bean.Constant;
import com.popomusic.bean.MusicBean;
import com.popomusic.data.LocalMusicData;
import com.popomusic.util.LogUtils;
import com.popomusic.videoModel.SectionList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import rx.Subscriber;
import rx.functions.Func1;


/**
 * Created by dingmouren on 2017/1/17.
 */

public class LocalMusicPresenter implements LocalMusicData.Presenter {
    private static final String TAG = LocalMusicPresenter.class.getName();
    private LocalMusicData.View mView;
    private Cursor mCursor;
    List<MusicBean> list = new ArrayList<>();
    public LocalMusicPresenter(LocalMusicData.View view) {
        this.mView = view;
    }

    @Override
    public void requestData() {
        mCursor = ((LocalMusicActivity) mView).getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (null != mCursor) {
            list.clear();
            Observable.just(mCursor).flatMap(new Function<Cursor, ObservableSource<List<MusicBean>>>() {
                @Override
                public ObservableSource<List<MusicBean>> apply(@NonNull Cursor cursor) throws Exception {
                    for (int i = 0; i < mCursor.getCount(); i++) {
                        MusicBean bean = new MusicBean();
                        mCursor.moveToNext();
                        bean.setSongid((int) mCursor.getLong(mCursor.getColumnIndex(MediaStore.Audio.Media._ID)));//音乐id
                        bean.setSongname(mCursor.getString((mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))));//歌曲名称
                        bean.setSingername(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));//歌手
                        bean.setUrl(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DATA)));//歌曲路径
                        bean.setType(Integer.parseInt(Constant.MUSIC_LOCAL));
                        MyApplication.getDaoSession().getMusicBeanDao().insertOrReplace(bean);
                        list.add(bean);
                    }
                    return Observable.just(list);
                }
            }).subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribe(list -> parseData(list));

        }
    }
    private void parseData(List<MusicBean> list){
        mView.setData(list);
    }
}




