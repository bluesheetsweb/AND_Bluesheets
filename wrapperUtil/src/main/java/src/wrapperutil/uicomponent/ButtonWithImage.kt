package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import src.wrapperutil.R
import src.wrapperutil.databinding.ButtonWithImageBinding
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class ButtonWithImage @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {
    private var inflater: LayoutInflater? = null
    private var view: View? = null
    private var binding: ButtonWithImageBinding? = null
    val border: GradientDrawable = GradientDrawable()
    val cornerRadius: Float
    val stokeWidth: Int
    val buttonMode: Int
    var textValue: String? = ""
    val imageSrc: Int
    var gravity_mode: Int
    var textMode: Int
    val lessPadding: Boolean
    var drawableMode: Int? = 1
    val defaultPadding: Boolean

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.AttrButton, 0, 0)
        cornerRadius = array.getDimension(R.styleable.AttrButton_corner_radius, resources.getDimension(R.dimen._7dp))
        stokeWidth = array.getDimensionPixelOffset(R.styleable.AttrButton_stroke_width, resources.getDimensionPixelOffset(R.dimen._1dp))
        textValue = array.getString(R.styleable.AttrButton_text_value)
        imageSrc = array.getResourceId(R.styleable.AttrButton_image_src, 0)
        buttonMode = array.getInt(R.styleable.AttrButton_button_mode, 0)
        drawableMode = array.getInt(R.styleable.AttrButton_drawable_mode, 0)
        gravity_mode = array.getInt(R.styleable.AttrButton_gravity_mode, 0)
        textMode = array.getInt(R.styleable.AttrButton_button_text_mode, 0)
        defaultPadding = array.getBoolean(R.styleable.AttrButton_defaultPadding, true)
        lessPadding = array.getBoolean(R.styleable.AttrButton_lessPadding, false)
        gravity = Gravity.CENTER
        array.recycle()
        init()
        updateTextMode(WrapperEnumAnnotation(textMode))
        updateMode(WrapperEnumAnnotation(buttonMode))
        updateGravityMode(WrapperEnumAnnotation(gravity_mode))
    }

    private fun init() {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater!!, R.layout.button_with_image, null, false)
        val layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        removeAllViews()
        addView(binding?.root, layoutParams)
        binding?.textView?.text = textValue
        updateImageSrc(imageSrc)
    }

    fun updateImageResource(
        drawableMode: WrapperEnumAnnotation,
        imageSrc: Int?
    ) {
        this.drawableMode = drawableMode.state
        when (this.drawableMode) {
            WrapperConstant.BUTTON_MODE_DRAWABLE_LEFT -> {
                binding?.textView?.setCompoundDrawablesWithIntrinsicBounds(imageSrc!!, 0, 0, 0)
            }
            WrapperConstant.BUTTON_MODE_DRAWABLE_RIGHT -> {
                binding?.textView?.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageSrc!!, 0)
            }
            else -> {
                binding?.textView?.setCompoundDrawablesWithIntrinsicBounds(imageSrc!!, 0, 0, 0)
            }
        }
    }

    fun setText(text: String?) {
        binding?.textView?.text = text
    }

    fun updateImageSrc(imageSrc: Int?) {
        when (drawableMode) {
            WrapperConstant.BUTTON_MODE_DRAWABLE_LEFT -> {
                binding?.textView?.setCompoundDrawablesWithIntrinsicBounds(imageSrc!!, 0, 0, 0)
            }
            WrapperConstant.BUTTON_MODE_DRAWABLE_RIGHT -> {
                binding?.textView?.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageSrc!!, 0)
            }
            else -> {
                binding?.textView?.setCompoundDrawablesWithIntrinsicBounds(imageSrc!!, 0, 0, 0)
            }
        }
    }

    fun removeImageSrc() {
        binding?.textView?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    fun updateGravityMode(mode: WrapperEnumAnnotation) {
        @WrapperEnumAnnotation.ButtonMode val status = mode.state
        when (status) {
            WrapperConstant.GRAVITY_CENTER -> {
                binding?.innerLinearLayout?.gravity = Gravity.CENTER
            }
            WrapperConstant.GRAVITY_LEFT -> {
                binding?.innerLinearLayout?.gravity = Gravity.LEFT and Gravity.CENTER_VERTICAL
            }
            WrapperConstant.GRAVITY_RIGHT -> {
                binding?.innerLinearLayout?.gravity = Gravity.RIGHT and Gravity.CENTER_VERTICAL
            }
            else -> {
                binding?.innerLinearLayout?.gravity = Gravity.LEFT and Gravity.CENTER_VERTICAL
            }
        }
    }

    fun updateMode(mode: WrapperEnumAnnotation) {
        @WrapperEnumAnnotation.ButtonMode val status = mode.state
        background = when (status) {
            WrapperConstant.BUTTON_MODE_PRIMARY -> {
                border.setColor(Color.parseColor(ColorPicker.PRIMARY))
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.WHITE))
                if (defaultPadding) {
                    var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
                    var paddingHeight = resources.getDimensionPixelOffset(R.dimen._15dp)
                    if (lessPadding)
                        paddingHeight = resources.getDimensionPixelOffset(R.dimen._7dp)
                    setPadding(padding, paddingHeight, padding, paddingHeight)
                }
//                isAllCaps=true
                isEnabled = true
                border.cornerRadius = cornerRadius
                border
            }
            WrapperConstant.BUTTON_MODE_SECONDARY -> {
                border.setColor(Color.parseColor(ColorPicker.SECONDARY))
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.WHITE))
                if (defaultPadding) {
                    var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
                    var paddingHeight = resources.getDimensionPixelOffset(R.dimen._15dp)
                    if (lessPadding)
                        paddingHeight = resources.getDimensionPixelOffset(R.dimen._7dp)
                    setPadding(padding, paddingHeight, padding, paddingHeight)
                }
//                isAllCaps=true
                isEnabled = true
                border.cornerRadius = cornerRadius
                border
            }
            WrapperConstant.BUTTON_MODE_DISABLED -> {
                border.setColor(Color.parseColor(ColorPicker.GRAY_50))
                isEnabled = false
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.WHITE))
                if (defaultPadding) {
                    var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
                    var paddingHeight = resources.getDimensionPixelOffset(R.dimen._15dp)
                    if (lessPadding)
                        paddingHeight = resources.getDimensionPixelOffset(R.dimen._7dp)
                    setPadding(padding, paddingHeight, padding, paddingHeight)
                }
                border.cornerRadius = cornerRadius
                border
            }
            WrapperConstant.BUTTON_MODE_PRIMARY_BLANK -> {
                border.setColor(Color.parseColor(ColorPicker.WHITE))
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.PRIMARY))
//                var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
//                setPadding(padding, padding, padding, padding)
                isEnabled = true
                border.cornerRadius = cornerRadius
                border.setStroke(stokeWidth, Color.parseColor(ColorPicker.WHITE))
                border
            }
            WrapperConstant.BUTTON_MODE_SECONDARY_BLANK -> {
                border.setColor(Color.parseColor(ColorPicker.WHITE))
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.SECONDARY))
//                var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
//                setPadding(padding, padding, padding, padding)
                isEnabled = true
                border.cornerRadius = cornerRadius
                border.setStroke(stokeWidth, Color.parseColor(ColorPicker.WHITE))
                border
            }
            WrapperConstant.BUTTON_MODE_PRIMARY_STROKED_ONLY -> {
                border.setColor(Color.parseColor(ColorPicker.WHITE))
                if (defaultPadding) {
                    var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
                    var paddingHeight = resources.getDimensionPixelOffset(R.dimen._15dp)
                    if (lessPadding)
                        paddingHeight = resources.getDimensionPixelOffset(R.dimen._7dp)
                    setPadding(padding, paddingHeight, padding, paddingHeight)
                }
                isEnabled = true
//                isAllCaps=true
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.PRIMARY))
                border.cornerRadius = cornerRadius
                border.setStroke(stokeWidth, Color.parseColor(ColorPicker.SEPERATOR_COLOR))
                border
            }
            WrapperConstant.BUTTON_MODE_SECONDARY_STROKED_ONLY -> {
                border.setColor(Color.parseColor(ColorPicker.WHITE))
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.SECONDARY))
                if (defaultPadding) {
                    var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
                    var paddingHeight = resources.getDimensionPixelOffset(R.dimen._15dp)
                    if (lessPadding)
                        paddingHeight = resources.getDimensionPixelOffset(R.dimen._7dp)
                    setPadding(padding, paddingHeight, padding, paddingHeight)
                }
                isEnabled = true
//                isAllCaps=true
                border.cornerRadius = cornerRadius
                border.setStroke(stokeWidth, Color.parseColor(ColorPicker.SEPERATOR_COLOR))
                border
            }
            WrapperConstant.BUTTON_MODE_STROKE_ONLY -> {
                border.setColor(Color.parseColor(ColorPicker.WHITE))
                isEnabled = true
//                isAllCaps=true
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.BLACK))
                border.cornerRadius = cornerRadius
                border.setStroke(stokeWidth, Color.parseColor(ColorPicker.SEPERATOR_COLOR))
                /*TODO changed*/
                if (defaultPadding) {
                    var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
                    var paddingHeight = resources.getDimensionPixelOffset(R.dimen._15dp)
                    if (lessPadding)
                        paddingHeight = resources.getDimensionPixelOffset(R.dimen._7dp)
                    setPadding(padding, paddingHeight, padding, paddingHeight)
                }
                border
            }
            WrapperConstant.BUTTON_MODE_TEXT_DISABLED -> {
                border.setColor(Color.parseColor(ColorPicker.WHITE))
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.GRAY_50))
//                var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
//                setPadding(padding, padding, padding, padding)
                isEnabled = false
                border.cornerRadius = cornerRadius
                border.setStroke(stokeWidth, Color.parseColor(ColorPicker.WHITE))
                border
            }
            WrapperConstant.BUTTON_MODE_WHITE_TEXT_STROKE_ONLY -> {
                border.setColor(Color.parseColor(ColorPicker.TRANSPARENT))
                isEnabled = true
//                isAllCaps=true
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.WHITE))
                border.cornerRadius = cornerRadius
                border.setStroke(stokeWidth, Color.parseColor(ColorPicker.WHITE))
                if (defaultPadding) {
                    var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
                    var paddingHeight = resources.getDimensionPixelOffset(R.dimen._15dp)
                    if (lessPadding)
                        paddingHeight = resources.getDimensionPixelOffset(R.dimen._7dp)
                    setPadding(padding, paddingHeight, padding, paddingHeight)
                }
                border
            }
            WrapperConstant.BUTTON_MODE_TEXT_BLACK_WITHOUT_BACKGROUND -> {
                border.setColor(Color.parseColor(ColorPicker.TRANSPARENT))
                isEnabled = true
//                isAllCaps=true
                // mTextView?.textSize=resources.get(R.dimen.txt_size_10sp)
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.BLACK))
                // border.cornerRadius = cornerRadius
                // border.setStroke(stokeWidth, Color.parseColor(DCColorPicker.WHITE))
                if (defaultPadding) {
                    var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
                    var paddingHeight = resources.getDimensionPixelOffset(R.dimen._15dp)
                    if (lessPadding)
                        paddingHeight = resources.getDimensionPixelOffset(R.dimen._7dp)
                    setPadding(padding, paddingHeight, padding, paddingHeight)
                }
                border
            }
            else -> {
                isEnabled = true
//                isAllCaps=true
                border.setColor(Color.parseColor(ColorPicker.PRIMARY))
                if (defaultPadding) {
                    var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
                    var paddingHeight = resources.getDimensionPixelOffset(R.dimen._15dp)
                    if (lessPadding)
                        paddingHeight = resources.getDimensionPixelOffset(R.dimen._7dp)
                    setPadding(padding, paddingHeight, padding, paddingHeight)
                }
                binding?.textView?.setTextColor(Color.parseColor(ColorPicker.WHITE))
                border.cornerRadius = cornerRadius
                border
            }
        }
    }

    fun disableTextView() {
        binding?.textView!!.setTextColor(Color.parseColor(ColorPicker.GRAY_50))
    }

    fun updateTextMode(mode: WrapperEnumAnnotation) {
        with(binding?.textView!!) {
            @WrapperEnumAnnotation.TextMode val status = mode.state
            when (status) {
                WrapperConstant.TEXT_MODE_HEADING_MEDIUM -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_20sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.BLACK))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_SUB_HEADING_BOLD -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.DEFAULT_BOLD
                        setTextColor(Color.parseColor(ColorPicker.BLACK))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_SUB_HEADING_REGULAR -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.BLACK))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM_WHITE -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.WHITE))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM_GRAY_75 -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.GRAY_75))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM_SECONDARY -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.SECONDARY))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_SUB_HEADING_REGULAR_GRAY_50 -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.GRAY_50))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_PARAGRAPH_BOLD -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_12sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.BLACK))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_PARAGRAPH_REGULAR_GRAY_50 -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_12sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.GRAY_50))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_GRAY_75 -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_12sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.GRAY_75))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_WHITE -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_12sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.WHITE))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_SECONDARY -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_12sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.SECONDARY))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_ERROR -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_12sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.PRIMARY))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                WrapperConstant.TEXT_MODE_SMALL_MEDIUM_GRAY_75 -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_10sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.GRAY_75))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    try {
                        var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                        setTextColor(Color.parseColor(ColorPicker.WHITE))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
