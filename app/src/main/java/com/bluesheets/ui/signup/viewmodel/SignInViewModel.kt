package com.bluesheets.ui.signup.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.BluesheetApplication
import com.bluesheets.ui.signup.repository.SignInRepo
import com.bluesheets.utils.AppUtils
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import src.networkutil.model.NetworkErrorBModel
import src.networkutil.network.NetworkRequest
import src.wrapperutil.utilities.Toaster
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class SignInViewModel: ParentVM() {

    private var email: String = ""
    private var password: String = ""
    var buttonEnabled = MutableLiveData<Boolean>()

    init {
        repository = SignInRepo()
    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun onEmailTextChanged(s: CharSequence) {
        email = s.toString()
        checkSignInButtonEnablity()
    }

    fun onPasswordTextChanged(s: CharSequence) {
        password = s.toString()
        checkSignInButtonEnablity()
    }

    private fun checkSignInButtonEnablity(){
        buttonEnabled.value =  AppUtils.isValidEmail(email) && AppUtils.isValidPasswordLength(password)
    }

    fun signInClicked() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        (repository as SignInRepo).login(email,password,object : NetworkRequest.IOnResponse{
            override fun onException(t: Throwable?) {
                errorToastState.msg = "Login Failed With Exception"
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }

            override fun onSuccess(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                    Toaster.show(BluesheetApplication.instance.applicationContext, "Login Success")
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
            }

            override fun onFailed(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                if (data!=null && data is NetworkErrorBModel) {
                    data.message?.let {
                        errorToastState.msg = it
                    }
                } else {
                    errorToastState.msg = "Login Failed"
                }

                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }
        })
    }

    fun signUpClicked() {
        NavigateTo.screen(activityType = FragmentConstant.SIGN_UP_ACTIVITY,
            fragmentType = FragmentConstant.SIGN_UP_FRAGMENT)
    }

    fun forgotClicked() {

    }
}