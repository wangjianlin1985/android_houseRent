package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.AreaInfoService;
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

public class WantHourseInfoSimpleAdapter extends SimpleAdapter { 
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

    public WantHourseInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.wanthourseinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
	  holder.tv_position = (TextView)convertView.findViewById(R.id.tv_position);
	  holder.tv_hourseTypeObj = (TextView)convertView.findViewById(R.id.tv_hourseTypeObj);
	  holder.tv_priceRangeObj = (TextView)convertView.findViewById(R.id.tv_priceRangeObj);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.tv_lianxiren = (TextView)convertView.findViewById(R.id.tv_lianxiren);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  /*设置各个控件的展示内容*/
	  holder.tv_userObj.setText("求租用户：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getRealName());
	  holder.tv_title.setText("标题：" + mData.get(position).get("title").toString());
	  holder.tv_position.setText("求租区域：" + (new AreaInfoService()).GetAreaInfo(Integer.parseInt(mData.get(position).get("position").toString())).getAreaName());
	  holder.tv_hourseTypeObj.setText("房屋类型：" + (new HourseTypeService()).GetHourseType(Integer.parseInt(mData.get(position).get("hourseTypeObj").toString())).getTypeName());
	  holder.tv_priceRangeObj.setText("价格范围：" + (new PriceRangeService()).GetPriceRange(Integer.parseInt(mData.get(position).get("priceRangeObj").toString())).getPriceName());
	  holder.tv_price.setText("最高能出租金：" + mData.get(position).get("price").toString());
	  holder.tv_lianxiren.setText("联系人：" + mData.get(position).get("lianxiren").toString());
	  holder.tv_telephone.setText("联系电话：" + mData.get(position).get("telephone").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_userObj;
    	TextView tv_title;
    	TextView tv_position;
    	TextView tv_hourseTypeObj;
    	TextView tv_priceRangeObj;
    	TextView tv_price;
    	TextView tv_lianxiren;
    	TextView tv_telephone;
    }
} 
