package com.njp.smartlab.ui.timetable

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.databinding.FragmentTimetableBinding

class TimetableFragment : Fragment() {

    private lateinit var binding: FragmentTimetableBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timetable, container, false)


        return binding.root
    }

}