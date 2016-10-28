package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromItemsDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/7/14
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PromItemsManagementLocalBean {
    /**
     * Fetch all Prom Items.
     * @return a list of DTO objects which mapped to rows of Prom Items.
     */
    List<PromItemsDTO> findAll();
}
