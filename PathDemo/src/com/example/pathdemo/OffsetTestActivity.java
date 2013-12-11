package com.example.pathdemo;

import org.ixming.android.inject.InjectorUtils;
import org.ixming.android.inject.annotation.ViewInject;

import com.example.pathdemo.view.OffsetTestView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 路线上的所有点坐标变化Δx,Δy
 * @author Yin Yong
 *
 */
public class OffsetTestActivity extends Activity {
	/** Called when the activity is first created. */
	
	@ViewInject(id = R.id.offset)
	OffsetTestView offset;
	@ViewInject(id = R.id.btn1)
	Button btn1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offset);
		InjectorUtils.defaultInstance().inject(this);
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				offset.toggle();
			}
		});
	}

}
