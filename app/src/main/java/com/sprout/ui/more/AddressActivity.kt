package com.sprout.ui.more

import android.content.Intent
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.MyLocationData
import com.shop.base.BaseActivity
import com.sprout.R
import com.sprout.databinding.ActivityAddressBinding
import com.sprout.vm.more.AdressViewModel

class AddressActivity:BaseActivity<AdressViewModel, ActivityAddressBinding>(R.layout.activity_address, AdressViewModel::class.java) {

    lateinit var mIntent:Intent
    lateinit var locationClient:LocationClient

    override fun initData() {
        mIntent = getIntent()
    }

    override fun initVM() {
    }

    override fun initVariable() {
    }

    override fun initView() {
        //定位初始化
        locationClient = LocationClient(this)

        //通过LocationClientOption设置LocationClient相关参数
        val option = LocationClientOption()
        option.isOpenGps = true // 打开gps
        option.setCoorType("bd09ll") // 设置坐标类型
        option.setScanSpan(1000)

        //设置locationClientOption
        locationClient.setLocOption(option)

        //注册LocationListener监听器
        val myLocationListener = MyLocationListener()
        locationClient.registerLocationListener(myLocationListener)
        //开启地图定位图层
        locationClient.start()
    }


    inner class MyLocationListener:BDAbstractLocationListener(){
        override fun onReceiveLocation(bdLocation: BDLocation?) {
            if(bdLocation == null) return

            var locData = MyLocationData.Builder()
                    .accuracy(bdLocation.radius)
                    .direction(bdLocation.direction).latitude(bdLocation.latitude)
                    .longitude(bdLocation.longitude).build()

        }

    }

    inner class ClickEvt{
        /**
         * 不显示地址
         */
        fun clickNoAddress(){
            if(mIntent != null){
                setResult(100)
                finish()
            }
        }
    }
}