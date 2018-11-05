package com.njp.smartlab.ui.register

import android.arch.lifecycle.MutableLiveData
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class RegisterViewModel : BaseViewModel() {

    val account = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val rePassword = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val validate = MutableLiveData<String>()

    fun verifyEmail() {
        Repository.getInstance().verifyEmail(email.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                EventBus.getDefault().post(RegisterEvent(RegisterEvent.emailSuccess))
                            } else {
                                EventBus.getDefault().post(RegisterEvent(RegisterEvent.emailFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(RegisterEvent(RegisterEvent.emailFail, "网络连接失败"))
                        }
                )?.let { disposables.add(it) }
    }

    fun register() {
        Repository.getInstance().register(
                account.value!!, password.value!!, email.value!!, name.value!!, validate.value!!
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                EventBus.getDefault().post(RegisterEvent(RegisterEvent.registerSuccess))
                            } else {
                                EventBus.getDefault().post(RegisterEvent(RegisterEvent.registerFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(RegisterEvent(RegisterEvent.registerFail, "网络连接失败"))
                        }
                )?.let { disposables.add(it) }
    }

}