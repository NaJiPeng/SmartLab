package com.njp.smartlab.network


/**
 * 应用网络请求唯一入口
 */
class Repository private constructor() {

    companion object {
        private var instance: Repository? = null

        fun getInstance() = instance ?: Repository().apply { instance = this }
    }

    private val service = NetworkConfig.getInstance().retrofit.create(NetworkService::class.java)

    /**
     * 登录
     */
    fun login(id: String, pwdHash: String) = service.login(id, pwdHash)

    /**
     * 注册验证码
     */
    fun verifyEmail(email: String) = service.verifyEmail(email)

    /**
     * 注册
     */
    fun register(
            userId: String, pwdHash: String, email: String, name: String, captcha: String
    ) = service.register(
            userId, pwdHash, email, name, captcha
    )

    /**
     * 修改密码验证
     */
    fun changePwdVerify(email: String) = service.changePwdVerify(email)

    /**
     * 修改密码
     */
    fun updatePwd(
            captcha: String, newPwd: String, email: String, id: String
    ) = service.updatePwd(
            captcha, newPwd, email, id
    )

    /**
     * 修改资料
     */
    fun update(name: String, major: String) = service.update(name, major)

    /**
     * 开门请求
     */
    fun opendoor() = service.opendoor()

}