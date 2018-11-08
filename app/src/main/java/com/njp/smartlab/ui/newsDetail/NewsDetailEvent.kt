package com.njp.smartlab.ui.newsDetail

class NewsDetailEvent(val id: Int, val msg: String = "") {
    companion object {
        const val newsSuccess = 0
        const val newsFail = 1
    }
}