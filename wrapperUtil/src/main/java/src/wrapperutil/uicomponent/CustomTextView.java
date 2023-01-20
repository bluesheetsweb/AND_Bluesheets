package src.wrapperutil.uicomponent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

import src.wrapperutil.utilities.ColorPicker;

public class CustomTextView extends TextView {

    private int mWidth;
    private int mHeight;


    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        Paint mPaint = new Paint();
//        int color = getResources().getColor(DCColorPicker.INSTANCE.getWHITE());
        int color = Color.parseColor(ColorPicker.INSTANCE.getWHITE());
        mPaint.setColor(color);
        Path mPath = new Path();
        mPath.moveTo(.0f, this.getHeight());
        mPath.lineTo(0.8f * this.getWidth(), this.getHeight());
        mPath.lineTo(this.getWidth(), 0.5f * this.getHeight());
        mPath.lineTo(0.8f * this.getWidth(), .0f);
        mPath.lineTo(.0f, .0f);
        mPath.lineTo(.0f, this.getHeight());

        canvas.clipPath(mPath);
        canvas.drawPath(mPath, mPaint);


    }
}