package src.wrapperutil.empty_state

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import src.wrapperutil.R
import src.wrapperutil.listener.OnYesNoClickListener
import src.wrapperutil.model.UIPlaceHolderItem
import src.wrapperutil.uicomponent.*
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.utilities.WrapperGlobalDataHolder
import src.wrapperutil.viewmodel.ParentVM

class StateManagerConstraintLayout : ConstraintLayout {

    private var inflater: LayoutInflater? = null
    private var view: View? = null
    private lateinit var mCancelButton: Button
    private lateinit var mTextTitle: TextView
    private lateinit var mImageIcon: ImageView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var progressBarTop: ProgressBar
    private lateinit var mProgressBarBottom: ProgressBar
    private lateinit var mLayoutError: src.wrapperutil.uicomponent.LinearLayout
    private lateinit var mTextDescription: TextView
    private lateinit var mOkButton: Button
    private var currentState: Int? = -1
    private lateinit var mParentEmptyLayout: RelativeLayout
    // private lateinit var mLayoutError: DCRelativeLayout
    // private lateinit var mSwipeRefeshLayout: DCSwipeRefreshLayout
    private var isInflate = false
    private var isToHandleClickFromOutside: Boolean? = false
    private var viewScreenMode: Int? = WrapperConstant.STATE_FULL_SCREEN

    var viewModel: ParentVM? = null

    companion object {
        val TAG = StateManagerConstraintLayout::class.java.simpleName
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        try {
            val array = context.obtainStyledAttributes(attrs, R.styleable.AttrStateView, 0, 0)
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            viewScreenMode = array.getInt(R.styleable.AttrStateView_state_mode, WrapperConstant.STATE_FULL_SCREEN)
        } catch (ex: Exception) {
            Log.e(TAG, "ex", ex)
        }

//        inflateView(view)
    }

    fun inflateView() {
        try {
            isInflate = true
            view = inflater!!.inflate(R.layout.empty_state_constraintlayout, null)
            mCancelButton = view!!.findViewById<Button>(R.id.cancelButton)
            mTextTitle = view!!.findViewById<TextView>(R.id.textTitle)
            mImageIcon = view!!.findViewById<ImageView>(R.id.imageIcon)
            mProgressBar = view!!.findViewById<ProgressBar>(R.id.progressBar)
            progressBarTop = view!!.findViewById<ProgressBar>(R.id.progressBarTop)
            mProgressBarBottom = view!!.findViewById<ProgressBar>(R.id.progressBarBottom)
            mLayoutError = view!!.findViewById<src.wrapperutil.uicomponent.LinearLayout>(R.id.layoutError)
            mTextDescription = view!!.findViewById<TextView>(R.id.textDescription)
            mOkButton = view!!.findViewById<Button>(R.id.okButton)
            mParentEmptyLayout = view!!.findViewById<RelativeLayout>(R.id.parentEmptyLayout)
            //   mLayoutError = view!!.findViewById<DCRelativeLayout>(R.id.mLayoutError)
            // mSwipeRefeshLayout = view!!.findViewById<DCSwipeRefreshLayout>(R.id.dcSwipeRefreshLayout)

            val layoutParams = when (viewScreenMode) {
                WrapperConstant.STATE_FULL_SCREEN -> ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                WrapperConstant.STATE_WRAP_CONTENT -> ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                else -> ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            addView(view, layoutParams)

//            GlobalDataHolder.backPressListener = object : OnBackPressHandleListener {
//                override fun onBackPressed() {
//                    removeTheView()
//                }
//            }
        } catch (ex: Exception) {
            Log.e(TAG, "ex", ex)
        }
    }

    fun registerViewModel(viewModel: ParentVM) {
        this.viewModel = viewModel
    }

    public fun configureData(model: UIPlaceHolderItem) {

        try {

            // Log.e(TAG, "progressBarMainVisible" + model.progressBarMainVisible)
            // Log.e(TAG, "progressBarBottom" + model.progressBarBottom)

            if (model.title.isNullOrBlank() && model.msg.isNullOrBlank() &&
                model.okButtonTitle.isNullOrBlank() &&
                model.cancelbuttonTitle.isNullOrBlank() &&
                (model.image == null || model.image == 0) &&
                !model.progressBarMainVisible!! && !model.progressBarBottom!! && !model.progrssBarTopVisible
            ) {
                Log.e(TAG, "remove views")
                isInflate = false
                removeTheView()
            } else {

                if (model.title.isNullOrBlank()) {
                    mTextTitle.visibility = View.GONE
                } else {
                    mTextTitle.visibility = View.VISIBLE
                    mTextTitle.text = model.title
                }

                if (model.msg.isNullOrBlank()) {
                    mTextDescription.visibility = View.GONE
                } else {
                    mTextDescription.visibility = View.VISIBLE
                    mTextDescription.text = model.msg
                }

                if (model.okButtonTitle.isNullOrBlank()) {
                    mOkButton.visibility = View.GONE
                } else {
                    mOkButton.visibility = View.VISIBLE
                    mOkButton.text = model.okButtonTitle

                    mOkButton.setOnClickListener {

                        if (currentState == WrapperConstant.STATE_NO_INTERNET && !isConnectingToInternet(context)) {
                            // do nothing
                        } else {
                            if (!isToHandleClickFromOutside!!)
                                removeTheView()
                            viewModel?.firstButtonClick()
                        }
                    }
                }

                if (model.cancelbuttonTitle.isNullOrBlank()) {
                    mCancelButton.visibility = View.GONE
                    mOkButton.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                } else {
                    mOkButton.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                    mCancelButton.visibility = View.VISIBLE
                    mCancelButton.text = model.cancelbuttonTitle

                    mCancelButton.setOnClickListener {
                        if (!isToHandleClickFromOutside!!)
                            removeTheView()
                        viewModel?.secondButtonClick()
                    }
                }

                if (model.image != null && model.image != 0) {
                    mImageIcon.visibility = View.VISIBLE
                    mImageIcon.setImageResource(model.image!!)
                } else {
                    mImageIcon.visibility = View.GONE
                }

                if (model.progressBarMainVisible!!) {
                    mProgressBar.visibility = View.VISIBLE
                } else {
                    mProgressBar.visibility = View.GONE
                }
                if (model.progressBarBottom!!) {
                    // Log.e(TAG, "progressBarBottom if")
                    mProgressBarBottom.visibility = View.VISIBLE
                } else {
                    // Log.e(TAG, "progressBarBottom else")
                    mProgressBarBottom.visibility = View.GONE
                }

                if (model.progrssBarTopVisible) {
                    progressBarTop.visibility = View.VISIBLE
                } else {
                    progressBarTop.visibility = View.GONE
                }

                //   Log.e(TAG, "if isToast" + model.isToast)
                // Log.e(TAG, "context" + context)
                // Log.e(TAG, "toast msg" + model.msg)
                if (model.isToast!! && context != null && !model.msg.isNullOrBlank()) {
                    Log.e(TAG, "if isToast")

                    val toast = Toast.makeText(context, model.msg, Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    removeTheView()
                    mLayoutError.visibility = View.GONE
                } else {
                    mLayoutError.visibility = View.VISIBLE
                    Log.e(TAG, "else isToast")
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "ex", ex)
        }
    }

    fun removeTheView() {
        try {
            WrapperGlobalDataHolder.isErrorScreenShown = false
//            GlobalDataHolder.backPressListener = null
            isInflate = false
            //  mLayoutError.removeAllViews()
            removeView(view)
        } catch (ex: Exception) {
            Log.e(TAG, "ex", ex)
        }
    }

/*need to use this*/

    fun setViewState(state: Int?, parentVM: ParentVM, handleClickFromOutside: Boolean? = false, isClickable: Boolean = true, isToHideKeyboard: Boolean = true) {
        var isLocalClickable = isClickable
        Handler(Looper.getMainLooper()).post {
            isToHandleClickFromOutside = handleClickFromOutside
            currentState = state
            try {
                if (!isInflate)
                    inflateView()

                mLayoutError.setBackground(WrapperEnumAnnotation(WrapperConstant.BACKGROUND_WHITE))
                WrapperGlobalDataHolder.isErrorScreenShown = true
                // Log.e(TAG,"isInflate"+isInflate)
                // Log.e(TAG, "setViewSate" + state)
                @WrapperEnumAnnotation.StateMode val status = state
                when (status) {
                    WrapperConstant.STATE_SCREEN_ERROR -> {
                        hideKeyboard()
                        configureData(parentVM.errorState)
                    }
                    WrapperConstant.STATE_SCREEN_SUCCESS -> {
//                hideKeyboard()
                        WrapperGlobalDataHolder.isErrorScreenShown = false
                        configureData(parentVM.successState)
                    }
                    WrapperConstant.STATE_SCREEN_NOTHING -> {
                        WrapperGlobalDataHolder.isErrorScreenShown = false
                        configureData(parentVM.successState)
                    }
                    WrapperConstant.STATE_SCREEN_LOADING -> {
                        WrapperGlobalDataHolder.isErrorScreenShown = false
                        mLayoutError.setBackground(WrapperEnumAnnotation(WrapperConstant.BACKGROUND_TRANSPARENT))
                        configureData(parentVM.loadingState)
                    }
                    WrapperConstant.STATE_SCREEN_NODATA -> {
                        WrapperGlobalDataHolder.isErrorScreenShown = false
                        if (isToHideKeyboard)
                            hideKeyboard()
                        configureData(parentVM.noDataState)
                    }
                    WrapperConstant.STATE_SCREEN_SEARCH_NODATA -> {
                        configureData(parentVM.noDataState)
                    }
                    WrapperConstant.STATE_SCREEN_NONE -> {
                        configureData(parentVM.noneState)
                    }
                    WrapperConstant.STATE_SCREEN_LOADING_BOTTOM -> {
                        WrapperGlobalDataHolder.isErrorScreenShown = false
                        isLocalClickable = false
                        mLayoutError.setBackground(WrapperEnumAnnotation(WrapperConstant.BACKGROUND_TRANSPARENT))
                        configureData(parentVM.loadingStateBottom)
                    } WrapperConstant.STATE_SCREEN_LOADING_TOP -> {
                        WrapperGlobalDataHolder.isErrorScreenShown = false
                        isLocalClickable = false
                        mLayoutError.setBackground(WrapperEnumAnnotation(WrapperConstant.BACKGROUND_TRANSPARENT))
                        configureData(parentVM.loadingStateTop)
                    }
                    WrapperConstant.STATE_SCREEN_ERROR_TOAST -> {
                        WrapperGlobalDataHolder.isErrorScreenShown = false
                        showOkDialog(
                            message = parentVM.errorToastState.msg,
                            okButtonText = parentVM.errorToastState.okButtonTitle,
                            context = context,
                            listener = object : OnYesNoClickListener {
                                override fun onYesClick(data: Any?) {
                                }

                                override fun onNoClick(data: Any?) {
                                }
                            }
                        )
                        configureData(parentVM.successState)
//                        configureData(dcParentVM.errorToastState)
                    }
                    WrapperConstant.STATE_SCREEN_NO_INTERNET_TOAST -> {
                        WrapperGlobalDataHolder.isErrorScreenShown = false
                        configureData(parentVM.noInternetToast)
                    }
                    WrapperConstant.STATE_NO_INTERNET -> {
                        WrapperGlobalDataHolder.isErrorScreenShown = false
                        hideKeyboard()
                        configureData(parentVM.noInternet)
                    }

                    WrapperConstant.STATE_SEARCH_NO_INTERNET -> {
                        WrapperGlobalDataHolder.isErrorScreenShown = false
                        configureData(parentVM.noInternet)
                    }
                    else -> {
                        hideKeyboard()
                        mProgressBar?.setVisibility(View.GONE)
                    }
                }

                try {
                    mParentEmptyLayout.isClickable = isLocalClickable
                } catch (ex: Exception) {
                    Log.w(TAG, "setViewState ex", ex)
                }
            } catch (ex: Exception) {
                Log.e(TAG, "ex", ex)
            }
        }
    }

    fun hideKeyboard() {
        try {
            view?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    try {
                        val r = Rect()
                        // r will be populated with the coordinates of your view that area still visible.
                        view?.getWindowVisibleDisplayFrame(r)

                        val heightDiff = view?.rootView?.height!! - (r.bottom - r.top)
                        if (heightDiff > 300) {
                            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                            imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                        } else {
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view?.getViewTreeObserver()?.removeOnGlobalLayoutListener(this)
                        } else {
                            view?.getViewTreeObserver()?.removeGlobalOnLayoutListener(this)
                        }
                    } catch (ex: Throwable) {
                        Log.e(TAG, "ex", ex)
                    }
                }
            })
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun isConnectingToInternet(_context: Context): Boolean {

        val connectivity = _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null)
                for (i in info.indices) {
                    Log.e(TAG, "isConnectingToInternet state" + info[i].state)
                    if (info[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
        }
        return false
    }

    var builder: AlertDialog? = null
    var dialogClickListener: DialogInterface.OnClickListener? = null

    fun showOkDialog(
        title: String? = "",
        message: String? = "",
        okButtonText: String? = "",
        context: Context,
        listener: OnYesNoClickListener? = null
    ) {
        if (message == null || message.isNullOrBlank())
            return
        try {
            if (builder != null) {
                if (builder?.isShowing!!) {
                    builder?.dismiss()
                }
                dialogClickListener = null
                builder = null
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    listener?.onYesClick(Any())
                }
            }
        }

        // Log.e(TAG, "ok dialog state")

        try {
            builder = AlertDialog.Builder(context, R.style.AlertDialog).create()
            builder?.setTitle(title)
            builder?.setMessage(message)
            builder?.setButton(AlertDialog.BUTTON_POSITIVE, okButtonText, dialogClickListener)
//        builder?.setPositiveButton(okButtonText, dialogClickListener)
            builder?.show()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}
