package com.njp.smartlab.ui.history

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentHistoryBinding
import com.njp.smartlab.base.MainActivity
import com.njp.smartlab.utils.ToastUtil
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 历史纪录页面
 */
class HistoryFragment : BaseFragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: HistoryViewModel

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        viewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.itemAnimator = SlideInUpAnimator()
        binding.recyclerView.adapter = SlideInBottomAnimationAdapter(viewModel.adapter)

        binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary)

        return binding.root
    }

    override fun initEvent() {

        binding.toolbar.setNavigationOnClickListener { _ ->
            (activity as MainActivity).navController.navigateUp()
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        binding.refreshLayout.isRefreshing = true
        viewModel.refresh()

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: HistoryEvent) {
        when (event.id) {
            HistoryEvent.refreshSuccess -> {
                binding.refreshLayout.isRefreshing = false
            }
            HistoryEvent.refreshFail -> {
                binding.refreshLayout.isRefreshing = false
                ToastUtil.getInstance().show(event.msg)
            }
        }
    }


}