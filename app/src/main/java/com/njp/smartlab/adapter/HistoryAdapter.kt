package com.njp.smartlab.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.bean.Manipulation
import com.njp.smartlab.databinding.ItemHistoryBinding

class HistoryAdapter(private val list: ArrayList<Manipulation>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.item_history, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.binding?.manipulation = list[p1]
    }

    fun setData(data: List<Manipulation>) {
        val oldSize = list.size
        list.clear()
        notifyItemRangeRemoved(0, oldSize)
        list.addAll(data)
        notifyItemRangeInserted(0, list.size)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemHistoryBinding>(itemView)
    }
}