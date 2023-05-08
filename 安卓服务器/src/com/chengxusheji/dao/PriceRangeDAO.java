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
import com.chengxusheji.domain.PriceRange;

@Service @Transactional
public class PriceRangeDAO {

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
    public void AddPriceRange(PriceRange priceRange) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(priceRange);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PriceRange> QueryPriceRangeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From PriceRange priceRange where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List priceRangeList = q.list();
    	return (ArrayList<PriceRange>) priceRangeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PriceRange> QueryPriceRangeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From PriceRange priceRange where 1=1";
    	Query q = s.createQuery(hql);
    	List priceRangeList = q.list();
    	return (ArrayList<PriceRange>) priceRangeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PriceRange> QueryAllPriceRangeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From PriceRange";
        Query q = s.createQuery(hql);
        List priceRangeList = q.list();
        return (ArrayList<PriceRange>) priceRangeList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From PriceRange priceRange where 1=1";
        Query q = s.createQuery(hql);
        List priceRangeList = q.list();
        recordNumber = priceRangeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public PriceRange GetPriceRangeByRangeId(int rangeId) {
        Session s = factory.getCurrentSession();
        PriceRange priceRange = (PriceRange)s.get(PriceRange.class, rangeId);
        return priceRange;
    }

    /*����PriceRange��Ϣ*/
    public void UpdatePriceRange(PriceRange priceRange) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(priceRange);
    }

    /*ɾ��PriceRange��Ϣ*/
    public void DeletePriceRange (int rangeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object priceRange = s.load(PriceRange.class, rangeId);
        s.delete(priceRange);
    }

}
