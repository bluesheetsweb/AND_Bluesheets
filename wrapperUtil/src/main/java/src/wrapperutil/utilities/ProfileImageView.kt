package src.wrapperutil.utilities

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import src.wrapperutil.R
import src.wrapperutil.databinding.ProfileViewBinding
import src.wrapperutil.listener.OnGlobalCallListener

class ProfileImageView : ConstraintLayout {

    private var inflater: LayoutInflater? = null
    private var binding: ProfileViewBinding? = null
    private var textSize: Int? = 0

    companion object {
        val TAG = ProfileImageView::class.java.simpleName
    }

    /*override fun onFinishInflate() {
        super.onFinishInflate()
        DataBindingUtil.bind(this)
    }*/

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val array = context.obtainStyledAttributes(attrs, src.wrapperutil.R.styleable.AttrCustomView, 0, 0)
        textSize = array.getResourceId(src.wrapperutil.R.styleable.AttrCustomView_textSize, 0)
        initView()
    }

    fun initView() {
        binding = DataBindingUtil.inflate(
            inflater!!,
            R.layout.profile_view, null, false
        )
//        removeAllViews()
        val layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(binding?.root, layoutParams)
    }

    fun updateTextMode(mode: WrapperEnumAnnotation) {
        binding?.textName?.updateMode(mode)
    }

    fun processDefaultDrawable(imagePath: String?, placeHolder: Int?) {
        binding?.viewName?.visibility = View.GONE
        binding?.imageView?.visibility = View.VISIBLE
        var newPlaceHolder = placeHolder
        if (imagePath.isNullOrBlank()) {
            if (newPlaceHolder != null && newPlaceHolder != 0)
                binding?.imageView?.setImageResource(newPlaceHolder)
            else {
                // TODO add image
                // binding?.imageView?.setImageResource(R.drawable.default_profile_rect)
            }
        } else {
            if (newPlaceHolder == null || newPlaceHolder == 0) {
                // TODO add image
                // newPlaceHolder = R.drawable.default_profile_rect
            }

            PicUtil.display160ProfileImgWithCenterCrop(
                context, imagePath!!, binding!!.imageView,
                newPlaceHolder!!
            )
        }
        binding?.circleStatus!!.visibility = View.GONE
    }

    fun setTextSizePx(textSizePx: Float) {
        // this.textSize=textSize
        Log.e(TAG, "setTextSizePx $textSizePx")
        if (textSizePx != 0f) {
            try {
                binding?.textName!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePx)
            } catch (ex: Exception) {
                Log.e(TAG, "setTextSizePx  ex" + ex.message)
            }
        }
    }

    fun processView(imagePath: String?, fullName: String?, presence: Int? = -1, placeHolder: Int? = R.drawable.ic_presenting, colorCode: Int? = 0) {

        Log.e(TAG, "imagePath" + imagePath)

        if (textSize != 0) {
            try {
                binding?.textName!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(textSize!!))
            } catch (ex: Exception) {
                Log.e(TAG, " processView ex" + ex.message)
            }
        }

        if (imagePath.isNullOrBlank() && !fullName.isNullOrBlank()) {
            Log.e(TAG, "imagePath if")
            var nameArray = fullName!!.split(" ")
            var name = ""

            for (attribute in nameArray) {
                if (!attribute.isNullOrBlank())
                    name += attribute[0]
            }
            Log.e(TAG, "processview name first" + name)
            if (name.length >= 3) {
                name = name.get(0).toString() + name.get(name.length - 1).toString()
            }

            binding?.imageView!!.visibility = View.GONE
            binding?.viewName!!.visibility = View.VISIBLE
            binding?.textName!!.text = name

            var firstAlphabet = ""
            var secondtAlphabet = ""

            if (name.length >= 2) {
                firstAlphabet = name.get(0).toString()
                secondtAlphabet = name.get(name.length - 1).toString()
            } else {
                firstAlphabet = name.get(0).toString()
            }

            Log.e(TAG, "processview name firstAlphabet" + firstAlphabet)
            Log.e(TAG, "processview name secondtAlphabet" + secondtAlphabet)

            if (colorCode != null && colorCode != 0) {
                binding?.circleBackground!!.initPaints(colorCode)
            } else {
                var random = KNUtil().getRandomColorType(firstAlphabet, secondtAlphabet)
                if (random == KNUtil.RANDOM_COLOR_1) {
                    binding?.circleBackground!!.initPaints(WrapperConstant.COLOR_RANDOM_1)
                } else if (random == KNUtil.RANDOM_COLOR_2) {
                    binding?.circleBackground!!.initPaints(WrapperConstant.COLOR_RANDOM_2)
                } else if (random == KNUtil.RANDOM_COLOR_3) {
                    binding?.circleBackground!!.initPaints(WrapperConstant.COLOR_RANDOM_3)
                } else if (random == KNUtil.RANDOM_COLOR_4) {
                    binding?.circleBackground!!.initPaints(WrapperConstant.COLOR_RANDOM_4)
                } else if (random == KNUtil.RANDOM_COLOR_5) {
                    binding?.circleBackground!!.initPaints(WrapperConstant.COLOR_RANDOM_5)
                } else if (random == KNUtil.RANDOM_COLOR_6) {
                    binding?.circleBackground!!.initPaints(WrapperConstant.COLOR_RANDOM_6)
                } else {
                    binding?.circleBackground!!.initPaints(WrapperConstant.COLOR_RANDOM_6)
                }

                Log.e(TAG, "random number" + random)
            }
        } else if (!imagePath.isNullOrEmpty()) {
            Log.e(TAG, "imagePath else if")
            binding?.viewName!!.visibility = View.GONE
            binding?.imageView!!.visibility = View.VISIBLE
            PicUtil.display160ProfileImgWithCenterCrop(
                context, imagePath, binding!!.imageView,
                placeHolder!!,
                object : OnGlobalCallListener {
                    override fun onSuccess(value: Any) {
                    }

                    override fun onError(value: Any) {
                        var nameArray = fullName!!.split(" ")
                        var name = fullName
                        if (nameArray != null && nameArray.isNotEmpty() && nameArray.size > 0) {
                            try {
                                for (attribute in nameArray) {
                                    name += attribute[0]
                                }
                            } catch (t: Throwable) {
                                t.printStackTrace()
                            }
                        }

                        if (name!!.length > 3)
                            name = name!!.substring(0, 2)
                        binding?.imageView!!.visibility = View.GONE
                        binding?.viewName!!.visibility = View.VISIBLE
                        binding?.textName!!.text = name
                        binding?.circleBackground!!.initPaints(WrapperConstant.COLOR_PRIMARY)
                    }
                }
            )
        } else if (imagePath.isNullOrEmpty()) {
            Log.e(TAG, "imagePath else")
            binding?.viewName!!.visibility = View.GONE
            binding?.imageView!!.visibility = View.VISIBLE
            binding?.imageView?.setImageResource(placeHolder!!)
        }
        if (presence == null)
            return
        if (presence == -1)
            binding?.circleStatus!!.visibility = View.GONE
        else {
            binding?.circleStatus!!.visibility = View.VISIBLE

            if (presence == WrapperConstant.PRESENCE_VERIFIED) {
                binding?.circleStatus!!.initPaints(WrapperConstant.COLOR_IMAGE)
                // TODO add image
                // binding!!.circleStatus.setBackgroundResource(R.drawable.ic_verified)
            } else if (presence == WrapperConstant.PRESENCE_NOT_VERIFIED) {
                binding?.circleStatus!!.initPaints(WrapperConstant.COLOR_IMAGE)
                // TODO add image
                // binding!!.circleStatus.setBackgroundResource(R.drawable.ic_not_verified)
            } else {
                binding?.circleStatus!!.initPaints(presence)
            }

            val observer = binding!!.root.viewTreeObserver
            observer.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {

                override fun onGlobalLayout() {

                    val headerLayoutHeight = binding!!.root.height
                    // val headerLayoutWidth = binding!!.root.getWidth()

                    var heightInDp = convertPixelsToDp(headerLayoutHeight.toFloat())

                    var bottomMarginForCircleDp = heightInDp / 6
                    var rightMarginForCircleDp = heightInDp / 6

                    var minusRightPx = convertDpToPixel(6f)
                    var minusBottomPx = convertDpToPixel(5f)

                    var bottomMarginForCirclePx = convertDpToPixel(bottomMarginForCircleDp)
                    var rightMarginForCirclePX = convertDpToPixel(rightMarginForCircleDp)

                    val params = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                    params.setMargins(0, 0, rightMarginForCirclePX - minusRightPx, bottomMarginForCirclePx - minusBottomPx)
                    params.gravity = Gravity.BOTTOM or Gravity.END
                    //  params.gravity=Gravity.CENTER

                    binding?.circleStatus!!.layoutParams = params
                    binding?.circleStatus!!.layoutParams.height = convertDpToPixel(10f)
                    binding?.circleStatus!!.layoutParams.width = convertDpToPixel(10f)
                    binding!!.root.viewTreeObserver.removeGlobalOnLayoutListener(
                        this
                    )
                }
            })
        }
    }

    fun updatePresence(presence: Int) {
        binding?.circleStatus!!.visibility = View.VISIBLE
        binding?.circleStatus!!.initPaints(presence)
    }

    fun convertDpToPixel(dp: Float): Int {
        return (dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

/* fun convertPxToDp(px:Int):Float{

     val dp = TypedValue.applyDimension(
             TypedValue.COMPLEX_UNIT_DIP,
             px.toFloat() ,
             resources.getDisplayMetrics()
     )

     return  dp
 }*/

/* fun convertDpToPx(dp:Float):Int{

     val px = TypedValue.applyDimension(
             TypedValue.COMPLEX_UNIT_PX,
             dp.toFloat() ,
             resources.getDisplayMetrics()
     )

     return  px.toInt()
 }*/

/*funconvertDpToPixel(dp: Float): Int {
    return (dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

*/
    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     *//*
    fun convertPixelsToDp(px: Float): Float {
        return px / (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

*/
}
