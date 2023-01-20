package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import src.wrapperutil.R
import src.wrapperutil.utilities.ProgressLayout
import java.util.*

class ProgressConstraintLayout : ConstraintLayout, ProgressLayout {

    private val CONTENT = "type_content"
    private val LOADING = "type_loading"
    private val EMPTY = "type_empty"
    private val ERROR = "type_error"

    private var inflater: LayoutInflater? = null
    private var view: View? = null
    private var defaultBackground: Drawable? = null

    private val contentViews = ArrayList<View>()

    private var loadingState: View? = null
    private var loadingStateProgressBar: ProgressBar? = null

    private var emptyState: View? = null
    private var emptyStateImageView: ImageView? = null
    private var emptyStateTitleTextView: TextView? = null
    private var emptyStateContentTextView: TextView? = null

    private var errorState: View? = null
    private var errorStateImageView: ImageView? = null
    private var errorStateTitleTextView: TextView? = null
    private var errorStateContentTextView: TextView? = null
    private var errorStateButton: Button? = null

    private var loadingStateProgressBarWidth: Int = 0
    private var loadingStateProgressBarHeight: Int = 0
    private var loadingStateProgressBarColor: Int = 0
    private var loadingStateBackgroundColor: Int = 0

    private var emptyStateImageWidth: Int = 0
    private var emptyStateImageHeight: Int = 0
    private var emptyStateTitleTextSize: Int = 0
    private var emptyStateTitleTextColor: Int = 0
    private var emptyStateContentTextSize: Int = 0
    private var emptyStateContentTextColor: Int = 0
    private var emptyStateBackgroundColor: Int = 0

    private var errorStateImageWidth: Int = 0
    private var errorStateImageHeight: Int = 0
    private var errorStateTitleTextSize: Int = 0
    private var errorStateTitleTextColor: Int = 0
    private var errorStateContentTextSize: Int = 0
    private var errorStateContentTextColor: Int = 0
    private var errorStateButtonTextColor: Int = 0
    private var errorStateButtonBackgroundColor: Int = 0
    private var errorStateBackgroundColor: Int = 0

    private var state = CONTENT

    constructor(context: Context) : super(context) {}

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

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressActivity)

        // Loading state attrs
        loadingStateProgressBarWidth = typedArray.getDimensionPixelSize(
            R.styleable.ProgressActivity_loadingProgressBarWidth,
            108
        )

        loadingStateProgressBarHeight = typedArray.getDimensionPixelSize(
            R.styleable.ProgressActivity_loadingProgressBarHeight,
            108
        )

        loadingStateProgressBarColor =
            typedArray.getColor(R.styleable.ProgressActivity_loadingProgressBarColor, Color.RED)

        loadingStateBackgroundColor = typedArray.getColor(
            R.styleable.ProgressActivity_loadingBackgroundColor,
            Color.TRANSPARENT
        )

        // Empty state attrs
        emptyStateImageWidth =
            typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_emptyImageWidth, 308)

        emptyStateImageHeight =
            typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_emptyImageHeight, 308)

        emptyStateTitleTextSize =
            typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_emptyTitleTextSize, 15)

        emptyStateTitleTextColor =
            typedArray.getColor(R.styleable.ProgressActivity_emptyTitleTextColor, Color.BLACK)

        emptyStateContentTextSize =
            typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_emptyContentTextSize, 14)

        emptyStateContentTextColor =
            typedArray.getColor(R.styleable.ProgressActivity_emptyContentTextColor, Color.BLACK)

        emptyStateBackgroundColor = typedArray.getColor(
            R.styleable.ProgressActivity_emptyBackgroundColor,
            Color.TRANSPARENT
        )

        // Error state attrs
        errorStateImageWidth =
            typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_errorImageWidth, 308)

        errorStateImageHeight =
            typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_errorImageHeight, 308)

        errorStateTitleTextSize =
            typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_errorTitleTextSize, 15)

        errorStateTitleTextColor =
            typedArray.getColor(R.styleable.ProgressActivity_errorTitleTextColor, Color.BLACK)

        errorStateContentTextSize =
            typedArray.getDimensionPixelSize(R.styleable.ProgressActivity_errorContentTextSize, 14)

        errorStateContentTextColor =
            typedArray.getColor(R.styleable.ProgressActivity_errorContentTextColor, Color.BLACK)

        errorStateButtonTextColor =
            typedArray.getColor(R.styleable.ProgressActivity_errorButtonTextColor, Color.BLACK)

        errorStateButtonBackgroundColor = typedArray.getColor(
            R.styleable.ProgressActivity_errorButtonBackgroundColor,
            Color.WHITE
        )

        errorStateBackgroundColor = typedArray.getColor(
            R.styleable.ProgressActivity_errorBackgroundColor,
            Color.TRANSPARENT
        )

        typedArray.recycle()

        defaultBackground = this.background
    }

    override fun showContent() {
        switchState(CONTENT, 0, null, null, null, null, emptyList())
    }

    override fun showContent(idsOfViewsNotToShow: List<Int>) {
        switchState(CONTENT, 0, null, null, null, null, idsOfViewsNotToShow)
    }

    override fun showLoading() {
        switchState(LOADING, 0, null, null, null, null, emptyList())
    }

    override fun showLoading(idsOfViewsNotToHide: List<Int>) {
        switchState(LOADING, 0, null, null, null, null, idsOfViewsNotToHide)
    }

    override fun showEmpty(icon: Int, title: String, description: String) {
        switchState(EMPTY, icon, title, description, null, null, emptyList())
    }

    override fun showEmpty(icon: Drawable, title: String, description: String) {
        switchState(EMPTY, icon, title, description, null, null, emptyList())
    }

    override fun showEmpty(
        icon: Int,
        title: String,
        description: String,
        idsOfViewsNotToHide: List<Int>
    ) {
        switchState(EMPTY, icon, title, description, null, null, idsOfViewsNotToHide)
    }

    override fun showEmpty(
        icon: Drawable,
        title: String,
        description: String,
        idsOfViewsNotToHide: List<Int>
    ) {
        switchState(EMPTY, icon, title, description, null, null, idsOfViewsNotToHide)
    }

    override fun showError(
        icon: Int,
        title: String,
        description: String,
        buttonText: String,
        buttonClickListener: View.OnClickListener
    ) {
        switchState(ERROR, icon, title, description, buttonText, buttonClickListener, emptyList())
    }

    override fun showError(
        icon: Drawable,
        title: String,
        description: String,
        buttonText: String,
        buttonClickListener: View.OnClickListener
    ) {
        switchState(ERROR, icon, title, description, buttonText, buttonClickListener, emptyList())
    }

    override fun showError(
        icon: Int,
        title: String,
        description: String,
        buttonText: String,
        buttonClickListener: View.OnClickListener,
        idsOfViewsNotToHide: List<Int>
    ) {
        switchState(
            ERROR,
            icon,
            title,
            description,
            buttonText,
            buttonClickListener,
            idsOfViewsNotToHide
        )
    }

    override fun showError(
        icon: Drawable,
        title: String,
        description: String,
        buttonText: String,
        buttonClickListener: View.OnClickListener,
        idsOfViewsNotToHide: List<Int>
    ) {
        switchState(
            ERROR,
            icon,
            title,
            description,
            buttonText,
            buttonClickListener,
            idsOfViewsNotToHide
        )
    }

    private fun switchState(
        state: String,
        icon: Int,
        title: String?,
        description: String?,
        buttonText: String?,
        buttonClickListener: View.OnClickListener?,
        idsOfViewsNotToHide: List<Int>
    ) {
        this.state = state

        hideAllStates()

        when (state) {
            CONTENT -> setContentVisibility(true, idsOfViewsNotToHide)
            LOADING -> {
                setContentVisibility(false, idsOfViewsNotToHide)
                inflateLoadingView()
            }
            EMPTY -> {
                setContentVisibility(false, idsOfViewsNotToHide)
                inflateEmptyView()

                emptyStateImageView!!.setImageResource(icon)
                emptyStateTitleTextView!!.text = title
                emptyStateContentTextView!!.text = description
            }
            ERROR -> {
                setContentVisibility(false, idsOfViewsNotToHide)
                inflateErrorView()
                errorStateImageView!!.setImageResource(icon)
                errorStateTitleTextView!!.text = title
                errorStateContentTextView!!.text = description
                errorStateButton!!.text = buttonText
                errorStateButton!!.setOnClickListener(buttonClickListener)
            }
        }
    }

    private fun switchState(
        state: String,
        icon: Drawable,
        title: String,
        description: String,
        buttonText: String?,
        buttonClickListener: View.OnClickListener?,
        idsOfViewsNotToHide: List<Int>
    ) {
        this.state = state

        hideAllStates()

        when (state) {
            CONTENT -> setContentVisibility(true, idsOfViewsNotToHide)
            LOADING -> {
                setContentVisibility(false, idsOfViewsNotToHide)
                inflateLoadingView()
            }
            EMPTY -> {
                setContentVisibility(false, idsOfViewsNotToHide)
                inflateEmptyView()

                emptyStateImageView!!.setImageDrawable(icon)
                emptyStateTitleTextView!!.text = title
                emptyStateContentTextView!!.text = description
            }
            ERROR -> {
                setContentVisibility(false, idsOfViewsNotToHide)
                inflateErrorView()

                errorStateImageView!!.setImageDrawable(icon)
                errorStateTitleTextView!!.text = title
                errorStateContentTextView!!.text = description
                errorStateButton!!.text = buttonText
                errorStateButton!!.setOnClickListener(buttonClickListener)
            }
        }
    }

    private fun hideAllStates() {
        hideLoadingView()
        hideEmptyView()
        hideErrorView()
        restoreDefaultBackground()
    }

    private fun hideLoadingView() {
        if (loadingState != null) {
            loadingState!!.visibility = View.GONE
        }
    }

    private fun hideEmptyView() {
        if (emptyState != null) {
            emptyState!!.visibility = View.GONE
        }
    }

    private fun hideErrorView() {
        if (errorState != null) {
            errorState!!.visibility = View.GONE
        }
    }

    private fun restoreDefaultBackground() {
        this.setBackgroundDrawable(defaultBackground)
    }

    private fun setContentVisibility(visible: Boolean, skipIds: List<Int>) {
        for (v in contentViews) {
            if (!skipIds.contains(v.id)) {
                v.visibility = if (visible) View.VISIBLE else View.GONE
            }
        }
    }

    private fun inflateLoadingView() {
        if (loadingState == null) {
            view = inflater!!.inflate(R.layout.view_loading, null)
            loadingState = view!!.findViewById(R.id.layout_loading)
            loadingState!!.tag = LOADING

            loadingStateProgressBar = view!!.findViewById(R.id.progress_bar_loading)
            loadingStateProgressBar!!.layoutParams.width = loadingStateProgressBarWidth
            loadingStateProgressBar!!.layoutParams.height = loadingStateProgressBarHeight
            loadingStateProgressBar!!.indeterminateDrawable
                .setColorFilter(loadingStateProgressBarColor, PorterDuff.Mode.SRC_IN)
            loadingStateProgressBar!!.requestLayout()

            if (loadingStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(loadingStateBackgroundColor)
            }

            val layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            layoutParams.topToTop = ConstraintSet.PARENT_ID
            layoutParams.bottomToBottom = ConstraintSet.PARENT_ID
            layoutParams.startToStart = ConstraintSet.PARENT_ID
            layoutParams.endToEnd = ConstraintSet.PARENT_ID

            addView(loadingState, layoutParams)
        } else {
            loadingState!!.visibility = View.VISIBLE
        }
    }

    private fun inflateEmptyView() {
        if (emptyState == null) {
            view = inflater!!.inflate(R.layout.view_empty, null)
            emptyState = view!!.findViewById(R.id.layout_empty)
            emptyState!!.tag = EMPTY

            emptyStateImageView = view!!.findViewById(R.id.image_icon)
            emptyStateTitleTextView = view!!.findViewById(R.id.text_title)
            emptyStateContentTextView = view!!.findViewById(R.id.text_description)

            emptyStateImageView!!.layoutParams.width = emptyStateImageWidth
            emptyStateImageView!!.layoutParams.height = emptyStateImageHeight
            emptyStateImageView!!.requestLayout()

            emptyStateTitleTextView!!.textSize = emptyStateTitleTextSize.toFloat()
            emptyStateTitleTextView!!.setTextColor(emptyStateTitleTextColor)

            emptyStateContentTextView!!.textSize = emptyStateContentTextSize.toFloat()
            emptyStateContentTextView!!.setTextColor(emptyStateContentTextColor)

            if (emptyStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(emptyStateBackgroundColor)
            }

            val layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            layoutParams.topToTop = ConstraintSet.PARENT_ID
            layoutParams.bottomToBottom = ConstraintSet.PARENT_ID
            layoutParams.startToStart = ConstraintSet.PARENT_ID
            layoutParams.endToEnd = ConstraintSet.PARENT_ID

            addView(emptyState, layoutParams)
        } else {
            emptyState!!.visibility = View.VISIBLE
        }
    }

    private fun inflateErrorView() {
        if (errorState == null) {
            view = inflater!!.inflate(R.layout.view_error, null)
            errorState = view!!.findViewById(R.id.layout_error)
            errorState!!.tag = ERROR

            errorStateImageView = view!!.findViewById(R.id.image_icon)
            errorStateTitleTextView = view!!.findViewById(R.id.text_title)
            errorStateContentTextView = view!!.findViewById(R.id.text_description)
            errorStateButton = view!!.findViewById(R.id.button_retry)

            errorStateImageView!!.layoutParams.width = errorStateImageWidth
            errorStateImageView!!.layoutParams.height = errorStateImageHeight
            errorStateImageView!!.requestLayout()

            errorStateTitleTextView!!.textSize = errorStateTitleTextSize.toFloat()
            errorStateTitleTextView!!.setTextColor(errorStateTitleTextColor)

            errorStateContentTextView!!.textSize = errorStateContentTextSize.toFloat()
            errorStateContentTextView!!.setTextColor(errorStateContentTextColor)

            errorStateButton!!.setTextColor(errorStateButtonTextColor)
            errorStateButton!!.background.colorFilter =
                LightingColorFilter(1, errorStateButtonBackgroundColor)

            if (errorStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(errorStateBackgroundColor)
            }

            val layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            layoutParams.topToTop = ConstraintSet.PARENT_ID
            layoutParams.bottomToBottom = ConstraintSet.PARENT_ID
            layoutParams.startToStart = ConstraintSet.PARENT_ID
            layoutParams.endToEnd = ConstraintSet.PARENT_ID

            addView(errorState, layoutParams)
        } else {
            errorState!!.visibility = View.VISIBLE
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)

        if (child.tag == null || child.tag != LOADING &&
            child.tag != EMPTY && child.tag != ERROR
        ) {

            contentViews.add(child)
        }
    }

    override fun getCurrentState(): String {
        return state
    }

    override fun isContentCurrentState(): Boolean {
        return state == CONTENT
    }

    override fun isLoadingCurrentState(): Boolean {
        return state == LOADING
    }

    override fun isEmptyCurrentState(): Boolean {
        return state == EMPTY
    }

    override fun isErrorCurrentState(): Boolean {
        return state == ERROR
    }
}
