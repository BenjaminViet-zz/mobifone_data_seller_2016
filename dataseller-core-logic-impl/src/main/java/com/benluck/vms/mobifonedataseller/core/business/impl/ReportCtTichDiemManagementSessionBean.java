package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.ReportCtTichDiemManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoQuanLyTinNhanDTO;
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
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ReportCtTichDiemManagementSessionEJB")
public class ReportCtTichDiemManagementSessionBean implements ReportCtTichDiemManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    public ReportCtTichDiemManagementSessionBean() {
    }

    @Override
    public List<BaoCaoQuanLyTinNhanDTO> baoCaoQuanLyTinNhan_tdcg(String loaiTinNhanFromKHCN, String loaiTinNhanFromVMS, Timestamp fromDateTime, Timestamp toDateTIme) {
        List resultSet  = this.ctTichDiemLocalBean.messageManagementReport(loaiTinNhanFromKHCN, loaiTinNhanFromVMS, fromDateTime, toDateTIme);
        List<BaoCaoQuanLyTinNhanDTO> dtoList = new ArrayList<BaoCaoQuanLyTinNhanDTO>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            BaoCaoQuanLyTinNhanDTO dto = new BaoCaoQuanLyTinNhanDTO();
            dto.setDoiTuong(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setThoiGianNhanTin(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);
            dto.setNoiDungTinNhan(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setSoLuongTB(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setSoLuongTinNhan(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setSoLuongTinNhanKhongThanhCong(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setSoLuongTinNhanThanhCong(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dtoList.add(dto);
        }
        return  dtoList;
    }

    @Override
    public List<BaoCaoQuanLyTinNhanDTO> baoCaoQuanLyTinNhan_qStudent(String loaiTinNhanFromKHCN, String loaiTinNhanFromVMS, Timestamp fromDateTime, Timestamp toDateTime) {
        List resultSet  = this.thueBaoPhatTrienMoiLocalBean.getMessageManagementReportData(loaiTinNhanFromKHCN, loaiTinNhanFromVMS, fromDateTime, toDateTime);
        List<BaoCaoQuanLyTinNhanDTO> dtoList = new ArrayList<BaoCaoQuanLyTinNhanDTO>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            BaoCaoQuanLyTinNhanDTO dto = new BaoCaoQuanLyTinNhanDTO();
            dto.setDoiTuong(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setThoiGianNhanTin(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);
            dto.setNoiDungTinNhan(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setSoLuongTB(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setSoLuongTinNhan(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setSoLuongTinNhanKhongThanhCong(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setSoLuongTinNhanThanhCong(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dtoList.add(dto);
        }
        return  dtoList;
    }
}
