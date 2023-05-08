package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.WantHourseInfo;
import com.mobileclient.service.WantHourseInfoService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.AreaInfo;
import com.mobileclient.service.AreaInfoService;
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

public class WantHourseInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_wantHourseId;
	// 声明求租用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*求租用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明标题输入框
	private EditText ET_title;
	// 声明求租区域下拉框
	private Spinner spinner_position;
	private ArrayAdapter<String> position_adapter;
	private static  String[] position_ShowText  = null;
	private List<AreaInfo> areaInfoList = null;
	/*求租区域管理业务逻辑层*/
	private AreaInfoService areaInfoService = new AreaInfoService();
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
	// 声明最高能出租金输入框
	private EditText ET_price;
	// 声明联系人输入框
	private EditText ET_lianxiren;
	// 声明联系电话输入框
	private EditText ET_telephone;
	protected String carmera_path;
	/*要保存的求租信息信息*/
	WantHourseInfo wantHourseInfo = new WantHourseInfo();
	/*求租信息管理业务逻辑层*/
	private WantHourseInfoService wantHourseInfoService = new WantHourseInfoService();

	private int wantHourseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.wanthourseinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑求租信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_wantHourseId = (TextView) findViewById(R.id.TV_wantHourseId);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的求租用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getRealName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				wantHourseInfo.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_title = (EditText) findViewById(R.id.ET_title);
		spinner_position = (Spinner) findViewById(R.id.Spinner_position);
		// 获取所有的求租区域
		try {
			areaInfoList = areaInfoService.QueryAreaInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int areaInfoCount = areaInfoList.size();
		position_ShowText = new String[areaInfoCount];
		for(int i=0;i<areaInfoCount;i++) { 
			position_ShowText[i] = areaInfoList.get(i).getAreaName();
		}
		// 将可选内容与ArrayAdapter连接起来
		position_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, position_ShowText);
		// 设置图书类别下拉列表的风格
		position_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_position.setAdapter(position_adapter);
		// 添加事件Spinner事件监听
		spinner_position.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				wantHourseInfo.setPosition(areaInfoList.get(arg2).getAreaId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_position.setVisibility(View.VISIBLE);
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
				wantHourseInfo.setHourseTypeObj(hourseTypeList.get(arg2).getTypeId()); 
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
				wantHourseInfo.setPriceRangeObj(priceRangeList.get(arg2).getRangeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_priceRangeObj.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_lianxiren = (EditText) findViewById(R.id.ET_lianxiren);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		wantHourseId = extras.getInt("wantHourseId");
		/*单击修改求租信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(WantHourseInfoEditActivity.this, "标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					wantHourseInfo.setTitle(ET_title.getText().toString());
					/*验证获取最高能出租金*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(WantHourseInfoEditActivity.this, "最高能出租金输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					wantHourseInfo.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*验证获取联系人*/ 
					if(ET_lianxiren.getText().toString().equals("")) {
						Toast.makeText(WantHourseInfoEditActivity.this, "联系人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_lianxiren.setFocusable(true);
						ET_lianxiren.requestFocus();
						return;	
					}
					wantHourseInfo.setLianxiren(ET_lianxiren.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(WantHourseInfoEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					wantHourseInfo.setTelephone(ET_telephone.getText().toString());
					/*调用业务逻辑层上传求租信息信息*/
					WantHourseInfoEditActivity.this.setTitle("正在更新求租信息信息，稍等...");
					String result = wantHourseInfoService.UpdateWantHourseInfo(wantHourseInfo);
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
	    wantHourseInfo = wantHourseInfoService.GetWantHourseInfo(wantHourseId);
		this.TV_wantHourseId.setText(wantHourseId+"");
		for (int i = 0; i < userInfoList.size(); i++) {
			if (wantHourseInfo.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_title.setText(wantHourseInfo.getTitle());
		for (int i = 0; i < areaInfoList.size(); i++) {
			if (wantHourseInfo.getPosition() == areaInfoList.get(i).getAreaId()) {
				this.spinner_position.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < hourseTypeList.size(); i++) {
			if (wantHourseInfo.getHourseTypeObj() == hourseTypeList.get(i).getTypeId()) {
				this.spinner_hourseTypeObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < priceRangeList.size(); i++) {
			if (wantHourseInfo.getPriceRangeObj() == priceRangeList.get(i).getRangeId()) {
				this.spinner_priceRangeObj.setSelection(i);
				break;
			}
		}
		this.ET_price.setText(wantHourseInfo.getPrice() + "");
		this.ET_lianxiren.setText(wantHourseInfo.getLianxiren());
		this.ET_telephone.setText(wantHourseInfo.getTelephone());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
