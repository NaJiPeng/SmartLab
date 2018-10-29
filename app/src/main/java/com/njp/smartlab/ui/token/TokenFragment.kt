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
import com.njp.smartlab.base.MainActivity
import com.njp.smartlab.databinding.FragmentTokenBinding
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.loadsir.ImageErrorCallback
import com.njp.smartlab.utils.loadsir.ScanningCallback

/**
 * 身份令牌页面
 */
class TokenFragment : BaseFragment() {

    private lateinit var binding: FragmentTokenBinding
    private lateinit var viewModel: TokenViewModel
    private lateinit var loadService: LoadService<*>

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_token, container, false)
        viewModel = ViewModelProviders.of(this).get(TokenViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        loadService = LoadSir.getDefault().register(binding.imgQrCode){
            loadService.showCallback(ScanningCallback::class.java)
            Thread {
                Thread.sleep(3000)
                activity?.runOnUiThread {
                    loadService.showCallback(ImageErrorCallback::class.java)
                }
            }.start()
        }

        loadService.showCallback(ImageErrorCallback::class.java)

        return binding.root
    }

    override fun initEvent() {
        binding.toolbar.setNavigationOnClickListener {
            (activity as MainActivity).navController.navigateUp()
        }

        binding.toolbar.inflateMenu(R.menu.menu_token)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.refresh -> {
                    ToastUtil.getInstance().show("刷新")
                }
            }
            false
        }

    }
}