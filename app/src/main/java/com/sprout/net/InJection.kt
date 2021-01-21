package com.sprout.net

object InJection {

    /**
     * 当前数据仓库
     */
    val repository:SproutRepository by lazy {
        SproutRepository()
    }

}