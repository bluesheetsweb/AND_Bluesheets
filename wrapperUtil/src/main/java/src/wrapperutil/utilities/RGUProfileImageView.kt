package src.wrapperutil.utilities

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import src.wrapperutil.R
import src.wrapperutil.databinding.RguProfileViewBinding
import src.wrapperutil.listener.OnGlobalCallListener

class RGUProfileImageView : ConstraintLayout {

    private var inflater: LayoutInflater? = null
    private var binding: RguProfileViewBinding? = null
    private var textSize: Int? = 0

    companion object {
        val TAG = RGUProfileImageView::class.java.simpleName
    }

    /*override fun onFinishInflate() {
        super.onFinishInflate()
        DataBindingUtil.bind(this)
    }*/

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val array =
            context.obtainStyledAttributes(attrs, src.wrapperutil.R.styleable.AttrCustomView, 0, 0)
        textSize = array.getResourceId(src.wrapperutil.R.styleable.AttrCustomView_textSize, 0)
        initView()
    }

    fun initView() {
        binding = DataBindingUtil.inflate(
            inflater!!,
            R.layout.rgu_profile_view, null, false
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
                context, imagePath, binding!!.imageView,
                newPlaceHolder!!
            )
        }
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

    fun processView(
        imagePath: String?,
        fullName: String?,
        presence: Int? = -1,
        placeHolder: Int? = R.drawable.ic_presenting,
        colorCode: Int? = 0,
        circleNeed: Int? = 0
    ) {

        Log.e(TAG, "imagePath" + imagePath)

        if (textSize != 0) {
            try {
                binding?.textName!!.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(textSize!!)
                )
            } catch (ex: Exception) {
                Log.e(TAG, " processView ex" + ex.message)
            }
        }

        if (imagePath.isNullOrBlank() && !fullName.isNullOrBlank()) {
            Log.e(TAG, "imagePath if")
            var nameArray = fullName.split("\\s+".toRegex())
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
            if (circleNeed == 0) {
                binding?.viewName!!.visibility = View.VISIBLE
            } else {
                binding?.viewName!!.visibility = View.GONE
                binding?.viewNameCircle!!.visibility = View.VISIBLE
            }

            binding?.textName!!.text = name
            binding?.textName1!!.text = name

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
                    }
                }
            )
        } else if (imagePath.isNullOrEmpty()) {
            Log.e(TAG, "imagePath else")
            binding?.viewName!!.visibility = View.GONE
            binding?.imageView!!.visibility = View.VISIBLE
            binding?.imageView?.setImageResource(placeHolder!!)
        }
//        if (presence == null)
//            return
//        if (presence == -1)
//            binding?.viewName!!.visibility = View.GONE
//        else {
//            binding?.viewName!!.visibility = View.VISIBLE
//
//            val observer = binding!!.root.viewTreeObserver
//            observer.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
//
//                override fun onGlobalLayout() {
//
//                    val headerLayoutHeight = binding!!.root.height
//                    // val headerLayoutWidth = binding!!.root.getWidth()
//
//                    var heightInDp = convertPixelsToDp(headerLayoutHeight.toFloat())
//
//                    var bottomMarginForCircleDp = heightInDp / 6
//                    var rightMarginForCircleDp = heightInDp / 6
//
//                    var minusRightPx = convertDpToPixel(6f)
//                    var minusBottomPx = convertDpToPixel(5f)
//
//
//                    var bottomMarginForCirclePx = convertDpToPixel(bottomMarginForCircleDp)
//                    var rightMarginForCirclePX = convertDpToPixel(rightMarginForCircleDp)
//
//
//                    val params = FrameLayout.LayoutParams(
//                        FrameLayout.LayoutParams.MATCH_PARENT,
//                        FrameLayout.LayoutParams.MATCH_PARENT
//                    )
//                    params.setMargins(
//                        0,
//                        0,
//                        rightMarginForCirclePX - minusRightPx,
//                        bottomMarginForCirclePx - minusBottomPx
//                    )
//                    params.gravity = Gravity.BOTTOM or Gravity.END
//                    //  params.gravity=Gravity.CENTER
//
//                    binding!!.root.viewTreeObserver.removeGlobalOnLayoutListener(
//                        this
//                    )
//                }
//            })
//        }
    }

    fun updatePresence(presence: Int) {
        binding?.viewName!!.visibility = View.VISIBLE
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
