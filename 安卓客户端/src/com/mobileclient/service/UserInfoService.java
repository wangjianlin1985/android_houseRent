package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.UserInfo;
import com.mobileclient.util.HttpUtil;

/*用户信息管理业务逻辑层*/
public class UserInfoService {
	/* 添加用户信息 */
	public String AddUserInfo(UserInfo userInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", userInfo.getUser_name());
		params.put("password", userInfo.getPassword());
		params.put("realName", userInfo.getRealName());
		params.put("sex", userInfo.getSex());
		params.put("birthday", userInfo.getBirthday().toString());
		params.put("cardNumber", userInfo.getCardNumber());
		params.put("city", userInfo.getCity());
		params.put("photo", userInfo.getPhoto());
		params.put("address", userInfo.getAddress());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询用户信息 */
	public List<UserInfo> QueryUserInfo(UserInfo queryConditionUserInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "UserInfoServlet?action=query";
		if(queryConditionUserInfo != null) {
			urlString += "&user_name=" + URLEncoder.encode(queryConditionUserInfo.getUser_name(), "UTF-8") + "";
			urlString += "&realName=" + URLEncoder.encode(queryConditionUserInfo.getRealName(), "UTF-8") + "";
			if(queryConditionUserInfo.getBirthday() != null) {
				urlString += "&birthday=" + URLEncoder.encode(queryConditionUserInfo.getBirthday().toString(), "UTF-8");
			}
			urlString += "&cardNumber=" + URLEncoder.encode(queryConditionUserInfo.getCardNumber(), "UTF-8") + "";
			urlString += "&city=" + URLEncoder.encode(queryConditionUserInfo.getCity(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		UserInfoListHandler userInfoListHander = new UserInfoListHandler();
		xr.setContentHandler(userInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<UserInfo> userInfoList = userInfoListHander.getUserInfoList();
		return userInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				UserInfo userInfo = new UserInfo();
				userInfo.setUser_name(object.getString("user_name"));
				userInfo.setPassword(object.getString("password"));
				userInfo.setRealName(object.getString("realName"));
				userInfo.setSex(object.getString("sex"));
				userInfo.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				userInfo.setCardNumber(object.getString("cardNumber"));
				userInfo.setCity(object.getString("city"));
				userInfo.setPhoto(object.getString("photo"));
				userInfo.setAddress(object.getString("address"));
				userInfoList.add(userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfoList;
	}

	/* 更新用户信息 */
	public String UpdateUserInfo(UserInfo userInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", userInfo.getUser_name());
		params.put("password", userInfo.getPassword());
		params.put("realName", userInfo.getRealName());
		params.put("sex", userInfo.getSex());
		params.put("birthday", userInfo.getBirthday().toString());
		params.put("cardNumber", userInfo.getCardNumber());
		params.put("city", userInfo.getCity());
		params.put("photo", userInfo.getPhoto());
		params.put("address", userInfo.getAddress());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除用户信息 */
	public String DeleteUserInfo(String user_name) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", user_name);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "用户信息信息删除失败!";
		}
	}

	/* 根据用户名获取用户信息对象 */
	public UserInfo GetUserInfo(String user_name)  {
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", user_name);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				UserInfo userInfo = new UserInfo();
				userInfo.setUser_name(object.getString("user_name"));
				userInfo.setPassword(object.getString("password"));
				userInfo.setRealName(object.getString("realName"));
				userInfo.setSex(object.getString("sex"));
				userInfo.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				userInfo.setCardNumber(object.getString("cardNumber"));
				userInfo.setCity(object.getString("city"));
				userInfo.setPhoto(object.getString("photo"));
				userInfo.setAddress(object.getString("address"));
				userInfoList.add(userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = userInfoList.size();
		if(size>0) return userInfoList.get(0); 
		else return null; 
	}
}
