package com.sprout.ui.more

import android.content.Intent
import android.graphics.Bitmap
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.shop.base.BaseActivity
import com.sprout.R
import com.sprout.app.GlideEngine
import com.sprout.app.Global
import com.sprout.databinding.ActivityMoreEditorBinding
import com.sprout.utils.BitmapUtils
import com.sprout.vm.more.MoreViewModel
import kotlinx.android.synthetic.main.activity_more_editor.*
import java.io.IOException

/**
 * 动态页面图片的编辑
 */
class MoreEditorActivity:BaseActivity<MoreViewModel, ActivityMoreEditorBinding>(R.layout.activity_more_editor, MoreViewModel::class.java) {
    val CODE_TAG = 99

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
        //打开相册选取图片
        openPhoto()
        txt_tag.setOnClickListener {
            var intent = Intent(this,TagsActivity::class.java)
            startActivityForResult(intent,CODE_TAG)
        }
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
                try {
                    val scaleBitmp: Bitmap = BitmapUtils.getScaleBitmap(selectList[0].path, Global.IMG_WIDTH, Global.IMG_HEIGHT)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //处理TAG设置返回
            CODE_TAG -> {
                if(resultCode == 1){
                    var id = data!!.getIntExtra("id",0)
                    var name = data!!.getStringExtra("name")
                }else if(resultCode == 2){
                    var id = data!!.getIntExtra("id",0)
                    var name = data!!.getStringExtra("name")
                }
            }
            else -> {
            }
        }
    }
}