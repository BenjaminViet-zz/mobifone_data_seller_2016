package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 5/4/15
 * Time: 9:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoSoDiemChoThueBaoDTO implements Serializable {
    private static final long serialVersionUID = -2097388709829041336L;

    private String soEz;
    private Integer soDiem;

    public String getSoEz() {
        return soEz;
    }

    public void setSoEz(String soEz) {
        this.soEz = soEz;
    }

    public Integer getSoDiem() {
        return soDiem;
    }

    public void setSoDiem(Integer soDiem) {
        this.soDiem = soDiem;
    }
}
