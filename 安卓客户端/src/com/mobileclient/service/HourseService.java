package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Hourse;
import com.mobileclient.util.HttpUtil;

/*房屋信息管理业务逻辑层*/
public class HourseService {
	/* 添加房屋信息 */
	public String AddHourse(Hourse hourse) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("hourseId", hourse.getHourseId() + "");
		params.put("hourseName", hourse.getHourseName());
		params.put("buildingObj", hourse.getBuildingObj() + "");
		params.put("housePhoto", hourse.getHousePhoto());
		params.put("hourseTypeObj", hourse.getHourseTypeObj() + "");
		params.put("priceRangeObj", hourse.getPriceRangeObj() + "");
		params.put("area", hourse.getArea());
		params.put("price", hourse.getPrice() + "");
		params.put("louceng", hourse.getLouceng());
		params.put("zhuangxiu", hourse.getZhuangxiu());
		params.put("caoxiang", hourse.getCaoxiang());
		params.put("madeYear", hourse.getMadeYear());
		params.put("connectPerson", hourse.getConnectPerson());
		params.put("connectPhone", hourse.getConnectPhone());
		params.put("detail", hourse.getDetail());
		params.put("address", hourse.getAddress());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HourseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询房屋信息 */
	public List<Hourse> QueryHourse(Hourse queryConditionHourse) throws Exception {
		String urlString = HttpUtil.BASE_URL + "HourseServlet?action=query";
		if(queryConditionHourse != null) {
			urlString += "&hourseName=" + URLEncoder.encode(queryConditionHourse.getHourseName(), "UTF-8") + "";
			urlString += "&buildingObj=" + queryConditionHourse.getBuildingObj();
			urlString += "&hourseTypeObj=" + queryConditionHourse.getHourseTypeObj();
			urlString += "&priceRangeObj=" + queryConditionHourse.getPriceRangeObj();
			urlString += "&madeYear=" + URLEncoder.encode(queryConditionHourse.getMadeYear(), "UTF-8") + "";
			urlString += "&connectPerson=" + URLEncoder.encode(queryConditionHourse.getConnectPerson(), "UTF-8") + "";
			urlString += "&connectPhone=" + URLEncoder.encode(queryConditionHourse.getConnectPhone(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		HourseListHandler hourseListHander = new HourseListHandler();
		xr.setContentHandler(hourseListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Hourse> hourseList = hourseListHander.getHourseList();
		return hourseList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Hourse> hourseList = new ArrayList<Hourse>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Hourse hourse = new Hourse();
				hourse.setHourseId(object.getInt("hourseId"));
				hourse.setHourseName(object.getString("hourseName"));
				hourse.setBuildingObj(object.getInt("buildingObj"));
				hourse.setHousePhoto(object.getString("housePhoto"));
				hourse.setHourseTypeObj(object.getInt("hourseTypeObj"));
				hourse.setPriceRangeObj(object.getInt("priceRangeObj"));
				hourse.setArea(object.getString("area"));
				hourse.setPrice((float) object.getDouble("price"));
				hourse.setLouceng(object.getString("louceng"));
				hourse.setZhuangxiu(object.getString("zhuangxiu"));
				hourse.setCaoxiang(object.getString("caoxiang"));
				hourse.setMadeYear(object.getString("madeYear"));
				hourse.setConnectPerson(object.getString("connectPerson"));
				hourse.setConnectPhone(object.getString("connectPhone"));
				hourse.setDetail(object.getString("detail"));
				hourse.setAddress(object.getString("address"));
				hourseList.add(hourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hourseList;
	}

	/* 更新房屋信息 */
	public String UpdateHourse(Hourse hourse) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("hourseId", hourse.getHourseId() + "");
		params.put("hourseName", hourse.getHourseName());
		params.put("buildingObj", hourse.getBuildingObj() + "");
		params.put("housePhoto", hourse.getHousePhoto());
		params.put("hourseTypeObj", hourse.getHourseTypeObj() + "");
		params.put("priceRangeObj", hourse.getPriceRangeObj() + "");
		params.put("area", hourse.getArea());
		params.put("price", hourse.getPrice() + "");
		params.put("louceng", hourse.getLouceng());
		params.put("zhuangxiu", hourse.getZhuangxiu());
		params.put("caoxiang", hourse.getCaoxiang());
		params.put("madeYear", hourse.getMadeYear());
		params.put("connectPerson", hourse.getConnectPerson());
		params.put("connectPhone", hourse.getConnectPhone());
		params.put("detail", hourse.getDetail());
		params.put("address", hourse.getAddress());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HourseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除房屋信息 */
	public String DeleteHourse(int hourseId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("hourseId", hourseId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HourseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "房屋信息信息删除失败!";
		}
	}

	/* 根据房屋编号获取房屋信息对象 */
	public Hourse GetHourse(int hourseId)  {
		List<Hourse> hourseList = new ArrayList<Hourse>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("hourseId", hourseId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HourseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Hourse hourse = new Hourse();
				hourse.setHourseId(object.getInt("hourseId"));
				hourse.setHourseName(object.getString("hourseName"));
				hourse.setBuildingObj(object.getInt("buildingObj"));
				hourse.setHousePhoto(object.getString("housePhoto"));
				hourse.setHourseTypeObj(object.getInt("hourseTypeObj"));
				hourse.setPriceRangeObj(object.getInt("priceRangeObj"));
				hourse.setArea(object.getString("area"));
				hourse.setPrice((float) object.getDouble("price"));
				hourse.setLouceng(object.getString("louceng"));
				hourse.setZhuangxiu(object.getString("zhuangxiu"));
				hourse.setCaoxiang(object.getString("caoxiang"));
				hourse.setMadeYear(object.getString("madeYear"));
				hourse.setConnectPerson(object.getString("connectPerson"));
				hourse.setConnectPhone(object.getString("connectPhone"));
				hourse.setDetail(object.getString("detail"));
				hourse.setAddress(object.getString("address"));
				hourseList.add(hourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = hourseList.size();
		if(size>0) return hourseList.get(0); 
		else return null; 
	}
}
