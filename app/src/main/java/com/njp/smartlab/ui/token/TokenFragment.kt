package com.njp.smartlab.ui.token

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.ui.main.MainActivity
import com.njp.smartlab.databinding.FragmentTokenBinding
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.loadsir.ImageErrorCallback
import com.njp.smartlab.utils.loadsir.ScanningCallback
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 身份令牌页面
 */
class TokenFragment : BaseFragment() {

    private lateinit var binding: FragmentTokenBinding
    private lateinit var viewModel: TokenViewModel
    private lateinit var loadService: LoadService<*>

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_token, container, false)
        viewModel = ViewModelProviders.of(this).get(TokenViewModel::class.java).apply {
            type = arguments?.getString("type") ?: "open"
            boxId = arguments?.getInt("boxId") ?: 0
        }
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun initEvent() {
        loadService = LoadSir.getDefault().register(binding.imgQrCode) {
            refresh()
        }

        binding.toolbar.setNavigationOnClickListener {
            (activity as MainActivity).navController.navigateUp()
        }

        binding.toolbar.inflateMenu(R.menu.menu_token)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.refresh -> {
                    refresh()
                }
            }
            false
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        refresh()

    }

    private fun refresh() {
        if (viewModel.isLoading) {
            ToastUtil.getInstance().show("正在刷新")
        } else {
            viewModel.isLoading = true
            loadService.showCallback(ScanningCallback::class.java)
            binding.countDownView.stop()
            binding.countDownView.allShowZero()
            viewModel.isLoading = true
            viewModel.refresh()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: TokenEvent) {
        when (event.id) {
            TokenEvent.tokenSuccess -> {
                loadService.showSuccess()
                binding.countDownView.apply {
                    start(61000)
                    setOnCountdownEndListener {
                        refresh()
                    }
                }
            }
            TokenEvent.tokenFail -> {
                ToastUtil.getInstance().show(event.msg)
                binding.countDownView.allShowZero()
                loadService.showCallback(ImageErrorCallback::class.java)
            }

        }
    }

}