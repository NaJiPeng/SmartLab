package com.njp.smartlab.utils.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.njp.smartlab.R

class ScanningCallback : Callback() {

    override fun onCreateView() = R.layout.layout_scanning

    override fun onReloadEvent(context: Context?, view: View?) = true

}