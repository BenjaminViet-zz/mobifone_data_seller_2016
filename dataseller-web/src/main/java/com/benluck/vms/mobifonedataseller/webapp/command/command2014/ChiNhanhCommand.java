package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/19/14
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChiNhanhCommand extends AbstractCommand<ChiNhanhDTO> {
    public ChiNhanhCommand(){
        this.pojo = new ChiNhanhDTO();
    }

    private List<ChiNhanhDTO> chiNhanhList = LazyList.decorate(
            new ArrayList(), FactoryUtils.instantiateFactory(ChiNhanhDTO.class)
    );

    public List<ChiNhanhDTO> getChiNhanhList() {
        return chiNhanhList;
    }

    public void setChiNhanhList(List<ChiNhanhDTO> chiNhanhList) {
        this.chiNhanhList = chiNhanhList;
    }
}
