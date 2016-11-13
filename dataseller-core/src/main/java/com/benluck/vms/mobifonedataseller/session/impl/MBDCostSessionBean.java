package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.MBDCostEntity;
import com.benluck.vms.mobifonedataseller.session.MBDCostLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "MBDCostSessionEJB")
public class MBDCostSessionBean extends AbstractSessionBean<MBDCostEntity, Long> implements MBDCostLocalBean{

    public MBDCostSessionBean() {
    }

    @Override
    public Object[] search4GeneralExpenseReport(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        StringBuilder sqlMainQuery = new StringBuilder();
        sqlMainQuery.append(" FROM MOBI_DATA_COST WHERE 1 = 1");

        if(properties.get("shopCode") != null){
            sqlMainQuery.append(" AND shop_Code LIKE :shopCode ");
        }
        if(properties.get("empCode") != null){
            sqlMainQuery.append(" AND emp_Code LIKE :empCode ");
        }
        if(properties.get("isdn") != null){
            sqlMainQuery.append(" AND isdn LIKE :isdn ");
        }
        if(properties.get("issuedDateTimeFrom") != null){
            sqlMainQuery.append(" AND c.issue_Month >= to_date(substr(:issuedDateTimeFrom,1,10), ' ").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("issuedDateTimeTo") != null){
            sqlMainQuery.append(" AND c.issue_Month <= to_date(substr(:issuedDateTimeTo,1,10), ' ").append(Constants.DB_DATE_FORMAT).append("') ");
        }

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT shop_Code, shop_Name, development_Amount1 ,development_Amount2 ,development_Amount3, maintain_Amount1, maintain_Amount2, maintain_Amount3 ")
                .append(sqlMainQuery.toString())
                .append(" ORDER BY shop_Code, shop_Name, issue_Month ASC ");

        Query query = entityManager.createNativeQuery(sqlQuery.toString());
        if(properties.get("shopCode") != null){
            query.setParameter("shopCode", "'%" + properties.get("shopCode").toString() + "%'");
        }
        if(properties.get("empCode") != null){
            query.setParameter("empCode", "'%" + properties.get("empCode").toString() + "%'");
        }
        if(properties.get("isdn") != null){
            query.setParameter("isdn", "'%" + properties.get("isdn").toString() + "%'");
        }
        if(properties.get("issuedDateTimeFrom") != null){
            query.setParameter("issuedDateTimeFrom", properties.get("issuedDateTimeFrom").toString());
        }
        if(properties.get("issuedDateTimeTo") != null){
            query.setParameter("issuedDateTimeTo", properties.get("issuedDateTimeTo").toString());
        }

        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder();
        sqlCountQuery.append(" SELECT COUNT(CostID) ").append(sqlMainQuery.toString());
        Query countQuery = entityManager.createNativeQuery(sqlCountQuery.toString());
        if(properties.get("shopCode") != null){
            countQuery.setParameter("shopCode", "'%" + properties.get("shopCode").toString() + "%'");
        }
        if(properties.get("empCode") != null){
            countQuery.setParameter("empCode", "'%" + properties.get("empCode").toString() + "%'");
        }
        if(properties.get("isdn") != null){
            countQuery.setParameter("isdn", "'%" + properties.get("isdn").toString() + "%'");
        }
        if(properties.get("issuedDateTimeFrom") != null){
            countQuery.setParameter("issuedDateTimeFrom", properties.get("issuedDateTimeFrom").toString());
        }
        if(properties.get("issuedDateTimeTo") != null){
            countQuery.setParameter("issuedDateTimeTo", properties.get("issuedDateTimeTo").toString());
        }
        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] search4DetailExpenseReport(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        StringBuilder sqlMainQuery = new StringBuilder();
        sqlMainQuery.append(" FROM MOBI_DATA_COST WHERE 1 = 1 ");
        if(properties.get("shopCode") != null){
            sqlMainQuery.append(" AND shop_Code LIKE :shopCode ");
        }
        if(properties.get("empCode") != null){
            sqlMainQuery.append(" AND emp_Code LIKE :empCode ");
        }
        if(properties.get("isdn") != null){
            sqlMainQuery.append(" AND isdn LIKE :isdn ");
        }
        if(properties.get("issuedDateTimeFrom") != null){
            sqlMainQuery.append(" AND issue_Month >= to_date(substr(:issuedDateTimeFrom,1,10), ' ").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("issuedDateTimeTo") != null){
            sqlMainQuery.append(" AND issue_Month <= to_date(substr(:issuedDateTimeTo,1,10), ' ").append(Constants.DB_DATE_FORMAT).append("') ");
        }

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT shop_Code, isdn, name as tenKhachHang, emp_Code as maNVPhatTrien, bus_Type as loaiHM, '' as laiTB, cust_Type as loaiKH ")
                .append("           , sta_DateTime as ngayDauNoi, act_Status as trangThaiChanCat ")
                .append("           , status as trangThaiThueBao")
                .append("           , development_amount1 as chPhiPTDot1, development_amount2 as chPhiPTDot2, development_amount3 as chPhiPTDot3 ")
                .append("           , maintain_amount1 as hoaHongGD1, maintain_amount2 as hoaHongGD2, maintain_amount3 as hoaHongGD3 ")
                .append(sqlMainQuery.toString())
                .append(" ORDER BY name ASC ");


        Query query = entityManager.createNativeQuery(sqlQuery.toString());
        if(properties.get("shopCode") != null){
            query.setParameter("shopCode", "'%" + properties.get("shopCode").toString() + "%'");
        }
        if(properties.get("empCode") != null){
            query.setParameter("empCode", "'%" + properties.get("empCode").toString() + "%'");
        }
        if(properties.get("isdn") != null){
            query.setParameter("isdn", "'%" + properties.get("isdn").toString() + "%'");
        }
        if(properties.get("issuedDateTimeFrom") != null){
            query.setParameter("issuedDateTimeFrom", properties.get("issuedDateTimeFrom").toString());
        }
        if(properties.get("issuedDateTimeTo") != null){
            query.setParameter("issuedDateTimeTo", properties.get("issuedDateTimeTo").toString());
        }

        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);

        List resultSet = query.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder();
        sqlCountQuery.append(" SELECT COUNT(CostID) ").append(sqlMainQuery.toString());
        Query countQuery = entityManager.createNativeQuery(sqlCountQuery.toString());
        if(properties.get("shopCode") != null){
            countQuery.setParameter("shopCode", "'%" + properties.get("shopCode").toString() + "%'");
        }
        if(properties.get("empCode") != null){
            countQuery.setParameter("empCode", "'%" + properties.get("empCode").toString() + "%'");
        }
        if(properties.get("isdn") != null){
            countQuery.setParameter("isdn", "'%" + properties.get("isdn").toString() + "%'");
        }
        if(properties.get("issuedDateTimeFrom") != null){
            countQuery.setParameter("issuedDateTimeFrom", properties.get("issuedDateTimeFrom").toString());
        }
        if(properties.get("issuedDateTimeTo") != null){
            countQuery.setParameter("issuedDateTimeTo", properties.get("issuedDateTimeTo").toString());
        }

        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] searchPaymentListByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems) {
        StringBuilder sqlMainQuery = new StringBuilder();
        sqlMainQuery.append(" FROM MOBI_DATA_COST c ")
                    .append(" WHERE  c.payment_Status = :paymentStatus ");
        if(properties.get("isdn") != null){
            sqlMainQuery.append(" AND c.isdn LIKE :isdn ");
        }
        if (properties.get("name") != null){
            sqlMainQuery.append(" AND c.name LIKE :name ");
        }
        if(properties.get("shopCode") != null){
            sqlMainQuery.append(" AND c.shop_Code LIKE :shopCode ");
        }
        if(properties.get("staDateTimeFrom") != null){
            sqlMainQuery.append(" AND c.sta_dateTime >= to_date(substr(:staDateTimeFrom,1,10), ' ").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("staDateTimeTo") != null){
            sqlMainQuery.append(" AND c.sta_dateTime <= to_date(substr(:staDateTimeTo,1,10), ' ").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT c.costId, c.shop_Code, c.shop_Name, c.isdn, c.name, c.bus_Type as loaiHM, c.cust_Type as loaiKH ")
                .append("           , c.sta_DateTime as ngayKichHoat, c.status as trangThaiThueBao, c.emp_Code as maNhanVienPT ")
                .append("           , c.act_Status as trangThaiChanCat, c.issue_Month as thangTraCuoc, c.payment as cuocThucThu ")
                .append("           , c.development_phase1 as trangThaiPTDot1, c.development_phase2 as trangThaiPTDot2, c.development_phase3 as trangThaiPTDot3 ")
                .append("           , c.development_amount1 as chPhiPTDot1, c.development_amount2 as chPhiPTDot2, c.development_amount3 as chPhiPTDot3 ")
                .append("           , c.maintain_phase1 as trangThaiChiPhiDuyTriGD1, c.maintain_phase2 as trangThaiChiPhiDuyTriGD2, c.maintain_phase3 as trangThaiChiPhiDuyTriGD3 ")
                .append("           , c.maintain_amount1 as hoaHongGD1, c.maintain_amount2 as hoaHongGD2, c.maintain_amount3 as hoaHongGD3, c.payment_Status, c.payment_Date ")
                .append(sqlMainQuery.toString())
                .append(" ORDER BY c. ").append(sortExpression);

        if(sortDirection.equals(Constants.SORT_DESC)){
            sqlQuery.append(" DESC ");
        }else{
            sqlQuery.append(" ASC ");
        }

        Query query = entityManager.createNativeQuery(sqlQuery.toString());
        query.setParameter("paymentStatus", properties.get("paymentStatus".toString()));
        if(properties.get("isdn") != null){
            query.setParameter("isdn", "%'" + properties.get("isdn").toString() + "%'");
        }
        if (properties.get("name") != null){
            query.setParameter("name", "%'" + properties.get("name").toString() + "%'");
        }
        if(properties.get("shopCode") != null){
            query.setParameter("shopCode", "%'" + properties.get("shopCode").toString() + "%'");
        }
        if(properties.get("staDateTimeFrom") != null){
            query.setParameter("staDateTimeFrom", properties.get("staDateTimeFrom").toString());
        }
        if(properties.get("staDateTimeTo") != null){
            query.setParameter("staDateTimeTo", properties.get("staDateTimeTo").toString());
        }

        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);

        List resultSet = query.getResultList();

        StringBuilder sqlCount = new StringBuilder();
        sqlCount.append(" SELECT COUNT(c.costId) ").append(sqlMainQuery.toString());
        Query countQuery = entityManager.createNativeQuery(sqlCount.toString());
        countQuery.setParameter("paymentStatus", properties.get("paymentStatus".toString()));
        if(properties.get("isdn") != null){
            countQuery.setParameter("isdn", "%'" + properties.get("isdn").toString() + "%'");
        }
        if (properties.get("name") != null){
            countQuery.setParameter("name", "%'" + properties.get("name").toString() + "%'");
        }
        if(properties.get("shopCode") != null){
            countQuery.setParameter("shopCode", "%'" + properties.get("shopCode").toString() + "%'");
        }
        if(properties.get("staDateTimeFrom") != null){
            countQuery.setParameter("staDateTimeFrom", properties.get("staDateTimeFrom").toString());
        }
        if(properties.get("staDateTimeTo") != null){
            countQuery.setParameter("staDateTimeTo", properties.get("staDateTimeTo").toString());
        }
        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }
}
