package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/1/16
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface PackageDataManagementLocalBean {

    List<PackageDataDTO> findAll();

    Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems);

}
