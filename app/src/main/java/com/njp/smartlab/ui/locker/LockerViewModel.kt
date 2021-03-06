package com.njp.smartlab.ui.locker

import android.util.Log
import com.njp.smartlab.adapter.LockerAdapter
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import org.greenrobot.eventbus.EventBus

class LockerViewModel : BaseViewModel() {

    var isFirstLoad = true

    val dataAdapter = LockerAdapter()

    val adapter = SlideInBottomAnimationAdapter(dataAdapter)

    fun getTools() {
        Repository.getInstance().getTools()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                dataAdapter.setData(it.tools)
                                EventBus.getDefault().post(LockerEvent(LockerEvent.lockerSuccess))
                            } else {
                                EventBus.getDefault().post(LockerEvent(LockerEvent.lockerFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(LockerEvent(LockerEvent.lockerFail, "网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }

}