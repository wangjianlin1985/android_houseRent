package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.WantHourseInfo;
import com.mobileclient.util.HttpUtil;

/*求租信息管理业务逻辑层*/
public class WantHourseInfoService {
	/* 添加求租信息 */
	public String AddWantHourseInfo(WantHourseInfo wantHourseInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("wantHourseId", wantHourseInfo.getWantHourseId() + "");
		params.put("userObj", wantHourseInfo.getUserObj());
		params.put("title", wantHourseInfo.getTitle());
		params.put("position", wantHourseInfo.getPosition() + "");
		params.put("hourseTypeObj", wantHourseInfo.getHourseTypeObj() + "");
		params.put("priceRangeObj", wantHourseInfo.getPriceRangeObj() + "");
		params.put("price", wantHourseInfo.getPrice() + "");
		params.put("lianxiren", wantHourseInfo.getLianxiren());
		params.put("telephone", wantHourseInfo.getTelephone());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "WantHourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询求租信息 */
	public List<WantHourseInfo> QueryWantHourseInfo(WantHourseInfo queryConditionWantHourseInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "WantHourseInfoServlet?action=query";
		if(queryConditionWantHourseInfo != null) {
			urlString += "&userObj=" + URLEncoder.encode(queryConditionWantHourseInfo.getUserObj(), "UTF-8") + "";
			urlString += "&title=" + URLEncoder.encode(queryConditionWantHourseInfo.getTitle(), "UTF-8") + "";
			urlString += "&position=" + queryConditionWantHourseInfo.getPosition();
			urlString += "&hourseTypeObj=" + queryConditionWantHourseInfo.getHourseTypeObj();
			urlString += "&priceRangeObj=" + queryConditionWantHourseInfo.getPriceRangeObj();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		WantHourseInfoListHandler wantHourseInfoListHander = new WantHourseInfoListHandler();
		xr.setContentHandler(wantHourseInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<WantHourseInfo> wantHourseInfoList = wantHourseInfoListHander.getWantHourseInfoList();
		return wantHourseInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<WantHourseInfo> wantHourseInfoList = new ArrayList<WantHourseInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				WantHourseInfo wantHourseInfo = new WantHourseInfo();
				wantHourseInfo.setWantHourseId(object.getInt("wantHourseId"));
				wantHourseInfo.setUserObj(object.getString("userObj"));
				wantHourseInfo.setTitle(object.getString("title"));
				wantHourseInfo.setPosition(object.getInt("position"));
				wantHourseInfo.setHourseTypeObj(object.getInt("hourseTypeObj"));
				wantHourseInfo.setPriceRangeObj(object.getInt("priceRangeObj"));
				wantHourseInfo.setPrice((float) object.getDouble("price"));
				wantHourseInfo.setLianxiren(object.getString("lianxiren"));
				wantHourseInfo.setTelephone(object.getString("telephone"));
				wantHourseInfoList.add(wantHourseInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wantHourseInfoList;
	}

	/* 更新求租信息 */
	public String UpdateWantHourseInfo(WantHourseInfo wantHourseInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("wantHourseId", wantHourseInfo.getWantHourseId() + "");
		params.put("userObj", wantHourseInfo.getUserObj());
		params.put("title", wantHourseInfo.getTitle());
		params.put("position", wantHourseInfo.getPosition() + "");
		params.put("hourseTypeObj", wantHourseInfo.getHourseTypeObj() + "");
		params.put("priceRangeObj", wantHourseInfo.getPriceRangeObj() + "");
		params.put("price", wantHourseInfo.getPrice() + "");
		params.put("lianxiren", wantHourseInfo.getLianxiren());
		params.put("telephone", wantHourseInfo.getTelephone());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "WantHourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除求租信息 */
	public String DeleteWantHourseInfo(int wantHourseId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("wantHourseId", wantHourseId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "WantHourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "求租信息信息删除失败!";
		}
	}

	/* 根据记录编号获取求租信息对象 */
	public WantHourseInfo GetWantHourseInfo(int wantHourseId)  {
		List<WantHourseInfo> wantHourseInfoList = new ArrayList<WantHourseInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("wantHourseId", wantHourseId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "WantHourseInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				WantHourseInfo wantHourseInfo = new WantHourseInfo();
				wantHourseInfo.setWantHourseId(object.getInt("wantHourseId"));
				wantHourseInfo.setUserObj(object.getString("userObj"));
				wantHourseInfo.setTitle(object.getString("title"));
				wantHourseInfo.setPosition(object.getInt("position"));
				wantHourseInfo.setHourseTypeObj(object.getInt("hourseTypeObj"));
				wantHourseInfo.setPriceRangeObj(object.getInt("priceRangeObj"));
				wantHourseInfo.setPrice((float) object.getDouble("price"));
				wantHourseInfo.setLianxiren(object.getString("lianxiren"));
				wantHourseInfo.setTelephone(object.getString("telephone"));
				wantHourseInfoList.add(wantHourseInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = wantHourseInfoList.size();
		if(size>0) return wantHourseInfoList.get(0); 
		else return null; 
	}
}
