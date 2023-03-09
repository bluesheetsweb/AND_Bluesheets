package com.bluesheets.ui.chat.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bluesheets.R
import com.bluesheets.databinding.AdapterChannelItemBinding
import com.bluesheets.ui.chat.viewmodel.ChannelItemViewModel
import io.getstream.chat.android.client.models.Channel

class ChannelItemAdapter : RecyclerView.Adapter<ChannelItemAdapter.ChannelItemViewHolder>() {
    private var listChannels: MutableList<Channel> = mutableListOf()
    fun updateList(list: List<Channel>){
        listChannels = mutableListOf()
        listChannels.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelItemViewHolder {
        val binding = AdapterChannelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ChannelItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelItemViewHolder, position: Int) {
        holder.updateItem(listChannels.get(position))
    }

    override fun getItemCount(): Int {
        return listChannels.size
    }

    class ChannelItemViewHolder(val binding: AdapterChannelItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun updateItem(channel: Channel) {
            var viewModel = ChannelItemViewModel(channel)
            viewModel.initData()
            binding.viewModel = viewModel
            viewModel.loadImage(binding.imageProfile, viewModel.image)
        }
    }

}