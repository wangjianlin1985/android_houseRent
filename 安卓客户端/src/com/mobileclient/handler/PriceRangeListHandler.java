package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.PriceRange;
public class PriceRangeListHandler extends DefaultHandler {
	private List<PriceRange> priceRangeList = null;
	private PriceRange priceRange;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (priceRange != null) { 
            String valueString = new String(ch, start, length); 
            if ("rangeId".equals(tempString)) 
            	priceRange.setRangeId(new Integer(valueString).intValue());
            else if ("priceName".equals(tempString)) 
            	priceRange.setPriceName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("PriceRange".equals(localName)&&priceRange!=null){
			priceRangeList.add(priceRange);
			priceRange = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		priceRangeList = new ArrayList<PriceRange>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("PriceRange".equals(localName)) {
            priceRange = new PriceRange(); 
        }
        tempString = localName; 
	}

	public List<PriceRange> getPriceRangeList() {
		return this.priceRangeList;
	}
}
