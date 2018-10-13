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
import com.njp.smartlab.ui.MainActivity
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

    private fun check(): Boolean {
        if (viewModel.accont.value.isNullOrEmpty()) {
            binding.etAccount.requestFocus()
            binding.etAccount.error = "账号不能为空"
            return false
        }
        if (viewModel.name.value.isNullOrEmpty()) {
            binding.etName.requestFocus()
            binding.etName.error = "姓名不能为空"
            return false
        }
        if (viewModel.password.value.isNullOrEmpty()) {
            binding.etPassword.requestFocus()
            binding.etPassword.error = "密码不能为空"
            return false
        }
        if (viewModel.password.value?.length ?: 0 < 6) {
            binding.etPassword.requestFocus()
            binding.etPassword.error = "密码长度为6-18位"
            return false
        }
        if (viewModel.password.value != viewModel.rePassword.value) {
            binding.etRePassword.requestFocus()
            binding.etRePassword.error = "两次输入的密码不一致"
            return false
        }
        if (viewModel.email.value.isNullOrEmpty()) {
            binding.etEmail.requestFocus()
            binding.etEmail.error = "邮箱不能为空"
            return false
        }
        if (viewModel.email.value?.matches("[\\w!#\$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#\$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?".toRegex()) != true) {
            binding.etEmail.requestFocus()
            binding.etEmail.error = "邮箱格式不正确"
            return false
        }
        if (viewModel.validate.value.isNullOrEmpty()) {
            binding.etValidate.requestFocus()
            binding.etValidate.error = "验证码不能为空"
            return false
        }

        if (viewModel.validate.value?.length ?: 0 < 6) {
            binding.etValidate.requestFocus()
            binding.etValidate.error = "验证码为6位"
            return false
        }
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEvent() {

        binding.etEmail.setOnTouchListener { _, motionEvent ->
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

        binding.tvLogin.setOnClickListener { _ ->
            (activity as MainActivity).navController.navigateUp()
        }

        binding.btnRegister.setOnClickListener { _ ->
            if (check()) {
                (activity as MainActivity).loadingDialog.show()
                Thread {
                    Thread.sleep(3000)
                    activity?.runOnUiThread {
                        (activity as MainActivity).loadingDialog.dismiss()
                        (activity as MainActivity).navController.navigateUp()
                    }
                }.start()
            }
        }
    }

}