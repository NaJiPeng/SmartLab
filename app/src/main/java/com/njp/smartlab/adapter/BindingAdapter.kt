package com.njp.smartlab.adapter

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.njp.smartlab.R
import com.njp.smartlab.bean.Manipulation
import java.text.SimpleDateFormat
import java.util.*

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

