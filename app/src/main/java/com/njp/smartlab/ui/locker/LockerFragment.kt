package com.njp.smartlab.ui.locker

import androidx.lifecycle.ViewModelProviders
import android.content.DialogInterface
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentLockerBinding
import com.njp.smartlab.ui.main.MainActivity
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.UserInfoHolder
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
    private lateinit var dialog: AlertDialog

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_locker, container, false)
        viewModel = ViewModelProviders.of(this).get(LockerViewModel::class.java)
        binding.viewModel = viewModel

        loadService = LoadSir.getDefault().register(binding.root) {
            loadService.showCallback(LoadingCallback::class.java)
            viewModel.getTools()
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

        (binding.recyclerView.layoutManager as GridLayoutManager).spanCount = 2
        binding.recyclerView.itemAnimator = SlideInLeftAnimator()

        return loadService.loadLayout
    }

    override fun initEvent() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getTools()
        }

        viewModel.dataAdapter.setListener { tool ->
            if (UserInfoHolder.getInstance().getUser().value == null) {
                dialog.show()
            } else {
                AlertDialog.Builder(context!!)
                        .setMessage("是否${if (tool.borrowed) "归还" else "借用"}${tool.boxId}号储物柜的物品？")
                        .setPositiveButton("确定") { _: DialogInterface, _: Int ->
                            (activity as MainActivity).navController.navigate(
                                    R.id.action_home_to_token,
                                    Bundle().apply {
                                        putString("type", if (tool.borrowed) "return" else "borrow")
                                        putInt("boxId", tool.boxId)
                                    }
                            )
                        }.setNegativeButton("取消") { dialogInterface: DialogInterface, _: Int ->
                            dialogInterface.dismiss()
                        }.show()
            }
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
            viewModel.getTools()
        } else {
            loadService.showSuccess()
        }
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