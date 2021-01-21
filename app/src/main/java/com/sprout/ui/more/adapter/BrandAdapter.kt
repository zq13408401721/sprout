package com.sprout.ui.more.adapter

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.shop.base.BaseAdapter
import com.shop.base.IItemClick
import com.sprout.model.BrandData

/**
 * 品牌的adapter
 */
class BrandAdapter(
        context: Context,
        list:List<BrandData.Data>,
        layouts:SparseArray<Int>,
        click: IItemClick<BrandData.Data>
): BaseAdapter<BrandData.Data>(context,list,layouts,click) {
    override fun bindData(binding: ViewDataBinding, data: BrandData.Data, layId: Int) {
        TODO("Not yet implemented")
    }

    override fun layoutId(position: Int): Int {
        TODO("Not yet implemented")
    }
}