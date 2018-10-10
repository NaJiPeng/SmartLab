package com.njp.smartlab.ui.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val fragment = childFragmentManager.findFragmentById(R.id.fragment_bottom) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView, fragment.navController)

        return binding.root
    }

}