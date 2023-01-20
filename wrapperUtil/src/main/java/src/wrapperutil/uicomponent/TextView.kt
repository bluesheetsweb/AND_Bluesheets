package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.util.Linkify
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import src.wrapperutil.R
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.CustomTypefaceSpan
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class TextView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    TextView(context, attrs, defStyleAttr) {
    private val TAG = "AttrTextView"

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.AttrTextView, 0, 0)
        val textMode = array.getInt(R.styleable.AttrTextView_text_mode, 0)
        val defaultTypeLinkCheck =
            array.getBoolean(R.styleable.AttrTextView_default_link_check, true)
        // Log.e(TAG, "defaultTypeLinkCheck is $defaultTypeLinkCheck")
//        if (defaultTypeLinkCheck) {
//            Linkify.addLinks(this, Linkify.WEB_URLS);
//            setLinksClickable(true);
        setLinkTextColor(Color.parseColor(ColorPicker.SECONDARY))
//            setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6.0f, resources.displayMetrics), 1.0f)
//        }
        updateMode(WrapperEnumAnnotation(textMode))
        array.recycle()
    }

    fun updateModeAsLink() {
        Linkify.addLinks(this, Linkify.WEB_URLS)
        setLinksClickable(true)
        setLinkTextColor(Color.parseColor(ColorPicker.SECONDARY))
        setLineSpacing(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                6.0f,
                resources.displayMetrics
            ), 1.0f
        )
    }

    fun setSpannableParagraphBoldAndParagraphRegularGray50(
        boldText: String?,
        regularText: String?,
        isSpaceRequiredBetween: Boolean? = true
    ) {
        // var fontSize = resources.getDimension(R.dimen.txt_size_12)
        var fontSize = 12f
        setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        var finalText = when (isSpaceRequiredBetween!!) {
            true -> "$boldText $regularText"
            false -> "$boldText$regularText"
        }
        var boldTextLength = boldText?.length!!
        var regularTextStartIndex = boldTextLength + 1
        var finalLength = finalText.length
        val font = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        val font2 = Typeface.create("sans-serif", Typeface.NORMAL)
        val SS = SpannableString(finalText)
        SS.setSpan(
            CustomTypefaceSpan("", font, Color.parseColor(ColorPicker.BLACK)),
            0,
            boldTextLength,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        SS.setSpan(
            CustomTypefaceSpan("", font2, Color.parseColor(ColorPicker.GRAY_50)),
            regularTextStartIndex,
            finalLength,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        setText(SS)
    }

    fun updateMode(mode: WrapperEnumAnnotation) {
        @WrapperEnumAnnotation.TextMode val status = mode.state
        when (status) {
            WrapperConstant.TEXT_MODE_HEADING_MEDIUM -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_20)
                    var fontSize = 20f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
//                    typeface = Typeface.create("sans-serif-bold", Typeface.NORMAL)
//                    typeface = Typeface.DEFAULT_BOLD
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.BLACK))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_BOLD -> {
                try {
                    //  var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.DEFAULT_BOLD
                    setTextColor(Color.parseColor(ColorPicker.BLACK))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_REGULAR -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.BLACK))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM_WHITE -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.WHITE))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM_GRAY_75 -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.GRAY_75))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM_SECONDARY -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.SECONDARY))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM_PRIMARY -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.PRIMARY))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.BLACK))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_REGULAR_GRAY_50 -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.GRAY_50))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_PARAGRAPH_BOLD -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_12)
                    var fontSize = 12f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
                    setTextColor(Color.parseColor(ColorPicker.BLACK))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_PARAGRAPH_REGULAR_GRAY_50 -> {
                try {
                    //  var fontSize = resources.getDimension(R.dimen.txt_size_12)
                    var fontSize = 12f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.GRAY_50))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            WrapperConstant.TEXT_MODE_PARAGRAPH_REGULAR -> {
                try {
                    var fontSize = 12f
                    // var fontSize = resources.getDimension(R.dimen.txt_size_12)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.BLACK))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_GRAY_75 -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_12)
                    var fontSize = 12f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.GRAY_75))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_WHITE -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_12)
                    var fontSize = 12f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.WHITE))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_SECONDARY -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_12)
                    var fontSize = 12f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.SECONDARY))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_ERROR -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_12)
                    var fontSize = 12f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.ERROR))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SMALL_MEDIUM_GRAY_75 -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_10)
                    var fontSize = 10f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.GRAY_75))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            WrapperConstant.TEXT_MODE_SUB_HEADING_REGULAR_SECONDARY -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.SECONDARY))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_LARGE_MEDIUM_WHITE -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 29f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.WHITE))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SMALL_MEDIUM_WHITE -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 10f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.WHITE))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_LARGE_REGULAR_WHITE -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 29f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.WHITE))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_LARGE_MEDIUM_BLACK -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 29f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.BLACK))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_BOLD_WHITE -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
                    setTextColor(Color.parseColor(ColorPicker.WHITE))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_REGULAR_BLUE -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.BLUE))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM_GREEN -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.GREEN))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_SUB_HEADING_REGULAR_GREEN -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_15)
                    var fontSize = 15f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.GREEN))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_PARAGRAPH_BOLD_GREEN -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_12)
                    var fontSize = 12f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
                    setTextColor(Color.parseColor(ColorPicker.GREEN))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.TEXT_MODE_PARAGRAPH_REGULAR_GREEN -> {
                try {
                    // var fontSize = resources.getDimension(R.dimen.txt_size_12)
                    var fontSize = 12f
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                    setTextColor(Color.parseColor(ColorPicker.GREEN))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else -> {
                /* try {
                     var fontSize = resources.getDimension(R.dimen.txt_size_12)
                     setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
                     typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                     setTextColor(Color.parseColor(DCColorPicker.BLACK))
                 } catch (e: Exception) {
                     e.printStackTrace()
                 }*/
            }
        }
    }

    fun updateTextColor(textColor: Int) {
        setTextColor(textColor)
    }
}
