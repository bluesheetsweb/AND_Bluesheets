package src.wrapperutil.utilities

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

abstract class GenericTextWatcher(
    private val firstEditText: EditText,
    private val secondEditText: EditText,
    private val thirdEditText: EditText,
    private val currentView: View,
    private val nextView: View?
) :
    TextWatcher {
    override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
        val text = editable.toString()
        when (currentView.id) {
            firstEditText.id -> if (text.length == 1) nextView!!.requestFocus()
            secondEditText.id -> if (text.length == 1) nextView!!.requestFocus()
            thirdEditText.id -> if (text.length == 1) nextView!!.requestFocus()
            //You can use EditText4 same as above to hide the keyboard
        }
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }
}
