package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.GuestBook;
import com.mobileclient.util.HttpUtil;

/*留言信息管理业务逻辑层*/
public class GuestBookService {
	/* 添加留言信息 */
	public String AddGuestBook(GuestBook guestBook) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("guestBookId", guestBook.getGuestBookId() + "");
		params.put("title", guestBook.getTitle());
		params.put("content", guestBook.getContent());
		params.put("userObj", guestBook.getUserObj());
		params.put("addTime", guestBook.getAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GuestBookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询留言信息 */
	public List<GuestBook> QueryGuestBook(GuestBook queryConditionGuestBook) throws Exception {
		String urlString = HttpUtil.BASE_URL + "GuestBookServlet?action=query";
		if(queryConditionGuestBook != null) {
			urlString += "&title=" + URLEncoder.encode(queryConditionGuestBook.getTitle(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionGuestBook.getUserObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		GuestBookListHandler guestBookListHander = new GuestBookListHandler();
		xr.setContentHandler(guestBookListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<GuestBook> guestBookList = guestBookListHander.getGuestBookList();
		return guestBookList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<GuestBook> guestBookList = new ArrayList<GuestBook>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				GuestBook guestBook = new GuestBook();
				guestBook.setGuestBookId(object.getInt("guestBookId"));
				guestBook.setTitle(object.getString("title"));
				guestBook.setContent(object.getString("content"));
				guestBook.setUserObj(object.getString("userObj"));
				guestBook.setAddTime(object.getString("addTime"));
				guestBookList.add(guestBook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return guestBookList;
	}

	/* 更新留言信息 */
	public String UpdateGuestBook(GuestBook guestBook) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("guestBookId", guestBook.getGuestBookId() + "");
		params.put("title", guestBook.getTitle());
		params.put("content", guestBook.getContent());
		params.put("userObj", guestBook.getUserObj());
		params.put("addTime", guestBook.getAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GuestBookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除留言信息 */
	public String DeleteGuestBook(int guestBookId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("guestBookId", guestBookId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GuestBookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "留言信息信息删除失败!";
		}
	}

	/* 根据记录编号获取留言信息对象 */
	public GuestBook GetGuestBook(int guestBookId)  {
		List<GuestBook> guestBookList = new ArrayList<GuestBook>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("guestBookId", guestBookId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GuestBookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				GuestBook guestBook = new GuestBook();
				guestBook.setGuestBookId(object.getInt("guestBookId"));
				guestBook.setTitle(object.getString("title"));
				guestBook.setContent(object.getString("content"));
				guestBook.setUserObj(object.getString("userObj"));
				guestBook.setAddTime(object.getString("addTime"));
				guestBookList.add(guestBook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = guestBookList.size();
		if(size>0) return guestBookList.get(0); 
		else return null; 
	}
}
