package com.njp.smartlab.ui.home

import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.UserInfoHolder
import com.njp.smartlab.utils.getVersionInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class HomeViewModel : BaseViewModel() {

    val userInfo = UserInfoHolder.getInstance().getUser()
    private val versionInfo = getVersionInfo()

    fun checkUpdate() {
        Repository.getInstance().update()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                if (versionInfo.first < it.version_code) {
                                    EventBus.getDefault().post(HomeEvent(HomeEvent.updateSuccess))
                                    EventBus.getDefault().post(it)
                                } else {
                                    EventBus.getDefault().post(HomeEvent(HomeEvent.updateFail, "已经是最新版本"))
                                }
                            } else {
                                EventBus.getDefault().post(HomeEvent(HomeEvent.updateFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(HomeEvent(HomeEvent.updateFail, "网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }

}