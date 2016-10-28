package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ServiceTypeDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/19/14
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceTypeManagementLocalBean {

    /**
     * Find all services has type is "SMS_SERVICE_TYPE"
     * @return A list of all matched services.
     */
    List<ServiceTypeDTO> findServiceTypeList();
}
