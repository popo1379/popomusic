package com.popomusic.data;

import com.popomusic.bean.MusicBean;
import com.popomusic.presenter.BasePresenter;
import com.popomusic.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
public interface LocalMusicData {
    interface View extends BaseView{
        void setData(List<MusicBean> list);
        void setRefresh(boolean refresh);
    }

    interface Presenter extends BasePresenter{

    }
}
