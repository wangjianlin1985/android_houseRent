package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Hourse;
public class HourseListHandler extends DefaultHandler {
	private List<Hourse> hourseList = null;
	private Hourse hourse;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (hourse != null) { 
            String valueString = new String(ch, start, length); 
            if ("hourseId".equals(tempString)) 
            	hourse.setHourseId(new Integer(valueString).intValue());
            else if ("hourseName".equals(tempString)) 
            	hourse.setHourseName(valueString); 
            else if ("buildingObj".equals(tempString)) 
            	hourse.setBuildingObj(new Integer(valueString).intValue());
            else if ("housePhoto".equals(tempString)) 
            	hourse.setHousePhoto(valueString); 
            else if ("hourseTypeObj".equals(tempString)) 
            	hourse.setHourseTypeObj(new Integer(valueString).intValue());
            else if ("priceRangeObj".equals(tempString)) 
            	hourse.setPriceRangeObj(new Integer(valueString).intValue());
            else if ("area".equals(tempString)) 
            	hourse.setArea(valueString); 
            else if ("price".equals(tempString)) 
            	hourse.setPrice(new Float(valueString).floatValue());
            else if ("louceng".equals(tempString)) 
            	hourse.setLouceng(valueString); 
            else if ("zhuangxiu".equals(tempString)) 
            	hourse.setZhuangxiu(valueString); 
            else if ("caoxiang".equals(tempString)) 
            	hourse.setCaoxiang(valueString); 
            else if ("madeYear".equals(tempString)) 
            	hourse.setMadeYear(valueString); 
            else if ("connectPerson".equals(tempString)) 
            	hourse.setConnectPerson(valueString); 
            else if ("connectPhone".equals(tempString)) 
            	hourse.setConnectPhone(valueString); 
            else if ("detail".equals(tempString)) 
            	hourse.setDetail(valueString); 
            else if ("address".equals(tempString)) 
            	hourse.setAddress(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Hourse".equals(localName)&&hourse!=null){
			hourseList.add(hourse);
			hourse = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		hourseList = new ArrayList<Hourse>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Hourse".equals(localName)) {
            hourse = new Hourse(); 
        }
        tempString = localName; 
	}

	public List<Hourse> getHourseList() {
		return this.hourseList;
	}
}
