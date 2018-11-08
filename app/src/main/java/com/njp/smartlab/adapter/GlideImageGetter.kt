package com.njp.smartlab.adapter

import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.text.Html
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.njp.smartlab.R

class GlideImageGetter(private val textView: TextView) : Html.ImageGetter {

    override fun getDrawable(p0: String?): Drawable {
        val drawable = LevelListDrawable()
        drawable.addLevel(0, 0, textView.resources.getDrawable(R.drawable.ic_img_error))
        drawable.setBounds(0, 0, 100, 100)
        Glide.with(textView)
                .load(p0)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        drawable.addLevel(1, 1, resource)
                        val rate = textView.width / resource.intrinsicWidth.toFloat()
                        drawable.setBounds(0, 0, textView.width, (rate * resource.intrinsicHeight).toInt())
                        drawable.level = 1
                        textView.text = textView.text
                        textView.invalidate()
                    }
                })
        return drawable
    }
}