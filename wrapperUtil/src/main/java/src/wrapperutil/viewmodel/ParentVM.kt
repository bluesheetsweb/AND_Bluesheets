package src.wrapperutil.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import src.wrapperutil.R
import src.wrapperutil.model.UIPlaceHolderItem
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.utilities.WrapperGlobalDataHolder

abstract class ParentVM : ViewModel() {

    var loadingState: UIPlaceHolderItem
    var errorState: UIPlaceHolderItem
    var successState: UIPlaceHolderItem
    var noDataState: UIPlaceHolderItem
    var noneState: UIPlaceHolderItem
    var noInternet: UIPlaceHolderItem
    var errorToastState: UIPlaceHolderItem
    var noInternetToast: UIPlaceHolderItem
    var loadingStateBottom: UIPlaceHolderItem
    var loadingStateTop: UIPlaceHolderItem

    var mSwipeRefresing = MutableLiveData<Boolean>()
    var mSwipeEnable = MutableLiveData<Boolean>()

    init {
        loadingStateTop = UIPlaceHolderItem().setData(
            "",
            "",
            "",
            "",
            0,
            false,
            false,
            false,
            progrssBarTopVisible = true
        )
        loadingStateBottom = UIPlaceHolderItem().setData("", "", "", "", 0, false, false, true)
        loadingState = UIPlaceHolderItem().setData("", "", "", "", 0, true, false)
        noInternetToast = UIPlaceHolderItem().setData(
            "",
            WrapperGlobalDataHolder.globalMessageForNoInternet,
            "",
            "",
            0,
            false,
            true
        )
        errorToastState = UIPlaceHolderItem().setData(
            "", WrapperGlobalDataHolder.globalMessageForError,
            WrapperGlobalDataHolder.globalOkButton, "", 0, false, true
        )
        errorState = UIPlaceHolderItem().setData(
            WrapperGlobalDataHolder.globalTitleForError,
            WrapperGlobalDataHolder.globalMessageForError,
            WrapperGlobalDataHolder.globalRetryButton,
            "",
            0,
            false,
            false
        )
        noInternet = UIPlaceHolderItem().setData(
            WrapperGlobalDataHolder.globalTitleForNoInternet,
            WrapperGlobalDataHolder.globalMessageForNoInternet,
            WrapperGlobalDataHolder.globalRetryButton,
            "",
            R.drawable.ic_no_internet,
            false,
            false
        )
        successState = UIPlaceHolderItem().setData("", "", "", "", 0, false, false)
        noDataState = UIPlaceHolderItem().setData(
            WrapperGlobalDataHolder.globalTitleForNotFound,
            WrapperGlobalDataHolder.globalMessageForNotFound,
            "",
            "",
            0,
            false,
            false
        )
        noneState = UIPlaceHolderItem().setData("", "", "", "", 0, false, false)
    }

    protected var mProgressState =
        MutableLiveData<src.wrapperutil.utilities.WrapperEnumAnnotation>()
    var repository: Any? = null
    var callBackListener: Any? = null
    var stateButtonClickCallbackListener: Any? = null
    var TAG = ParentVM::class.java.simpleName

    abstract fun getState(): MutableLiveData<src.wrapperutil.utilities.WrapperEnumAnnotation>

    open fun changeState(mProgressState: MutableLiveData<WrapperEnumAnnotation>?) {
        if (mProgressState != null)
            this.mProgressState = mProgressState!!
    }

    open fun onBackPressed() {
    }

    open fun firstButtonClick() {
    }

    open fun secondButtonClick() {
    }

    open fun swipeToRefrsh() {
    }

    open fun openToolbarProfile() {
    }
}
