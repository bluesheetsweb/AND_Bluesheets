package com.bluesheets.ui.chat.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.bluesheets.databinding.CustomChannelMessageHeaderBinding

class CustomChannelMessageHeader @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private var inflater: LayoutInflater? = null
//    private var view: View? = null
    private var binding: CustomChannelMessageHeaderBinding? = null

    init {
        init()
    }

    private fun init() {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CustomChannelMessageHeaderBinding.inflate(inflater!!, this, false)

//        val layoutParams = ConstraintLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        removeAllViews()
//        addView(binding?.root, layoutParams)
    }
}
