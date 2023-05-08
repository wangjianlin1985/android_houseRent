package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.GuestBook;
public class GuestBookListHandler extends DefaultHandler {
	private List<GuestBook> guestBookList = null;
	private GuestBook guestBook;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (guestBook != null) { 
            String valueString = new String(ch, start, length); 
            if ("guestBookId".equals(tempString)) 
            	guestBook.setGuestBookId(new Integer(valueString).intValue());
            else if ("title".equals(tempString)) 
            	guestBook.setTitle(valueString); 
            else if ("content".equals(tempString)) 
            	guestBook.setContent(valueString); 
            else if ("userObj".equals(tempString)) 
            	guestBook.setUserObj(valueString); 
            else if ("addTime".equals(tempString)) 
            	guestBook.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("GuestBook".equals(localName)&&guestBook!=null){
			guestBookList.add(guestBook);
			guestBook = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		guestBookList = new ArrayList<GuestBook>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("GuestBook".equals(localName)) {
            guestBook = new GuestBook(); 
        }
        tempString = localName; 
	}

	public List<GuestBook> getGuestBookList() {
		return this.guestBookList;
	}
}
