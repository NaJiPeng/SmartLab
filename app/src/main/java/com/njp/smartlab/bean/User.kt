package com.njp.smartlab.bean

data class User(
        var userId: String,
        var name: String,
        var pwdHash: String,
        var avatarHash: String,
        var major: String?,
        var email: String,
        var isAllowed: Int,
        var role: Int,
        var coin: Int,
        var createTime: Long,
        var updateTime: Long
)