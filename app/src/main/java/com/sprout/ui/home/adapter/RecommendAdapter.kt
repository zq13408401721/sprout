package com.sprout.ui.home.adapter

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.shop.base.BaseAdapter
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.model.TrendsData

class RecommendAdapter(
    context: Context,
    list:List<TrendsData>,
    layouts:SparseArray<Int>,
    click:IItemClick<TrendsData>
):BaseAdapter<TrendsData>(context,list,layouts,click) {
    override fun bindData(binding: ViewDataBinding, data: TrendsData, layId: Int) {
        binding.setVariable(BR.trendsClick,itemClick)
    }

    override fun layoutId(position: Int): Int {
        return R.layout.layout_trends_item
    }

}