package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.BranchMappingEntity;
import com.benluck.vms.mobifonedataseller.session.BranchLocalBean;

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
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BranchSessionEJB")
public class BranchSessionBean implements BranchLocalBean{
    public BranchSessionBean() {
    }

    @PersistenceContext(unitName = "core-data")
    protected EntityManager entityManager;

    @Override
    public List findAllBranchesByDBLink(String dbLinkName) throws ObjectNotFoundException{
        try{
            StringBuilder sqlQueryClause = new StringBuilder();
            sqlQueryClause.append(" select b.branch_Id, b.branch_code, b.branch_name from Branch")
                    .append(dbLinkName)
                    .append(" b order by b.branch_name ");
            Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
            return  query.getResultList();
        }catch (Exception e){
            throw new ObjectNotFoundException("Error(s) found with DBLINK: " + dbLinkName);
        }
    }

    @Override
    public Object[] findByBranchIdAndDBLink(Long branchId, String dbLinkName) throws ObjectNotFoundException {
        try{
            StringBuilder sqlQueryClause = new StringBuilder();
            sqlQueryClause.append(" select b.branch_Id, b.branch_code, b.branch_name from Branch")
                    .append(dbLinkName).append(" b ")
                    .append(" where b.branch_Id = :branch_Id ")
                    .append(" order by b.branch_name ");
            Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
            query.setParameter("branch_Id", branchId);
            List resultSet = query.getResultList();
            if(resultSet.size() > 0){
                return (Object[])resultSet.get(0);
            }
            throw new ObjectNotFoundException("Branch not found by branch_Id: " + branchId + " and dbLINK: " + dbLinkName);
        }catch (Exception e){
            throw new ObjectNotFoundException("Error(s) found with branch_Id: " + branchId + " and dbLINK: " + dbLinkName);
        }
    }
}
