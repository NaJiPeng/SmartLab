package com.njp.smartlab.bean

data class Tool(
        val borrowed: Boolean,
        val boxId: Int,
        val createTime: Long,
        val isBorrowed: Int,
        val name: String,
        val updateTime: Long,
        val userId: String,
        val userName: String?
)