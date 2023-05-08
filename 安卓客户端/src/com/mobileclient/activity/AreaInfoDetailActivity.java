package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.AreaInfo;
import com.mobileclient.service.AreaInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class AreaInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_areaId;
	// 声明区域名称控件
	private TextView TV_areaName;
	/* 要保存的区域信息信息 */
	AreaInfo areaInfo = new AreaInfo(); 
	/* 区域信息管理业务逻辑层 */
	private AreaInfoService areaInfoService = new AreaInfoService();
	private int areaId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.areainfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看区域信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_areaId = (TextView) findViewById(R.id.TV_areaId);
		TV_areaName = (TextView) findViewById(R.id.TV_areaName);
		Bundle extras = this.getIntent().getExtras();
		areaId = extras.getInt("areaId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AreaInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    areaInfo = areaInfoService.GetAreaInfo(areaId); 
		this.TV_areaId.setText(areaInfo.getAreaId() + "");
		this.TV_areaName.setText(areaInfo.getAreaName());
	} 
}
