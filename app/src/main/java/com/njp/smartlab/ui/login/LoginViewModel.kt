package com.njp.smartlab.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val account = MutableLiveData<String>()
    val password = MutableLiveData<String>()

}