package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemMaPhieuDTO;
import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemSoDiemDTO;
import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemSoDiemStatisticByMonthDTO;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by hieu
 * Date: 10/11/14
 * Time: 2:12 PM
 */
@Stateless(name = "CTTichDiemSessionEJB")
public class CTTichDiemSessionBean implements CTTichDiemLocalBean{

    @PersistenceContext(unitName = "core-data")
    protected EntityManager entityManager;

    @Override
    public Object[] searchByThueBao(String phoneNumber, Integer da_doi_qua, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        if(StringUtils.isNotBlank(phoneNumber)){
            phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        }
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select t.thue_bao, t.ngay_ps, t.ma_phieu, t.da_doi_qua, t.ngay_doi_qua ")
                    .append("               , (select nvl(sum(t1.so_diem), 0) from ct_Tich_Diem_2014_diem@Mkitdw_Qlud2_Dbl t1 where t1.thue_bao = t.thue_bao) as diemTichLuy ")
                    .append("               , case when t.da_doi_qua = '2' then (select d.name as departmentName from Vms_department d where upper(d.code) = upper(t.shop_code)) else null end as tenCuaHang ")
                    .append("   from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl t where 1 = 1 ");

        if(StringUtils.isNotBlank(phoneNumber)){
            mainQuery.append(" and t.thue_bao like :thueBao ");
        }
        if(da_doi_qua != null && !da_doi_qua.equals(-1)){
            if(da_doi_qua.equals(2)){
                mainQuery.append(" and t.da_doi_qua = 2 ");
            }else{
                mainQuery.append(" and t.da_doi_qua != 2 ");
            }
        }
        if(StringUtils.isNotBlank(sortExpression)){
            mainQuery.append(" order by ").append(sortExpression);
        }else{
            mainQuery.append(" order by ngay_ps, ma_phieu ");
        }

        if(StringUtils.isNotBlank(sortDirection)){
            mainQuery.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            mainQuery.append(" desc ");
        }

        StringBuilder sqlQueryClause = new StringBuilder(" select * from ( ").append(mainQuery).append(" ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(StringUtils.isNotBlank(phoneNumber)){
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
        if (StringUtils.isNotBlank(phoneNumber)){
            countQuery.setParameter("thueBao", "%" + phoneNumber + "%");
        }
        if(da_doi_qua != null){
            if(da_doi_qua.equals(0)){
                mainQuery.append(" and t.da_doi_qua = 0 ");
            }else if(da_doi_qua.equals(1)){
                mainQuery.append(" and t.da_doi_qua > 0 ");
            }
        }
        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, dtoList};
    }

    @Override
    public Object[] searchBySoThueBaoAndNgayPS(String thueBao, Timestamp ngay_ps, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        thueBao = CommonUtil.removeCountryCode(thueBao);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select thue_bao, ma_phieu, ngay_ps, da_doi_qua, ngay_doi_qua, user_name, shop_code ")
                    .append("   from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl ")
                    .append("   where thue_bao like :thueBao and to_timestamp(substr(ngay_ps, 1, 10)) = to_timestamp(substr(:ngay_ps, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("')");
        if(StringUtils.isNotBlank(sortExpression)) {
            sqlQueryClause.append(" order by ").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by ngay_ps ");
        }

        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            sqlQueryClause.append(" desc ");
        }

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thueBao", "%" + thueBao + "%");
        query.setParameter("ngay_ps", ngay_ps.toString());

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
            dto.setMa_phieu(row[1] != null ? row[1].toString() : null);
            dto.setNgay_ps(row[2] != null ? Timestamp.valueOf(row[2].toString()) : null);
            dto.setDa_doi_qua(row[3] != null ? Integer.valueOf(row[3].toString()) : null);
            dto.setNgay_doi_qua(row[4] != null ? Timestamp.valueOf(row[4].toString()) : null);
            dto.setUser_name(row[5] != null ? row[5].toString() : null);
            dto.setShop_code(row[6] != null ? row[6].toString() : null);
            dtoList.add(dto);
        }

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(thue_bao) from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl where thue_bao like :thueBao and to_timestamp(substr(ngay_ps, 1, 10)) = to_timestamp(substr(:ngay_ps, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("')");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("thueBao", "%" + thueBao + "%");
        countQuery.setParameter("ngay_ps", ngay_ps.toString());

        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, dtoList};
    }

    @Override
    public Object[] searchByMaPhieu(String maPhieu, Integer da_doi_qua, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select t.thue_bao, t.ma_phieu, t.ngay_ps, t.da_doi_qua, t.ngay_doi_qua, t.user_name, t.shop_code ")
                    .append("               , (select nvl(sum(t1.so_diem), 0) from ct_Tich_Diem_2014_diem@Mkitdw_Qlud2_Dbl t1 where t1.thue_bao = t.thue_bao ) as diemTichLuyHienTai ")
                    .append("               , case when t.da_doi_qua = '2' then (select d.name as departmentName from Vms_department d where upper(d.code) = upper(t.shop_code)) else null end as tenCuaHang ")
                    .append("   from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl t ")
                    .append("   where 1= 1 ");
        if(StringUtils.isNotBlank(maPhieu)){
            mainQuery.append(" and ma_phieu = :maPhieu ");
        }
        if(da_doi_qua != null && !da_doi_qua.equals(-1)){
            if(da_doi_qua.equals(2)){
                mainQuery.append(" and da_doi_qua = 2 ");
            }else{
                mainQuery.append(" and da_doi_qua != 2 ");
            }
        }

        if(StringUtils.isNotBlank(sortExpression)){
            mainQuery.append(" order by ").append(sortDirection);
        }else{
            mainQuery.append(" order by ngay_ps, ma_phieu ");
        }

        if(StringUtils.isNotBlank(sortDirection)){
            mainQuery.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            mainQuery.append(" desc ");
        }

        StringBuilder sqlQueryClause = new StringBuilder("select * from (").append(mainQuery).append(" ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(StringUtils.isNotBlank(maPhieu)){
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
        if(StringUtils.isNotBlank(maPhieu)){
            countQuery.setParameter("maPhieu", maPhieu);
        }
        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, dtoList};
    }

    @Override
    public void sendSMS(String serverIP, String phoneNumber, String content) {
        phoneNumber = phoneNumber.replace(Constants.PREFIX_KPP_USERNAME, "");
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        Query query = entityManager.createNativeQuery("INSERT INTO sms_tt2.voucher_app_sms_queue@CHINHANH_CT_KM_VOUCHER_TT2_DW2_DBL VALUES(:phoneNumber, :content, :serverIP, sysdate)");
        query.setParameter("serverIP", serverIP);
        query.setParameter("phoneNumber", CommonUtil.removeCountryCode(phoneNumber));
        query.setParameter("content", content);
        query.executeUpdate();
    }

    @Override
    public void shopUserGiftExchange(List<String> dsMaPhieus, String shopCode, String shopUserName) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" update ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl ")
                    .append("   set shop_code = :shopCode, user_name = :shopUserName, da_doi_qua = :daGiaoQuaStatus, ngay_doi_qua = :ngayDoiQua ")
                    .append("   where ma_phieu in (:maPhieus) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("maPhieus", dsMaPhieus);
        query.setParameter("shopCode", shopCode);
        query.setParameter("shopUserName", shopUserName);
        query.setParameter("daGiaoQuaStatus", Constants.NV_DOIQUA);
        query.setParameter("ngayDoiQua", new Timestamp(new Date().getTime()));
        query.executeUpdate();
    }

    @Override
    public Object[] statisticCumulativeScoresByDate(String phoneNumber, String whereClause, Integer offset, Integer limit) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select t.thue_bao, t.ngay_ps, nvl(sum(t.amount), 0) as amount, t.type_input from ct_tich_diem_2014_psc@mkitdw_qlud2_dbl t ").append(whereClause);
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

        StringBuilder sqlCountClause = new StringBuilder("select count(distinct(to_timestamp(substr(t.ngay_ps, 1, 10)))) as total from ct_tich_diem_2014_psc@mkitdw_qlud2_dbl t where t.thue_bao like :soThueBao ");
        query = entityManager.createNativeQuery(sqlCountClause.toString());
        query.setParameter("soThueBao", phoneNumber);
        Long count = Long.valueOf(query.getSingleResult().toString());
        return new Object[]{dtoList.size(), dtoList};
    }

    @Override
    public Boolean checkIfAlreadyRegistered(String phoneNumber) throws ObjectNotFoundException{
        StringBuilder sqlQueryClause = new StringBuilder("select t.thue_bao, nvl(t.trang_thai, 0) as trang_thai from ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl t where t.thue_bao like :soThueBao");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soThueBao", "%" + CommonUtil.removeCountryCode(phoneNumber) + "%");
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
    public Boolean checkIfAlreadyRegister4KPP(String phoneNumber) throws ObjectNotFoundException {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select count(properties_value) from dealer_properties@mkitdw_qlud2_dbl where properties_value like :thue_bao ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + phoneNumber + "%");
        Integer count = Integer.valueOf(query.getSingleResult().toString());
        if(count.compareTo(0) > 0){
            return true;
        }else{
            throw new ObjectNotFoundException("So thue bao nay chua dang ky nhan ma OTP cho khach hanh KPP");
        }
    }

    @Override
    public Object[] searchDanhSachMaPhieu(String phoneNumber, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select t.* from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl t where t.thue_bao like :soThueBao order by t.da_doi_qua ");
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
        sqlCountClause.append("select count(t.thue_bao) from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl t where t.thue_bao like :soThueBao order by t.da_doi_qua");
        query = entityManager.createNativeQuery(sqlCountClause.toString());
        query.setParameter("soThueBao", "%" + phoneNumber + "%");
        Long count = Long.valueOf(query.getSingleResult().toString());

        return new Object[]{count, dtoList};
    }

    @Override
    public Integer getCurrentPointTotal(String phoneNumber) {
        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select nvl(sum(t.so_diem), 0) as totalScore ")
                    .append("   from ct_tich_diem_2014_diem@mkitdw_qlud2_dbl t ")
                    .append("   where t.thue_bao like :soThueBao ");
        Query query = entityManager.createNativeQuery(sqlCountClause.toString());
        query.setParameter("soThueBao", "%" + CommonUtil.removeCountryCode(phoneNumber) + "%");
        return  Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public Object[] statisticCumulativeScoresByMonth(String phoneNumber) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("select distinct(extract(month from t.ngay_ps)) as month, nvl(sum(t.amount), 0) as total_amount from ct_tich_diem_2014_psc@mkitdw_qlud2_dbl t ")
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
    public Integer countSoPhieuChuaDoi(String phoneNumber) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select count(t.thue_bao) from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl t where t.thue_bao like :soThueBao and t.da_doi_qua = 0");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soThueBao", "%" + phoneNumber + "%");
        return Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public void updateExchangeStatus4Maphieu(String phoneNumber) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" update ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl t set t.da_doi_qua = :status, t.ngay_doi_qua = :current_date where t.thue_bao like :soThueBao and t.da_doi_qua = 0 ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soThueBao", "%" + phoneNumber + "%");
        query.setParameter("status", Constants.THUEBAO_DOIQUA);
        query.setParameter("current_date", new Date());
        query.executeUpdate();
    }

    @Override
    public String findUserPhoneNumberByMaPhieu(String ticketCode) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("select thue_bao from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl where ma_phieu = :ma_phieu ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("ma_phieu", ticketCode);
        List list = query.getResultList();
        if(list.size() > 0){
            return  list.get(0).toString();
        }
        return null;
    }

    @Override
    public Integer getTotalOfExchangedMaPhieu() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select count(da_doi_qua) as total from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl where da_doi_qua = :daDoiQua");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("daDoiQua", Constants.DA_GIAO_QUA);
        return Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public Integer getTotalOfExchangedMaPhieu(String phoneNumber) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select nvl(count(ma_phieu), 0) as total from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl where thue_bao like :thue_bao and da_doi_qua = :da_doi_qua ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + phoneNumber + "%");
        query.setParameter("da_doi_qua", Constants.DA_GIAO_QUA);
        return Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public List messageManagementReport(String loaiTinNhanFromKHCN, String loaiTinNhanFromVMS, Timestamp fromDateTime, Timestamp toDateTime) {
        Query query = entityManager.createNamedQuery("baocaoquanlytinnhan");
        List<String> dsLoaiTinNhanFromKHCN = new ArrayList<String>();
        if(StringUtils.isNotBlank(loaiTinNhanFromKHCN)){
            dsLoaiTinNhanFromKHCN.add(loaiTinNhanFromKHCN);
        }else{
            dsLoaiTinNhanFromKHCN.add(Constants.MOBIFONE_DK);
            dsLoaiTinNhanFromKHCN.add(Constants.MOBIFONE_HUY);
            dsLoaiTinNhanFromKHCN.add(Constants.MOBIFONE_KT);
            dsLoaiTinNhanFromKHCN.add(Constants.MOBIFONE_DP);
        }

        List<String> dsLoaiTinNhanFromVMS = new ArrayList<String>();
        if(StringUtils.isNotBlank(loaiTinNhanFromVMS)){
            dsLoaiTinNhanFromVMS.add(loaiTinNhanFromVMS);
        }else{
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_1);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_2_1);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_2_2);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_3);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_4_1);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_4_2);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_5);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_6);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_7_1);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_7_2);
            dsLoaiTinNhanFromVMS.add(Constants.LOAITINNHAN_8);
        }
        query.setParameter("dsLoaiTinNhanFromKHCN", dsLoaiTinNhanFromKHCN);
        query.setParameter("dsLoaiTinNhanFromVMS", dsLoaiTinNhanFromVMS);
        if(fromDateTime != null){
            query.setParameter("fromDate", fromDateTime.toString());
        }else{
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(2014, 0, 1);
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
    public Object[] searchSubscriberInfo(String phoneNumber, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);

        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl mp ")
                .append("   left join Vms_department d on upper(d.code) = upper(mp.shop_Code) ")
                .append("   where mp.thue_bao like :thue_bao ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select mp.ma_phieu, mp.ngay_ps, mp.da_doi_qua, mp.ngay_doi_qua, d.name as ten_cua_hang ").append(mainQuery);
        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by mp.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by mp.ngay_ps ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            sqlQueryClause.append(" desc ");
        }

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + phoneNumber + "%");
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        List list = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(mp.ma_phieu) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("thue_bao", "%" + phoneNumber + "%");
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, list};
    }

    @Override
    public List findRetailDealerList(Long branchId, Long districtId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("select rd.dealer_id, rd.dealer_code, rd.dealer_name from retail_dealer@mkitdw_qlud2_dbl rd ");
        sqlQueryClause.append(" where 1 = 1 ");
        if(branchId != null){
            sqlQueryClause.append(" and rd.branch_id = :branch_Id ");
        }
        if(districtId != null){
            sqlQueryClause.append(" and rd.district_id = :district_Id ");
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
    public Object[] baoCaoThongTinThiTruongTheoDaiLy(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select branch_name, district_name, dealer_code, dealer_name, address ")
                .append("           , nvl(sum(slBanSimViettel), 0) as slBanSimViettel ")
                .append("           , nvl(sum(giaSimViettel), 0) / nvl(count(giaSimViettel), 1) as giaSimViettelTrungBinh ")
                .append("           , nvl(sum(slBanTheVietel), 0) as slBanTheVietel ")
                .append("           , nvl(sum(slBanSimMobi), 0) as slBanSimMobi ")
                .append("           , nvl(sum(giaSimMobi), 0) / nvl(count(giaSimMobi), 1) as giaSimMobiTrungBinh ")
                .append("           , nvl(sum(slBanTheMobi), 0) as slBanTheMobi ")
                .append("           , nvl(sum(slBanSimVina), 0) as slBanSimVina ")
                .append("           , nvl(sum(giaSimVina), 0) / nvl(count(giaSimVina), 1) as giaSimVinaTrungBinh ")
                .append("           , nvl(sum(slBanTheVina), 0) as slBanTheVina ")
                .append("   from ( ")
                .append("       select b.branch_name, d.district_name, rd.dealer_code, rd.dealer_name, rd.address ")
                .append("           , kpp.vt_sim_quantity as slBanSimViettel, kpp.vt_sim_price as giaSimViettel, kpp.vt_card_quantity as slBanTheVietel ")
                .append("           , kpp.mb_sim_quantity as slBanSimMobi, kpp.mb_sim_price as giaSimMobi, kpp.mb_card_quantity as slBanTheMobi ")
                .append("           , kpp.vp_sim_quantity as slBanSimVina, kpp.vp_sim_price as giaSimVina, kpp.vp_card_quantity as slBanTheVina ")
                .append("       from kpp_market_info@mkitdw_qlud2_dbl kpp ")
                .append("       inner join retail_dealer@mkitdw_qlud2_dbl rd on kpp.dealer_id = rd.dealer_id ")
                .append("       inner join branch@mkitdw_qlud2_dbl b on rd.branch_id = b.branch_id ")
                .append("       inner join district@mkitdw_qlud2_dbl d on d.district_id = rd.district_id ")
                .append("       where 1 = 1 ");

        if(properties.get("branchId") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append(" and rd.dealer_Id = :dealer_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and kpp.issue_date >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and trunc(kpp.issue_date) < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        mainQuery.append("   ) ")
                .append("   group by branch_name, district_name, dealer_code, dealer_name, address ")
                .append("   order by branch_name, district_name, dealer_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branchId") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List list = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branchId") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, list};
    }

    @Override
    public Object[] baoCaoThongTinThiTruongTheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select branch_name, district_name ")
                .append("           , nvl(sum(slBanSimViettel), 0) as slBanSimViettel ")
                .append("           , nvl(sum(giaSimViettel), 0) / nvl(count(giaSimViettel), 1) as giaSimViettelTrungBinh ")
                .append("           , nvl(sum(slBanTheVietel), 0) as slBanTheVietel ")
                .append("           , nvl(sum(slBanSimMobi), 0) as slBanSimMobi ")
                .append("           , nvl(sum(giaSimMobi), 0) / nvl(count(giaSimMobi), 1) as giaSimMobiTrungBinh ")
                .append("           , nvl(sum(slBanTheMobi), 0) as slBanTheMobi ")
                .append("           , nvl(sum(slBanSimVina), 0) as slBanSimVina ")
                .append("           , nvl(sum(giaSimVina), 0) / nvl(count(giaSimVina), 1) as giaSimVinaTrungBinh ")
                .append("           , nvl(sum(slBanTheVina), 0) as slBanTheVina ")
                .append("   from ( ")
                .append("       select b.branch_name, d.district_name, rd.dealer_id ")
                .append("           , kpp.vt_sim_quantity as slBanSimViettel, kpp.vt_sim_price as giaSimViettel, kpp.vt_card_quantity as slBanTheVietel ")
                .append("           , kpp.mb_sim_quantity as slBanSimMobi, kpp.mb_sim_price as giaSimMobi, kpp.mb_card_quantity as slBanTheMobi ")
                .append("           , kpp.vp_sim_quantity as slBanSimVina, kpp.vp_sim_price as giaSimVina, kpp.vp_card_quantity as slBanTheVina ")
                .append("       from kpp_market_info@mkitdw_qlud2_dbl kpp ")
                .append("       inner join retail_dealer@mkitdw_qlud2_dbl rd on kpp.dealer_id = rd.dealer_id ")
                .append("       inner join branch@mkitdw_qlud2_dbl b on rd.branch_id = b.branch_id ")
                .append("       inner join district@mkitdw_qlud2_dbl d on d.district_id = rd.district_id ")
                .append("       where 1 = 1 ");

        if(properties.get("branchId") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.district_Id = :district_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and kpp.issue_Date >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and kpp.issue_Date < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        mainQuery.append(" ) ")
                .append(" group by branch_name, district_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);

        if(properties.get("branchId") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
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
    public Object[] baoCaoThongTinThiTruongTheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select branch_name ")
                .append("           , nvl(sum(slBanSimViettel), 0) as slBanSimViettel ")
                .append("           , nvl(sum(giaSimViettel), 0) / nvl(count(giaSimViettel), 1) as giaSimViettelTrungBinh ")
                .append("           , nvl(sum(slBanTheVietel), 0) as slBanTheVietel ")
                .append("           , nvl(sum(slBanSimMobi), 0) as slBanSimMobi ")
                .append("           , nvl(sum(giaSimMobi), 0) / nvl(count(giaSimMobi), 1) as giaSimMobiTrungBinh ")
                .append("           , nvl(sum(slBanTheMobi), 0) as slBanTheMobi ")
                .append("           , nvl(sum(slBanSimVina), 0) as slBanSimVina ")
                .append("           , nvl(sum(giaSimVina), 0) / nvl(count(giaSimVina), 1) as giaSimVinaTrungBinh ")
                .append("           , nvl(sum(slBanTheVina), 0) as slBanTheVina ")
                .append("   from ( ")
                .append("       select b.branch_name, d.district_name, rd.dealer_id ")
                .append("           , kpp.vt_sim_quantity as slBanSimViettel, kpp.vt_sim_price as giaSimViettel, kpp.vt_card_quantity as slBanTheVietel ")
                .append("           , kpp.mb_sim_quantity as slBanSimMobi, kpp.mb_sim_price as giaSimMobi, kpp.mb_card_quantity as slBanTheMobi ")
                .append("           , kpp.vp_sim_quantity as slBanSimVina, kpp.vp_sim_price as giaSimVina, kpp.vp_card_quantity as slBanTheVina ")
                .append("       from kpp_market_info@mkitdw_qlud2_dbl kpp ")
                .append("       inner join retail_dealer@mkitdw_qlud2_dbl rd on kpp.dealer_id = rd.dealer_id ")
                .append("       inner join branch@mkitdw_qlud2_dbl b on rd.branch_id = b.branch_id ")
                .append("       inner join district@mkitdw_qlud2_dbl d on d.district_id = rd.district_id ")
                .append("       where 1 = 1 ");

        if(properties.get("branchId") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and d.district_Id = :district_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and kpp.issue_Date >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and kpp.issue_Date < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        mainQuery.append("  ) group by branch_name ")
                .append("   order by branch_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);

        if(properties.get("branchId") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }

        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
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
    public Object[] traCuuPhatSinhTheoNgay4KPP(Integer thangTichDiem, Long item_Id, String thue_bao, Timestamp fromDate, Timestamp toDate, Integer offfset, Integer limit, String sortExpression, String sortDirection) {
        thue_bao = CommonUtil.removeCountryCode(thue_bao);
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from Dealer_Account_Action@mkitdw_Qlud2_Dbl m ")
                .append("   left join prom_items@mkitdw_Qlud2_Dbl i on i.item_Id = m.item_Id ")
                .append("   Where m.dealer_Id = (select dp.dealer_Id from Dealer_Properties@mkitdw_Qlud2_Dbl dp where dp.properties_Value like :thue_bao ) ")
                .append("           and extract(month from m.issue_date) = :thangTichDiem and (i.item_Id between 1 and 7) ");

        if(fromDate != null){
            mainQuery.append(" and to_date(substr(m.issue_date,1,10)) >= to_date(substr(:fromDate").append(",1,10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(toDate != null){
            mainQuery.append(" and to_date(substr(m.issue_date,1,10)) < (to_date(substr(:toDate").append(",1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        if(item_Id != null){
            mainQuery.append(" and m.item_Id = :item_Id ");
        }

        StringBuilder sqlQueryClause = new StringBuilder(" select i.item_name, m.issue_date, nvl(m.prom_point, 0) as from_point, i.item_Id, m.dealer_Id ").append(mainQuery);
        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by m.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by m.issue_date ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            sqlQueryClause.append(" desc ");
        }
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + thue_bao + "%");
        query.setParameter("thangTichDiem", thangTichDiem);
        if(fromDate != null){
            query.setParameter("fromDate", fromDate.toString());
        }
        if(toDate != null){
            query.setParameter("toDate", toDate.toString());
        }
        if(item_Id != null){
            query.setParameter("item_Id", item_Id);
        }
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(m.dealer_Id) ").append(mainQuery.toString());
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("thue_bao", "%" + thue_bao + "%");
        countQuery.setParameter("thangTichDiem", thangTichDiem);
        if(fromDate != null){
            countQuery.setParameter("fromDate", fromDate.toString());
        }
        if(toDate != null){
            countQuery.setParameter("toDate", toDate.toString());
        }
        if(item_Id != null){
            countQuery.setParameter("item_Id", item_Id);
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Integer getCurrentPointTotal4KPP(String thue_bao) {
        thue_bao = thue_bao.replace(Constants.PREFIX_KPP_USERNAME, "");
        thue_bao = CommonUtil.removeCountryCode(thue_bao);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select nvl(sum(t.prom_point), 0) as totalScore from dealer_account_action@mkitdw_qlud2_dbl t, retail_dealer@mkitdw_qlud2_dbl t1 ")
                    .append("   where t.dealer_Id = t1.dealer_Id and t1.dealer_Id = (select distinct t2.dealer_Id from dealer_properties@mkitdw_qlud2_dbl t2 where properties_value like :thue_bao) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + thue_bao + "%");
        return Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public Object[] searchWinningTicketList(String phoneNumber, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
       phoneNumber = phoneNumber.replace(Constants.PREFIX_KPP_USERNAME, "");
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
       StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" from ct_tich_diem_kpp_2014_ma_phieu@mkitdw_qlud2_dbl m, dealer_properties@mkitdw_qlud2_dbl dp ")
                .append("  where m.dealer_id = dp.dealer_id and dp.properties_value like :thue_bao ");
        if(StringUtils.isNotBlank(sortExpression)){
            mainQuery.append(" order by m.").append(sortExpression);
        }else{
            mainQuery.append(" order by m.insert_dateTime ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            mainQuery.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            mainQuery.append(" desc ");
        }

        StringBuilder sqlQueryClause = new StringBuilder(" select m.cycle, m.ma_phieu, m.exchange_gift_status, m.exchange_gift_name ").append(mainQuery);
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + phoneNumber + "%");
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(m.ma_phieu) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("thue_bao", "%" + phoneNumber + "%");
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] TraCuuThongTinDoiDiemDBH(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" from kpp_dealer_account@mkitdw_qlud2_dbl da ")
                .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = da.dealer_Id ")
                .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_Id = rd.branch_Id ")
                .append("   inner join district@mkitdw_qlud2_dbl d on d.district_Id = rd.district_Id ")
                .append("   where 1 = 1 ");
        if(properties.get("branchId") != null){
            mainQuery.append(" and b.branch_Id = :branchId ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append(" and d.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append(" and rd.dealer_Id = :dealer_Id ");
        }
        if(properties.get("dealer_code") != null){
            mainQuery.append(" and rd.dealer_code like :dealer_code ");
        }
        if(properties.get("trangThaiChotKy") != null){
            mainQuery.append(" and da.cycle_lock_status = :trang_thai_chot_ky ");
        }
        if(properties.get("trangThaiDoiTien") != null){
            mainQuery.append(" and da.exchange_money_status = :trang_thai_doi_tien ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append(" and extract(month from da.cycle) = :cycle ");
        }
        if(properties.get("trangThaiDuDieuKienDoiTien") != null){
            if(properties.get("trangThaiDuDieuKienDoiTien").toString().equalsIgnoreCase(Constants.TRANG_THAI_DOI_TIEN_DAT_KPP)){
                mainQuery.append(" and condition_status = '1' ");
            }else if(properties.get("trangThaiDuDieuKienDoiTien").toString().equalsIgnoreCase(Constants.TRANG_THAI_DOI_TIEN_KHONG_DAT_KPP)){
                mainQuery.append(" and condition_status = '0' ");
            }
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( select rd.dealer_Id, rd.dealer_code, rd.dealer_name, da.cycle ")
                    .append("                           , da.prom_point as soDiemQuyDoi, da.prom_amount as soTienQuyDoi ")
                    .append("                           , da.cycle_lock_status as trangThaiChoPhepDoiTien ")
                    .append("                           , d.district_name ")
                    .append("                           , da.exchange_money_status ")
                    .append("                           , b.branch_name ")
                    .append("                           , case when da.condition_status is null then '0' else da.condition_status end as condition_status ")
                    .append(mainQuery)
                    .append("   ) ")
                    .append("   order by branch_name, district_name, dealer_name, cycle ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("dealer_code") != null){
            query.setParameter("dealer_code", properties.get("dealer_code").toString());
        }
        if(properties.get("trangThaiChotKy") != null){
            query.setParameter("trang_thai_chot_ky", properties.get("trangThaiChotKy").toString());
        }
        if(properties.get("trangThaiDoiTien") != null){
            query.setParameter("trang_thai_doi_tien", properties.get("trangThaiDoiTien").toString());
        }
        if(properties.get("cycle") != null){
            query.setParameter("cycle", Integer.valueOf(properties.get("cycle").toString()));
        }
        if(properties.get("branchId") != null){
            query.setParameter("branchId", Long.valueOf(properties.get("branchId").toString()));
        }
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(da.dealer_Id) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("dealer_code") != null){
            countQuery.setParameter("dealer_code", properties.get("dealer_code").toString());
        }
        if(properties.get("trangThaiChotKy") != null){
            countQuery.setParameter("trang_thai_chot_ky", properties.get("trangThaiChotKy").toString());
        }
        if(properties.get("trangThaiDoiTien") != null){
            countQuery.setParameter("trang_thai_doi_tien", properties.get("trangThaiDoiTien").toString());
        }
        if(properties.get("cycle") != null){
            countQuery.setParameter("cycle", Integer.valueOf(properties.get("cycle").toString()));
        }
        if(properties.get("branchId") != null){
            countQuery.setParameter("branchId", Long.valueOf(properties.get("branchId").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public List findDistrictListByBranchId(Long branchId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select d.district_Id, d.district_code,d.district_name, d.city_Id from District@mkitdw_qlud2_dbl d where d.branch_Id = :branch_Id order by d.district_code ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("branch_Id", branchId);
        return query.getResultList();
    }

    @Override
    public List findAllBranches() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select b.branch_Id, b.branch_code, b.branch_name from Branch@mkitdw_qlud2_dbl b order by b.branch_code asc ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        return  query.getResultList();
    }

    @Override
    public String getAgencyInformation(String phoneNumber) throws ObjectNotFoundException{
        phoneNumber = phoneNumber.replace(Constants.PREFIX_KPP_USERNAME, "");
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select dp.properties_value, rd.dealer_code, rd.dealer_name ")
                    .append("   from dealer_properties@mkitdw_qlud2_dbl dp ")
                    .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_id = dp.dealer_id ")
                    .append("   where dp.properties_value like :thue_bao ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + phoneNumber + "%");
        List resultSet = query.getResultList();
        if(resultSet != null && resultSet.size() > 0){
            Object[] tmpArr = (Object[])resultSet.get(0);
            StringBuilder builder = new StringBuilder();
            if(tmpArr[2] != null){
                builder.append(tmpArr[2].toString());
            }
            if(tmpArr[1] != null){
                builder.append("_").append(tmpArr[1].toString());
            }
            if(tmpArr[0] != null){
                builder.append("_").append(tmpArr[0].toString());
            }
            return builder.toString();
        }else{
            throw new ObjectNotFoundException("Can not build thongTinDBH for thue_bao: " + phoneNumber);
        }
    }

    @Override
    public List findAllPromItems() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select item_id, item_code, item_name from Prom_Items@mkitdw_qlud2_dbl where item_Id between 1 and 7 order by item_code ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        return query.getResultList();
    }

    @Override
    public List getThongTinMaTrungThuong(String thue_bao, String maDuThuong) throws ObjectNotFoundException {
        try{
            thue_bao = thue_bao.replace(Constants.PREFIX_KPP_USERNAME, "");
            thue_bao = CommonUtil.removeCountryCode(thue_bao);
            StringBuilder sqlQueryClause = new StringBuilder();
            sqlQueryClause.append(" select mp.dealer_Id, mp.dealer_Code, mp.item_Id, mp.ma_phieu, mp.cycle, mp.insert_dateTime, mp.sms_status, mp.exchange_gift_status, mp.exchange_gift_Id, mp.exchange_gift_name, mp.winning_status ")
                    .append("   from ct_tich_diem_kpp_2014_ma_phieu@mkitdw_qlud2_dbl mp ")
                    .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = mp.dealer_Id ")
                    .append("   inner join dealer_properties@mkitdw_qlud2_dbl dp on dp.dealer_Id = rd.dealer_Id ")
                    .append("   where upper(mp.ma_phieu) = upper(:ma_du_thuong) ")
                    .append("       and dp.properties_value like :thue_bao ");
            Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
            query.setParameter("thue_bao", "%" + thue_bao + "%");
            query.setParameter("ma_du_thuong", maDuThuong);
            List resultSet = query.getResultList();
            if(resultSet != null && resultSet.size() > 0){
                return resultSet;
            }
            return null;
        }catch (Exception e){
            throw new ObjectNotFoundException("Not found ma du thuong: " + maDuThuong);
        }
    }

    @Override
    public Object[] getWinningCodeList(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from ct_tich_diem_kpp_2014_ma_phieu@mkitdw_qlud2_dbl mp ")
                .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = mp.dealer_Id ")
                .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_Id = rd.branch_Id ")
                .append("   inner join district@mkitdw_qlud2_dbl d on d.district_Id = rd.district_Id ")
                .append("   inner join prom_items@mkitdw_qlud2_dbl i on i.item_Id = mp.item_Id ")
                .append("   where 1 = 1 and (i.item_id between 1 and 7) ");
        if(properties.get("branch_Id") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append(" and d.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append(" and rd.dealer_Id = :dealer_Id ");
        }
        if(properties.get("item_Id") != null){
            mainQuery.append(" and i.item_Id = :item_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and mp.insert_DateTime >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and mp.insert_DateTime < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select b.branch_Name, d.district_Name, i.item_Name, mp.insert_DateTime, rd.dealer_code, rd.dealer_Name, rd.ez_Isdn, mp.ma_phieu, 0 as trang_thai_quay_so, null as dat_giai ")
                    .append(mainQuery)
                    .append("   order by b.branch_Name, d.district_Name, rd.dealer_name, mp.insert_dateTime ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            query.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(mp.dealer_Id) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            countQuery.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
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
    public Object[] baoCaoTheoHangMucPhatSinh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select dac.dealer_Id, rd.dealer_code, rd.dealer_name, b.branch_Id, b.branch_name, d.district_Id, d.district_name ")
                .append("           , i.item_Id, i.item_name, i.item_unit ")
                .append("           , nvl(sum(dac.amount), 0) as soPS ")
                .append("           , extract(month from da.cycle) as cycle ")
                .append("           , nvl(sum(dac.prom_point), 0) as quyDiem ")
                .append("           , nvl(sum(dac.prom_amount), 0) as soTienTuongUng ")
                .append("           , nvl(sum(dac.ticket_quantity), 0) as soMaDuThuong ")
                .append("           ,da.kit_sales_condition_status, da.sms_sales_condition_status, da.sub_charge_condition_status ")
                .append("           ,da.sub_intro_condition_status, da.vas_sales_condition_status, da.market_info_condition_status ")
                .append("   from dealer_account_action@mkitdw_qlud2_dbl dac ")
                .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = dac.dealer_Id ")
                .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_Id = rd.branch_Id ")
                .append("   inner join district@mkitdw_qlud2_dbl d on d.district_Id = rd.district_Id ")
                .append("   inner join prom_items@mkitdw_qlud2_dbl i on i.item_Id = dac.item_Id ")
                .append("   inner join dealer_account@mkitdw_qlud2_dbl da on da.dealer_Id = dac.dealer_Id and da.item_Id = dac.item_Id and extract(month from dac.issue_date) = extract(month from da.cycle) ")
                .append("   where 1 = 1 and (i.item_Id between 1 and 7) and da.amount > 0 ");
        if(properties.get("branch_Id") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append(" and d.district_Id = :district_Id ");
        }
        if(properties.get("item_Id") != null){
            mainQuery.append(" and i.item_Id = :item_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and dac.issue_Date >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and dac.issue_Date < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        mainQuery.append("   group by dac.dealer_Id, rd.dealer_code, rd.dealer_name, b.branch_Id, b.branch_name, d.district_Id, d.district_name, i.item_Id, i.item_name, i.item_unit, da.cycle ")
                .append("           ,da.kit_sales_condition_status, da.sms_sales_condition_status, da.sub_charge_condition_status ")
                .append("           ,da.sub_intro_condition_status, da.vas_sales_condition_status, da.market_info_condition_status ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ")
                    .append(mainQuery)
                    .append(" ) ")
                    .append("   order by branch_name, district_name, dealer_name, cycle ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            query.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(dealer_Id) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            countQuery.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
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
    public Object[] baoCaoTienThuongMaDuThuongTheoDaiLy(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from ( select dac.dealer_Id as dealer_Id1, dac.item_Id as item_Id1 ")
                .append("               , dac.issue_date as ngay_ps ")
                .append("               , nvl(dac.execute_amount, 0)  as soLuongThucTe ")
                .append("               , nvl(dac.amount, 0)          as soDuocTinhDiem ")
                .append("               , nvl(dac.exchange_amount, 0) as soDuocQuiDoi ")
                .append("               , nvl(dac.prom_point, 0)      as soDiemQuiDoi ")
                .append("               , nvl(dac.prom_amount, 0)     as soTienQuiDoi ")
                .append("               , nvl(dac.ticket_quantity, 0) as soMaDuThuong ")
                .append("           from dealer_account_action@mkitdw_qlud2_dbl dac ")
                .append("           where dac.amount > 0 ");
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and dac.issue_date >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and dac.issue_date < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
         mainQuery.append("       ) d ")
                .append("   inner join kpp_dealer_account@mkitdw_qlud2_dbl da on da.dealer_Id = d.dealer_id1 ")
                .append("          and extract(month from da.cycle) = extract(month from d.ngay_ps) ")
                .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = da.dealer_Id ")
                .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_Id = rd.branch_Id ")
                .append("   inner join district@mkitdw_qlud2_dbl d on d.district_Id = rd.district_Id ")
                .append("   inner join prom_items@mkitdw_qlud2_dbl i on i.item_Id = d.item_Id1 ")
                .append("   where (i.item_Id between 1 and 7) ");
        if(properties.get("branch_Id") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append(" and d.district_Id = :district_Id ");
        }
        if(properties.get("item_Id") != null){
            mainQuery.append(" and i.item_Id = :item_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append(" and rd.dealer_Id = :dealer_Id ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( select b.branch_name as tenChiNhanh, d.district_name as tenQuanHuyen, rd.dealer_code as maDBH, rd.dealer_name as tenDBH, rd.address as diaChi, i.item_name as hangmucPS ")
                    .append("           , extract(day from ngay_ps) as ngay, extract(month from ngay_ps) as thang, extract(year from ngay_ps) as nam ")
                    .append("           , soLuongThucTe, soDuocTinhDiem, soDuocQuiDoi, soDiemQuiDoi, soTienQuiDoi, soMaDuThuong, da.exchange_money_status as trangThaiTraThuong ")
                    .append(mainQuery)
                    .append("   ) where 1 = 1 ");
        if(properties.get("trangThaiTraThuong") != null){
            sqlQueryClause.append(" and da.exchange_money_status = :trang_thai_tra_thuong ");
        }
        sqlQueryClause.append("  order by tenChiNhanh, tenQuanHuyen, tenDBH, ngay, thang, nam ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("trangThaiTraThuong") != null){
            query.setParameter("trang_thai_tra_thuong", properties.get("trangThaiTraThuong").toString());
        }
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            query.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(*) from ( select b.branch_name as tenChiNhanh, d.district_name as tenQuanHuyen, rd.dealer_code as maDBH, rd.dealer_name as tenDBH, rd.address as diaChi, i.item_name as hangmucPS ")
                            .append("                                                   , extract(day from da.cycle) as ngay, extract(month from da.cycle) as thang, extract(year from da.cycle) as nam ")
                            .append("                                                   , soLuongThucTe, soDuocTinhDiem, soDuocQuiDoi, soDiemQuiDoi, soTienQuiDoi, soMaDuThuong, da.exchange_money_status as trangThaiTraThuong ")
                            .append(mainQuery)
                            .append(" ) where 1 = 1 ");
        if(properties.get("trangThaiTraThuong") != null){
            sqlCountClause.append(" and da.exchange_money_status = :trang_thai_tra_thuong ");
        }
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("trangThaiTraThuong") != null){
            countQuery.setParameter("trang_thai_tra_thuong", properties.get("trangThaiTraThuong").toString());
        }
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            countQuery.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
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
    public Object[] baoCaoTienThuongMaDuThuongTheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from dealer_account_action@mkitdw_qlud2_dbl dac " )
                .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = dac.dealer_Id ")
                .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_Id = rd.branch_Id ")
                .append("   inner join district@mkitdw_qlud2_dbl d on d.district_Id = rd.district_Id ")
                .append("   inner join prom_items@mkitdw_qlud2_dbl i on i.item_Id = dac.item_Id ")
                .append("   where 1 = 1 and (i.item_Id between 1 and 7) ");
        if(properties.get("branch_Id") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append(" and d.district_Id = :district_Id ");
        }
        if(properties.get("item_Id") != null){
            mainQuery.append(" and i.item_Id = :item_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and dac.issue_Date >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and dac.issue_Date < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ")
                .append("                   select b.branch_Name, d.district_Name, i.item_Name ")
                .append("                           , extract(day from dac.issue_date) as ngay, extract(month from dac.issue_date) as thang, extract(year from dac.issue_date) as nam ")
                .append("                           , nvl(sum(dac.execute_amount), 0) as soLuongThucTe ")
                .append("                           , nvl(sum(dac.amount), 0) as soDuocTinhDiem ")
                .append("                           , nvl(sum(dac.exchange_amount), 0) as soDuocQuiDoi ")
                .append("                           , nvl(sum(dac.prom_point), 0) as soDiemQuiDoi ")
                .append("                           , nvl(sum(dac.prom_amount), 0) as soTienQuiDoi ")
                .append("                           , nvl(sum(dac.ticket_quantity), 0) as soMaDuThuong ")
                .append(mainQuery)
                .append("                   group by b.branch_Name, d.district_Name, i.item_Name, dac.issue_date ")
                .append("                   order by dac.issue_date ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            query.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(branch_Name) from ( ")
                                                            .append("                   select b.branch_Name, d.district_Name, i.item_Name ")
                                                            .append("                           , extract(day from dac.issue_date) as ngay, extract(month from dac.issue_date) as thang, extract(year from dac.issue_date) as nam ")
                                                            .append("                           , nvl(sum(dac.execute_amount), 0) as soLuongThucTe ")
                                                            .append("                           , nvl(sum(dac.amount), 0) as soDuocTinhDiem ")
                                                            .append("                           , nvl(sum(dac.exchange_amount), 0) as soDuocQuiDoi ")
                                                            .append("                           , nvl(sum(dac.prom_point), 0) as soDiemQuiDoi ")
                                                            .append("                           , nvl(sum(dac.prom_amount), 0) as soTienQuiDoi ")
                                                            .append("                           , nvl(sum(dac.ticket_quantity), 0) as soMaDuThuong ")
                                                            .append(mainQuery)
                                                            .append("                   group by b.branch_Name, d.district_Name, i.item_Name, dac.issue_date ")
                                                            .append("                   order by dac.issue_date ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            countQuery.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
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
    public Object[] baoCaoTienThuongMaDuThuongTheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from dealer_account_action@mkitdw_qlud2_dbl dac " )
                .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = dac.dealer_Id ")
                .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_Id = rd.branch_Id ")
                .append("   inner join prom_items@mkitdw_qlud2_dbl i on i.item_Id = dac.item_Id ")
                .append("   where 1 = 1 and (i.item_Id between 1 and 7) ");
        if(properties.get("branch_Id") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        if(properties.get("item_Id") != null){
            mainQuery.append(" and i.item_Id = :item_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append(" and dac.issue_Date >= to_date(substr(:fromDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append(" and dac.issue_Date < (to_date(substr(:toDateTime, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ")
                .append("                   select b.branch_Name, i.item_Name ")
                .append("                           , extract(day from dac.issue_date) as ngay, extract(month from dac.issue_date) as thang, extract(year from dac.issue_date) as nam ")
                .append("                           , nvl(sum(dac.execute_amount), 0) as soLuongThucTe ")
                .append("                           , nvl(sum(dac.amount), 0) as soDuocTinhDiem ")
                .append("                           , nvl(sum(dac.exchange_amount), 0) as soDuocQuiDoi ")
                .append("                           , nvl(sum(dac.prom_point), 0) as soDiemQuiDoi ")
                .append("                           , nvl(sum(dac.prom_amount), 0) as soTienQuiDoi ")
                .append("                           , nvl(sum(dac.ticket_quantity), 0) as soMaDuThuong ")
                .append(mainQuery)
                .append("                   group by b.branch_Name, i.item_Name, dac.issue_date ")
                .append("                   order by dac.issue_date ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            query.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(branch_Name) from ( ")
                                                                .append("                   select b.branch_Name, i.item_Name ")
                                                                .append("                           , extract(day from dac.issue_date) as ngay, extract(month from dac.issue_date) as thang, extract(year from dac.issue_date) as nam ")
                                                                .append("                           , nvl(sum(dac.execute_amount), 0) as soLuongThucTe ")
                                                                .append("                           , nvl(sum(dac.amount), 0) as soDuocTinhDiem ")
                                                                .append("                           , nvl(sum(dac.exchange_amount), 0) as soDuocQuiDoi ")
                                                                .append("                           , nvl(sum(dac.prom_point), 0) as soDiemQuiDoi ")
                                                                .append("                           , nvl(sum(dac.prom_amount), 0) as soTienQuiDoi ")
                                                                .append("                           , nvl(sum(dac.ticket_quantity), 0) as soMaDuThuong ")
                                                                .append(mainQuery)
                                                                .append("                   group by b.branch_Name, i.item_Name, dac.issue_date ")
                                                                .append("                   order by dac.issue_date ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            countQuery.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
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
    public Object[] xemChiTietHangMucPhatSinh4Item1(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from kpp_invoice@mkitdw_qlud2_dbl k, kpp_invoice_detail@mkitdw_qlud2_dbl kd, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("   where k.invoice_id = kd.invoice_id ")
                .append("           and k.invoice_date = kd.invoice_date ")
                .append("           and k.dealer_id = r.dealer_id ")
                .append("           and kd.good_group_type = '1' ")
                .append("           and r.dealer_id = :dealer_Id ");
        if(fromDateTime != null){
            mainQuery.append("      and trunc(k.invoice_date) >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTime != null){
            mainQuery.append("      and trunc(k.invoice_date) < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select r.dealer_code, r.dealer_name, k.invoice_no, k.invoice_date, kd.good_code,  kd.good_name, kd.quantity,  kd.amount ")
                    .append(mainQuery)
                    .append("   order by dealer_code, invoice_date, invoice_no, good_code ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            query.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            query.setParameter("toDateTime", toDateTime.toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder("select count(k.invoice_id) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            countQuery.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            countQuery.setParameter("toDateTime", toDateTime.toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] xemChiTietHangMucPhatSinh4Item24User(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from kpp_sms@mkitdw_qlud2_dbl k, dealer_properties@mkitdw_qlud2_dbl dp, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("   where k.calling_number = substr(dp.properties_value, 3) ")
                .append("           and dp.dealer_id = r.dealer_id ")
                .append("           and k.reply_type is not null ")
                .append("           and upper(k.code) = 'BHTT' ")
                .append("           and r.dealer_id = :dealer_Id ");
        if(fromDateTime != null){
            mainQuery.append("      and trunc(k.issue_dateTime) >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTime != null){
            mainQuery.append("      and trunc(k.issue_dateTime) < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("           order by k.issue_datetime ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select r.dealer_id, r.dealer_code, r.dealer_name, dp.properties_value, k.issue_datetime, receive_message sms_content ")
                    .append("           , (select name from ap_domain@mkitdw_qlud2_dbl where type = 'KPP_REPLY_TYPE' and code = k.reply_type) ket_qua ")
                    .append(mainQuery);
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTime != null){
            query.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            query.setParameter("toDateTime", toDateTime.toString());
        }
        query.setParameter("dealer_Id", dealer_Id);
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(r.dealer_id) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            countQuery.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            countQuery.setParameter("toDateTime", toDateTime.toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] xemChiTietHangMucPhatSinh4Item24Admin(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from kpp_sms@mkitdw_qlud2_dbl k, dealer_properties@mkitdw_qlud2_dbl dp, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("   where k.calling_number = substr(dp.properties_value, 3) ")
                .append("           and dp.dealer_id = r.dealer_id ")
                .append("           and k.reply_type is not null ")
                .append("           and upper(k.code) = 'BHTT' ")
                .append("           and r.dealer_id = :dealer_Id ");
        if(fromDateTime != null){
            mainQuery.append("      and trunc(k.issue_dateTime) >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTime != null){
            mainQuery.append("      and trunc(k.issue_dateTime) < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("           order by k.issue_datetime ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select r.dealer_id, r.dealer_code, r.dealer_name, dp.properties_value, k.issue_datetime, receive_message sms_content ")
                .append("           , (select name from ap_domain@mkitdw_qlud2_dbl where type = 'KPP_REPLY_TYPE' and code = k.reply_type) ket_qua ")
                .append(mainQuery);
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(fromDateTime != null){
            query.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            query.setParameter("toDateTime", toDateTime.toString());
        }
        query.setParameter("dealer_Id", dealer_Id);
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(r.dealer_id) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            countQuery.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            countQuery.setParameter("toDateTime", toDateTime.toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] xemChiTietHangMucPhatSinh4Item24AdminNew(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl c, retail_dealer@mkitdw_qlud2_dbl r, dealer_properties@mkitdw_qlud2_dbl dp ")
                .append("   where c.so_ez = substr(dp.properties_value,3) ")
                .append("           and dp.properties_code = 'EZ_ISDN' ")
                .append("           and r.dealer_id = dp.dealer_id ")
                .append("           and type_input = 'BHTT_NEW' ")
                .append("           and r.dealer_Id = :dealer_Id ");
        if(fromDateTime != null){
            mainQuery.append("      and trunc(c.ngay_ps) >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTime != null){
            mainQuery.append("      and trunc(c.ngay_ps) < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select r.dealer_code, r.dealer_name, r.ez_isdn, c.ngay_ps, c.so_khach_hang, c.active_dateTime ")
                .append(mainQuery);
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            query.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            query.setParameter("toDateTime", toDateTime.toString());
        }
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(r.dealer_Code) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            countQuery.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            countQuery.setParameter("toDateTime", toDateTime.toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] xemChiTietHangMucPhatSinh4Item3(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from ct_tich_diem_kpp_2014_psc@mkitdw_qlud2_dbl c, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("               , ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl s ")
                .append("   where c.dealer_code = r.dealer_code ")
                .append("           and r.dealer_id = :dealer_Id ")
                .append("           and c.thue_bao = s.so_khach_hang ")
                .append("           and s.type_input = 'BHTT_NEW' ")
                .append("           and c.amount > 0 ");
        if(fromDateTime != null){
            mainQuery.append("      and trunc(c.ngay_ps) >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTime != null){
            mainQuery.append("      and trunc(c.ngay_ps) < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select r.dealer_code, r.dealer_name, c.thue_bao, c.ngay_ps, c.amount cuoc_ps, c.exchange_point ")
                    .append("           ,s.insert_date as ngay_nhan_tin, s.active_dateTime as ngay_kich_hoat ")
                    .append(mainQuery)
                    .append("   order by c.thue_bao, c.ngay_ps ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            query.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            query.setParameter("toDateTime", toDateTime.toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(c.dealer_code) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            countQuery.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            countQuery.setParameter("toDateTime", toDateTime.toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] xemChiTietHangMucPhatSinh4Item4(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from kpp_invoice@mkitdw_qlud2_dbl k, kpp_invoice_detail@mkitdw_qlud2_dbl kd, retail_dealer@mkitdw_qlud2_dbl r")
                .append("   where k.invoice_id = kd.invoice_id ")
                .append("       and k.invoice_date = kd.invoice_date ")
                .append("       and k.dealer_id = r.dealer_id ")
                .append("       and kd.good_group_type != '1' ")
                .append("       and r.dealer_id = :dealer_Id ");
        if(fromDateTime != null){
            mainQuery.append("      and trunc(k.invoice_date) >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTime != null){
            mainQuery.append("      and trunc(k.invoice_date) < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select r.dealer_code, r.dealer_name, k.invoice_no, k.invoice_date, kd.good_code, kd.good_name, kd.quantity, kd.amount ")
                    .append(mainQuery)
                    .append("   order by r.dealer_code, k.invoice_date, k.invoice_no, kd.good_code ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        query.setFirstResult(firstItem);
        if(fromDateTime != null){
            query.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            query.setParameter("toDateTime", toDateTime.toString());
        }
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(k.invoice_id) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            countQuery.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            countQuery.setParameter("toDateTime", toDateTime.toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] xemChiTietHangMucPhatSinh4Item5(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from kpp_sms@mkitdw_qlud2_dbl k, dealer_properties@mkitdw_qlud2_dbl dp, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("   where k.calling_number = substr(dp.properties_value, 3) ")
                .append("           and dp.dealer_id = r.dealer_id ")
                .append("           and k.reply_type is not null ")
                .append("           and upper(k.code) = 'GTKH' ")
                .append("           and r.dealer_id = :dealer_Id ");
        if(fromDateTime != null){
            mainQuery.append("      and trunc(k.issue_dateTime) >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTime != null){
            mainQuery.append("      and trunc(k.issue_dateTime) < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("   order by k.issue_datetime ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select r.dealer_id, r.dealer_code, r.dealer_name, dp.properties_value, k.issue_datetime, receive_message sms_content ")
                    .append("           , (select name from ap_domain@mkitdw_qlud2_dbl where type = 'KPP_REPLY_TYPE' and code = k.reply_type) ket_qua ")
                    .append(mainQuery);
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            query.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            query.setParameter("toDateTime", toDateTime.toString());
        }
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(r.dealer_id) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            countQuery.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            countQuery.setParameter("toDateTime", toDateTime.toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] xemChiTietHangMucPhatSinh4Item6(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from kpp_vas_point_detail@mkitdw_qlud2_dbl c, retail_dealer@mkitdw_qlud2_dbl r, dealer_properties@mkitdw_qlud2_dbl dp ")
                .append("   where c.ez_isdn = dp.properties_value ")
                .append("           and dp.properties_code = 'EZ_ISDN' ")
                .append("           and r.dealer_id = dp.dealer_id ")
                .append("           and r.dealer_id = :dealer_Id ");
        if(fromDateTime != null){
            mainQuery.append("      and trunc(c.comm_date) >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTime != null){
            mainQuery.append("      and trunc(c.comm_date) < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("   order by c.ma_giao_dich ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select r.dealer_code, r.dealer_name, r.ez_isdn, c.ma_giao_dich, c.ma_goi, c.ten_goi, c.chu_ky, c.comm_date as ngay_ps ")
                    .append(mainQuery);
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            query.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            query.setParameter("toDateTime", toDateTime.toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(c.dealer_id) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            countQuery.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            countQuery.setParameter("toDateTime", toDateTime.toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] xemChiTietHangMucPhatSinh4Item7(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from kpp_sms@mkitdw_qlud2_dbl k, dealer_properties@mkitdw_qlud2_dbl dp, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("   where k.calling_number = substr(dp.properties_value, 3) ")
                .append("           and dp.dealer_id = r.dealer_id ")
                .append("           and k.reply_type is not null ")
                .append("           and upper(k.code) = 'TTTT' ")
                .append("           and r.dealer_id = :dealer_Id ");
        if(fromDateTime != null){
            mainQuery.append("      and trunc(k.issue_dateTime) >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTime != null){
            mainQuery.append("      and trunc(k.issue_dateTime) < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("           order by k.issue_datetime ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select r.dealer_id, r.dealer_code, r.dealer_name, dp.properties_value, k.issue_datetime, receive_message sms_content ")
                    .append("           , (select name from ap_domain@mkitdw_qlud2_dbl where type = 'KPP_REPLY_TYPE' and code = k.reply_type) ket_qua ")
                    .append(mainQuery);
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            query.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            query.setParameter("toDateTime", toDateTime.toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(r.dealer_id) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("dealer_Id", dealer_Id);
        if(fromDateTime != null){
            countQuery.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            countQuery.setParameter("toDateTime", toDateTime.toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] traCuuDanhSachTrungThuongKPP(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from ct_tich_diem_kpp_2014_ma_phieu@mkitdw_qlud2_dbl mp ")
                .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = mp.dealer_Id ")
                .append("   inner join kpp_gift@mkitdw_qlud2_dbl g on g.gif_Id = mp.exchange_gift_Id ")
                .append("   where 1 = 1 and mp.winning_status = '1' ");
        if(properties.get("dealer_Code") != null){
            mainQuery.append(" and upper(rd.dealer_Code) like upper(:dealer_Code) ");
        }
        if(properties.get("soEZ") != null){
            mainQuery.append(" and upper(rd.ez_Isdn) like upper(:soEZ) ");
        }
        if(properties.get("kppGift_Id") != null){
            mainQuery.append(" and mp.exchange_gift_Id = :kppGift_Id ");
        }

        StringBuilder sqlQueryClause = new StringBuilder(" select rd.dealer_Id, rd.dealer_code, rd.dealer_Name, mp.ma_phieu, mp.insert_dateTime, g.gif_Name, mp.exchange_gift_status, g.gif_Level  ")
                        .append(mainQuery)
                        .append(" order by mp.ma_phieu ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("dealer_Code") != null){
            query.setParameter("dealer_Code", "%" + properties.get("dealer_Code").toString() + "%");
        }
        if(properties.get("soEZ") != null){
            query.setParameter("soEZ", "%" + properties.get("soEZ").toString() + "%");
        }
        if(properties.get("kppGift_Id") != null){
            query.setParameter("kppGift_Id", Long.valueOf(properties.get("kppGift_Id").toString()));
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(mp.dealer_Id) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("dealer_Code") != null){
            countQuery.setParameter("dealer_Code", "%" + properties.get("dealer_Code").toString() + "%");
        }
        if(properties.get("soEZ") != null){
            countQuery.setParameter("soEZ", "%" + properties.get("soEZ").toString() + "%");
        }
        if(properties.get("kppGift_Id") != null){
            countQuery.setParameter("kppGift_Id", Long.valueOf(properties.get("kppGift_Id").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] findBranchById(Long branchId) throws ObjectNotFoundException{
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select branch_Id, branch_Code, branch_Name from Branch@mkitdw_qlud2_dbl where branch_Id = :branch_Id ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("branch_Id", branchId);
        List resultSet = query.getResultList();
        if(resultSet != null && resultSet.size() > 0){
            return (Object[])resultSet.get(0);
        }else{
            throw new ObjectNotFoundException("Can not find info for BranchId: " + branchId + ". Please mapping branch with chiNhanh again.");
        }
    }

    @Override
    public Object[] baoCaoHangHoaTheoDaiLy(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from (   select r.branch_id, r.district_id, r.dealer_id, r.dealer_code, r.dealer_name, case when item_id = 4 then amount else 0 end card_quantity, ")
                    .append("               case when item_id = 1 then amount else 0 end kit_quantity, ")
                    .append("               case when item_id = 2 then amount else 0 end bhtt_quantity, ")
                    .append("               0 bhtt_psc_25k, 0 psc_amount, ")
                    .append("               case when item_id = 5 then amount else 0 end gtkh_quantity, ")
                    .append("               0 gtkh_psc_25k, ")
                    .append("               case when item_id = 6 then amount else 0 end vas_quantity ")
                    .append("           from dealer_account_action@mkitdw_qlud2_dbl d, retail_dealer@mkitdw_qlud2_dbl r ")
                    .append("           where d.dealer_id = r.dealer_id ")
                    .append("                   and d.issue_date >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("')")
                    .append("                   and d.issue_date < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                    .append("           union all ")
                    .append("           select r.branch_id, r.district_id, r.dealer_id, r.dealer_code, r.dealer_name, 0 card_quantity, 0 kit_quantity, ")
                    .append("                   0 bhtt_quantity, 0 bhtt_psc_25k, 0 psc, 0 gtkh_quantity, 0 gtkh_psc_25k, 0 vas_quantity ")
                    .append("           from kpp_invoice@mkitdw_qlud2_dbl ki, kpp_invoice_detail@mkitdw_qlud2_dbl kid, retail_dealer@mkitdw_qlud2_dbl r ")
                    .append("           where ki.dealer_id = r.dealer_id ")
                    .append("                   and ki.invoice_id = kid.invoice_id ")
                    .append("                   and ki.invoice_date = kid.invoice_date ")
                    .append("                   and kid.good_group_type = '2' ")
                    .append("                   and ki.invoice_date >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') ")
                    .append("                   and ki.invoice_date < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                    .append("           union all ")
                    .append("           select r.branch_id, r.district_id, r.dealer_id, r.dealer_code, r.dealer_name, 0 card_quantity, 0 kit_quantity, 0 bhtt_quantity, ")
                    .append("                   case when c.type_input = 'BHTT_NEW' then 1 else 0 end ")
                    .append("                   bhtt_psc_25k, 0 psc, 0 gtkh_quantity, ")
                    .append("                   case when c.type_input = 'GTKH' THEN 1 ELSE 0 END gtkh_psc_25k, ")
                    .append("                   0 vas_quantity ")
                    .append("           from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl c, retail_dealer@mkitdw_qlud2_dbl r ")
                    .append("           where c.shop_code = r.dealer_code ")
                    .append("                   and charge_status = '1' ")
                    .append("                   and ngay_ps >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') ")
                    .append("                   and ngay_ps < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                    .append("           union all ")
                    .append("           select r.branch_id, r.district_id, r.dealer_id, r.dealer_code, r.dealer_name, 0 card_quantity, 0 kit_quantity, 0 bhtt_quantity, 0 bhtt_psc_25k, ")
                    .append("                   amount psc, 0 gtkh_quantity, 0 gtkh_psc_25k, 0 vas_quantity ")
                    .append("           from ct_tich_diem_kpp_2014_psc@mkitdw_qlud2_dbl k, retail_dealer@mkitdw_qlud2_dbl r ")
                    .append("           where k.dealer_code = r.dealer_code ")
                    .append("                   and k.ngay_ps >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') ")
                    .append("                   and k.ngay_ps < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                    .append("   ) m ")
                    .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_id = m.dealer_id ")
                    .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_id = m.branch_id ")
                    .append("   inner join district@mkitdw_qlud2_dbl d on d.district_id = m.district_id ")
                    .append("   where 1 = 1 ");

        if(properties.get("branch_Id") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append(" and d.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append(" and rd.dealer_Id = :dealer_Id ");
        }
        mainQuery.append("   group by b.branch_name, d.district_name, m.dealer_id, m.dealer_code, m.dealer_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select b.branch_name, d.district_name, m.dealer_id, m.dealer_code, m.dealer_name, ")
                .append("           sum (m.card_quantity) card_quantity, ")
                .append("           sum (m.kit_quantity) kit_quantity, ")
                .append("           sum (m.bhtt_quantity) bhtt_quantity, ")
                .append("           sum (m.bhtt_psc_25k) bhtt_psc_25k, ")
                .append("           sum (m.psc_amount) psc_amount, ")
                .append("           sum (m.gtkh_quantity) gtkh_quantity, ")
                .append("           sum (m.gtkh_psc_25k) gtkh_psc_25k, ")
                .append("           sum (m.vas_quantity) vas_quantity ")
                .append(mainQuery)
                .append("       order by b.branch_name, d.district_name, m.dealer_name ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("fromDateTime") != null){
            query.setParameter("p_from_date", properties.get("fromDateTime").toString());
        }else{
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(2014, 0, 1);
            Timestamp fromDateTime1 = new Timestamp(fromCalendar.getTimeInMillis());
            query.setParameter("p_from_date", fromDateTime1.toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("p_to_date", properties.get("toDateTime").toString());
        }else{
            Timestamp toDateTime1 = new Timestamp(System.currentTimeMillis());
            query.setParameter("p_to_date", toDateTime1.toString());
        }
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(*) from ( ")
                    .append("       select b.branch_name, d.district_name, m.dealer_id, m.dealer_code, m.dealer_name, ")
                    .append("           sum (m.card_quantity) card_quantity, ")
                    .append("           sum (m.kit_quantity) kit_quantity, ")
                    .append("           sum (m.bhtt_quantity) bhtt_quantity, ")
                    .append("           sum (m.bhtt_psc_25k) bhtt_psc_25k, ")
                    .append("           sum (m.psc_amount) psc_amount, ")
                    .append("           sum (m.gtkh_quantity) gtkh_quantity, ")
                    .append("           sum (m.gtkh_psc_25k) gtkh_psc_25k, ")
                    .append("           sum (m.vas_quantity) vas_quantity ")
                    .append(mainQuery)
                    .append("   ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("p_from_date", properties.get("fromDateTime").toString());
        }else{
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(2014, 0, 1);
            Timestamp fromDateTime1 = new Timestamp(fromCalendar.getTimeInMillis());
            countQuery.setParameter("p_from_date", fromDateTime1.toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("p_to_date", properties.get("toDateTime").toString());
        }else{
            Timestamp toDateTime1 = new Timestamp(System.currentTimeMillis());
            countQuery.setParameter("p_to_date", toDateTime1.toString());
        }
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoHangHoaTheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from (   select r.branch_id, r.district_id, case when item_id = 4 then amount else 0 end card_quantity, ")
                .append("               case when item_id = 1 then amount else 0 end kit_quantity, ")
                .append("               case when item_id = 2 then amount else 0 end bhtt_quantity, ")
                .append("               0 bhtt_psc_25k, 0 psc_amount, ")
                .append("               case when item_id = 5 then amount else 0 end gtkh_quantity, ")
                .append("               0 gtkh_psc_25k, ")
                .append("               case when item_id = 6 then amount else 0 end vas_quantity ")
                .append("           from dealer_account_action@mkitdw_qlud2_dbl d, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where d.dealer_id = r.dealer_id ")
                .append("                   and d.issue_date >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("')")
                .append("                   and d.issue_date < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                .append("           union all ")
                .append("           select r.branch_id, r.district_id, 0 card_quantity, 0 kit_quantity, ")
                .append("                   0 bhtt_quantity, 0 bhtt_psc_25k, 0 psc, 0 gtkh_quantity, 0 gtkh_psc_25k, 0 vas_quantity ")
                .append("           from kpp_invoice@mkitdw_qlud2_dbl ki, kpp_invoice_detail@mkitdw_qlud2_dbl kid, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where ki.dealer_id = r.dealer_id ")
                .append("                   and ki.invoice_id = kid.invoice_id ")
                .append("                   and ki.invoice_date = kid.invoice_date ")
                .append("                   and kid.good_group_type = '2' ")
                .append("                   and ki.invoice_date >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') ")
                .append("                   and ki.invoice_date < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                .append("           union all ")
                .append("           select r.branch_id, r.district_id, 0 card_quantity, 0 kit_quantity, 0 bhtt_quantity, ")
                .append("                   case when c.type_input = 'BHTT_NEW' then 1 else 0 end ")
                .append("                   bhtt_psc_25k, 0 psc, 0 gtkh_quantity, ")
                .append("                   case when c.type_input = 'GTKH' THEN 1 ELSE 0 END gtkh_psc_25k, ")
                .append("                   0 vas_quantity ")
                .append("           from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl c, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where c.shop_code = r.dealer_code ")
                .append("                   and charge_status = '1' ")
                .append("                   and ngay_ps >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') ")
                .append("                   and ngay_ps < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                .append("           union all ")
                .append("           select r.branch_id, r.district_id, 0 card_quantity, 0 kit_quantity, 0 bhtt_quantity, 0 bhtt_psc_25k, ")
                .append("                   amount psc, 0 gtkh_quantity, 0 gtkh_psc_25k, 0 vas_quantity ")
                .append("           from ct_tich_diem_kpp_2014_psc@mkitdw_qlud2_dbl k, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where k.dealer_code = r.dealer_code ")
                .append("                   and k.ngay_ps >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') ")
                .append("                   and k.ngay_ps < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                .append("   ) m ")
                .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_id = m.branch_id ")
                .append("   inner join district@mkitdw_qlud2_dbl d on d.district_id = m.district_id ")
                .append("   where 1 = 1 ");

        if(properties.get("branch_Id") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append(" and d.district_Id = :district_Id ");
        }
        mainQuery.append("   group by b.branch_name, d.district_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select b.branch_name, d.district_name, ")
                .append("           sum (m.card_quantity) card_quantity, ")
                .append("           sum (m.kit_quantity) kit_quantity, ")
                .append("           sum (m.bhtt_quantity) bhtt_quantity, ")
                .append("           sum (m.bhtt_psc_25k) bhtt_psc_25k, ")
                .append("           sum (m.psc_amount) psc_amount, ")
                .append("           sum (m.gtkh_quantity) gtkh_quantity, ")
                .append("           sum (m.gtkh_psc_25k) gtkh_psc_25k, ")
                .append("           sum (m.vas_quantity) vas_quantity ")
                .append(mainQuery)
                .append("       order by b.branch_name, d.district_name ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("fromDateTime") != null){
            query.setParameter("p_from_date", properties.get("fromDateTime").toString());
        }else{
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(2014, 0, 1);
            Timestamp fromDateTime1 = new Timestamp(fromCalendar.getTimeInMillis());
            query.setParameter("p_from_date", fromDateTime1.toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("p_to_date", properties.get("toDateTime").toString());
        }else{
            Timestamp toDateTime1 = new Timestamp(System.currentTimeMillis());
            query.setParameter("p_to_date", toDateTime1.toString());
        }
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);

        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(*) from ( ")
                    .append("       select b.branch_name, d.district_name, ")
                    .append("           sum (m.card_quantity) card_quantity, ")
                    .append("           sum (m.kit_quantity) kit_quantity, ")
                    .append("           sum (m.bhtt_quantity) bhtt_quantity, ")
                    .append("           sum (m.bhtt_psc_25k) bhtt_psc_25k, ")
                    .append("           sum (m.psc_amount) psc_amount, ")
                    .append("           sum (m.gtkh_quantity) gtkh_quantity, ")
                    .append("           sum (m.gtkh_psc_25k) gtkh_psc_25k, ")
                    .append("           sum (m.vas_quantity) vas_quantity ")
                    .append(mainQuery)
                    .append("       ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("p_from_date", properties.get("fromDateTime").toString());
        }else{
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(2014, 0, 1);
            Timestamp fromDateTime1 = new Timestamp(fromCalendar.getTimeInMillis());
            countQuery.setParameter("p_from_date", fromDateTime1.toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("p_to_date", properties.get("toDateTime").toString());
        }else{
            Timestamp toDateTime1 = new Timestamp(System.currentTimeMillis());
            countQuery.setParameter("p_to_date", toDateTime1.toString());
        }
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoHangHoaTheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from (   select r.branch_id, case when item_id = 4 then amount else 0 end card_quantity, ")
                .append("               case when item_id = 1 then amount else 0 end kit_quantity, ")
                .append("               case when item_id = 2 then amount else 0 end bhtt_quantity, ")
                .append("               0 bhtt_psc_25k, 0 psc_amount, ")
                .append("               case when item_id = 5 then amount else 0 end gtkh_quantity, ")
                .append("               0 gtkh_psc_25k, ")
                .append("               case when item_id = 6 then amount else 0 end vas_quantity ")
                .append("           from dealer_account_action@mkitdw_qlud2_dbl d, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where d.dealer_id = r.dealer_id ")
                .append("                   and d.issue_date >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("')")
                .append("                   and d.issue_date < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                .append("           union all ")
                .append("           select r.branch_id, 0 card_quantity, 0 kit_quantity, ")
                .append("                   0 bhtt_quantity, 0 bhtt_psc_25k, 0 psc, 0 gtkh_quantity, 0 gtkh_psc_25k, 0 vas_quantity ")
                .append("           from kpp_invoice@mkitdw_qlud2_dbl ki, kpp_invoice_detail@mkitdw_qlud2_dbl kid, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where ki.dealer_id = r.dealer_id ")
                .append("                   and ki.invoice_id = kid.invoice_id ")
                .append("                   and ki.invoice_date = kid.invoice_date ")
                .append("                   and kid.good_group_type = '2' ")
                .append("                   and ki.invoice_date >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') ")
                .append("                   and ki.invoice_date < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                .append("           union all ")
                .append("           select r.branch_id, 0 card_quantity, 0 kit_quantity, 0 bhtt_quantity, ")
                .append("                   case when c.type_input = 'BHTT_NEW' then 1 else 0 end ")
                .append("                   bhtt_psc_25k, 0 psc, 0 gtkh_quantity, ")
                .append("                   case when c.type_input = 'GTKH' THEN 1 ELSE 0 END gtkh_psc_25k, ")
                .append("                   0 vas_quantity ")
                .append("           from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl c, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where c.shop_code = r.dealer_code ")
                .append("                   and charge_status = '1' ")
                .append("                   and ngay_ps >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') ")
                .append("                   and ngay_ps < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                .append("           union all ")
                .append("           select r.branch_id, 0 card_quantity, 0 kit_quantity, 0 bhtt_quantity, 0 bhtt_psc_25k, ")
                .append("                   amount psc, 0 gtkh_quantity, 0 gtkh_psc_25k, 0 vas_quantity ")
                .append("           from ct_tich_diem_kpp_2014_psc@mkitdw_qlud2_dbl k, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where k.dealer_code = r.dealer_code ")
                .append("                   and k.ngay_ps >= to_date(substr(:p_from_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') ")
                .append("                   and k.ngay_ps < (to_date(substr(:p_to_date,1,10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ")
                .append("   ) m ")
                .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_id = m.branch_id ")
                .append("   where 1 = 1 ");

        if(properties.get("branch_Id") != null){
            mainQuery.append(" and b.branch_Id = :branch_Id ");
        }
        mainQuery.append("   group by b.branch_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select b.branch_name, ")
                .append("           sum (m.card_quantity) card_quantity, ")
                .append("           sum (m.kit_quantity) kit_quantity, ")
                .append("           sum (m.bhtt_quantity) bhtt_quantity, ")
                .append("           sum (m.bhtt_psc_25k) bhtt_psc_25k, ")
                .append("           sum (m.psc_amount) psc_amount, ")
                .append("           sum (m.gtkh_quantity) gtkh_quantity, ")
                .append("           sum (m.gtkh_psc_25k) gtkh_psc_25k, ")
                .append("           sum (m.vas_quantity) vas_quantity ")
                .append(mainQuery)
                .append("       order by b.branch_name ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("fromDateTime") != null){
            query.setParameter("p_from_date", properties.get("fromDateTime").toString());
        }else{
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(2014, 0, 1);
            Timestamp fromDateTime1 = new Timestamp(fromCalendar.getTimeInMillis());
            query.setParameter("p_from_date", fromDateTime1.toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("p_to_date", properties.get("toDateTime").toString());
        }else{
            Timestamp toDateTime1 = new Timestamp(System.currentTimeMillis());
            query.setParameter("p_to_date", toDateTime1.toString());
        }
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(*) from ( ")
                    .append("       select b.branch_name, ")
                    .append("           sum (m.card_quantity) card_quantity, ")
                    .append("           sum (m.kit_quantity) kit_quantity, ")
                    .append("           sum (m.bhtt_quantity) bhtt_quantity, ")
                    .append("           sum (m.bhtt_psc_25k) bhtt_psc_25k, ")
                    .append("           sum (m.psc_amount) psc_amount, ")
                    .append("           sum (m.gtkh_quantity) gtkh_quantity, ")
                    .append("           sum (m.gtkh_psc_25k) gtkh_psc_25k, ")
                    .append("           sum (m.vas_quantity) vas_quantity ")
                    .append(mainQuery)
                    .append("   ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("fromDateTime") != null){
            countQuery.setParameter("p_from_date", properties.get("fromDateTime").toString());
        }else{
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(2014, 0, 1);
            Timestamp fromDateTime1 = new Timestamp(fromCalendar.getTimeInMillis());
            countQuery.setParameter("p_from_date", fromDateTime1.toString());
        }
        if(properties.get("toDateTime") != null){
            countQuery.setParameter("p_to_date", properties.get("toDateTime").toString());
        }else{
            Timestamp toDateTime1 = new Timestamp(System.currentTimeMillis());
            countQuery.setParameter("p_to_date", toDateTime1.toString());
        }
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public List findRetailDealerListByBranchId(Long branchId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select rd.dealer_Id, rd.dealer_Code, rd.dealer_Name ")
                    .append("   from retail_dealer@mkitdw_qlud2_dbl rd ")
                    .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_Id = rd.branch_Id ")
                    .append("   where b.branch_Id = :branch_Id ")
                    .append("   order by rd.dealer_Code ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("branch_Id", branchId);
        return query.getResultList();
    }

    public List baoCaoDanhGiaKetQuaTHCT_tieuChi1(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia){
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select distinct a.input_type ")
                    .append("           , (select count(b.thue_bao) from ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl b where b.input_type = a.input_type) as slThamGia ")
                    .append("   from ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl a ")
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
        return query.getResultList();
    }

    @Override
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi2(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select distinct a.input_type ")
                    .append("          , sum((select count(b.isdn) from ct_tich_diem_2014_vlr@mkitdw_qlud2_dbl b where b.isdn = a.thue_bao and b.vlr_type = '1')) as slThamGiaTrenVLR ")
                    .append("   from ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl a ")
                    .append("   where a.trang_thai = '1' ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append("        and a.ngay_dang_ky >= to_date(substr(:fromDateTimeThamGia,1,10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia != null){
            sqlQueryClause.append("        and a.ngay_dang_ky < (to_date(substr(:toDateTimeThamGia,1,10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append("   group by a.input_type ");
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
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi3And4Summary(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select  c.phan_loai, a.input_type, sum(b.total_credit) as total_credit, sum(b.total_bonus) as total_bonus ")
                .append("   from ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl a, ct_tich_diem_2014_dttd@mkitdw_qlud2_dbl b, ct_tich_diem_2014_dm_item@mkitdw_qlud2_dbl c ")
                .append("   where a.thue_bao = b.calling_number ")
                .append("       and b.item_id = c.item_id ")
                .append("       and c.phan_loai in ('t', 's', 'd', 'v', 'r', 'k') ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append(" and a.insert_date >= to_date(substr(:fromDateTimeThamGia, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia!= null){
            sqlQueryClause.append(" and a.insert_date < (to_date(substr(:toDateTimeThamGia, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append(" and b.issue_date >= to_date(substr('2014-09-01',1,10), 'YYYY-MM-DD') ")
                .append("       and b.issue_date < (to_date(substr('2014-09-30',1,10), 'YYYY-MM-DD') + 1) ")
                .append("    group by c.phan_loai, a.input_type ")
                .append("    order by c.phan_loai, a.input_type ");

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
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi3And4TheoNgay(Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select b.issue_date ")
                    .append("           , c.phan_loai ")
                    .append("           , a.input_type ")
                    .append("           , sum(b.total_credit) dt_tkc_t9 ")
                    .append("           , sum(b.total_bonus)  dt_tkt_t9 ")
                    .append("    from  ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl a ")
                    .append("          , ct_tich_diem_2014_dttd@mkitdw_qlud2_dbl b ")
                    .append("          , ct_tich_diem_2014_dm_item@mkitdw_qlud2_dbl c ");
        if(branchId != null){
            sqlQueryClause.append("    , branch@mkitdw_qlud2_dbl d ");
        }
        if(districtId != null){
            sqlQueryClause.append("    , district@mkitdw_qlud2_dbl e ");
        }
        sqlQueryClause.append("  where 1 = 1 ")
                .append("           and a.thue_bao = b.calling_number ")
                .append("           and b.item_id = c.item_id ");
        if (branchId != null){
            sqlQueryClause.append(" and e.branch_id = d.branch_Id ")
                        .append("   and d.branch_Id = :branch_Id ");
        }
        if(districtId != null){
            sqlQueryClause.append(" and b.district_id = e.dttd_id ")
                        .append("   and e.district_Id = :dttd_Id ");
        }
        sqlQueryClause.append("     and c.phan_loai in ('t', 's', 'd', 'v', 'r', 'k') ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append(" and a.insert_date >= to_date(substr(:fromDateTimeThamGia, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia!= null){
            sqlQueryClause.append(" and a.insert_date < (to_date(substr(:toDateTimeThamGia, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append("     and b.issue_date >= to_date(substr('2014-09-01',1,10), 'YYYY-MM-DD') ")
                        .append("   and b.issue_date < (to_date(substr('2014-09-30',1,10), 'YYYY-MM-DD') + 1) ")
                        .append(" group by c.phan_loai, b.issue_date, a.input_type ")
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
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi5And6Summary(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select  c.phan_loai, a.input_type, sum(b.total_credit) as total_credit, sum(b.total_bonus) as total_bonus ")
                .append("   from ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl a, ct_tich_diem_2014_dttd@mkitdw_qlud2_dbl b, ct_tich_diem_2014_dm_item@mkitdw_qlud2_dbl c ")
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
        sqlQueryClause.append(" group by c.phan_loai, a.input_type ")
                    .append("   order by c.phan_loai, a.input_type ");

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
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi5And6TheoNgay(Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select b.issue_date ")
                .append("           , c.phan_loai ")
                .append("           , a.input_type ")
                .append("           , sum(b.total_credit) dt_tkc ")
                .append("           , sum(b.total_bonus)  dt_tkt ")
                .append("    from  ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl a ")
                .append("          , ct_tich_diem_2014_dttd@mkitdw_qlud2_dbl b ")
                .append("          , ct_tich_diem_2014_dm_item@mkitdw_qlud2_dbl c ");
        if(branchId != null){
            sqlQueryClause.append("    , branch@mkitdw_qlud2_dbl d ");
        }
        if(districtId != null){
            sqlQueryClause.append("    , district@mkitdw_qlud2_dbl e ");
        }
        sqlQueryClause.append("  where 1 = 1 ")
                .append("           and a.thue_bao = b.calling_number ")
                .append("           and b.item_id = c.item_id ");
        if (branchId != null){
            sqlQueryClause.append(" and e.branch_id = d.branch_Id ")
                    .append("   and d.branch_Id = :branch_Id ");
        }
        if(districtId != null){
            sqlQueryClause.append(" and b.district_id = e.dttd_id ")
                    .append("   and e.district_Id = :dttd_Id ");
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
        sqlQueryClause.append(" group by c.phan_loai, b.issue_date, a.input_type ")
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
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi7(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select phan_loai, input_type, count(1) ")
                    .append("   from ( select a.thue_bao, ")
                    .append("               c.input_type, ")
                    .append("               (floor(a.so_diem / 100) + count(b.ma_phieu)) so_ma, ")
                    .append("               decode(floor(a.so_diem / 100) + count(b.ma_phieu), 1, 1, 2, 2, 3, 3, 4) phan_loai ")
                    .append("           from ct_tich_diem_2014_diem@mkitdw_qlud2_dbl a ")
                    .append("   inner join ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl c on ( ")
                    .append("           a.thue_bao = c.thue_bao ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append("        and trunc(c.insert_date) >= to_date(substr(:fromDateTimeThamGia,1,10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia != null){
            sqlQueryClause.append("        and trunc(c.insert_date) < (to_date(substr(:toDateTimeThamGia,1,10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append("         ) ")
                    .append("   left join ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl b ")
                    .append("       on (a.thue_bao = b.thue_bao) ")
                    .append("   group by a.thue_bao, a.so_diem, c.input_type ")
                    .append("   ) ")
                    .append("   where so_ma > 0 ")
                    .append("   group by phan_loai, input_type ");

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
        sqlQueryClause.append(" select phan_loai, input_type, sum(so_ma) ")
                    .append("   from ")
                    .append("       (select a.thue_bao, ")
                    .append("               c.input_type, ")
                    .append("               count(a.ma_phieu) so_ma, ")
                    .append("               decode(count(a.ma_phieu), 1, 1, 2, 2, 3, 3, 4) phan_loai ")
                    .append("        from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl a ")
                    .append("        inner join ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl c ")
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
                    .append("        group by a.thue_bao, c.input_type ")
                    .append("       ) ")
                    .append("   where so_ma > 0 ")
                    .append("   group by phan_loai, input_type ");

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
    public Object[] traCuuPhatSinhTheoNgay4KPPNew(Integer thangTichDiem, Long item_Id, String thue_bao, Timestamp fromDate, Timestamp toDate, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        thue_bao = thue_bao.replace(Constants.PREFIX_KPP_USERNAME, "");
        thue_bao = CommonUtil.removeCountryCode(thue_bao);
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" from prom_items@mkitdw_qlud2_dbl i ")
                .append("  where 1 = 1 and i.item_Id in (1,2,3,4,5,6,7) ");
        if(item_Id != null){
            mainQuery.append(" and i.item_Id = :item_Id ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select i.item_Id, i.item_Name ")
                .append("           , (select nvl(sum(dac1.prom_point), 0) ")
                .append("               from dealer_account_action@mkitdw_qlud2_dbl dac1 ")
                .append("               inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = dac1.dealer_Id ")
                .append("               where dac1.item_Id = i.item_id ")
                .append("                       and exists(select 1 from dealer_properties@mkitdw_qlud2_dbl dp where dp.dealer_Id = rd.dealer_Id and dp.properties_value like :thue_bao) ");
        if(thangTichDiem != null){
            sqlQueryClause.append("                 and extract(month from dac1.issue_date) = :thang_tich_diem ");
        }
        if(fromDate != null){
            sqlQueryClause.append("                 and dac1.issue_date >= to_date(substr(:fromDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(toDate != null){
            sqlQueryClause.append("                 and dac1.issue_date < (to_date(substr(:toDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("')) + 1 ");
        }
        sqlQueryClause.append("         ) as diemTichLuy ")
                    .append("           ,(  select nvl(sum(dac1.prom_amount), 0) ")
                    .append("               from dealer_account_action@mkitdw_qlud2_dbl dac1 ")
                    .append("               inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = dac1.dealer_Id ")
                    .append("               where dac1.item_Id = i.item_id ")
                    .append("                       and extract(month from dac1.issue_date) = :thang_tich_diem ")
                    .append("                       and exists(select 1 from dealer_properties@mkitdw_qlud2_dbl dp where dp.dealer_Id = rd.dealer_Id and dp.properties_value like :thue_bao) ");
        if(fromDate != null){
            sqlQueryClause.append("                 and dac1.issue_date >= to_date(substr(:fromDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(toDate != null){
            sqlQueryClause.append("                 and dac1.issue_date < (to_date(substr(:toDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        sqlQueryClause.append("           ) as soTienTuongUng ")
                    .append("           , ( select nvl(sum(dac1.ticket_quantity), 0) ")
                    .append("               from dealer_account_action@mkitdw_qlud2_dbl dac1 ")
                    .append("               where dac1.dealer_Id = (select dp.dealer_Id from dealer_properties@mkitdw_qlud2_dbl dp where dp.properties_value like :thue_bao) ")
                    .append("                       and extract(month from dac1.issue_date) = :thang_tich_diem ")
                    .append("                       and dac1.item_id = i.item_Id ");
        if(fromDate != null){
            sqlQueryClause.append("                 and dac1.issue_date >= to_date(substr(:fromDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(toDate != null){
            sqlQueryClause.append("                 and dac1.issue_date < (to_date(substr(:toDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        sqlQueryClause.append("         ) as SoMaDuThuong ")
                    .append("           , ").append(thangTichDiem).append(" as cycle ")
                    .append(mainQuery)
                    .append(" order by i.item_code ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + thue_bao + "%");
        if(item_Id != null){
            query.setParameter("item_Id", item_Id);
        }
        if(thangTichDiem != null){
            query.setParameter("thang_tich_diem", thangTichDiem);
        }
        if(fromDate != null){
            query.setParameter("fromDate", fromDate.toString());
        }
        if(toDate != null){
            query.setParameter("toDate", toDate.toString());
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List resultSet = query.getResultList();

//        StringBuilder sqlCountClause = new StringBuilder(" select count(i.item_Id) ").append(mainQuery);
//        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
//        if(item_Id != null){
//            countQuery.setParameter("item_Id", item_Id);
//        }
//        if(thangTichDiem != null){
//            countQuery.setParameter("thangTichDiem", thangTichDiem);
//        }
//        if(StringUtils.isNotBlank(thue_bao)){
//            countQuery.setParameter("thue_bao", "%" + thue_bao + "%");
//        }
//        if(fromDate != null){
//            countQuery.setParameter("fromDate", fromDate.toString());
//        }
//        if(toDate != null){
//            countQuery.setParameter("fromDate", toDate.toString());
//        }
//        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{resultSet.size(), resultSet};
    }

    @Override
    public List traCuuPhatSinhDiemTheoThangKPP(String thue_bao) {
        thue_bao = thue_bao.replace(Constants.PREFIX_KPP_USERNAME, "");
        thue_bao = CommonUtil.removeCountryCode(thue_bao);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select extract (month from dac.cycle) ")
                    .append("           , nvl(sum(dac.prom_point), 0) as diemTichLuy ")
                    .append("           , nvl(sum(dac.prom_amount), 0) as soTienTuongUng ")
                    .append("           , nvl(sum(dac.ticket_quantity), 0) as soMaDuThuong ")
                    .append("           , dac.cycle_lock_status ")
                    .append("           , dac.condition_status ")
                    .append("           , dac.dealer_Id ")
                    .append("   from kpp_dealer_account@mkitdw_qlud2_dbl dac ")
                    .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_id = dac.dealer_id ")
                    .append("   where exists(select 1 from dealer_properties@mkitdw_qlud2_dbl dp where dp.dealer_Id = rd.dealer_Id and dp.properties_value like :thue_bao) ")
                    .append("   group by extract (month from dac.cycle) ")
                    .append("           , dac.cycle_lock_status, dac.condition_status, dac.dealer_Id  ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + thue_bao + "%");
        return query.getResultList();
    }

    @Override
    public Object[] findRetailDealerByEZ(String ez) throws ObjectNotFoundException{
        ez = ez.replace(Constants.PREFIX_KPP_USERNAME, "");
        ez = CommonUtil.removeCountryCode(ez);
       StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select rd.dealer_Id, rd.dealer_code, rd.dealer_name  ")
                .append("       from retail_dealer@mkitdw_qlud2_dbl rd ")
                .append("       inner join dealer_properties@mkitdw_qlud2_dbl dp on dp.dealer_Id = rd.dealer_Id ")
                .append("       where dp.properties_value like :thue_bao ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao","%" + ez + "%");
        List resultSet = query.getResultList();
        if(resultSet != null && resultSet.size() > 0){
            return (Object[])resultSet.get(0);
        }else{
            throw new ObjectNotFoundException("Can not find retailDealer for thueBao: " + ez);
        }
    }

    @Override
    public List findAllKppGifts() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select gif_Id, gif_Name, gif_Level, gif_Quantity, gif_Value ")
                    .append("   from kpp_Gift@mkitdw_qlud2_dbl ")
                    .append("   order by gif_Name desc ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        return query.getResultList();
    }

    @Override
    public Object[] traCuuChiTietTrangThaiKy(Long dealer_Id, Integer cycle) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from kpp_dealer_account@mkitdw_qlud2_dbl da ")
                .append("   where da.dealer_Id = :dealer_Id ")
                .append("           and extract(month from da.cycle) = :cycle ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select kit_sales_condition_status, sms_sales_condition_status, sub_charge_condition_status ")
                    .append("           , sub_intro_condition_status, vas_sales_condition_status, market_info_condition_status ")
                    .append(mainQuery);
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_Id);
        query.setParameter("cycle", cycle);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(da.dealer_Id) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("dealer_Id", dealer_Id);
        countQuery.setParameter("cycle", cycle);
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};

    }

    @Override
    public List findAllRetailDealers() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select dealer_Id, dealer_code, dealer_name from retail_dealer@mkitdw_qlud2_dbl order by dealer_name ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        return query.getResultList();
    }

    @Override
    public List findAllDistricts() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select district_Id, district_code, district_name from district@mkitdw_qlud2_dbl order by district_name ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        return query.getResultList();
    }

    @Override
    public List findRetailDealerListByDistrictId(Long district_Id) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select dealer_Id, dealer_code, dealer_name from retail_dealer@mkitdw_qlud2_dbl where district_id = :district_Id order by dealer_name ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("district_Id", district_Id);
        return query.getResultList();
    }

    @Override
    public Object[] searchKPPInformationByProperties(Map<String, Object> properties, Integer firstItems, Integer maxPageItems, String sortExpression, String sortDirection) {
        String soEZ = null;
        if(properties.get("soEZ") != null){
            soEZ = properties.get("soEZ").toString();
            soEZ = soEZ.replace(Constants.PREFIX_KPP_USERNAME, "");
            soEZ = CommonUtil.removeCountryCode(soEZ);
        }
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from prom_items@mkitdw_qlud2_dbl i ")
                .append("   inner join ( ")
                .append("           select item_Id, dealer_Id, cycle as cycle, nvl(sum(prom_point), 0) as prom_point ")
                .append("                       , nvl(sum(prom_amount), 0) as prom_amount ")
                .append("                       , nvl(sum(ticket_quantity), 0) as ticket_quantity ")
                .append("           from ( ")
                .append("               select dac1.item_Id, dac1.dealer_Id, extract(month from dac1.issue_date) as cycle, nvl(sum(dac1.prom_point), 0) as prom_point ")
                .append("                               , nvl(sum(dac1.prom_amount), 0) as prom_amount ")
                .append("                               , nvl(sum(dac1.ticket_quantity), 0) as ticket_quantity ")
                .append("               from dealer_account_action@mkitdw_qlud2_dbl dac1 ")
                .append("               inner join retail_dealer@mkitdw_qlud2_dbl rd ")
                .append("                   on rd.dealer_Id = dac1.dealer_Id ")
                .append("               where 1 = 1 ");
        if(properties.get("dealer_Id") != null){
            mainQuery.append("              and dac1.dealer_Id = :dealer_Id ");
        }
        if(StringUtils.isNotBlank(soEZ)){
            mainQuery.append("              and exists(select 1 from dealer_properties@mkitdw_qlud2_dbl dp where dp.dealer_Id = rd.dealer_Id and dp.properties_value like :soEZ) ");
        }
        mainQuery.append("                  and dac1.prom_point > 0 ");
        if(properties.get("fromDate") != null){
            mainQuery.append("              and dac1.issue_date >= to_date(substr(:fromDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("toDate") != null){
            mainQuery.append("              and dac1.issue_date < (to_date(substr(:toDate, 1, 10), '").append(Constants.DB_DATE_FORMAT).append("') + 1) ");
        }
        mainQuery.append("              group by dac1.item_Id, dac1.dealer_Id, dac1.issue_date ")
                .append("           ) ")
                .append("       group by item_Id, dealer_Id, cycle ")
                .append("   ) m on m.item_Id = i.item_Id ")
                .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = m.dealer_Id ")
                .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_Id = rd.branch_Id ")
                .append("   inner join district@mkitdw_qlud2_dbl d on d.district_Id = rd.district_Id ")
                .append("   where i.item_Id in (1,2,3,4,5,6,7) ");
        if(properties.get("item_Id") != null){
            mainQuery.append(" and i.item_Id = :item_Id ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select i.item_id, i.item_Name, b.branch_Name, d.district_Name, rd.dealer_Id, rd.dealer_Code, rd.dealer_Name, m.cycle, m.prom_point, m.prom_amount, m.ticket_quantity ")
                    .append(mainQuery)
                    .append("   order by i.item_code ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(StringUtils.isNotBlank(soEZ)){
            query.setParameter("soEZ", "%" + soEZ + "%");
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            query.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
        }
        if(properties.get("fromDate") != null){
            query.setParameter("fromDate", properties.get("fromDate").toString());
        }
        if(properties.get("toDate") != null){
            query.setParameter("toDate", properties.get("toDate").toString());
        }
        query.setFirstResult(firstItems);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(i.item_Id) ")
                    .append(mainQuery);

        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(StringUtils.isNotBlank(soEZ)){
            countQuery.setParameter("soEZ", "%" + soEZ + "%");
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("item_Id") != null){
            countQuery.setParameter("item_Id", Long.valueOf(properties.get("item_Id").toString()));
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
    public Object[] findRetailDealerInfoById(Long dealerId) throws ObjectNotFoundException {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select dealer_Id, dealer_Code, dealer_Name from retail_dealer@mkitdw_qlud2_dbl where dealer_Id = :dealer_Id ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealerId);
        List resultSet = query.getResultList();
        if(resultSet != null && resultSet.size() > 0){
            return (Object[])resultSet.get(0);
        }else{
            throw new ObjectNotFoundException("Not found info for dealer_Id: " + dealerId);
        }
    }

    @Override
    public Object[] findDistrictById(Long districtId) throws ObjectNotFoundException {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select district_Id, district_code, district_name from district@mkitdw_qlud2_dbl where district_Id = :district_Id ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("district_Id", districtId);
        List resultSet = query.getResultList();
        if(resultSet != null && resultSet.size() > 0){
            return (Object[])resultSet.get(0);
        }else{
            throw new ObjectNotFoundException("Object not found for district_Id: " + districtId);
        }
    }

    @Override
    public Object[] baoCaoTongHopChuongTrinhKPP_theoDaiLy(Map<String, Object> properties, Boolean hasFilterDate, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select distinct branch_name, district_name ")
                .append("       , case when extract(month from cycle) = 10 then 1 ")
                .append("               when extract(month from cycle) = 11 then 2 ")
                .append("               when extract(month from cycle) = 12 then 3 else null end as giaiDoan ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("  , trunc(issue_date) ");
        }
        mainQuery.append("      , dealer_code, dealer_name ")
                .append("       , ( select case when condition_status != '1' then '0' else condition_status end as condition_status ")
                .append("           from kpp_dealer_account@mkitdw_qlud2_dbl ")
                .append("           where cycle = a.cycle ")
                .append("               and dealer_id = a.dealer_id) condition_status ")
                .append("       , sum(kit_quantity) kit_quantity ")
                .append("       , sum(card_revenue) card_revenue ")
                .append("       , sum(vas_quantity) vas_quantity ")
                .append("       , sum(vas_revenue) vas_revenue ")
                .append("       , sum(remain_quantity) remain_quantity ")
                .append("       , sum(new_dev_quantity) new_dev_quantity ")
                .append("       , sum(remain_charge_amount) remain_charge_amount ")
                .append("       , sum(new_charge_amount) new_charge_amount ")
                .append("       , sum(sub_intr_quantity) sub_intr_quantity ")
                .append("       , sum(sub_charged_quantity) sub_charged_quantity ")
                .append("       , sum(sub_intr_charge_amount) sub_intr_charge_amount ")
                .append("   from (select r.branch_name, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name ")
                .append("               , trunc(ki.invoice_date, 'month') as cycle, ki.invoice_date issue_date ")
                .append("               , case when kid.good_group_type = '1' then quantity else 0 end kit_quantity ")
                .append("               , case when kid.good_group_type != '1' then kid.amount else 0 end card_revenue ")
                .append("               , 0 vas_quantity, 0 vas_revenue, 0 remain_quantity, 0 new_dev_quantity, 0 remain_charge_amount ")
                .append("               , 0 new_charge_amount, 0 sub_intr_quantity, 0 sub_charged_quantity, 0 sub_intr_charge_amount ")
                .append("          from kpp_invoice@mkitdw_qlud2_dbl ki, kpp_invoice_detail@mkitdw_qlud2_dbl kid, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("          where ki.dealer_id = r.dealer_id ")
                .append("               and ki.invoice_id = kid.invoice_id ")
                .append("               and ki.invoice_date = kid.invoice_date ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("          and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("          and r.district_id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("          and r.dealer_id = :dealer_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("          and trunc(ki.invoice_date, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("          and ki.invoice_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
            mainQuery.append("          and kid.invoice_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("          and ki.invoice_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
            mainQuery.append("          and kid.invoice_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("          union all ")
                .append("           select r.branch_name, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name, trunc(v.comm_date, 'month') as cycle ")
                .append("                   , v.comm_date issue_date, 0 kit_quantity, 0 card_revenue, 1 vas_quantity, v.gia_goi vas_revenue ")
                .append("                   , 0 remain_quantity, 0 new_dev_quantity, 0 remain_charge_amount, 0 new_charge_amount, 0 sub_intr_quantity ")
                .append("                   , 0 sub_charged_quantity, 0 sub_intr_charge_amount ")
                .append("           from kpp_vas_point_detail@mkitdw_qlud2_dbl v, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where v.dealer_id = r.dealer_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("          and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("          and r.district_id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("          and r.dealer_id = :dealer_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("          and trunc(v.comm_date, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("          and v.comm_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("          and v.comm_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("          union all ")
                .append("           select r.branch_name, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name ")
                .append("                   , trunc(s.ngay_ps, 'month') as cycle ")
                .append("                   , s.ngay_ps issue_date, 0 kit_quantity, 0 card_revenue, 0 vas_quantity, 0 vas_revenue ")
                .append("                   , case when s.prom_sub_type = '1' and s.type_input = 'BHTT_NEW' then 1 else 0 end remain_quantity ")
                .append("                   , case when s.prom_sub_type = '2' and s.type_input = 'BHTT_NEW' then 1 else 0 end new_dev_quantity ")
                .append("                   , 0 remain_charge_amount, 0 new_charge_amount ")
                .append("                   , case when s.type_input = 'GTKH' then 1 else 0 end sub_intr_quantity ")
                .append("                   , case when s.type_input = 'GTKH' and s.gtkh_charge_status = '1' then 1 else 0 end sub_charged_quantity ")
                .append("                   , 0 sub_intr_charge_amount ")
                .append("           from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl s, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where s.shop_code = r.dealer_code ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("          and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("          and r.district_id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("          and r.dealer_id = :dealer_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("          and trunc(s.ngay_ps, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("          and s.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("          and s.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("          union all ")
                .append("           select r.branch_name, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name, trunc(s.ngay_ps, 'month') as cycle ")
                .append("                   , c.ngay_ps issue_date ")
                .append("                   , 0 kit_quantity ")
                .append("                   , 0 card_revenue ")
                .append("                   , 0 vas_quantity ")
                .append("                   , 0 vas_revenue ")
                .append("                   , 0 remain_quantity ")
                .append("                   , 0 new_dev_quantity ")
                .append("                   , case when s.prom_sub_type = '1' and s.type_input = 'BHTT_NEW' then nvl(c.amount, 0) else 0 end remain_charge_amount ")
                .append("                   , case when s.prom_sub_type = '2' and s.type_input = 'BHTT_NEW' then nvl(c.amount, 0) else 0 end new_charge_amount ")
                .append("                   , 0 sub_intr_quantity, 0 sub_charged_quantity ")
                .append("                   , case when s.type_input = 'GTKH' then c.amount else 0 end sub_intr_charge_amount ")
                .append("           from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl s ")
                .append("                   , ct_tich_diem_kpp_2014_psc@mkitdw_qlud2_dbl c ")
                .append("                   , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where s.shop_code = r.dealer_code ")
                .append("               and s.so_khach_hang = c.thue_bao ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("          and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("          and r.district_id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("          and r.dealer_id = :dealer_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("          and trunc(s.ngay_ps, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("          and c.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("          and c.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append(" ) a ")
                .append("   where branch_name is not null ")
                .append("   group by branch_name, district_name, a.dealer_id, dealer_code, dealer_name, a.cycle ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("      , trunc(issue_date) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) where 1 = 1 ");
        if(properties.get("condition_status") != null){
            if(properties.get("condition_status").toString().equals("1")){
                sqlQueryClause.append("      and condition_status = '1' ");
            }else{
                sqlQueryClause.append("      and condition_status != '1' ");
            }
        }
        mainQuery.append(" order by branch_name, district_name, dealer_name, giaiDoan ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("cycle") != null){
            query.setParameter("cycle", "2014-" + properties.get("cycle").toString() + "-01");
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(branch_name) from ( ").append(mainQuery).append(" ) where 1 = 1 ");
        if(properties.get("condition_status") != null){
            if(properties.get("condition_status").toString().equals("1")){
                sqlCountClause.append("      and condition_status = '1' ");
            }else{
                sqlCountClause.append("      and condition_status != '1' ");
            }
        }
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("cycle") != null){
            countQuery.setParameter("cycle", "2014-" + properties.get("cycle").toString() + "-01");
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
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoTongHopChuongTrinhKPP_theoQuanHuyen(Map<String, Object> properties, Boolean hasFilterDate, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select branch_name, district_name, giaiDoan ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("  ,trunc(issue_date) issue_date ");
        }
        mainQuery.append("      , nvl(count(dealer_name), 0) as dealer_quantity ")
                .append("       , ( select count(kpp.dealer_id) from kpp_dealer_account@mkitdw_qlud2_dbl kpp, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where r.dealer_id = kpp.dealer_id and r.branch_id = m.branch_id and r.district_id = m.district_id and kpp.condition_status = '1') as valid_condition_quantity ")
                .append("       , nvl(sum(kit_quantity), 0) as kit_quantity ")
                .append("       , nvl(sum(card_revenue), 0) as card_revenue ")
                .append("       , nvl(sum(vas_quantity), 0) as vas_quantity ")
                .append("       , nvl(sum(vas_revenue), 0) as vas_revenue ")
                .append("       , nvl(sum(remain_quantity), 0) as remain_quantity ")
                .append("       , nvl(sum(new_dev_quantity), 0) as new_dev_quantity ")
                .append("       , nvl(sum(remain_charge_amount), 0) as remain_charge_amount ")
                .append("       , nvl(sum(new_charge_amount), 0) as new_charge_amount ")
                .append("       , nvl(sum(sub_intr_quantity), 0) as sub_intr_quantity ")
                .append("       , nvl(sum(sub_charged_quantity), 0) as sub_charged_quantity ")
                .append("       , nvl(sum(sub_intr_charge_amount), 0) as sub_intr_charge_amount ")
                .append("   from (  select branch_id, branch_name, district_id, district_name ")
                .append("               , case when extract(month from cycle) = 10 then 1 ")
                .append("                       when extract(month from cycle) = 11 then 2 ")
                .append("                       when extract(month from cycle) = 12 then 3 else null end as giaiDoan ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("          , issue_date ");
        }
        mainQuery.append("              , dealer_id, dealer_code, dealer_name ")
                .append("               ,( select case when condition_status != '1' then '0' else condition_status end as condition_status ")
                .append("                               from kpp_dealer_account@mkitdw_qlud2_dbl ")
                .append("                               where cycle = a.cycle ")
                .append("                                   and dealer_id = a.dealer_id) condition_status ")
                .append("               , nvl(sum(kit_quantity), 0) as kit_quantity ")
                .append("               , nvl(sum(card_revenue), 0) as card_revenue ")
                .append("               , nvl(sum(vas_quantity), 0) as vas_quantity ")
                .append("               , nvl(sum(vas_revenue), 0) as vas_revenue ")
                .append("               , nvl(sum(remain_quantity), 0) as remain_quantity ")
                .append("               , nvl(sum(new_dev_quantity), 0) as new_dev_quantity ")
                .append("               , nvl(sum(remain_charge_amount), 0) as remain_charge_amount ")
                .append("               , nvl(sum(new_charge_amount), 0) as new_charge_amount ")
                .append("               , nvl(sum(sub_intr_quantity), 0) as sub_intr_quantity ")
                .append("               , nvl(sum(sub_charged_quantity), 0) as sub_charged_quantity ")
                .append("               , nvl(sum(sub_intr_charge_amount), 0) as sub_intr_charge_amount ")
                .append("           from (  select r.branch_id, r.branch_name, r.district_id, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name ")
                .append("                       , trunc(ki.invoice_date, 'month') as cycle ")
                .append("                       , ki.invoice_date issue_date ")
                .append("                       , case when kid.good_group_type = '1' then quantity else 0 end kit_quantity ")
                .append("                       , case when kid.good_group_type != '1' then kid.amount else 0 end card_revenue ")
                .append("                       , 0 vas_quantity, 0 vas_revenue, 0 remain_quantity, 0 new_dev_quantity ")
                .append("                       , 0 remain_charge_amount, 0 new_charge_amount, 0 sub_intr_quantity ")
                .append("                       , 0 sub_charged_quantity, 0 sub_intr_charge_amount ")
                .append("                   from kpp_invoice@mkitdw_qlud2_dbl ki, kpp_invoice_detail@mkitdw_qlud2_dbl kid, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("                   where ki.dealer_id  = r.dealer_id and ki.invoice_id = kid.invoice_id and ki.invoice_date = kid.invoice_date ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("                  and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("                  and r.district_id = :district_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("                  and trunc(ki.invoice_date, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("                  and ki.invoice_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
            mainQuery.append("                  and kid.invoice_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("                  and ki.invoice_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
            mainQuery.append("                  and kid.invoice_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("                   union all ")
                .append("                   select r.branch_id, r.branch_name, r.district_id, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name ")
                .append("                       , trunc(v.comm_date, 'month') as cycle, v.comm_date issue_date, 0 kit_quantity, 0 card_revenue, 1 vas_quantity ")
                .append("                       , v.gia_goi vas_revenue , 0 remain_quantity, 0 new_dev_quantity, 0 remain_charge_amount, 0 new_charge_amount ")
                .append("                       , 0 sub_intr_quantity, 0 sub_charged_quantity, 0 sub_intr_charge_amount ")
                .append("                   from kpp_vas_point_detail@mkitdw_qlud2_dbl v, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("                   where v.dealer_id = r.dealer_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("              and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("              and r.district_id = :district_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("                  and trunc(v.comm_date, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("              and v.comm_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("              and v.comm_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("                   union all ")
                .append("                   select r.branch_id, r.branch_name, r.district_id, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name ")
                .append("                       , trunc(s.ngay_ps, 'month') as cycle, s.ngay_ps issue_date, 0 kit_quantity, 0 card_revenue, 0 vas_quantity ")
                .append("                       , 0 vas_revenue ")
                .append("                       , case when s.prom_sub_type = '1' and s.type_input = 'BHTT_NEW' then 1 else 0 end remain_quantity ")
                .append("                       , case when s.prom_sub_type = '2' and s.type_input = 'BHTT_NEW' then 1 else 0 end new_dev_quantity ")
                .append("                       , 0 remain_charge_amount, 0 new_charge_amount ")
                .append("                       , case when s.type_input = 'GTKH' then 1 else 0 end sub_intr_quantity ")
                .append("                       , case when s.type_input = 'GTKH' and s.gtkh_charge_status = '1' then 1 else 0 end sub_charged_quantity ")
                .append("                       , 0 sub_intr_charge_amount ")
                .append("                   from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl s, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("                   where s.shop_code = r.dealer_code ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("              and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("              and r.district_id = :district_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("                  and trunc(s.ngay_ps, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("              and s.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("              and s.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("                   union all ")
                .append("                   select r.branch_id, r.branch_name, r.district_id, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name, trunc(s.ngay_ps, 'month') as cycle ")
                .append("                       , c.ngay_ps issue_date, 0 kit_quantity, 0 card_revenue, 0 vas_quantity ")
                .append("                       , 0 vas_revenue, 0 remain_quantity, 0 new_dev_quantity ")
                .append("                       , case when s.prom_sub_type = '1' and s.type_input = 'BHTT_NEW' then nvl(c.amount, 0) else 0 end remain_charge_amount ")
                .append("                       , case when s.prom_sub_type = '2' and s.type_input = 'BHTT_NEW' then nvl(c.amount, 0) else 0 end new_charge_amount ")
                .append("                       , 0 sub_intr_quantity, 0 sub_charged_quantity ")
                .append("                        , case when s.type_input = 'GTKH' then c.amount else 0 end sub_intr_charge_amount ")
                .append("                   from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl s , ct_tich_diem_kpp_2014_psc@mkitdw_qlud2_dbl c , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("                   where s.shop_code   = r.dealer_code and s.so_khach_hang = c.thue_bao ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("                  and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("                  and r.district_id = :district_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("                  and trunc(s.ngay_ps, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("                  and s.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("                  and s.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("               ) a ")
                .append("           where branch_name is not null ")
                .append("           group by branch_id, branch_name, district_id, district_name , cycle , dealer_id, dealer_code, dealer_name ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("              , issue_date ");
        }
        mainQuery.append("       ) m ")
                .append("       where 1 = 1 ");
        if(properties.get("condition_status") != null){
            if(properties.get("condition_status").toString().equals("1")){
                mainQuery.append("      and condition_status = '1' ");
            }else{
                mainQuery.append("      and condition_status != '1' ");
            }
        }
        mainQuery.append("       group by m.branch_id, m.branch_name, m.district_id, m.district_name, m.giaiDoan ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("          , trunc(issue_date) ");
        }
        mainQuery.append("      order by m.branch_name, m.district_name, m.giaiDoan ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("          , trunc(issue_date) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("cycle") != null){
            query.setParameter("cycle", "2014-" + properties.get("cycle").toString() + "-01");
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("cycle") != null){
            countQuery.setParameter("cycle", "2014-" + properties.get("cycle").toString() + "-01");
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
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public Object[] baoCaoTongHopChuongTrinhKPP_theoChiNhanh(Map<String, Object> properties, Boolean hasFilterDate, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select branch_name, giaiDoan ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("  ,trunc(issue_date) issue_date ");
        }
        mainQuery.append("      , nvl(count(dealer_name), 0) as dealer_quantity ")
                .append("       , ( select count(kpp.dealer_id) from kpp_dealer_account@mkitdw_qlud2_dbl kpp, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           where r.dealer_id = kpp.dealer_id and r.branch_id = m.branch_id and kpp.condition_status = '1') as valid_condition_quantity ")
                .append("       , nvl(sum(kit_quantity), 0) as kit_quantity ")
                .append("       , nvl(sum(card_revenue), 0) as card_revenue ")
                .append("       , nvl(sum(vas_quantity), 0) as vas_quantity ")
                .append("       , nvl(sum(vas_revenue), 0) as vas_revenue ")
                .append("       , nvl(sum(remain_quantity), 0) as remain_quantity ")
                .append("       , nvl(sum(new_dev_quantity), 0) as new_dev_quantity ")
                .append("       , nvl(sum(remain_charge_amount), 0) as remain_charge_amount ")
                .append("       , nvl(sum(new_charge_amount), 0) as new_charge_amount ")
                .append("       , nvl(sum(sub_intr_quantity), 0) as sub_intr_quantity ")
                .append("       , nvl(sum(sub_charged_quantity), 0) as sub_charged_quantity ")
                .append("       , nvl(sum(sub_intr_charge_amount), 0) as sub_intr_charge_amount ")
                .append("   from (  select branch_id, branch_name, district_id, district_name ")
                .append("               , case when extract(month from cycle) = 10 then 1 ")
                .append("                       when extract(month from cycle) = 11 then 2 ")
                .append("                       when extract(month from cycle) = 12 then 3 else null end as giaiDoan ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("          , issue_date ");
        }
        mainQuery.append("              , dealer_id, dealer_code, dealer_name ")
                .append("               ,( select case when condition_status != '1' then '0' else condition_status end as condition_status ")
                .append("                               from kpp_dealer_account@mkitdw_qlud2_dbl ")
                .append("                               where cycle = a.cycle ")
                .append("                                   and dealer_id = a.dealer_id) condition_status ")
                .append("               , nvl(sum(kit_quantity), 0) as kit_quantity ")
                .append("               , nvl(sum(card_revenue), 0) as card_revenue ")
                .append("               , nvl(sum(vas_quantity), 0) as vas_quantity ")
                .append("               , nvl(sum(vas_revenue), 0) as vas_revenue ")
                .append("               , nvl(sum(remain_quantity), 0) as remain_quantity ")
                .append("               , nvl(sum(new_dev_quantity), 0) as new_dev_quantity ")
                .append("               , nvl(sum(remain_charge_amount), 0) as remain_charge_amount ")
                .append("               , nvl(sum(new_charge_amount), 0) as new_charge_amount ")
                .append("               , nvl(sum(sub_intr_quantity), 0) as sub_intr_quantity ")
                .append("               , nvl(sum(sub_charged_quantity), 0) as sub_charged_quantity ")
                .append("               , nvl(sum(sub_intr_charge_amount), 0) as sub_intr_charge_amount ")
                .append("           from (  select r.branch_id, r.branch_name, r.district_id, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name ")
                .append("                       , trunc(ki.invoice_date, 'month') as cycle ")
                .append("                       , ki.invoice_date issue_date ")
                .append("                       , case when kid.good_group_type = '1' then quantity else 0 end kit_quantity ")
                .append("                       , case when kid.good_group_type != '1' then kid.amount else 0 end card_revenue ")
                .append("                       , 0 vas_quantity, 0 vas_revenue, 0 remain_quantity, 0 new_dev_quantity ")
                .append("                       , 0 remain_charge_amount, 0 new_charge_amount, 0 sub_intr_quantity ")
                .append("                       , 0 sub_charged_quantity, 0 sub_intr_charge_amount ")
                .append("                   from kpp_invoice@mkitdw_qlud2_dbl ki, kpp_invoice_detail@mkitdw_qlud2_dbl kid, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("                   where ki.dealer_id  = r.dealer_id and ki.invoice_id = kid.invoice_id and ki.invoice_date = kid.invoice_date ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("                  and r.branch_id = :branch_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("                  and trunc(ki.invoice_date, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("                  and ki.invoice_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
            mainQuery.append("                  and kid.invoice_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("                  and ki.invoice_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
            mainQuery.append("                  and kid.invoice_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("                   union all ")
                .append("                   select r.branch_id, r.branch_name, r.district_id, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name ")
                .append("                       , trunc(v.comm_date, 'month') as cycle, v.comm_date issue_date, 0 kit_quantity, 0 card_revenue, 1 vas_quantity ")
                .append("                       , v.gia_goi vas_revenue , 0 remain_quantity, 0 new_dev_quantity, 0 remain_charge_amount, 0 new_charge_amount ")
                .append("                       , 0 sub_intr_quantity, 0 sub_charged_quantity, 0 sub_intr_charge_amount ")
                .append("                   from kpp_vas_point_detail@mkitdw_qlud2_dbl v, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("                   where v.dealer_id = r.dealer_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("              and r.branch_id = :branch_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("                  and trunc(v.comm_date, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("              and v.comm_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("              and v.comm_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("                   union all ")
                .append("                   select r.branch_id, r.branch_name, r.district_id, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name ")
                .append("                       , trunc(s.ngay_ps, 'month') as cycle, s.ngay_ps issue_date, 0 kit_quantity, 0 card_revenue, 0 vas_quantity ")
                .append("                       , 0 vas_revenue ")
                .append("                       , case when s.prom_sub_type = '1' and s.type_input = 'BHTT_NEW' then 1 else 0 end remain_quantity ")
                .append("                       , case when s.prom_sub_type = '2' and s.type_input = 'BHTT_NEW' then 1 else 0 end new_dev_quantity ")
                .append("                       , 0 remain_charge_amount, 0 new_charge_amount ")
                .append("                       , case when s.type_input = 'GTKH' then 1 else 0 end sub_intr_quantity ")
                .append("                       , case when s.type_input = 'GTKH' and s.gtkh_charge_status = '1' then 1 else 0 end sub_charged_quantity ")
                .append("                       , 0 sub_intr_charge_amount ")
                .append("                   from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl s, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("                   where s.shop_code = r.dealer_code ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("              and r.branch_id = :branch_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("                  and trunc(s.ngay_ps, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("              and s.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("              and s.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("                   union all ")
                .append("                   select r.branch_id, r.branch_name, r.district_id, r.district_name, r.dealer_id, r.dealer_code, r.dealer_name, trunc(s.ngay_ps, 'month') as cycle ")
                .append("                       , c.ngay_ps issue_date, 0 kit_quantity, 0 card_revenue, 0 vas_quantity ")
                .append("                       , 0 vas_revenue, 0 remain_quantity, 0 new_dev_quantity ")
                .append("                       , case when s.prom_sub_type = '1' and s.type_input = 'BHTT_NEW' then nvl(c.amount, 0) else 0 end remain_charge_amount ")
                .append("                       , case when s.prom_sub_type = '2' and s.type_input = 'BHTT_NEW' then nvl(c.amount, 0) else 0 end new_charge_amount ")
                .append("                       , 0 sub_intr_quantity, 0 sub_charged_quantity ")
                .append("                        , case when s.type_input = 'GTKH' then c.amount else 0 end sub_intr_charge_amount ")
                .append("                   from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl s , ct_tich_diem_kpp_2014_psc@mkitdw_qlud2_dbl c , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("                   where s.shop_code   = r.dealer_code and s.so_khach_hang = c.thue_bao ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("                  and r.branch_id = :branch_Id ");
        }
        if(properties.get("cycle") != null){
            mainQuery.append("                  and trunc(s.ngay_ps, 'month') = trunc(to_date(:cycle, 'YYYY-MM-DD'), 'month') ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("                  and s.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("                  and s.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("               ) a ")
                .append("           where branch_name is not null ")
                .append("           group by branch_id, branch_name, district_id, district_name , cycle , dealer_id, dealer_code, dealer_name ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("              , issue_date ");
        }
        mainQuery.append("       ) m ")
                .append("       where 1 = 1 ");
        if(properties.get("condition_status") != null){
            if(properties.get("condition_status").toString().equals("1")){
                mainQuery.append("      and condition_status = '1' ");
            }else{
                mainQuery.append("      and condition_status != '1' ");
            }
        }
        mainQuery.append("       group by m.branch_id, m.branch_name, m.giaiDoan ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("          , trunc(issue_date) ");
        }
        mainQuery.append("      order by m.branch_name, m.giaiDoan ");
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            mainQuery.append("          , trunc(issue_date) ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("cycle") != null){
            query.setParameter("cycle", "2014-" + properties.get("cycle").toString() + "-01");
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("cycle") != null){
            countQuery.setParameter("cycle", "2014-" + properties.get("cycle").toString() + "-01");
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobiFone_theoDaiLy(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select b.branch_name, d.district_name, r.dealer_code, r.dealer_name ")
                .append("           , k.invoice_date as ngayDonHang, k.invoice_no as donHang, kd.quantity as soLuongBTG ")
                .append("   from kpp_invoice@mkitdw_qlud2_dbl k, kpp_invoice_detail@mkitdw_qlud2_dbl kd, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           , branch@mkitdw_qlud2_dbl b, district@mkitdw_qlud2_dbl d ")
                .append("   where k.invoice_id = kd.invoice_id ")
                .append("       and k.invoice_date = kd.invoice_date ")
                .append("       and k.dealer_id = r.dealer_id ")
                .append("       and kd.good_group_type = '1' ")
                .append("       and r.branch_id = b.branch_id ")
                .append("       and d.branch_id = b.branch_id ")
                .append("       and r.district_id = d.district_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("  and b.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("  and d.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("  and r.dealer_Id = :dealer_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("  and k.invoice_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("  and k.invoice_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("   order by b.branch_name, d.district_name, r.dealer_name, k.invoice_date ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobiFone_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("      select b.branch_name, d.district_name ")
                .append("               , k.invoice_date as ngayDonHang, k.invoice_no as donHang, kd.quantity as soLuongBTG ")
                .append("       from kpp_invoice@mkitdw_qlud2_dbl k, kpp_invoice_detail@mkitdw_qlud2_dbl kd, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("               , branch@mkitdw_qlud2_dbl b, district@mkitdw_qlud2_dbl d ")
                .append("       where k.invoice_id = kd.invoice_id ")
                .append("           and k.invoice_date = kd.invoice_date ")
                .append("           and k.dealer_id = r.dealer_id ")
                .append("           and kd.good_group_type = '1' ")
                .append("           and r.branch_id = b.branch_id ")
                .append("           and d.branch_id = b.branch_id ")
                .append("           and r.district_id = d.district_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("      and b.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("      and d.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("      and r.dealer_Id = :dealer_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and k.invoice_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and k.invoice_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("   order by b.branch_name, d.district_name, r.dealer_name, k.invoice_date ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobiFone_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        return null;
    }

    @Override
    public Object[] baoCaoChiTietHangMucPSC_kpp_mua_the_tu_mobiFone_theoDaiLy(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select b.branch_name, d.district_name, r.dealer_code, r.dealer_name ")
                .append("           , k.invoice_date as ngayDonHang, k.invoice_no as donHang, kd.quantity as soLuongBTG ")
                .append("   from kpp_invoice@mkitdw_qlud2_dbl k, kpp_invoice_detail@mkitdw_qlud2_dbl kd, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("           , branch@mkitdw_qlud2_dbl b, district@mkitdw_qlud2_dbl d ")
                .append("   where k.invoice_id = kd.invoice_id ")
                .append("       and k.invoice_date = kd.invoice_date ")
                .append("       and k.dealer_id = r.dealer_id ")
                .append("       and kd.good_group_type != '1' ")
                .append("       and r.branch_id = b.branch_id ")
                .append("       and d.branch_id = b.branch_id ")
                .append("       and r.district_id = d.district_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("  and b.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("  and d.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("  and r.dealer_Id = :dealer_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("  and k.invoice_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("  and k.invoice_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("   order by b.branch_name, d.district_name, r.dealer_name, k.invoice_date ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_ban_vas_theoDaiLy(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select branch_name, district_name, dealer_code, dealer_name, soEZ, trunc(ngayBanVAS) ")
                .append("           , nvl(sum(soKH), 0) as soKH, loaiVAS, nvl(sum(soLuong), 0) as soLuong, nvl(sum(doanhThu), 0) as doanhThu, bhtt_status ")
                .append("   from ( ")
                .append("       select b.branch_name, d.district_name, r.dealer_code, r.dealer_name, r.ez_isdn AS soEZ ")
                .append("               , c.comm_date as ngayBanVAS, c.so_KH as soKH, trim(c.ten_goi) as loaiVAS, 1 as soLuong, c.gia_goi as doanhThu ")
                .append("               , nvl(( select 1 from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl ")
                .append("                       where type_input = 'BHTT_NEW' and so_khach_hang = SUBSTR (c.so_kh, 3)), '0') bhtt_status ")
                .append("       from kpp_vas_point_detail@mkitdw_qlud2_dbl c, retail_dealer@mkitdw_qlud2_dbl r, dealer_properties@mkitdw_qlud2_dbl dp ")
                .append("               , branch@mkitdw_qlud2_dbl b, district@mkitdw_qlud2_dbl d ")
                .append("       where c.ez_isdn = dp.properties_value ")
                .append("           and dp.properties_code = 'EZ_ISDN' ")
                .append("           and r.dealer_id = dp.dealer_id ")
                .append("           and r.branch_id = b.branch_id ")
                .append("           and r.district_id = d.district_id ")
                .append("           and d.branch_id = b.branch_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("      and b.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("      and d.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("      and r.dealer_Id = :dealer_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and c.comm_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and c.comm_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("      ) ")
                .append("       group by branch_name, district_name, dealer_code, dealer_name, soEZ, trunc(ngayBanVAS), loaiVAS, bhtt_status ")
                .append("       order by branch_name, district_name, dealer_code, dealer_name, soEZ, trunc(ngayBanVAS), loaiVAS ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_ban_vas_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select branch_name, district_name, loaiVAS, nvl(sum(soLuong), 0) as soLuong, nvl(sum(doanhThu), 0) as doanhThu ")
                .append("   from ( ")
                .append("       select b.branch_name, d.district_name, r.dealer_code, r.dealer_name, r.ez_isdn AS soEZ ")
                .append("               , c.comm_date as ngayBanVAS, c.so_KH as soKH, trim(c.ten_goi) as loaiVAS, 1 as soLuong, c.gia_goi as doanhThu ")
                .append("               , nvl(( select 1 from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl ")
                .append("                       where type_input = 'BHTT_NEW' and so_khach_hang = SUBSTR (c.so_kh, 3)), '0') bhtt_status ")
                .append("       from kpp_vas_point_detail@mkitdw_qlud2_dbl c, retail_dealer@mkitdw_qlud2_dbl r, dealer_properties@mkitdw_qlud2_dbl dp ")
                .append("               , branch@mkitdw_qlud2_dbl b, district@mkitdw_qlud2_dbl d ")
                .append("       where c.ez_isdn = dp.properties_value ")
                .append("           and dp.properties_code = 'EZ_ISDN' ")
                .append("           and r.dealer_id = dp.dealer_id ")
                .append("           and r.branch_id = b.branch_id ")
                .append("           and r.district_id = d.district_id ")
                .append("           and d.branch_id = b.branch_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("      and b.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("      and d.district_Id = :district_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and c.comm_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and c.comm_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("      ) ")
                .append("       group by branch_name, district_name, loaiVAS ")
                .append("       order by branch_name, district_name, loaiVAS ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_ban_vas_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select branch_name, loaiVAS, nvl(sum(soLuong), 0) as soLuong, nvl(sum(doanhThu), 0) as doanhThu ")
                .append("   from ( ")
                .append("       select b.branch_name, d.district_name, r.dealer_code, r.dealer_name, r.ez_isdn AS soEZ ")
                .append("               , c.comm_date as ngayBanVAS, c.so_KH as soKH, trim(c.ten_goi) as loaiVAS, 1 as soLuong, c.gia_goi as doanhThu ")
                .append("               , nvl(( select 1 from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl ")
                .append("                       where type_input = 'BHTT_NEW' and so_khach_hang = SUBSTR (c.so_kh, 3)), '0') bhtt_status ")
                .append("       from kpp_vas_point_detail@mkitdw_qlud2_dbl c, retail_dealer@mkitdw_qlud2_dbl r, dealer_properties@mkitdw_qlud2_dbl dp ")
                .append("               , branch@mkitdw_qlud2_dbl b, district@mkitdw_qlud2_dbl d ")
                .append("       where c.ez_isdn = dp.properties_value ")
                .append("           and dp.properties_code = 'EZ_ISDN' ")
                .append("           and r.dealer_id = dp.dealer_id ")
                .append("           and r.branch_id = b.branch_id ")
                .append("           and r.district_id = d.district_id ")
                .append("           and d.branch_id = b.branch_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("      and b.branch_id = :branch_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and c.comm_date >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and c.comm_date < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("      ) ")
                .append("       group by branch_name, loaiVAS ")
                .append("       order by branch_name, loaiVAS ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoDaiLy(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select branch_name, district_name, dealer_code, dealer_name, so_ez, ngayNhanTin, so_khach_hang ")
                .append("      , ngay_kich, thang_kich, nam_kich, cuoc_thoai, cuoc_sms, cuoc_data, cuoc_khac ")
                .append("   from ( ")
                .append("       select r.branch_name, r.district_name, r.dealer_code, r.dealer_name, c.so_ez, c.ngay_ps as ngayNhanTin, c.so_khach_hang ")
                .append("               , extract(day from c.active_datetime) as ngay_kich ")
                .append("               , extract(month from c.active_datetime) as thang_kich ")
                .append("               , extract(year from c.active_datetime) as nam_kich ")
                .append("               , nvl(cuoc_thoai, 0) as cuoc_thoai, nvl(cuoc_sms, 0) as cuoc_sms, nvl(cuoc_data, 0) as cuoc_data, nvl(cuoc_khac, 0) as cuoc_khac ")
                .append("       from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl c ")
                .append("               , (select thue_bao ")
                .append("                       , sum(cuoc_thoai) cuoc_thoai ")
                .append("                       , sum(cuoc_sms) cuoc_sms ")
                .append("                       , sum(cuoc_data) cuoc_data ")
                .append("                       , sum(cuoc_khac) cuoc_khac ")
                .append("                   from (  select thue_bao ")
                .append("                               , case when charge_type = 'CALL' then amount else 0 end cuoc_thoai ")
                .append("                               , case when charge_type = 'SMS' then amount else 0 end cuoc_sms ")
                .append("                               , case when charge_type = 'DATA' then amount else 0 end cuoc_data ")
                .append("                               , case when charge_type not in ('CALL', 'SMS', 'DATA') then amount else 0 end cuoc_khac ")
                .append("                           from kpp_sub_charge_detail@mkitdw_qlud2_dbl ")
                .append("                   ) ")
                .append("                   group by thue_bao ")
                .append("       ) k ")
                .append("       , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("        where c.so_khach_hang = k.thue_bao(+) and c.shop_code = r.dealer_code and c.type_input = 'GTKH' ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("          and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("          and r.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("          and r.dealer_Id = :dealer_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and c.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and c.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("   ) ")
                .append("    order by branch_name, district_name, dealer_name, ngayNhanTin, so_khach_hang, ngay_kich, thang_kich, nam_kich ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select branch_name, district_name, sum(cuoc_thoai) as cuoc_thoai, sum(cuoc_sms) as cuoc_sms, sum(cuoc_data) as cuoc_data, sum(cuoc_khac) as cuoc_khac ")
                .append("   from ( ")
                .append("       select r.branch_name, r.district_name, r.dealer_code, r.dealer_name, c.so_ez, c.ngay_ps as ngayNhanTin, c.so_khach_hang ")
                .append("               , extract(day from c.active_datetime) as ngay_kich ")
                .append("               , extract(month from c.active_datetime) as thang_kich ")
                .append("               , extract(year from c.active_datetime) as nam_kich ")
                .append("               , nvl(cuoc_thoai, 0) as cuoc_thoai, nvl(cuoc_sms, 0) as cuoc_sms, nvl(cuoc_data, 0) as cuoc_data, nvl(cuoc_khac, 0) as cuoc_khac ")
                .append("       from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl c ")
                .append("               , (select thue_bao ")
                .append("                       , sum(cuoc_thoai) cuoc_thoai ")
                .append("                       , sum(cuoc_sms) cuoc_sms ")
                .append("                       , sum(cuoc_data) cuoc_data ")
                .append("                       , sum(cuoc_khac) cuoc_khac ")
                .append("                   from (  select thue_bao ")
                .append("                               , case when charge_type = 'CALL' then amount else 0 end cuoc_thoai ")
                .append("                               , case when charge_type = 'SMS' then amount else 0 end cuoc_sms ")
                .append("                               , case when charge_type = 'DATA' then amount else 0 end cuoc_data ")
                .append("                               , case when charge_type not in ('CALL', 'SMS', 'DATA') then amount else 0 end cuoc_khac ")
                .append("                           from kpp_sub_charge_detail@mkitdw_qlud2_dbl ")
                .append("                   ) ")
                .append("                   group by thue_bao ")
                .append("       ) k ")
                .append("       , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("        where c.so_khach_hang = k.thue_bao(+) and c.shop_code = r.dealer_code and c.type_input = 'GTKH' ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("          and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("          and r.district_Id = :district_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and c.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and c.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("   ) ")
                .append("    group by branch_name, district_name ")
                .append("    order by branch_name, district_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select branch_name, sum(cuoc_thoai) as cuoc_thoai, sum(cuoc_sms) as cuoc_sms, sum(cuoc_data) as cuoc_data, sum(cuoc_khac) as cuoc_khac ")
                .append("   from ( ")
                .append("       select r.branch_name, r.district_name, r.dealer_code, r.dealer_name, c.so_ez, c.ngay_ps as ngayNhanTin, c.so_khach_hang ")
                .append("               , extract(day from c.active_datetime) as ngay_kich ")
                .append("               , extract(month from c.active_datetime) as thang_kich ")
                .append("               , extract(year from c.active_datetime) as nam_kich ")
                .append("               , nvl(cuoc_thoai, 0) as cuoc_thoai, nvl(cuoc_sms, 0) as cuoc_sms, nvl(cuoc_data, 0) as cuoc_data, nvl(cuoc_khac, 0) as cuoc_khac ")
                .append("       from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl c ")
                .append("               , (select thue_bao ")
                .append("                       , sum(cuoc_thoai) cuoc_thoai ")
                .append("                       , sum(cuoc_sms) cuoc_sms ")
                .append("                       , sum(cuoc_data) cuoc_data ")
                .append("                       , sum(cuoc_khac) cuoc_khac ")
                .append("                   from (  select thue_bao ")
                .append("                               , case when charge_type = 'CALL' then amount else 0 end cuoc_thoai ")
                .append("                               , case when charge_type = 'SMS' then amount else 0 end cuoc_sms ")
                .append("                               , case when charge_type = 'DATA' then amount else 0 end cuoc_data ")
                .append("                               , case when charge_type not in ('CALL', 'SMS', 'DATA') then amount else 0 end cuoc_khac ")
                .append("                           from kpp_sub_charge_detail@mkitdw_qlud2_dbl ")
                .append("                   ) ")
                .append("                   group by thue_bao ")
                .append("       ) k ")
                .append("       , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("        where c.so_khach_hang = k.thue_bao(+) and c.shop_code = r.dealer_code and c.type_input = 'GTKH' ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("          and r.branch_id = :branch_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and c.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and c.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("   ) ")
                .append("    group by branch_name ")
                .append("    order by branch_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoDBH(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select branch_name, district_name, dealer_code, dealer_name, so_ez, ngayNhanTin, so_khach_hang ")
                .append("      , ngay_kich, thang_kich, nam_kich, cuoc_thoai, cuoc_sms, cuoc_data, cuoc_khac, khuyen_mai ")
                .append("   from ( ")
                .append("       select r.branch_name, r.district_name, r.dealer_code, r.dealer_name, c.so_ez, c.ngay_ps as ngayNhanTin, c.so_khach_hang ")
                .append("               , extract(day from c.active_datetime) as ngay_kich ")
                .append("               , extract(month from c.active_datetime) as thang_kich ")
                .append("               , extract(year from c.active_datetime) as nam_kich ")
                .append("               , nvl(cuoc_thoai, 0) as cuoc_thoai, nvl(cuoc_sms, 0) as cuoc_sms, nvl(cuoc_data, 0) as cuoc_data, nvl(cuoc_khac, 0) as cuoc_khac, nvl(khuyen_mai, 0) as khuyen_mai ")
                .append("       from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl c ")
                .append("               , (select thue_bao ")
                .append("                       , sum(cuoc_thoai) cuoc_thoai ")
                .append("                       , sum(cuoc_sms) cuoc_sms ")
                .append("                       , sum(cuoc_data) cuoc_data ")
                .append("                       , sum(cuoc_khac) cuoc_khac ")
                .append("                       , sum(total_bonus) khuyen_mai ")
                .append("                   from (  select thue_bao ")
                .append("                               , case when charge_type = 'CALL' then amount else 0 end cuoc_thoai ")
                .append("                               , case when charge_type = 'SMS' then amount else 0 end cuoc_sms ")
                .append("                               , case when charge_type = 'DATA' then amount else 0 end cuoc_data ")
                .append("                               , case when charge_type not in ('CALL', 'SMS', 'DATA') then amount else 0 end cuoc_khac ")
                .append("                               , total_bonus ")
                .append("                           from kpp_sub_charge_detail@mkitdw_qlud2_dbl ")
                .append("                   ) ")
                .append("                   group by thue_bao ")
                .append("       ) k ")
                .append("       , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("        where c.so_khach_hang = k.thue_bao(+) and c.shop_code = r.dealer_code and c.type_input = 'BHTT_NEW' ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("          and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("          and r.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("          and r.dealer_Id = :dealer_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and c.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and c.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("   ) ")
                .append("    order by branch_name, district_name, dealer_name, ngayNhanTin, so_khach_hang, ngay_kich, thang_kich, nam_kich ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select branch_name, district_name, sum(cuoc_thoai) as cuoc_thoai, sum(cuoc_sms) as cuoc_sms, sum(cuoc_data) as cuoc_data, sum(cuoc_khac) as cuoc_khac ")
                .append("           ,   sum(khuyen_mai) as khuyen_mai ")
                .append("   from ( ")
                .append("       select r.branch_name, r.district_name, r.dealer_code, r.dealer_name, c.so_ez, c.ngay_ps as ngayNhanTin, c.so_khach_hang ")
                .append("               , extract(day from c.active_datetime) as ngay_kich ")
                .append("               , extract(month from c.active_datetime) as thang_kich ")
                .append("               , extract(year from c.active_datetime) as nam_kich ")
                .append("               , nvl(cuoc_thoai, 0) as cuoc_thoai, nvl(cuoc_sms, 0) as cuoc_sms, nvl(cuoc_data, 0) as cuoc_data, nvl(cuoc_khac, 0) as cuoc_khac ")
                .append("               , nvl(khuyen_mai, 0) as khuyen_mai ")
                .append("       from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl c ")
                .append("               , (select thue_bao ")
                .append("                       , sum(cuoc_thoai) cuoc_thoai ")
                .append("                       , sum(cuoc_sms) cuoc_sms ")
                .append("                       , sum(cuoc_data) cuoc_data ")
                .append("                       , sum(cuoc_khac) cuoc_khac ")
                .append("                       , sum(total_bonus) as khuyen_mai ")
                .append("                   from (  select thue_bao ")
                .append("                               , case when charge_type = 'CALL' then amount else 0 end cuoc_thoai ")
                .append("                               , case when charge_type = 'SMS' then amount else 0 end cuoc_sms ")
                .append("                               , case when charge_type = 'DATA' then amount else 0 end cuoc_data ")
                .append("                               , case when charge_type not in ('CALL', 'SMS', 'DATA') then amount else 0 end cuoc_khac ")
                .append("                               , total_bonus ")
                .append("                           from kpp_sub_charge_detail@mkitdw_qlud2_dbl ")
                .append("                   ) ")
                .append("                   group by thue_bao ")
                .append("       ) k ")
                .append("       , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("        where c.so_khach_hang = k.thue_bao(+) and c.shop_code = r.dealer_code and c.type_input = 'BHTT_NEW' ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("          and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("          and r.district_Id = :district_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and c.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and c.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("   ) ")
                .append("    group by branch_name, district_name ")
                .append("    order by branch_name, district_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
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
    public Object[] baoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  select branch_name, sum(cuoc_thoai) as cuoc_thoai, sum(cuoc_sms) as cuoc_sms, sum(cuoc_data) as cuoc_data, sum(cuoc_khac) as cuoc_khac ")
                .append("           , sum(khuyen_mai) as khuyen_mai ")
                .append("   from ( ")
                .append("       select r.branch_name, r.district_name, r.dealer_code, r.dealer_name, c.so_ez, c.ngay_ps as ngayNhanTin, c.so_khach_hang ")
                .append("               , extract(day from c.active_datetime) as ngay_kich ")
                .append("               , extract(month from c.active_datetime) as thang_kich ")
                .append("               , extract(year from c.active_datetime) as nam_kich ")
                .append("               , nvl(cuoc_thoai, 0) as cuoc_thoai, nvl(cuoc_sms, 0) as cuoc_sms, nvl(cuoc_data, 0) as cuoc_data, nvl(cuoc_khac, 0) as cuoc_khac ")
                .append("               , nvl(khuyen_mai, 0) as khuyen_mai ")
                .append("       from ct_tich_diem_kpp_2014_so_diem@mkitdw_qlud2_dbl c ")
                .append("               , (select thue_bao ")
                .append("                       , sum(cuoc_thoai) cuoc_thoai ")
                .append("                       , sum(cuoc_sms) cuoc_sms ")
                .append("                       , sum(cuoc_data) cuoc_data ")
                .append("                       , sum(cuoc_khac) cuoc_khac ")
                .append("                       , sum(total_bonus) as khuyen_mai ")
                .append("                   from (  select thue_bao ")
                .append("                               , case when charge_type = 'CALL' then amount else 0 end cuoc_thoai ")
                .append("                               , case when charge_type = 'SMS' then amount else 0 end cuoc_sms ")
                .append("                               , case when charge_type = 'DATA' then amount else 0 end cuoc_data ")
                .append("                               , case when charge_type not in ('CALL', 'SMS', 'DATA') then amount else 0 end cuoc_khac ")
                .append("                               , total_bonus ")
                .append("                           from kpp_sub_charge_detail@mkitdw_qlud2_dbl ")
                .append("                   ) ")
                .append("                   group by thue_bao ")
                .append("       ) k ")
                .append("       , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("        where c.so_khach_hang = k.thue_bao(+) and c.shop_code = r.dealer_code and c.type_input = 'BHTT_NEW' ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("          and r.branch_id = :branch_Id ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and c.ngay_ps >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and c.ngay_ps < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("   ) ")
                .append("    group by branch_name ")
                .append("    order by branch_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
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
    public Object[] baoCaoTinNhan_kpp_theoDBH(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select branch_name, district_name, dealer_code, dealer_name, properties_value as soEZ, service_type as hangMuc  ")
                .append("       , sum(success_quantity) success_quantity ")
                .append("       , sum(invalid_ez_quantity) invalid_ez_quantity ")
                .append("       , sum(invalid_sub_type_quantity) invalid_sub_type_quantity ")
                .append("       , sum(exists_error_quantity) exists_error_quantity ")
                .append("       , sum(waiting_quantity) waiting_quantity ")
                .append("       , sum(syntax_error_quantity) syntax_error_quantity ")
                .append("  from (select r.branch_name, r.district_name, r.dealer_code, r.dealer_name, d.properties_value ")
                .append("               , (select name from ap_domain@mkitdw_qlud2_dbl where code = upper(k.service_type) and type = 'SMS_SERVICE_TYPE') service_type ")
                .append("               , case when k.reply_type = '4' then 1 else 0 end success_quantity ")
                .append("               , case when k.reply_type = '1' then 1 else 0 end invalid_ez_quantity ")
                .append("               , case when k.reply_type = '2' then 1 else 0 end invalid_sub_type_quantity ")
                .append("               , case when k.reply_type = '3' then 1 else 0 end exists_error_quantity ")
                .append("               , case when k.reply_type = '5' then 1 else 0 end waiting_quantity ")
                .append("               , case when k.reply_type = '6' then 1 else 0 end syntax_error_quantity ")
                .append("          from kpp_sms@mkitdw_qlud2_dbl k, dealer_properties@mkitdw_qlud2_dbl d, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("          where r.dealer_id = d.dealer_id ")
                .append("              and k.calling_number = substr(d.properties_value, 3) ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("      and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("      and r.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("      and r.dealer_Id = :dealer_Id ");
        }
        if(properties.get("hangMucCode") != null){
            mainQuery.append("      and upper(k.code) = upper(:serviceTypeCoce) ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and k.issue_datetime >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and k.issue_datetime < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("      ) ")
                .append("       group by branch_name, district_name, dealer_code, dealer_name, properties_value, service_type ")
                .append("       order by branch_name, district_name, dealer_name, properties_value, service_type ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("hangMucCode") != null){
            query.setParameter("serviceTypeCode", properties.get("hangMucCode").toString());
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        if(properties.get("hangMucCode") != null){
            countQuery.setParameter("serviceTypeCode", properties.get("hangMucCode").toString());
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
    public Object[] baoCaoTinNhan_kpp_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select branch_name, district_name, service_type as hangMuc ")
                .append("       , sum(success_quantity) success_quantity ")
                .append("       , sum(invalid_ez_quantity) invalid_ez_quantity ")
                .append("       , sum(invalid_sub_type_quantity) invalid_sub_type_quantity ")
                .append("       , sum(exists_error_quantity) exists_error_quantity ")
                .append("       , sum(waiting_quantity) waiting_quantity ")
                .append("       , sum(syntax_error_quantity) syntax_error_quantity ")
                .append("  from (select r.branch_name, r.district_name, r.dealer_code, r.dealer_name, d.properties_value ")
                .append("               , (select name from ap_domain@mkitdw_qlud2_dbl where code = upper(k.service_type) and type = 'SMS_SERVICE_TYPE') service_type ")
                .append("               , case when k.reply_type = '4' then 1 else 0 end success_quantity ")
                .append("               , case when k.reply_type = '1' then 1 else 0 end invalid_ez_quantity ")
                .append("               , case when k.reply_type = '2' then 1 else 0 end invalid_sub_type_quantity ")
                .append("               , case when k.reply_type = '3' then 1 else 0 end exists_error_quantity ")
                .append("               , case when k.reply_type = '5' then 1 else 0 end waiting_quantity ")
                .append("               , case when k.reply_type = '6' then 1 else 0 end syntax_error_quantity ")
                .append("          from kpp_sms@mkitdw_qlud2_dbl k, dealer_properties@mkitdw_qlud2_dbl d, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("          where r.dealer_id = d.dealer_id ")
                .append("              and k.calling_number = substr(d.properties_value, 3) ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("      and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("      and r.district_Id = :district_Id ");
        }
        if(properties.get("hangMucCode") != null){
            mainQuery.append("      and upper(k.code) = upper(:serviceTypeCode) ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and k.issue_datetime >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and k.issue_datetime < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("      ) ")
                .append("       group by branch_name, district_name, service_type ")
                .append("       order by branch_name, district_name, service_type ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("hangMucCode") != null){
            query.setParameter("serviceTypeCode", properties.get("hangMucCode").toString());
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", Long.valueOf(properties.get("district_Id").toString()));
        }
        if(properties.get("hangMucCode") != null){
            countQuery.setParameter("serviceTypeCode", properties.get("hangMucCode").toString());
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
    public Object[] baoCaoTinNhan_kpp_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select branch_name, service_type as hangMuc ")
                .append("       , sum(success_quantity) success_quantity ")
                .append("       , sum(invalid_ez_quantity) invalid_ez_quantity ")
                .append("       , sum(invalid_sub_type_quantity) invalid_sub_type_quantity ")
                .append("       , sum(exists_error_quantity) exists_error_quantity ")
                .append("       , sum(waiting_quantity) waiting_quantity ")
                .append("       , sum(syntax_error_quantity) syntax_error_quantity ")
                .append("  from (select r.branch_name, r.district_name, r.dealer_code, r.dealer_name, d.properties_value ")
                .append("               , (select name from ap_domain@mkitdw_qlud2_dbl where code = upper(k.service_type) and type = 'SMS_SERVICE_TYPE') service_type ")
                .append("               , case when k.reply_type = '4' then 1 else 0 end success_quantity ")
                .append("               , case when k.reply_type = '1' then 1 else 0 end invalid_ez_quantity ")
                .append("               , case when k.reply_type = '2' then 1 else 0 end invalid_sub_type_quantity ")
                .append("               , case when k.reply_type = '3' then 1 else 0 end exists_error_quantity ")
                .append("               , case when k.reply_type = '5' then 1 else 0 end waiting_quantity ")
                .append("               , case when k.reply_type = '6' then 1 else 0 end syntax_error_quantity ")
                .append("          from kpp_sms@mkitdw_qlud2_dbl k, dealer_properties@mkitdw_qlud2_dbl d, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("          where r.dealer_id = d.dealer_id ")
                .append("              and k.calling_number = substr(d.properties_value, 3) ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("      and r.branch_id = :branch_Id ");
        }
        if(properties.get("hangMucCode") != null){
            mainQuery.append("      and upper(k.code) = upper(:serviceTypeCode) ");
        }
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and k.issue_datetime >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and k.issue_datetime < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("      ) ")
                .append("       group by branch_name, service_type ")
                .append("       order by branch_name, service_type ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("hangMucCode") != null){
            query.setParameter("serviceTypeCode", properties.get("hangMucCode").toString());
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("hangMucCode") != null){
            countQuery.setParameter("serviceTypeCode", properties.get("hangMucCode").toString());
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
    public Object[] baoCaoCanhBao_kpp_theoDBH(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select r.branch_name, r.district_name, r.dealer_code, r.dealer_name, extract(month from cycle) as chuKy, item_name ")
                .append("           , nvl(amount, 0) as soPS ")
                .append("           , nvl(prom_point, 0) as soDiemDaDat ")
                .append("           , min_point_per_cycle soPhaiDat ")
                .append("           , ( select param_2 from thread_process@mkitdw_qlud2_dbl where thread_name = 'SUM_DEALER_ACTION_DAILY') collection_date ")
                .append("           , ( select last_day (cycle) - param_2 from thread_process@mkitdw_qlud2_dbl where thread_name = 'SUM_DEALER_ACTION_DAILY') remain_days ")
                .append("  from (   select d.dealer_id, p.item_name, d.amount, d.prom_point, p.min_point_per_cycle, cycle ")
                .append("           from dealer_account@mkitdw_qlud2_dbl d, prom_items@mkitdw_qlud2_dbl p ")
                .append("           where 1 = 1 ");
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and d.cycle >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and d.cycle < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("          and d.item_id = p.item_id and cycle = trunc(sysdate, 'month')) dp ")
                .append("       , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("  where dp.dealer_id(+) = r.dealer_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("      and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("      and r.district_Id = :district_Id ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append("      and r.dealer_Id = :dealer_Id ");
        }
        mainQuery.append("  order by branch_name, district_name, dealer_name, cycle, item_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", properties.get("district_Id").toString());
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", properties.get("dealer_Id").toString());
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", properties.get("district_Id").toString());
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", properties.get("dealer_Id").toString());
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
    public Object[] baoCaoCanhBao_kpp_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select r.branch_name, r.district_name, extract(month from cycle) as chuKy, item_name ")
                .append("           , nvl(sum(amount), 0) as soPS ")
                .append("           , nvl(sum(prom_point), 0) as soDiemDaDat ")
                .append("           , sum(min_point_per_cycle) as soPhaiDat ")
                .append("           , ( select last_day (cycle) - param_2 from thread_process@mkitdw_qlud2_dbl where thread_name = 'SUM_DEALER_ACTION_DAILY') remain_days ")
                .append("  from (   select d.dealer_id, p.item_name, d.amount, d.prom_point, p.min_point_per_cycle, cycle ")
                .append("           from dealer_account@mkitdw_qlud2_dbl d, prom_items@mkitdw_qlud2_dbl p ")
                .append("           where 1 = 1 ");
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and d.cycle >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and d.cycle < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("          and d.item_id = p.item_id and cycle = trunc(sysdate, 'month')) dp ")
                .append("       , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("  where dp.dealer_id(+) = r.dealer_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("      and r.branch_id = :branch_Id ");
        }
        if(properties.get("district_Id") != null){
            mainQuery.append("      and r.district_Id = :district_Id ");
        }
        mainQuery.append(" group by branch_name, district_name, cycle, item_name ")
                .append("  order by branch_name, district_name, cycle, item_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            query.setParameter("district_Id", properties.get("district_Id").toString());
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("district_Id") != null){
            countQuery.setParameter("district_Id", properties.get("district_Id").toString());
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
    public Object[] baoCaoCanhBao_kpp_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select r.branch_name, extract(month from cycle) as chuKy, item_name ")
                .append("           , nvl(sum(amount), 0) as soPS ")
                .append("           , nvl(sum(prom_point), 0) as soDiemDaDat ")
                .append("           , sum(min_point_per_cycle) as soPhaiDat ")
                .append("           , ( select last_day (cycle) - param_2 from thread_process@mkitdw_qlud2_dbl where thread_name = 'SUM_DEALER_ACTION_DAILY') remain_days ")
                .append("  from (   select d.dealer_id, p.item_name, d.amount, d.prom_point, p.min_point_per_cycle, cycle ")
                .append("           from dealer_account@mkitdw_qlud2_dbl d, prom_items@mkitdw_qlud2_dbl p ")
                .append("           where 1 = 1 ");
        if(properties.get("fromDateTime") != null){
            mainQuery.append("      and d.cycle >= to_date(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(properties.get("toDateTime") != null){
            mainQuery.append("      and d.cycle < (to_date(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        mainQuery.append("          and d.item_id = p.item_id and cycle = trunc(sysdate, 'month')) dp ")
                .append("       , retail_dealer@mkitdw_qlud2_dbl r ")
                .append("  where dp.dealer_id(+) = r.dealer_id ");
        if(properties.get("branch_Id") != null){
            mainQuery.append("      and r.branch_id = :branch_Id ");
        }
        mainQuery.append(" group by branch_name, cycle, item_name ")
                .append("  order by branch_name, cycle, item_name ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branch_Id") != null){
            query.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
        }
        if(properties.get("fromDateTime") != null){
            query.setParameter("fromDateTime", properties.get("fromDateTime").toString());
        }
        if(properties.get("toDateTime") != null){
            query.setParameter("toDateTime", properties.get("toDateTime").toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(branch_name) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branch_Id") != null){
            countQuery.setParameter("branch_Id", Long.valueOf(properties.get("branch_Id").toString()));
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
    public Object[] getWarningInfo(String soEZ, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        soEZ = soEZ.replace(Constants.PREFIX_KPP_USERNAME, "");
        soEZ = CommonUtil.removeCountryCode(soEZ);
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" select extract(month from cycle) as chuKy, item_name ")
                .append("           , nvl(amount, 0) as soPS ")
                .append("           , nvl(prom_point, 0) as soDiemDaDat ")
                .append("           , min_point_per_cycle soPhaiDat ")
                .append("           , ( select param_2 from thread_process@mkitdw_qlud2_dbl ")
                .append("               where thread_name = 'SUM_DEALER_ACTION_DAILY' ) collection_date ")
                .append("           , ( select last_day (cycle) - param_2 from thread_process@mkitdw_qlud2_dbl ")
                .append("               where thread_name = 'SUM_DEALER_ACTION_DAILY' ) remain_days ")
                .append("  from (   select d.dealer_id, p.item_name, d.amount, d.prom_point, p.min_point_per_cycle, cycle ")
                .append("           from dealer_account@mkitdw_qlud2_dbl d, prom_items@mkitdw_qlud2_dbl p ")
                .append("           WHERE 1 = 1 and d.item_id = p.item_id ")
                .append("               and cycle = trunc(sysdate, 'month') ) dp, retail_dealer@mkitdw_qlud2_dbl r ")
                .append("  where dp.dealer_id(+) = r.dealer_id ")
                .append("       and exists (select 1 from dealer_properties@mkitdw_qlud2_dbl dp where r.dealer_Id = dp.dealer_Id and dp.properties_value like :soEZ) ")
                .append("  order by cycle, item_name  ");

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select * from ( ").append(mainQuery).append(" ) ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("soEZ", "%" + soEZ + "%");
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder();
        sqlCountClause.append(" select count(*) from ( ").append(mainQuery).append(" ) ");
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("soEZ", "%" + soEZ + "%");
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }

    @Override
    public List findServiceTypeList() {
        StringBuilder sqlQueruClause = new StringBuilder();
        sqlQueruClause.append(" select code, name from ap_domain@mkitdw_qlud2_dbl where type = 'SMS_SERVICE_TYPE' order by name ");
        Query query = entityManager.createNativeQuery(sqlQueruClause.toString());
        return query.getResultList();
    }

    @Override
    public String[] validateMaPhieuNeedToExchanged(String[] maPhieuList) {
        List<String> maPhieus = new ArrayList<String>();
        for(String maPhieu : maPhieuList){
            maPhieus.add(maPhieu);
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select ma_phieu from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl where ma_phieu in (:maPhieus) and da_doi_qua = :da_doi_qua_status ");
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
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi9(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select input_type, sum(soMaPhieuDaPhatSinh) ")
                    .append("   from ( select ds.thue_bao ")
                    .append("                   , ds.input_type ")
                    .append("                   , count(mp.ma_phieu) as soMaPhieuDaPhatSinh ")
                    .append("           from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl mp ")
                    .append("           inner join ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl ds on ds.thue_bao = mp.thue_bao ")
                    .append("           where trim(ds.input_type) in ('truoc', 'sau') ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append("             and ds.insert_date >= to_date(substr(:fromDateTimeThamGia, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia != null){
            sqlQueryClause.append("             and ds.insert_date < (to_date(substr(:toDateTimeThamGia, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append("         group by ds.thue_bao, ds.input_type ")
                    .append("           ) ")
                    .append("   group by input_type  ");

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
    public List baoCaoDanhGiaKetQuaTHCT_tieuChi10(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("  select input_type, sum(soMaPhieuDaDoiQua) ")
                .append("   from ( select ds.thue_bao ")
                .append("                   , ds.input_type ")
                .append("                   , count(mp.ma_phieu) as soMaPhieuDaDoiQua ")
                .append("           from ct_tich_diem_2014_ma_phieu@mkitdw_qlud2_dbl mp ")
                .append("           inner join ct_tich_diem_2014_ds_tham_gia@mkitdw_qlud2_dbl ds on ds.thue_bao = mp.thue_bao ")
                .append("           where trim(ds.input_type) in ('truoc', 'sau') ")
                .append("                       and mp.da_doi_qua = '2' ");
        if(fromDateTimeThamGia != null){
            sqlQueryClause.append("             and ds.insert_date >= to_date(substr(:fromDateTimeThamGia, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTimeThamGia != null){
            sqlQueryClause.append("             and ds.insert_date < (to_date(substr(:toDateTimeThamGia, 1, 10), 'YYYY-MM-DD') + 1) ");
        }
        sqlQueryClause.append("         group by ds.thue_bao, ds.input_type ")
                .append("           ) ")
                .append("   group by input_type  ");

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
    public Object[] findDealerInfoByDealerId(Long dealer_id) throws ObjectNotFoundException{
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select dealer_Id, dealer_code, dealer_name from Retail_Dealer@mkitdw_qlud2_dbl where dealer_Id = :dealer_Id ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_id);
        List resultSet = query.getResultList();
        if(resultSet != null && resultSet.size() > 0){
            return (Object[])resultSet.get(0);
        }else{
            throw new ObjectNotFoundException("Object not found for Dealer_Id: " + dealer_id);
        }
    }

    @Override
    public Object[] findDealerAccountByDealerIdAndCycle(Long dealerId, Integer cycle) throws ObjectNotFoundException{
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select dealer_Id, cycle, nvl(prom_amount, 0) from kpp_dealer_account@mkitdw_qlud2_dbl where dealer_Id = :dealer_Id and extract(Month from cycle) = :cycle ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealerId);
        query.setParameter("cycle", cycle);
        List resultSet = query.getResultList();
        if(resultSet != null && resultSet.size() > 0){
            return (Object[])resultSet.get(0);
        }else{
            throw new ObjectNotFoundException("Object not found for Dealer_Id: " + dealerId + " and cycle: " + cycle);
        }
    }

    @Override
    public void updateDealerAccountExchangeMoneyStatusByDealerIdAndCycle(Long dealer_id, Integer cycle) throws ObjectNotFoundException {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" update kpp_dealer_account@mkitdw_qlud2_dbl set exchange_money_status = 1 where dealer_Id = :dealer_Id and extract(Month from cycle) = :cycle ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("dealer_Id", dealer_id);
        query.setParameter("cycle", cycle);
        int countAffected = query.executeUpdate();
        if(countAffected == 0){
            throw new ObjectNotFoundException("Object not found for Dealer Account to update Cycle Lock Status by dealer_Id: " + dealer_id + " and cycle: " + cycle);
        }
    }

    @Override
    public Integer getTotalSoMaDuThuongHienTaiKPP(String phoneNumber) {
        phoneNumber = phoneNumber.replace(Constants.PREFIX_KPP_USERNAME, "");
        phoneNumber = CommonUtil.removeCountryCode(phoneNumber);
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select nvl(sum(dac.ticket_quantity), 0) from dealer_account_action@mkitdw_qlud2_dbl dac ")
                    .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_id = dac.dealer_id ")
                    .append("   inner join dealer_properties@mkitdw_qlud2_dbl dp on dp.dealer_id = rd.dealer_id ")
                    .append("   where dp.properties_value like :thue_bao ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("thue_bao", "%" + phoneNumber + "%");
        return Integer.valueOf(query.getSingleResult().toString());
    }
}
