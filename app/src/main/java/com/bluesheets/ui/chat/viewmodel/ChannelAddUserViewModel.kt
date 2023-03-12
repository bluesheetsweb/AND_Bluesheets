package com.bluesheets.ui.chat.viewmodel

import android.os.Bundle
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bluesheets.BR
import com.bluesheets.R
import com.bluesheets.ui.chat.model.ConnectionUserModel
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import com.bluesheets.utils.SharedUtils
import com.bluesheets.utils.UserInfoUtil
import com.bumptech.glide.Glide
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Member
import src.wrapperutil.uicomponent.roundedimageview.RoundedImageView
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.AdapterPVM

class ChannelAddUserViewModel(val member: ConnectionUserModel, val onSelectedUser: (ConnectionUserModel) -> Unit): AdapterPVM() {

    var name: String = ""
    var image: String = ""
    var imageThumb: Int = R.drawable.ic_profile_thumb
//    @Bindable
    var isChecked: Boolean = false

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun initData(){
        name = member.username
        image = member.profileimageurl
//        isChecked = isSelected
//        notifyPropertyChanged(BR.isChecked)
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: RoundedImageView, url: String?) {
            Glide.with(view.context).load(url).centerCrop()
                .placeholder(imageThumb)
                .into(view)
    }

    fun onItemClicked() {
        isChecked = !isChecked
        onSelectedUser(member)
//        notifyPropertyChanged(BR.isChecked)
    }
}