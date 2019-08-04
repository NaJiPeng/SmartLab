package com.njp.smartlab.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.bean.Lession
import com.njp.smartlab.databinding.ItemLessonBinding

class LessonsAdapter : RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {

    private val list = ArrayList<Lession>()

    private lateinit var listener: (Lession) -> (Unit)

    fun setListener(listener: (Lession) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.item_lesson, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.binding?.lesson = list[p1]
        p0.itemView.setOnClickListener {
            listener.invoke(list[p1])
        }
    }

    fun setData(data: List<Lession>) {
        val oldSize = list.size
        list.clear()
        notifyItemRangeRemoved(0, oldSize)
        list.addAll(data)
        notifyItemRangeInserted(0, list.size)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemLessonBinding>(itemView)
    }
}