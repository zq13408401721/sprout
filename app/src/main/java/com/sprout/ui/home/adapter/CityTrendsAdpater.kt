package com.sprout.ui.home.adapter

import android.content.Context
import android.opengl.Visibility
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shop.base.BaseAdapter
import com.shop.base.IItemClick
import com.shop.ext.findView
import com.sprout.BR
import com.sprout.R
import com.sprout.app.Global
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
        var imgType = binding.root.findView<ImageView>(R.id.img_type).value
        //动态数据资源的图标
        when(data.type){
            Global.TYPE_IMG -> {
                Glide.with(context).load(data.url).into(imgCity)
                if(data.res.size > 1){
                    imgType.setImageResource(R.mipmap.ic_type_imgs)
                    imgType.visibility = View.VISIBLE
                }else{
                    imgType.visibility = View.GONE
                }
            }
            Global.TYPE_VIDEO -> {
                imgType.setImageResource(R.mipmap.ic_type_play)
                imgType.visibility = View.VISIBLE
                //视频取第一秒作为封面图
                Glide.with(context)
                        .setDefaultRequestOptions(
                                RequestOptions()
                                        .frame(1000000)
                                        .centerCrop()
                                        .error(R.mipmap.ic_video_error) //可以忽略
                        )
                        .load(data.url)
                        .into(imgCity)
            }
        }
    }

    override fun layoutId(position: Int): Int {
        return R.layout.layout_city_trends_item
    }
}