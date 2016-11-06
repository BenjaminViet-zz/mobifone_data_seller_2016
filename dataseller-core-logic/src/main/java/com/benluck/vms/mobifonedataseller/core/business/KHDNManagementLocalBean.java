package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;

import javax.ejb.Local;
import javax.ejb.RemoveException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:36
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface KHDNManagementLocalBean {

    List<KHDNDTO> findAll();

    Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems);

}
