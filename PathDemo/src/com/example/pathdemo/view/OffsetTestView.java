package com.example.pathdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 路线上的所有点坐标变化Δx,Δy
 * @author Yin Yong
 *
 */
public class OffsetTestView extends View {
	public static int screenW, screenH;
	
	private boolean mIsR = false;
	
	// Path
	private Path path;
	// 为了不影响主画笔，这里绘制贝赛尔曲线单独用一个新画笔
	private Paint paintQ;
	
	public OffsetTestView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		//贝赛尔曲线相关初始化
		path = new Path();
		paintQ = new Paint();
		paintQ.setAntiAlias(true);
		paintQ.setStyle(Style.STROKE);
		paintQ.setStrokeWidth(5);
		paintQ.setColor(Color.WHITE);
	}
	
	@Override
	public void draw(Canvas canvas) {
		Log.d("yytest", "draw");
		super.draw(canvas);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		Log.d("yytest", "dispatchDraw");
		canvas.drawColor(Color.BLACK);
		path.reset();
		path.moveTo(50, 50);
		path.lineTo(200, 250);
		if (mIsR) {
			// 路线上的所有点坐标变化Δx,Δy
			path.offset(100, 150);
			canvas.drawPath(path, paintQ);
		} else {
			canvas.drawPath(path, paintQ);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.d("yytest", "onDraw");
	}
	
	public void toggle() {
		mIsR = !mIsR;
		postInvalidate();
	}
}