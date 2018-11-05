package com.njp.smartlab.network

import com.njp.smartlab.bean.TimeTableDisplayBean
import com.zhuangfei.timetable.model.Schedule


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
    fun openDoor() = service.openDoor()

    /**
     * 获取历史操作记录
     */
    fun getHistory() = service.getHistory()

    /**
     * 获取课程信息
     */
    fun getLessons() = service.getLessons()

    /**
     * 获取物品信息
     */
    fun getTools() = service.getTools()

    /**
     * 选课
     */
    fun choose(activityId: Int) = service.choose(activityId)

    /**
     * 我的课程
     */
    fun getMyLessons() = service.myLessons().map {
        val bean = TimeTableDisplayBean(isSuccess = it.success, msg = it.msg, curWeek = it.currWeek)
        if (it.success) {
            val list = ArrayList<Schedule>()
            it.lessions.forEach { lesson ->
                lesson.activityDetailResult.activityDetails.forEach { detail ->
                    list.add(Schedule().apply {
                        name = lesson.activityDetailResult.name
                        room = detail.location
                        teacher = lesson.activityDetailResult.teacher
                        weekList = detail.week.split(",").map { week ->
                            week.toInt()
                        }
                        start = detail.activityOrder.split(",")[0].toInt()
                        step = detail.activityOrder.split(",").size
                        day = detail.day.toInt()
                        colorRandom = lesson.activityDetailResult.type
                    })
                }
            }
            bean.data = list
        }
        return@map bean
    }

}