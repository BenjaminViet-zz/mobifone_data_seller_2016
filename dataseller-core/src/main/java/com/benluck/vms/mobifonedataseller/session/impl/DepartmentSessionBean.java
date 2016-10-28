package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.DepartmentEntity;
import com.benluck.vms.mobifonedataseller.session.DepartmentLocalBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "DepartmentSessionEJB")
public class DepartmentSessionBean extends AbstractSessionBean<DepartmentEntity, Long> implements DepartmentLocalBean {
    public DepartmentSessionBean() {
    }

    @Override
    public DepartmentEntity findByCode(String code) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List findDepartmentListByBranchId(Long branchId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select d.departmentId, d.code, d.name from Vms_department d")
                    .append("   inner join Vms_shopcode_chiNhanh sc on upper(sc.shopCode) = upper(d.code) ")
                    .append("   inner join Vms_chiNhanh cn on cn.chiNhanhId = sc.chiNhanhId ")
                    .append("   where cn.chiNhanhId = :chiNhanhId ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("chiNhanhId", branchId);
        return query.getResultList();
    }

    @Override
    public List findByIdAndShopCodeId(Long departmentId, Long shopCodeId) throws ObjectNotFoundException {
        Query query = entityManager.createQuery("select d.departmentId, d.code, d.name, d.address, d.tel, d.contactName,sc.chiNhanh.chiNhanhId FROM DepartmentEntity d, ShopCodeChiNhanhEntity sc WHERE upper(sc.shopCode) = upper(d.code) and sc.shopCodeChiNhanhId = :shopCodeId and d.departmentId = :departmentId  ");
        query.setParameter("shopCodeId", shopCodeId);
        query.setParameter("departmentId", departmentId);
        try{
            return query.getResultList();
        }catch (Exception e) {
            throw new ObjectNotFoundException("NOT FOUND Department: ");
        }
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from  Vms_department d ")
                .append("   inner join Vms_ShopCode_ChiNhanh sc on upper(sc.shopCode) = upper(d.code) ")
                .append("   inner join Vms_chiNhanh cn on cn.chiNhanhId = sc.chiNhanhId ")
                .append("   where 1 = 1 ");
        if(properties.get("chiNhanhId") != null){
            mainQuery.append(" and cn.chiNhanhId = :chiNhanhId ");
        }

        StringBuilder sqlQueryClause = new StringBuilder(" select d.departmentId, d.code, d.name, cn.name as chiNhanhName, d.tel, d.address, sc.SHOPCODECNID ").append(mainQuery);

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by d.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by d.departmentId");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            sqlQueryClause.append(" asc ");
        }

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        if(properties.get("chiNhanhId") != null){
            query.setParameter("chiNhanhId", Long.valueOf(properties.get("chiNhanhId").toString()));
        }

        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(d.departmentId) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("chiNhanhId") != null){
            countQuery.setParameter("chiNhanhId", Long.valueOf(properties.get("chiNhanhId").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return  new Object[]{count, resultSet};
    }

    @Override
    public List findByBranchIdAndpromotionId(Long branchId, Long promotionId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select d.departmentId, d.code, d.name from Vms_department d")
                .append("   inner join Vms_shopcode_chiNhanh sc on upper(sc.shopCode) = upper(d.code) ")
                .append("   inner join BRANCH_MAPPING bm on bm.chiNhanhId = sc.chiNhanhId ")
                .append("   where bm.branchId = :branch_Id ")
                .append("   and bm.chuongTrinhId = :chuongTrinh_Id ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("branch_Id", branchId);
        query.setParameter("chuongTrinh_Id", promotionId);
        return query.getResultList();
    }
}
