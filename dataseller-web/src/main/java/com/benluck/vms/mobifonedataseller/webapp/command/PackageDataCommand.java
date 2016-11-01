package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/1/16
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class PackageDataCommand extends AbstractCommand<PackageDataDTO>{
    public PackageDataCommand(){
        this.pojo = new PackageDataDTO();
    }

}
