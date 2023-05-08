package com.mobileclient.activity;

import java.util.Date;
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
public class WantHourseInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_wantHourseId;
	// 声明求租用户控件
	private TextView TV_userObj;
	// 声明标题控件
	private TextView TV_title;
	// 声明求租区域控件
	private TextView TV_position;
	// 声明房屋类型控件
	private TextView TV_hourseTypeObj;
	// 声明价格范围控件
	private TextView TV_priceRangeObj;
	// 声明最高能出租金控件
	private TextView TV_price;
	// 声明联系人控件
	private TextView TV_lianxiren;
	// 声明联系电话控件
	private TextView TV_telephone;
	/* 要保存的求租信息信息 */
	WantHourseInfo wantHourseInfo = new WantHourseInfo(); 
	/* 求租信息管理业务逻辑层 */
	private WantHourseInfoService wantHourseInfoService = new WantHourseInfoService();
	private UserInfoService userInfoService = new UserInfoService();
	private AreaInfoService areaInfoService = new AreaInfoService();
	private HourseTypeService hourseTypeService = new HourseTypeService();
	private PriceRangeService priceRangeService = new PriceRangeService();
	private int wantHourseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.wanthourseinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看求租信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_wantHourseId = (TextView) findViewById(R.id.TV_wantHourseId);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_position = (TextView) findViewById(R.id.TV_position);
		TV_hourseTypeObj = (TextView) findViewById(R.id.TV_hourseTypeObj);
		TV_priceRangeObj = (TextView) findViewById(R.id.TV_priceRangeObj);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_lianxiren = (TextView) findViewById(R.id.TV_lianxiren);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		Bundle extras = this.getIntent().getExtras();
		wantHourseId = extras.getInt("wantHourseId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				WantHourseInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    wantHourseInfo = wantHourseInfoService.GetWantHourseInfo(wantHourseId); 
		this.TV_wantHourseId.setText(wantHourseInfo.getWantHourseId() + "");
		UserInfo userObj = userInfoService.GetUserInfo(wantHourseInfo.getUserObj());
		this.TV_userObj.setText(userObj.getRealName());
		this.TV_title.setText(wantHourseInfo.getTitle());
		AreaInfo position = areaInfoService.GetAreaInfo(wantHourseInfo.getPosition());
		this.TV_position.setText(position.getAreaName());
		HourseType hourseTypeObj = hourseTypeService.GetHourseType(wantHourseInfo.getHourseTypeObj());
		this.TV_hourseTypeObj.setText(hourseTypeObj.getTypeName());
		PriceRange priceRangeObj = priceRangeService.GetPriceRange(wantHourseInfo.getPriceRangeObj());
		this.TV_priceRangeObj.setText(priceRangeObj.getPriceName());
		this.TV_price.setText(wantHourseInfo.getPrice() + "");
		this.TV_lianxiren.setText(wantHourseInfo.getLianxiren());
		this.TV_telephone.setText(wantHourseInfo.getTelephone());
	} 
}
