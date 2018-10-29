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
import com.njp.smartlab.utils.ToastUtil

/**
 * 主activity
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var exitTime: Long = 0
    lateinit var navController: NavController
    lateinit var loadingDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = fragment.navController

        loadingDialog = Dialog(this, R.style.dialog).apply {
            setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_loading, null))
            setCancelable(false)
        }

    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.home && (System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis()
            ToastUtil.getInstance().show("再按一次退出程序")
        } else {
            super.onBackPressed()
        }

    }

}
