package com.njp.smartlab.ui.lesson

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentLessonBinding
import com.njp.smartlab.utils.Logger
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.loadsir.FailCallback
import com.njp.smartlab.utils.loadsir.LoadingCallback
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 课程信息页面
 */
class LessonFragment : BaseFragment() {

    private lateinit var binding: FragmentLessonBinding
    private lateinit var viewModel: LessonViewModel
    private lateinit var loadService: LoadService<*>

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson, container, false)
        viewModel = ViewModelProviders.of(this).get(LessonViewModel::class.java)
        binding.viewModel = viewModel

        loadService = LoadSir.getDefault().register(binding.root) {
            viewModel.getLessons()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary)

        binding.recyclerView.itemAnimator = SlideInLeftAnimator()

        return loadService.loadLayout
    }

    override fun initEvent() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getLessons()
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onLazyLoad() {
        if (viewModel.isFirstLoad) {
            loadService.showCallback(LoadingCallback::class.java)
            viewModel.getLessons()
        } else {
            loadService.showSuccess()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: LessonEvent) {
        when (event.id) {
            LessonEvent.lessonSuccess -> {
                viewModel.isFirstLoad = false
                loadService.showSuccess()
                binding.refreshLayout.isRefreshing = false
            }
            LessonEvent.lessonFail -> {
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