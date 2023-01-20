package src.wrapperutil.uicomponent

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

open class DCFragment : androidx.fragment.app.Fragment(), OnBackPressListener {

    override fun onBackPressed(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO causing issue removed from now
        retainInstance = true
    }

    fun setFocus(editText: EditText) {
        editText.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

//    fun setFocus(editText: EditText) {
//        editText.requestFocus()
//        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
//    }

//    fun removeFocus(editText: AttrEditText) {
//        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromInputMethod(editText.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
//    }

    fun hideSoftKeyboard() {
        val view = requireActivity().findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    open fun fragmentOnResume() {
    }
}

interface OnBackPressListener {
    fun onBackPressed(): Boolean
}
