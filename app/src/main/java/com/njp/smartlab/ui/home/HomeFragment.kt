package com.njp.smartlab.ui.home

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.njp.smartlab.R
import com.njp.smartlab.adapter.HomePagerAdapter
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentHomeBinding
import com.njp.smartlab.ui.main.MainActivity
import com.njp.smartlab.network.NetworkConfig
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.UserInfoHolder
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 主页面
 */
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var dialog: AlertDialog

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        dialog = AlertDialog.Builder(context)
                .setMessage("你还没有登录，是否前去登录？")
                .setNegativeButton("算了") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }.setPositiveButton("登录") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                    (activity as MainActivity).navController.navigate(R.id.action_home_to_login)
                }
                .create()

        return binding.root
    }

    override fun initEvent() {
        setupViewPager()

        setupNavigationView()

        setupToolbar()

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    /**
     * 配置ViewPager和BottomNavigationView
     */
    private fun setupViewPager() {
        binding.viewPager.adapter = HomePagerAdapter(childFragmentManager)
        binding.viewPager.offscreenPageLimit = 3
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
                    if (UserInfoHolder.getInstance().getUser().value != null) {
                        (activity as MainActivity).navController.navigate(R.id.action_home_to_timetable)
                    } else {
                        dialog.show()
                    }
                }
                R.id.files -> {
                    if (UserInfoHolder.getInstance().getUser().value != null) {
                        (activity as MainActivity).navController.navigate(R.id.action_home_to_files)
                    } else {
                        dialog.show()
                    }
                }
                R.id.history -> {
                    if (UserInfoHolder.getInstance().getUser().value != null) {
                        (activity as MainActivity).navController.navigate(R.id.action_home_to_history)
                    } else {
                        dialog.show()
                    }
                }
                R.id.about -> {
                    (activity as MainActivity).navController.navigate(R.id.action_home_to_about)
                }
                R.id.update -> {
                    (activity as MainActivity).loadingDialog.show()
                    viewModel.checkUpdate()
                }
                R.id.logout -> {
                    if (UserInfoHolder.getInstance().getUser().value != null) {
                        UserInfoHolder.getInstance().clearUser()
                        (NetworkConfig.getInstance().client.cookieJar() as ClearableCookieJar).clear()
                        ToastUtil.getInstance().show("已退出登录")
                    } else {
                        ToastUtil.getInstance().show("未登录")
                    }
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
                        (activity as MainActivity).navController.navigate(
                                R.id.action_home_to_token,
                                Bundle().apply {
                                    putString("type", "open")
                                    putInt("boxId", 0)
                                }
                        )
                    } else {
                        dialog.show()
                    }
                }
            }
            false
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: HomeEvent) {
        when (event.id) {
            HomeEvent.updateSuccess -> {
                (activity as MainActivity).loadingDialog.dismiss()
            }
            HomeEvent.updateFail -> {
                (activity as MainActivity).loadingDialog.dismiss()
                ToastUtil.getInstance().show(event.msg)
            }

        }
    }

}