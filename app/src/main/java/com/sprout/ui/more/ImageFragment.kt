package com.sprout.ui.more

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.sprout.R
import com.sprout.model.BrandData
import com.sprout.model.GoodData
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment:Fragment() {

    val instance:ImageFragment by lazy {
        ImageFragment()
    }

    var index:Int = 0
    lateinit var path:String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_image,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(path.isNotEmpty()){
            image.setImageURI(Uri.parse(path))
        }

    }

    /**
     * 添加标签到界面
     */
    fun addTagsToView(type:Int,id:Int,name:String){
        var view = layoutInflater.inflate(R.layout.layout_tag_item,null)
        var imgTag = view.findViewById<ImageView>(R.id.img_tag)
        when(type){
            1-> imgTag.setImageResource(R.mipmap.tag_icon_w_brand)
            2-> imgTag.setImageResource(R.mipmap.tag_icon_w_commodity)
        }
        var param = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        param.setMargins(100,100,0,0) //控制组件的坐标位置
        layout_tags.addView(view,param)
    }

}