package com.popomusic.data;

import com.popomusic.bean.MusicBean;
import com.popomusic.presenter.BasePresenter;
import com.popomusic.view.BaseView;

import java.util.List;

/**
 * Created by popo on 2017/4/10 0010.
 */
public interface JKMusicData {
    interface View extends BaseView {
    void setData(List<MusicBean> list);
        void hideProgress();
        void showProgress();

    }

    interface Presenter extends BasePresenter {

    }
}
