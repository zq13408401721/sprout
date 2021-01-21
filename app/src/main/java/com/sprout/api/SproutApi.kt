package com.sprout.api

import com.baseclient.base.BaseApi
import com.baseclient.model.BaseResp
import com.sprout.model.BrandData
import com.sprout.model.ChannelData
import com.sprout.model.GoodData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 网络请求的Api
 */
interface SproutApi{
    /**
     * 获取频道数据
     */
    @GET("channel/channel")
    suspend fun getChannel():BaseResp<ChannelData>

    @GET("tag/brand")
    suspend fun getBrand(@Query("page") page:Int,@Query("size") size:Int):BaseResp<BrandData>

    @GET("tag/good")
    suspend fun getGood(@Query("page") page:Int,@Query("size") size:Int):BaseResp<GoodData>





}