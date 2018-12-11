package com.din.retrofitdemo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import rx.Observable

/**
 * @author: dinzhenyan
 * @time: 2018/12/10 下午8:49
 */
interface Api {

    /**
     * @param url 网址
     * @return 返回类型
     */
    @GET
    // 请求方式
    fun getCall(@Url url: String): Call<String>


    /**
     * @param page  网址参数
     * @param count 网址参数
     * @return 返回类型
     */
    @GET("getTangPoetry")
    // 请求方式
    fun getPoetry(@Query("page") page: String, @Query("count") count: String): Call<Poetry>


    /**
     * @param page  网址参数
     * @param count 网址参数
     * @return 返回类型
     */
    @GET("getTangPoetry")
    // 请求方式
    fun getPoetries(@Query("page") page: String, @Query("count") count: String): Observable<Poetry>

}
