package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.NewsInfo;
import com.mobileclient.service.NewsInfoService;
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
public class NewsInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明标题输入框
	private EditText ET_newsTitle;
	// 声明新闻内容输入框
	private EditText ET_newsContent;
	// 出版发布日期控件
	private DatePicker dp_newsDate;
	protected String carmera_path;
	/*要保存的新闻公告信息*/
	NewsInfo newsInfo = new NewsInfo();
	/*新闻公告管理业务逻辑层*/
	private NewsInfoService newsInfoService = new NewsInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.newsinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加新闻公告");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_newsTitle = (EditText) findViewById(R.id.ET_newsTitle);
		ET_newsContent = (EditText) findViewById(R.id.ET_newsContent);
		dp_newsDate = (DatePicker)this.findViewById(R.id.dp_newsDate);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加新闻公告按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取标题*/ 
					if(ET_newsTitle.getText().toString().equals("")) {
						Toast.makeText(NewsInfoAddActivity.this, "标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_newsTitle.setFocusable(true);
						ET_newsTitle.requestFocus();
						return;	
					}
					newsInfo.setNewsTitle(ET_newsTitle.getText().toString());
					/*验证获取新闻内容*/ 
					if(ET_newsContent.getText().toString().equals("")) {
						Toast.makeText(NewsInfoAddActivity.this, "新闻内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_newsContent.setFocusable(true);
						ET_newsContent.requestFocus();
						return;	
					}
					newsInfo.setNewsContent(ET_newsContent.getText().toString());
					/*获取发布日期*/
					Date newsDate = new Date(dp_newsDate.getYear()-1900,dp_newsDate.getMonth(),dp_newsDate.getDayOfMonth());
					newsInfo.setNewsDate(new Timestamp(newsDate.getTime()));
					/*调用业务逻辑层上传新闻公告信息*/
					NewsInfoAddActivity.this.setTitle("正在上传新闻公告信息，稍等...");
					String result = newsInfoService.AddNewsInfo(newsInfo);
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
	}
}
