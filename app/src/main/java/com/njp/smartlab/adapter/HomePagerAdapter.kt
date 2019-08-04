package com.njp.smartlab.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import com.njp.smartlab.ui.lesson.LessonFragment
import com.njp.smartlab.ui.locker.LockerFragment
import com.njp.smartlab.ui.news.NewsFragment

/**
 * 主页面ViewPager适配器
 */
class HomePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments = listOf(
            NewsFragment(), LessonFragment(), LockerFragment()
    )

    override fun getItem(p0: Int) = fragments[p0]

    override fun getCount() = fragments.size

}