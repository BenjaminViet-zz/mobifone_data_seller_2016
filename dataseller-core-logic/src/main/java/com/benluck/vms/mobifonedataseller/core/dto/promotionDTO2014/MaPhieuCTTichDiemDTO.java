package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class MaPhieuCTTichDiemDTO implements Serializable {

    private String thueBao;
    private String maPhieu;
    private Timestamp ngayPS;
    private String typeInput;
    private Integer tinhTrang;
    private Timestamp ngayDoiQua;
    private String userName;
    private String shopCode;

    public String getThueBao() {
        return thueBao;
    }

    public void setThueBao(String thueBao) {
        this.thueBao = thueBao;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public Timestamp getNgayPS() {
        return ngayPS;
    }

    public void setNgayPS(Timestamp ngayPS) {
        this.ngayPS = ngayPS;
    }

    public String getTypeInput() {
        return typeInput;
    }

    public void setTypeInput(String typeInput) {
        this.typeInput = typeInput;
    }

    public Integer getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(Integer tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Timestamp getNgayDoiQua() {
        return ngayDoiQua;
    }

    public void setNgayDoiQua(Timestamp ngayDoiQua) {
        this.ngayDoiQua = ngayDoiQua;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
}
