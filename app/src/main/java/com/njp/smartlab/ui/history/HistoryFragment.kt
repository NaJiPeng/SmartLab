package com.njp.smartlab.ui.history

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentHistoryBinding
import com.njp.smartlab.base.MainActivity

/**
 * 历史纪录页面
 */
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)

        binding.toolbar.setNavigationOnClickListener { _->
            (activity as MainActivity).navController.navigateUp()
        }

        return binding.root
    }

}