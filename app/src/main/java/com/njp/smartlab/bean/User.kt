package com.njp.smartlab.bean

data class User(
        val userId: String,
        val name: String,
        val pwdHash: String,
        val avatarHash: String,
        val major: Any,
        val email: String,
        val isAllowed: Int,
        val role: Int,
        val coin: Int,
        val createTime: Long,
        val updateTime: Long
)