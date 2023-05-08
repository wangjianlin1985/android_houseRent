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
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.GuestBook;

@Service @Transactional
public class GuestBookDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddGuestBook(GuestBook guestBook) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(guestBook);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GuestBook> QueryGuestBookInfo(String title,UserInfo userObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From GuestBook guestBook where 1=1";
    	if(!title.equals("")) hql = hql + " and guestBook.title like '%" + title + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and guestBook.userObj.user_name='" + userObj.getUser_name() + "'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List guestBookList = q.list();
    	return (ArrayList<GuestBook>) guestBookList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GuestBook> QueryGuestBookInfo(String title,UserInfo userObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From GuestBook guestBook where 1=1";
    	if(!title.equals("")) hql = hql + " and guestBook.title like '%" + title + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and guestBook.userObj.user_name='" + userObj.getUser_name() + "'";
    	Query q = s.createQuery(hql);
    	List guestBookList = q.list();
    	return (ArrayList<GuestBook>) guestBookList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GuestBook> QueryAllGuestBookInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From GuestBook";
        Query q = s.createQuery(hql);
        List guestBookList = q.list();
        return (ArrayList<GuestBook>) guestBookList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String title,UserInfo userObj) {
        Session s = factory.getCurrentSession();
        String hql = "From GuestBook guestBook where 1=1";
        if(!title.equals("")) hql = hql + " and guestBook.title like '%" + title + "%'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and guestBook.userObj.user_name='" + userObj.getUser_name() + "'";
        Query q = s.createQuery(hql);
        List guestBookList = q.list();
        recordNumber = guestBookList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public GuestBook GetGuestBookByGuestBookId(int guestBookId) {
        Session s = factory.getCurrentSession();
        GuestBook guestBook = (GuestBook)s.get(GuestBook.class, guestBookId);
        return guestBook;
    }

    /*����GuestBook��Ϣ*/
    public void UpdateGuestBook(GuestBook guestBook) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(guestBook);
    }

    /*ɾ��GuestBook��Ϣ*/
    public void DeleteGuestBook (int guestBookId) throws Exception {
        Session s = factory.getCurrentSession();
        Object guestBook = s.load(GuestBook.class, guestBookId);
        s.delete(guestBook);
    }

}
