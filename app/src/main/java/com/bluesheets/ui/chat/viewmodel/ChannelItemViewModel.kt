package com.bluesheets.ui.chat.viewmodel

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

class ChannelItemViewModel(val channel: Channel): AdapterPVM() {

    var name: String = ""
    var subDetail: String = ""
    var image: String = ""
    var imageThumb: Int = R.drawable.ic_profile_thumb
    var isToShowCount: Boolean = false
    var count: String = ""

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun initData(){
        name = SharedUtils.getChannelName(channel)
        if (name.isEmpty()) {
            name = "No Name"
        }
        subDetail = SharedUtils.getSubMessage(channel)
        image = SharedUtils.getChannelImageUrl(channel)
        imageThumb = SharedUtils.getChannelThumb(channel)
        isToShowCount = channel.hasUnread
        count = channel.unreadCount ?.let {
            if (it > 9) {
                "9+"
            } else {
                "$it"
            }
        }.toString()
    }

    fun onItemClicked() {
        val bundle = Bundle()
        bundle.putString("cId", channel.cid)
        NavigateTo.screen(FragmentConstant.CHAT_ACTIVITY, FragmentConstant.CHAT_MESSAGE_FRAGMENT, bundle)
    }
}