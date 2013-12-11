package com.example.pathdemo;

import org.ixming.android.inject.InjectorUtils;
import org.ixming.android.inject.annotation.ViewInject;

import com.example.pathdemo.view.RLineSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RRelatedTestActivity extends Activity {
	/** Called when the activity is first created. */
	
	@ViewInject(id = R.id.rline)
	RLineSurfaceView rline;
	@ViewInject(id = R.id.btn1)
	Button btn1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rline);
		
		InjectorUtils.defaultInstance().inject(this);
		
		
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				rline.toggle();
			}
		});
	}

}
