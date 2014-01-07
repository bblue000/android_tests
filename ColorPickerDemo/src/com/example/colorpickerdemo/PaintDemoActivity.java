package com.example.colorpickerdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PaintDemoActivity extends Activity {

	Context context;
	private TextView tvText;

	private ColorPickerDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
	}

	/**
	 * 初始化UI
	 */
	private void initViews() {
		tvText = (TextView) findViewById(R.id.tv);
		tvText.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				dialog = new ColorPickerDialog(context, tvText.getTextColors()
						.getDefaultColor(), getResources().getString(
						R.string.app_name),
						new ColorPickerDialog.OnColorChangedListener() {

							public void colorChanged(int color) {
								tvText.setTextColor(color);
							}
						});
				dialog.show();
			}
		});

		tvText = (TextView) findViewById(R.id.tv);
	}
}