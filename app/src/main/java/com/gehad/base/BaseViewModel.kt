package com.gehad.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<N>:ViewModel(){

    var navigator:N?=null
    val showLoading=MutableLiveData<Boolean>()
    val showMessage=MutableLiveData<String>()

}