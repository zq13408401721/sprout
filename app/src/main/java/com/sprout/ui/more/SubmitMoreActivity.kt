package com.sprout.ui.more

import android.content.Intent
import android.util.Log
import android.util.SparseArray
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.shop.base.BaseActivity
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.app.GlideEngine
import com.sprout.databinding.ActivitySubmitMoreBinding
import com.sprout.model.ImgData
import com.sprout.ui.more.adapter.SubmitImgAdapter
import com.sprout.vm.more.SubmitViewModel
import kotlinx.android.synthetic.main.activity_more_editor.*
import kotlinx.android.synthetic.main.activity_submit_more.*
import kotlinx.android.synthetic.main.activity_tags.*
import kotlinx.android.synthetic.main.layout_submit_imgitem.*
import kotlinx.coroutines.selects.select
import org.json.JSONArray

/**
 * 动态数据的提交
 */
class SubmitMoreActivity:BaseActivity<SubmitViewModel,ActivitySubmitMoreBinding>(R.layout.activity_submit_more,SubmitViewModel::class.java) {

    var imgs:MutableList<ImgData> = mutableListOf()
    var max_img = 9

    lateinit var imgAdapter:SubmitImgAdapter

    override fun initData() {
        var json = intent.getStringExtra("data")
        if(json!!.isNotEmpty()){
            var jsonArr = JSONArray(json)
            for(i in 0 until jsonArr.length()){
                var imgData = Gson().fromJson<ImgData>(jsonArr.getString(i),ImgData::class.java)
                imgs.add(imgData)
            }
            //处理加号
            if(imgs.size < max_img){
                var imgData = ImgData(null, mutableListOf())
                imgs.add(imgData)
            }
        }
    }

    override fun initVM() {

    }

    override fun initVariable() {

    }

    override fun initView() {
        var layouts = SparseArray<Int>()
        layouts.put(R.layout.layout_submit_imgitem,BR.submitData)
        imgAdapter = SubmitImgAdapter(mContext,imgs,layouts,ItemClick())
        recyImgs.layoutManager = GridLayoutManager(mContext,3)
        recyImgs.adapter = imgAdapter
        imgAdapter.clickEvt = SubmitClickEvt()
    }

    private fun openPhoto(){
        //当前还能插入的图片数量
        var num = max_img-imgs.size+1
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                .maxSelectNum(num)
                .imageSpanCount(3)
                .selectionMode(PictureConfig.MULTIPLE)
                .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    /**
     * 打开activity后回传
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PictureConfig.CHOOSE_REQUEST -> {
                // onResult Callback
                val selectList = PictureSelector.obtainMultipleResult(data)
                if (selectList.size == 0) return
                //获取本地图片的选择地址，上传到服务器
                //头像的压缩和二次采样
                //把选中的图片插入到列表
                for(i in 0 until selectList.size){
                    var imgData = ImgData(selectList.get(i).path, mutableListOf())
                    var index = imgs.size-1
                    imgs.add(index,imgData)
                }
                if(imgs.size > max_img){
                    imgs.removeLast()
                }
                imgAdapter.notifyDataSetChanged()

            }

            else -> {
            }
        }
    }

    /**
     * item条目的点击
     */
    inner class ItemClick:IItemClick<ImgData>{
        override fun itemClick(data: ImgData) {
            //当前点击的是加号
            if(data.path.isNullOrEmpty()){
                openPhoto()
            }
        }
    }

    inner class SubmitClickEvt{
        fun clickDelete(data:ImgData){
            imgs.remove(data)
            imgAdapter.notifyDataSetChanged()
        }
    }
}