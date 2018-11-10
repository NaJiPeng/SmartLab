package com.njp.smartlab.utils

import com.njp.smartlab.base.MyApplication

fun getVersionInfo(): Pair<Int, String> {
    MyApplication.instance.packageManager
            .getPackageInfo(MyApplication.instance.packageName, 0).let {
                return Pair(it.versionCode, it.versionName)
            }
}