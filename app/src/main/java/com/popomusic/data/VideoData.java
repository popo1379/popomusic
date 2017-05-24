package com.popomusic.data;

import com.popomusic.bean.MusicBean;
import com.popomusic.presenter.BasePresenter;
import com.popomusic.videoModel.ItemList;
import com.popomusic.videoModel.SectionList;
import com.popomusic.view.BaseView;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public interface VideoData {
    interface View extends BaseView {
        void setData(List<ItemList> list);
        void hideProgress();
        void showProgress();


    }

    interface Presenter  {
        void requestData(int id);
    }
}
