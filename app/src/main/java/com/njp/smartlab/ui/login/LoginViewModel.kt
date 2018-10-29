package com.njp.smartlab.ui.login

import android.arch.lifecycle.MutableLiveData
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import com.njp.smartlab.utils.UserInfoHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LoginViewModel : BaseViewModel() {

    val account = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun login() {
        Repository.getInstance().login(account.value!!, password.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                it.user.avatarHash = "https://www.gravatar.com/avatar/$it.user.avatarHash?d=retro"
                                UserInfoHolder.getInstance().saveUser(it.user)
                                EventBus.getDefault().post(LoginEvent(LoginEvent.loginSuccess))
                            } else {
                                EventBus.getDefault().post(LoginEvent(LoginEvent.loginFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(LoginEvent(LoginEvent.loginFail, "网络连接失败"))
                        }
                )?.let {
                    disposables.add(it)
                }
    }

    init {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: LoginEvent) {
        if (event.id == LoginEvent.fillOut) {
            val list = event.msg.split(",")
            account.value = list[0]
            password.value = list[1]
        }
    }


}