package com.sprout.vm.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.base.BaseViewModel
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

class SubmitViewModel:BaseViewModel<SproutRepository>(InJection.repository) {


    var state:MutableLiveData<Int> = MutableLiveData()

    /**
     * 提交动态数据
     */
    fun submitTrends(json:String){
        var body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json)
        viewModelScope.launch {
            var result = repository.submitTrends(body)
            /*if(result.errno == 0){
                state.postValue(200)
            }else{
                state.postValue(-1)
            }*/
        }
    }

}