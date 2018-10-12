package com.njp.smartlab.ui.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.njp.smartlab.R
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

        val fragment = childFragmentManager.findFragmentById(R.id.fragment_bottom) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView, fragment.navController)

        binding.toolbar.setNavigationOnClickListener { _ ->
            binding.drawerLayout.openDrawer(Gravity.START)
        }

        binding.navigationView.setCheckedItem(R.id.home)
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


        return binding.root
    }

}