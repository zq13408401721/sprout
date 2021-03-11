package com.sprout.utils

import android.content.Context
import org.jetbrains.anko.windowManager

/**
 * 屏幕相关的工具类
 */
class ScreenUtils {

    /**
     * 静态方法
     */
    companion object {
        /**
         * 获取屏幕的宽度
         */
        fun getSreenWidth(context:Context):Int{
            return context.windowManager.defaultDisplay.width
        }
    }


}