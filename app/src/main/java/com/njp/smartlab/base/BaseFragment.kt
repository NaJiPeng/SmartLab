package com.njp.smartlab.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.greenrobot.eventbus.EventBus

/**
 * 实现了懒加载功能的父Fragment
 */
abstract class BaseFragment : Fragment() {

    private var isFirstLoad = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = initView(inflater, container)

        initEvent()

        isFirstLoad = true

        if (userVisibleHint) {
            onLazyLoad()
            isFirstLoad = false
        }

        return rootView
    }

    abstract fun initView(inflater: LayoutInflater, container: ViewGroup?): View

    abstract fun initEvent()

    open fun onLazyLoad() {
        //Load your data
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFirstLoad = false
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isFirstLoad) {
            onLazyLoad()
            isFirstLoad = false
        }
    }

}