package com.bluesheets.ui.signup.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bluesheets.ui.organisation.repository.CreateOrgRepo
import com.bluesheets.ui.signup.repository.VerifyOtpRepo
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class OtpViewModel : ParentVM() {

    var title: String? = ""
    var heading: String? = ""
    var otpMessage: String? = ""
    var nextButtonText: String? = ""
    var goBackText: String? = ""
    val navigateBackLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val navigateLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun onNextClick() {
        navigateLiveData.value = true
    }

    fun goBack() {
        navigateBackLiveData.value = true
    }


    init {
        repository = VerifyOtpRepo()
    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun verifyOTP() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)

    }



}
