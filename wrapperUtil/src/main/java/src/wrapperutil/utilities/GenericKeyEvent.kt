package src.wrapperutil.utilities

import android.view.KeyEvent
import android.view.View
import android.widget.EditText

class GenericKeyEvent(private val firstView:EditText, private val currentView: EditText, private val previousView: EditText?) : View.OnKeyListener{
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if(event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != firstView.id && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }


}
