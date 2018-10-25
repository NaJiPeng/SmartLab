package com.njp.smartlab.base

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.njp.smartlab.R
import com.njp.smartlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var loadingDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = fragment.navController

        loadingDialog = Dialog(this, R.style.dialog).apply {
            setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_loading,null))
            setCancelable(false)
        }

    }
}
