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

class SignUpViewModel: ParentVM() {

    private var name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var mobile: String = ""
    private var rePassword: String = ""
    var passwordDontMatch = MutableLiveData<Boolean>()
    var buttonEnabled = MutableLiveData<Boolean>()

    init {
        repository = SignInRepo()
    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun onNameTextChanged(s: CharSequence) {
        name = s.toString()
        checkSignInButtonEnablity()
    }

    fun onEmailTextChanged(s: CharSequence) {
        email = s.toString()
        checkSignInButtonEnablity()
    }

    fun onPhoneTextChanged(s: CharSequence) {
        mobile = s.toString()
        checkSignInButtonEnablity()
    }

    fun onPasswordTextChanged(s: CharSequence) {
        password = s.toString()
        checkSignInButtonEnablity()
    }

    fun onRePasswordTextChanged(s: CharSequence) {
        rePassword = s.toString()
        checkSignInButtonEnablity()
    }

    private fun checkSignInButtonEnablity() {
            if (AppUtils.isValidEmail(email) && AppUtils.isValidPasswordLength(password)
                && AppUtils.isValidPasswordLength(rePassword) && AppUtils.isValidNameLength(name)
                && AppUtils.isValidMobileLength(mobile)) {
                if  (password == rePassword) {
                    passwordDontMatch.value = false
                    buttonEnabled.value = true
                } else {
                    buttonEnabled.value = false
                    passwordDontMatch.value = true
                }
            } else {
                buttonEnabled.value = false
            }
    }


    fun signInClicked() {
        NavigateTo.screen(activityType = FragmentConstant.SIGN_UP_ACTIVITY,
            fragmentType = FragmentConstant.SIGN_IN_FRAGMENT)

    }

    fun signUpClicked() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        (repository as SignInRepo).register(name, email, mobile, password, object : NetworkRequest.IOnResponse{
            override fun onException(t: Throwable?) {
                errorToastState.msg = "Login Failed With Exception"
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }

            override fun onSuccess(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                Toaster.show(BluesheetApplication.instance.applicationContext, "Registration Success")
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
            }

            override fun onFailed(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                if (data!=null && data is NetworkErrorBModel) {
                    data.message?.let {
                        errorToastState.msg = it
                    }
                } else {
                    errorToastState.msg = "Registration Failed"
                }
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }
        })
    }
}