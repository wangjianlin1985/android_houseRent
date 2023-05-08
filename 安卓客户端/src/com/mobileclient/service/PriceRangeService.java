package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.PriceRange;
import com.mobileclient.util.HttpUtil;

/*租金范围管理业务逻辑层*/
public class PriceRangeService {
	/* 添加租金范围 */
	public String AddPriceRange(PriceRange priceRange) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("rangeId", priceRange.getRangeId() + "");
		params.put("priceName", priceRange.getPriceName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PriceRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询租金范围 */
	public List<PriceRange> QueryPriceRange(PriceRange queryConditionPriceRange) throws Exception {
		String urlString = HttpUtil.BASE_URL + "PriceRangeServlet?action=query";
		if(queryConditionPriceRange != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		PriceRangeListHandler priceRangeListHander = new PriceRangeListHandler();
		xr.setContentHandler(priceRangeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<PriceRange> priceRangeList = priceRangeListHander.getPriceRangeList();
		return priceRangeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<PriceRange> priceRangeList = new ArrayList<PriceRange>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				PriceRange priceRange = new PriceRange();
				priceRange.setRangeId(object.getInt("rangeId"));
				priceRange.setPriceName(object.getString("priceName"));
				priceRangeList.add(priceRange);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return priceRangeList;
	}

	/* 更新租金范围 */
	public String UpdatePriceRange(PriceRange priceRange) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("rangeId", priceRange.getRangeId() + "");
		params.put("priceName", priceRange.getPriceName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PriceRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除租金范围 */
	public String DeletePriceRange(int rangeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("rangeId", rangeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PriceRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "租金范围信息删除失败!";
		}
	}

	/* 根据记录编号获取租金范围对象 */
	public PriceRange GetPriceRange(int rangeId)  {
		List<PriceRange> priceRangeList = new ArrayList<PriceRange>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("rangeId", rangeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PriceRangeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				PriceRange priceRange = new PriceRange();
				priceRange.setRangeId(object.getInt("rangeId"));
				priceRange.setPriceName(object.getString("priceName"));
				priceRangeList.add(priceRange);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = priceRangeList.size();
		if(size>0) return priceRangeList.get(0); 
		else return null; 
	}
}
