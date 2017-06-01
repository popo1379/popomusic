package com.popomusic.data;

import com.popomusic.picBean.Contentlist;
import com.popomusic.picBean.Picbean;
import com.popomusic.videoModel.ItemList;
import com.popomusic.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public interface PicData {
    interface View extends BaseView {
        void setData(List<Contentlist> list);
        void hideProgress();
        void showProgress();
    }
    interface Presenter  {
        void requestData();
    }
    }
