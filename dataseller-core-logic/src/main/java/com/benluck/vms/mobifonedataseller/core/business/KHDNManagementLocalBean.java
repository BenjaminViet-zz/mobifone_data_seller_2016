package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;

import javax.ejb.Local;
import java.util.List;

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
}
