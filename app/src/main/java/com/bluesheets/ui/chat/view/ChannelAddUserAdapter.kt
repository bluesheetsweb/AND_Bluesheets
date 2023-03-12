package com.bluesheets.ui.chat.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bluesheets.BluesheetApplication
import com.bluesheets.databinding.AdapterChannelAddUsersBinding
import com.bluesheets.databinding.AdapterChannelUsersBinding
import com.bluesheets.ui.chat.model.ConnectionUserModel
import com.bluesheets.ui.chat.viewmodel.ChannelAddUserViewModel
import com.bluesheets.ui.chat.viewmodel.ChannelUserViewModel
import com.bumptech.glide.Glide
import io.getstream.chat.android.client.models.Member

class ChannelAddUserAdapter(val isCreation:Boolean = false, val onSelected: (ConnectionUserModel) -> Unit) : RecyclerView.Adapter<ChannelAddUserAdapter.ChannelItemViewHolder>() {
    private var listChannels: MutableList<ConnectionUserModel> = mutableListOf()
    private var listSelected: Set<String> = mutableSetOf()
    fun updateList(list: List<ConnectionUserModel>){
        listChannels = mutableListOf()
        listChannels.addAll(list)
        notifyDataSetChanged()
    }

    fun updateSelection(selected: Set<String>){
        listSelected = selected
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelItemViewHolder {
        val binding = AdapterChannelAddUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ChannelItemViewHolder(isCreation, binding)
    }

    override fun onBindViewHolder(holder: ChannelItemViewHolder, position: Int) {
        holder.updateItem(listChannels.get(position), listSelected.contains(listChannels.get(position).getStreamId()), onSelected)
    }

    override fun getItemCount(): Int {
        return listChannels.size
    }

    class ChannelItemViewHolder(val isCreation:Boolean, val binding: AdapterChannelAddUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun updateItem(member: ConnectionUserModel,isSelected: Boolean, onSelected: (ConnectionUserModel) -> Unit) {
            var viewModel = ChannelAddUserViewModel(member, onSelected)
            viewModel.initData()
            binding.viewModel = viewModel
            if (isCreation) {
                binding.checkBox.visibility = View.GONE
            }else {
                binding.checkBox.visibility = View.VISIBLE
            }
            viewModel.setChecked(isSelected)
            Glide.with(BluesheetApplication.instance).load(viewModel.image).centerCrop()
                .placeholder(viewModel.imageThumb)
                .into(binding.imageProfile)
        }
    }

}