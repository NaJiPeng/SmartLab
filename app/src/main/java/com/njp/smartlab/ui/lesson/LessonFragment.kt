package com.njp.smartlab.ui.lesson

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.njp.smartlab.R
import com.njp.smartlab.base.BaseFragment
import com.njp.smartlab.databinding.FragmentLessonBinding
import com.njp.smartlab.utils.loadsir.FailCallback
import com.njp.smartlab.utils.loadsir.LoadingCallback

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

        loadService = LoadSir.getDefault().register(binding.root) {
            loadService.showCallback(LoadingCallback::class.java)
            Thread{
                Thread.sleep(3000)
                activity?.runOnUiThread {
                    loadService.showCallback(FailCallback::class.java)
                }
            }.start()
        }

        return loadService.loadLayout
    }

    override fun initEvent() {
    }

    override fun onLazyLoad() {
        loadService.showCallback(LoadingCallback::class.java)
        Thread{
            Thread.sleep(3000)
            activity?.runOnUiThread {
                loadService.showCallback(FailCallback::class.java)
            }
        }.start()
    }


}