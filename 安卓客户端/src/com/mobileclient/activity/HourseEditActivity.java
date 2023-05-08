package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Hourse;
import com.mobileclient.service.HourseService;
import com.mobileclient.domain.BuildingInfo;
import com.mobileclient.service.BuildingInfoService;
import com.mobileclient.domain.HourseType;
import com.mobileclient.service.HourseTypeService;
import com.mobileclient.domain.PriceRange;
import com.mobileclient.service.PriceRangeService;
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

public class HourseEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明房屋编号TextView
	private TextView TV_hourseId;
	// 声明房屋名称输入框
	private EditText ET_hourseName;
	// 声明所在楼盘下拉框
	private Spinner spinner_buildingObj;
	private ArrayAdapter<String> buildingObj_adapter;
	private static  String[] buildingObj_ShowText  = null;
	private List<BuildingInfo> buildingInfoList = null;
	/*所在楼盘管理业务逻辑层*/
	private BuildingInfoService buildingInfoService = new BuildingInfoService();
	// 声明房屋图片图片框控件
	private ImageView iv_housePhoto;
	private Button btn_housePhoto;
	protected int REQ_CODE_SELECT_IMAGE_housePhoto = 1;
	private int REQ_CODE_CAMERA_housePhoto = 2;
	// 声明房屋类型下拉框
	private Spinner spinner_hourseTypeObj;
	private ArrayAdapter<String> hourseTypeObj_adapter;
	private static  String[] hourseTypeObj_ShowText  = null;
	private List<HourseType> hourseTypeList = null;
	/*房屋类型管理业务逻辑层*/
	private HourseTypeService hourseTypeService = new HourseTypeService();
	// 声明价格范围下拉框
	private Spinner spinner_priceRangeObj;
	private ArrayAdapter<String> priceRangeObj_adapter;
	private static  String[] priceRangeObj_ShowText  = null;
	private List<PriceRange> priceRangeList = null;
	/*价格范围管理业务逻辑层*/
	private PriceRangeService priceRangeService = new PriceRangeService();
	// 声明面积输入框
	private EditText ET_area;
	// 声明租金(元/月)输入框
	private EditText ET_price;
	// 声明楼层/总楼层输入框
	private EditText ET_louceng;
	// 声明装修输入框
	private EditText ET_zhuangxiu;
	// 声明朝向输入框
	private EditText ET_caoxiang;
	// 声明建筑年代输入框
	private EditText ET_madeYear;
	// 声明联系人输入框
	private EditText ET_connectPerson;
	// 声明联系电话输入框
	private EditText ET_connectPhone;
	// 声明详细信息输入框
	private EditText ET_detail;
	// 声明地址输入框
	private EditText ET_address;
	protected String carmera_path;
	/*要保存的房屋信息信息*/
	Hourse hourse = new Hourse();
	/*房屋信息管理业务逻辑层*/
	private HourseService hourseService = new HourseService();

	private int hourseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.hourse_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑房屋信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_hourseId = (TextView) findViewById(R.id.TV_hourseId);
		ET_hourseName = (EditText) findViewById(R.id.ET_hourseName);
		spinner_buildingObj = (Spinner) findViewById(R.id.Spinner_buildingObj);
		// 获取所有的所在楼盘
		try {
			buildingInfoList = buildingInfoService.QueryBuildingInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int buildingInfoCount = buildingInfoList.size();
		buildingObj_ShowText = new String[buildingInfoCount];
		for(int i=0;i<buildingInfoCount;i++) { 
			buildingObj_ShowText[i] = buildingInfoList.get(i).getBuildingName();
		}
		// 将可选内容与ArrayAdapter连接起来
		buildingObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, buildingObj_ShowText);
		// 设置图书类别下拉列表的风格
		buildingObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_buildingObj.setAdapter(buildingObj_adapter);
		// 添加事件Spinner事件监听
		spinner_buildingObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				hourse.setBuildingObj(buildingInfoList.get(arg2).getBuildingId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_buildingObj.setVisibility(View.VISIBLE);
		iv_housePhoto = (ImageView) findViewById(R.id.iv_housePhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_housePhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HourseEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_housePhoto);
			}
		});
		btn_housePhoto = (Button) findViewById(R.id.btn_housePhoto);
		btn_housePhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_housePhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_housePhoto);  
			}
		});
		spinner_hourseTypeObj = (Spinner) findViewById(R.id.Spinner_hourseTypeObj);
		// 获取所有的房屋类型
		try {
			hourseTypeList = hourseTypeService.QueryHourseType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int hourseTypeCount = hourseTypeList.size();
		hourseTypeObj_ShowText = new String[hourseTypeCount];
		for(int i=0;i<hourseTypeCount;i++) { 
			hourseTypeObj_ShowText[i] = hourseTypeList.get(i).getTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		hourseTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, hourseTypeObj_ShowText);
		// 设置图书类别下拉列表的风格
		hourseTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_hourseTypeObj.setAdapter(hourseTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_hourseTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				hourse.setHourseTypeObj(hourseTypeList.get(arg2).getTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_hourseTypeObj.setVisibility(View.VISIBLE);
		spinner_priceRangeObj = (Spinner) findViewById(R.id.Spinner_priceRangeObj);
		// 获取所有的价格范围
		try {
			priceRangeList = priceRangeService.QueryPriceRange(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int priceRangeCount = priceRangeList.size();
		priceRangeObj_ShowText = new String[priceRangeCount];
		for(int i=0;i<priceRangeCount;i++) { 
			priceRangeObj_ShowText[i] = priceRangeList.get(i).getPriceName();
		}
		// 将可选内容与ArrayAdapter连接起来
		priceRangeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, priceRangeObj_ShowText);
		// 设置图书类别下拉列表的风格
		priceRangeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_priceRangeObj.setAdapter(priceRangeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_priceRangeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				hourse.setPriceRangeObj(priceRangeList.get(arg2).getRangeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_priceRangeObj.setVisibility(View.VISIBLE);
		ET_area = (EditText) findViewById(R.id.ET_area);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_louceng = (EditText) findViewById(R.id.ET_louceng);
		ET_zhuangxiu = (EditText) findViewById(R.id.ET_zhuangxiu);
		ET_caoxiang = (EditText) findViewById(R.id.ET_caoxiang);
		ET_madeYear = (EditText) findViewById(R.id.ET_madeYear);
		ET_connectPerson = (EditText) findViewById(R.id.ET_connectPerson);
		ET_connectPhone = (EditText) findViewById(R.id.ET_connectPhone);
		ET_detail = (EditText) findViewById(R.id.ET_detail);
		ET_address = (EditText) findViewById(R.id.ET_address);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		hourseId = extras.getInt("hourseId");
		/*单击修改房屋信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取房屋名称*/ 
					if(ET_hourseName.getText().toString().equals("")) {
						Toast.makeText(HourseEditActivity.this, "房屋名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_hourseName.setFocusable(true);
						ET_hourseName.requestFocus();
						return;	
					}
					hourse.setHourseName(ET_hourseName.getText().toString());
					if (!hourse.getHousePhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						HourseEditActivity.this.setTitle("正在上传图片，稍等...");
						String housePhoto = HttpUtil.uploadFile(hourse.getHousePhoto());
						HourseEditActivity.this.setTitle("图片上传完毕！");
						hourse.setHousePhoto(housePhoto);
					} 
					/*验证获取面积*/ 
					if(ET_area.getText().toString().equals("")) {
						Toast.makeText(HourseEditActivity.this, "面积输入不能为空!", Toast.LENGTH_LONG).show();
						ET_area.setFocusable(true);
						ET_area.requestFocus();
						return;	
					}
					hourse.setArea(ET_area.getText().toString());
					/*验证获取租金(元/月)*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(HourseEditActivity.this, "租金(元/月)输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					hourse.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*验证获取楼层/总楼层*/ 
					if(ET_louceng.getText().toString().equals("")) {
						Toast.makeText(HourseEditActivity.this, "楼层/总楼层输入不能为空!", Toast.LENGTH_LONG).show();
						ET_louceng.setFocusable(true);
						ET_louceng.requestFocus();
						return;	
					}
					hourse.setLouceng(ET_louceng.getText().toString());
					/*验证获取装修*/ 
					if(ET_zhuangxiu.getText().toString().equals("")) {
						Toast.makeText(HourseEditActivity.this, "装修输入不能为空!", Toast.LENGTH_LONG).show();
						ET_zhuangxiu.setFocusable(true);
						ET_zhuangxiu.requestFocus();
						return;	
					}
					hourse.setZhuangxiu(ET_zhuangxiu.getText().toString());
					/*验证获取朝向*/ 
					if(ET_caoxiang.getText().toString().equals("")) {
						Toast.makeText(HourseEditActivity.this, "朝向输入不能为空!", Toast.LENGTH_LONG).show();
						ET_caoxiang.setFocusable(true);
						ET_caoxiang.requestFocus();
						return;	
					}
					hourse.setCaoxiang(ET_caoxiang.getText().toString());
					/*验证获取建筑年代*/ 
					if(ET_madeYear.getText().toString().equals("")) {
						Toast.makeText(HourseEditActivity.this, "建筑年代输入不能为空!", Toast.LENGTH_LONG).show();
						ET_madeYear.setFocusable(true);
						ET_madeYear.requestFocus();
						return;	
					}
					hourse.setMadeYear(ET_madeYear.getText().toString());
					/*验证获取联系人*/ 
					if(ET_connectPerson.getText().toString().equals("")) {
						Toast.makeText(HourseEditActivity.this, "联系人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_connectPerson.setFocusable(true);
						ET_connectPerson.requestFocus();
						return;	
					}
					hourse.setConnectPerson(ET_connectPerson.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_connectPhone.getText().toString().equals("")) {
						Toast.makeText(HourseEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_connectPhone.setFocusable(true);
						ET_connectPhone.requestFocus();
						return;	
					}
					hourse.setConnectPhone(ET_connectPhone.getText().toString());
					/*验证获取详细信息*/ 
					if(ET_detail.getText().toString().equals("")) {
						Toast.makeText(HourseEditActivity.this, "详细信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_detail.setFocusable(true);
						ET_detail.requestFocus();
						return;	
					}
					hourse.setDetail(ET_detail.getText().toString());
					/*验证获取地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(HourseEditActivity.this, "地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					hourse.setAddress(ET_address.getText().toString());
					/*调用业务逻辑层上传房屋信息信息*/
					HourseEditActivity.this.setTitle("正在更新房屋信息信息，稍等...");
					String result = hourseService.UpdateHourse(hourse);
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
	    hourse = hourseService.GetHourse(hourseId);
		this.TV_hourseId.setText(hourseId+"");
		this.ET_hourseName.setText(hourse.getHourseName());
		for (int i = 0; i < buildingInfoList.size(); i++) {
			if (hourse.getBuildingObj() == buildingInfoList.get(i).getBuildingId()) {
				this.spinner_buildingObj.setSelection(i);
				break;
			}
		}
		byte[] housePhoto_data = null;
		try {
			// 获取图片数据
			housePhoto_data = ImageService.getImage(HttpUtil.BASE_URL + hourse.getHousePhoto());
			Bitmap housePhoto = BitmapFactory.decodeByteArray(housePhoto_data, 0, housePhoto_data.length);
			this.iv_housePhoto.setImageBitmap(housePhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < hourseTypeList.size(); i++) {
			if (hourse.getHourseTypeObj() == hourseTypeList.get(i).getTypeId()) {
				this.spinner_hourseTypeObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < priceRangeList.size(); i++) {
			if (hourse.getPriceRangeObj() == priceRangeList.get(i).getRangeId()) {
				this.spinner_priceRangeObj.setSelection(i);
				break;
			}
		}
		this.ET_area.setText(hourse.getArea());
		this.ET_price.setText(hourse.getPrice() + "");
		this.ET_louceng.setText(hourse.getLouceng());
		this.ET_zhuangxiu.setText(hourse.getZhuangxiu());
		this.ET_caoxiang.setText(hourse.getCaoxiang());
		this.ET_madeYear.setText(hourse.getMadeYear());
		this.ET_connectPerson.setText(hourse.getConnectPerson());
		this.ET_connectPhone.setText(hourse.getConnectPhone());
		this.ET_detail.setText(hourse.getDetail());
		this.ET_address.setText(hourse.getAddress());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_housePhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_housePhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_housePhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_housePhoto.setImageBitmap(booImageBm);
				this.iv_housePhoto.setScaleType(ScaleType.FIT_CENTER);
				this.hourse.setHousePhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_housePhoto && resultCode == Activity.RESULT_OK) {
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
				this.iv_housePhoto.setImageBitmap(bm); 
				this.iv_housePhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			hourse.setHousePhoto(filename); 
		}
	}
}
