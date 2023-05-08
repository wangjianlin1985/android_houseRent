package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.HourseType;
import com.mobileclient.util.HttpUtil;

/*房屋类别管理业务逻辑层*/
public class HourseTypeService {
	/* 添加房屋类别 */
	public String AddHourseType(HourseType hourseType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", hourseType.getTypeId() + "");
		params.put("typeName", hourseType.getTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HourseTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询房屋类别 */
	public List<HourseType> QueryHourseType(HourseType queryConditionHourseType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "HourseTypeServlet?action=query";
		if(queryConditionHourseType != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		HourseTypeListHandler hourseTypeListHander = new HourseTypeListHandler();
		xr.setContentHandler(hourseTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<HourseType> hourseTypeList = hourseTypeListHander.getHourseTypeList();
		return hourseTypeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<HourseType> hourseTypeList = new ArrayList<HourseType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				HourseType hourseType = new HourseType();
				hourseType.setTypeId(object.getInt("typeId"));
				hourseType.setTypeName(object.getString("typeName"));
				hourseTypeList.add(hourseType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hourseTypeList;
	}

	/* 更新房屋类别 */
	public String UpdateHourseType(HourseType hourseType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", hourseType.getTypeId() + "");
		params.put("typeName", hourseType.getTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HourseTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除房屋类别 */
	public String DeleteHourseType(int typeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HourseTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "房屋类别信息删除失败!";
		}
	}

	/* 根据类别编号获取房屋类别对象 */
	public HourseType GetHourseType(int typeId)  {
		List<HourseType> hourseTypeList = new ArrayList<HourseType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HourseTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				HourseType hourseType = new HourseType();
				hourseType.setTypeId(object.getInt("typeId"));
				hourseType.setTypeName(object.getString("typeName"));
				hourseTypeList.add(hourseType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = hourseTypeList.size();
		if(size>0) return hourseTypeList.get(0); 
		else return null; 
	}
}
