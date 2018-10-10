package com.njp.smartlab.ui

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.njp.smartlab.R
import com.njp.smartlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.START)
        }

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        NavigationUI.setupWithNavController(binding.navigationView,fragment.navController)

//        binding.navigationView.setNavigationItemSelectedListener {
//
//
//        }

    }
}
