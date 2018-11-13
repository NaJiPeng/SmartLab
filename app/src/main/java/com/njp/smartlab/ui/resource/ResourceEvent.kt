package com.njp.smartlab.ui.resource

data class ResourceEvent(val id: Int, val msg: String = "") {
    companion object {
        const val resourceSuccess = 0
        const val resourceFail = 1
    }
}