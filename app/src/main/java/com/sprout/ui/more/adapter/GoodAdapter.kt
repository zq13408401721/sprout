package com.sprout.ui.more.adapter

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.shop.base.BaseAdapter
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.model.BrandData
import com.sprout.model.GoodData

/**
 * 商品的adapter
 */
class GoodAdapter(
        context: Context,
        list:List<GoodData.Data>,
        layouts:SparseArray<Int>,
        click: IItemClick<GoodData.Data>
): BaseAdapter<GoodData.Data>(context,list,layouts,click) {
    override fun bindData(binding: ViewDataBinding, data: GoodData.Data, layId: Int) {
        binding.setVariable(BR.goodData,data)
    }

    override fun layoutId(position: Int): Int {
        return R.layout.layout_good_item
    }
}