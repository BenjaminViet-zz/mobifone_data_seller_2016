package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchMappingDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/4/15
 * Time: 9:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class BranchMappingCommand extends AbstractCommand<BranchMappingDTO> {
    public BranchMappingCommand(){
        this.pojo = new BranchMappingDTO();
    }

    private List<BranchMappingDTO> branchMappingList = LazyList.decorate(
            new ArrayList(), FactoryUtils.instantiateFactory(BranchMappingDTO.class)
    );

    public List<BranchMappingDTO> getBranchMappingList() {
        return branchMappingList;
    }

    public void setBranchMappingList(List<BranchMappingDTO> branchMappingList) {
        this.branchMappingList = branchMappingList;
    }
}
