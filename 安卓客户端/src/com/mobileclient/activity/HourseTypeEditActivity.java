package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.HourseType;
import com.mobileclient.service.HourseTypeService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class HourseTypeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明类别编号TextView
	private TextView TV_typeId;
	// 声明房屋类型输入框
	private EditText ET_typeName;
	protected String carmera_path;
	/*要保存的房屋类别信息*/
	HourseType hourseType = new HourseType();
	/*房屋类别管理业务逻辑层*/
	private HourseTypeService hourseTypeService = new HourseTypeService();

	private int typeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.hoursetype_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑房屋类别信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_typeId = (TextView) findViewById(R.id.TV_typeId);
		ET_typeName = (EditText) findViewById(R.id.ET_typeName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		typeId = extras.getInt("typeId");
		/*单击修改房屋类别按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取房屋类型*/ 
					if(ET_typeName.getText().toString().equals("")) {
						Toast.makeText(HourseTypeEditActivity.this, "房屋类型输入不能为空!", Toast.LENGTH_LONG).show();
						ET_typeName.setFocusable(true);
						ET_typeName.requestFocus();
						return;	
					}
					hourseType.setTypeName(ET_typeName.getText().toString());
					/*调用业务逻辑层上传房屋类别信息*/
					HourseTypeEditActivity.this.setTitle("正在更新房屋类别信息，稍等...");
					String result = hourseTypeService.UpdateHourseType(hourseType);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    hourseType = hourseTypeService.GetHourseType(typeId);
		this.TV_typeId.setText(typeId+"");
		this.ET_typeName.setText(hourseType.getTypeName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
