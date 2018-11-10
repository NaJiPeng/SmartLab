package com.njp.smartlab.ui.forget

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentForgetBinding
import com.njp.smartlab.ui.main.MainActivity
import com.njp.smartlab.ui.login.LoginEvent
import com.njp.smartlab.utils.ToastUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 修改密码页面
 */
class ForgetFragment : BaseFragment() {

    private lateinit var binding: FragmentForgetBinding
    private lateinit var viewModel: ForgetViewModel

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forget, container, false)
        viewModel = ViewModelProviders.of(this).get(ForgetViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        initEvent()

        return binding.root
    }

    override fun initEvent() {
        binding.tvLogin.setOnClickListener { _ ->
            (activity as MainActivity).navController.navigateUp()
        }

        binding.imgSend.setOnClickListener {
            if (checkEmail()) {
                viewModel.changePwdVerify()
                it.isEnabled = false
            }
        }

        binding.btnCommit.setOnClickListener {
            if (check()){
                (activity as MainActivity).loadingDialog.show()
                viewModel.updatePwd()
            }
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    /**
     * 输入校验
     */
    private fun check(): Boolean {
        if (viewModel.account.value.isNullOrEmpty()) {
            binding.etAccount.requestFocus()
            binding.etAccount.error = "学号不能为空"
            return false
        }
        if (!checkEmail()) {
            return false
        }
        if (viewModel.validate.value.isNullOrEmpty()) {
            binding.etValidate.requestFocus()
            binding.etValidate.error = "验证码不能为空"
            return false
        }
        if (viewModel.validate.value?.length ?: 0 < 5) {
            binding.etValidate.requestFocus()
            binding.etValidate.error = "验证码为5位"
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
        return true
    }

    private fun checkEmail(): Boolean {
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
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: ForgetEvent) {
        when (event.id) {
            ForgetEvent.emailSuccess -> {
                ToastUtil.getInstance().show("验证码已发送")
                binding.imgSend.visibility = View.INVISIBLE
                binding.countDownView.apply {
                    visibility = View.VISIBLE
                    start(46000)
                    setOnCountdownEndListener {
                        binding.imgSend.apply {
                            visibility = View.VISIBLE
                            isEnabled = true
                        }
                        it.visibility = View.INVISIBLE
                    }
                }
            }
            ForgetEvent.emailFail -> {
                ToastUtil.getInstance().show(event.msg)
                binding.imgSend.isEnabled = true
            }
            ForgetEvent.forgetSuccess -> {
                (activity as MainActivity).loadingDialog.dismiss()
                ToastUtil.getInstance().show("修改成功！")
                EventBus.getDefault().post(LoginEvent(
                        LoginEvent.fillOut,
                        "${viewModel.account.value},${viewModel.password.value}"
                ))
                (activity as MainActivity).navController.navigateUp()
            }
            ForgetEvent.forgetFail -> {
                (activity as MainActivity).loadingDialog.dismiss()
                ToastUtil.getInstance().show(event.msg)
            }
        }
    }

}