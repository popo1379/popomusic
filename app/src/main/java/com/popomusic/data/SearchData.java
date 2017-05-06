package com.popomusic.data;

import com.popomusic.bean.SearchBean;
import com.popomusic.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/20 0020.
 */
public interface SearchData {
    interface View extends BaseView {
        void setData(List<SearchBean> list);
    }

    interface Presenter {
        void requestData(String search);
    }
}
