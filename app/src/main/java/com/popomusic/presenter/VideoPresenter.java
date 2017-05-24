package com.popomusic.presenter;

import android.provider.ContactsContract;
import android.widget.Toast;

import com.popomusic.MyApplication;
import com.popomusic.activity.JKActivity;
import com.popomusic.api.ApiManager;
import com.popomusic.api.VideoApiManager;
import com.popomusic.bean.Constant;
import com.popomusic.bean.MusicBean;
import com.popomusic.data.JKMusicData;
import com.popomusic.data.VideoData;

import com.popomusic.util.LogUtils;
import com.popomusic.videoModel.Category;
import com.popomusic.videoModel.CategoryInfo;
import com.popomusic.videoModel.Data;
import com.popomusic.videoModel.SectionList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Flowable;
import me.drakeet.multitype.Items;


/**
 * Created by Administrator on 2017/5/20 0020.
 */
public class VideoPresenter implements VideoData.Presenter {
    private static final String HORIZONTAL_SCROLL_CARD_SECTION = "horizontalScrollCardSection";
    private static final String VIDEO_LIST_SECTION = "videoListSection";
    private static final String AUTHOR_SECTION = "authorSection";
    private Items items = new Items();
    private CategoryInfo categoryInfo;
    private static final String TAG = VideoPresenter.class.getName();
    private VideoData.View mView;

    public VideoPresenter(VideoData.View view) {
        this.mView = view;
    }
    private LinkedList<SectionList> mList;
    @Override
    public void requestData(int id) {
        mView.showProgress();
        VideoApiManager.getApiManager().getVideoApiService()
                .findVideo(id)
                .filter(find -> find != null)
                .filter(find -> find.sectionList != null)
                .doOnNext(find -> this.categoryInfo = find.categoryInfo)
                .flatMap(find -> Flowable.fromIterable(find.sectionList))
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(videoResult -> parseData(videoResult),this:: loadError);
    }

    private  void loadError(Throwable throwable) {
        throwable.printStackTrace();
        LogUtils.w("VideoPresenter","loadError RXjava异常！！");
        Toast.makeText(MyApplication.mContext,"网络异常！",Toast.LENGTH_SHORT).show();
        mView.hideProgress();

    }
    private void parseData(SectionList sectionList){
        if (sectionList.type.equals(VIDEO_LIST_SECTION)) {
            mView.setData(sectionList.itemList);
        } mView.hideProgress();
    }
}




