package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ThongTinThueBaoMaPhieuDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/22/14
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThongTinThueBaoCommand extends AbstractCommand<ThongTinThueBaoMaPhieuDTO> {
    public ThongTinThueBaoCommand(){
        this.pojo = new ThongTinThueBaoMaPhieuDTO();
    }

    private String thue_bao;
    private Integer soDiemHienTai;
    private Integer soPhieuDaDoi;
    private String message;
    private String warning;

    public String getThue_bao() {
        return thue_bao;
    }

    public void setThue_bao(String thue_bao) {
        this.thue_bao = thue_bao;
    }

    public Integer getSoDiemHienTai() {
        return soDiemHienTai;
    }

    public void setSoDiemHienTai(Integer soDiemHienTai) {
        this.soDiemHienTai = soDiemHienTai;
    }

    public Integer getSoPhieuDaDoi() {
        return soPhieuDaDoi;
    }

    public void setSoPhieuDaDoi(Integer soPhieuDaDoi) {
        this.soPhieuDaDoi = soPhieuDaDoi;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }
}
