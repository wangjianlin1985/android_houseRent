package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Hourse;
import com.mobileclient.service.HourseService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.HourseSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class HourseListActivity extends Activity {
	HourseSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int hourseId;
	/* 房屋信息操作业务逻辑层对象 */
	HourseService hourseService = new HourseService();
	/*保存查询参数条件的房屋信息对象*/
	private Hourse queryConditionHourse;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.hourse_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(HourseListActivity.this, HourseQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("房屋信息查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(HourseListActivity.this, HourseAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionHourse = (Hourse)extras.getSerializable("queryConditionHourse");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionHourse = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//在子线程中进行下载数据操作
				list = getDatas();
				//发送消失到handler，通知主线程下载完成
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new HourseSimpleAdapter(HourseListActivity.this, list,
	        					R.layout.hourse_list_item,
	        					new String[] { "hourseName","buildingObj","housePhoto","hourseTypeObj","price","louceng","zhuangxiu","madeYear","connectPerson","connectPhone" },
	        					new int[] { R.id.tv_hourseName,R.id.tv_buildingObj,R.id.iv_housePhoto,R.id.tv_hourseTypeObj,R.id.tv_price,R.id.tv_louceng,R.id.tv_zhuangxiu,R.id.tv_madeYear,R.id.tv_connectPerson,R.id.tv_connectPhone,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(hourseListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int hourseId = Integer.parseInt(list.get(arg2).get("hourseId").toString());
            	Intent intent = new Intent();
            	intent.setClass(HourseListActivity.this, HourseDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("hourseId", hourseId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener hourseListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "编辑房屋信息信息"); 
			menu.add(0, 1, 0, "删除房屋信息信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑房屋信息信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取房屋编号
			hourseId = Integer.parseInt(list.get(position).get("hourseId").toString());
			Intent intent = new Intent();
			intent.setClass(HourseListActivity.this, HourseEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("hourseId", hourseId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除房屋信息信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取房屋编号
			hourseId = Integer.parseInt(list.get(position).get("hourseId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(HourseListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = hourseService.DeleteHourse(hourseId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询房屋信息信息 */
			List<Hourse> hourseList = hourseService.QueryHourse(queryConditionHourse);
			for (int i = 0; i < hourseList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("hourseId",hourseList.get(i).getHourseId());
				map.put("hourseName", hourseList.get(i).getHourseName());
				map.put("buildingObj", hourseList.get(i).getBuildingObj());
				/*byte[] housePhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ hourseList.get(i).getHousePhoto());// 获取图片数据
				BitmapFactory.Options housePhoto_opts = new BitmapFactory.Options();  
				housePhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(housePhoto_data, 0, housePhoto_data.length, housePhoto_opts); 
				housePhoto_opts.inSampleSize = photoListActivity.computeSampleSize(housePhoto_opts, -1, 100*100); 
				housePhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap housePhoto = BitmapFactory.decodeByteArray(housePhoto_data, 0, housePhoto_data.length, housePhoto_opts);
					map.put("housePhoto", housePhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("housePhoto", HttpUtil.BASE_URL+ hourseList.get(i).getHousePhoto());
				map.put("hourseTypeObj", hourseList.get(i).getHourseTypeObj());
				map.put("price", hourseList.get(i).getPrice());
				map.put("louceng", hourseList.get(i).getLouceng());
				map.put("zhuangxiu", hourseList.get(i).getZhuangxiu());
				map.put("madeYear", hourseList.get(i).getMadeYear());
				map.put("connectPerson", hourseList.get(i).getConnectPerson());
				map.put("connectPhone", hourseList.get(i).getConnectPhone());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
