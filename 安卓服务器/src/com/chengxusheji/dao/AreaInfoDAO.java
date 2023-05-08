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
import com.chengxusheji.domain.AreaInfo;

@Service @Transactional
public class AreaInfoDAO {

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
    public void AddAreaInfo(AreaInfo areaInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(areaInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<AreaInfo> QueryAreaInfoInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From AreaInfo areaInfo where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List areaInfoList = q.list();
    	return (ArrayList<AreaInfo>) areaInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<AreaInfo> QueryAreaInfoInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From AreaInfo areaInfo where 1=1";
    	Query q = s.createQuery(hql);
    	List areaInfoList = q.list();
    	return (ArrayList<AreaInfo>) areaInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<AreaInfo> QueryAllAreaInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From AreaInfo";
        Query q = s.createQuery(hql);
        List areaInfoList = q.list();
        return (ArrayList<AreaInfo>) areaInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From AreaInfo areaInfo where 1=1";
        Query q = s.createQuery(hql);
        List areaInfoList = q.list();
        recordNumber = areaInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public AreaInfo GetAreaInfoByAreaId(int areaId) {
        Session s = factory.getCurrentSession();
        AreaInfo areaInfo = (AreaInfo)s.get(AreaInfo.class, areaId);
        return areaInfo;
    }

    /*更新AreaInfo信息*/
    public void UpdateAreaInfo(AreaInfo areaInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(areaInfo);
    }

    /*删除AreaInfo信息*/
    public void DeleteAreaInfo (int areaId) throws Exception {
        Session s = factory.getCurrentSession();
        Object areaInfo = s.load(AreaInfo.class, areaId);
        s.delete(areaInfo);
    }

}
