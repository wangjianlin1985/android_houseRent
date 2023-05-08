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
import com.chengxusheji.domain.BuildingInfo;
import com.chengxusheji.domain.HourseType;
import com.chengxusheji.domain.PriceRange;
import com.chengxusheji.domain.Hourse;

@Service @Transactional
public class HourseDAO {

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
    public void AddHourse(Hourse hourse) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(hourse);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Hourse> QueryHourseInfo(String hourseName,BuildingInfo buildingObj,HourseType hourseTypeObj,PriceRange priceRangeObj,String madeYear,String connectPerson,String connectPhone,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Hourse hourse where 1=1";
    	if(!hourseName.equals("")) hql = hql + " and hourse.hourseName like '%" + hourseName + "%'";
    	if(null != buildingObj && buildingObj.getBuildingId()!=0) hql += " and hourse.buildingObj.buildingId=" + buildingObj.getBuildingId();
    	if(null != hourseTypeObj && hourseTypeObj.getTypeId()!=0) hql += " and hourse.hourseTypeObj.typeId=" + hourseTypeObj.getTypeId();
    	if(null != priceRangeObj && priceRangeObj.getRangeId()!=0) hql += " and hourse.priceRangeObj.rangeId=" + priceRangeObj.getRangeId();
    	if(!madeYear.equals("")) hql = hql + " and hourse.madeYear like '%" + madeYear + "%'";
    	if(!connectPerson.equals("")) hql = hql + " and hourse.connectPerson like '%" + connectPerson + "%'";
    	if(!connectPhone.equals("")) hql = hql + " and hourse.connectPhone like '%" + connectPhone + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List hourseList = q.list();
    	return (ArrayList<Hourse>) hourseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Hourse> QueryHourseInfo(String hourseName,BuildingInfo buildingObj,HourseType hourseTypeObj,PriceRange priceRangeObj,String madeYear,String connectPerson,String connectPhone) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Hourse hourse where 1=1";
    	if(!hourseName.equals("")) hql = hql + " and hourse.hourseName like '%" + hourseName + "%'";
    	if(null != buildingObj && buildingObj.getBuildingId()!=0) hql += " and hourse.buildingObj.buildingId=" + buildingObj.getBuildingId();
    	if(null != hourseTypeObj && hourseTypeObj.getTypeId()!=0) hql += " and hourse.hourseTypeObj.typeId=" + hourseTypeObj.getTypeId();
    	if(null != priceRangeObj && priceRangeObj.getRangeId()!=0) hql += " and hourse.priceRangeObj.rangeId=" + priceRangeObj.getRangeId();
    	if(!madeYear.equals("")) hql = hql + " and hourse.madeYear like '%" + madeYear + "%'";
    	if(!connectPerson.equals("")) hql = hql + " and hourse.connectPerson like '%" + connectPerson + "%'";
    	if(!connectPhone.equals("")) hql = hql + " and hourse.connectPhone like '%" + connectPhone + "%'";
    	Query q = s.createQuery(hql);
    	List hourseList = q.list();
    	return (ArrayList<Hourse>) hourseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Hourse> QueryAllHourseInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Hourse";
        Query q = s.createQuery(hql);
        List hourseList = q.list();
        return (ArrayList<Hourse>) hourseList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String hourseName,BuildingInfo buildingObj,HourseType hourseTypeObj,PriceRange priceRangeObj,String madeYear,String connectPerson,String connectPhone) {
        Session s = factory.getCurrentSession();
        String hql = "From Hourse hourse where 1=1";
        if(!hourseName.equals("")) hql = hql + " and hourse.hourseName like '%" + hourseName + "%'";
        if(null != buildingObj && buildingObj.getBuildingId()!=0) hql += " and hourse.buildingObj.buildingId=" + buildingObj.getBuildingId();
        if(null != hourseTypeObj && hourseTypeObj.getTypeId()!=0) hql += " and hourse.hourseTypeObj.typeId=" + hourseTypeObj.getTypeId();
        if(null != priceRangeObj && priceRangeObj.getRangeId()!=0) hql += " and hourse.priceRangeObj.rangeId=" + priceRangeObj.getRangeId();
        if(!madeYear.equals("")) hql = hql + " and hourse.madeYear like '%" + madeYear + "%'";
        if(!connectPerson.equals("")) hql = hql + " and hourse.connectPerson like '%" + connectPerson + "%'";
        if(!connectPhone.equals("")) hql = hql + " and hourse.connectPhone like '%" + connectPhone + "%'";
        Query q = s.createQuery(hql);
        List hourseList = q.list();
        recordNumber = hourseList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Hourse GetHourseByHourseId(int hourseId) {
        Session s = factory.getCurrentSession();
        Hourse hourse = (Hourse)s.get(Hourse.class, hourseId);
        return hourse;
    }

    /*����Hourse��Ϣ*/
    public void UpdateHourse(Hourse hourse) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(hourse);
    }

    /*ɾ��Hourse��Ϣ*/
    public void DeleteHourse (int hourseId) throws Exception {
        Session s = factory.getCurrentSession();
        Object hourse = s.load(Hourse.class, hourseId);
        s.delete(hourse);
    }

}
