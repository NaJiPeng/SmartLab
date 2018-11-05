package com.njp.smartlab.adapter

import android.databinding.BindingAdapter
import android.graphics.Color
import android.support.v7.widget.CardView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.njp.smartlab.R
import com.njp.smartlab.bean.ActivityDetail
import com.njp.smartlab.bean.Manipulation
import com.zhuangfei.timetable.TimetableView
import com.zhuangfei.timetable.model.Schedule
import com.zhuangfei.timetable.model.ScheduleEnable
import com.zhuangfei.timetable.view.WeekView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

@BindingAdapter("image")
fun ImageView.setImage(image: Any?) {
    Glide.with(this)
            .load(image)
            .into(this)
}

@BindingAdapter("head")
fun ImageView.setUrl(url: Any?) {
    Glide.with(this)
            .load(url)
            .apply(RequestOptions().error(R.drawable.ic_account))
            .into(this)
}

@BindingAdapter("account")
fun TextView.setAccount(account: String?) {
    text = account ?: "未登录"
}

@BindingAdapter("status")
fun TextView.setStatus(status: Int?) {
    text = if (status != null) "状态：${if (status == 1) "通过" else "未通过"}" else ""
}

@BindingAdapter("coin")
fun TextView.setCoin(coin: Int?) {
    text = if (coin != null) "金币：$coin" else ""
}

@BindingAdapter("time")
fun TextView.setTime(time: Long) {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
    text = sdf.format(Date(time))
}

@BindingAdapter("manipulation")
fun TextView.setManipulation(manipulation: Manipulation) {
    text = when (manipulation.functionType) {
        1 -> "进入实验室"
        2 -> "借用${manipulation.boxId}号储物柜物品"
        3 -> "归还${manipulation.boxId}号储物柜物品"
        else -> ""
    }
}

@BindingAdapter("restNum", "maxNum", requireAll = true)
fun TextView.setLessonNum(restNum: Int, maxNum: Int) {
    text = "课余量：$restNum/$maxNum"
}

@BindingAdapter("lessonType")
fun TextView.setLessonType(type: Int) {
    text = when (type) {
        1 -> "课程安排："
        2 -> "讲座安排："
        3 -> "交流会安排："
        else -> ""
    }
}

@BindingAdapter("lessonDetail")
fun TextView.setLessonDetail(detail: List<ActivityDetail>) {
    text = StringBuilder().apply {
        detail.forEach {
            append("周数：${it.week}")
            append("\n")
            append("时间：星期${it.day}    第${it.activityOrder}节课")
            append("\n")
            append("地点：${it.location}")
            append("\n")
        }
        if (length > 0) {
            deleteCharAt(length - 1)
        }
    }.toString()
}

@BindingAdapter("boxId")
fun TextView.setBoxId(id: Int) {
    text = "No.$id"
}

@BindingAdapter("borrow")
fun TextView.setBorrowInfo(name: String?) {
    text = if (name == null) "可以借用" else "已被${name}借用"
}

@BindingAdapter("canBorrow")
fun CardView.setCanBorrow(can: Boolean) {
    setCardBackgroundColor(
            if (can) Color.parseColor("#3c8ce7") else Color.parseColor("#8a8a8a")
    )
}

@BindingAdapter("data", "curWeek")
fun TimetableView.setData(data: List<Schedule>, curWeek: Int) {
    data(data)
    curWeek(curWeek)
    showView()
}

@BindingAdapter("data", "curWeek")
fun WeekView.setData(source: List<Schedule>, curWeek: Int) {
    data(source)
    curWeek(curWeek)
    showView()
}



