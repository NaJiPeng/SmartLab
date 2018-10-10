package com.njp.smartlab.ui.files

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentFilesBinding

class FilesFragment : Fragment() {

    private lateinit var binding: FragmentFilesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_files, container, false)


        return binding.root
    }

}