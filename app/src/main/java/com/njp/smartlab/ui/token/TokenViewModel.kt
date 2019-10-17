package com.njp.smartlab.ui.token

import androidx.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.util.Log
import com.njp.smartlab.base.BaseViewModel
import com.njp.smartlab.network.Repository
import com.uuzuche.lib_zxing.activity.CodeUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class TokenViewModel : BaseViewModel() {

    var type = ""
    var boxId = 0
    var isLoading = false
    val bitmap = MutableLiveData<Bitmap>()

    fun refresh() {
        Repository.getInstance().hardware(type, boxId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            isLoading = false
                            if (it.success) {
                                bitmap.value = CodeUtils.createImage(it.uuid, 400, 400, null)
                                EventBus.getDefault().post(TokenEvent(TokenEvent.tokenSuccess))
                            } else {
                                EventBus.getDefault().post(TokenEvent(TokenEvent.tokenFail, it.msg))
                            }
                        },
                        {
                            Log.e("mmmm", "error", it)
                            isLoading = false
                            EventBus.getDefault().post(TokenEvent(TokenEvent.tokenFail, "网络连接失败"))
                        }
                ).let { disposables.add(it) }
    }

}