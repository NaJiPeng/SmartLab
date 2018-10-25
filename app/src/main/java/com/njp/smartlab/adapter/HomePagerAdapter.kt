package com.njp.smartlab.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.njp.smartlab.ui.lesson.LessonFragment
import com.njp.smartlab.ui.network.NetworkFragment
import com.njp.smartlab.ui.news.NewsFragment

/**
 * 主页面ViewPager适配器
 */
class HomePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = listOf(
            NewsFragment(), LessonFragment(), NetworkFragment()
    )

    override fun getItem(p0: Int) = fragments[p0]

    override fun getCount() = fragments.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
    }
}