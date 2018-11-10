package com.njp.smartlab.ui.news

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.ui.main.MainActivity
import com.njp.smartlab.databinding.FragmentNewsBinding
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.loadsir.FailCallback
import com.njp.smartlab.utils.loadsir.LoadingCallback
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 科技资讯页面
 */
class NewsFragment : BaseFragment() {


    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var loadService: LoadService<*>

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        binding.banner.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                Glide.with(context!!)
                        .load(path)
                        .into(imageView!!)
            }
        })
        binding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)

        binding.recyclerView.itemAnimator = SlideInUpAnimator()

        loadService = LoadSir.getDefault().register(binding.root) {
            loadService.showCallback(LoadingCallback::class.java)
            viewModel.refresh()
        }

        return loadService.loadLayout
    }

    override fun initEvent() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.refreshLayout.setOnLoadMoreListener {
            viewModel.loadmore()
        }

        binding.banner.setOnBannerListener {
            (activity as MainActivity).navController.navigate(
                    R.id.action_home_to_news_detail,
                    Bundle().apply {
                        putString("type", "banner")
                        putString("title", viewModel.banners.value?.get(it)?.name)
                        putString("url", viewModel.banners.value?.get(it)?.link)
                    }
            )
        }

        viewModel.dataAdapter.setListener {
            (activity as MainActivity).navController.navigate(
                    R.id.action_home_to_news_detail,
                    Bundle().apply {
                        putString("type", "blog")
                        putString("title", it.name)
                        putString("url", it.link)
                    }
            )
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
        if (viewModel.isFirstLoad) {
            loadService.showCallback(LoadingCallback::class.java)
            viewModel.refresh()
        } else {
            loadService.showSuccess()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: NewsEvent) {
        when (event.id) {
            NewsEvent.bannerSuccess -> {
                if (viewModel.isFirstLoad) {
                    loadService.showSuccess()
                    viewModel.isFirstLoad = false
                } else {
                    binding.refreshLayout.finishRefresh()
                }
            }
            NewsEvent.bannerFail -> {
                if (viewModel.isFirstLoad) {
                    loadService.showCallback(FailCallback::class.java)
                } else {
                    binding.refreshLayout.finishRefresh(false)
                }
                ToastUtil.getInstance().show(event.msg)
            }
            NewsEvent.newsSuccess -> {
                binding.refreshLayout.finishLoadMore()
            }
            NewsEvent.newsFail -> {
                binding.refreshLayout.finishLoadMore(false)
                ToastUtil.getInstance().show(event.msg)
            }
        }
    }

}