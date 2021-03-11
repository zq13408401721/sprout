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
    @GET("api/channel/channel")
    suspend fun getChannel():BaseResp<List<ChannelData>>

    @GET("api/tag/brand")
    suspend fun getBrand(@Query("page") page:Int,@Query("size") size:Int):BaseResp<BrandData>

    @GET("api/tag/goods")
    suspend fun getGood(@Query("page") page:Int,@Query("size") size:Int):BaseResp<GoodData>

    /**
     * 主题数据
     */
    @GET("api/theme/getTheme")
    suspend fun getTheme():BaseResp<List<ThemeData>>

    /**
     * 发布动态接口
     */
    @POST("api/trends/submitTrends")
    @Headers("Content-Type:application/json")
    suspend fun submitTrends(@Body trends:RequestBody):BaseResp<SubmitTrendsData>

    /**
     * 获取动态数据
     */
    @GET("api/trends/trendsList")
    suspend fun trendsList(@Query("command") command:Int,
                           @Query("channelid") channelid:Int,
                           @Query("page") page:Int,
                           @Query("size") size: Int):BaseResp<List<TrendsData>>




    //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
    /**
     * 获取微信的access_token
     */
    @GET("sns/oauth2/access_token")
    @Headers("newurl:https://api.weixin.qq.com/")
    suspend fun getWxAccessToken(@QueryMap map:Map<String,String>):WxAccessToken

    /**
     * 检查微信的token有效性
     */
    @GET("sns/auth")
    @Headers("newurl:https://api.weixin.qq.com/")
    suspend fun authToken(@Query("access_token") accesstoken:String,@Query("openid") openid:String):WxAuth


    /**
     * 刷新微信token
     * grant_type:refresh_token
     */
    @GET("sns/oauth2/access_token")
    @Headers("newurl:https://api.weixin.qq.com/")
    suspend fun refreshToken(@Query("appid") appid:String,@Query("grant_type") grant_type:String,@Query("refresh_token") refreshToken:String):WxAccessToken

    /**
     * 微信用户信息
     *
     */
    @GET("sns/userinfo")
    @Headers("newurl:https://api.weixin.qq.com/")
    suspend fun wxUserInfo(@Query("access_token") accesstoken: String,@Query("openid") openid:String,@Query("lang") lang:String="zh_CN"):WxUserInfo

    /**
     * 获取订单信息
     */
    @GET("wxorder")
    @Headers("newurl:http://192.168.3.76:9000/")
    suspend fun wxOrder():WxPrepayBean

}