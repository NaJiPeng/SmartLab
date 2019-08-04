package com.njp.smartlab.ui.history

import androidx.recyclerview.widget.DiffUtil
import com.njp.smartlab.adapter.HistoryAdapter
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.bean.Manipulation
import com.njp.smartlab.network.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class HistoryViewModel : BaseViewModel() {

    val adapter = HistoryAdapter()

    fun refresh() {
        Repository.getInstance().getHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success){
                                if (it.manipulations.isEmpty()){
                                    EventBus.getDefault().post(HistoryEvent(HistoryEvent.refreshFail,"没有任何记录"))
                                }else{
                                    adapter.setData(it.manipulations)
                                    EventBus.getDefault().post(HistoryEvent(HistoryEvent.refreshSuccess))
                                }
                            }else{
                                EventBus.getDefault().post(HistoryEvent(HistoryEvent.refreshFail,it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(HistoryEvent(HistoryEvent.refreshFail,"网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }

}