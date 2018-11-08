package com.njp.smartlab.ui.news

class NewsEvent(val id: Int, val msg: String = "") {
    companion object {
        const val bannerSuccess = 0
        const val bannerFail = 1
        const val newsSuccess = 2
        const val newsFail = 3
    }
}