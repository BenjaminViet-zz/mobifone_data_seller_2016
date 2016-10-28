package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.domain.ActionLogEntity;
import com.benluck.vms.mobifonedataseller.session.ActionLogLocalBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/16/15
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ActionLogSessionEJB")
public class ActionLogSessionBean extends AbstractSessionBean<ActionLogEntity, Long> implements ActionLogLocalBean{
    public ActionLogSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  FROM ACTION_LOG a ")
                .append("   INNER JOIN VMS_USER u ON u.userId = a.userId ")
                .append("   INNER JOIN RETAIL_DEALER@MKITDW_CSDBL_DBL rd ON rd.dealer_Id = a.column_id_log_value ")
                .append("   INNER JOIN DISTRICT@MKITDW_CSDBL_DBL d ON rd.district_Id = d.district_Id ")
                .append("   INNER JOIN BRANCH@MKITDW_CSDBL_DBL b ON b.branch_Id = d.branch_Id ")
                .append("   WHERE 1 = 1 ");

        if(properties.get("branchId") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.district_Id = :district_Id ");
        }
        if(properties.get("retailDealerId") != null){
            mainQuery.append(" and rd.dealer_Id = :retailDealer_Id ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" SELECT  ")
                .append("              u.displayName as nguoiChiTra, ")
                .append("              (SELECT sum(rt.prom_Amount) FROM REGISTRATION_TRANS@MKITDW_CSDBL_DBL rt WHERE rt.dealer_Id = a.column_id_log_value " )
                .append("                   AND rt.payment_Date >= TO_DATE(substr(a.support_data, instr(a.support_data, '=', -1) + 1, length(a.support_data)), 'YYYY-MM-DD')    ")
                .append("                   AND rt.payment_Date < TO_DATE(substr(a.support_data, instr(a.support_data, '=', -1) + 1, length(a.support_data)), 'YYYY-MM-DD') + 1 ")
                .append("              ) AS total,     ")
                .append("              (SELECT DISTINCT rt.payment_Date ")
                .append("               FROM REGISTRATION_TRANS@MKITDW_CSDBL_DBL rt ")
                .append("               WHERE rt.dealer_Id = a.column_id_log_value ")
                .append("                   AND rt.payment_Date >= TO_DATE(substr(a.support_Data, instr(a.support_Data, '=', -1) + 1, length(a.support_Data)), 'YYYY-MM-DD') ")
                .append("                   AND rt.payment_Date < TO_DATE(substr(a.support_data, instr(a.support_data, '=', -1) + 1, length(a.support_data)), 'YYYY-MM-DD') + 1 ")
                .append("              ) as ngayChiTra, ")
                .append("              rd.dealer_Code as maDiemBanHang, ")
                .append("              rd.dealer_Name as tenDiemBanHang ")
                .append(mainQuery)
                .append("       ORDER BY u.displayName, rd.dealer_Code, rd.dealer_Name ");

        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" ORDER BY rt.").append(sortExpression);
        }else{
            sqlQueryClause.append(" ORDER BY u.displayName, rt.payment_Date, rd.dealer_Code, rd.dealer_Name ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("retailDealerId") != null){
            queryClause.setParameter("retailDealer_Id", Long.valueOf(properties.get("retailDealerId").toString()));
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(rd.dealer_Name)").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("retailDealerId") != null){
            countQuery.setParameter("retailDealer_Id", Long.valueOf(properties.get("retailDealerId").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public void removeMaPhieuGiftHistory(String ticketCode) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" UPDATE ACTION_LOG a ")
                .append("       SET a.support_Data = REPLACE(REPLACE( REPLACE(a.support_Data, :maPhieu2), :maPhieu3 ), :maPhieu1)  ")
                .append("       WHERE substr(a.support_Data, instr(a.support_Data, '=') + 1, instr(a.support_Data, ';') - instr(a.support_Data, '=') -1) LIKE :maPhieu  ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("maPhieu", "%" + ticketCode + "%");
        query.setParameter("maPhieu2", "#" + ticketCode);
        query.setParameter("maPhieu3", ticketCode + "#");
        query.setParameter("maPhieu1", ticketCode);

        query.executeUpdate();
    }

    @Override
    public Boolean checkLastMaPhieu(String maPhieu) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" SELECT substr(sd, instr(sd, '=') + 1, instr(sd, ';') - instr(sd, '=') -1) ")
                .append("       FROM ( ")
                .append("               SELECT REPLACE(REPLACE( REPLACE(a.support_Data, :maPhieu2), :maPhieu3 ), :maPhieu1) sd ")
                .append("               FROM ACTION_Log a  ")
                .append("               WHERE substr(a.support_Data, instr(a.support_Data, '=') + 1, instr(a.support_Data, ';') - instr(a.support_Data, '=') -1) LIKE :maPhieu )  ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("maPhieu", "%" + maPhieu + "%");
        query.setParameter("maPhieu2", "#" + maPhieu);
        query.setParameter("maPhieu3", maPhieu + "#");
        query.setParameter("maPhieu1", maPhieu);

        if (query.getSingleResult() == null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void removeGiftHistory(String maPhieu) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" DELETE FROM ACTION_LOG a ")
                .append("       WHERE  substr(a.support_Data, instr(a.support_Data, '=') + 1, instr(a.support_Data, ';') - instr(a.support_Data, '=') -1) LIKE :maPhieu  ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("maPhieu", "%" + maPhieu + "%");

        query.executeUpdate();
    }

    @Override
    public List findByMaPhieu(String maPhieu) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" SELECT  a.column_id_log_value, a.support_Data ")
                .append("       FROM ACTION_Log a  ")
                .append("       WHERE substr(a.support_Data, instr(a.support_Data, '=') + 1, instr(a.support_Data, ';') - instr(a.support_Data, '=') -1) LIKE :maPhieu   ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("maPhieu", "%" + maPhieu + "%");

        return query.getResultList();
    }

    @Override
    public Object[] report4KHCN_qStudent(Map<String, Object> properties, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        String soEZ = null;
        if(properties.get("soThueBao") != null){
            soEZ = CommonUtil.removeCountryCode(properties.get("soThueBao").toString());
        }

        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" FROM QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL mp  ")
                .append("  INNER JOIN  ACTION_LOG a ON substr(a.support_Data, instr(a.support_Data, '=') + 1, instr(a.support_Data, ';') - instr(a.support_Data, '=') -1) LIKE mp.ma_phieu  ")
                .append("  LEFT JOIN QSV_2015_GIFT@MKITDW_KM_QSV_DBL g ON g.gift_Id = substr(a.support_Data, instr(a.support_Data, '=', -1) + 1, length(a.support_Data))   ")
                .append("  LEFT JOIN VMS_DEPARTMENT d ON d.CODE = substr(a.support_Data, instr(a.support_Data, '=', 1, 2) + 1, instr(a.support_Data, ';', 1, 2) - instr(a.support_Data, '=', 1, 2) -1) ")
                .append("  LEFT JOIN VMS_USER u ON d.departmentId = u.departmentId ")
                .append("  WHERE 1 = 1 And da_doi_qua = 2 ");

        if(StringUtils.isNotBlank(soEZ)){
            mainQuery.append(" AND mp.thue_bao LIKE :thuebao ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("     AND  a.createdTime >= TO_DATE(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("     AND  a.createdTime < (TO_DATE(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }

        mainQuery.append(" GROUP BY mp.thue_Bao, mp.ma_phieu, g.gift_name, a.createdTime, u.userName, d.name, mp.da_doi_qua  ")
                .append("  ORDER BY mp.thue_Bao, mp.ma_phieu, g.gift_name, a.createdTime, u.userName,  sd.name, mp.da_doi_qua  ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" SELECT mp.thue_bao as thueBao,   ")
                .append("           a.createdTime,  ")
                .append("           u.userName,     ")
                .append("           g.gift_name,    ")
                .append("           mp.ma_phieu,    ")
                .append("           d.name,         ")
                .append("           mp.da_doi_qua   ")
                .append(mainQuery);

        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }

        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(offset);
        queryClause.setMaxResults(limit);


        if(StringUtils.isNotBlank(soEZ)){
            queryClause.setParameter("thuebao", "%" + soEZ + "%");
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        List resultList = queryClause.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder("select count(thueBao) from( ").append(sqlQueryClause).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountQuery.toString());

        if(StringUtils.isNotBlank(soEZ)){
            countQuery.setParameter("thuebao", "%" + soEZ + "%");
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return  new Object[]{count, resultList};
    }
}
