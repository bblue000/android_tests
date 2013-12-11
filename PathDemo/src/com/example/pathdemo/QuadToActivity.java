package com.example.pathdemo;

import com.example.pathdemo.view.QuadToSurfaceView;

import android.app.Activity;
import android.os.Bundle;

/**
 * 赛贝尔曲线——从起始点，根据中间点使用二次方程式，到达目标点
 * @author Yin Yong
 *
 */
public class QuadToActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new QuadToSurfaceView(this));
	}
	
}
