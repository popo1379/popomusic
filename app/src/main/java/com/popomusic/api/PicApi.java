package com.popomusic.api;

import com.popomusic.picBean.Example;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public interface PicApi {
    @GET("852-2")
    Observable<Example>
    getPic(@Query("showapi_appid") String showapi_appid, @Query("showapi_sign") String showapi_sign, @Query("type")String type,@Query("page")String page);

}







