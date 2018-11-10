package com.njp.smartlab.ui.timetable

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentTimetableBinding
import com.njp.smartlab.ui.main.MainActivity
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.loadsir.FailCallback
import com.njp.smartlab.utils.loadsir.LoadingCallback
import com.zhuangfei.timetable.listener.IWeekView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 我的课表页面
 */
class TimetableFragment : BaseFragment() {

    private lateinit var binding: FragmentTimetableBinding
    private lateinit var viewModel: TimeTableViewModel
    private lateinit var loadService: LoadService<*>

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timetable, container, false)
        viewModel = ViewModelProviders.of(this).get(TimeTableViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        loadService = LoadSir.getDefault().register(binding.contentLayout) {
            loadService.showCallback(LoadingCallback::class.java)
            viewModel.getLessons()
        }

        return binding.root
    }

    override fun initEvent() {
        binding.toolbar.setNavigationOnClickListener { _ ->
            (activity as MainActivity).navController.navigateUp()
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        binding.weekView.callback(IWeekView.OnWeekItemClickedListener {
            binding.timetableView.changeWeekOnly(it)
        })

        loadService.showCallback(LoadingCallback::class.java)
        viewModel.getLessons()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: TimeTableEvent) {
        when (event.id) {
            TimeTableEvent.lessonsSuccess -> {
                loadService.showSuccess()
            }
            TimeTableEvent.lessonsFail -> {
                loadService.showCallback(FailCallback::class.java)
                ToastUtil.getInstance().show(event.msg)
            }
        }
    }


}