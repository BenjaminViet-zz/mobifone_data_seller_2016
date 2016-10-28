package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemMaPhieuDTO;
import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemSoDiemDTO;
import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemSoDiemStatisticByMonthDTO;
import com.benluck.vms.mobifonedataseller.domain.GiftDeliveryThueBaoHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/11/15
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ThueBaoPhatTrienMoiSessionEJB")
public class ThueBaoPhatTrienMoiSessionBean implements ThueBaoPhatTrienMoiLocalBean {
    public ThueBaoPhatTrienMoiSessionBean() {
    }

    @PersistenceContext(unitName = "core-data")
    protected EntityManager entityManager;

    @Override
    public Boolean checkIfAlreadyRegister4KPP(String thue_bao)throws ObjectNotFoundException{
        thue_bao = CommonUtil.removeCountryCode(thue_bao);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select count(properties_value) from dealer_properties@mkitdw_csdbl_dbl where properties_value like :thue_bao ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + thue_bao);
        Integer count = Integer.valueOf(query.getSingleResult().toString());
        if(count.compareTo(0) > 0){
            return true;
        }else{
            throw new ObjectNotFoundException("So thue bao nay chua dang ky nhan ma OTP cho khach hanh KPP");
        }
    }

    @Override
    public Object[] searchKetQuaThueBaoThamGiaGoiCuoc(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        String soEZ = properties.get("soEZ").toString().replace(Constants.PREFIX_KPP_USERNAME, "");
        soEZ = CommonUtil.removeCountryCode(soEZ);
        StringBuilder mainClause = new StringBuilder();
        mainClause.append("     from registration_trans@mkitdw_csdbl_dbl t ")
                .append("       left join prom_package@mkitdw_csdbl_dbl pk on pk.package_Id = t.package_Id ")
                .append("       where exists (select 1 from retail_dealer@mkitdw_csdbl_dbl r where t.dealer_Id = r.dealer_Id ")
                .append("                           and exists (select 1 from dealer_properties@mkitdw_csdbl_dbl dp where dp.properties_value like :soEZ and dp.dealer_Id = r.dealer_Id)) ");
        if(properties.get("soThueBaoKH") != null){
            mainClause.append(" and t.customer_isdn like :soThueBaoKH ");
        }
        if(properties.get("goiCuocId") != null){
            mainClause.append(" and t.package_Id = :goiCuocId ");
        }
        if(properties.get("fromDate") != null){
            mainClause.append(" and t.trans_date >= to_date(substr(:fromDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("')");
        }
        if(properties.get("toDate") != null){
            mainClause.append(" and t.trans_date < (to_date(substr(:toDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1)");
        }
        if(properties.get("tinhTrangKhuyenKhich") != null){
            mainClause.append(" and t.prom_condition_status = :tinhTrangKhuyenKhich ");
        }
        if(properties.get("tinhTrangGiaoDich") != null){
            mainClause.append(" and t.trans_Status = :tinhTrangGiaoDich ");
        }


        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select t.ez_isdn as soEZDangKy, ")
                .append("               t.customer_isdn as soThueBaoKH, ")
                .append("               pk.package_Name as tenGoi, ")
                .append("               pk.prom_amount as mucKhuyenKhich, ")
                .append("               t.trans_date as ngayDangKy, ")
                .append("               t.reg_position_status as viTriPhatSinh, ")
                .append("               t.prom_condition_status as tinhTrangKhuyenKhich, ")
                .append("               t.trans_status as tinhTrangGiaoDich ")
                .append(mainClause);
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by t.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by t.trans_date ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }

        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);
        queryClause.setParameter("soEZ", "%" + soEZ + "%");
        if(properties.get("soThueBaoKH") != null){
            String soThueBaoKH = CommonUtil.removeCountryCode(properties.get("soThueBaoKH").toString());
            queryClause.setParameter("soThueBaoKH", "%" + soThueBaoKH + "%");
        }
        if(properties.get("goiCuocId") != null){
            queryClause.setParameter("goiCuocId", Long.valueOf(properties.get("goiCuocId").toString()));
        }
        if(properties.get("fromDate") != null){
            queryClause.setParameter("fromDate", properties.get("fromDate").toString());
        }
        if(properties.get("toDate") != null){
            queryClause.setParameter("toDate", properties.get("toDate").toString());
        }
        if(properties.get("tinhTrangKhuyenKhich") != null){
            queryClause.setParameter("tinhTrangKhuyenKhich", properties.get("tinhTrangKhuyenKhich").toString());
        }
        if(properties.get("tinhTrangGiaoDich") != null){
            queryClause.setParameter("tinhTrangGiaoDich", properties.get("tinhTrangGiaoDich").toString());
        }
        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(t.ez_isdn) ").append(mainClause);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("soEZ", "%" + soEZ + "%");
        if(properties.get("soThueBaoKH") != null){
            String soThueBaoKH = CommonUtil.removeCountryCode(properties.get("soThueBaoKH").toString());
            countQuery.setParameter("soThueBaoKH", "%" + soThueBaoKH + "%");
        }
        if(properties.get("goiCuocId") != null){
            countQuery.setParameter("goiCuocId", Long.valueOf(properties.get("goiCuocId").toString()));
        }
        if(properties.get("fromDate") != null){
            countQuery.setParameter("fromDate", properties.get("fromDate").toString());
        }
        if(properties.get("toDate") != null){
            countQuery.setParameter("toDate", properties.get("toDate").toString());
        }
        if(properties.get("tinhTrangKhuyenKhich") != null){
            countQuery.setParameter("tinhTrangKhuyenKhich", properties.get("tinhTrangKhuyenKhich").toString());
        }
        if(properties.get("tinhTrangGiaoDich") != null){
            countQuery.setParameter("tinhTrangGiaoDich", properties.get("tinhTrangGiaoDich"));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{resultSet, count};
    }

    @Override
    public Object[] searchResultOfExchangedUsers(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        String soEZ = null;
        if(properties.get("ez") != null){
            soEZ = CommonUtil.removeCountryCode(properties.get("ez").toString());
        }
        StringBuilder mainClause = new StringBuilder();
        mainClause.append(" SELECT branch_name, ")
                .append(" DISTRICT_NAME, ")
                .append(" dealer_Id, ")
                .append(" dealer_code, ")
                .append(" DEALER_NAME, ")
                .append(" EZ_ISDN, ")
                .append(" SUM_DATE, ")
                .append(" payment_Date, ")
                .append(" SUM(TONGTIENDUOCQUIDOI) as TONGTIENDUOCQUIDOI, ")
                .append(" sum(soTienDuDK) as soTienDuDK, ")
                .append(" payment_status, ")
                .append(" sum(totalTrans_ThoaDKCT) as totalTrans_ThoaDKCT, ")
                .append(" Tax_code,  ")
                .append(" ADDRESS,  ")
                .append(" CONTACT_NAME  ")
                .append(" FROM ")
                .append("    (   SELECT rt.dealer_id, ")
                .append("            rt.dealer_code, ")
                .append("            RT.DEALER_NAME, ")
                .append("            pk.PROM_AMOUNT as TONGTIENDUOCQUIDOI, ")
                .append("            case when t.prom_condition_Status = '1' then t.prom_amount else 0 end as soTienDuDK, ")
                .append("            case when t.prom_condition_Status = '1' then 1 else 0 end as totalTrans_ThoaDKCT, ")
                .append("            t.payment_status             AS payment_status, ")
                .append("            br.branch_name               AS branch_name, ")
                .append("            dt.district_name             AS district_name, ")
                .append("            TRUNC(t.payment_Date, 'DDD') AS payment_Date, ")
                .append("            TRUNC(t.sum_date, 'DDD') AS sum_Date, ")
                .append("            t.EZ_ISDN, ")
                .append("            rt.tax_Code as Tax_code, ")
                .append("            rt.ADDRESS as ADDRESS, ")
                .append("            rt.CONTACT_NAME as CONTACT_NAME ")
                .append("        from REGISTRATION_TRANS@MKITDW_CSDBL_DBL T ")
                .append("        inner join prom_package@MKITDW_CSDBL_DBL pk on pk.package_Id = t.package_Id ")
                .append("        LEFT JOIN retail_dealer@mkitdw_csdbl_dbl rt ")
                .append("            ON rt.dealer_id = t.dealer_id ")
                .append("        LEFT JOIN district@mkitdw_csdbl_dbl dt ")
                .append("            ON rt.district_id = dt.district_id ")
                .append("        LEFT JOIN branch@mkitdw_csdbl_dbl br ")
                .append("            ON dt.branch_id = br.branch_id ")
                .append("        where 1         = 1 ")
                .append("            and T.TRANS_STATUS = 1 and T.SUM_DATE is not null ") ;
        if(properties.get("branch_Id") != null){
            mainClause.append("     and br.branch_id = :branch_id ");
        }
        if(properties.get("district_Id") != null){
            mainClause.append("     and dt.district_id = :district_id ");
        }
        if(properties.get("dealer_Id") != null){
            mainClause.append("     and rt.dealer_id = :dealer_id ");
        }
        if(properties.get("payment_Status") != null){
            mainClause.append("     and t.payment_Status = :payment_Status ");
        }
        if(properties.get("fromDateTime") != null){
            mainClause.append("     and t.SUM_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainClause.append("     and t.SUM_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        if(properties.get("ez") != null){
            mainClause.append("     and t.EZ_ISDN like :ez ");
        }
        mainClause.append(" ) m ");
        mainClause.append(" group by m.dealer_id, ")
                .append(" m.DEALER_CODE, ")
                .append(" m.DEALER_NAME, ")
                .append(" m.payment_status, ")
                .append(" m.BRANCH_NAME, ")
                .append(" m.DISTRICT_NAME, ")
                .append(" m.PAYMENT_DATE, ")
                .append(" m.sum_Date, ")
                .append(" m.EZ_ISDN, ")
                .append(" m.Tax_code,  ")
                .append(" m.ADDRESS,  ")
                .append(" m.CONTACT_NAME  ")
                .append(" order by branch_name, DISTRICT_NAME, dealer_Id, dealer_code, DEALER_NAME, EZ_ISDN, SUM_DATE, PAYMENT_DATE ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" SELECT branch_name, ")
                .append(" DISTRICT_NAME, ")
                .append(" dealer_Id, ")
                .append(" dealer_code, ")
                .append(" DEALER_NAME, ")
                .append(" EZ_ISDN, ")
                .append(" SUM_DATE, ")
                .append(" payment_Date, ")
                .append(" TONGTIENDUOCQUIDOI, ")
                .append(" soTienDuDK, ")
                .append(" payment_status, ")
                .append(" totalTrans_ThoaDKCT, ")
                .append(" Tax_code, ")
                .append(" ADDRESS,  ")
                .append(" CONTACT_NAME  ")
                .append(" from ( ")
                .append(mainClause)
                .append(" ) ");
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("branch_Id") != null){
            queryClause.setParameter("branch_id", properties.get("branch_Id").toString());
        }
        if(properties.get("district_Id") != null){
            queryClause.setParameter("district_id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            queryClause.setParameter("dealer_id", properties.get("dealer_Id").toString());
        }
        if(properties.get("payment_Status") != null){
            queryClause.setParameter("payment_Status", properties.get("payment_Status").toString());
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(StringUtils.isNotBlank(soEZ)){
            queryClause.setParameter("ez", "%" + soEZ + "%");
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(dealer_Id) from ( ").append(mainClause).append(" ) ");

        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());

        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_id", properties.get("branch_Id").toString());
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_id", properties.get("dealer_Id").toString());
        }
        if(properties.get("payment_Status") != null){
            countQuery.setParameter("payment_Status", properties.get("payment_Status").toString());
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(StringUtils.isNotBlank(soEZ)){
            countQuery.setParameter("ez", "%" + soEZ + "%");
        }

        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] searchUserInfo(String thue_bao, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems) {
        thue_bao = CommonUtil.removeCountryCode(thue_bao);

        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  FROM PROM_SUB_LIST@mkitdw_csdbl_dbl psl ")
                .append("   where psl.ISDN = :thue_bao ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select psl.ISDN,psl.ACTIVE_DATETIME,psl.SUB_TYPE ").append(mainQuery);
        if(org.apache.commons.lang3.StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by psl.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by psl.ACTIVE_DATETIME ");
        }
        if(org.apache.commons.lang3.StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            sqlQueryClause.append(" desc ");
        }

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", thue_bao);
        query.setFirstResult(firstItems);
        query.setMaxResults(maxPageItems);

        List list = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(psl.ISDN) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("thue_bao", thue_bao);
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, list};
    }

    @Override
    public Object[] BaoCaoSoEZ_NhanTinThamGiaChuongTrinh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     from REGISTRATION_TRANS@MKITDW_CSDBL_DBL rt ")
                .append("      left join PROM_PACKAGE@MKITDW_CSDBL_DBL pp on pp.PACKAGE_ID = rt.PACKAGE_ID");
        if(properties.get("branch_Id") != null){
            mainQuery.append(" inner join retail_dealer@mkitdw_csdbl_dbl rd on rd.dealer_Id = rt.dealer_Id ");
        }
        mainQuery.append("       where 1 = 1 ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and pp.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        if(properties.get("branch_Id") != null){
            mainQuery.append(" and exists(select d.district_Id from District@mkitdw_csdbl_dbl d where rd.district_Id = d.district_Id and exists (select b.branch_Id from Branch@mkitdw_csdbl_dbl b where b.branch_Id = d.branch_Id and b.branch_Id = :branch_Id)) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select rt.EZ_ISDN as soEZ, ")
                .append("              rt.CUSTOMER_ISDN as soThueBaoKH, ")
                .append("              pp.PACKAGE_NAME as tenGoi, ")
                .append("              rt.TRANS_DATE as ngayDK, ")
                .append("              pp.PROM_AMOUNT as mucKK, ")
                .append("              rt.serial,  ")
                .append("              rt.TRANS_STATUS,  ")
                .append("              rt.TRANS_ERROR  ")
                .append(mainQuery);

        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by rt.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by rt.DEALER_CODE ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(properties.get("branch_Id") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(rt.EZ_ISDN)").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoDiemBanHang(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        String soEZ = null;
        if(properties.get("soEZ") != null){
            soEZ = CommonUtil.removeCountryCode(properties.get("soEZ").toString());
        }

        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     from REGISTRATION_TRANS@MKITDW_CSDBL_DBL rt ")
                .append("       left join PROM_PACKAGE@MKITDW_CSDBL_DBL pp on pp.PACKAGE_ID = rt.PACKAGE_ID")
                .append("       left join RETAIL_DEALER@MKITDW_CSDBL_DBL rd on rd.DEALER_ID = rt.DEALER_ID")
                .append("       left join DISTRICT@MKITDW_CSDBL_DBL d on d.DISTRICT_ID = rd.DISTRICT_ID")
                .append("       left join BRANCH@MKITDW_CSDBL_DBL b on b.BRANCH_ID = d.BRANCH_ID")
                .append("       where 1 = 1 ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and pp.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and b.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("retailDealerId") != null){
            mainQuery.append(" and rd.DEALER_ID = :retailDealer_Id ");
        }
        if(properties.get("conditionStatus") != null){
            mainQuery.append(" and rt.TRANS_STATUS = :condition_Status ");
        }
        if(properties.get("promConditionStatus") != null){
            if (properties.get("promConditionStatus").equals("1")){
                mainQuery.append(" and rt.PROM_CONDITION_STATUS = :promCondition_Status ");
            } else if (properties.get("promConditionStatus").equals("2")){
                mainQuery.append(" and rt.PROM_CONDITION_STATUS = :promCondition_Status or rt.PROM_CONDITION_STATUS = 0 ");
            } else{
                mainQuery.append(" and rt.PROM_CONDITION_STATUS IS NULL  ");
            }
        }
        if(StringUtils.isNotBlank(soEZ)){
            mainQuery.append(" and rt.ez_Isdn like :soEZ ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.sum_Date >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.sum_Date < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        if(properties.get("fromTransDate") != null){
            mainQuery.append(" and rt.trans_Date >= to_date(substr(:fromTransDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toTransDate") != null){
            mainQuery.append(" and rt.trans_Date < (to_date(substr(:toTransDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        mainQuery.append(" group by b.BRANCH_NAME, ")
                .append("           d.DISTRICT_NAME,")
                .append("           rd.DEALER_NAME,")
                .append("           rt.EZ_ISDN,")
                .append("           rt.CUSTOMER_ISDN, ")
                .append("           pp.PACKAGE_NAME, ")
                .append("           rt.TRANS_DATE, ")
                .append("           rt.TRANS_STATUS,")
                .append("           rt.PROM_CONDITION_STATUS, ")
                .append("           rt.TRANS_ERROR, ")
                .append("           rt.PROM_CONDITION_ERROR, ")
                .append("           rt.SERIAL, ")
                .append("           rd.dealer_code ,  ")
                .append("           rt.REG_POSITION_STATUS,   ")
                .append("           rt.sum_Date, ")
                .append("           rt.Active_Datetime, ")
                .append("           rt.event_code, ")
                .append("           rt.event_pos_code, ")
                .append("           rt.event_pos_name, ")
                .append("           rt.Sales_shop_code ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select b.BRANCH_NAME as chiNhanh, ")
                .append("              d.DISTRICT_NAME as quanHuyen,")
                .append("              rd.DEALER_NAME as diemBanHang,")
                .append("              rt.EZ_ISDN as soEZ,")
                .append("              rt.CUSTOMER_ISDN as soThueBaoKH, ")
                .append("              pp.PACKAGE_NAME as tenGoi, ")
                .append("              rt.TRANS_DATE as ngayDK, ")
                .append("              rt.TRANS_STATUS as tinhTrang,")
                .append("              rt.PROM_CONDITION_STATUS as tinhTrangCondition, ")
                .append("              rt.TRANS_ERROR as tinhTrangError, ")
                .append("              rt.PROM_CONDITION_ERROR as tinhTrangConditionError, ")
                .append("              rt.SERIAL as SERIAL, ")
                .append("              rd.dealer_code as maDiemBanHang,  ")
                .append("              rt.REG_POSITION_STATUS as viTriDangKy,   ")
                .append("              rt.sum_Date as ngayTongHop, ")
                .append("              sum(rt.calling_amount), ")
                .append("              sum(rt.data_amount), ")
                .append("              sum(rt.sms_amount), ")
                .append("              sum(rt.others_amount), ")
                .append("              rt.Active_Datetime as ngaKichHoat, ")
                .append("              rt.event_code, ")
                .append("              rt.event_pos_code, ")
                .append("              rt.event_pos_name, ")
                .append("              rt.Sales_shop_code ")
                .append(mainQuery);

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by rt.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by b.BRANCH_NAME, d.DISTRICT_NAME, rd.DEALER_NAME, rt.EZ_ISDN, rt.CUSTOMER_ISDN, pp.PACKAGE_NAME, rt.TRANS_DATE, rt.sum_Date ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("retailDealerId") != null){
            queryClause.setParameter("retailDealer_Id", Long.valueOf(properties.get("retailDealerId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            queryClause.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("promConditionStatus") != null && !properties.get("promConditionStatus").equals("0")){
            queryClause.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(StringUtils.isNotBlank(soEZ)){
            queryClause.setParameter("soEZ", "%" + soEZ + "%");
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(properties.get("fromTransDate") != null){
            queryClause.setParameter("fromTransDate", properties.get("fromTransDate").toString());
        }
        if(properties.get("toTransDate") != null){
            queryClause.setParameter("toTransDate", properties.get("toTransDate").toString());
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(soEZ) from ( ").append(sqlQueryClause).append(" )  ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("retailDealerId") != null){
            countQuery.setParameter("retailDealer_Id", Long.valueOf(properties.get("retailDealerId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            countQuery.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }

        if(properties.get("promConditionStatus") != null && !properties.get("promConditionStatus").equals("0")){
            countQuery.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(StringUtils.isNotBlank(soEZ)){
            countQuery.setParameter("soEZ", "%" + soEZ + "%");
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(properties.get("fromTransDate") != null){
            countQuery.setParameter("fromTransDate", properties.get("fromTransDate").toString());
        }
        if(properties.get("toTransDate") != null){
            countQuery.setParameter("toTransDate", properties.get("toTransDate").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     from REGISTRATION_TRANS@MKITDW_CSDBL_DBL rt ")
                .append("       left join PROM_PACKAGE@MKITDW_CSDBL_DBL pp on pp.PACKAGE_ID = rt.PACKAGE_ID")
                .append("       left join RETAIL_DEALER@MKITDW_CSDBL_DBL rd on rd.DEALER_ID = rt.DEALER_ID")
                .append("       left join DISTRICT@MKITDW_CSDBL_DBL d on d.DISTRICT_ID = rd.DISTRICT_ID")
                .append("       left join BRANCH@MKITDW_CSDBL_DBL b on b.BRANCH_ID = d.BRANCH_ID")
                .append("       where 1 = 1 ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and pp.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and b.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("conditionStatus") != null){
            mainQuery.append(" and rt.TRANS_STATUS = :condition_Status ");
        }
        if(properties.get("promConditionStatus") != null){
            mainQuery.append(" and rt.PROM_CONDITION_STATUS = :promCondition_Status ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select b.BRANCH_NAME as chiNhanh, ")
                .append("              d.DISTRICT_NAME as quanHuyen,")
                .append("              pp.PACKAGE_NAME as tenGoi, ")
                .append("              rt.TRANS_DATE as ngayDK, ")
                .append("              rt.TRANS_STATUS as tinhTrang,")
                .append("              rt.PROM_CONDITION_STATUS as tinhTrangCondition,")
                .append("              rt.TRANS_ERROR as tinhTrangError,")
                .append("              rt.PROM_CONDITION_ERROR as tinhTrangConditionError")
                .append(mainQuery);

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by rt.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by b.BRANCH_NAME, d.DISTRICT_NAME, pp.PACKAGE_NAME  ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            queryClause.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("promConditionStatus") != null){
            queryClause.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(d.DISTRICT_NAME)").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            countQuery.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("promConditionStatus") != null){
            countQuery.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     from REGISTRATION_TRANS@MKITDW_CSDBL_DBL rt ")
                .append("       left join PROM_PACKAGE@MKITDW_CSDBL_DBL pp on pp.PACKAGE_ID = rt.PACKAGE_ID")
                .append("       left join RETAIL_DEALER@MKITDW_CSDBL_DBL rd on rd.DEALER_ID = rt.DEALER_ID")
                .append("       left join DISTRICT@MKITDW_CSDBL_DBL d on d.DISTRICT_ID = rd.DISTRICT_ID")
                .append("       left join BRANCH@MKITDW_CSDBL_DBL b on b.BRANCH_ID = d.BRANCH_ID")
                .append("       where 1 = 1 ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and pp.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and b.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("conditionStatus") != null){
            mainQuery.append(" and rt.TRANS_STATUS = :condition_Status ");
        }
        if(properties.get("promConditionStatus") != null){
            mainQuery.append(" and rt.PROM_CONDITION_STATUS = :promCondition_Status ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select b.BRANCH_NAME as chiNhanh, ")
                .append("              pp.PACKAGE_NAME as tenGoi, ")
                .append("              rt.TRANS_DATE as ngayDK, ")
                .append("              rt.TRANS_STATUS as tinhTrang,")
                .append("              rt.PROM_CONDITION_STATUS as tinhTrangCondition,")
                .append("              rt.TRANS_ERROR as tinhTrangError,")
                .append("              rt.PROM_CONDITION_ERROR as tinhTrangConditionError")
                .append(mainQuery)
                .append("              group by b.BRANCH_NAME, pp.PACKAGE_NAME, rt.TRANS_DATE, rt.TRANS_STATUS,         ")
                .append("              rt.PROM_CONDITION_STATUS, rt.TRANS_ERROR, rt.PROM_CONDITION_ERROR         ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by rt.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by b.BRANCH_NAME, pp.PACKAGE_NAME ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            queryClause.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("promConditionStatus") != null){
            queryClause.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(b.BRANCH_NAME)").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            countQuery.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("promConditionStatus") != null){
            countQuery.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoTongHopKetQuaChuongTrinh_TheoDiemBanHang(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("     select branch_Name, district_Name, dealer_Code, dealer_Name, ez_isdn,  ")
                .append("      sum(BTG_MOI) as BTG_MOI,     ")
                .append("      sum(BTG_TON) as BTG_TON,     ")
                .append("      sum(PK1_DKTC) as T50_DKTC,   ")
                .append("      sum(PK2_DKTC) as \"3T50_DKTC\",  ")
                .append("      sum(PK3_DKTC) as T100_DKTC,  ")
                .append("      sum(PK4_DKTC) as \"3T100_DKTC\", ")
                .append("      sum(PK1_DCT) as T50_DCT,   ")
                .append("      sum(PK2_DCT) as \"3T50_DCT\",  ")
                .append("      sum(PK3_DCT) as T100_DCT,  ")
                .append("      sum(PK4_DCT) as \"3T100_DCT\", ")
                .append("      branch_id,district_id,dealer_id,    ")
                .append("      sum(price * BTG_MOI) as DT_BTG_Moi,   ")
                .append("      sum(price * BTG_TON) as DT_BTG_Ton,  ")
                .append("      package_name  ")
                .append("      from ( ")
                .append("      select b.BRANCH_NAME, d.DISTRICT_NAME, rd.DEALER_CODE, rd.DEALER_NAME,rt.ez_isdn, rt.trans_date,b.branch_id, ")
                .append("      d.district_id,rt.dealer_id,pk.package_id, pk.price, pk.package_name  ")
                .append("      , case when ps.isdn = rt.customer_isdn and ps.sub_type = 'PTM' then 1 else 0 end BTG_MOI  ")
                .append("      , case when ps.isdn = rt.customer_isdn and ps.sub_type = 'TON' then 1 else 0 end BTG_TON  ")
                .append("      , case when pk.package_code = 'T50'  and rt.trans_Status = '1' then 1 else 0 end PK1_DKTC  ")
                .append("      , case when pk.package_code = '3T50'  and rt.trans_Status = '1' then 1 else 0 end PK2_DKTC  ")
                .append("      , case when pk.package_code = 'T100'  and rt.trans_Status = '1' then 1 else 0 end PK3_DKTC  ")
                .append("      , case when pk.package_code = '3T100' and rt.trans_status = '1' then 1 else 0 end PK4_DKTC  ")
                .append("      , case when pk.package_code = 'T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK1_DCT  ")
                .append("      , case when pk.package_code = '3T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK2_DCT  ")
                .append("      , case when pk.package_code = 'T100'  and rt.prom_condition_status = '1' then 1 else 0 end PK3_DCT  ")
                .append("      , case when pk.package_code = '3T100' and rt.prom_condition_status = '1' then 1 else 0 end PK4_DCT  ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on rt.customer_isdn = ps.isdn ) rt ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and rt.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and rt.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("retailDealerId") != null){
            mainQuery.append(" and rt.DEALER_ID = :retailDealer_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        mainQuery.append("      group by rt.branch_Name, rt.district_Name, rt.dealer_Code, rt.dealer_Name, rt.ez_isdn,  ")
                .append("       rt.branch_id, rt.district_id, rt.dealer_id, rt.package_name  ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name, ort.district_Name, ort.dealer_Name, ort.ez_isdn ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("retailDealerId") != null){
            queryClause.setParameter("retailDealer_Id", Long.valueOf(properties.get("retailDealerId").toString()));
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.EZ_ISDN)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("retailDealerId") != null){
            countQuery.setParameter("retailDealer_Id", Long.valueOf(properties.get("retailDealerId").toString()));
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoTongHopKetQuaChuongTrinh_TheoQuanHuyen(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("     select branch_Name, district_Name,  ")
                .append("      sum(BTG_MOI) as BTG_MOI,     ")
                .append("      sum(BTG_TON) as BTG_TON,     ")
                .append("      sum(PK1_DKTC) as T50_DKTC,   ")
                .append("      sum(PK2_DKTC) as \"3T50_DKTC\",  ")
                .append("      sum(PK3_DKTC) as T100_DKTC,  ")
                .append("      sum(PK4_DKTC) as \"3T100_DKTC\", ")
                .append("      sum(PK1_DCT) as T50_DCT,   ")
                .append("      sum(PK2_DCT) as \"3T50_DCT\",  ")
                .append("      sum(PK3_DCT) as T100_DCT,  ")
                .append("      sum(PK4_DCT) as \"3T100_DCT\", ")
                .append("      COUNT(DISTINCT  DEALER_ID) As sLDBHThamGia, ")
                .append("      branch_id,district_id,  ")
                .append("      sum(price * BTG_MOI) as DT_BTG_Moi,   ")
                .append("      sum(price * BTG_TON) as DT_BTG_Ton,  ")
                .append("      package_name  ")
                .append("      from ( ")
                .append("      select b.BRANCH_NAME, d.DISTRICT_NAME, rt.trans_date,b.branch_id, ")
                .append("      rd.DEALER_ID, d.district_id,pk.package_id, pk.price, pk.package_name  ")
                .append("      , case when ps.isdn = rt.customer_isdn and ps.sub_type = 'PTM' then 1 else 0 end BTG_MOI  ")
                .append("      , case when ps.isdn = rt.customer_isdn and ps.sub_type = 'TON' then 1 else 0 end BTG_TON  ")
                .append("      , case when pk.package_code = 'T50'  and rt.trans_Status = '1' then 1 else 0 end PK1_DKTC  ")
                .append("      , case when pk.package_code = '3T50'  and rt.trans_Status = '1' then 1 else 0 end PK2_DKTC  ")
                .append("      , case when pk.package_code = 'T100'  and rt.trans_Status = '1' then 1 else 0 end PK3_DKTC  ")
                .append("      , case when pk.package_code = '3T100' and rt.trans_status = '1' then 1 else 0 end PK4_DKTC  ")
                .append("      , case when pk.package_code = 'T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK1_DCT  ")
                .append("      , case when pk.package_code = '3T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK2_DCT  ")
                .append("      , case when pk.package_code = 'T100'  and rt.prom_condition_status = '1' then 1 else 0 end PK3_DCT  ")
                .append("      , case when pk.package_code = '3T100' and rt.prom_condition_status = '1' then 1 else 0 end PK4_DCT  ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on rt.customer_isdn = ps.isdn ) rt ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and rt.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and rt.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        mainQuery.append("      group by rt.branch_Name, rt.district_Name,  ")
                .append("       rt.branch_id, rt.district_id, rt.package_name  ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name, ort.district_Name  ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.district_id)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoTongHopKetQuaChuongTrinh_TheoChiNhanh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("     select branch_Name,  ")
                .append("      sum(BTG_MOI) as BTG_MOI,     ")
                .append("      sum(BTG_TON) as BTG_TON,     ")
                .append("      sum(PK1_DKTC) as T50_DKTC,   ")
                .append("      sum(PK2_DKTC) as \"3T50_DKTC\",  ")
                .append("      sum(PK3_DKTC) as T100_DKTC,  ")
                .append("      sum(PK4_DKTC) as \"3T100_DKTC\", ")
                .append("      sum(PK1_DCT) as T50_DCT,   ")
                .append("      sum(PK2_DCT) as \"3T50_DCT\",  ")
                .append("      sum(PK3_DCT) as T100_DCT,  ")
                .append("      sum(PK4_DCT) as \"3T100_DCT\", ")
                .append("      COUNT(DISTINCT DEALER_ID) As sLDBHThamGia, ")
                .append("      branch_id,  ")
                .append("      sum(price * BTG_MOI) as DT_BTG_Moi,   ")
                .append("      sum(price * BTG_TON) as DT_BTG_Ton,  ")
                .append("      package_name  ")
                .append("      from ( ")
                .append("      select b.BRANCH_NAME, rt.trans_date,b.branch_id, rt.PROM_CONDITION_STATUS, rt.TRANS_STATUS, rt.PAYMENT_STATUS, ")
                .append("      rd.DEALER_ID, pk.package_id, pk.price, pk.package_name  ")
                .append("      , case when ps.isdn = rt.customer_isdn and ps.sub_type = 'PTM' then 1 else 0 end BTG_MOI  ")
                .append("      , case when ps.isdn = rt.customer_isdn and ps.sub_type = 'TON' then 1 else 0 end BTG_TON  ")
                .append("      , case when pk.package_code = 'T50'  and rt.trans_Status = '1' then 1 else 0 end PK1_DKTC  ")
                .append("      , case when pk.package_code = '3T50'  and rt.trans_Status = '1' then 1 else 0 end PK2_DKTC  ")
                .append("      , case when pk.package_code = 'T100'  and rt.trans_Status = '1' then 1 else 0 end PK3_DKTC  ")
                .append("      , case when pk.package_code = '3T100' and rt.trans_status = '1' then 1 else 0 end PK4_DKTC  ")
                .append("      , case when pk.package_code = 'T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK1_DCT  ")
                .append("      , case when pk.package_code = '3T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK2_DCT  ")
                .append("      , case when pk.package_code = 'T100'  and rt.prom_condition_status = '1' then 1 else 0 end PK3_DCT  ")
                .append("      , case when pk.package_code = '3T100' and rt.prom_condition_status = '1' then 1 else 0 end PK4_DCT  ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on rt.customer_isdn = ps.isdn ) rt ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and rt.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        mainQuery.append("      group by rt.branch_Name, rt.package_name, ")
                .append("      rt.branch_id   ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.branch_id)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoDiemBanHang(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) throws ObjectNotFoundException{
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("    from Registration_Trans@MKITDW_CSDBL_DBL rt ")
                .append("    left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id ")
                .append("    left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id ")
                .append("    left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id ")
                .append("    left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on SUBSTR(rt.customer_isdn,2) = ps.isdn ")
                .append("    where 1=1 and ")
                .append("    rt.trans_status = '1' and rt.prom_condition_status = '1' and rt.prom_amount > 0 ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and b.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.DISTRICT_ID = :district_Id ");
        }
        if (properties.get("retailDealerId") != null){
            mainQuery.append(" and rd.dealer_id = :retailDealer_Id");
        }
        if(properties.get("conditionStatus") != null){
            mainQuery.append(" and rt.TRANS_STATUS = :condition_Status ");
        }
        if(properties.get("paymentStatus") != null){
            mainQuery.append(" and rt.PAYMENT_STATUS = :payment_Status ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.sum_Date >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.sum_Date < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        if(properties.get("fromPaymentDateTime") != null){
            mainQuery.append(" and rt.PAYMENT_DATE >= to_date(substr(:fromPaymentDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toPaymentDateTime") != null){
            mainQuery.append(" and rt.PAYMENT_DATE < (to_date(substr(:toPaymentDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        mainQuery.append(" group by  ")
                .append("    branch_name, ")
                .append("    district_name, ")
                .append("    dealer_code, ")
                .append("    ez_isdn , ")
                .append("    b.branch_id, ")
                .append("    d.district_id, ")
                .append("    rt.dealer_id, ")
                .append("    rd.DEALER_NAME, ")
                .append("    rd.tax_Code , ")
                .append("    rd.ADDRESS , ")
                .append("    rd.CONTACT_NAME  ");
        if(properties.get("loaiBaoCao") != null && properties.get("loaiBaoCao").toString() == "0"){
            mainQuery.append("  , TO_DATE(rt.trans_date) + 3 , ")
                    .append("    rt.trans_date, ")
                    .append("    rt.payment_date, ")
                    .append("    rt.sum_date,  ")
                    .append("    rt.serial ");
        }
        mainQuery.append(" order by branch_name, district_name, DEALER_NAME, dealer_code, ez_isdn   ");
        if (properties.get("loaiBaoCao") != null && properties.get("loaiBaoCao").toString() == "0" ){
            mainQuery.append("  , TO_DATE(rt.trans_date) + 3 , sum_date, payment_date  ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("    select ")
                .append("    branch_name as CHINHANH, ")
                .append("    district_name as QUANHUYEN, ")
                .append("    dealer_code as MA_DBH, ")
                .append("    ez_isdn as SO_EZ ");
                if(properties.get("loaiBaoCao") != null && properties.get("loaiBaoCao").toString() == "0"){
                    sqlQueryClause.append("    , sum(case when rt.PAYMENT_STATUS = 1 then rt.prom_amount else 0 end  ) as SOTIENDACHI,  ");
                }else {
                    sqlQueryClause.append("  ,  sum(rt.prom_amount) as tongTien,  ");
                }

        sqlQueryClause.append("    b.branch_id, ")
                .append("    d.district_id, ")
                .append("    rt.dealer_id, ")
                .append("    rd.DEALER_NAME as DBH, ")
                .append("    rd.tax_Code as Tax_code, ")
                .append("    rd.ADDRESS as ADDRESS, ")
                .append("    rd.CONTACT_NAME as CONTACT_NAME ");
        if(properties.get("loaiBaoCao") != null && properties.get("loaiBaoCao").toString() == "0"){
            sqlQueryClause.append(" ,   TO_DATE(rt.trans_date) + 3 as THOIGIANPHAICHITRA, ")
                    .append("    rt.payment_date as THOIGIANTHUCCHI, ")
                    .append("    rt.sum_date as ngayTonghop,  ")
                    .append("    rt.serial, ")
                    .append("    sum(rt.prom_amount) as soTienKhuyenKich ");
        }
        sqlQueryClause.append(mainQuery);

        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());

        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("retailDealerId") != null){
            queryClause.setParameter("retailDealer_Id", Long.valueOf(properties.get("retailDealerId").toString()));
        }

        if(properties.get("conditionStatus") != null){
            queryClause.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            queryClause.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            queryClause.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(properties.get("fromPaymentDateTime") != null){
            queryClause.setParameter("fromPaymentDate", properties.get("fromPaymentDateTime").toString());
        }
        if(properties.get("toPaymentDateTime") != null){
            queryClause.setParameter("toPaymentDate", properties.get("toPaymentDateTime").toString());
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" SELECT COUNT(CHINHANH) from ( ").append(sqlQueryClause).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("retailDealerId") != null){
            countQuery.setParameter("retailDealer_Id", Long.valueOf(properties.get("retailDealerId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            countQuery.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            countQuery.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            countQuery.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(properties.get("fromPaymentDateTime") != null){
            countQuery.setParameter("fromPaymentDate", properties.get("fromPaymentDateTime").toString());
        }
        if(properties.get("toPaymentDateTime") != null){
            countQuery.setParameter("toPaymentDate", properties.get("toPaymentDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};

    }

    @Override
    public Object[] baoCaoTinNhan_TheoDiemBanHang(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("      select branch_Name, district_Name, dealer_Code, dealer_Name, ez_isdn,  ")
                .append("      sum(DKTC) as SL_DKTC,     ")
                .append("      sum(DCT) as SL_DCT,     ")
                .append("      sum(KTC) as SL_KTC,  ")
                .append("      branch_id,district_id,dealer_id  ")
                .append("      from ( ")
                .append("      select b.BRANCH_NAME, d.DISTRICT_NAME, rd.DEALER_CODE, rd.DEALER_NAME,rt.ez_isdn, b.branch_id, ")
                .append("      d.district_id,rt.dealer_id  ")
                .append("      , case when rt.trans_status = '1' then 1 else 0 end DKTC  ")
                .append("      , case when rt.prom_condition_status = '1' then 1 else 0 end DCT  ")
                .append("      , case when rt.trans_status = '0' then 1 else 0 end KTC  ")
                .append("      , rt.package_Id ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      ) rt ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and rt.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and rt.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("retailDealerId") != null){
            mainQuery.append(" and rt.DEALER_ID = :retailDealer_Id ");
        }
        mainQuery.append("      group by rt.branch_Name, rt.district_Name, rt.dealer_Code, rt.dealer_Name, rt.ez_isdn,  ")
                .append("      rt.branch_id, rt.district_id, rt.dealer_id  ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name, ort.district_Name, ort.dealer_Name, ort.ez_isdn ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
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

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.EZ_ISDN)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
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
    public Object[] baoCaoTinNhan_TheoQuanHuyen(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("     select branch_Name, district_Name,  ")
                .append("      sum(DKTC) as SL_DKTC,     ")
                .append("      sum(DCT) as SL_DCT,     ")
                .append("      sum(KTC) as SL_KTC,  ")
                .append("      branch_id,district_id  ")
                .append("      from ( ")
                .append("      select b.BRANCH_NAME, d.DISTRICT_NAME, b.branch_id, ")
                .append("      d.district_id  ")
                .append("      , case when rt.trans_status = '1' then 1 else 0 end DKTC  ")
                .append("      , case when rt.prom_condition_status = '1' then 1 else 0 end DCT  ")
                .append("      , case when rt.trans_status = '0' then 1 else 0 end KTC  ")
                .append("      , rt.package_Id  ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      ) rt ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and rt.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and rt.DISTRICT_ID = :district_Id ");
        }
        mainQuery.append("      group by rt.branch_Name, rt.district_Name,  ")
                .append("      rt.branch_id, rt.district_id  ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name, ort.district_Name  ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.district_Name)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoTinNhan_TheoChiNhanh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("     select branch_Name,  ")
                .append("      sum(DKTC) as SL_DKTC,     ")
                .append("      sum(DCT) as SL_DCT,     ")
                .append("      sum(KTC) as SL_KTC,  ")
                .append("      branch_id  ")
                .append("      from ( ")
                .append("      select b.BRANCH_NAME, b.branch_id ")
                .append("      , case when rt.trans_status = '1' then 1 else 0 end DKTC  ")
                .append("      , case when rt.prom_condition_status = '1' then 1 else 0 end DCT  ")
                .append("      , case when rt.trans_status = '0' then 1 else 0 end KTC  ")
                .append("      , rt.package_Id ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      ) rt ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and rt.BRANCH_ID = :branch_Id ");
        }
        mainQuery.append("      group by rt.branch_Name,  ")
                .append("      rt.branch_id  ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());

        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.branch_Name)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoDiemBanHang(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("     select branch_Name, district_Name, dealer_Code, dealer_Name, customer_isdn, LOAITB,   ")
                .append("      sum(PK1_DKTC) as T50_DKTC,   ")
                .append("      sum(PK2_DKTC) as \"3T50_DKTC\",  ")
                .append("      sum(PK3_DKTC) as T100_DKTC,  ")
                .append("      sum(PK4_DKTC) as \"3T100_DKTC\", ")
                .append("      sum(PK1_DCT) as T50_DCT,   ")
                .append("      sum(PK2_DCT) as \"3T50_DCT\",  ")
                .append("      sum(PK3_DCT) as T100_DCT,  ")
                .append("      sum(PK4_DCT) as \"3T100_DCT\", ")
                .append("      sum(PK1_KCT) as T50_KCT,   ")
                .append("      sum(PK2_KCT) as \"3T50_KCT\",  ")
                .append("      sum(PK3_KCT) as T100_KCT,  ")
                .append("      sum(PK4_KCT) as \"3T100_KCT\", ")
                .append("      rt.REG_POSITION_STATUS As viTriDK ")
                .append("      from ( ")
                .append("      select b.BRANCH_NAME, d.DISTRICT_NAME, rd.DEALER_CODE, rd.DEALER_NAME, rt.customer_isdn, rt.REG_POSITION_STATUS,  ")
                .append("      b.branch_id,d.district_id,rd.dealer_id,pk.package_id              ")
                .append("      , CASE WHEN ps.isdn = SUBSTR(rt.customer_isdn,2) AND ps.sub_type = 'PTM' THEN 'MOI' ELSE 'TON' END LOAITB  ")
                .append("      , case when pk.package_code = 'T50'  and rt.trans_Status = '1' then 1 else 0 end PK1_DKTC  ")
                .append("      , case when pk.package_code = '3T50'  and rt.trans_Status = '1' then 1 else 0 end PK2_DKTC  ")
                .append("      , case when pk.package_code = 'T100'  and rt.trans_Status = '1' then 1 else 0 end PK3_DKTC  ")
                .append("      , case when pk.package_code = '3T100' and rt.trans_status = '1' then 1 else 0 end PK4_DKTC  ")
                .append("      , case when pk.package_code = 'T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK1_DCT  ")
                .append("      , case when pk.package_code = '3T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK2_DCT  ")
                .append("      , case when pk.package_code = 'T100'  and rt.prom_condition_status = '1' then 1 else 0 end PK3_DCT  ")
                .append("      , case when pk.package_code = '3T100' and rt.prom_condition_status = '1' then 1 else 0 end PK4_DCT  ")
                .append("      , case when pk.package_code = 'T50'  and rt.prom_condition_status = '0' then 1 else 0 end PK1_KCT  ")
                .append("      , case when pk.package_code = '3T50'  and rt.prom_condition_status = '0' then 1 else 0 end PK2_KCT  ")
                .append("      , case when pk.package_code = 'T100'  and rt.prom_condition_status = '0' then 1 else 0 end PK3_KCT  ")
                .append("      , case when pk.package_code = '3T100' and rt.prom_condition_status = '0' then 1 else 0 end PK4_KCT  ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on SUBSTR(rt.customer_isdn,2) = ps.isdn ) rt ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and rt.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and rt.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("retailDealerId") != null){
            mainQuery.append(" and rt.DEALER_ID = :retailDealer_Id ");
        }
        mainQuery.append("      group by rt.branch_Name, rt.district_Name, rt.dealer_Code, rt.dealer_Name, rt.LOAITB, rt.customer_isdn, rt.REG_POSITION_STATUS  ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name, ort.district_Name, ort.dealer_Name, ort.LOAITB, ort.customer_isdn ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
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

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.customer_isdn)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
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
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoQuanHuyen(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("     select branch_Name, district_Name,   ")
                .append("      sum(PK1_DKTC) as T50_DKTC,   ")
                .append("      sum(PK2_DKTC) as \"3T50_DKTC\",  ")
                .append("      sum(PK3_DKTC) as T100_DKTC,  ")
                .append("      sum(PK4_DKTC) as \"3T100_DKTC\", ")
                .append("      sum(PK1_DCT) as T50_DCT,   ")
                .append("      sum(PK2_DCT) as \"3T50_DCT\",  ")
                .append("      sum(PK3_DCT) as T100_DCT,  ")
                .append("      sum(PK4_DCT) as \"3T100_DCT\", ")
                .append("      sum(PK1_KCT) as T50_KCT,   ")
                .append("      sum(PK2_KCT) as \"3T50_KCT\",  ")
                .append("      sum(PK3_KCT) as T100_KCT,  ")
                .append("      sum(PK4_KCT) as \"3T100_KCT\", ")
                .append("      rt.REG_POSITION_STATUS As viTriDK ")
                .append("      from ( ")
                .append("      select b.BRANCH_NAME, d.DISTRICT_NAME, rt.REG_POSITION_STATUS,  ")
                .append("      b.branch_id,d.district_id,pk.package_id              ")
                .append("      , CASE WHEN ps.isdn = SUBSTR(rt.customer_isdn,2) AND ps.sub_type = 'PTM' THEN 'MOI' ELSE 'TON' END LOAITB  ")
                .append("      , case when pk.package_code = 'T50'  and rt.trans_Status = '1' then 1 else 0 end PK1_DKTC  ")
                .append("      , case when pk.package_code = '3T50'  and rt.trans_Status = '1' then 1 else 0 end PK2_DKTC  ")
                .append("      , case when pk.package_code = 'T100'  and rt.trans_Status = '1' then 1 else 0 end PK3_DKTC  ")
                .append("      , case when pk.package_code = '3T100' and rt.trans_status = '1' then 1 else 0 end PK4_DKTC  ")
                .append("      , case when pk.package_code = 'T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK1_DCT  ")
                .append("      , case when pk.package_code = '3T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK2_DCT  ")
                .append("      , case when pk.package_code = 'T100'  and rt.prom_condition_status = '1' then 1 else 0 end PK3_DCT  ")
                .append("      , case when pk.package_code = '3T100' and rt.prom_condition_status = '1' then 1 else 0 end PK4_DCT  ")
                .append("      , case when pk.package_code = 'T50'  and rt.prom_condition_status = '0' then 1 else 0 end PK1_KCT  ")
                .append("      , case when pk.package_code = '3T50'  and rt.prom_condition_status = '0' then 1 else 0 end PK2_KCT  ")
                .append("      , case when pk.package_code = 'T100'  and rt.prom_condition_status = '0' then 1 else 0 end PK3_KCT  ")
                .append("      , case when pk.package_code = '3T100' and rt.prom_condition_status = '0' then 1 else 0 end PK4_KCT  ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on SUBSTR(rt.customer_isdn,2) = ps.isdn ) rt ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and rt.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and rt.DISTRICT_ID = :district_Id ");
        }
        mainQuery.append("      group by rt.branch_Name, rt.district_Name, rt.REG_POSITION_STATUS   ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name, ort.district_Name  ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.district_Name)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }
    @Override
    public Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoChiNhanh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) throws ObjectNotFoundException {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" FROM (                    ")
                .append("    select ")
                .append("    branch_name as CHINHANH, ")
                .append("    pk.price as SOTIENKHUYENKHICH, ")
                .append("    rt.prom_amount as SOTIENDACHI, ")
                .append("    TO_DATE(rt.trans_date+3) as THOIGIANPHAICHITRA, ")
                .append("    rt.payment_date as THOIGIANTHUCCHI, ")
                .append("    rt.package_id, ")
                .append("    d.district_id, ")
                .append("    rt.trans_date")
                .append("    from Registration_Trans@MKITDW_CSDBL_DBL rt ")
                .append("    left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id ")
                .append("    left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id ")
                .append("    left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id ")
                .append("    inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id ")
                .append("    left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on SUBSTR(rt.customer_isdn,2) = ps.isdn ")
                .append("    where 1=1 and ")
                .append("    rt.trans_status = '1' and rt.prom_condition_status = '1' and rt.prom_amount > 0 ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and b.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("conditionStatus") != null){
            mainQuery.append(" and rt.TRANS_STATUS = :condition_Status ");
        }
        if(properties.get("paymentStatus") != null){
            mainQuery.append(" and rt.PAYMENT_STATUS = :payment_Status ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.CHINHANH ;");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            queryClause.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            queryClause.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.CHINHANH)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            countQuery.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            countQuery.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }
    @Override
    public Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoQuanHuyen(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) throws ObjectNotFoundException {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" FROM (                    ")
                .append("    select ")
                .append("    branch_name as CHINHANH, ")
                .append("    district_name as QUANHUYEN, ")
                .append("    pk.price as SOTIENKHUYENKHICH, ")
                .append("    rt.prom_amount as SOTIENDACHI, ")
                .append("    TO_DATE(rt.trans_date+3) as THOIGIANPHAICHITRA, ")
                .append("    rt.payment_date as THOIGIANTHUCCHI, ")
                .append("    rt.package_id, ")
                .append("    b.branch_id, ")
                .append("    d.district_id, ")
                .append("    rt.trans_date")
                .append("    from Registration_Trans@MKITDW_CSDBL_DBL rt ")
                .append("    left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id ")
                .append("    left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id ")
                .append("    left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id ")
                .append("    inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id ")
                .append("    left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on SUBSTR(rt.customer_isdn,2) = ps.isdn ")
                .append("    where 1=1 and ")
                .append("    rt.trans_status = '1' and rt.prom_condition_status = '1' and rt.prom_amount > 0 ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and b.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("conditionStatus") != null){
            mainQuery.append(" and rt.TRANS_STATUS = :condition_Status ");
        }
        if(properties.get("paymentStatus") != null){
            mainQuery.append(" and rt.PAYMENT_STATUS = :payment_Status ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.QUANHUYEN ;");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            queryClause.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            queryClause.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.district_id)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            countQuery.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            countQuery.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoChiNhanh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("     select branch_Name, ")
                .append("      sum(PK1_DKTC) as T50_DKTC,   ")
                .append("      sum(PK2_DKTC) as \"3T50_DKTC\",  ")
                .append("      sum(PK3_DKTC) as T100_DKTC,  ")
                .append("      sum(PK4_DKTC) as \"3T100_DKTC\", ")
                .append("      sum(PK1_DCT) as T50_DCT,   ")
                .append("      sum(PK2_DCT) as \"3T50_DCT\",  ")
                .append("      sum(PK3_DCT) as T100_DCT,  ")
                .append("      sum(PK4_DCT) as \"3T100_DCT\", ")
                .append("      sum(PK1_KCT) as T50_KCT,   ")
                .append("      sum(PK2_KCT) as \"3T50_KCT\",  ")
                .append("      sum(PK3_KCT) as T100_KCT,  ")
                .append("      sum(PK4_KCT) as \"3T100_KCT\", ")
                .append("      rt.REG_POSITION_STATUS As viTriDK ")
                .append("      from ( ")
                .append("      select b.BRANCH_NAME, rt.REG_POSITION_STATUS,  ")
                .append("      b.branch_id,d.district_id,pk.package_id              ")
                .append("      , CASE WHEN ps.isdn = SUBSTR(rt.customer_isdn,2) AND ps.sub_type = 'PTM' THEN 'MOI' ELSE 'TON' END LOAITB  ")
                .append("      , case when pk.package_code = 'T50'  and rt.trans_Status = '1' then 1 else 0 end PK1_DKTC  ")
                .append("      , case when pk.package_code = '3T50'  and rt.trans_Status = '1' then 1 else 0 end PK2_DKTC  ")
                .append("      , case when pk.package_code = 'T100'  and rt.trans_Status = '1' then 1 else 0 end PK3_DKTC  ")
                .append("      , case when pk.package_code = '3T100' and rt.trans_status = '1' then 1 else 0 end PK4_DKTC  ")
                .append("      , case when pk.package_code = 'T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK1_DCT  ")
                .append("      , case when pk.package_code = '3T50'  and rt.prom_condition_status = '1' then 1 else 0 end PK2_DCT  ")
                .append("      , case when pk.package_code = 'T100'  and rt.prom_condition_status = '1' then 1 else 0 end PK3_DCT  ")
                .append("      , case when pk.package_code = '3T100' and rt.prom_condition_status = '1' then 1 else 0 end PK4_DCT  ")
                .append("      , case when pk.package_code = 'T50'  and rt.prom_condition_status = '0' then 1 else 0 end PK1_KCT  ")
                .append("      , case when pk.package_code = '3T50'  and rt.prom_condition_status = '0' then 1 else 0 end PK2_KCT  ")
                .append("      , case when pk.package_code = 'T100'  and rt.prom_condition_status = '0' then 1 else 0 end PK3_KCT  ")
                .append("      , case when pk.package_code = '3T100' and rt.prom_condition_status = '0' then 1 else 0 end PK4_KCT  ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on SUBSTR(rt.customer_isdn,2) = ps.isdn ) rt ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and rt.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and rt.BRANCH_ID = :branch_Id ");
        }
        mainQuery.append("      group by rt.branch_Name, rt.REG_POSITION_STATUS   ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.branch_Name)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoDiemBanHang(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("      select b.BRANCH_NAME, d.DISTRICT_NAME, rd.DEALER_CODE, rd.DEALER_NAME, rt.trans_date, rt.customer_isdn,  ")
                .append("      rt.ez_isdn             ")
                .append("      , case when ps.isdn = SUBSTR(rt.customer_isdn,2) and ps.sub_type = 'PTM' then 'MOI' else 'TON' end LOAITB  ")
                .append("      , pk.package_name, rt.trans_status, rt.trans_error, rt.prom_condition_status, rt.prom_condition_error    ")
                .append("      ,rt.serial  ")
                .append("      , b.branch_id, d.district_id, rd.dealer_id, pk.package_id    ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on SUBSTR(rt.customer_isdn,2) = ps.isdn ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and pk.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and b.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("retailDealerId") != null){
            mainQuery.append(" and rd.DEALER_ID = :retailDealer_Id ");
        }
        if(properties.get("conditionStatus") != null){
            mainQuery.append(" and rt.TRANS_STATUS = :condition_Status ");
        }
        if(properties.get("promConditionStatus") != null){
            mainQuery.append(" and rt.PROM_CONDITION_STATUS = :promCondition_Status ");
        }
        if(properties.get("paymentStatus") != null){
            mainQuery.append(" and rt.PAYMENT_STATUS = :payment_Status ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name, ort.district_Name, ort.dealer_Name, ort.LOAITB, ort.customer_isdn ")
                    .append(" , ort.package_name, ort.trans_date   ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("retailDealerId") != null){
            queryClause.setParameter("retailDealer_Id", Long.valueOf(properties.get("retailDealerId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            queryClause.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("promConditionStatus") != null){
            queryClause.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            queryClause.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.dealer_Name)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("retailDealerId") != null){
            countQuery.setParameter("retailDealer_Id", Long.valueOf(properties.get("retailDealerId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            countQuery.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("promConditionStatus") != null){
            countQuery.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            countQuery.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoQuanHuyen(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("      select b.BRANCH_NAME, d.DISTRICT_NAME, rt.trans_date  ")
                .append("      , pk.package_name, rt.trans_status, rt.trans_error, rt.prom_condition_status, rt.prom_condition_error    ")
                .append("      , b.branch_id, d.district_id, rd.dealer_id, pk.package_id    ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on SUBSTR(rt.customer_isdn,2) = ps.isdn ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and pk.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and b.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("conditionStatus") != null){
            mainQuery.append(" and rt.TRANS_STATUS = :condition_Status ");
        }
        if(properties.get("promConditionStatus") != null){
            mainQuery.append(" and rt.PROM_CONDITION_STATUS = :promCondition_Status ");
        }
        if(properties.get("paymentStatus") != null){
            mainQuery.append(" and rt.PAYMENT_STATUS = :payment_Status ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name, ort.district_Name, ort.package_name, ort.trans_date   ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            queryClause.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("promConditionStatus") != null){
            queryClause.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            queryClause.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.district_Name)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            countQuery.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("promConditionStatus") != null){
            countQuery.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            countQuery.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoChiNhanh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     FROM (    ")
                .append("      select b.BRANCH_NAME, rt.trans_date  ")
                .append("      , pk.package_name, rt.trans_status, rt.trans_error, rt.prom_condition_status, rt.prom_condition_error    ")
                .append("      , b.branch_id, d.district_id, rd.dealer_id, pk.package_id    ")
                .append("      FROM Registration_Trans@MKITDW_CSDBL_DBL rt  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.dealer_Id = rt.dealer_Id  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.district_id = rd.district_Id  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("      inner join prom_package@MKITDW_CSDBL_DBL pk on rt.package_Id = pk.package_Id  ")
                .append("      left join PROM_SUB_LIST@MKITDW_CSDBL_DBL ps on SUBSTR(rt.customer_isdn,2) = ps.isdn ")
                .append("      where 1 = 1   ");

        if(properties.get("packageId") != null){
            mainQuery.append(" and pk.PACKAGE_ID = :package_Id ");
        }
        if(properties.get("branchId") != null){
            mainQuery.append(" and b.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("conditionStatus") != null){
            mainQuery.append(" and rt.TRANS_STATUS = :condition_Status ");
        }
        if(properties.get("promConditionStatus") != null){
            mainQuery.append(" and rt.PROM_CONDITION_STATUS = :promCondition_Status ");
        }
        if(properties.get("paymentStatus") != null){
            mainQuery.append(" and rt.PAYMENT_STATUS = :payment_Status ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and rt.TRANS_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * ")
                .append(mainQuery)
                .append(" ) ort ");

        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by ort.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ort.branch_Name, ort.package_name, ort.trans_date   ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            queryClause.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("promConditionStatus") != null){
            queryClause.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            queryClause.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ort.branch_Name)").append(mainQuery).append(" ) ort ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("package_Id", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("conditionStatus") != null){
            countQuery.setParameter("condition_Status", properties.get("conditionStatus").toString());
        }
        if(properties.get("promConditionStatus") != null){
            countQuery.setParameter("promCondition_Status", properties.get("promConditionStatus").toString());
        }
        if(properties.get("paymentStatus") != null){
            countQuery.setParameter("payment_Status", properties.get("paymentStatus").toString());
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Boolean checkIfDealerHaveDOC(Long dealerId, String soEZ) {
        soEZ = CommonUtil.removeCountryCode(soEZ);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select count(dp.dealer_Id) from IMPORT_DEALER_DOC@mkitdw_csdbl_dbl dp ")
                .append("  where dp.EZ_ISDN like :soEZ and dp.dealer_Id = :dealer_Id ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealerId);
        query.setParameter("soEZ", "%" + soEZ + "%");

        Long count = Long.valueOf(query.getSingleResult().toString());
        if(count.compareTo(0L) > 0){
            return true;
        }
        return false;
    }

    @Override
    public void saveImportDealerSigned(Long dealerId, String soEZ, String dealer_code) {
        soEZ = CommonUtil.removeCountryCode(soEZ);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" insert into IMPORT_DEALER_DOC@mkitdw_csdbl_dbl (DEALER_CODE, EZ_ISDN, IMPORT_DATE, DEALER_ID) values (:dealer_code, :soEZ, current_timestamp, ").append(dealerId != null ? ":dealer_Id" : null).append(") ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(dealerId != null){
            query.setParameter("dealer_Id", dealerId);
        }
        query.setParameter("dealer_code", dealer_code);
        query.setParameter("soEZ", soEZ);
        query.executeUpdate();
    }

    @Override
    public void updateDealerSigned(Long dealerId, String soEZ, String dealer_code) {
        soEZ = CommonUtil.removeCountryCode(soEZ);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" update IMPORT_DEALER_DOC@mkitdw_csdbl_dbl set IMPORT_DATE = current_timeStamp where dealer_Id = :dealer_Id and DEALER_CODE = :dealer_code and EZ_ISDN like :soEZ ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealerId);
        query.setParameter("soEZ", "%" + soEZ + "%");
        query.setParameter("dealer_code", dealer_code);
        query.executeUpdate();
    }

    @Override
    public Object[] baoCaoEzNhanTinThamGiaChuongTrinh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        String soEZ = null;
        if(properties.get("ez") != null){
            soEZ = CommonUtil.removeCountryCode(properties.get("ez").toString());
        }
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     from ez_reg_sms@mkitdw_csdbl_dbl ez  ")
                .append("      left join Retail_dealer@MKITDW_CSDBL_DBL rd on rd.DEALER_CODE = ez.DEALER_CODE  ")
                .append("      left join District@MKITDW_CSDBL_DBL d on d.DISTRICT_CODE = ez.DISTRICT_CODE  ")
                .append("      left join Branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_id  ")
                .append("       where 1 = 1 ");

        if(properties.get("branchId") != null){
            mainQuery.append(" and b.BRANCH_ID = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("retailDealerId") != null){
            mainQuery.append(" and rd.DEALER_ID = :retailDealer_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and ez.REG_DATE >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and ez.REG_DATE < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        if(properties.get("fromNgayKichHoat") != null){
            mainQuery.append(" and ez.ACTIVE_DATETIME >= to_date(substr(:fromNgayKichHoat, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toNgayKichHoat") != null){
            mainQuery.append(" and ez.ACTIVE_DATETIME < (to_date(substr(:toNgayKichHoat, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        if(properties.get("ez") != null){
            mainQuery.append(" and ez.EZ_ISDN like :ez ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select ez.EZ_ISDN as soEZ, ")
                .append("              b.BRANCH_NAME as chiNhanh, ")
                .append("              d.DISTRICT_NAME as quanHuyen, ")
                .append("              rd.DEALER_CODE as maDiemBanHang, ")
                .append("              rd.DEALER_NAME as tenDiemBanHang, ")
                .append("              ez.REG_DATE as ngayNhanTin, ")
                .append("              ez.REG_LOG as ghiChu,  ")
                .append("              ez.ACTIVE_DATETIME as ngayKichHoat  ")
                .append(mainQuery);

        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by rt.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by b.BRANCH_NAME, d.DISTRICT_NAME, rd.DEALER_CODE, rd.DEALER_NAME, ez.REG_DATE, ez.EZ_ISDN ");
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
        if(properties.get("fromDateTime") != null){
            queryClause.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            queryClause.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(properties.get("fromNgayKichHoat") != null){
            queryClause.setParameter("fromNgayKichHoat", properties.get("fromNgayKichHoat").toString());
        }
        if(properties.get("toNgayKichHoat") != null){
            queryClause.setParameter("toNgayKichHoat", properties.get("toNgayKichHoat").toString());
        }
        if(StringUtils.isNotBlank(soEZ)){
            queryClause.setParameter("ez", "%" + soEZ + "%");
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(ez.EZ_ISDN)").append(mainQuery);
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
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(properties.get("fromNgayKichHoat") != null){
            countQuery.setParameter("fromNgayKichHoat", properties.get("fromNgayKichHoat").toString());
        }
        if(properties.get("toNgayKichHoat") != null){
            countQuery.setParameter("toNgayKichHoat", properties.get("toNgayKichHoat").toString());
        }
        if(StringUtils.isNotBlank(soEZ)){
            countQuery.setParameter("ez", "%" + soEZ + "%");
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Boolean checkPaymentStatusByDealerIdAndSumDate(Long dealer_Id, List<Date> sumDateList, String soEZ) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select count(trans_Id) from registration_Trans@mkitdw_csdbl_dbl where dealer_Id = :dealer_Id and PAYMENT_STATUS = '1' ")
                .append("       and ez_Isdn = :soEZ ")
                .append("       and (( ")
                .append("               sum_Date >= to_date(substr(:sum_Date, 1, 10), 'YYYY-MM-DD')        ")
                .append("               and sum_Date < to_date(substr(:sum_Date, 1, 10), 'YYYY-MM-DD') + 1) ");


        for (int i = 1; i < sumDateList.size() ; i++){
            sqlQueryClause.append(" or ( ")
                    .append("       sum_Date >= to_date(substr('")
                    .append(new Timestamp(sumDateList.get(i).getTime()).toString())
                    .append("'    , 1, 10), 'YYYY-MM-DD') ")
                    .append("       and sum_Date < to_date(substr('")
                    .append(new Timestamp(sumDateList.get(i).getTime()).toString())
                    .append("'  , 1, 10), 'YYYY-MM-DD') + 1  ")
                    .append("  ) ");
        }
        sqlQueryClause.append("  ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        query.setParameter("sum_Date", new Timestamp(sumDateList.get(0).getTime()).toString());
        query.setParameter("soEZ", soEZ);
        Long count = Long.valueOf(query.getSingleResult().toString());
        if(count.compareTo(0L) > 0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkIfHaveDocBySoEZ(String soEZ, String dealer_Code) {
        soEZ = CommonUtil.removeCountryCode(soEZ);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select count(ez_Isdn) from import_Dealer_Doc@mkitdw_csdbl_dbl where ez_Isdn = :soEz and dealer_Code = :dealerCode ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soEz", soEZ);
        query.setParameter("dealerCode", dealer_Code);
        Long count = Long.valueOf(query.getSingleResult().toString());
        if(count.compareTo(0L) > 0){
            return true;
        }
        return false;
    }

    @Override
    public void updateDealerSignedBySoEZAndDealerCode(String soEZ, String dealer_code) {
        soEZ = CommonUtil.removeCountryCode(soEZ);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" update import_Dealer_Doc@mkitdw_csdbl_dbl set import_Date = current_date where dealer_Code = :dealer_Code and ez_Isdn = :soEZ ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soEZ", soEZ);
        query.setParameter("dealer_Code", dealer_code);
        query.executeUpdate();
    }

    @Override
    public Integer getTotalTrans_ThoaDKCT(Long dealer_Id, List<Date> sumDateList, String soEZ) {
        soEZ = CommonUtil.removeCountryCode(soEZ);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select count(trans_Id) from registration_Trans@mkitdw_csdbl_dbl ")
                .append("       where dealer_Id = :dealer_Id ")
                .append("       and PAYMENT_STATUS <> '1'  ")
                .append("       and ez_Isdn = :soEZ ")
                .append("       and PROM_CONDITION_STATUS = '1' ")
                .append("       and (( ")
                .append("               sum_Date >= to_date(substr(:sum_Date, 1, 10), 'YYYY-MM-DD')        ")
                .append("               and sum_Date < to_date(substr(:sum_Date, 1, 10), 'YYYY-MM-DD') + 1) ");


        for (int i = 1; i < sumDateList.size() ; i++){
            sqlQueryClause.append(" or ( ")
                    .append("       sum_Date >= to_date(substr('")
                    .append(new Timestamp(sumDateList.get(i).getTime()).toString())
                    .append("'    , 1, 10), 'YYYY-MM-DD') ")
                    .append("       and sum_Date < to_date(substr('")
                    .append(new Timestamp(sumDateList.get(i).getTime()).toString())
                    .append("'  , 1, 10), 'YYYY-MM-DD') + 1  ")
                    .append("  ) ");
        }
        sqlQueryClause.append("  ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        query.setParameter("sum_Date", new Timestamp(sumDateList.get(0).getTime()).toString());
        query.setParameter("soEZ", soEZ);
        Integer count = Integer.valueOf(query.getSingleResult().toString());
        return count;
    }

    @Override
    public Integer getCurrentPointTotal(String phoneNumber) {
        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select nvl(sum(t.so_diem), 0) as totalScore ")
                .append("   from QSV_2015_DIEM@MKITDW_KM_QSV_DBL t ")
                .append("   where t.thue_bao like :soThueBao ");
        Query query = entityManager.createNativeQuery(sqlCountClause.toString());
        query.setParameter("soThueBao", "%" + CommonUtil.removeCountryCode(phoneNumber) + "%");
        return  Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public Integer countExchangedMaPhieus(String phoneNumber) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select nvl(count(ma_phieu), 0) as total from QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL where thue_bao like :thue_bao and da_doi_qua = :da_doi_qua ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + phoneNumber + "%");
        query.setParameter("da_doi_qua", Constants.DA_GIAO_QUA);
        return Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public Object[] searchSubscriberInfo(String phoneNumber, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);

        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" from QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL mp ")
                .append("   left join Vms_department d on upper(d.code) = upper(mp.shop_Code) ")
                .append("   where mp.thue_bao like :thue_bao ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select mp.ma_phieu, mp.ngay_ps, mp.da_doi_qua, mp.ngay_doi_qua, d.name as ten_cua_hang ").append(mainQuery);
        if(org.apache.commons.lang3.StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by mp.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by mp.ngay_ps ");
        }
        if(org.apache.commons.lang3.StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            sqlQueryClause.append(" desc ");
        }

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + phoneNumber + "%");
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);

        List list = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(mp.ma_phieu) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("thue_bao", "%" + phoneNumber + "%");
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, list};
    }

    @Override
    public Object[] statisticCumulativeScoresByMonth(String phoneNumber) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("select distinct(extract(month from t.ngay_ps)) as month, nvl(sum(t.amount), 0) as total_amount from QSV_2015_PSC@MKITDW_KM_QSV_DBL t ")
                .append("   where t.thue_bao like :soThueBao ")
                .append("   group by extract(month from t.ngay_ps) ")
                .append("   order by extract(month from t.ngay_ps) asc ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soThueBao", "%" + phoneNumber + "%");
        List list = query.getResultList();
        List<CTTichDiemSoDiemStatisticByMonthDTO> dtoList = new ArrayList<CTTichDiemSoDiemStatisticByMonthDTO>();
        for (Object object : list){
            Object[] tmpArr = (Object[])object;
            CTTichDiemSoDiemStatisticByMonthDTO dto = new CTTichDiemSoDiemStatisticByMonthDTO();
            dto.setMonth(Integer.valueOf(tmpArr[0].toString()));
            dto.setTotal(Double.valueOf(tmpArr[1].toString()) / Constants.UNIT_SCORE);
            dtoList.add(dto);
        }
        return new Object[]{dtoList.size(), dtoList};
    }

    @Override
    public Object[] statisticCumulativeScoresByDate(String phoneNumber, String whereClause, Integer offset, Integer limit) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select t.thue_bao, t.ngay_ps, nvl(sum(t.amount), 0) as amount, t.type_input from QSV_2015_PSC@MKITDW_KM_QSV_DBL t ").append(whereClause);
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soThueBao", "%" + phoneNumber + "%");
        if(offset != null){
            query.setFirstResult(offset);
        }
        if(limit != null){
            query.setMaxResults(limit);
        }
        List list = query.getResultList();
        List<CTTichDiemSoDiemDTO> dtoList = new ArrayList<>();
        for(Object object : list){
            Object[] tmpArr = (Object[])object;
            CTTichDiemSoDiemDTO dto = new CTTichDiemSoDiemDTO();
            dto.setThue_bao(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setNgay_ps(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);
            dto.setSo_tien_psc(tmpArr[2] != null ? Double.valueOf(tmpArr[2].toString()) : null);
            dto.setType_input(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dtoList.add(dto);
        }

        StringBuilder sqlCountClause = new StringBuilder("select count(distinct(to_timestamp(substr(t.ngay_ps, 1, 10)))) as total from QSV_2015_PSC@MKITDW_KM_QSV_DBL t where t.thue_bao like :soThueBao ");
        query = entityManager.createNativeQuery(sqlCountClause.toString());
        query.setParameter("soThueBao", phoneNumber);
        Long count = Long.valueOf(query.getSingleResult().toString());
        return new Object[]{dtoList.size(), dtoList};
    }
    @Override
    public Object[] searchByMaPhieu(String maPhieu, Integer exchangedStatus, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select t.thue_bao, t.ma_phieu, t.ngay_ps, t.da_doi_qua, t.ngay_doi_qua, t.user_name, t.shop_code ")
                .append("               , (select nvl(sum(t1.so_diem), 0) from QSV_2015_DIEM@MKITDW_KM_QSV_DBL t1 where t1.thue_bao = t.thue_bao ) as diemTichLuyHienTai ")
                .append("               , case when t.da_doi_qua = '2' then (select d.name as departmentName from Vms_department d where upper(d.code) = upper(t.shop_code)) else null end as tenCuaHang ")
                .append("   from QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL t ")
                .append("   where 1= 1 ");
        if(org.apache.commons.lang3.StringUtils.isNotBlank(maPhieu)){
            mainQuery.append(" and ma_phieu = :maPhieu ");
        }
        if(exchangedStatus != null && !exchangedStatus.equals(-1)){
            if(exchangedStatus.equals(2)){
                mainQuery.append(" and da_doi_qua = 2 ");
            }else{
                mainQuery.append(" and da_doi_qua != 2 ");
            }
        }

        if(org.apache.commons.lang3.StringUtils.isNotBlank(sortExpression)){
            mainQuery.append(" order by ").append(sortDirection);
        }else{
            mainQuery.append(" order by ngay_ps, ma_phieu ");
        }

        if(org.apache.commons.lang3.StringUtils.isNotBlank(sortDirection)){
            mainQuery.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            mainQuery.append(" desc ");
        }

        StringBuilder sqlQueryClause = new StringBuilder("select * from (").append(mainQuery).append(" ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(org.apache.commons.lang3.StringUtils.isNotBlank(maPhieu)){
            query.setParameter("maPhieu", maPhieu);
        }

        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List list = query.getResultList();
        List<CTTichDiemMaPhieuDTO> dtoList = new ArrayList<>();
        for(Object obj : list) {
            Object[] row = (Object[])obj;
            CTTichDiemMaPhieuDTO dto = new CTTichDiemMaPhieuDTO();
            dto.setThue_bao(row[0] != null ? row[0].toString() : null);
            dto.setMa_phieu(row[1] != null ? row[1].toString() : null);
            dto.setNgay_ps(row[2] != null ? Timestamp.valueOf(row[2].toString()) : null);
            dto.setDa_doi_qua(row[3] != null ? Integer.valueOf(row[3].toString()) : null);
            dto.setNgay_doi_qua(row[4] != null ? Timestamp.valueOf(row[4].toString()) : null);
            dto.setUser_name(row[5] != null ? row[5].toString() : null);
            dto.setShop_code(row[6] != null ? row[6].toString() : null);
            dto.setDiemTichLuy(row[7] != null ? Double.valueOf(row[7].toString()) : 0);
            dto.setTenCuaHang(row[8] != null ? row[8].toString() : null);
            dtoList.add(dto);
        }

        StringBuilder sqlCountClause = new StringBuilder("");
        sqlCountClause.append(" select count(ma_phieu) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(org.apache.commons.lang3.StringUtils.isNotBlank(maPhieu)){
            countQuery.setParameter("maPhieu", maPhieu);
        }
        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, dtoList};
    }

    @Override
    public Object[] searchByThueBao(String phoneNumber, Integer exchangedStatus, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        if(org.apache.commons.lang3.StringUtils.isNotBlank(phoneNumber)){
            phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        }
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select t.thue_bao, t.ngay_ps, t.ma_phieu, t.da_doi_qua, t.ngay_doi_qua ")
                .append("               , (select nvl(sum(t1.so_diem), 0) from QSV_2015_DIEM@MKITDW_KM_QSV_DBL t1 where t1.thue_bao = t.thue_bao) as diemTichLuy ")
                .append("               , case when t.da_doi_qua = '2' then (select d.name as departmentName from Vms_department d where upper(d.code) = upper(t.shop_code)) else null end as tenCuaHang ")
                .append("   from QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL t where 1 = 1 ");

        if(org.apache.commons.lang3.StringUtils.isNotBlank(phoneNumber)){
            mainQuery.append(" and t.thue_bao like :thueBao ");
        }
        if(exchangedStatus != null && !exchangedStatus.equals(-1)){
            if(exchangedStatus.equals(2)){
                mainQuery.append(" and t.da_doi_qua = 2 ");
            }else{
                mainQuery.append(" and t.da_doi_qua != 2 ");
            }
        }
        if(org.apache.commons.lang3.StringUtils.isNotBlank(sortExpression)){
            mainQuery.append(" order by ").append(sortExpression);
        }else{
            mainQuery.append(" order by ngay_ps, ma_phieu ");
        }

        if(org.apache.commons.lang3.StringUtils.isNotBlank(sortDirection)){
            mainQuery.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            mainQuery.append(" desc ");
        }

        StringBuilder sqlQueryClause = new StringBuilder(" select * from ( ").append(mainQuery).append(" ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(org.apache.commons.lang3.StringUtils.isNotBlank(phoneNumber)){
            query.setParameter("thueBao", "%" + phoneNumber + "%");
        }

        if(offset != null) {
            query.setFirstResult(offset);
        }
        if(limit != null) {
            query.setMaxResults(limit);
        }
        List list = query.getResultList();
        List<CTTichDiemMaPhieuDTO> dtoList = new ArrayList<>();
        for(Object obj : list) {
            Object[] row = (Object[])obj;
            CTTichDiemMaPhieuDTO dto = new CTTichDiemMaPhieuDTO();
            dto.setThue_bao(row[0] != null ? row[0].toString() : null);
            dto.setNgay_ps(row[1] != null ? Timestamp.valueOf(row[1].toString()) : null);
            dto.setMa_phieu(row[2] != null ? row[2].toString() : null);
            dto.setDa_doi_qua(row[3] != null ? Integer.valueOf(row[3].toString()) : null);
            dto.setNgay_doi_qua(row[4] != null ? Timestamp.valueOf(row[4].toString()) : null);
            dto.setDiemTichLuy(row[5] != null ? Double.valueOf(row[5].toString()) : 0);
            dto.setTenCuaHang(row[6] != null ? row[6].toString() : null);
            dtoList.add(dto);
        }

        StringBuilder sqlCountClause = new StringBuilder("select count(ma_phieu) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if (org.apache.commons.lang3.StringUtils.isNotBlank(phoneNumber)){
            countQuery.setParameter("thueBao", "%" + phoneNumber + "%");
        }
        if(exchangedStatus != null){
            if(exchangedStatus.equals(0)){
                mainQuery.append(" and t.da_doi_qua = 0 ");
            }else if(exchangedStatus.equals(1)){
                mainQuery.append(" and t.da_doi_qua > 0 ");
            }
        }
        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, dtoList};
    }

    @Override
    public Object[] searchMaPhieuListByProperties(String phoneNumber, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select t.* from QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL t where t.thue_bao like :soThueBao order by t.da_doi_qua ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soThueBao", "%" + phoneNumber + "%");
        if(offset != null){
            query.setFirstResult(offset);
        }
        if(limit != null){
            query.setMaxResults(limit);
        }
        List list = query.getResultList();
        List<CTTichDiemMaPhieuDTO> dtoList = new ArrayList<>();
        for(Object object : list){
            Object[] tmpArr = (Object[])object;
            CTTichDiemMaPhieuDTO dto = new CTTichDiemMaPhieuDTO();
            dto.setThue_bao(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setMa_phieu(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setNgay_ps(tmpArr[2] != null ? Timestamp.valueOf(tmpArr[2].toString()) : null);
            dto.setDa_doi_qua(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setNgay_doi_qua(tmpArr[5] != null ? Timestamp.valueOf(tmpArr[5].toString()) : null);
            dto.setUser_name(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setShop_code(tmpArr[7] != null ? tmpArr[7].toString() : null);
            dtoList.add(dto);
        }

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append("select count(t.thue_bao) from QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL t where t.thue_bao like :soThueBao order by t.da_doi_qua");
        query = entityManager.createNativeQuery(sqlCountClause.toString());
        query.setParameter("soThueBao", "%" + phoneNumber + "%");
        Long count = Long.valueOf(query.getSingleResult().toString());

        return new Object[]{count, dtoList};
    }

    @Override
    public void updateMaphieuStatus_tdcg(String phoneNumber) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" update QSV_2015_MA_PHIEU@mkitdw_csdbl_dbl t set t.da_doi_qua = :status, t.ngay_doi_qua = :current_date where t.thue_bao like :soThueBao and t.da_doi_qua = 0 ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soThueBao", "%" + phoneNumber + "%");
        query.setParameter("status", Constants.THUEBAO_DOIQUA);
        query.setParameter("current_date", new Date());
        query.executeUpdate();

    }

    @Override
    public Boolean checkIfAlreadyRegister(String mobifoneNumber)throws ObjectNotFoundException{
        StringBuilder sqlQueryClause = new StringBuilder("select t.thue_bao, nvl(t.trang_thai, 0) as trang_thai from QSV_2015_DS_THAM_GIA@MKITDW_KM_QSV_DBL t where t.thue_bao like :soThueBao");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soThueBao", "%" + CommonUtil.removeCountryCode(mobifoneNumber) + "%");
        List list = query.getResultList();
        if(list.size() > 0){
            Object[] tmpArr = (Object[])list.get(0);
            if(tmpArr[1] != null && tmpArr[1].toString().equals("1")){
                return  true;
            }
            return false;
        }else{
            throw new ObjectNotFoundException("Thue bao nay chua dang ky tham gia chuong trinh");
        }
    }

    @Override
    public List getMessageManagementReportData(String loaiTinNhanFromKHCN, String loaiTinNhanFromVMS, Timestamp fromDateTime, Timestamp toDateTime) {
        Query query = entityManager.createNamedQuery("baocaoquanlytinnhanqstudent2015");
        List<String> dsLoaiTinNhanFromKHCN = new ArrayList<String>();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(loaiTinNhanFromKHCN)){
            dsLoaiTinNhanFromKHCN.add(loaiTinNhanFromKHCN);
        }else{
            dsLoaiTinNhanFromKHCN.add(Constants.DK_MOBIFONE);
            dsLoaiTinNhanFromKHCN.add(Constants.HUY_MOBIFONE);
            dsLoaiTinNhanFromKHCN.add(Constants.KT_MOBIFONE);
            dsLoaiTinNhanFromKHCN.add(Constants.DP_MOBIFONE);
        }

        List<String> dsLoaiTinNhanFromVMS = new ArrayList<String>();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(loaiTinNhanFromVMS)){
            dsLoaiTinNhanFromVMS.add(loaiTinNhanFromVMS);
        }else{
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_1);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_2_1);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_2_2);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_3);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_4);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_5);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_6);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_7_1);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_7_2);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_8);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_9);
        }
        query.setParameter("dsLoaiTinNhanFromKHCN", dsLoaiTinNhanFromKHCN);
        query.setParameter("dsLoaiTinNhanFromVMS", dsLoaiTinNhanFromVMS);
        if(fromDateTime != null){
            query.setParameter("fromDate", fromDateTime.toString());
        }else{
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(2015, 0, 1);
            Timestamp fromDateTime1 = new Timestamp(fromCalendar.getTimeInMillis());
            query.setParameter("fromDate", fromDateTime1.toString());
        }
        if(toDateTime != null){
            query.setParameter("toDate", toDateTime.toString());
        }else{
            Timestamp toDateTime1 = new Timestamp(System.currentTimeMillis());
            query.setParameter("toDate", toDateTime1.toString());
        }
        return query.getResultList();
    }

    @Override
    public Long baoCaoDanhGiaKetQuaTHCT_tieuChi1(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select count(a.thue_bao) as slThamGia ")
                .append("   from QSV_2015_DS_THAM_GIA@MKITDW_KM_QSV_DBL a ")
                .append("   where a.trang_thai = '1' ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append("           and a.ngay_dang_ky >= to_date(substr(:fromDateTimeThamGia,1,10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(toDateTimeThamGia != null){
            sqlQueryClause.append("           and a.ngay_dang_ky < (to_date(substr(:toDateTimeThamGia,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTimeThamGia != null){
            query.setParameter("fromDateTimeThamGia", fromDateTimeThamGia.toString());
        }
        if(toDateTimeThamGia != null){
            query.setParameter("toDateTimeThamGia", toDateTimeThamGia.toString());
        }
        return Long.valueOf(query.getSingleResult().toString());
    }

    @Override
    public Long baoCaoDanhGiaKetQuaTHCT_tieuChi2(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select  ")
                .append("           nvl(sum((select count(b.isdn) from QSV_2015_VLR@MKITDW_KM_QSV_DBL b where b.isdn = a.thue_bao and b.vlr_type = '1')),0) as slThamGiaTrenVLR ")
                .append("   from QSV_2015_DS_THAM_GIA@MKITDW_KM_QSV_DBL a ")
                .append("   where a.trang_thai = '1' ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append("        and a.ngay_dang_ky >= to_date(substr(:fromDateTimeThamGia,1,10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia != null){
            sqlQueryClause.append("        and a.ngay_dang_ky < (to_date(substr(:toDateTimeThamGia,1,10), 'YYYY-MM-DD') + 1) ");
        }
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTimeThamGia != null){
            query.setParameter("fromDateTimeThamGia", fromDateTimeThamGia.toString());
        }
        if(toDateTimeThamGia != null){
            query.setParameter("toDateTimeThamGia", toDateTimeThamGia.toString());
        }
        return Long.valueOf(query.getSingleResult().toString());
    }

    @Override
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi3And4Summary(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select  c.phan_loai, sum(b.total_credit) as total_credit, sum(b.total_bonus) as total_bonus ")
                .append("   from QSV_2015_DS_THAM_GIA@MKITDW_KM_QSV_DBL a, QSV_2015_DTTD@MKITDW_KM_QSV_DBL b, QSV_2015_DM_ITEM@MKITDW_KM_QSV_DBL c ")
                .append("   where a.thue_bao = b.calling_number ")
                .append("       and b.item_id = c.item_id ")
                .append("       and c.phan_loai in ('t', 's', 'd', 'v', 'r', 'k') ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append(" and a.insert_date >= to_date(substr(:fromDateTimeThamGia, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia!= null){
            sqlQueryClause.append(" and a.insert_date < (to_date(substr(:toDateTimeThamGia, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append(" and b.issue_date >= to_date(substr('2015-03-01',1,10), 'YYYY-MM-DD') ")
                .append("       and b.issue_date < (to_date(substr('2015-03-31',1,10), 'YYYY-MM-DD') + 1) ")
                .append("    group by c.phan_loai ")
                .append("    order by c.phan_loai ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTimeThamGia != null){
            query.setParameter("fromDateTimeThamGia", fromDateTimeThamGia.toString());
        }
        if(toDateTimeThamGia != null){
            query.setParameter("toDateTimeThamGia", toDateTimeThamGia.toString());
        }
        return query.getResultList();
    }

    @Override
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi5And6Summary(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select  c.phan_loai, sum(b.total_credit) as total_credit, sum(b.total_bonus) as total_bonus ")
                .append("   from QSV_2015_DS_THAM_GIA@MKITDW_KM_QSV_DBL a, QSV_2015_DTTD@MKITDW_KM_QSV_DBL b, QSV_2015_DM_ITEM@MKITDW_KM_QSV_DBL c ")
                .append("   where a.thue_bao = b.calling_number ")
                .append("       and b.item_id = c.item_id ")
                .append("       and c.phan_loai in ('t', 's', 'd', 'v', 'r', 'k') ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append(" and a.insert_date >= to_date(substr(:fromDateTimeThamGia, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia!= null){
            sqlQueryClause.append(" and a.insert_date < (to_date(substr(:toDateTimeThamGia, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        if(fromDateTimeReportFilter != null){
            sqlQueryClause.append(" and b.issue_date >= to_date(substr(:fromDateTimeReportFilter,1,10), 'YYYY-MM-DD')  ");
        }
        if(toDateTimeReportFilter != null){
            sqlQueryClause.append(" and b.issue_date < (to_date(substr(:toDateTimeReportFilter,1,10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append(" group by c.phan_loai ")
                .append("   order by c.phan_loai ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTimeThamGia != null){
            query.setParameter("fromDateTimeThamGia", fromDateTimeThamGia.toString());
        }
        if(toDateTimeThamGia != null){
            query.setParameter("toDateTimeThamGia", toDateTimeThamGia.toString());
        }
        if(fromDateTimeReportFilter != null){
            query.setParameter("fromDateTimeReportFilter", fromDateTimeReportFilter.toString());
        }
        if(toDateTimeThamGia != null){
            query.setParameter("toDateTimeReportFilter", toDateTimeReportFilter.toString());
        }
        return query.getResultList();
    }

    @Override
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi7(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select phan_loai, count(1) ")
                .append("   from ( select a.thue_bao, ")
                .append("               (floor(a.so_diem / 100) + count(b.ma_phieu)) so_ma, ")
                .append("               decode(floor(a.so_diem / 100) + count(b.ma_phieu), 1, 1, 2, 2, 3, 3, 4) phan_loai ")
                .append("           from QSV_2015_DIEM@MKITDW_KM_QSV_DBL a ")
                .append("   inner join QSV_2015_DS_THAM_GIA@MKITDW_KM_QSV_DBL c on ( ")
                .append("           a.thue_bao = c.thue_bao ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append("        and trunc(c.insert_date) >= to_date(substr(:fromDateTimeThamGia,1,10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia != null){
            sqlQueryClause.append("        and trunc(c.insert_date) < (to_date(substr(:toDateTimeThamGia,1,10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append("         ) ")
                .append("   left join QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL b ")
                .append("       on (a.thue_bao = b.thue_bao) ")
                .append("   group by a.thue_bao, a.so_diem ")
                .append("   ) ")
                .append("   where so_ma > 0 ")
                .append("   group by phan_loai ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTimeThamGia != null){
            query.setParameter("fromDateTimeThamGia", fromDateTimeThamGia.toString());
        }
        if(toDateTimeThamGia != null){
            query.setParameter("toDateTimeThamGia", toDateTimeThamGia.toString());
        }
        return query.getResultList();
    }

    @Override
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi8(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select phan_loai, sum(so_ma) ")
                .append("   from ")
                .append("       (select a.thue_bao, ")
                .append("               count(a.ma_phieu) so_ma, ")
                .append("               decode(count(a.ma_phieu), 1, 1, 2, 2, 3, 3, 4) phan_loai ")
                .append("        from QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL a ")
                .append("        inner join QSV_2015_DS_THAM_GIA@MKITDW_KM_QSV_DBL c ")
                .append("               on ( ")
                .append("                   a.thue_bao = c.thue_bao ")
                .append("                   and a.da_doi_qua = '2' ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append("             and trunc(c.insert_date) >= to_date(substr(:fromDateTimeThamGia,1,10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia != null){
            sqlQueryClause.append("             and trunc(c.insert_date) < (to_date(substr(:toDateTimeThamGia,1,10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append("               ) ")
                .append("        group by a.thue_bao ")
                .append("       ) ")
                .append("   where so_ma > 0 ")
                .append("   group by phan_loai ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTimeThamGia != null){
            query.setParameter("fromDateTimeThamGia", fromDateTimeThamGia.toString());
        }
        if(toDateTimeThamGia != null){
            query.setParameter("toDateTimeThamGia", toDateTimeThamGia.toString());
        }
        return query.getResultList();
    }

    @Override
    public Long baoCaoDanhGiaKetQuaTHCT_tieuChi9(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select nvl(sum(soMaPhieuDaPhatSinh),0) ")
                .append("   from ( select ds.thue_bao ")
                .append("                   , count(mp.ma_phieu) as soMaPhieuDaPhatSinh ")
                .append("           from QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL mp ")
                .append("           inner join QSV_2015_DS_THAM_GIA@MKITDW_KM_QSV_DBL ds on ds.thue_bao = mp.thue_bao ")
                .append("           where trim(ds.input_type) in ('truoc', 'sau') ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append("             and ds.insert_date >= to_date(substr(:fromDateTimeThamGia, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia != null){
            sqlQueryClause.append("             and ds.insert_date < (to_date(substr(:toDateTimeThamGia, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append("         group by ds.thue_bao ")
                .append("           ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTimeThamGia != null){
            query.setParameter("fromDateTimeThamGia", fromDateTimeThamGia.toString());
        }
        if(toDateTimeThamGia != null){
            query.setParameter("toDateTimeThamGia", toDateTimeThamGia.toString());
        }
        return Long.valueOf(query.getSingleResult().toString());
    }

    @Override
    public Long baoCaoDanhGiaKetQuaTHCT_tieuChi10(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select nvl(sum(soMaPhieuDaDoiQua),0) ")
                .append("   from ( select ds.thue_bao ")
                .append("                   , count(mp.ma_phieu) as soMaPhieuDaDoiQua ")
                .append("           from QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL mp ")
                .append("           inner join QSV_2015_DS_THAM_GIA@MKITDW_KM_QSV_DBL ds on ds.thue_bao = mp.thue_bao ")
                .append("           where trim(ds.input_type) in ('truoc', 'sau') ")
                .append("                       and mp.da_doi_qua = '2' ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append("             and ds.insert_date >= to_date(substr(:fromDateTimeThamGia, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia != null){
            sqlQueryClause.append("             and ds.insert_date < (to_date(substr(:toDateTimeThamGia, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append("         group by ds.thue_bao ")
                .append("           ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTimeThamGia != null){
            query.setParameter("fromDateTimeThamGia", fromDateTimeThamGia.toString());
        }
        if(toDateTimeThamGia != null){
            query.setParameter("toDateTimeThamGia", toDateTimeThamGia.toString());
        }
        return Long.valueOf(query.getSingleResult().toString());
    }

    @Override
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi3And4TheoNgay(Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select b.issue_date ")
                .append("           , c.phan_loai ")
                .append("           , sum(b.total_credit) dt_tkc_t9 ")
                .append("           , sum(b.total_bonus)  dt_tkt_t9 ")
                .append("    from  QSV_2015_DS_THAM_GIA@MKITDW_KM_QSV_DBL a ")
                .append("          , QSV_2015_DTTD@MKITDW_KM_QSV_DBL b ")
                .append("          , QSV_2015_DM_ITEM@MKITDW_KM_QSV_DBL c ");
        if(branchId != null){
            sqlQueryClause.append("    , branch@MKITDW_KM_QSV_DBL d ");
        }
        if(districtId != null || branchId != null){
            sqlQueryClause.append("    , district@MKITDW_KM_QSV_DBL e ");
        }
        sqlQueryClause.append("  where 1 = 1 ")
                .append("           and a.thue_bao = b.calling_number ")
                .append("           and b.item_id = c.item_id ");
        if (branchId != null){
            sqlQueryClause.append(" and e.branch_id = d.branch_Id ")
                    .append("   and d.branch_Id = :branch_Id ");
        }
        if(districtId != null || branchId != null){
            sqlQueryClause.append(" and b.district_id = e.dttd_id ");
        }
        if(districtId != null){
            sqlQueryClause.append("   and e.district_Id = :dttd_Id ");
        }
        sqlQueryClause.append("     and c.phan_loai in ('t', 's', 'd', 'v', 'r', 'k') ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append(" and a.insert_date >= to_date(substr(:fromDateTimeThamGia, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia!= null){
            sqlQueryClause.append(" and a.insert_date < (to_date(substr(:toDateTimeThamGia, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append("     and b.issue_date >= to_date(substr('2015-03-01',1,10), 'YYYY-MM-DD') ")
                .append("   and b.issue_date < (to_date(substr('2015-03-31',1,10), 'YYYY-MM-DD') + 1) ")
                .append(" group by c.phan_loai, b.issue_date ")
                .append(" order by c.phan_loai, b.issue_date ");


        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTimeThamGia != null){
            query.setParameter("fromDateTimeThamGia", fromDateTimeThamGia.toString());
        }
        if(toDateTimeThamGia != null){
            query.setParameter("toDateTimeThamGia", toDateTimeThamGia.toString());
        }
        if(branchId != null){
            query.setParameter("branch_Id", branchId);
        }
        if(districtId != null){
            query.setParameter("dttd_Id", districtId);
        }
        return query.getResultList();
    }

    @Override
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi5And6TheoNgay(Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select b.issue_date ")
                .append("           , c.phan_loai ")
                .append("           , sum(b.total_credit) dt_tkc ")
                .append("           , sum(b.total_bonus)  dt_tkt ")
                .append("    from  QSV_2015_DS_THAM_GIA@MKITDW_KM_QSV_DBL a ")
                .append("          , QSV_2015_DTTD@MKITDW_KM_QSV_DBL b ")
                .append("          , QSV_2015_DM_ITEM@MKITDW_KM_QSV_DBL c ");
        if(branchId != null){
            sqlQueryClause.append("    , branch@MKITDW_KM_QSV_DBL d ");
        }
        if(districtId != null || branchId !=null){
            sqlQueryClause.append("    , district@MKITDW_KM_QSV_DBL e ");
        }
        sqlQueryClause.append("  where 1 = 1 ")
                .append("           and a.thue_bao = b.calling_number ")
                .append("           and b.item_id = c.item_id ");
        if (branchId != null){
            sqlQueryClause.append(" and e.branch_id = d.branch_Id ")
                    .append("   and d.branch_Id = :branch_Id ");
        }
        if (districtId !=null || branchId != null){
            sqlQueryClause.append(" and b.district_id = e.dttd_id ");
        }
        if(districtId != null){
            sqlQueryClause.append("   and e.district_Id = :dttd_Id ");
        }
        sqlQueryClause.append("     and c.phan_loai in ('t', 's', 'd', 'v', 'r', 'k') ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append(" and a.insert_date >= to_date(substr(:fromDateTimeThamGia, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia!= null){
            sqlQueryClause.append(" and a.insert_date < (to_date(substr(:toDateTimeThamGia, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        if(fromDateTimeReportFilter != null){
            sqlQueryClause.append(" and b.issue_date >= to_date(substr(:fromDateTimeReportFilter, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeReportFilter != null){
            sqlQueryClause.append(" and b.issue_date < (to_date(substr(:toDateTimeReportFilter, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append(" group by c.phan_loai, b.issue_date ")
                .append("   order by c.phan_loai, b.issue_date ");


        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTimeThamGia != null){
            query.setParameter("fromDateTimeThamGia", fromDateTimeThamGia.toString());
        }
        if(toDateTimeThamGia != null){
            query.setParameter("toDateTimeThamGia", toDateTimeThamGia.toString());
        }
        if(fromDateTimeReportFilter != null){
            query.setParameter("fromDateTimeReportFilter", fromDateTimeReportFilter.toString());
        }
        if(toDateTimeReportFilter != null){
            query.setParameter("toDateTimeReportFilter", toDateTimeReportFilter.toString());
        }
        if(branchId != null){
            query.setParameter("branch_Id", branchId);
        }
        if(districtId != null){
            query.setParameter("dttd_Id", districtId);
        }
        return query.getResultList();
    }

    @Override
    public String[] getExchangedMaPhieuListByList(String[] maPhieuList) {
        List<String> maPhieus = new ArrayList<String>();
        for(String maPhieu : maPhieuList){
            maPhieus.add(maPhieu);
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select ma_phieu from QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL where ma_phieu in (:maPhieus) and da_doi_qua = :da_doi_qua_status ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("maPhieus", maPhieus);
        query.setParameter("da_doi_qua_status", Constants.DA_GIAO_QUA);
        List resultSet = query.getResultList();

        if(resultSet != null && resultSet.size() > 0){
            String[] exchangedMaPhieuList = new String[resultSet.size()];
            int index = 0;
            for (Object object : resultSet){
                exchangedMaPhieuList[index] = object != null ? object.toString() : "";
                index++;
            }
            return exchangedMaPhieuList;
        }
        return null;
    }

    @Override
    public void shopUserGiaoQua(List<String> dsMaPhieus, String shopCode, String userName) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" update QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL ")
                .append("   set shop_code = :shopCode, user_name = :shopUserName, da_doi_qua = :daGiaoQuaStatus, ngay_doi_qua = :ngayDoiQua ")
                .append("   where ma_phieu in (:maPhieus) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("maPhieus", dsMaPhieus);
        query.setParameter("shopCode", shopCode);
        query.setParameter("shopUserName", userName);
        query.setParameter("daGiaoQuaStatus", Constants.NV_DOIQUA);
        query.setParameter("ngayDoiQua", new Timestamp(System.currentTimeMillis()));
        query.executeUpdate();
    }

    @Override
    public Boolean checkRegisterThueBao4QTeen(String thue_bao) throws ObjectNotFoundException {
        StringBuilder sqlQueryClause = new StringBuilder("select t.thue_bao, nvl(t.trang_thai, 0) as trang_thai from qsv_2015_ds_tham_gia@mkitdw_km_qsv_dbl t where t.thue_bao like :soThueBao");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soThueBao", "%" + CommonUtil.removeCountryCode(thue_bao) + "%");
        List list = query.getResultList();
        if(list.size() > 0){
            Object[] tmpArr = (Object[])list.get(0);
            if(tmpArr[1] != null && tmpArr[1].toString().equals("1")){
                return  true;
            }
            return false;
        }else{
            throw new ObjectNotFoundException("Thue bao nay chua dang ky tham gia chuong trinh");
        }
    }

    @Override
    public void updateShopStock_qStudent(Map<String, Object> properties) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" UPDATE QSV_2015_SHOP_STOCK_GOODS@MKITDW_KM_QSV_DBL sg ")
                .append("       SET sg.quantity = :quantity ")
                .append("       WHERE sg.stock_Id = :stockId AND sg.gift_Id = :giftId   ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("QUANTITY", properties.get("quantity"));
        query.setParameter("stockId", properties.get("stockId"));
        query.setParameter("giftId", properties.get("giftId"));
        query.executeUpdate();
    }

    @Override
    public void updateMaPhieuStatus2NoExchange(List<String> maPhieus) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" update QSV_2015_MA_PHIEU@MKITDW_KM_QSV_DBL ")
                .append("   set shop_code = null, user_name = null, da_doi_qua = 0, ngay_doi_qua = null ")
                .append("   where ma_phieu in (:maPhieus) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("maPhieus", maPhieus);
        query.executeUpdate();
    }

    @Override
    public Object[] baoCaoSoDiemChoThueBao(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("     from QSV_2015_DIEM@MKITDW_KM_QSV_DBL d ")
                 .append("       where 1 = 1 ");

        if(properties.get("soEz") != null){
            mainQuery.append(" and d.THUE_BAO = :so_Ez ");
        }
        if(properties.get("soDiemTo") != null){
            mainQuery.append(" and d.SO_DIEM >= :soDiem_To ");
        }
        if(properties.get("soDiemFrom") != null){
            mainQuery.append(" and d.SO_DIEM <= :soDiem_From  ");
        }
        mainQuery.append(" group by d.THUE_BAO  ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select d.THUE_BAO as soEZ, ")
                .append("              sum(d.SO_DIEM) as soDiem ")
                .append(mainQuery);


        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by d.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by d.THUE_BAO ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);

        if(properties.get("soEz") != null){
            queryClause.setParameter("so_Ez", Long.valueOf(properties.get("soEz").toString()));
        }
        if(properties.get("soDiemTo") != null){
            queryClause.setParameter("soDiem_To", Double.valueOf(properties.get("soDiemTo").toString()));
        }
        if(properties.get("soDiemFrom") != null){
            queryClause.setParameter("soDiem_From", Double.valueOf(properties.get("soDiemFrom").toString()));
        }

        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(soEZ) FROM (  ").append(sqlQueryClause).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("soEz") != null){
            countQuery.setParameter("so_Ez", properties.get("soEz").toString());
        }
        if(properties.get("soDiemTo") != null){
            countQuery.setParameter("soDiem_To", Double.valueOf(properties.get("soDiemTo").toString()));
        }
        if(properties.get("soDiemFrom") != null){
            countQuery.setParameter("soDiem_From", Double.valueOf(properties.get("soDiemFrom").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoPhatTrienGoi(Map<String, Object> properties, int firstItem, int reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  FROM (  SELECT  s.package_code, ")
                .append("                   p.price package_price, ")
                .append("                   s.accumulated_quantity, ")
                .append("                   s.new_reg_quantity,")
                .append("                   s.finished_quantity, ")
                .append("                   s.renewal_quantity, ")
                .append("                   (s.new_reg_quantity + s.renewal_quantity) * p.price ")
                .append("                       package_revenue ")
                .append("           FROM sum_prom_package@mkitdw_csdbl_dbl s, prom_package@mkitdw_csdbl_dbl p ")
                .append("           WHERE s.package_code = p.package_code  ");


        if(properties.get("packageId") != null){
            mainQuery.append("  AND p.package_Id = :packageId  ");
        }
        if(properties.get("fromDate") != null){
            mainQuery.append(" and s.sum_date >= to_date(substr(:fromDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDate") != null){
            mainQuery.append(" and s.sum_date < (to_date(substr(:toDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        mainQuery.append(" ) GROUP BY package_code,package_price  ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" SELECT package_code, ")
                .append("       package_price, ")
                .append("       MAX (accumulated_quantity) accumulated_quantity, ")
                .append("       SUM (new_reg_quantity) new_reg_quantity, ")
                .append("       SUM (finished_quantity) finished_quantity, ")
                .append("       SUM (renewal_quantity) renewal_quantity, ")
                .append("       SUM (package_revenue) package_revenue ")
                .append(mainQuery);


        sqlQueryClause.append(" ORDER BY package_code ");
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(reportMaxPageItems);

        if(properties.get("packageId") != null){
            queryClause.setParameter("packageId", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("fromDate") != null){
            queryClause.setParameter("fromDate", properties.get("fromDate").toString());
        }
        if(properties.get("toDate") != null){
            queryClause.setParameter("toDate", properties.get("toDate").toString());
        }
        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(*) FROM (  ").append(sqlQueryClause).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("packageId") != null){
            countQuery.setParameter("packageId", Long.valueOf(properties.get("packageId").toString()));
        }
        if(properties.get("fromDate") != null){
            countQuery.setParameter("fromDate", properties.get("fromDate").toString());
        }
        if(properties.get("toDate") != null){
            countQuery.setParameter("toDate", properties.get("toDate").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public List findAllPromPackage() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select package_Id, package_Code, package_Name, package_Type, price, create_DateTime, create_User, description, prom_Amount from prom_package@mkitdw_csdbl_dbl  ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        return query.getResultList();
    }


    @Override
    public void updatePaymentStatus(Long dealer_Id, List<Date> sumDateList, Timestamp paymentDate, String soEZ) {
        StringBuilder mainClause = new StringBuilder();
        mainClause.append(" update registration_trans@mkitdw_csdbl_dbl t ")
                .append(" set payment_status = 1, payment_date = :ngayChiTra ")
                .append(" where t.dealer_Id = :dealer_Id ")
                .append("   and t.payment_Status <> '1' ")
                .append("   and t.trans_Status = '1' ")
                .append("   and t.prom_Condition_Status = '1' ")
                .append("   and t.payment_Date is null ")
                .append("   and t.ez_Isdn = :soEZ ")
                .append("       and (( ")
                .append("               sum_Date >= to_date(substr(:sumDate, 1, 10), 'YYYY-MM-DD')        ")
                .append("               and sum_Date < to_date(substr(:sumDate, 1, 10), 'YYYY-MM-DD') + 1) ");


        for (int i = 1; i < sumDateList.size() ; i++){
            mainClause.append(" or ( ")
                    .append("       sum_Date >= to_date(substr('")
                    .append(new Timestamp(sumDateList.get(i).getTime()).toString())
                    .append("'    , 1, 10), 'YYYY-MM-DD') ")
                    .append("       and sum_Date < to_date(substr('")
                    .append(new Timestamp(sumDateList.get(i).getTime()).toString())
                    .append("'  , 1, 10), 'YYYY-MM-DD') + 1  ")
                    .append("  ) ");
        }
        mainClause.append("  ) ");

        Query query = entityManager.createNativeQuery(mainClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        query.setParameter("sumDate", new Timestamp(sumDateList.get(0).getTime()).toString());
        Date ngayChiTraDate = new Date();
        ngayChiTraDate.setTime(paymentDate.getTime());
        query.setParameter("ngayChiTra", ngayChiTraDate);
        query.setParameter("soEZ", soEZ);
        query.executeUpdate();
    }

    @Override
    public List findAllBranches() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select b.branch_Id, b.branch_code, b.branch_name from Branch@MKITDW_CSDBL_DBL b order by b.branch_code asc ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        return  query.getResultList();
    }

    @Override
    public List findAllDistricts() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select district_Id, district_code, district_name from district@MKITDW_CSDBL_DBL order by district_name ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        return query.getResultList();
    }

    @Override
    public List findAllRetailDealers() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select DEALER_ID, DEALER_CODE, DEALER_NAME from RETAIL_DEALER@MKITDW_CSDBL_DBL order by DEALER_NAME ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        return query.getResultList();
    }

    @Override
    public List findByDistrictIdAndByBranchId(Long branchId, Long districtId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        /*sqlQueryClause.append("select rd.dealer_id, rd.dealer_code, rd.dealer_name from retail_dealer@MKITDW_CSDBL_DBL rd ");
        sqlQueryClause.append(" where 1 = 1 ");*/
        sqlQueryClause.append(" select rd.dealer_Id, rd.dealer_Code, rd.dealer_Name ")
                .append("   from retail_dealer@MKITDW_CSDBL_DBL rd ")
                .append("   LEFT JOIN DISTRICT@MKITDW_CSDBL_DBL d on d.DISTRICT_ID = rd.DISTRICT_ID ")
                .append("   LEFT join branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_Id ")
                .append("   where 1 = 1  ");
        if(branchId != null){
            sqlQueryClause.append(" and b.branch_id = :branch_Id ");
        }
        if(districtId != null){
            sqlQueryClause.append(" and d.district_id = :district_Id ");
        }
        sqlQueryClause.append(" order by rd.dealer_code ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(branchId != null){
            query.setParameter("branch_Id", branchId);
        }
        if(districtId != null){
            query.setParameter("district_Id", districtId);
        }
        return query.getResultList();
    }

    @Override
    public List findDistrictListByBranchId(Long branchId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select d.district_Id, d.district_code,d.district_name from District@MKITDW_CSDBL_DBL d where d.branch_Id = :branch_Id order by d.district_code ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("branch_Id", branchId);
        return query.getResultList();
    }
    @Override
    public List findRetailDealerListByBranchId(Long branchId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select rd.dealer_Id, rd.dealer_Code, rd.dealer_Name ")
                .append("   from retail_dealer@MKITDW_CSDBL_DBL rd ")
                .append("   LEFT JOIN DISTRICT@MKITDW_CSDBL_DBL d on d.DISTRICT_ID = rd.DISTRICT_ID ")
                .append("   LEFT join branch@MKITDW_CSDBL_DBL b on b.branch_Id = d.branch_Id ")
                .append("   where b.branch_Id = :branch_Id ")
                .append("   order by rd.dealer_Code ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("branch_Id", branchId);
        return query.getResultList();
    }

    @Override
    public List findRetailDealerListByDistrictId(Long district_Id) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select dealer_Id, dealer_code, dealer_name from retail_dealer@MKITDW_CSDBL_DBL where district_id = :district_Id order by dealer_name ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("district_Id", district_Id);
        return query.getResultList();
    }

    @Override
    public Object[] searchDealerList(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        String soEZ = null;
        if(properties.get("ez") != null){
            soEZ = CommonUtil.removeCountryCode(properties.get("ez").toString());
        }

        StringBuilder mainClause = new StringBuilder();
        mainClause.append("     from DEALER_PROPERTIES@MKITDW_CSDBL_DBL dp ")
                .append("       inner join retail_dealer@MKITDW_CSDBL_DBL rd on rd.DEALER_ID = dp.DEALER_ID ")
                .append("       left join DISTRICT@MKITDW_CSDBL_DBL d on d.DISTRICT_ID = rd.DISTRICT_ID ")
                .append("       left join BRANCH@MKITDW_CSDBL_DBL b on b.BRANCH_ID = d.BRANCH_ID");
        mainClause.append("       where 1 = 1 ");

        if(properties.get("district_Id") != null){
            mainClause.append(" and d.DISTRICT_ID = :district_Id ");
        }
        if(properties.get("branch_Id") != null){
            mainClause.append(" and b.BRANCH_ID like :branch_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainClause.append(" and rd.dealer_Id = :dealer_Id ");
        }
        if(properties.get("ez") != null){
            mainClause.append(" and exists (select dp1.dealer_id from dealer_properties@MKITDW_CSDBL_DBL dp1 where  dp.dealer_Id = dp1.dealer_Id and dp1.PROPERTIES_VALUE like :ez) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select rd.DEALER_ID as dealerID, ")
                .append("              rd.DEALER_CODE as maDiemBanHang, ")
                .append("              rd.DEALER_NAME as tenDiemBanHang, ")
                .append("              d.DISTRICT_NAME as quanHuyen, ")
                .append("              b.BRANCH_NAME as chiNhanh, ")
                .append("              rd.CONTACT_NAME as tenLienLac, ")
                .append("              rd.EMAIL as email, ")
                .append("              rd.ADDRESS as diaChi, ")
//                .append("              (select LISTAGG(dp.properties_value, ';') WITHIN GROUP (ORDER BY properties_value) FROM dealer_properties@MKITDW_CSDBL_DBL dp where DP.DEALER_ID = RD.DEALER_ID ) AS soEz, ")
                .append("              dp.properties_value as soEZ, ")
                .append("              dp.IMPORT_DATETIME as ngayTao, ")
                .append("              dp.PROPERTIES_CODE as  HAVE_DOC,  ")
                .append("              dp.IS_PRIMARY as  isPrimary  ")
                .append(mainClause);
        Query queryClause = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by rd.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by rd.DEALER_CODE ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }

        queryClause.setFirstResult(firstItem);
        queryClause.setMaxResults(maxPageItems);
        if(properties.get("district_Id") != null){
            queryClause.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("branch_Id") != null){
            queryClause.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            queryClause.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(StringUtils.isNotBlank(soEZ)){
            queryClause.setParameter("ez", "%" + soEZ + "%");
        }
        List resultSet = queryClause.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(rd.DEALER_CODE) ").append(mainClause);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());

        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(StringUtils.isNotBlank(soEZ)){
            countQuery.setParameter("ez", "%" + soEZ + "%");
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{resultSet, count};
    }
}
