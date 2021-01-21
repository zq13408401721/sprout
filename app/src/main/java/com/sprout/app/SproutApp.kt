package com.sprout.app

import com.baseclient.app.BaseApp
import com.baseclient.base.BaseApi

class SproutApp:BaseApp() {
    override fun onCreate() {
        super.onCreate()
        BaseApi.baseUrl = "http://sprout.cdwan.cn/api/" //初始化基础地址
    }
}