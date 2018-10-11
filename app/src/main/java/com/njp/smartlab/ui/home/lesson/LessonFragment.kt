package com.njp.smartlab.ui.home.lesson

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentLessonBinding

/**
 * 课程信息页面
 */
class LessonFragment : Fragment() {

    private lateinit var binding: FragmentLessonBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson, container, false)


        return binding.root
    }

}