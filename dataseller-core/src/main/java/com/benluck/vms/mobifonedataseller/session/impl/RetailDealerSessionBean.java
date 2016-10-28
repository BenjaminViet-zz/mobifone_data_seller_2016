package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.domain.BranchMappingEntity;
import com.benluck.vms.mobifonedataseller.session.RetailDealerLocalBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/5/15
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "RetailDealerSessionEJB")
public class RetailDealerSessionBean implements RetailDealerLocalBean{
    public RetailDealerSessionBean() {
    }

    @PersistenceContext(unitName = "core-data")
    protected EntityManager entityManager;


    @Override
    public List findAllByDBLink(String dbLink) throws ObjectNotFoundException {
        try{
            StringBuilder sqlQueryClause = new StringBuilder();
            sqlQueryClause.append(" select dealer_Id, dealer_Code, dealer_Name, district_Id from Retail_Dealer")
                    .append(dbLink)
                    .append(" order by dealer_Id ");
            Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
            return query.getResultList();
        }catch (Exception e){
            throw new ObjectNotFoundException("Error(s) found with DBLink: " + dbLink);
        }
    }

    @Override
    public List findByBranchIdAndDBLink(Long branchId, String dbLink) throws ObjectNotFoundException{
        try{
            StringBuilder sqlQueryClause = new StringBuilder();
            sqlQueryClause.append(" select rd.dealer_Id, rd.dealer_Code, rd.dealer_Name, rd.district_Id from Retail_Dealer")
                    .append(dbLink).append(" rd ")
                    .append(" where exists (select d.district_Id from District").append(dbLink).append(" d where rd.district_Id = d.district_Id and exists (select b.branch_Id from Branch").append(dbLink).append(" b where b.branch_Id = d.branch_Id and b.branch_Id = :branchId)) ")
                    .append(" order by rd.dealer_Id ");
            Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
            query.setParameter("branchId", branchId);
            return query.getResultList();
        }catch (Exception e){
            throw new ObjectNotFoundException("Error(s) found with branchId: " + branchId + " and DBLink: " + dbLink);
        }
    }

    @Override
    public List findByDistrictIdAndDBLink(Long district_Id, String dbLink) throws ObjectNotFoundException{
        try{
            StringBuilder sqlQueryClause = new StringBuilder();
            sqlQueryClause.append(" select rd.dealer_Id, rd.dealer_Code, rd.dealer_Name, rd.district_Id from Retail_Dealer")
                    .append(dbLink).append(" rd ")
                    .append(" where exists (select d.district_Id from District").append(dbLink).append(" d where rd.district_Id = d.district_Id and d.district_id = :districtId) ")
                    .append(" order by rd.dealer_Id ");
            Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
            query.setParameter("districtId", district_Id);
            return query.getResultList();
        }catch (Exception e){
            throw new ObjectNotFoundException("Error(s) found with districtId: " + district_Id + " and DBLink: " + dbLink);
        }
    }

    @Override
    public Object[] findByEzAndDBLink(Map<String, Object> properties, String dbLinkName) throws ObjectNotFoundException {
        String soEZ = null;
        if(properties.get("ez") != null){
            soEZ = CommonUtil.removeCountryCode(properties.get("ez").toString());
        }
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select rd.dealer_Id, rd.dealer_Code, rd.dealer_Name     ")
                .append("      FROM DEALER_PROPERTIES").append(dbLinkName).append(" dp   ")
                .append("      INNER join Retail_Dealer").append(dbLinkName).append(" rd on dp.dealer_Id = rd.dealer_Id  ")
                .append("      INNER join District").append(dbLinkName).append(" d on d.district_id = rd.district_Id  ")
                .append("      INNER join Branch").append(dbLinkName).append(" b on b.branch_Id = d.branch_id    ")
                .append("  where    ");
        if(properties.get("dealercode") != null){
            sqlQueryClause.append(" rd.dealer_code = :dealer_code ");
        }
        if(properties.get("branchcode") != null){
            sqlQueryClause.append(" and b.BRANCH_CODE = :branch_code ");
        }
        if(properties.get("districtcode") != null){
            sqlQueryClause.append(" and d.DISTRICT_CODE = :district_code ");
        }
        if(properties.get("ez") != null){
            sqlQueryClause.append(" and dp.properties_Value like :ez_isdn ");
        }
        sqlQueryClause.append(" order by rd.dealer_Id ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("dealercode") != null){
            query.setParameter("dealer_code", properties.get("dealercode").toString());
        }
        if(properties.get("branchcode") != null){
            query.setParameter("branch_code", properties.get("branchcode").toString());
        }
        if(properties.get("districtcode") != null){
            query.setParameter("district_code", properties.get("districtcode").toString());
        }
        if(StringUtils.isNotBlank(soEZ)){
            query.setParameter("ez_isdn", soEZ);
        }
        List resultSet = query.getResultList();
        if(resultSet != null && resultSet.size() > 0){
            return (Object[])resultSet.get(0);
        }else{
            throw new ObjectNotFoundException("Not found info for ez_isdn: " + properties.get("ez"));
        }
    }

    @Override
    public Object[] findBySoEZAndDBLinkName(String soEZ, String dbLinkName) {
        soEZ = CommonUtil.removeCountryCode(soEZ);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select dp.properties_Value as soEZ ")
                    .append("           , rd.dealer_Code ")
                    .append("           , rd.dealer_Name ")
                    .append("           , dp.import_DateTime as ngayDangKy ")
                    .append("           , d.district_Name as quanHuyen ")
                    .append("   from retail_Dealer").append(dbLinkName).append(" rd ")
                    .append("   left join district").append(dbLinkName).append(" d on rd.district_Id = d.district_Id ")
                    .append("   left join dealer_Properties").append(dbLinkName).append(" dp on dp.dealer_Id = rd.dealer_Id ")
                    .append("   where dp.properties_Value like :soEZ ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soEZ", "%" + soEZ + "%");
        List resultSet = query.getResultList();
        if(resultSet.size() > 0){
            return (Object[])resultSet.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Object[] findByIdAndDBLinkName(Long dealer_Id, String dbLinkName) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select rd.dealer_Id   ")
                .append("       , rd.dealer_Code ")
                .append("       , rd.dealer_Name ")
                .append("       , rd.district_Id ")
                .append("   from retail_dealer").append(dbLinkName).append(" rd ")
                .append("   where rd.dealer_id = :dealer_Id ")
                .append("   order by rd.dealer_Code ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        List resultSet = query.getResultList();
        if(resultSet.size() > 0){
            return (Object[])resultSet.get(0);
        }
        return null;
    }
}
