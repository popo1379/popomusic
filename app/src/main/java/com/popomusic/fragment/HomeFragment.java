package com.popomusic.fragment;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.popo.popomusic.R;
import com.popomusic.util.UIcollector;

import org.androidannotations.internal.core.handler.BeanHandler;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.webview)
    WebView webView;
    String url="http://popo1379.com/";
    @Override
    protected View initView() {
        view = View.inflate(UIcollector.getActivity(), R.layout.fragment_home, null);
        return view;
    }
    @Override
    protected void initData() {

        webView.getSettings().setJavaScriptEnabled(true);
        //防止转跳新网页打开浏览器
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}

