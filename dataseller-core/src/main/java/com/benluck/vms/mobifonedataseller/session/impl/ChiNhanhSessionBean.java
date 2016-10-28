package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.ChiNhanhEntity;
import com.benluck.vms.mobifonedataseller.session.ChiNhanhLocalBean;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;


@Stateless(name = "ChiNhanhSessionEJB")
public class ChiNhanhSessionBean extends AbstractSessionBean<ChiNhanhEntity, Long> implements ChiNhanhLocalBean{
    public ChiNhanhSessionBean() {
    }

    @Override
    public List<ChiNhanhEntity> findAllAndSort() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("from ChiNhanhEntity c order by c.chiNhanh asc");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        return (List<ChiNhanhEntity>)query.getResultList();
    }

    @Override
    public Object[] findByDepartmentId(Long departmentId) throws ObjectNotFoundException{
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select c.chiNhanhId, c.chiNhanh, c.name, c.branchId  ")
                    .append("   from Vms_chiNhanh c ")
                    .append("   inner join Vms_shopCode_chiNhanh sc on sc.chiNhanhId = c.chiNhanhId ")
                    .append("   inner join Vms_department d on d.code = sc.shopCode ")
                    .append("   where d.departmentId = :departmentId ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("departmentId", departmentId);
        List resultSet = query.getResultList();
        if(resultSet != null && resultSet.size() > 0){
            return (Object[])resultSet.get(0);
        }else{
            throw new ObjectNotFoundException("Can not find out ChiNhanh for departmentId: " + departmentId);
        }
    }
}
