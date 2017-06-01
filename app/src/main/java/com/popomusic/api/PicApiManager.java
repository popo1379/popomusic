package com.popomusic.api;

import com.popomusic.bean.Constant;
import com.popomusic.util.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by popo on 2017/5/31 0031.
 */
public class PicApiManager {
    private static final int READ_TIME_OUT = 3;

    private static final int CONNECT_TIME_OUT = 3;

    private PicApi picApiService;

    //构造函数私有化，只创建一个实例
    private PicApiManager(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> showRetrofitLog(message)).setLevel(HttpLoggingInterceptor.Level.BODY);//打印请求日志
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit1 = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.Show_BASE_URL)
                .build();
        picApiService = retrofit1.create(PicApi.class);
    }

    /**
     * 单例对象持有者
     */
    private static class SingletonHolder{
        private static final PicApiManager INSTANCE = new PicApiManager();
    }

    /**
     * 获取ApiManager单例对象
     */
    public static PicApiManager getApiManager(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 打印日志
     * 返回的是json，就打印格式化好了的json，不是json就原样打印
     * @param message
     */
    private void showRetrofitLog(String message){
        if (message.startsWith("{")){
            LogUtils.d("Retrofit:",message);
        }else {
            LogUtils.e("Retrofit:",message);
        }
    }

    public PicApi getApiService(){
        return picApiService;
    }
}
