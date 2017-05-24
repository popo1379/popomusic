package com.popomusic.presenter;

import android.widget.Toast;

import com.popomusic.MyApplication;
import com.popomusic.activity.MainActivty;
import com.popomusic.api.ApiManager;
import com.popomusic.bean.Constant;
import com.popomusic.bean.SearchBean;
import com.popomusic.data.SearchData;
import com.popomusic.util.LogUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2017/2/10.
 */

public class SearchPresenter implements SearchData.Presenter {

    private static final String TAG = SearchPresenter.class.getName();
    private SearchData.View mView;
    public SearchPresenter(SearchData.View view) {
        this.mView = view;
    }

    @Override
    public void requestData(String search) {
      LogUtils.e(TAG,"requestData--"+ search );
        if (search.equals("")){
            Toast.makeText(MyApplication.mContext,"你就不能写上歌曲名儿呀╭(╯^╰)╮",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            ApiManager.getApiManager().getQQMusicApiService().searchQQMusic(Constant.QQ_MUSIC_APP_ID,Constant.QQ_MUSIC_SIGN,search,"1")
                    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribe(qqMusicBodyQQMusicResult ->{parseData(qqMusicBodyQQMusicResult.getShowapi_res_body().getPagebean().getContentlist());
                    },this::loadError );
        } catch (Exception e) {
            LogUtils.e(TAG,e.getMessage());
        }
    }

    private  void loadError(Throwable throwable) {
        throwable.printStackTrace();
        LogUtils.e(TAG,throwable.getMessage());
            Toast.makeText(MyApplication.mContext,"没有网络了哟，请检查网络设置",Toast.LENGTH_SHORT).show();
    }

    private void parseData(List<SearchBean> list){
        mView.setData(list);
    }
}
