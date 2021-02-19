package com.sprout.net

import com.baseclient.base.BaseRepository
import com.shop.utils.MyMmkv
import com.sprout.api.SproutApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody

/**
 * Sprout数据仓库
 */
class SproutRepository: BaseRepository<SproutApi>(SproutApi::class.java) {

    /**
     * 获取频道数据接口 给VM调用
     */
    suspend fun getChannel() = withContext(Dispatchers.IO){
        api.getChannel()
    }

    /**
     * 获取标签的品牌数据
     */
    suspend fun getBrand(page:Int,size:Int) = withContext(Dispatchers.IO){
        api.getBrand(page,size)
    }

    /**
     * 获取标签的商品数据
     */
    suspend fun getGood(page:Int,size:Int) = withContext(Dispatchers.IO){
        api.getGood(page,size)
    }

    /**
     * 获取主题数据
     */
    suspend fun getTheme() = withContext(Dispatchers.IO){
        api.getTheme()
    }

    /**
     * 提交动态数据
     */
    suspend fun submitTrends(requestBody: RequestBody) = withContext(Dispatchers.IO){
        api.submitTrends(requestBody)
    }

    /**
     * 获取动态列表数据
     */
    suspend fun trendsList(command:Int,channelid:Int,page:Int,size:Int) = withContext(Dispatchers.IO){
        api.trendsList(command,channelid,page,size)
    }


}