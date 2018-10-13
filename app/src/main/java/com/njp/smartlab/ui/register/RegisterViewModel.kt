package com.njp.smartlab.ui.register

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class RegisterViewModel:ViewModel() {

    val accont = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val rePassword = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val validate = MutableLiveData<String>()

}