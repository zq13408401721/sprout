package com.sprout.ui.more.adapter

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.shop.base.BaseAdapter
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.model.ChannelData
import com.sprout.model.ThemeData

class ThemeAdapter(
        context: Context,
        list: List<ThemeData>,
        layouts: SparseArray<Int>,
        click: IItemClick<ThemeData>
): BaseAdapter<ThemeData>(context,list,layouts,click) {
    override fun bindData(binding: ViewDataBinding, data: ThemeData, layId: Int) {
        binding.setVariable(BR.themeClick,itemClick)
    }

    override fun layoutId(position: Int): Int {
        return R.layout.layout_theme_item
    }
}