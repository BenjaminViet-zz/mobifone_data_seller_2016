package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoSoDiemChoThueBaoDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 5/4/15
 * Time: 9:32 AM
 * To change this template use File | Settings | File Templates.
 */
    public class BaoCaoSoDiemChoThueBaoCommand extends AbstractCommand<BaoCaoSoDiemChoThueBaoDTO> {
    public BaoCaoSoDiemChoThueBaoCommand(){
        this.pojo = new BaoCaoSoDiemChoThueBaoDTO();
    }

    private Integer soDiemTo;
    private Integer soDiemFrom;

    public Integer getSoDiemTo() {
        return soDiemTo;
    }

    public void setSoDiemTo(Integer soDiemTo) {
        this.soDiemTo = soDiemTo;
    }

    public Integer getSoDiemFrom() {
        return soDiemFrom;
    }

    public void setSoDiemFrom(Integer soDiemFrom) {
        this.soDiemFrom = soDiemFrom;
    }
}
