package com.example.pathdemo;

import org.ixming.android.inject.InjectorUtils;
import org.ixming.android.inject.annotation.ViewInject;

import com.example.pathdemo.view.ComplexDrawingView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ComplexPathDrawingActivity extends Activity {

	@ViewInject(id = R.id.complex)
	ComplexDrawingView offset;
	@ViewInject(id = R.id.btn1)
	Button btn1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complex);
		
		InjectorUtils.defaultInstance().inject(this);
		
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				offset.clear();
			}
		});
	}
	
}
