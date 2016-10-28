package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ThongTinThueBaoDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/6/15
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThongTinThueBao2015Command extends AbstractCommand<ThongTinThueBaoDTO> {

    public ThongTinThueBao2015Command(){
        this.pojo = new ThongTinThueBaoDTO();
    }

    private Date registerDateFrom;
    private Date registerDateTo;

    public Date getRegisterDateFrom() {
        return registerDateFrom;
    }

    public void setRegisterDateFrom(Date registerDateFrom) {
        this.registerDateFrom = registerDateFrom;
    }

    public Date getRegisterDateTo() {
        return registerDateTo;
    }

    public void setRegisterDateTo(Date registerDateTo) {
        this.registerDateTo = registerDateTo;
    }
}
