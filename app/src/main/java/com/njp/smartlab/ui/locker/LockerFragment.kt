package com.njp.smartlab.ui.locker

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentLockerBinding
import com.njp.smartlab.utils.loadsir.FailCallback
import com.njp.smartlab.utils.loadsir.LoadingCallback

/**
 * 智慧物联网页面
 */
class LockerFragment : BaseFragment() {

    private lateinit var binding: FragmentLockerBinding
    private lateinit var loadService: LoadService<*>

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_locker, container, false)

        loadService = LoadSir.getDefault().register(binding.root) {
            loadService.showCallback(LoadingCallback::class.java)
            Thread {
                Thread.sleep(3000)
                activity?.runOnUiThread {
                    loadService.showCallback(FailCallback::class.java)
                }
            }.start()
        }

        return loadService.loadLayout
    }
    override fun initEvent() {

    }

    override fun onLazyLoad() {
        loadService.showCallback(LoadingCallback::class.java)
        Thread{
            Thread.sleep(3000)
            activity?.runOnUiThread {
                loadService.showCallback(FailCallback::class.java)
            }
        }.start()
    }
}