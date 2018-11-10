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

    private val service1 = NetworkConfig.getInstance().retrofit1.create(NetworkService1::class.java)

    private val service2 = NetworkConfig.getInstance().retrofit2.create(NetworkService2::class.java)

    /**
     * 登录
     */
    fun login(id: String, pwdHash: String) = service1.login(id, pwdHash)

    /**
     * 注册验证码
     */
    fun verifyEmail(email: String) = service1.verifyEmail(email)

    /**
     * 注册
     */
    fun register(
            userId: String, pwdHash: String, email: String, name: String, captcha: String
    ) = service1.register(
            userId, pwdHash, email, name, captcha
    )

    /**
     * 修改密码验证
     */
    fun changePwdVerify(email: String) = service1.changePwdVerify(email)

    /**
     * 修改密码
     */
    fun updatePwd(
            captcha: String, newPwd: String, email: String, id: String
    ) = service1.updatePwd(
            captcha, newPwd, email, id
    )

    /**
     * 修改资料
     */
    fun update(name: String, major: String) = service1.update(name, major)

    /**
     * 硬件操作相关
     */
    fun hardware(type: String, boxId: Int) = service1.hardware(type, boxId)

    /**
     * 获取历史操作记录
     */
    fun getHistory() = service1.getHistory()

    /**
     * 获取课程信息
     */
    fun getLessons() = service1.getLessons()

    /**
     * 获取物品信息
     */
    fun getTools() = service1.getTools()

    /**
     * 选课
     */
    fun choose(activityId: Int) = service1.choose(activityId)

    /**
     * 我的课程
     */
    fun getMyLessons() = service1.myLessons().map {
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

    /**
     * 获取头条新闻
     */
    fun getBanners() = service2.getBannerNews()

    /**
     * 获取新闻列表
     */
    fun getNews(page: Int) = service2.getNews(page)

    /**
     * 获取新闻详情
     */
    fun getDetail(type: String, url: String) = service2.getDetail(type, url)

    /**
     * 检查更新
     */
    fun update() = service2.update()

}