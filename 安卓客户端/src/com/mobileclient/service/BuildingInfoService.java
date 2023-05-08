package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.BuildingInfo;
import com.mobileclient.util.HttpUtil;

/*楼盘信息管理业务逻辑层*/
public class BuildingInfoService {
	/* 添加楼盘信息 */
	public String AddBuildingInfo(BuildingInfo buildingInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("buildingId", buildingInfo.getBuildingId() + "");
		params.put("areaObj", buildingInfo.getAreaObj() + "");
		params.put("buildingName", buildingInfo.getBuildingName());
		params.put("buildingPhoto", buildingInfo.getBuildingPhoto());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BuildingInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询楼盘信息 */
	public List<BuildingInfo> QueryBuildingInfo(BuildingInfo queryConditionBuildingInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "BuildingInfoServlet?action=query";
		if(queryConditionBuildingInfo != null) {
			urlString += "&areaObj=" + queryConditionBuildingInfo.getAreaObj();
			urlString += "&buildingName=" + URLEncoder.encode(queryConditionBuildingInfo.getBuildingName(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		BuildingInfoListHandler buildingInfoListHander = new BuildingInfoListHandler();
		xr.setContentHandler(buildingInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<BuildingInfo> buildingInfoList = buildingInfoListHander.getBuildingInfoList();
		return buildingInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<BuildingInfo> buildingInfoList = new ArrayList<BuildingInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				BuildingInfo buildingInfo = new BuildingInfo();
				buildingInfo.setBuildingId(object.getInt("buildingId"));
				buildingInfo.setAreaObj(object.getInt("areaObj"));
				buildingInfo.setBuildingName(object.getString("buildingName"));
				buildingInfo.setBuildingPhoto(object.getString("buildingPhoto"));
				buildingInfoList.add(buildingInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buildingInfoList;
	}

	/* 更新楼盘信息 */
	public String UpdateBuildingInfo(BuildingInfo buildingInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("buildingId", buildingInfo.getBuildingId() + "");
		params.put("areaObj", buildingInfo.getAreaObj() + "");
		params.put("buildingName", buildingInfo.getBuildingName());
		params.put("buildingPhoto", buildingInfo.getBuildingPhoto());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BuildingInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除楼盘信息 */
	public String DeleteBuildingInfo(int buildingId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("buildingId", buildingId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BuildingInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "楼盘信息信息删除失败!";
		}
	}

	/* 根据楼盘编号获取楼盘信息对象 */
	public BuildingInfo GetBuildingInfo(int buildingId)  {
		List<BuildingInfo> buildingInfoList = new ArrayList<BuildingInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("buildingId", buildingId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BuildingInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				BuildingInfo buildingInfo = new BuildingInfo();
				buildingInfo.setBuildingId(object.getInt("buildingId"));
				buildingInfo.setAreaObj(object.getInt("areaObj"));
				buildingInfo.setBuildingName(object.getString("buildingName"));
				buildingInfo.setBuildingPhoto(object.getString("buildingPhoto"));
				buildingInfoList.add(buildingInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = buildingInfoList.size();
		if(size>0) return buildingInfoList.get(0); 
		else return null; 
	}
}
