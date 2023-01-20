package src.wrapperutil.rguicomponent.interfaces

import android.view.View

interface RGEdittextViewInterface : RGBaseViewInterface {

    fun startTyping(char: CharSequence)

    fun endTyping(char: CharSequence, self: View): String

    fun textChange(char: CharSequence, self: View)
}
