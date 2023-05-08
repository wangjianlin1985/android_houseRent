package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.NewsInfo;
public class NewsInfoListHandler extends DefaultHandler {
	private List<NewsInfo> newsInfoList = null;
	private NewsInfo newsInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (newsInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("newsId".equals(tempString)) 
            	newsInfo.setNewsId(new Integer(valueString).intValue());
            else if ("newsTitle".equals(tempString)) 
            	newsInfo.setNewsTitle(valueString); 
            else if ("newsContent".equals(tempString)) 
            	newsInfo.setNewsContent(valueString); 
            else if ("newsDate".equals(tempString)) 
            	newsInfo.setNewsDate(Timestamp.valueOf(valueString));
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("NewsInfo".equals(localName)&&newsInfo!=null){
			newsInfoList.add(newsInfo);
			newsInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		newsInfoList = new ArrayList<NewsInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("NewsInfo".equals(localName)) {
            newsInfo = new NewsInfo(); 
        }
        tempString = localName; 
	}

	public List<NewsInfo> getNewsInfoList() {
		return this.newsInfoList;
	}
}
