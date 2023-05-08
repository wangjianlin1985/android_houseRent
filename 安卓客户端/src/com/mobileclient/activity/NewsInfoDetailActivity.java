package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.NewsInfo;
import com.mobileclient.service.NewsInfoService;
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
public class NewsInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_newsId;
	// 声明标题控件
	private TextView TV_newsTitle;
	// 声明新闻内容控件
	private TextView TV_newsContent;
	// 声明发布日期控件
	private TextView TV_newsDate;
	/* 要保存的新闻公告信息 */
	NewsInfo newsInfo = new NewsInfo(); 
	/* 新闻公告管理业务逻辑层 */
	private NewsInfoService newsInfoService = new NewsInfoService();
	private int newsId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.newsinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看新闻公告详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_newsId = (TextView) findViewById(R.id.TV_newsId);
		TV_newsTitle = (TextView) findViewById(R.id.TV_newsTitle);
		TV_newsContent = (TextView) findViewById(R.id.TV_newsContent);
		TV_newsDate = (TextView) findViewById(R.id.TV_newsDate);
		Bundle extras = this.getIntent().getExtras();
		newsId = extras.getInt("newsId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NewsInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    newsInfo = newsInfoService.GetNewsInfo(newsId); 
		this.TV_newsId.setText(newsInfo.getNewsId() + "");
		this.TV_newsTitle.setText(newsInfo.getNewsTitle());
		this.TV_newsContent.setText(newsInfo.getNewsContent());
		Date newsDate = new Date(newsInfo.getNewsDate().getTime());
		String newsDateStr = (newsDate.getYear() + 1900) + "-" + (newsDate.getMonth()+1) + "-" + newsDate.getDate();
		this.TV_newsDate.setText(newsDateStr);
	} 
}
