package com.bluesheets.ui.documents.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bluesheets.BluesheetApplication
import com.bluesheets.databinding.AdapterDocumentItemBinding
import com.bluesheets.ui.documents.model.DocumentListData
import com.bluesheets.ui.documents.viewmodel.DocumentItemViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DocumentListAdapter(
    val isCreation: Boolean = false,
    val onSelected: (DocumentListData) -> Unit
) : RecyclerView.Adapter<DocumentListAdapter.DocumentItemViewHolder>() {
    private var listChannels: MutableList<DocumentListData> = mutableListOf()
    private var listSelected: Set<String> = mutableSetOf()
    fun updateList(list: List<DocumentListData>) {
        listChannels = mutableListOf()
        listChannels.addAll(list)
        notifyDataSetChanged()
    }

    fun updateSelection(selected: Set<String>) {
        listSelected = selected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentItemViewHolder {
        val binding =
            AdapterDocumentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DocumentItemViewHolder(isCreation, binding)
    }

    override fun onBindViewHolder(holder: DocumentItemViewHolder, position: Int) {
        holder.updateItem(listChannels.get(position), onSelected)
    }

    override fun getItemCount(): Int {
        return listChannels.size
    }

    class DocumentItemViewHolder(val isCreation: Boolean, val binding: AdapterDocumentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun updateItem(member: DocumentListData, onSelected: (DocumentListData) -> Unit) {
            var viewModel = DocumentItemViewModel(member, onSelected)
            viewModel.initData()
            binding.viewModel = viewModel
            Glide.with(BluesheetApplication.instance)
                .load(viewModel.image).centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .placeholder(viewModel.imageThumb)
                .into(binding.imgDocument)
            binding.txtDocumentName
            binding.txtActionRequired
//            binding.txtStatusProcessing
//            binding.txtStatusProcessing.visibility = View.VISIBLE
            binding.txtActionRequired.visibility = View.VISIBLE
        }
    }

}