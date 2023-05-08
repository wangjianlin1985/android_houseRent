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
import com.chengxusheji.domain.AreaInfo;
import com.chengxusheji.domain.HourseType;
import com.chengxusheji.domain.PriceRange;
import com.chengxusheji.domain.WantHourseInfo;

@Service @Transactional
public class WantHourseInfoDAO {

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
    public void AddWantHourseInfo(WantHourseInfo wantHourseInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(wantHourseInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<WantHourseInfo> QueryWantHourseInfoInfo(UserInfo userObj,String title,AreaInfo position,HourseType hourseTypeObj,PriceRange priceRangeObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From WantHourseInfo wantHourseInfo where 1=1";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and wantHourseInfo.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!title.equals("")) hql = hql + " and wantHourseInfo.title like '%" + title + "%'";
    	if(null != position && position.getAreaId()!=0) hql += " and wantHourseInfo.position.areaId=" + position.getAreaId();
    	if(null != hourseTypeObj && hourseTypeObj.getTypeId()!=0) hql += " and wantHourseInfo.hourseTypeObj.typeId=" + hourseTypeObj.getTypeId();
    	if(null != priceRangeObj && priceRangeObj.getRangeId()!=0) hql += " and wantHourseInfo.priceRangeObj.rangeId=" + priceRangeObj.getRangeId();
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List wantHourseInfoList = q.list();
    	return (ArrayList<WantHourseInfo>) wantHourseInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<WantHourseInfo> QueryWantHourseInfoInfo(UserInfo userObj,String title,AreaInfo position,HourseType hourseTypeObj,PriceRange priceRangeObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From WantHourseInfo wantHourseInfo where 1=1";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and wantHourseInfo.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!title.equals("")) hql = hql + " and wantHourseInfo.title like '%" + title + "%'";
    	if(null != position && position.getAreaId()!=0) hql += " and wantHourseInfo.position.areaId=" + position.getAreaId();
    	if(null != hourseTypeObj && hourseTypeObj.getTypeId()!=0) hql += " and wantHourseInfo.hourseTypeObj.typeId=" + hourseTypeObj.getTypeId();
    	if(null != priceRangeObj && priceRangeObj.getRangeId()!=0) hql += " and wantHourseInfo.priceRangeObj.rangeId=" + priceRangeObj.getRangeId();
    	Query q = s.createQuery(hql);
    	List wantHourseInfoList = q.list();
    	return (ArrayList<WantHourseInfo>) wantHourseInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<WantHourseInfo> QueryAllWantHourseInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From WantHourseInfo";
        Query q = s.createQuery(hql);
        List wantHourseInfoList = q.list();
        return (ArrayList<WantHourseInfo>) wantHourseInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(UserInfo userObj,String title,AreaInfo position,HourseType hourseTypeObj,PriceRange priceRangeObj) {
        Session s = factory.getCurrentSession();
        String hql = "From WantHourseInfo wantHourseInfo where 1=1";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and wantHourseInfo.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!title.equals("")) hql = hql + " and wantHourseInfo.title like '%" + title + "%'";
        if(null != position && position.getAreaId()!=0) hql += " and wantHourseInfo.position.areaId=" + position.getAreaId();
        if(null != hourseTypeObj && hourseTypeObj.getTypeId()!=0) hql += " and wantHourseInfo.hourseTypeObj.typeId=" + hourseTypeObj.getTypeId();
        if(null != priceRangeObj && priceRangeObj.getRangeId()!=0) hql += " and wantHourseInfo.priceRangeObj.rangeId=" + priceRangeObj.getRangeId();
        Query q = s.createQuery(hql);
        List wantHourseInfoList = q.list();
        recordNumber = wantHourseInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public WantHourseInfo GetWantHourseInfoByWantHourseId(int wantHourseId) {
        Session s = factory.getCurrentSession();
        WantHourseInfo wantHourseInfo = (WantHourseInfo)s.get(WantHourseInfo.class, wantHourseId);
        return wantHourseInfo;
    }

    /*更新WantHourseInfo信息*/
    public void UpdateWantHourseInfo(WantHourseInfo wantHourseInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(wantHourseInfo);
    }

    /*删除WantHourseInfo信息*/
    public void DeleteWantHourseInfo (int wantHourseId) throws Exception {
        Session s = factory.getCurrentSession();
        Object wantHourseInfo = s.load(WantHourseInfo.class, wantHourseId);
        s.delete(wantHourseInfo);
    }

}
