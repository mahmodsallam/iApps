package com.task.iApps.ui.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flow_mvvm_sample.databinding.ItemsListItemBinding
import com.task.iApps.model.ResponseModel

class ItemsAdapter(
    private val onClickListener: (ResponseModel.Item) -> Unit,
    private var list: List<ResponseModel.Item?> = listOf()
) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemsListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.also {
            it.setOnClick { onClickListener(item!!) }
            it.item = item
            it.executePendingBindings()
        }
    }

    fun setList(list: List<ResponseModel.Item?>) {
        if (list.isNotEmpty()) { this.list = list }
    }

    class ViewHolder(
        val binding: ItemsListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}