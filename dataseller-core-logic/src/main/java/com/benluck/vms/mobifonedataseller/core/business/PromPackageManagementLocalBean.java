package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 2/12/15
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PromPackageManagementLocalBean {

    /**
     * Fetch all PromPackage from DB.
     * @return A list of PromPackageDTO.
     */
    List<PromPackageDTO> findAll();
}
