package com.njp.smartlab.ui.resource

import com.njp.smartlab.adapter.FilesAdapter
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import org.greenrobot.eventbus.EventBus

class ResourceViewModel : BaseViewModel() {

    val dataAdapter = FilesAdapter()
    val adapter = SlideInBottomAnimationAdapter(dataAdapter)

    fun getResource() {
        Repository.getInstance().getResource()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success) {
                                dataAdapter.setData(it.file_list)
                                EventBus.getDefault().post(ResourceEvent(ResourceEvent.resourceSuccess))
                            } else {
                                EventBus.getDefault().post(ResourceEvent(ResourceEvent.resourceFail, it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(ResourceEvent(ResourceEvent.resourceFail, "网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }

}