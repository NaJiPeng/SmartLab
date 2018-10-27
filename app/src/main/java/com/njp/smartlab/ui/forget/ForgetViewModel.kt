package com.njp.smartlab.ui.forget

import android.arch.lifecycle.MutableLiveData
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class ForgetViewModel : BaseViewModel() {

    val account = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val validate = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val rePassword = MutableLiveData<String>()

    /**
     * 发送验证码
     */
    fun changePwdVerify() {
        Repository.getInstance().changePwdVerify(email.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                EventBus.getDefault().post(ForgetEvent(ForgetEvent.emailSuccess))
                            } else {
                                EventBus.getDefault().post(ForgetEvent(ForgetEvent.emailFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(ForgetEvent(ForgetEvent.emailFail, "网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }

    /**
     * 修改密码
     */
    fun updatePwd() {
        Repository.getInstance().updatePwd(validate.value!!, password.value!!, email.value!!, account.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                EventBus.getDefault().post(ForgetEvent(ForgetEvent.forgetSuccess))
                            } else {
                                EventBus.getDefault().post(ForgetEvent(ForgetEvent.forgetFail,it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(ForgetEvent(ForgetEvent.forgetFail,"网络连接失败"))
                        }
                ).let { disposables.add(it) }

    }

}