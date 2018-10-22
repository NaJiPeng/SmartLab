package com.njp.smartlab.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.njp.smartlab.network.Repository
import com.njp.smartlab.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel : ViewModel() {

    val account = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun login() {
        Logger.getInstance().log("LoginViewModel:login")
        Repository.getInstance().login(account.value!!, password.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Logger.getInstance().log(it.user.email)
                        },
                        {
                            Logger.getInstance().log(it.message)
                        }
                )
    }

}