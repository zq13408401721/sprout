package com.sprout.api

import com.baseclient.base.BaseApi
import com.baseclient.model.BaseResp
import com.sprout.model.*
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * 网络请求的Api
 */
interface SproutApi{
    /**
     * 获取频道数据
     */
    @GET("channel/channel")
    suspend fun getChannel():BaseResp<List<ChannelData>>

    @GET("tag/brand")
    suspend fun getBrand(@Query("page") page:Int,@Query("size") size:Int):BaseResp<BrandData>

    @GET("tag/goods")
    suspend fun getGood(@Query("page") page:Int,@Query("size") size:Int):BaseResp<GoodData>

    /**
     * 主题数据
     */
    @GET("theme/getTheme")
    suspend fun getTheme():BaseResp<List<ThemeData>>

    /**
     * 发布动态接口
     */
    @POST("trends/submitTrends")
    @Headers("Content-Type:application/json")
    suspend fun submitTrends(@Body trends:RequestBody):BaseResp<SubmitTrendsData>

    /**
     * 获取动态数据
     */
    @GET("trends/trendsList")
    suspend fun trendsList(@Query("command") command:Int,
                           @Query("channelid") channelid:Int,
                           @Query("page") page:Int,
                           @Query("size") size: Int):BaseResp<List<TrendsData>>







}