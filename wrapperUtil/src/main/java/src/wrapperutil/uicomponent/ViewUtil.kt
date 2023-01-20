package src.wrapperutil.uicomponent

import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.Gravity
import android.view.View
import android.view.View.LAYER_TYPE_SOFTWARE
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat

object ViewUtil {

    fun generateBackgroundWithShadow(
        view: View,
        @ColorRes backgroundColor: Int,
        @DimenRes cornerRadius: Int,
        @ColorRes shadowColor: Int,
        @DimenRes elevation: Int,
        shadowGravity: Int
    ): Drawable {
        val cornerRadiusValue = view.getContext().getResources().getDimension(cornerRadius)
        val elevationValue = view.getContext().getResources().getDimension(elevation).toInt()

        val shadowColorValue = ContextCompat.getColor(view.getContext(), shadowColor)
        val backgroundColorValue = ContextCompat.getColor(view.getContext(), backgroundColor)

        val outerRadius = floatArrayOf(cornerRadiusValue, cornerRadiusValue, cornerRadiusValue, cornerRadiusValue, cornerRadiusValue, cornerRadiusValue, cornerRadiusValue, cornerRadiusValue)

        val backgroundPaint = Paint()
        backgroundPaint.setStyle(Paint.Style.FILL)
        backgroundPaint.setShadowLayer(cornerRadiusValue, 0f, 0f, 0)

        val shapeDrawablePadding = Rect()
        shapeDrawablePadding.left = elevationValue
        shapeDrawablePadding.right = elevationValue

        val DY: Int
        when (shadowGravity) {
            Gravity.CENTER -> {
                shapeDrawablePadding.top = elevationValue
                shapeDrawablePadding.bottom = elevationValue
                DY = 0
            }
            Gravity.TOP -> {
                shapeDrawablePadding.top = elevationValue * 2
                shapeDrawablePadding.bottom = elevationValue
                DY = -1 * elevationValue / 3
            }
            Gravity.BOTTOM -> {
                shapeDrawablePadding.top = elevationValue
                shapeDrawablePadding.bottom = elevationValue * 2
                DY = elevationValue / 3
            }
            else -> {
                shapeDrawablePadding.top = elevationValue
                shapeDrawablePadding.bottom = elevationValue * 2
                DY = elevationValue / 3
            }
        }

        val shapeDrawable = ShapeDrawable()
        shapeDrawable.setPadding(shapeDrawablePadding)

        shapeDrawable.paint.color = backgroundColorValue
        shapeDrawable.paint.setShadowLayer(cornerRadiusValue / 3, 0f, DY.toFloat(), shadowColorValue)

        view.setLayerType(LAYER_TYPE_SOFTWARE, shapeDrawable.paint)

        shapeDrawable.shape = RoundRectShape(outerRadius, null, null)

        val drawable = LayerDrawable(arrayOf<Drawable>(shapeDrawable))
        drawable.setLayerInset(0, elevationValue, elevationValue * 2, elevationValue, elevationValue * 2)

        return drawable
    }
}
