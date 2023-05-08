package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.AreaInfo;
import com.mobileclient.util.HttpUtil;

/*区域信息管理业务逻辑层*/
public class AreaInfoService {
	/* 添加区域信息 */
	public String AddAreaInfo(AreaInfo areaInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("areaId", areaInfo.getAreaId() + "");
		params.put("areaName", areaInfo.getAreaName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "AreaInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询区域信息 */
	public List<AreaInfo> QueryAreaInfo(AreaInfo queryConditionAreaInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "AreaInfoServlet?action=query";
		if(queryConditionAreaInfo != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		AreaInfoListHandler areaInfoListHander = new AreaInfoListHandler();
		xr.setContentHandler(areaInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<AreaInfo> areaInfoList = areaInfoListHander.getAreaInfoList();
		return areaInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<AreaInfo> areaInfoList = new ArrayList<AreaInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				AreaInfo areaInfo = new AreaInfo();
				areaInfo.setAreaId(object.getInt("areaId"));
				areaInfo.setAreaName(object.getString("areaName"));
				areaInfoList.add(areaInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areaInfoList;
	}

	/* 更新区域信息 */
	public String UpdateAreaInfo(AreaInfo areaInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("areaId", areaInfo.getAreaId() + "");
		params.put("areaName", areaInfo.getAreaName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "AreaInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除区域信息 */
	public String DeleteAreaInfo(int areaId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("areaId", areaId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "AreaInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "区域信息信息删除失败!";
		}
	}

	/* 根据记录编号获取区域信息对象 */
	public AreaInfo GetAreaInfo(int areaId)  {
		List<AreaInfo> areaInfoList = new ArrayList<AreaInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("areaId", areaId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "AreaInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				AreaInfo areaInfo = new AreaInfo();
				areaInfo.setAreaId(object.getInt("areaId"));
				areaInfo.setAreaName(object.getString("areaName"));
				areaInfoList.add(areaInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = areaInfoList.size();
		if(size>0) return areaInfoList.get(0); 
		else return null; 
	}
}
