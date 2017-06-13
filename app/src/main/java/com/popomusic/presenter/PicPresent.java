package com.popomusic.presenter;

import android.widget.Toast;

import com.popomusic.MyApplication;
import com.popomusic.activity.JKActivity;
import com.popomusic.api.ApiManager;
import com.popomusic.api.PicApiManager;
import com.popomusic.bean.Constant;
import com.popomusic.bean.MusicBean;
import com.popomusic.data.JKMusicData;
import com.popomusic.data.PicData;
import com.popomusic.picBean.Contentlist;
import com.popomusic.picBean.Picbean;
import com.popomusic.util.LogUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class PicPresent implements PicData.Presenter {
    private PicData.View mView;
    public PicPresent(PicData.View view) {
        this.mView = view;
    }
    @Override
    public void requestData() {
        mView.showProgress();
        PicApiManager.getApiManager().getApiService()
                .getPic(Constant.QQ_MUSIC_APP_ID,Constant.QQ_MUSIC_SIGN,""+4001,""+1)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Example -> parseData(Example.getShowapiResBody().getPagebean().getContentlist()),this:: loadError);
    }

    private  void loadError(Throwable throwable) {
        throwable.printStackTrace();
        LogUtils.w("PicPresent","loadError RXjava异常！！");
        mView.hideProgress();
        Toast.makeText(MyApplication.mContext,"网络异常！",Toast.LENGTH_SHORT).show();
    }

    private void parseData(List<Contentlist> list){
        mView.setData(list);
        mView.hideProgress();
    }
}
