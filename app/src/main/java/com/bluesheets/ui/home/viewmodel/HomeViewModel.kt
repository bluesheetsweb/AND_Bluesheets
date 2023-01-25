package com.bluesheets.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.utils.UserInfoUtil
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class HomeViewModel: ParentVM() {

    var currentSelectedPos = MutableLiveData<Int>()
    var organizationName: String? = UserInfoUtil.organizationName
    var workSpaceName: String? = UserInfoUtil.workSpaceName

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun chatClicked(){
        if (currentSelectedPos.value != 0) {
            currentSelectedPos.value = 0
        }
    }

    fun expenseClicked(){
        if (currentSelectedPos.value != 1) {
            currentSelectedPos.value = 1
        }
    }

    fun documentClicked(){
        if (currentSelectedPos.value != 2) {
            currentSelectedPos.value = 2
        }
    }

    fun contactClicked(){
        if (currentSelectedPos.value != 3) {
            currentSelectedPos.value = 3
        }
    }
}