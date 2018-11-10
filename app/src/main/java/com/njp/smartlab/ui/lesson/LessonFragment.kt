package com.njp.smartlab.ui.lesson

import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.ui.main.MainActivity
import com.njp.smartlab.databinding.FragmentLessonBinding
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.UserInfoHolder
import com.njp.smartlab.utils.loadsir.FailCallback
import com.njp.smartlab.utils.loadsir.LoadingCallback
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 课程信息页面
 */
class LessonFragment : BaseFragment() {

    private lateinit var binding: FragmentLessonBinding
    private lateinit var viewModel: LessonViewModel
    private lateinit var dialog: AlertDialog
    private lateinit var loadService: LoadService<*>

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson, container, false)
        viewModel = ViewModelProviders.of(this).get(LessonViewModel::class.java)
        binding.viewModel = viewModel

        loadService = LoadSir.getDefault().register(binding.root) {
            loadService.showCallback(LoadingCallback::class.java)
            viewModel.getLessons()
        }

        dialog = AlertDialog.Builder(context!!)
                .setMessage("你还没有登录，是否前去登录？")
                .setNegativeButton("算了") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }.setPositiveButton("登录") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                    (activity as MainActivity).navController.navigate(R.id.action_home_to_login)
                }
                .create()

        binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary)

        binding.recyclerView.itemAnimator = SlideInLeftAnimator()

        return loadService.loadLayout
    }

    override fun initEvent() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getLessons()
        }

        viewModel.dataAdapter.setListener {
            AlertDialog.Builder(context!!)
                    .setMessage("是否预约${it.name}?")
                    .setPositiveButton("预约") { dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                        if (UserInfoHolder.getInstance().getUser().value != null) {
                            (activity as MainActivity).loadingDialog.show()
                            viewModel.choose(it.activityId)
                        } else {
                            dialog.show()
                        }
                    }.setNegativeButton("取消") { dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }.show()
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
            LessonEvent.chooseSuccess -> {
                (activity as MainActivity).loadingDialog.dismiss()
                ToastUtil.getInstance().show("选课成功")
            }
            LessonEvent.chooseFail -> {
                (activity as MainActivity).loadingDialog.dismiss()
                ToastUtil.getInstance().show(event.msg)
            }
        }
    }


}