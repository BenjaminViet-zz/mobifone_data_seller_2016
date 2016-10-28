package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.domain.UserEntity;
import com.benluck.vms.mobifonedataseller.session.UserLocalBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UserSessionEJB")
public class UserSessionBean extends AbstractSessionBean<UserEntity, Long> implements UserLocalBean{
    public UserSessionBean() {
    }

    @Override
    public List<UserEntity> findByName(String fullName, String name) {
        if (StringUtils.isNotBlank(name)){
            StringBuffer sqlQuery = new StringBuffer(" FROM UsersEntity pte WHERE 1 = 1 ");
            sqlQuery.append(" AND UPPER(pte.fullname) like  UPPER(:value)");
            StringBuffer sqlQueryClause = new StringBuffer();
            sqlQueryClause.append(sqlQuery.toString());
            Query query = entityManager.createQuery(sqlQueryClause.toString());
            query.setParameter("value", "%"+name+"%");
            return (List<UserEntity>)query.getResultList();
        } else {
            return null;
        }
    }

    @Override
    public UserEntity findByUserName(String userName) throws ObjectNotFoundException {
        Query query = entityManager.createQuery("FROM UserEntity u WHERE upper(u.userName) = :userName");
        query.setParameter("userName", userName.toUpperCase());
        try{
            return (UserEntity)query.getSingleResult();
        }catch (Exception e) {
            throw new ObjectNotFoundException("NOT FOUND User Name: " + userName);
        }
    }

    @Override
    public Object[] findListUser(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        List<String> userGroupCodes = new ArrayList<String>();
        userGroupCodes.add(Constants.USERGROUP_ADMIN);
        userGroupCodes.add(Constants.USERGROUP_TD);
        userGroupCodes.add(Constants.USERGROUP_NV);
        userGroupCodes.add(Constants.USERGROUP_CN);
        userGroupCodes.add(Constants.USERGROUP_BAOCAO);

        StringBuffer mainQuery = new StringBuffer();
        mainQuery.append("  from Vms_user u ")
                .append("   left join Vms_department d on d.departmentId = u.departmentId ")
                .append("   inner join Vms_UserGroup ug on ug.userGroupId = u.userGroupId ")
                .append("   left join Vms_ShopCode_ChiNhanh sc on upper(sc.shopCode) = upper(d.code) ")
                .append("   left join Vms_ChiNhanh cn on cn.chiNhanhId = sc.chiNhanhId ");

        mainQuery.append("   where 1 = 1 and upper(ug.code) in (:userGroupCodes) ");
        if(properties.get("userName") != null){
            mainQuery.append(" and upper(u.userName) like upper(:userName) ");
        }
        if(properties.get("displayName") != null){
            mainQuery.append(" and upper(u.displayName) like upper(:displayName) ");
        }
        if(properties.get("chiNhanhId") != null){
            mainQuery.append(" and u.chiNhanhId = :chiNhanhId ");
        }
        if(properties.get("departmentId") != null){
            mainQuery.append(" and u.departmentId = :departmentId");
        }
        if(properties.get("userGroupId") != null){
            mainQuery.append(" and ug.userGroupId = :userGroupId");
        }
        if(StringUtils.isNotBlank(sortExpression)){
            mainQuery.append(" order by u.").append(sortExpression);
        }else{
            mainQuery.append(" order by u.userName");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            mainQuery.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            mainQuery.append(" desc ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select u.userId, u.userName, u.displayName, u.mobileNumber, d.departmentId, d.name as departmentName, u.status, ug.userGroupId, ug.name as userGroupName ")
                        .append(", case when u.departmentId is null and u.chiNhanhId is not null ")
                        .append("           then (select cn1.name from Vms_chiNhanh cn1 where cn1.chiNhanhId = u.chiNhanhId) ")
                        .append("   else cn.name end as chiNhanhName    ")
                        .append(mainQuery);

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("userGroupCodes", userGroupCodes);
        if(properties.get("userName") != null){
            query.setParameter("userName", "%" + properties.get("userName").toString() + "%");
        }
        if(properties.get("displayName") != null){
            query.setParameter("displayName", "%" + properties.get("displayName").toString() + "%");
        }
        if(properties.get("chiNhanhId") != null){
            query.setParameter("chiNhanhId", Long.valueOf(properties.get("chiNhanhId").toString()));
        }
        if(properties.get("departmentId") != null){
            query.setParameter("departmentId", Long.valueOf(properties.get("departmentId").toString()));
        }
        if(properties.get("userGroupId") != null){
            query.setParameter("userGroupId", Long.valueOf(properties.get("userGroupId").toString()));
        }

        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);

        List resultSet = query.getResultList();

        StringBuffer sqlCount = new StringBuffer(" select count(u.userId) ").append(mainQuery.toString()) ;
        Query queryCount = entityManager.createNativeQuery(sqlCount.toString());
        queryCount.setParameter("userGroupCodes", userGroupCodes);
        if(properties.get("userName") != null){
            queryCount.setParameter("userName", "%" + properties.get("userName").toString() + "%");
        }
        if(properties.get("displayName") != null){
            queryCount.setParameter("displayName", "%" + properties.get("displayName").toString() + "%");
        }
        if(properties.get("chiNhanhId") != null){
            queryCount.setParameter("chiNhanhId", Long.valueOf(properties.get("chiNhanhId").toString()));
        }
        if(properties.get("departmentId") != null){
            queryCount.setParameter("departmentId", Long.valueOf(properties.get("departmentId").toString()));
        }
        if(properties.get("userGroupId") != null){
            queryCount.setParameter("userGroupId", Long.valueOf(properties.get("userGroupId").toString()));
        }
        Integer count = Integer.valueOf(queryCount.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public UserEntity findByMobileNumber(String mobileNumber) throws ObjectNotFoundException {
        Query query = entityManager.createQuery("FROM UserEntity u WHERE upper(u.mobileNumber) = :mobile");
        query.setParameter("mobile", mobileNumber.toUpperCase());
        try{
            return (UserEntity)query.getSingleResult();
        }catch (Exception e) {
            throw new ObjectNotFoundException("NOT FOUND Mobile Number: " + mobileNumber);
        }
    }
}
