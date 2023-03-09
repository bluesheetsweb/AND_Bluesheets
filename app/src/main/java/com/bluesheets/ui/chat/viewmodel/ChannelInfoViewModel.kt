package com.bluesheets.ui.chat.viewmodel

import androidx.lifecycle.MutableLiveData
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class ChannelInfoViewModel: ParentVM() {


    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }
}