package com.njp.smartlab.ui.home.news

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentNewsBinding
import com.njp.smartlab.utils.ToastUtil
import com.njp.smartlab.utils.loadsir.FailCallback
import com.njp.smartlab.utils.loadsir.LoadingCallback

/**
 * 科技资讯页面
 */
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var loadService: LoadService<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)

        loadService = LoadSir.getDefault().register(binding.root) {
            loadService.showCallback(LoadingCallback::class.java)
            Thread{
                Thread.sleep(3000)
                activity?.runOnUiThread {
                    loadService.showCallback(FailCallback::class.java)
                }
            }.start()
        }

        loadService.showCallback(FailCallback::class.java)

        return loadService.loadLayout
    }

}