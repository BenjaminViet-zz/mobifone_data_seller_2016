package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/11/14
 * Time: 12:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO implements Serializable{
    private static final long serialVersionUID = -4245691037897863443L;

    private Timestamp date;
    private Double doanhThu_truoc = new Double(0);
    private Double doanhThu_sau = new Double(0);

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getDoanhThu_truoc() {
        return doanhThu_truoc;
    }

    public void setDoanhThu_truoc(Double doanhThu_truoc) {
        this.doanhThu_truoc = doanhThu_truoc;
    }

    public Double getDoanhThu_sau() {
        return doanhThu_sau;
    }

    public void setDoanhThu_sau(Double doanhThu_sau) {
        this.doanhThu_sau = doanhThu_sau;
    }
}
