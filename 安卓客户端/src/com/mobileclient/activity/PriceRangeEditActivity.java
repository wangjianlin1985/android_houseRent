package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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

public class PriceRangeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_rangeId;
	// 声明价格区间输入框
	private EditText ET_priceName;
	protected String carmera_path;
	/*要保存的租金范围信息*/
	PriceRange priceRange = new PriceRange();
	/*租金范围管理业务逻辑层*/
	private PriceRangeService priceRangeService = new PriceRangeService();

	private int rangeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.pricerange_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑租金范围信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_rangeId = (TextView) findViewById(R.id.TV_rangeId);
		ET_priceName = (EditText) findViewById(R.id.ET_priceName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		rangeId = extras.getInt("rangeId");
		/*单击修改租金范围按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取价格区间*/ 
					if(ET_priceName.getText().toString().equals("")) {
						Toast.makeText(PriceRangeEditActivity.this, "价格区间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_priceName.setFocusable(true);
						ET_priceName.requestFocus();
						return;	
					}
					priceRange.setPriceName(ET_priceName.getText().toString());
					/*调用业务逻辑层上传租金范围信息*/
					PriceRangeEditActivity.this.setTitle("正在更新租金范围信息，稍等...");
					String result = priceRangeService.UpdatePriceRange(priceRange);
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
	    priceRange = priceRangeService.GetPriceRange(rangeId);
		this.TV_rangeId.setText(rangeId+"");
		this.ET_priceName.setText(priceRange.getPriceName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
