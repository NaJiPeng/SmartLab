package com.njp.smartlab.ui.forget

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class ForgetViewModel : ViewModel() {

    val account = MutableLiveData<String>()
    val validate = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val rePassword = MutableLiveData<String>()

}