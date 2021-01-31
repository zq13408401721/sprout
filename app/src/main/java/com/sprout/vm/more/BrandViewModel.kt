package com.sprout.vm.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.base.BaseViewModel
import com.sprout.model.BrandData
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import kotlinx.coroutines.launch

class BrandViewModel:BaseViewModel<SproutRepository>(InJection.repository) {

    var bList: MutableLiveData<BrandData> = MutableLiveData()
    var bpage = 0
    var gpage = 0
    var size = 10

    fun getBrand(){
        viewModelScope.launch {
            var result = repository.getBrand(bpage,size)
            if(result.errno == 0){
                bList.postValue(result.data)
            }
        }
    }

}