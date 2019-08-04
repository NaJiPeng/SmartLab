package com.njp.smartlab.ui.resource

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentFilesBinding
import com.njp.smartlab.ui.main.MainActivity
import com.njp.smartlab.utils.ToastUtil
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 课程资源页面
 */
class ResourceFragment : BaseFragment() {
    private lateinit var binding: FragmentFilesBinding
    private lateinit var viewModel: ResourceViewModel

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_files, container, false)
        viewModel = ViewModelProviders.of(this).get(ResourceViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        binding.recyclerView.itemAnimator = SlideInUpAnimator()
        binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary)

        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun initEvent() {
        binding.toolbar.setNavigationOnClickListener { _ ->
            (activity as MainActivity).navController.navigateUp()
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getResource()
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        binding.refreshLayout.isRefreshing = true
        viewModel.getResource()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: ResourceEvent) {
        when (event.id) {
            ResourceEvent.resourceSuccess -> {
                binding.refreshLayout.isRefreshing = false
            }
            ResourceEvent.resourceFail -> {
                binding.refreshLayout.isRefreshing = false
                ToastUtil.getInstance().show(event.msg)
            }
        }
    }


}