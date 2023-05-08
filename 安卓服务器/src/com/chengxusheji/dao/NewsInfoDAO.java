package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.NewsInfo;

@Service @Transactional
public class NewsInfoDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddNewsInfo(NewsInfo newsInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(newsInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NewsInfo> QueryNewsInfoInfo(String newsTitle,String newsDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From NewsInfo newsInfo where 1=1";
    	if(!newsTitle.equals("")) hql = hql + " and newsInfo.newsTitle like '%" + newsTitle + "%'";
    	if(!newsDate.equals("")) hql = hql + " and newsInfo.newsDate like '%" + newsDate + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List newsInfoList = q.list();
    	return (ArrayList<NewsInfo>) newsInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NewsInfo> QueryNewsInfoInfo(String newsTitle,String newsDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From NewsInfo newsInfo where 1=1";
    	if(!newsTitle.equals("")) hql = hql + " and newsInfo.newsTitle like '%" + newsTitle + "%'";
    	if(!newsDate.equals("")) hql = hql + " and newsInfo.newsDate like '%" + newsDate + "%'";
    	Query q = s.createQuery(hql);
    	List newsInfoList = q.list();
    	return (ArrayList<NewsInfo>) newsInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NewsInfo> QueryAllNewsInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From NewsInfo";
        Query q = s.createQuery(hql);
        List newsInfoList = q.list();
        return (ArrayList<NewsInfo>) newsInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String newsTitle,String newsDate) {
        Session s = factory.getCurrentSession();
        String hql = "From NewsInfo newsInfo where 1=1";
        if(!newsTitle.equals("")) hql = hql + " and newsInfo.newsTitle like '%" + newsTitle + "%'";
        if(!newsDate.equals("")) hql = hql + " and newsInfo.newsDate like '%" + newsDate + "%'";
        Query q = s.createQuery(hql);
        List newsInfoList = q.list();
        recordNumber = newsInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public NewsInfo GetNewsInfoByNewsId(int newsId) {
        Session s = factory.getCurrentSession();
        NewsInfo newsInfo = (NewsInfo)s.get(NewsInfo.class, newsId);
        return newsInfo;
    }

    /*更新NewsInfo信息*/
    public void UpdateNewsInfo(NewsInfo newsInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(newsInfo);
    }

    /*删除NewsInfo信息*/
    public void DeleteNewsInfo (int newsId) throws Exception {
        Session s = factory.getCurrentSession();
        Object newsInfo = s.load(NewsInfo.class, newsId);
        s.delete(newsInfo);
    }

}
