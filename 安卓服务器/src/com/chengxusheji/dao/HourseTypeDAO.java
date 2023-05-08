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
import com.chengxusheji.domain.HourseType;

@Service @Transactional
public class HourseTypeDAO {

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
    public void AddHourseType(HourseType hourseType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(hourseType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HourseType> QueryHourseTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From HourseType hourseType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List hourseTypeList = q.list();
    	return (ArrayList<HourseType>) hourseTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HourseType> QueryHourseTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From HourseType hourseType where 1=1";
    	Query q = s.createQuery(hql);
    	List hourseTypeList = q.list();
    	return (ArrayList<HourseType>) hourseTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HourseType> QueryAllHourseTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From HourseType";
        Query q = s.createQuery(hql);
        List hourseTypeList = q.list();
        return (ArrayList<HourseType>) hourseTypeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From HourseType hourseType where 1=1";
        Query q = s.createQuery(hql);
        List hourseTypeList = q.list();
        recordNumber = hourseTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public HourseType GetHourseTypeByTypeId(int typeId) {
        Session s = factory.getCurrentSession();
        HourseType hourseType = (HourseType)s.get(HourseType.class, typeId);
        return hourseType;
    }

    /*更新HourseType信息*/
    public void UpdateHourseType(HourseType hourseType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(hourseType);
    }

    /*删除HourseType信息*/
    public void DeleteHourseType (int typeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object hourseType = s.load(HourseType.class, typeId);
        s.delete(hourseType);
    }

}
