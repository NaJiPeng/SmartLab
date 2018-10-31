package com.njp.smartlab.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.njp.smartlab.R
import com.njp.smartlab.adapter.HomePagerAdapter
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentHomeBinding
import com.njp.smartlab.base.MainActivity
import com.njp.smartlab.bean.User
import com.njp.smartlab.network.NetworkConfig
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.UserInfoHolder
import com.tencent.mmkv.MMKV

/**
 * 主页面
 */
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun initEvent() {
        setupViewPager()

        setupNavigationView()

        setupToolbar()
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
                    UserInfoHolder.getInstance().clearUser()
                    (NetworkConfig.getInstance().client.cookieJar() as ClearableCookieJar).clear()
                    ToastUtil.getInstance().show("已退出登录")
                }
            }
            true
        }

        binding.imgHead.setOnClickListener { _ ->
            if (viewModel.userInfo.value == null) {
                (activity as MainActivity).navController.navigate(R.id.action_home_to_login)
            } else {
                (activity as MainActivity).navController.navigate(R.id.action_home_to_update)
            }
        }
    }

    /**
     * 配置标题栏
     */
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { _ ->
            binding.drawerLayout.openDrawer(Gravity.START)
        }

        binding.toolbar.inflateMenu(R.menu.menu_home)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.token -> {
                    if (UserInfoHolder.getInstance().getUser().value != null) {
                        (activity as MainActivity).navController.navigate(R.id.action_home_to_token)
                    }else{
                        ToastUtil.getInstance().show("你还没登录")
                    }
                }
            }
            false
        }
    }

}