package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 4/8/15
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO implements Serializable {

    private static final long serialVersionUID = 7377764398157357435L;
    private Timestamp date;
    private Double doanhThu = new Double(0);

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(Double doanhThu) {
        this.doanhThu = doanhThu;
    }
}
