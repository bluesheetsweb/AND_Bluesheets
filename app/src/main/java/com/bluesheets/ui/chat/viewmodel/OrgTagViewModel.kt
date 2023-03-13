package com.bluesheets.ui.chat.viewmodel

import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bluesheets.R
import com.bluesheets.ui.chat.model.OrgTagModel
import com.bluesheets.ui.chat.model.TagsModel
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import com.bluesheets.utils.SharedUtils
import com.bumptech.glide.Glide
import io.getstream.chat.android.client.models.Channel
import src.wrapperutil.uicomponent.roundedimageview.RoundedImageView
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.AdapterPVM

class OrgTagViewModel(var isOrg: Boolean, var data: Any, val onSelected: (Any)-> Unit): AdapterPVM() {

    var name: String = ""

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun initData(){
        if (isOrg) {
            (data as OrgTagModel).name ?.let {
            name = it
        }
        } else {
            (data as TagsModel).name ?.let {
                name = it
            }
        }

    }

    fun onItemClicked() {
        onSelected(data)
    }
}