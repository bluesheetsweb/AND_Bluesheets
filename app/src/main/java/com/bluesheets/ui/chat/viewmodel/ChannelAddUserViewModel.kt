package com.bluesheets.ui.chat.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bluesheets.BR
import com.bluesheets.R
import com.bluesheets.ui.chat.model.ConnectionUserModel
import com.bumptech.glide.Glide
import src.wrapperutil.uicomponent.roundedimageview.RoundedImageView
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.AdapterPVM

class ChannelAddUserViewModel(val member: ConnectionUserModel, val onSelectedUser: (ConnectionUserModel) -> Unit): AdapterPVM() {

    var name: String = ""
    var image: String = ""
    var imageThumb: Int = R.drawable.ic_profile_thumb
    private var isChecked: Boolean = false

    @Bindable
    fun getChecked(): Boolean {
        return isChecked
    }

    fun setChecked(checked: Boolean) {
        isChecked = checked
        notifyPropertyChanged(BR.checked)
    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun initData(){
        name = member.username
        image = member.profileimageurl
    }

    fun onItemClicked() {
        setChecked(!getChecked())
        onSelectedUser(member)
    }
}