package com.bluesheets.ui.documents.viewmodel

import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bluesheets.R
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import com.bluesheets.utils.SharedUtils
import com.bumptech.glide.Glide
import io.getstream.chat.android.client.models.Channel
import src.wrapperutil.uicomponent.roundedimageview.RoundedImageView
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.AdapterPVM

class DocumentItemViewModel(val channel: Channel): AdapterPVM() {

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
        bundle.putString("cId", channel.cid)
        NavigateTo.screen(FragmentConstant.CHAT_ACTIVITY, FragmentConstant.CHAT_MESSAGE_FRAGMENT, bundle)
    }
}