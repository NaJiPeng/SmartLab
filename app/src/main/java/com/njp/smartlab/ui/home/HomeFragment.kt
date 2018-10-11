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
                R.id.timetable -> (activity as MainActivity).navController.navigate(R.id.action_home_to_timetable)
                R.id.files -> (activity as MainActivity).navController.navigate(R.id.action_home_to_files)
                R.id.history -> (activity as MainActivity).navController.navigate(R.id.action_home_to_history)
            }
            true
        }

        return binding.root
    }

}