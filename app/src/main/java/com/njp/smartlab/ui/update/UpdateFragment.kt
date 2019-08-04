package com.njp.smartlab.ui.update

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.view.*
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.ui.main.MainActivity
import com.njp.smartlab.databinding.FragmentUpdateBinding
import com.njp.smartlab.utils.ToastUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class UpdateFragment : BaseFragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var viewModel: UpdateViewModel

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)
        viewModel = ViewModelProviders.of(this).get(UpdateViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun initEvent() {
        setupToolbar()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            (activity as MainActivity).navController.navigateUp()
        }
        binding.toolbar.inflateMenu(R.menu.menu_update)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.commit -> {
                    (activity as MainActivity).loadingDialog.show()
                    viewModel.update()
                }
            }
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: UpdateEvent) {
        when (event.id) {
            UpdateEvent.updateSuccess -> {
                (activity as MainActivity).loadingDialog.dismiss()
                (activity as MainActivity).navController.navigateUp()
            }
            UpdateEvent.updateFail -> {
                (activity as MainActivity).loadingDialog.dismiss()
                ToastUtil.getInstance().show(event.msg)
            }
        }
    }

}