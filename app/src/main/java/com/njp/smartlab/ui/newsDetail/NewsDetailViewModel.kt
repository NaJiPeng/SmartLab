package com.njp.smartlab.ui.newsDetail

import android.arch.lifecycle.MutableLiveData
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class NewsDetailViewModel : BaseViewModel() {

    var type = ""
    var title = ""
    var url = ""

    val html = MutableLiveData<String>().apply { value = "" }

    fun getDetail() {
        Repository.getInstance().getDetail(type, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.success){
                                html.value = it.html
                                EventBus.getDefault().post(NewsDetailEvent(NewsDetailEvent.newsSuccess))
                            }else{
                                EventBus.getDefault().post(NewsDetailEvent(NewsDetailEvent.newsFail,it.msg))
                            }
                        },
                        {
                            EventBus.getDefault().post(NewsDetailEvent(NewsDetailEvent.newsFail,"网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }


}