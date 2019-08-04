package com.njp.smartlab.ui.update

import androidx.lifecycle.MutableLiveData
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import com.njp.smartlab.utils.UserInfoHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class UpdateViewModel : BaseViewModel() {

    val userId = MutableLiveData<String>()
    val imgUrl = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val major = MutableLiveData<String>()
    var password: String = ""

    init {
        UserInfoHolder.getInstance().getUser().value?.let {
            userId.value = it.userId
            imgUrl.value = it.avatarHash
            email.value = it.email
            name.value = it.name
            major.value = it.major
            password = it.pwdHash
        }
    }

    fun update() {
        Repository.getInstance().update(name.value!!, major.value ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                it.user.avatarHash = "https://www.gravatar.com/avatar/$it.user.avatarHash?d=retro"
                                it.user.pwdHash = password
                                UserInfoHolder.getInstance().saveUser(it.user)
                                EventBus.getDefault().post(UpdateEvent(UpdateEvent.updateSuccess))
                            } else {
                                EventBus.getDefault().post(UpdateEvent(UpdateEvent.updateFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(UpdateEvent(UpdateEvent.updateFail, "网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }

}