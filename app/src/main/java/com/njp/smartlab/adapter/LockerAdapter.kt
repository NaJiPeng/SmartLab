package com.njp.smartlab.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.bean.Tool
import com.njp.smartlab.databinding.ItemLockerBinding

class LockerAdapter : RecyclerView.Adapter<LockerAdapter.ViewHolder>() {

    private val list = ArrayList<Tool>()
    private lateinit var listener: (Tool) -> Unit

    fun setListener(listener: (Tool) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.item_locker, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.binding?.tool = list[p1]
        p0.itemView.setOnClickListener {
            Log.i("wwww", "onclick$p1")
            listener.invoke(list[p1])
        }
    }

    fun setData(data: List<Tool>) {
        val oldSize = list.size
        list.clear()
        notifyItemRangeRemoved(0, oldSize)
        list.addAll(data)
        notifyItemRangeInserted(0, list.size)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemLockerBinding>(itemView)
    }
}