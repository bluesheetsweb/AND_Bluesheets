package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.os.BuildCompat
import androidx.core.view.inputmethod.EditorInfoCompat
import androidx.core.view.inputmethod.InputConnectionCompat
import src.wrapperutil.R
import src.wrapperutil.listener.OnBackPressHandleListener
import src.wrapperutil.listener.OnPasteDone
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class EditText : AppCompatEditText {

    private var plistner: OnPasteDone? = null

    private var onBackListner: OnBackPressHandleListener? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        intiAttributes(attrs)
    }

    fun setListner(listner: OnPasteDone?) {
        this.plistner = listner
    }

    fun setOnBackLisnter(listner: OnBackPressHandleListener) {
        this.onBackListner = listner
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        intiAttributes(attrs)
    }

    companion object {
        val TAG = EditText::class.java.simpleName
    }

    init {

        if (background != null) {
            if (isFocused)
                background.mutate().setColorFilter(
                    Color.parseColor(ColorPicker.BLACK),
                    PorterDuff.Mode.SRC_ATOP
                )
            else
                background.mutate().setColorFilter(
                    Color.parseColor(ColorPicker.GRAY_50),
                    PorterDuff.Mode.SRC_ATOP
                )
        }
    }

    private fun intiAttributes(attrs: AttributeSet) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.AttrEditText, 0, 0)
        var textMode = array.getInt(R.styleable.AttrEditText_edit_mode, 0)
        var defaultPadding = array.getBoolean(R.styleable.AttrEditText_defaultInputPadding, true)
        var lesspadding = array.getBoolean(R.styleable.AttrEditText_lessInputPadding, false)
        setTextColor(Color.parseColor(ColorPicker.BLACK))
        updateMode(WrapperEnumAnnotation(textMode))
        var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
        var paddingOther = resources.getDimensionPixelOffset(R.dimen._3dp)
        var paddingLess = resources.getDimensionPixelOffset(R.dimen._12dp)
        if (lesspadding) {
            setPadding(paddingLess, paddingLess, paddingLess, paddingLess)
        } else if (defaultPadding) {
            setPadding(paddingOther, padding, paddingOther, padding)
        }

        array.recycle()
    }

    fun updateMode(mode: WrapperEnumAnnotation) {
        @WrapperEnumAnnotation.EditMode val status = mode.state
        when (status) {
            WrapperConstant.EDIT_MODE_LARGE -> {
                try {
                    var fontSize = resources.getDimension(R.dimen.txt_size_20sp)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
//                    typeface = Typeface.DEFAULT_BOLD
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.EDIT_MODE_SMALL -> {
                try {
                    var fontSize = resources.getDimension(R.dimen.txt_size_12sp)
//                    var padding = resources.getDimensionPixelOffset(R.dimen._9dp)
//                    //  var paddingOther = resources.getDimensionPixelOffset(R.dimen._5dp)
//                    setPadding(padding, padding, padding, padding)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.EDIT_MODE_MEDIUM -> {
                try {
                    var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
//                    var padding = resources.getDimensionPixelOffset(R.dimen._9dp)
//                    //  var paddingOther = resources.getDimensionPixelOffset(R.dimen._5dp)
//                    setPadding(padding, padding, padding, padding)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                    setHintTextColor(Color.parseColor(ColorPicker.GRAY_50))
                    typeface = Typeface.create("sans-serif-bold", Typeface.NORMAL)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.EDIT_MODE_WHITE -> {
                try {
                    var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
//                    var padding = resources.getDimensionPixelOffset(R.dimen._9dp)
//                    //  var paddingOther = resources.getDimensionPixelOffset(R.dimen._5dp)
//                    setPadding(padding, padding, padding, padding)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                    setHintTextColor(Color.parseColor(ColorPicker.GRAY_50))
                    setTextColor(Color.parseColor(ColorPicker.GRAY_50))
                    typeface = Typeface.create("sans-serif-bold", Typeface.NORMAL)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.EDIT_MODE_ERROR -> {
                try {
                    background.mutate().setColorFilter(
                        Color.parseColor(ColorPicker.PRIMARY),
                        PorterDuff.Mode.SRC_ATOP
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.EDIT_MODE_GREEN -> {
                try {
                    var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
//                    var padding = resources.getDimensionPixelOffset(R.dimen._9dp)
//                    //  var paddingOther = resources.getDimensionPixelOffset(R.dimen._5dp)
//                    setPadding(padding, padding, padding, padding)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                    setHintTextColor(Color.parseColor(ColorPicker.GREEN))
                    setTextColor(Color.parseColor(ColorPicker.GREEN))
                    typeface = Typeface.create("sans-serif-bold", Typeface.NORMAL)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else -> {
                try {
                    var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        // Do your thing:
        val consumed = super.onTextContextMenuItem(id)
        // React:
        when (id) {
            android.R.id.cut -> {
                Log.e("YOOYY", "isCUR")
                onTextCut()
            }
            android.R.id.paste -> {
                Log.e("YOOYY", "paste")
                plistner?.isPaste(true)
            }
            android.R.id.copy -> {
                Log.e("YOOYY", "cOPY")
                onTextCopy()
            }
        }
        return consumed
    }

    /**
     * Text was cut from this EditText.
     */
    fun onTextCut() {
        //  Toast.makeText(mmContext, "Cut!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Text was copied from this EditText.
     */
    fun onTextCopy() {
        // Toast.makeText(mmContext, "Copy!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Text was pasted into the EditText.
     */

    override fun onCreateInputConnection(editorInfo: EditorInfo): InputConnection? {
        val ic = super.onCreateInputConnection(editorInfo)
        EditorInfoCompat.setContentMimeTypes(
            editorInfo,
            arrayOf("image/gif", "image/png", "image/jpeg", "image/webp")
        )

        val callback: InputConnectionCompat.OnCommitContentListener
        callback = InputConnectionCompat.OnCommitContentListener { inputContentInfo, flags, opts ->
            // read and display inputContentInfo asynchronously
            if (BuildCompat.isAtLeastNMR1() && flags and InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION != 0) {
                try {
                    inputContentInfo.requestPermission()
                } catch (e: Exception) {
                    Log.e(TAG, "onCommitContent: " + e.message)
                    return@OnCommitContentListener false // return false if failed
                }
            }

            // read and display inputContentInfo asynchronously.
            // call inputContentInfo.releasePermission() as needed.
            /*try {
                val toast = Toast.makeText(getContext(), getContext().resources.getString(R.string.not_supported_input), Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } catch (ex: Exception) {
                Log.e(TAG, "onCommitContent:ex ")
            }*/

            true // return true if succeeded
        }
        if (ic != null) {
            return InputConnectionCompat.createWrapper(ic, editorInfo, callback)
        } else {
            return ic
        }
    }

    fun updateButtonBackground(color: String) {
        if (background != null) {
            background.mutate().setColorFilter(
                Color.parseColor(color),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        if (!getText().isNullOrBlank()) {
            try {
                if (getText()?.length != null)
                    setSelection(getText()?.length!!)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        Log.e(TAG, "onKeyPreIme call $keyCode $event")
        if (keyCode == KEYCODE_BACK) {
            onBackListner?.onBackPressed()
        }

        return super.onKeyPreIme(keyCode, event)
    }
}
