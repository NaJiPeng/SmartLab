package com.njp.smartlab.ui.main

import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import com.njp.smartlab.utils.UserInfoHolder
import com.njp.smartlab.utils.getVersionInfo
import com.tencent.mmkv.MMKV
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel : BaseViewModel() {

    fun login() {
        val user = UserInfoHolder.getInstance().getUser().value
        user?.let { userInfo ->
            Repository.getInstance().login(userInfo.userId, userInfo.pwdHash)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                if (it.success) {
                                    it.user.avatarHash = "https://www.gravatar.com/avatar/$it.user.avatarHash?d=retro"
                                    it.user.pwdHash = userInfo.pwdHash
                                    UserInfoHolder.getInstance().saveUser(it.user)
                                }
                            },
                            {
                                //Do nothing
                            }
                    ).let { disposables.add(it) }
        }
    }

    fun update() {
        val versionInfo = getVersionInfo()
        Repository.getInstance().update()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                if (it.version_code > versionInfo.first
                                        && MMKV.defaultMMKV().decodeInt("ignore") != it.version_code) {
                                    EventBus.getDefault().post(it)
                                }
                            }
                        },
                        {
                            //Do nothing
                        }
                ).let { disposables.add(it) }
    }

}