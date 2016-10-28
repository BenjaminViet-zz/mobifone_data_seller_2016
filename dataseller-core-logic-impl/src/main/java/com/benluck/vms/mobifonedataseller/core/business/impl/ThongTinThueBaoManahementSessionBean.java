package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.ThongTinThueBaoManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ThongTinThueBaoMaPhieuDTO;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/22/14
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ThongTinThueBaoManahementSessionEJB")
public class ThongTinThueBaoManahementSessionBean implements ThongTinThueBaoManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    public ThongTinThueBaoManahementSessionBean() {
    }

    @Override
    public Integer getSoDiemHienTai_tdcg(String thue_bao) {
        return  this.ctTichDiemLocalBean.getCurrentPointTotal(thue_bao);
    }

    @Override
    public Integer getSoDiemHienTai_qStudent(String thue_bao) {
        return this.thueBaoPhatTrienMoiLocalBean.getCurrentPointTotal(thue_bao);
    }

    @Override
    public Integer getSoPhieuDaDoi_tdcg(String thue_bao) {
        return  this.ctTichDiemLocalBean.getTotalOfExchangedMaPhieu(thue_bao);
    }

    @Override
    public Integer getSoPhieuDaDoi_qStudent(String thue_bao) {
        return  this.thueBaoPhatTrienMoiLocalBean.countExchangedMaPhieus(thue_bao);
    }

    @Override
    public Object[] traCuuThongTinThueBao_tdcg(String thue_bao, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.searchSubscriberInfo(thue_bao, firstItem, maxPageItems, sortExpression, sortDirection);
        List<ThongTinThueBaoMaPhieuDTO> dtoList = new ArrayList<ThongTinThueBaoMaPhieuDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            ThongTinThueBaoMaPhieuDTO thongTinThueBaoMaPhieuDTO = new ThongTinThueBaoMaPhieuDTO();
            thongTinThueBaoMaPhieuDTO.setMa_phieu(tmpArr[0] != null ? tmpArr[0].toString() : null);
            thongTinThueBaoMaPhieuDTO.setNgay_ps(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);
            thongTinThueBaoMaPhieuDTO.setDa_doi_qua(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            thongTinThueBaoMaPhieuDTO.setNgay_doi_qua(tmpArr[3] != null ? Timestamp.valueOf(tmpArr[3].toString()) : null);
            thongTinThueBaoMaPhieuDTO.setCua_hang_doi_qua_name(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dtoList.add(thongTinThueBaoMaPhieuDTO);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] traCuuThongTinThueBao_qStudent(String thue_bao, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.searchSubscriberInfo(thue_bao, firstItem, maxPageItems, sortExpression, sortDirection);
        List<ThongTinThueBaoMaPhieuDTO> dtoList = new ArrayList<ThongTinThueBaoMaPhieuDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            ThongTinThueBaoMaPhieuDTO thongTinThueBaoMaPhieuDTO = new ThongTinThueBaoMaPhieuDTO();
            thongTinThueBaoMaPhieuDTO.setMa_phieu(tmpArr[0] != null ? tmpArr[0].toString() : null);
            thongTinThueBaoMaPhieuDTO.setNgay_ps(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);
            thongTinThueBaoMaPhieuDTO.setDa_doi_qua(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            thongTinThueBaoMaPhieuDTO.setNgay_doi_qua(tmpArr[3] != null ? Timestamp.valueOf(tmpArr[3].toString()) : null);
            thongTinThueBaoMaPhieuDTO.setCua_hang_doi_qua_name(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dtoList.add(thongTinThueBaoMaPhieuDTO);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] search_tbptm(String thue_bao, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.searchUserInfo(thue_bao, sortExpression, sortDirection, firstItems, maxPageItems);
//        List<ThongTinThueBaoMaPhieuDTO> dtoList = new ArrayList<ThongTinThueBaoMaPhieuDTO>();
//        for (Object object : (List)resultObject[1]){
//            Object[] tmpArr = (Object[])object;
//            ThongTinThueBaoMaPhieuDTO thongTinThueBaoMaPhieuDTO = new ThongTinThueBaoMaPhieuDTO();
//            thongTinThueBaoMaPhieuDTO.setMa_phieu(tmpArr[0] != null ? tmpArr[0].toString() : null);
//            thongTinThueBaoMaPhieuDTO.setNgay_ps(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);
//            thongTinThueBaoMaPhieuDTO.setDa_doi_qua(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
//            thongTinThueBaoMaPhieuDTO.setNgay_doi_qua(tmpArr[3] != null ? Timestamp.valueOf(tmpArr[3].toString()) : null);
//            thongTinThueBaoMaPhieuDTO.setCua_hang_doi_qua_name(tmpArr[4] != null ? tmpArr[4].toString() : null);
//            dtoList.add(thongTinThueBaoMaPhieuDTO);
//        }
//        resultObject[1] = dtoList;
        return resultObject;
    }
}
