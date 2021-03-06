package com.njp.smartlab.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njp.smartlab.R
import com.njp.smartlab.bean.File
import com.njp.smartlab.databinding.ItemFileBinding
import com.njp.smartlab.utils.ToastUtil

class FilesAdapter : RecyclerView.Adapter<FilesAdapter.ViewHolder>() {

    private val list = ArrayList<File>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.item_file, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.binding?.file = list[p1]
        p0.itemView.setOnClickListener {
            ToastUtil.getInstance().show("你暂时没有权限下载此文件，请联系管理员授权")
        }
    }

    fun setData(data: List<File>) {
        val oldSize = list.size
        list.clear()
        notifyItemRangeRemoved(0, oldSize)
        list.addAll(data)
        notifyItemRangeInserted(0, list.size)
    }

    fun addData(data: List<File>) {
        val oldSize = list.size
        list.addAll(data)
        notifyItemRangeInserted(oldSize, data.size)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemFileBinding>(itemView)
    }
}