package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import src.wrapperutil.R
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.WrapperConstant

class CircleWithText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr) {
    private var inflater: LayoutInflater? = null
    private var view: View? = null
    //  private var textValue: String? = null
    val backgroundMode: Int
    private var mTextView: TextView? = null
    private var mCircle: Circle? = null

    companion object {
        val TAG = CircleWithText::class.java.simpleName
    }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.AttrCircleWithText, 0, 0)
        // textValue = array.getString(R.styleable.AttrCircleWithText_text_str)
        backgroundMode = array.getInt(R.styleable.AttrCircleWithText_background_mode, 0)
        array.recycle()
        initView()
    }

    private fun initView() {

        try {
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater!!.inflate(R.layout.circle_with_text, null)
            mTextView = view!!.findViewById(R.id.mTextView)
            mCircle = view!!.findViewById(R.id.mCircle)
            // Log.e(TAG,"backgroundMode"+backgroundMode)
            if (backgroundMode == WrapperConstant.BACKGROUND_PRIMARY) {
                mCircle!!.initPaints(WrapperConstant.COLOR_SECONDARY)
            } else if (backgroundMode == WrapperConstant.BACKGROUND_TRANSPARENT_BLACK) {
                mCircle!!.initPaints(WrapperConstant.COLOR_BLACK_TRANSPARENT)
            } else {
                mCircle!!.initPaints(WrapperConstant.COLOR_PRIMARY)
            }

            mTextView!!.setTextColor(Color.parseColor(ColorPicker.WHITE))
            mTextView!!.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10f)
            mTextView!!.typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)

//            when(textCircleMode){
//
//            }

            val layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            removeAllViews()
            addView(view, layoutParams)
            //  mTextView?.setText(textValue)
        } catch (throwObj: Throwable) {
            Log.e(TAG, "init view ex" + throwObj.message)
        }
    }

    fun setTextValue(str: String?) {
        if (!str.isNullOrEmpty())
            mTextView?.setText(str)
    }

    /* @BindingAdapter("textString")
     fun setTextValue(view: AttrTextView, value: String?) {

     }*/
}
