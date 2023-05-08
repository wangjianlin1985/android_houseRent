package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.WantHourseInfo;
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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class WantHourseInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明求租用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户信息管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明标题输入框
	private EditText ET_title;
	// 声明求租区域下拉框
	private Spinner spinner_position;
	private ArrayAdapter<String> position_adapter;
	private static  String[] position_ShowText  = null;
	private List<AreaInfo> areaInfoList = null; 
	/*区域信息管理业务逻辑层*/
	private AreaInfoService areaInfoService = new AreaInfoService();
	// 声明房屋类型下拉框
	private Spinner spinner_hourseTypeObj;
	private ArrayAdapter<String> hourseTypeObj_adapter;
	private static  String[] hourseTypeObj_ShowText  = null;
	private List<HourseType> hourseTypeList = null; 
	/*房屋类别管理业务逻辑层*/
	private HourseTypeService hourseTypeService = new HourseTypeService();
	// 声明价格范围下拉框
	private Spinner spinner_priceRangeObj;
	private ArrayAdapter<String> priceRangeObj_adapter;
	private static  String[] priceRangeObj_ShowText  = null;
	private List<PriceRange> priceRangeList = null; 
	/*租金范围管理业务逻辑层*/
	private PriceRangeService priceRangeService = new PriceRangeService();
	/*查询过滤条件保存到这个对象中*/
	private WantHourseInfo queryConditionWantHourseInfo = new WantHourseInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.wanthourseinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置求租信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的用户信息
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getRealName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置求租用户下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionWantHourseInfo.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionWantHourseInfo.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_title = (EditText) findViewById(R.id.ET_title);
		spinner_position = (Spinner) findViewById(R.id.Spinner_position);
		// 获取所有的区域信息
		try {
			areaInfoList = areaInfoService.QueryAreaInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int areaInfoCount = areaInfoList.size();
		position_ShowText = new String[areaInfoCount+1];
		position_ShowText[0] = "不限制";
		for(int i=1;i<=areaInfoCount;i++) { 
			position_ShowText[i] = areaInfoList.get(i-1).getAreaName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		position_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, position_ShowText);
		// 设置求租区域下拉列表的风格
		position_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_position.setAdapter(position_adapter);
		// 添加事件Spinner事件监听
		spinner_position.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionWantHourseInfo.setPosition(areaInfoList.get(arg2-1).getAreaId()); 
				else
					queryConditionWantHourseInfo.setPosition(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_position.setVisibility(View.VISIBLE);
		spinner_hourseTypeObj = (Spinner) findViewById(R.id.Spinner_hourseTypeObj);
		// 获取所有的房屋类别
		try {
			hourseTypeList = hourseTypeService.QueryHourseType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int hourseTypeCount = hourseTypeList.size();
		hourseTypeObj_ShowText = new String[hourseTypeCount+1];
		hourseTypeObj_ShowText[0] = "不限制";
		for(int i=1;i<=hourseTypeCount;i++) { 
			hourseTypeObj_ShowText[i] = hourseTypeList.get(i-1).getTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		hourseTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, hourseTypeObj_ShowText);
		// 设置房屋类型下拉列表的风格
		hourseTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_hourseTypeObj.setAdapter(hourseTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_hourseTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionWantHourseInfo.setHourseTypeObj(hourseTypeList.get(arg2-1).getTypeId()); 
				else
					queryConditionWantHourseInfo.setHourseTypeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_hourseTypeObj.setVisibility(View.VISIBLE);
		spinner_priceRangeObj = (Spinner) findViewById(R.id.Spinner_priceRangeObj);
		// 获取所有的租金范围
		try {
			priceRangeList = priceRangeService.QueryPriceRange(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int priceRangeCount = priceRangeList.size();
		priceRangeObj_ShowText = new String[priceRangeCount+1];
		priceRangeObj_ShowText[0] = "不限制";
		for(int i=1;i<=priceRangeCount;i++) { 
			priceRangeObj_ShowText[i] = priceRangeList.get(i-1).getPriceName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		priceRangeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, priceRangeObj_ShowText);
		// 设置价格范围下拉列表的风格
		priceRangeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_priceRangeObj.setAdapter(priceRangeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_priceRangeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionWantHourseInfo.setPriceRangeObj(priceRangeList.get(arg2-1).getRangeId()); 
				else
					queryConditionWantHourseInfo.setPriceRangeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_priceRangeObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionWantHourseInfo.setTitle(ET_title.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionWantHourseInfo", queryConditionWantHourseInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
