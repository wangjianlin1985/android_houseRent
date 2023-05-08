package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.BuildingInfo;
import com.mobileclient.service.BuildingInfoService;
import com.mobileclient.domain.AreaInfo;
import com.mobileclient.service.AreaInfoService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class BuildingInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明所在区域下拉框
	private Spinner spinner_areaObj;
	private ArrayAdapter<String> areaObj_adapter;
	private static  String[] areaObj_ShowText  = null;
	private List<AreaInfo> areaInfoList = null;
	/*所在区域管理业务逻辑层*/
	private AreaInfoService areaInfoService = new AreaInfoService();
	// 声明楼盘名称输入框
	private EditText ET_buildingName;
	// 声明楼盘图片图片框控件
	private ImageView iv_buildingPhoto;
	private Button btn_buildingPhoto;
	protected int REQ_CODE_SELECT_IMAGE_buildingPhoto = 1;
	private int REQ_CODE_CAMERA_buildingPhoto = 2;
	protected String carmera_path;
	/*要保存的楼盘信息信息*/
	BuildingInfo buildingInfo = new BuildingInfo();
	/*楼盘信息管理业务逻辑层*/
	private BuildingInfoService buildingInfoService = new BuildingInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.buildinginfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加楼盘信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_areaObj = (Spinner) findViewById(R.id.Spinner_areaObj);
		// 获取所有的所在区域
		try {
			areaInfoList = areaInfoService.QueryAreaInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int areaInfoCount = areaInfoList.size();
		areaObj_ShowText = new String[areaInfoCount];
		for(int i=0;i<areaInfoCount;i++) { 
			areaObj_ShowText[i] = areaInfoList.get(i).getAreaName();
		}
		// 将可选内容与ArrayAdapter连接起来
		areaObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, areaObj_ShowText);
		// 设置下拉列表的风格
		areaObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_areaObj.setAdapter(areaObj_adapter);
		// 添加事件Spinner事件监听
		spinner_areaObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				buildingInfo.setAreaObj(areaInfoList.get(arg2).getAreaId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_areaObj.setVisibility(View.VISIBLE);
		ET_buildingName = (EditText) findViewById(R.id.ET_buildingName);
		iv_buildingPhoto = (ImageView) findViewById(R.id.iv_buildingPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_buildingPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(BuildingInfoAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_buildingPhoto);
			}
		});
		btn_buildingPhoto = (Button) findViewById(R.id.btn_buildingPhoto);
		btn_buildingPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_buildingPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_buildingPhoto);  
			}
		});
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加楼盘信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取楼盘名称*/ 
					if(ET_buildingName.getText().toString().equals("")) {
						Toast.makeText(BuildingInfoAddActivity.this, "楼盘名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_buildingName.setFocusable(true);
						ET_buildingName.requestFocus();
						return;	
					}
					buildingInfo.setBuildingName(ET_buildingName.getText().toString());
					if(buildingInfo.getBuildingPhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						BuildingInfoAddActivity.this.setTitle("正在上传图片，稍等...");
						String buildingPhoto = HttpUtil.uploadFile(buildingInfo.getBuildingPhoto());
						BuildingInfoAddActivity.this.setTitle("图片上传完毕！");
						buildingInfo.setBuildingPhoto(buildingPhoto);
					} else {
						buildingInfo.setBuildingPhoto("upload/noimage.jpg");
					}
					/*调用业务逻辑层上传楼盘信息信息*/
					BuildingInfoAddActivity.this.setTitle("正在上传楼盘信息信息，稍等...");
					String result = buildingInfoService.AddBuildingInfo(buildingInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_buildingPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_buildingPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_buildingPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_buildingPhoto.setImageBitmap(booImageBm);
				this.iv_buildingPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.buildingInfo.setBuildingPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_buildingPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_buildingPhoto.setImageBitmap(bm); 
				this.iv_buildingPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			buildingInfo.setBuildingPhoto(filename); 
		}
	}
}
