package com.bluesheets.ui.chat.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bluesheets.BluesheetApplication
import com.bluesheets.R
import com.bluesheets.databinding.AdapterChannelItemBinding
import com.bluesheets.databinding.AdapterTagOrgBinding
import com.bluesheets.ui.chat.model.OrgTagModel
import com.bluesheets.ui.chat.model.TagsModel
import com.bluesheets.ui.chat.viewmodel.ChannelItemViewModel
import com.bluesheets.ui.chat.viewmodel.OrgTagViewModel
import com.bumptech.glide.Glide
import io.getstream.chat.android.client.models.Channel

class OrgTagAdapter(var isTypeOrg: Boolean, val onItemClicked: (Any)-> Unit) : RecyclerView.Adapter<OrgTagAdapter.ChannelItemViewHolder>() {

    private var listData: MutableList<Any> = mutableListOf()

    fun updateList(list: List<Any>){
        listData = mutableListOf()
        listData.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelItemViewHolder {
        val binding = AdapterTagOrgBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ChannelItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelItemViewHolder, position: Int) {
        holder.updateItem(isTypeOrg, listData.get(position), onItemClicked)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ChannelItemViewHolder(val binding: AdapterTagOrgBinding): RecyclerView.ViewHolder(binding.root) {
        fun updateItem(isOrg: Boolean, data: Any, onSelected: (Any)-> Unit) {
            var viewModel = OrgTagViewModel(isOrg, data, onSelected)
            viewModel.initData()
            binding.viewModel = viewModel
        }
    }

}