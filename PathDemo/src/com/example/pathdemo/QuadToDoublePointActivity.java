package com.example.pathdemo;

import android.app.Activity;
import android.os.Bundle;

import com.example.pathdemo.view.QuadToDoublePointSurfaceView;

public class QuadToDoublePointActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new QuadToDoublePointSurfaceView(this));
	}
	
}
