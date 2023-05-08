package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Hourse;
import com.mobileclient.domain.BuildingInfo;
import com.mobileclient.service.BuildingInfoService;
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
public class HourseQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明房屋名称输入框
	private EditText ET_hourseName;
	// 声明所在楼盘下拉框
	private Spinner spinner_buildingObj;
	private ArrayAdapter<String> buildingObj_adapter;
	private static  String[] buildingObj_ShowText  = null;
	private List<BuildingInfo> buildingInfoList = null; 
	/*楼盘信息管理业务逻辑层*/
	private BuildingInfoService buildingInfoService = new BuildingInfoService();
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
	// 声明建筑年代输入框
	private EditText ET_madeYear;
	// 声明联系人输入框
	private EditText ET_connectPerson;
	// 声明联系电话输入框
	private EditText ET_connectPhone;
	/*查询过滤条件保存到这个对象中*/
	private Hourse queryConditionHourse = new Hourse();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.hourse_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置房屋信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_hourseName = (EditText) findViewById(R.id.ET_hourseName);
		spinner_buildingObj = (Spinner) findViewById(R.id.Spinner_buildingObj);
		// 获取所有的楼盘信息
		try {
			buildingInfoList = buildingInfoService.QueryBuildingInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int buildingInfoCount = buildingInfoList.size();
		buildingObj_ShowText = new String[buildingInfoCount+1];
		buildingObj_ShowText[0] = "不限制";
		for(int i=1;i<=buildingInfoCount;i++) { 
			buildingObj_ShowText[i] = buildingInfoList.get(i-1).getBuildingName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		buildingObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, buildingObj_ShowText);
		// 设置所在楼盘下拉列表的风格
		buildingObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_buildingObj.setAdapter(buildingObj_adapter);
		// 添加事件Spinner事件监听
		spinner_buildingObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionHourse.setBuildingObj(buildingInfoList.get(arg2-1).getBuildingId()); 
				else
					queryConditionHourse.setBuildingObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_buildingObj.setVisibility(View.VISIBLE);
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
					queryConditionHourse.setHourseTypeObj(hourseTypeList.get(arg2-1).getTypeId()); 
				else
					queryConditionHourse.setHourseTypeObj(0);
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
					queryConditionHourse.setPriceRangeObj(priceRangeList.get(arg2-1).getRangeId()); 
				else
					queryConditionHourse.setPriceRangeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_priceRangeObj.setVisibility(View.VISIBLE);
		ET_madeYear = (EditText) findViewById(R.id.ET_madeYear);
		ET_connectPerson = (EditText) findViewById(R.id.ET_connectPerson);
		ET_connectPhone = (EditText) findViewById(R.id.ET_connectPhone);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionHourse.setHourseName(ET_hourseName.getText().toString());
					queryConditionHourse.setMadeYear(ET_madeYear.getText().toString());
					queryConditionHourse.setConnectPerson(ET_connectPerson.getText().toString());
					queryConditionHourse.setConnectPhone(ET_connectPhone.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionHourse", queryConditionHourse);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
