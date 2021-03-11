package com.sprout.ui.home.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.shop.base.BaseAdapter
import com.shop.base.IItemClick
import com.shop.ext.findView
import com.sprout.BR
import com.sprout.R
import com.sprout.app.Global
import com.sprout.model.TrendsData
import com.sprout.utils.ScreenUtils


class TrendsAdapter(
        context: Context,
        list: List<TrendsData>,
        layouts: SparseArray<Int>,
        click: IItemClick<TrendsData>
):BaseAdapter<TrendsData>(context, list, layouts, click) {
    override fun bindData(binding: ViewDataBinding, data: TrendsData, layId: Int) {
        binding.setVariable(BR.trendsClick, itemClick)
        binding.setVariable(BR.trendsData,data)
        var img = binding.root.findView<ImageView>(R.id.img_trends).value
        var imgType = binding.root.findView<ImageView>(R.id.img_type).value
        imgType.visibility = View.GONE

        val layoutParams: ViewGroup.LayoutParams = img.getLayoutParams()
        val itemWidth: Float = (ScreenUtils.getSreenWidth(context).toFloat() - 16 * 3) / 2
        layoutParams.width = itemWidth.toInt()
        /*val scale: Float = (itemWidth + 0f) /
        layoutParams.height = (android.R.at
        0tr.data.getHeight() * scale)*/


        when(data.type){
            Global.TYPE_IMG -> {
                Glide.with(context).load(data.url)
                        .override(itemWidth.toInt(),Target.SIZE_ORIGINAL)
                        .fitCenter()
                        .into(img)  //封面
                if (data.res.size > 1) {
                    imgType.setImageResource(R.mipmap.icon_photos)
                    imgType.visibility = View.VISIBLE
                } else {
                    imgType.visibility = View.GONE
                }
            }
            Global.TYPE_VIDEO -> {
                imgType.setImageResource(R.mipmap.icon_video)
                imgType.visibility = View.VISIBLE
                //数据列表条目为视频的时候，封面图的显示
                Glide.with(context)
                        .setDefaultRequestOptions(
                                RequestOptions()
                                        .frame(1000000)
                                        .centerCrop()
                                        .error(R.mipmap.ic_video_error) //可以忽略
                        )
                        .load(data.url)
                        .override(itemWidth.toInt(),Target.SIZE_ORIGINAL)
                        .fitCenter()
                        .into(img)
            }
        }
    }

    override fun layoutId(position: Int): Int {
        return R.layout.layout_trends_item
    }
}

