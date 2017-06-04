package com.popomusic.fragment;

import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.popo.popomusic.R;
import com.popomusic.activity.MainActivty;
import com.popomusic.util.LogUtils;
import com.popomusic.util.UIcollector;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/3 0003.
 */
public class PicXXfragment extends BaseFragment {
@BindView(R.id.picxx_img)
    ImageView imageView;
    String picurl;
    HomeFragment homeFragment;
    protected View initView(){
        if (view == null) {
            view = View.inflate(UIcollector.getActivity(), R.layout.fragment_picxx, null);
        }
        return view;
    }

    @Override
    protected void initData() {
      picurl= ((MainActivty)getActivity()).getPicUrl();
        Glide.with(UIcollector.getContext())
                .load(picurl)
                .into(imageView);

homeFragment=new HomeFragment();
        imageView.setOnClickListener(view1 -> {
            LogUtils.d("PicXXfragment","mageView.OnClickListener");
            ((MainActivty)getActivity()).replaceFragment(homeFragment);
        });

    }




}
