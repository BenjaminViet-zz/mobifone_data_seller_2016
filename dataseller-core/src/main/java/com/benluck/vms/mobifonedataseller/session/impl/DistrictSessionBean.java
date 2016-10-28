package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.session.DistrictLocalBean;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/5/15
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "DistrictSessionEJB")
public class DistrictSessionBean implements DistrictLocalBean{
    public DistrictSessionBean() {
    }

    @PersistenceContext(unitName = "core-data")
    protected EntityManager entityManager;


    @Override
    public List findAllByDBLink(String dbLinkName) throws ObjectNotFoundException {
        try{
            StringBuilder sqlQueryClause = new StringBuilder();
            sqlQueryClause.append(" select district_Id,  district_Code, district_Name from District")
                    .append(dbLinkName)
                    .append(" order by district_Id ");
            Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
            return query.getResultList();
        }catch (Exception e){
            throw new ObjectNotFoundException("Error(s) found with DBLink: " + dbLinkName);
        }
    }

    @Override
    public List findByBranchIdAndDBLink(Long branchId, String dbLinkName) throws ObjectNotFoundException {
        try{
            StringBuilder sqlQueryClause = new StringBuilder();
            sqlQueryClause.append(" select district_Id,  district_Code, district_Name from District")
                    .append(dbLinkName)
                    .append(" where branch_Id = :branchId ")
                    .append(" order by district_Id ");
            Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
            query.setParameter("branchId", branchId);
            return query.getResultList();
        }catch (Exception e){
            throw new ObjectNotFoundException("Error(s) found with branchId: " + branchId + " and dbLink: " + dbLinkName);
        }
    }
}
