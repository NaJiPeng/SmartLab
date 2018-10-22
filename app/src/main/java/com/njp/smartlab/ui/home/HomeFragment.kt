package com.njp.smartlab.ui.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.njp.smartlab.R
import com.njp.smartlab.adapter.HomePagerAdapter
import com.njp.smartlab.databinding.FragmentHomeBinding
import com.njp.smartlab.ui.MainActivity
import com.njp.smartlab.utils.ToastUtil
import com.tencent.mmkv.MMKV

/**
 * 主页面
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        setupViewPager()

        setupNavigationView()

        setupToolbar()

        return binding.root
    }

    /**
     * 配置ViewPager和BottomNavigationView
     */
    private fun setupViewPager() {
        binding.viewPager.adapter = HomePagerAdapter(childFragmentManager)
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                binding.bottomNavigationView.selectedItemId = when (p0) {
                    0 -> R.id.news
                    1 -> R.id.lesson
                    2 -> R.id.network
                    else -> 0
                }
            }

        })

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            binding.viewPager.currentItem = when (it.itemId) {
                R.id.news -> 0
                R.id.lesson -> 1
                R.id.network -> 2
                else -> 0
            }
            true
        }
    }

    /**
     * 配置侧滑菜单
     */
    private fun setupNavigationView() {
        binding.navigationView.setNavigationItemSelectedListener {
            binding.drawerLayout.closeDrawer(Gravity.START)
            when (it.itemId) {
                R.id.timetable -> {
                    (activity as MainActivity).navController.navigate(R.id.action_home_to_timetable)
                }
                R.id.files -> {
                    (activity as MainActivity).navController.navigate(R.id.action_home_to_files)
                }
                R.id.history -> {
                    (activity as MainActivity).navController.navigate(R.id.action_home_to_history)
                }
                R.id.about -> {
                    (activity as MainActivity).navController.navigate(R.id.action_home_to_about)
                }
                R.id.night_mode -> {
                    if (MMKV.defaultMMKV().decodeBool("night")) {
                        MMKV.defaultMMKV().encode("night", false)
                    } else {
                        MMKV.defaultMMKV().encode("night", true)
                    }
                    ToastUtil.getInstance().show("夜间模式:${
                    if (MMKV.defaultMMKV().decodeBool("night")) "开" else "关"
                    }")
                }
                R.id.logout -> {
                    ToastUtil.getInstance().show("退出登录")
                }
            }
            true
        }

        binding.headLayout.setOnClickListener { _ ->
            (activity as MainActivity).navController.navigate(R.id.action_home_to_login)
        }
    }

    /**
     * 配置标题栏
     */
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { _ ->
            binding.drawerLayout.openDrawer(Gravity.START)
        }
    }

}