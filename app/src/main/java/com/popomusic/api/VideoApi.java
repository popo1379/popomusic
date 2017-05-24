package com.popomusic.api;

import com.popomusic.videoModel.Find;
import com.popomusic.videoModel.Interesting;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kaiyan on 2017/5/20 0020.
 */
public interface VideoApi {
    @GET("v3/videos?num=10")
    Flowable<Interesting> getInteresting(
            @Query("start") int start, @Query("categoryId") int categoryId,
            @Query("strategy") String strategy);

    @GET("v3/tag/videos")
    Flowable<Interesting> related(
            @Query("start") int start, @Query("tagId") int id,
            @Query("strategy") String strategy);

    @GET("v3/pgc/videos")
    Flowable<Interesting> relatedHeader(
            @Query("start") int start, @Query("pgcId") int id,
            @Query("strategy") String strategy);

    @GET("v3/categories/detail")
    Flowable<Find> findVideo(@Query("id") int id);

    @GET("v3/categories/videoList")
    Flowable<Interesting> videoList(@Query("id") int id, @Query("start") int start,
                                    @Query("strategy") String strategy);

}
