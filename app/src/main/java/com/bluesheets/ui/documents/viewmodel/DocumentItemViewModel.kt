package com.bluesheets.ui.documents.viewmodel

import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bluesheets.R
import com.bluesheets.ui.chat.model.ConnectionUserModel
import com.bluesheets.ui.documents.model.DocumentListData
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import com.bluesheets.utils.SharedUtils
import com.bumptech.glide.Glide
import io.getstream.chat.android.client.models.Channel
import src.wrapperutil.uicomponent.roundedimageview.RoundedImageView
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.AdapterPVM

class DocumentItemViewModel(val document: DocumentListData, val onSelectedUser: (DocumentListData) -> Unit): AdapterPVM() {

    var documentName: String = ""
    var docStatusProcessing: String = ""
    var docStatusActionRequired: String = ""
    var image: String = ""
    var imageThumb: Int = R.drawable.ic_doc_placeholder
    var isToShowCount: Boolean = false
    var count: String = ""

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun onItemClicked() {
        val bundle = Bundle()
        bundle.putInt("docId", document.id)
    }

    fun initData(){
        documentName = document.filename
        image = document.exportPreviewUrl
        docStatusActionRequired = document.statusExport
    }
}