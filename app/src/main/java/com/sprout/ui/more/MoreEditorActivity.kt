package com.sprout.ui.more

import android.content.Intent
import android.graphics.Bitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.shop.base.BaseActivity
import com.sprout.R
import com.sprout.app.GlideEngine
import com.sprout.app.Global
import com.sprout.databinding.ActivityMoreEditorBinding
import com.sprout.model.ImgData
import com.sprout.utils.BitmapUtils
import com.sprout.vm.more.MoreViewModel
import kotlinx.android.synthetic.main.activity_more_editor.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

/**
 * 动态页面图片的编辑
 */
class MoreEditorActivity:BaseActivity<MoreViewModel, ActivityMoreEditorBinding>(R.layout.activity_more_editor, MoreViewModel::class.java) {
    val CODE_TAG = 99
    var imgList:MutableList<String> = mutableListOf()
    var fragments:MutableList<ImageFragment> = mutableListOf()
    lateinit var fAdapter:FAdapter

    //当前界面tag相关数据
    var imgArray:MutableList<ImgData> = mutableListOf()

    override fun initData() {

    }

    override fun initVM() {

    }

    override fun initVariable() {

    }

    /**
     * 初始化界面
     */
    override fun initView() {
        imgList = mutableListOf()
        txt_tag.setOnClickListener {
            var intent = Intent(this,TagsActivity::class.java)
            startActivityForResult(intent,CODE_TAG)
        }
        fAdapter = FAdapter(supportFragmentManager)
        viewPager.adapter = fAdapter

        txt_next.setOnClickListener {
            var intent = Intent(this,SubmitMoreActivity::class.java)
            intent.putExtra("data",decodeImgs())
            startActivity(intent)
        }

        //打开相册选取图片
        openPhoto()
    }

    private fun openPhoto(){
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                .maxSelectNum(9)
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
                    imgList.add(selectList.get(i).path) //保留图片的绝对路径
                    //图片数据的初始化
                    var imgData = ImgData(selectList.get(i).path, mutableListOf())
                    imgArray.add(imgData)
                    var fragment = ImageFragment.instance(i,selectList.get(i).path,imgData.tags)
                    fragments.add(fragment)

                }
                fAdapter.notifyDataSetChanged()
            }
            //处理TAG设置返回
            CODE_TAG -> {
                if(resultCode == 1){
                    var id = data!!.getIntExtra("id",0)
                    var name = data!!.getStringExtra("name")
                    var pos = viewPager.currentItem
                    if(fragments.size <= pos) return
                    fragments.get(pos).addTagsToView(1,id,name!!)
                }else if(resultCode == 2){
                    var id = data!!.getIntExtra("id",0)
                    var name = data!!.getStringExtra("name")
                    if(fragments.size <= viewPager.currentItem) return
                    fragments.get(viewPager.currentItem).addTagsToView(2,id,name!!)
                }
            }
            else -> {
            }
        }
    }

    /**
     * json结构原生的封装
     */
    private fun decodeImgs():String{
        var imgs = JSONArray()
        for(i in 0 until imgArray.size){
            var item = imgArray[i]
            var imgJson = JSONObject()  //图片的json结构
            imgJson.put("path",item.path)
            var tags = JSONArray()
            for(j in 0 until item.tags.size){
                var tag = item.tags[j]
                var tagJson = JSONObject()
                tagJson.put("x",tag.x)
                tagJson.put("y",tag.y)
                tagJson.put("type",tag.type)
                tagJson.put("name",tag.name)
                tagJson.put("lng",tag.lng)
                tagJson.put("lat",tag.lat)
                tags.put(tagJson)
            }
            imgJson.put("tags",tags)
            imgs.put(imgJson)
        }
        return imgs.toString()

    }

    inner class FAdapter(
        fm:FragmentManager
    ): FragmentPagerAdapter(fm){
        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments.get(position)
        }

    }

}