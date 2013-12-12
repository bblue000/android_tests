package com.example.pathdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ComplexDrawingView extends View {
	
	private static final SLNode EMPTY = null;
	private class SLNode {
		SLNode next;
		float x;
		float y;
		
		public SLNode(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		@SuppressWarnings("unused")
		public SLNode(float x, float y, SLNode next) {
			this(x, y);
			this.next = next;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof SLNode)) {
				return false;
			}
			SLNode another = (SLNode) o;
			return x == another.x && y == another.y;
		}
		
		@Override
		public String toString() {
			return "x = " + x + ", y = " + y;
		}
	}
	
	private boolean mIsFillingState = false;
	private SLNode mStartNode = EMPTY;
	private Path mPath = new Path();
	private Paint mPaint_Normal = new Paint();
	private Paint mPaint_Fill = new Paint();
	public ComplexDrawingView(Context context) {
		super(context);
		init();
	}
	
	public ComplexDrawingView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ComplexDrawingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		setFocusable(true);
		
		mPaint_Normal.setAntiAlias(true);
		mPaint_Normal.setStyle(Style.STROKE);
		mPaint_Normal.setStrokeWidth(5);
		mPaint_Normal.setColor(Color.RED);
		
		mPaint_Normal.setPathEffect(new CornerPathEffect(5));
		
		mPaint_Fill.setAntiAlias(true);
		mPaint_Fill.setStyle(Style.FILL);
		mPaint_Fill.setColor(0x55FFFFFF);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.i("yytest", "onDraw");
		Log.d("yytest", "onDraw mStartNode: " + mStartNode);
		canvas.drawColor(0xFFE3A869);
		if (mAnimating) {
			Log.d("yytest", "onDraw is animating");
			canvas.drawPath(mPath, mPaint_Normal);
		} else {
			// 绘制路线
			rebuildPath();
			canvas.drawPath(mPath, mIsFillingState ? mPaint_Fill : mPaint_Normal);
		}
	}
	
	private void rebuildPath() {
		if (EMPTY == mStartNode) {
			return ;
		}
		mPath.reset();
		// first point
		mPath.moveTo(mStartNode.x, mStartNode.y);
		SLNode tmp = mStartNode;
		for (;;) {
			tmp = tmp.next;
			if (null == tmp) {
				break;
			}
			mPath.lineTo(tmp.x, tmp.y);
		}
		if (mIsFillingState) {
			mPath.lineTo(mStartNode.x, mStartNode.y);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mIsFillingState) {
			return false;
		}
		final float x = event.getX();
		final float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mStartNode = new SLNode(x, y);
			break;
		case MotionEvent.ACTION_MOVE: {
			SLNode cur = mStartNode;
			for (; null != cur;) {
				SLNode next = cur.next;
				if (null == next) {
					cur.next = new SLNode(x, y);
					break;
				}
				cur = next;
			}
			break;
		}
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL: {
			SLNode cur = mStartNode;
			for (; null != cur;) {
				SLNode next = cur.next;
				if (null == next) {
					cur.next = new SLNode(x, y);
					break;
				}
				cur = next;
			}
			mIsFillingState = true;
			break;
		}
		}
		//TODO 重新绘制
		invalidate();
		return true;
	}
	
	@Override
	public void computeScroll() {
		Log.i("yytest", "computeScroll");
	}
	
	public void clear() {
		if (mAnimating) {
			mAnimating = false;
			removeCallbacks(mAnuimRun);
			mPath.reset();
		}
		
		if (mIsFillingState) {
			mIsFillingState = false;
		}
		
		mStartNode = EMPTY;
		mPath.reset();
		postInvalidate();
	}
	
	public void repeatPath() {
		if (EMPTY != mStartNode) {
			mAnimating = true;
			mPath.reset();
			mPath.moveTo(mStartNode.x, mStartNode.y);
			post(mAnuimRun = new RepeatPathRun());
		}
	}

	private boolean mAnimating = false;
	private Runnable mAnuimRun;
	private class RepeatPathRun implements Runnable {
		private SLNode cur;
		public RepeatPathRun() {
			cur = mStartNode.next;
		}
		
		@Override
		public void run() {
			if (null == cur) {
				invalidate();
				return ;
			}
			mPath.lineTo(cur.x, cur.y);
			invalidate();
			cur = cur.next;
			postDelayed(this, 50L);
		}
	}
}
