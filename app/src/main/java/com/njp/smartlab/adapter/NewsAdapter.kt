package com.njp.smartlab.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.bean.News
import com.njp.smartlab.databinding.ItemNewsBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val list = ArrayList<News>()

    private lateinit var listener: ((News) -> Unit)

    fun setListener(listener: ((News) -> (Unit))) {
        this.listener = listener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.item_news, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.binding?.news = list[p1]
        p0.itemView.setOnClickListener { listener.invoke(list[p1]) }
    }

    fun setData(data: List<News>) {
        val oldSize = list.size
        list.clear()
        notifyItemRangeRemoved(0, oldSize)
        list.addAll(data)
        notifyItemRangeInserted(0, list.size)
    }

    fun addData(data: List<News>) {
        val oldSize = list.size
        list.addAll(data)
        notifyItemRangeInserted(oldSize, data.size)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemNewsBinding>(itemView)
    }
}