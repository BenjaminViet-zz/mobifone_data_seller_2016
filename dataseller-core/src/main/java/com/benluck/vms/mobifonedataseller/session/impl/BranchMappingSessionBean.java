package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.BranchMappingEntity;
import com.benluck.vms.mobifonedataseller.session.BranchMappingLocalBean;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BranchMappingSessionEJB")
public class BranchMappingSessionBean extends AbstractSessionBean<BranchMappingEntity, Long> implements BranchMappingLocalBean{
    public BranchMappingSessionBean() {
    }

    @Override
    public BranchMappingEntity findByUniqueProperties(Long chuongTrinhId, Long chiNhanhId) throws ObjectNotFoundException{
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" from BranchMappingEntity where chuongTrinh.chuongTrinhId = :chuongTrinhId and chiNhanh.chiNhanhId = :chiNhanhId ");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("chuongTrinhId", chuongTrinhId);
        query.setParameter("chiNhanhId", chiNhanhId);
        List<BranchMappingEntity> entityList = (List<BranchMappingEntity>)query.getResultList();
        if(entityList.size() > 0){
            return entityList.get(0);
        }
        throw new ObjectNotFoundException("Not found BranchMappingEntity by chuongTrinhId: " + chuongTrinhId + " and chiNhanhId: " + chiNhanhId);
    }

    @Override
    public BranchMappingEntity findByUniqueProperties(Long chiNhanhId, String ctCode) throws ObjectNotFoundException{
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" from BranchMappingEntity where chuongTrinh.code = :chuongTrinhCode and chiNhanh.chiNhanhId = :chiNhanhId ");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("chuongTrinhCode", ctCode);
        query.setParameter("chiNhanhId", chiNhanhId);
        List<BranchMappingEntity> entityList = (List<BranchMappingEntity>)query.getResultList();
        if(entityList.size() > 0){
            return entityList.get(0);
        }
        throw new ObjectNotFoundException("Not found BranchMappingEntity by chuongTrinhCode: " + ctCode + " and chiNhanhId: " + chiNhanhId);
    }

    @Override
    public BranchMappingEntity findByDepartmentIdAndCtCode(Long departmentId, String ctCode) throws ObjectNotFoundException {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select bm from BranchMappingEntity as  bm, ShopCodeChiNhanhEntity as sc, DepartmentEntity as  d   ")
                    .append("   where bm.chuongTrinh.code = :chuongTrinhCode and d.departmentId = :departmentId  ")
                    .append("   and sc.chiNhanh.chiNhanhId = bm.chiNhanh.chiNhanhId  ")
                    .append("   and upper(d.code) = upper(sc.shopCode)  ");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("chuongTrinhCode", ctCode);
        query.setParameter("departmentId", departmentId);
        List<BranchMappingEntity> entityList = (List<BranchMappingEntity>)query.getResultList();
        if(entityList.size() > 0){
            return entityList.get(0);
        }
        throw new ObjectNotFoundException("Not found BranchMappingEntity by chuongTrinhCode: " + ctCode + " and departmentId: " + departmentId);
    }
}
