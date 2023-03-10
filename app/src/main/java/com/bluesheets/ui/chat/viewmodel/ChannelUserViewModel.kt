package com.bluesheets.ui.chat.viewmodel

import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bluesheets.R
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

class ChannelUserViewModel(val adminId: String,val isGroup:Boolean, val member: Member, val onRemoveUser: (Member) -> Unit): AdapterPVM() {

    var name: String = ""
    var image: String = ""
    var imageThumb: Int = R.drawable.ic_profile_thumb
    var canDelete: Boolean = false
    var isAdmin: Boolean = false

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun initData(){
        name = SharedUtils.getUserName(member.user)
        if (name.isEmpty()) {
            name = "No Name"
        }
        isAdmin = member.getUserId() == adminId
        if (isGroup && member.getUserId() != UserInfoUtil.getChatId()) {
            canDelete = adminId == UserInfoUtil.getChatId()
        } else {
            canDelete = false
        }
//        image = SharedUtils.getChannelImageUrl(channel).toString()
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: RoundedImageView, url: String?) {
            Glide.with(view.context).load(url).centerCrop()
                .placeholder(imageThumb)
                .into(view)
    }

    fun onItemClicked() {

    }

    fun onDelete() {
        onRemoveUser(member)
    }
}