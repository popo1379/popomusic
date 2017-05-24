package com.popomusic.presenter;


import android.widget.Toast;

import com.popomusic.MyApplication;
import com.popomusic.activity.JKActivity;
import com.popomusic.api.ApiManager;
import com.popomusic.bean.Constant;
import com.popomusic.bean.MusicBean;
import com.popomusic.data.JKMusicData;

import com.popomusic.util.LogUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.Observable;

import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2017/2/7.
 * 从数据库取数据，就不用这个类了，后期考虑可能分页加载什么的，先不删除了
 */

public class JKPresenter implements JKMusicData.Presenter {
    private static final String TAG = JKPresenter.class.getName();
    private JKMusicData.View mView;
    private JKActivity jkActivity;
    public JKPresenter(JKMusicData.View view) {
        this.mView = view;
    }
    private List<MusicBean> mList;
    @Override
    public void requestData() {
        ApiManager.getApiManager().getQQMusicApiService()
                .getQQMusic(Constant.QQ_MUSIC_APP_ID,Constant.QQ_MUSIC_SIGN,Constant.MUSIC_KOREA)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qqMusicBodyQQMusicResult -> parseData(Constant.MUSIC_KOREA,qqMusicBodyQQMusicResult.getShowapi_res_body().getPagebean().getSonglist()),this:: loadError);
    }

    private  void loadError(Throwable throwable) {
        throwable.printStackTrace();
        LogUtils.w("JKPresenter","loadError RXjava异常！！");
        mView.hideProgress();
        Toast.makeText(MyApplication.mContext,"网络异常！",Toast.LENGTH_SHORT).show();
    }

    private void parseData(String topic,List<MusicBean> list){
        mView.setData(list);
        mView.hideProgress();
        if (null != topic && null != list) {
            Observable.from(list).observeOn(Schedulers.io()).subscribe(bean -> {
                bean.setType(Integer.parseInt(topic));
                //MyApplication.getDaoSession().getMusicBeanDao().queryBuilder().where(MusicBeanDao.Properties.Type.eq(Constant.MUSIC_KOREA)).list().removeAll();
                MyApplication.getDaoSession().getMusicBeanDao().insertOrReplace(bean);
                LogUtils.d("JKPresenter","更新数据库");
            });
        }
    }
}
