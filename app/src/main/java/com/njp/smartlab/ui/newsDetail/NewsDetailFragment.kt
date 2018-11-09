package com.njp.smartlab.ui.newsDetail

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
import com.njp.smartlab.databinding.FragmentNewsDetailBinding
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.loadsir.FailCallback
import com.njp.smartlab.utils.loadsir.LoadingCallback
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class NewsDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsDetailBinding
    private lateinit var viewModel: NewsDetailViewModel
    private lateinit var loadService: LoadService<*>

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_detail, container, false)
        viewModel = ViewModelProviders.of(this).get(NewsDetailViewModel::class.java).apply {
            type = arguments?.getString("type") ?: ""
            title = arguments?.getString("title") ?: "新闻详情"
            url = arguments?.getString("url") ?: ""
        }
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        loadService = LoadSir.getDefault().register(binding.contentLayout) {
            loadService.showCallback(LoadingCallback::class.java)
            viewModel.getDetail()
        }


        return binding.root
    }

    override fun initEvent() {
        binding.toolbar.setNavigationOnClickListener {
            (activity as MainActivity).navController.navigateUp()
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        loadService.showCallback(LoadingCallback::class.java)
        viewModel.getDetail()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: NewsDetailEvent) {
        when (event.id) {
            NewsDetailEvent.newsSuccess -> {
                loadService.showSuccess()
            }
            NewsDetailEvent.newsFail -> {
                loadService.showCallback(FailCallback::class.java)
                ToastUtil.getInstance().show(event.msg)
            }
        }
    }

}