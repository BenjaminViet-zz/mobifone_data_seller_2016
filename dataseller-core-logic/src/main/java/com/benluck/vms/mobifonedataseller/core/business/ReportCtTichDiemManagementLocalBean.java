package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoQuanLyTinNhanDTO;

import javax.ejb.Local;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/22/14
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface ReportCtTichDiemManagementLocalBean {
    /**
     * Fetch CT tich diem cuoc goi
     * @param loaiTinNhanFromKHCN
     * @param loaiTinNhanFromVMS
     * @param fromDateTime
     * @param toDateTime
     * @return
     */
    List<BaoCaoQuanLyTinNhanDTO> baoCaoQuanLyTinNhan_tdcg(String loaiTinNhanFromKHCN, String loaiTinNhanFromVMS, Timestamp fromDateTime, Timestamp toDateTime);

    /**
     * Fetch CT Q-Student Q-Teen
     * @param loaiTinNhanFromKHCN
     * @param loaiTinNhanFromVMS
     * @param fromDateTime
     * @param toDateTime
     * @return
     */
    List<BaoCaoQuanLyTinNhanDTO> baoCaoQuanLyTinNhan_qStudent(String loaiTinNhanFromKHCN, String loaiTinNhanFromVMS, Timestamp fromDateTime, Timestamp toDateTime);
}
