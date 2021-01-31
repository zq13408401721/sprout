package com.sprout.ui.home.adapter

import android.content.Context
import android.util.SparseArray
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.shop.base.BaseAdapter
import com.shop.base.IItemClick
import com.shop.ext.findView
import com.sprout.BR
import com.sprout.R
import com.sprout.model.TrendsData

class CityTrendsAdpater(
    context: Context,
    list: List<TrendsData>,
    layouts:SparseArray<Int>,
    click:IItemClick<TrendsData>
):BaseAdapter<TrendsData>(context,list,layouts,click) {
    override fun bindData(binding: ViewDataBinding, data: TrendsData, layId: Int) {
        binding.setVariable(BR.cityClick,itemClick)
        var imgCity = binding.root.findView<ImageView>(R.id.img_city).value
        Glide.with(context).load(data.url).into(imgCity)
    }

    override fun layoutId(position: Int): Int {
        return R.layout.layout_city_trends_item
    }
}