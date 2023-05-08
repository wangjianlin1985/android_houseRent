package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.BuildingInfo;
public class BuildingInfoListHandler extends DefaultHandler {
	private List<BuildingInfo> buildingInfoList = null;
	private BuildingInfo buildingInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (buildingInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("buildingId".equals(tempString)) 
            	buildingInfo.setBuildingId(new Integer(valueString).intValue());
            else if ("areaObj".equals(tempString)) 
            	buildingInfo.setAreaObj(new Integer(valueString).intValue());
            else if ("buildingName".equals(tempString)) 
            	buildingInfo.setBuildingName(valueString); 
            else if ("buildingPhoto".equals(tempString)) 
            	buildingInfo.setBuildingPhoto(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("BuildingInfo".equals(localName)&&buildingInfo!=null){
			buildingInfoList.add(buildingInfo);
			buildingInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		buildingInfoList = new ArrayList<BuildingInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("BuildingInfo".equals(localName)) {
            buildingInfo = new BuildingInfo(); 
        }
        tempString = localName; 
	}

	public List<BuildingInfo> getBuildingInfoList() {
		return this.buildingInfoList;
	}
}
