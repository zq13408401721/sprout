package com.sprout.ui.more.adapter

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.shop.base.BaseAdapter
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.model.ChannelData

/**
 * 频道选择列表
 */
class ChannelAdapter(
        context: Context,
        list: List<ChannelData>,
        layouts:SparseArray<Int>,
        click:IItemClick<ChannelData>
): BaseAdapter<ChannelData>(context,list,layouts,click) {
    override fun bindData(binding: ViewDataBinding, data: ChannelData, layId: Int) {
        binding.setVariable(BR.channelClick,itemClick)
    }

    override fun layoutId(position: Int): Int {
        return R.layout.layout_channel_item

    }
}