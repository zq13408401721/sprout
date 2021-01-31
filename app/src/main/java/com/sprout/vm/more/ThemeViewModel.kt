package com.sprout.vm.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.base.BaseViewModel
import com.sprout.model.ThemeData
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import kotlinx.coroutines.launch

class ThemeViewModel:BaseViewModel<SproutRepository>(InJection.repository) {

    var list:MutableLiveData<List<ThemeData>> = MutableLiveData()

    fun getTheme(){
        viewModelScope.launch {
            list.postValue(repository.getTheme().data)
        }
    }

}