package src.wrapperutil.rguicomponent.uiclasses

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatCheckBox
import src.wrapperutil.R

class RGCheckBox : AppCompatCheckBox {

    companion object {
        val TAG = "DcCheckBox"
    }

    var isDisbale = false

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun initView(context: Context) {
        buttonDrawable = null

        Log.e(TAG, "initView")
        // background= ContextCompat.getDrawable(context,R.drawable.checkbox_selector)
        // buttonDrawable=makeSelector()
    }

    fun makeSelector(): StateListDrawable {
        val res = StateListDrawable()
        res.addState(intArrayOf(android.R.attr.state_checked), ColorDrawable(Color.BLACK))
        res.addState(intArrayOf(android.R.attr.state_enabled), ColorDrawable(Color.BLACK))
        res.addState(intArrayOf(-android.R.attr.state_checked), ColorDrawable(Color.BLACK))
        res.addState(intArrayOf(-android.R.attr.state_enabled), ColorDrawable(Color.BLACK))
        // res.addState(intArrayOf(), ColorDrawable(Color.TRANSPARENT))
        return res
    }

    /*override fun setChecked(checked: Boolean) {
        //super.setChecked(checked)
      *//* *//*
        if(checked){
            buttonTintList(R.color.cardview_shadow_start_color)
        }else{
            setBackgroundResource(R.color.cardview_shadow_start_color)
        }
    }
*/

    override fun setChecked(t: Boolean) {
        if (t) {
            buttonDrawable = null

            if (isDisbale) {
                this.setBackgroundResource(R.drawable.ic_empty_checkbox)
            } else {
                this.setBackgroundResource(R.drawable.ic_blue_checkbox)
            }
        } else {
            buttonDrawable = null
            this.setBackgroundResource(R.drawable.ic_empty_checkbox)
        }
        super.setChecked(t)
    }

    fun updateDisableCheckBox() {
        isDisbale = true
        /* setEnabled(false);
         this.setBackgroundResource(R.drawable.ic_toggle_tick)*/
    }

    fun updateDisableCheckBox(isDisbale: Boolean) {
        this.isDisbale = isDisbale
        /* setEnabled(false);
         this.setBackgroundResource(R.drawable.ic_toggle_tick)*/
    }
}
