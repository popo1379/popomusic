package com.popomusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.popomusic.util.UIcollector;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
public abstract class BaseFragment extends Fragment {

    protected boolean isVisible;
    protected static final String FRAGMENT_INDEX = "fragment_index";
    protected int index = 0;
    protected int page = 1;
    protected boolean isNull = false;
    protected View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            index = bundle.getInt(FRAGMENT_INDEX);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initView();
        Log.i("BaseFragment", getClass().getSimpleName());
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    void Toast(String content) {
        Toast.makeText(UIcollector.getContext(), content, Toast.LENGTH_LONG).show();
    }


    private void onVisible() {
        initData();
    }

    private void onInvisible() {
    }

    protected abstract View initView();

    protected abstract void initData();


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
