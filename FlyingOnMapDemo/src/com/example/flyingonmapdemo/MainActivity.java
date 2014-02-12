package com.example.flyingonmapdemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.CancelableCallback;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;

public class MainActivity extends Activity implements LocationSource,
		AMapLocationListener {

	private final LatLng mOriginal = new LatLng(39.936713, 116.386475);

	private ImageView planeIV;
	private Matrix mPlaneMatrix = new Matrix();

	private MapView mapView;
	private AMap mAMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		planeIV = (ImageView) findViewById(R.id.plane);
		planeIV.setScaleType(ScaleType.MATRIX);
		planeIV.setImageMatrix(mPlaneMatrix);

		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		mAMap = mapView.getMap();

		init();
	}

	private void init() {
		mAMap.getUiSettings().setAllGesturesEnabled(false);
		mAMap.getUiSettings().setZoomGesturesEnabled(true);
		mAMap.getUiSettings().setScrollGesturesEnabled(true);

		mAMap.getUiSettings().setMyLocationButtonEnabled(false);
		mAMap.getUiSettings().setZoomControlsEnabled(false);
		mAMap.getUiSettings().setScaleControlsEnabled(false);
		mAMap.getUiSettings().setCompassEnabled(false);

		mAMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {

			@Override
			public void onCameraChangeFinish(CameraPosition position) {
			}

			@Override
			public void onCameraChange(CameraPosition position) {
			}
		});

		mAMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {

			@Override
			public void onMapLoaded() {
				Log.d("yytest", "onMapLoaded");

				// 自定义系统定位小蓝点
				MyLocationStyle myLocationStyle = new MyLocationStyle();
				myLocationStyle.myLocationIcon(BitmapDescriptorFactory
						.fromResource(R.drawable.find_pin));// 设置小蓝点的图标
				// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
				mAMap.setMyLocationStyle(myLocationStyle);
				mAMap.setLocationSource(MainActivity.this);// 设置定位监听
				mAMap.setMyLocationEnabled(true);
			}
		});

	}
	
	private int i = 0;
	private boolean flag = false;
	private void randomFly() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			private void d() {
				handler.postDelayed(this, 1000L);
			}
			
			@Override
			public void run() {
				LatLng base = mAMap.getCameraPosition().target;
				LatLng target = randomNextLatLng(base);
				boolean s = true;
				if (i % 3 == 0) {
					s = false;
				}
				onFlyTo(target, base, mAMap.getCameraPosition().zoom,
						new CancelableCallback() {
							@Override
							public void onFinish() {
								if (i++ % 3 == 0) {
									LatLng base = mAMap.getCameraPosition().target;
									LatLng target = base;
									float zoom = mAMap.getCameraPosition().zoom;
									if (flag) {
										zoom += -2;
									} else {
										zoom += 2;
									}
									onFlyTo(target, base, zoom, new CancelableCallback() {
										
										@Override
										public void onFinish() {
											d();
										}
										
										@Override
										public void onCancel() {
										}
									});
									flag =  !flag;
								}
							}
							
							@Override
							public void onCancel() {
							}
						});
				if (s) {
					handler.post(this);
				}
			}
		}, 1000L);

	}
	
	private void onFlyTo(LatLng to, LatLng from, float zoom, CancelableCallback callback) {
		changeDirection(to, from);
		mAMap.animateCamera(
				CameraUpdateFactory.newLatLngZoom(to, zoom),
				calculateDuration(to, from), callback);
	}

	private void flyToOriginal(CameraPosition position) {
		mAMap.animateCamera(
				CameraUpdateFactory.newLatLngZoom(mOriginal, position.zoom),
				calculateDuration(position.target, mOriginal), null);
	}

	private LatLng randomNextLatLng(LatLng src) {
		double d1;
		double d2;
		double d3;
		double d4;
		if (src == null) {
			d1 = 0.0D;
		} else {
			d1 = src.longitude;
		}
		// 在南北纬60度之内
		d2 = 60D * Math.random();
		d3 = 10.0D * Math.random();
		if (d1 >= 0.0D) {
			d4 = d1 - d3;
		} else {
			d4 = d1 + d3;
		}
		return new LatLng(d2, d4);
	}

	private void changeDirection(LatLng to, LatLng from) {
		double d1 = to.longitude - from.longitude;
		mPlaneMatrix.reset();
		if (d1 < 0) {
			float[] values = { -1f, 0.0f, planeIV.getWidth(), 0.0f, 1f, 0.0f, 0.0f, 0.0f,
					1.0f };
			mPlaneMatrix.setValues(values);
			// mPlaneMatrix.setRotate(-180, planeIV.getWidth() / 2,
			// planeIV.getHeight() / 2);
		}
		planeIV.setImageMatrix(mPlaneMatrix);
	}

	private int calculateDuration(LatLng to, LatLng from) {
		double d1 = to.longitude - from.longitude;
		double d2 = to.latitude - from.latitude;
		return Math.max(150, (int) (Math.sqrt(d1 * d1 + d2 * d2) / 0.18D));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;

	@Override
	public void activate(OnLocationChangedListener listener) {
		Log.d("yytest", "activate");
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是5000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
			mAMapLocationManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 5000, 10, this);
		}
	}

	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		Log.d("yytest", "onLocationChanged");
		if (mListener != null && location != null) {
			mListener.onLocationChanged(location);// 显示系统小蓝点
			// TODO 启动
			mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					location.getLatitude(), location.getLongitude()), 0));// 设置当前地图显示为北京市恭王府

			randomFly();

			deactivate();
		}
	}

}
