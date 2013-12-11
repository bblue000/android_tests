package com.example.pathdemo;

import org.ixming.android.inject.InjectorUtils;
import org.ixming.android.inject.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AMainActivity extends Activity implements OnClickListener{

	@ViewInject(id=R.id.btn1)
	private Button btn1;
	@ViewInject(id=R.id.btn2)
	private Button btn2;
	@ViewInject(id=R.id.btn3)
	private Button btn3;
	@ViewInject(id=R.id.btn4)
	private Button btn4;
	@ViewInject(id=R.id.btn5)
	private Button btn5;
	@ViewInject(id=R.id.btn6)
	private Button btn6;
	@ViewInject(id=R.id.btn7)
	private Button btn7;
	@ViewInject(id=R.id.btn8)
	private Button btn8;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		InjectorUtils.defaultInstance().inject(this);
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			startActivity(new Intent(this, PathEffectActivity.class));
			break;
		case R.id.btn2:
			startActivity(new Intent(this, QuadToActivity.class));
			break;
		case R.id.btn3:
			startActivity(new Intent(this, QuadToDoublePointActivity.class));
			break;
		case R.id.btn4:
			startActivity(new Intent(this, RRelatedTestActivity.class));
			break;
			
			
		case R.id.btn5:
			startActivity(new Intent(this, OffsetTestActivity.class));
			break;
		case R.id.btn6:
			startActivity(new Intent(this, CubicTestActivity.class));
			break;
		case R.id.btn7:
			startActivity(new Intent(this, ComplexPathDrawingActivity.class));
			break;
		case R.id.btn8:
			startActivity(new Intent(this, RRelatedTestActivity.class));
			break;

		default:
			break;
		}
	}

}
