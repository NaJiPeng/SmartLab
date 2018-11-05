package com.njp.smartlab.ui.locker

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentLockerBinding
import com.njp.smartlab.utils.Logger
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.loadsir.FailCallback
import com.njp.smartlab.utils.loadsir.LoadingCallback
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 智慧物联网页面
 */
class LockerFragment : BaseFragment() {

    private lateinit var binding: FragmentLockerBinding
    private lateinit var loadService: LoadService<*>
    private lateinit var viewModel: LockerViewModel

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_locker, container, false)
        viewModel = ViewModelProviders.of(this).get(LockerViewModel::class.java)
        binding.viewModel = viewModel

        loadService = LoadSir.getDefault().register(binding.root) {
            viewModel.getTools()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary)

        (binding.recyclerView.layoutManager as GridLayoutManager).spanCount = 2
        binding.recyclerView.itemAnimator = SlideInLeftAnimator()

        return loadService.loadLayout
    }

    override fun initEvent() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getTools()
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onLazyLoad() {
        loadService.showCallback(LoadingCallback::class.java)
        viewModel.getTools()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: LockerEvent) {
        when (event.id) {
            LockerEvent.lockerSuccess -> {
                viewModel.isFirstLoad = false
                loadService.showSuccess()
                binding.refreshLayout.isRefreshing = false
            }
            LockerEvent.lockerFail -> {
                if (viewModel.isFirstLoad) {
                    loadService.showCallback(FailCallback::class.java)
                } else {
                    binding.refreshLayout.isRefreshing = false
                }
                ToastUtil.getInstance().show(event.msg)

            }
        }
    }

}