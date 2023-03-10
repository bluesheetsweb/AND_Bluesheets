package com.bluesheets.ui.chat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bluesheets.databinding.AdapterChannelAddUsersBinding
import com.bluesheets.databinding.AdapterChannelUsersBinding
import com.bluesheets.ui.chat.model.ConnectionUserModel
import com.bluesheets.ui.chat.viewmodel.ChannelAddUserViewModel
import com.bluesheets.ui.chat.viewmodel.ChannelUserViewModel
import io.getstream.chat.android.client.models.Member

class ChannelAddUserAdapter(val onSelected: (ConnectionUserModel) -> Unit) : RecyclerView.Adapter<ChannelAddUserAdapter.ChannelItemViewHolder>() {
    private var listChannels: MutableList<ConnectionUserModel> = mutableListOf()
    fun updateList(list: List<ConnectionUserModel>){
        listChannels = mutableListOf()
        listChannels.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelItemViewHolder {
        val binding = AdapterChannelAddUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ChannelItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelItemViewHolder, position: Int) {
        holder.updateItem(listChannels.get(position), onSelected)
    }

    override fun getItemCount(): Int {
        return listChannels.size
    }

    class ChannelItemViewHolder(val binding: AdapterChannelAddUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun updateItem(member: ConnectionUserModel, onSelected: (ConnectionUserModel) -> Unit) {
            var viewModel = ChannelAddUserViewModel(member, onSelected)
            viewModel.initData()
            binding.viewModel = viewModel
            viewModel.loadImage(binding.imageProfile, viewModel.image)
        }
    }

}