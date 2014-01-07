package com.example.colorpickerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class ColorPicker extends View {

	private Paint mPaint;
	private int[] mColors;
	public ColorPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ColorPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ColorPicker(Context context) {
		super(context);
		init();
	}

	private void init() {
		mColors = new int[] { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF,
                0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };
        Shader s = new SweepGradient(0, 0, mColors, null);
        
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(s);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(55);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.translate(100, 100);
		canvas.drawOval(new RectF(-55, -55, 55, 55), mPaint);
	}
	
}
