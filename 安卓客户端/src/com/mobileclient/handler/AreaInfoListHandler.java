package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.AreaInfo;
public class AreaInfoListHandler extends DefaultHandler {
	private List<AreaInfo> areaInfoList = null;
	private AreaInfo areaInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (areaInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("areaId".equals(tempString)) 
            	areaInfo.setAreaId(new Integer(valueString).intValue());
            else if ("areaName".equals(tempString)) 
            	areaInfo.setAreaName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("AreaInfo".equals(localName)&&areaInfo!=null){
			areaInfoList.add(areaInfo);
			areaInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		areaInfoList = new ArrayList<AreaInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("AreaInfo".equals(localName)) {
            areaInfo = new AreaInfo(); 
        }
        tempString = localName; 
	}

	public List<AreaInfo> getAreaInfoList() {
		return this.areaInfoList;
	}
}
