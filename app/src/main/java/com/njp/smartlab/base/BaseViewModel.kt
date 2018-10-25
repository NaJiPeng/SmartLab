package com.njp.smartlab.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    protected val disposables = ArrayList<Disposable>()

    override fun onCleared() {
        super.onCleared()
        disposables.forEach { it.dispose() }
        disposables.clear()
    }
}