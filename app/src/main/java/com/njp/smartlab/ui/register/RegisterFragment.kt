package com.njp.smartlab.ui.register

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentRegisterBinding
import com.njp.smartlab.utils.ToastUtil

/**
 * 注册页面
 */
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        initEvent()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEvent() {
        binding.etEmail.setOnTouchListener { view, motionEvent ->
            val drawable = binding.etEmail.compoundDrawables[2] ?: return@setOnTouchListener false
            if (motionEvent.action != MotionEvent.ACTION_UP) {
                return@setOnTouchListener false
            }
            if (motionEvent.x > binding.etEmail.width - binding.etEmail.paddingRight - drawable.intrinsicWidth) {
                ToastUtil.getInstance().show("发送验证码")
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
    }

}