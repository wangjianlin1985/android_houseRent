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
import com.chengxusheji.domain.BuildingInfo;

@Service @Transactional
public class BuildingInfoDAO {

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
    public void AddBuildingInfo(BuildingInfo buildingInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(buildingInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BuildingInfo> QueryBuildingInfoInfo(AreaInfo areaObj,String buildingName,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From BuildingInfo buildingInfo where 1=1";
    	if(null != areaObj && areaObj.getAreaId()!=0) hql += " and buildingInfo.areaObj.areaId=" + areaObj.getAreaId();
    	if(!buildingName.equals("")) hql = hql + " and buildingInfo.buildingName like '%" + buildingName + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List buildingInfoList = q.list();
    	return (ArrayList<BuildingInfo>) buildingInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BuildingInfo> QueryBuildingInfoInfo(AreaInfo areaObj,String buildingName) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From BuildingInfo buildingInfo where 1=1";
    	if(null != areaObj && areaObj.getAreaId()!=0) hql += " and buildingInfo.areaObj.areaId=" + areaObj.getAreaId();
    	if(!buildingName.equals("")) hql = hql + " and buildingInfo.buildingName like '%" + buildingName + "%'";
    	Query q = s.createQuery(hql);
    	List buildingInfoList = q.list();
    	return (ArrayList<BuildingInfo>) buildingInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BuildingInfo> QueryAllBuildingInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From BuildingInfo";
        Query q = s.createQuery(hql);
        List buildingInfoList = q.list();
        return (ArrayList<BuildingInfo>) buildingInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(AreaInfo areaObj,String buildingName) {
        Session s = factory.getCurrentSession();
        String hql = "From BuildingInfo buildingInfo where 1=1";
        if(null != areaObj && areaObj.getAreaId()!=0) hql += " and buildingInfo.areaObj.areaId=" + areaObj.getAreaId();
        if(!buildingName.equals("")) hql = hql + " and buildingInfo.buildingName like '%" + buildingName + "%'";
        Query q = s.createQuery(hql);
        List buildingInfoList = q.list();
        recordNumber = buildingInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public BuildingInfo GetBuildingInfoByBuildingId(int buildingId) {
        Session s = factory.getCurrentSession();
        BuildingInfo buildingInfo = (BuildingInfo)s.get(BuildingInfo.class, buildingId);
        return buildingInfo;
    }

    /*更新BuildingInfo信息*/
    public void UpdateBuildingInfo(BuildingInfo buildingInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(buildingInfo);
    }

    /*删除BuildingInfo信息*/
    public void DeleteBuildingInfo (int buildingId) throws Exception {
        Session s = factory.getCurrentSession();
        Object buildingInfo = s.load(BuildingInfo.class, buildingId);
        s.delete(buildingInfo);
    }

}
