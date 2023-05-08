package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Hourse;
import com.mobileclient.service.HourseService;
import com.mobileclient.domain.BuildingInfo;
import com.mobileclient.service.BuildingInfoService;
import com.mobileclient.domain.HourseType;
import com.mobileclient.service.HourseTypeService;
import com.mobileclient.domain.PriceRange;
import com.mobileclient.service.PriceRangeService;
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
public class HourseDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明房屋编号控件
	private TextView TV_hourseId;
	// 声明房屋名称控件
	private TextView TV_hourseName;
	// 声明所在楼盘控件
	private TextView TV_buildingObj;
	// 声明房屋图片图片框
	private ImageView iv_housePhoto;
	// 声明房屋类型控件
	private TextView TV_hourseTypeObj;
	// 声明价格范围控件
	private TextView TV_priceRangeObj;
	// 声明面积控件
	private TextView TV_area;
	// 声明租金(元/月)控件
	private TextView TV_price;
	// 声明楼层/总楼层控件
	private TextView TV_louceng;
	// 声明装修控件
	private TextView TV_zhuangxiu;
	// 声明朝向控件
	private TextView TV_caoxiang;
	// 声明建筑年代控件
	private TextView TV_madeYear;
	// 声明联系人控件
	private TextView TV_connectPerson;
	// 声明联系电话控件
	private TextView TV_connectPhone;
	// 声明详细信息控件
	private TextView TV_detail;
	// 声明地址控件
	private TextView TV_address;
	/* 要保存的房屋信息信息 */
	Hourse hourse = new Hourse(); 
	/* 房屋信息管理业务逻辑层 */
	private HourseService hourseService = new HourseService();
	private BuildingInfoService buildingInfoService = new BuildingInfoService();
	private HourseTypeService hourseTypeService = new HourseTypeService();
	private PriceRangeService priceRangeService = new PriceRangeService();
	private int hourseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.hourse_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看房屋信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_hourseId = (TextView) findViewById(R.id.TV_hourseId);
		TV_hourseName = (TextView) findViewById(R.id.TV_hourseName);
		TV_buildingObj = (TextView) findViewById(R.id.TV_buildingObj);
		iv_housePhoto = (ImageView) findViewById(R.id.iv_housePhoto); 
		TV_hourseTypeObj = (TextView) findViewById(R.id.TV_hourseTypeObj);
		TV_priceRangeObj = (TextView) findViewById(R.id.TV_priceRangeObj);
		TV_area = (TextView) findViewById(R.id.TV_area);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_louceng = (TextView) findViewById(R.id.TV_louceng);
		TV_zhuangxiu = (TextView) findViewById(R.id.TV_zhuangxiu);
		TV_caoxiang = (TextView) findViewById(R.id.TV_caoxiang);
		TV_madeYear = (TextView) findViewById(R.id.TV_madeYear);
		TV_connectPerson = (TextView) findViewById(R.id.TV_connectPerson);
		TV_connectPhone = (TextView) findViewById(R.id.TV_connectPhone);
		TV_detail = (TextView) findViewById(R.id.TV_detail);
		TV_address = (TextView) findViewById(R.id.TV_address);
		Bundle extras = this.getIntent().getExtras();
		hourseId = extras.getInt("hourseId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HourseDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    hourse = hourseService.GetHourse(hourseId); 
		this.TV_hourseId.setText(hourse.getHourseId() + "");
		this.TV_hourseName.setText(hourse.getHourseName());
		BuildingInfo buildingObj = buildingInfoService.GetBuildingInfo(hourse.getBuildingObj());
		this.TV_buildingObj.setText(buildingObj.getBuildingName());
		byte[] housePhoto_data = null;
		try {
			// 获取图片数据
			housePhoto_data = ImageService.getImage(HttpUtil.BASE_URL + hourse.getHousePhoto());
			Bitmap housePhoto = BitmapFactory.decodeByteArray(housePhoto_data, 0,housePhoto_data.length);
			this.iv_housePhoto.setImageBitmap(housePhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HourseType hourseTypeObj = hourseTypeService.GetHourseType(hourse.getHourseTypeObj());
		this.TV_hourseTypeObj.setText(hourseTypeObj.getTypeName());
		PriceRange priceRangeObj = priceRangeService.GetPriceRange(hourse.getPriceRangeObj());
		this.TV_priceRangeObj.setText(priceRangeObj.getPriceName());
		this.TV_area.setText(hourse.getArea());
		this.TV_price.setText(hourse.getPrice() + "");
		this.TV_louceng.setText(hourse.getLouceng());
		this.TV_zhuangxiu.setText(hourse.getZhuangxiu());
		this.TV_caoxiang.setText(hourse.getCaoxiang());
		this.TV_madeYear.setText(hourse.getMadeYear());
		this.TV_connectPerson.setText(hourse.getConnectPerson());
		this.TV_connectPhone.setText(hourse.getConnectPhone());
		this.TV_detail.setText(hourse.getDetail());
		this.TV_address.setText(hourse.getAddress());
	} 
}
