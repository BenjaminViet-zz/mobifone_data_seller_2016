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
        StringBuilder sqlSelectQuery = new StringBuilder();
        sqlSelectQuery.append(" SELECT shop_Code, shop_Name, development_Amount1 ,development_Amount2 ,development_Amount3, maintain_Phase1, maintain_Phase2, maintain_Phase3 ")
                    .append(" FROM MOBI_DATA_COST WHERE Cust_ID = :custID ORDER BY shop_Code, shop_Name ASC ");

        Query query = entityManager.createNativeQuery(sqlSelectQuery.toString());
        query.setParameter("custID", Long.valueOf(properties.get("custID").toString()));
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder();
        sqlCountQuery.append(" SELECT COUNT(CostID) FROM MOBI_DATA_COST WHERE Cust_ID = :custID ");
        Query countQuery = entityManager.createNativeQuery(sqlCountQuery.toString()).setParameter("custID", Long.valueOf(properties.get("custID").toString()));
        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] search4DetailExpenseReport(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        StringBuilder sqlSelectQuery = new StringBuilder();
        sqlSelectQuery.append(" SELECT emp_Code, isdn, name, name as tenKhachHang, 'LOAI HM' as loaiHM, bus_Type, cust_Type ")
                    .append("           , sta_DateTime as ngayDauNoi, insert_DateTime as ngayNopHoSo, act_Status as trangThaiChanCat ")
                    .append("           , status as trangThaiThueBao, '0' as cuocThucThu, '0' as chuKy , 0 as hoaHong ")
                    .append(" FROM MOBI_DATA_COST WHERE Cust_ID = :custID ORDER BY name ASC ");

        Query query = entityManager.createNativeQuery(sqlSelectQuery.toString());
        query.setParameter("custID", Long.valueOf(properties.get("custID").toString()));
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder();
        sqlCountQuery.append(" SELECT COUNT(CostID) FROM MOBI_DATA_COST WHERE Cust_ID = :custID ");
        Query countQuery = entityManager.createNativeQuery(sqlCountQuery.toString()).setParameter("custID", Long.valueOf(properties.get("custID").toString()));
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
