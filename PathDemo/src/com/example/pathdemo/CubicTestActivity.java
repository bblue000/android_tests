package com.example.pathdemo;

import android.app.Activity;
import android.os.Bundle;

import com.example.pathdemo.view.CubicToSurfaceView;

/**
 * 按照三次方程式的曲线，要提供四个点；——S形状曲线
 * 
 * 中间两个点事控制曲线伸展方向的。
 * 
 * @author Yin Yong
 *
 */
public class CubicTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new CubicToSurfaceView(this));
	}
	
}
