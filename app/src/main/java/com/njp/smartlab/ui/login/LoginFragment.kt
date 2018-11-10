package com.njp.smartlab.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentLoginBinding
import com.njp.smartlab.ui.main.MainActivity
import com.njp.smartlab.utils.ToastUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 登录页面
 */
class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun initEvent() {

        binding.btnLogin.setOnClickListener {
            if (checkout()) {
                (activity as MainActivity).loadingDialog.show()
                viewModel.login()
            }
        }

        binding.tvRegister.setOnClickListener { _ ->
            (activity as MainActivity).navController.navigate(R.id.action_login_to_register)
        }

        binding.tvForget.setOnClickListener { _ ->
            (activity as MainActivity).navController.navigate(R.id.action_login_to_forget)
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    /**
     * 输入校验
     */
    private fun checkout(): Boolean {
        if (viewModel.account.value.isNullOrEmpty()) {
            binding.etAccount.requestFocus()
            binding.etAccount.error = "学号不能为空"
            return false
        }
        if (viewModel.password.value.isNullOrEmpty()) {
            binding.etPassword.requestFocus()
            binding.etPassword.error = "密码不能为空"
            return false
        } else if (viewModel.password.value?.length ?: 0 < 6) {
            binding.etPassword.requestFocus()
            binding.etPassword.error = "密码长度为6-18位"
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: LoginEvent) {
        when (event.id) {
            LoginEvent.loginSuccess -> {
                (activity as MainActivity).loadingDialog.dismiss()
                (activity as MainActivity).navController.navigateUp()
            }
            LoginEvent.loginFail -> {
                (activity as MainActivity).loadingDialog.dismiss()
                ToastUtil.getInstance().show(event.msg)
            }
        }
    }

}