package com.sprout.net

import com.baseclient.base.BaseRepository
import com.sprout.api.SproutApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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


    suspend fun getBrand(page:Int,size:Int) = withContext(Dispatchers.IO){
        api.getBrand(page,size)
    }

    suspend fun getGood(page:Int,size:Int) = withContext(Dispatchers.IO){
        api.getGood(page,size)
    }


}