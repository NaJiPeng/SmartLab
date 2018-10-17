package com.njp.smartlab.utils.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.njp.smartlab.R

class LoadingCallback() : Callback() {

    override fun onCreateView() = R.layout.layout_loading

    override fun onReloadEvent(context: Context?, view: View?) = true

}