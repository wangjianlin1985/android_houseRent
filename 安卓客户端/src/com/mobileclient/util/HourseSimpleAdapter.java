package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.BuildingInfoService;
import com.mobileclient.service.HourseTypeService;
import com.mobileclient.service.PriceRangeService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class HourseSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public HourseSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.hourse_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_hourseName = (TextView)convertView.findViewById(R.id.tv_hourseName);
	  holder.tv_buildingObj = (TextView)convertView.findViewById(R.id.tv_buildingObj);
	  holder.iv_housePhoto = (ImageView)convertView.findViewById(R.id.iv_housePhoto);
	  holder.tv_hourseTypeObj = (TextView)convertView.findViewById(R.id.tv_hourseTypeObj);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.tv_louceng = (TextView)convertView.findViewById(R.id.tv_louceng);
	  holder.tv_zhuangxiu = (TextView)convertView.findViewById(R.id.tv_zhuangxiu);
	  holder.tv_madeYear = (TextView)convertView.findViewById(R.id.tv_madeYear);
	  holder.tv_connectPerson = (TextView)convertView.findViewById(R.id.tv_connectPerson);
	  holder.tv_connectPhone = (TextView)convertView.findViewById(R.id.tv_connectPhone);
	  /*设置各个控件的展示内容*/
	  holder.tv_hourseName.setText("房屋名称：" + mData.get(position).get("hourseName").toString());
	  holder.tv_buildingObj.setText("所在楼盘：" + (new BuildingInfoService()).GetBuildingInfo(Integer.parseInt(mData.get(position).get("buildingObj").toString())).getBuildingName());
	  holder.iv_housePhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener housePhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_housePhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("housePhoto"),housePhotoLoadListener);  
	  holder.tv_hourseTypeObj.setText("房屋类型：" + (new HourseTypeService()).GetHourseType(Integer.parseInt(mData.get(position).get("hourseTypeObj").toString())).getTypeName());
	  holder.tv_price.setText("租金(元/月)：" + mData.get(position).get("price").toString());
	  holder.tv_louceng.setText("楼层/总楼层：" + mData.get(position).get("louceng").toString());
	  holder.tv_zhuangxiu.setText("装修：" + mData.get(position).get("zhuangxiu").toString());
	  holder.tv_madeYear.setText("建筑年代：" + mData.get(position).get("madeYear").toString());
	  holder.tv_connectPerson.setText("联系人：" + mData.get(position).get("connectPerson").toString());
	  holder.tv_connectPhone.setText("联系电话：" + mData.get(position).get("connectPhone").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_hourseName;
    	TextView tv_buildingObj;
    	ImageView iv_housePhoto;
    	TextView tv_hourseTypeObj;
    	TextView tv_price;
    	TextView tv_louceng;
    	TextView tv_zhuangxiu;
    	TextView tv_madeYear;
    	TextView tv_connectPerson;
    	TextView tv_connectPhone;
    }
} 
