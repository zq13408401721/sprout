package com.sprout.app

import android.content.Context
import android.util.Log
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.baseclient.app.BaseApp
import com.baseclient.base.BaseApi
import com.iknow.android.utils.ToastUtil
import iknow.android.utils.BaseUtils
import nl.bravobit.ffmpeg.FFmpeg

class SproutApp:BaseApp() {
    override fun onCreate() {
        super.onCreate()
        //BaseApi.baseUrl = "http://sprout.cdwan.cn/api/" //初始化基础地址
        BaseApi.baseUrl = "http://192.168.3.186:11000/"

        //视频编辑
        BaseUtils.init(this)
        initFFmpegBinary(this)
    }

    //ffmpeg库的初始化
    private fun initFFmpegBinary(context: Context) {
        if (!FFmpeg.getInstance(context).isSupported) {
            Log.e("ZApplication", "Android cup arch not supported!")
        }
    }

    fun initMap(){
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this)
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL)
    }
}