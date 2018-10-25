package com.njp.smartlab.ui.about

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentAboutBinding
import com.njp.smartlab.base.MainActivity

/**
 * 关于页面
 */
class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)


        binding.toolbar.setNavigationOnClickListener { _->
            (activity as MainActivity).navController.navigateUp()
        }

        return binding.root
    }

}