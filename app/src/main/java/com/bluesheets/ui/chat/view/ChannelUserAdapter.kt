package com.bluesheets.ui.chat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bluesheets.databinding.AdapterChannelUsersBinding
import com.bluesheets.ui.chat.viewmodel.ChannelUserViewModel
import io.getstream.chat.android.client.models.Member

class ChannelUserAdapter(val adminId: String, val isGroup: Boolean, val onRemoveUser: (Member) -> Unit) : RecyclerView.Adapter<ChannelUserAdapter.ChannelItemViewHolder>() {
    private var listChannels: MutableList<Member> = mutableListOf()
    fun updateList(list: List<Member>){
        listChannels = mutableListOf()
        listChannels.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelItemViewHolder {
        val binding = AdapterChannelUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ChannelItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelItemViewHolder, position: Int) {
        holder.updateItem(adminId, isGroup, listChannels.get(position), onRemoveUser)
    }

    override fun getItemCount(): Int {
        return listChannels.size
    }

    class ChannelItemViewHolder(val binding: AdapterChannelUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun updateItem(adminId: String, isGroup:Boolean, member: Member, onRemoveUser: (Member) -> Unit) {
            var viewModel = ChannelUserViewModel(adminId, isGroup, member, onRemoveUser)
            viewModel.initData()
            binding.viewModel = viewModel
            viewModel.loadImage(binding.imageProfile, viewModel.image)
        }
    }

}