package com.popomusic.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.popomusic.util.UIcollector;

import butterknife.ButterKnife;


/**
 * Created by popo1379 on 2017/4/6 0006.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static Context context;
    private static Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        init(savedInstanceState);//初始化view之前，进行的操作
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        context = getApplicationContext();
        activity = this;
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
    }
    public void init(Bundle savedInstanceState){}
    public abstract int setLayoutResourceID();
    public abstract void initView();
    public  void initListener(){}
    public abstract void initData();

    public static Context getContext() {
        return context;
    }

    public static Activity getActivity() {
        return activity;
    }

    void Toast(String content) {
        Toast.makeText(UIcollector.getContext(), content, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
