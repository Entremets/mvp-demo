package com.example.myapplication.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.R;

public class RoundButton extends AppCompatButton {

    private int borderColor;
    private float borderWidth;
    private Paint borderPaint;

    public RoundButton(Context context) {
        super(context);
        init(null, 0);
    }

    public RoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.RoundButton,
                defStyleAttr,
                0
        );

        borderColor = a.getColor(R.styleable.RoundButton_borderColor, 0xFF000000);
        borderWidth = a.getDimension(R.styleable.RoundButton_borderWidth, 2);

        a.recycle();

        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectF, getHeight() / 2, getHeight() / 2, borderPaint);
    }
}