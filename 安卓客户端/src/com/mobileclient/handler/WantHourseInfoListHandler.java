package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.WantHourseInfo;
public class WantHourseInfoListHandler extends DefaultHandler {
	private List<WantHourseInfo> wantHourseInfoList = null;
	private WantHourseInfo wantHourseInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (wantHourseInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("wantHourseId".equals(tempString)) 
            	wantHourseInfo.setWantHourseId(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	wantHourseInfo.setUserObj(valueString); 
            else if ("title".equals(tempString)) 
            	wantHourseInfo.setTitle(valueString); 
            else if ("position".equals(tempString)) 
            	wantHourseInfo.setPosition(new Integer(valueString).intValue());
            else if ("hourseTypeObj".equals(tempString)) 
            	wantHourseInfo.setHourseTypeObj(new Integer(valueString).intValue());
            else if ("priceRangeObj".equals(tempString)) 
            	wantHourseInfo.setPriceRangeObj(new Integer(valueString).intValue());
            else if ("price".equals(tempString)) 
            	wantHourseInfo.setPrice(new Float(valueString).floatValue());
            else if ("lianxiren".equals(tempString)) 
            	wantHourseInfo.setLianxiren(valueString); 
            else if ("telephone".equals(tempString)) 
            	wantHourseInfo.setTelephone(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("WantHourseInfo".equals(localName)&&wantHourseInfo!=null){
			wantHourseInfoList.add(wantHourseInfo);
			wantHourseInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		wantHourseInfoList = new ArrayList<WantHourseInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("WantHourseInfo".equals(localName)) {
            wantHourseInfo = new WantHourseInfo(); 
        }
        tempString = localName; 
	}

	public List<WantHourseInfo> getWantHourseInfoList() {
		return this.wantHourseInfoList;
	}
}
