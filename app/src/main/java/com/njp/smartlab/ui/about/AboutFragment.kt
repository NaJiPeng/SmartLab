package com.njp.smartlab.ui.about

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentAboutBinding
import com.njp.smartlab.ui.main.MainActivity
import com.njp.smartlab.utils.getVersionInfo

/**
 * 关于页面
 */
class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)


        binding.toolbar.setNavigationOnClickListener { _ ->
            (activity as MainActivity).navController.navigateUp()
        }

        val versionInfo = getVersionInfo()
        binding.tvVersionInfo.text = "${versionInfo.second} (${versionInfo.first})"

        return binding.root
    }

}