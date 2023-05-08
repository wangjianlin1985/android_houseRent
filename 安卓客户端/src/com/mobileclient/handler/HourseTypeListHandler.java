package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.HourseType;
public class HourseTypeListHandler extends DefaultHandler {
	private List<HourseType> hourseTypeList = null;
	private HourseType hourseType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (hourseType != null) { 
            String valueString = new String(ch, start, length); 
            if ("typeId".equals(tempString)) 
            	hourseType.setTypeId(new Integer(valueString).intValue());
            else if ("typeName".equals(tempString)) 
            	hourseType.setTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("HourseType".equals(localName)&&hourseType!=null){
			hourseTypeList.add(hourseType);
			hourseType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		hourseTypeList = new ArrayList<HourseType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("HourseType".equals(localName)) {
            hourseType = new HourseType(); 
        }
        tempString = localName; 
	}

	public List<HourseType> getHourseTypeList() {
		return this.hourseTypeList;
	}
}
