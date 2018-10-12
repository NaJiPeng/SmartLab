package com.njp.smartlab.ui.login

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentLoginBinding
import com.njp.smartlab.ui.MainActivity

/**
 * 登录页面
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        initEvent()

        return binding.root
    }

    private fun initEvent() {

        binding.btnLogin.setOnClickListener {
            if (checkout()) {
                (activity as MainActivity).loadingDialog.show()
            }
        }

        binding.tvRegister.setOnClickListener { _ ->
            (activity as MainActivity).navController.navigate(R.id.action_login_to_register)
        }
    }

    /**
     * 输入校验
     */
    private fun checkout(): Boolean {
        if (viewModel.account.value.isNullOrEmpty()) {
            binding.etAccount.error = "账号不能为空"
            return false
        }
        if (viewModel.password.value.isNullOrEmpty()) {
            binding.etPassword.error = "密码不能为空"
            return false
        } else if (viewModel.password.value?.length ?: 0 < 6) {
            binding.etPassword.error = "密码长度为6-18位"
            return false
        }
        return true
    }


}