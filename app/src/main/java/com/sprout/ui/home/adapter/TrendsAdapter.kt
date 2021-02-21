package com.sprout.ui.home.adapter

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.shop.base.BaseAdapter
import com.shop.base.IItemClick
import com.shop.ext.findView
import com.sprout.BR
import com.sprout.R
import com.sprout.app.Global
import com.sprout.model.TrendsData

class TrendsAdapter(
    context: Context,
    list:List<TrendsData>,
    layouts:SparseArray<Int>,
    click: IItemClick<TrendsData>
):BaseAdapter<TrendsData>(context,list,layouts,click) {
    override fun bindData(binding: ViewDataBinding, data: TrendsData, layId: Int) {
        binding.setVariable(BR.trendsClick,itemClick)
        var img = binding.root.findView<ImageView>(R.id.img_trends).value
        Glide.with(context).load(data.url).into(img)
        var imgType = binding.root.findView<ImageView>(R.id.img_type).value
        imgType.visibility = View.GONE
        when(data.type){
            Global.TYPE_IMG -> {
                if(data.res.size > 1){
                    imgType.setImageResource(R.mipmap.icon_photos)
                    imgType.visibility = View.VISIBLE
                }else{
                    imgType.visibility = View.GONE
                }
            }
            Global.TYPE_VIDEO -> {
                imgType.setImageResource(R.mipmap.icon_video)
                imgType.visibility = View.VISIBLE
            }
        }
    }

    override fun layoutId(position: Int): Int {
        return R.layout.layout_trends_item
    }
}