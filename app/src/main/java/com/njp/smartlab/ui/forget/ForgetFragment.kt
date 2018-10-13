package com.njp.smartlab.ui.forget

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentForgetBinding
import com.njp.smartlab.ui.MainActivity

/**
 * 注册页面
 */
class ForgetFragment : Fragment() {

    private lateinit var binding: FragmentForgetBinding
    private lateinit var viewModel: ForgetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forget, container, false)
        viewModel = ViewModelProviders.of(this).get(ForgetViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        initEvent()

        return binding.root
    }

    private fun check(): Boolean {

        return true
    }


    private fun initEvent() {
        binding.tvLogin.setOnClickListener { _->
            (activity as MainActivity).navController.navigateUp()
        }
    }

}