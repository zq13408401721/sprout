package com.sprout.ui.more

import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import org.json.JSONObject

/**
 * 动态数据的提交
 */
class SubmitMoreActivity:BaseActivity<SubmitViewModel,ActivitySubmitMoreBinding>(R.layout.activity_submit_more,SubmitViewModel::class.java) {

    final var CODE_CHANNEL = 101  //选择频道
    final var CODE_THEME = 102 //选择主题
    final var CODE_ADDRESS = 103 //选择地址


    var imgs:MutableList<ImgData> = mutableListOf()
    var max_img = 9

    lateinit var imgAdapter:SubmitImgAdapter

    var channelId:Int = 0 //频道id
    lateinit var channelName:String
    var themeId:Int = 0 //主题id
    lateinit var themeName:String
    lateinit var address:String  //地址
    var lat:Double = 0.0  //经度
    var lng:Double = 0.0  //纬度
    var type:Int = 1
    override fun initData() {
        var from = intent.getIntExtra("from",0)
        //默认的提交流程
        if(from == 0){
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
        }else{ //草稿再次编辑
            //去本地数据库取值

        }

    }

    override fun initVM() {
        mViewModel.state.observe(this, Observer {
            when(it){
                200 -> {
                    finish()
                }
                -1 -> {

                }
            }
        })

    }

    override fun initVariable() {
        mDataBinding.submitClick = SubmitClickEvt()
    }

    override fun initView() {
        var layouts = SparseArray<Int>()
        layouts.put(R.layout.layout_submit_imgitem,BR.submitData)
        imgAdapter = SubmitImgAdapter(mContext,imgs,layouts,ItemClick())
        recyImgs.layoutManager = GridLayoutManager(mContext,3)
        recyImgs.addItemDecoration(ImgItemDecoration())
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
            CODE_CHANNEL -> {
                if(data != null){
                    channelId = data!!.getIntExtra("channelId",0)
                    channelName = data!!.getStringExtra("channelName")!!
                    txt_channel.setText(channelName)
                }
            }
            CODE_THEME -> {
                if(data != null){
                    themeId = data!!.getIntExtra("themeId",0)
                    themeName = data!!.getStringExtra("themeName")!!
                    txt_theme.setText(themeName)
                }
            }
            CODE_ADDRESS -> {
                if(data != null){
                    address = data!!.getStringExtra("address")!!
                    lat = data!!.getDoubleExtra("lat",0.0)
                    lng = data!!.getDoubleExtra("lng",0.0)
                    txt_address.setText(address)
                }
            }
            else -> {

            }
        }
    }

    /**
     * 添加地址
     */
    fun addAddress(){

    }

    /**
     * 组装提交的内容
     */
    fun getSubmitJson():String{
        var title = edit_title.toString()
        var mood =edit_mood.toString()
        var json:JSONObject = JSONObject()
        json.put("type",type)
        json.put("title",title)
        json.put("mood",mood)
        json.put("address",address)
        json.put("themeid",themeId)
        json.put("channelid",channelId)
        json.put("lng",lng)
        json.put("lat",lat)
        var res = JSONArray()
        when(type){
            1 -> { //图片
                for(i in 0 until imgs.size){
                    var img = JSONObject()
                    img.put("url",imgs[i].path)
                    var tags = JSONArray()
                    img.put("tags",tags)
                    for(j in 0 until imgs[i].tags.size){
                        var tagItem = imgs[i].tags[j]
                        var tag = JSONObject()
                        tag.put("type",tagItem.type)
                        tag.put("id",tagItem.id)
                        tag.put("name",tagItem.name)
                        tag.put("x",tagItem.x)
                        tag.put("y",tagItem.y)
                        tag.put("lng",tagItem.lng)
                        tag.put("lat",tagItem.lat)
                        tags.put(tag)
                    }
                    json.put("res",res)
                }
            }
            2 -> {

            }
        }

        return json.toString()
    }

    /**
     * 分割线
     */
    inner class ImgItemDecoration:RecyclerView.ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            if (parent.getChildAdapterPosition(view).toInt()%3 == 0){
                outRect.set(10,10,0,10)
            }else if(parent.getChildAdapterPosition(view).toInt()%3 == 2){
                outRect.set(0,10,10,10)
            }else{
                outRect.set(10,10,10,10)
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

        /**
         * 删除图片
         */
        fun clickDelete(data:ImgData){
            imgs.remove(data)
            imgAdapter.notifyDataSetChanged()
        }

        /**
         * 频道选择
         */
        fun clickChannel(){
            var intent = Intent(mContext,ChannelActivity::class.java)
            startActivityForResult(intent,CODE_CHANNEL)
        }

        /**
         * 主题选择
         */
        fun clickTheme(){
            var intent = Intent(mContext,ThemeActivity::class.java)
            startActivityForResult(intent,CODE_THEME)
        }

        /**
         * 地址选择
         */
        fun clickAddress(){
            var intent = Intent(mContext,AddressActivity::class.java)
            startActivityForResult(intent,CODE_ADDRESS)
        }

        /**
         * 提交发布数据
         */
        fun submit(){
            var content = getSubmitJson()
            mViewModel.submitTrends(content)
        }

    }
}