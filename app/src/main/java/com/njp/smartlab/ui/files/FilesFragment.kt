package com.njp.smartlab.ui.files

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentFilesBinding
import com.njp.smartlab.ui.main.MainActivity

/**
 * 课程资源页面
 */
class FilesFragment : Fragment() {

    private lateinit var binding: FragmentFilesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_files, container, false)

        binding.toolbar.setNavigationOnClickListener { _->
            (activity as MainActivity).navController.navigateUp()
        }

        return binding.root
    }

}