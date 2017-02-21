package com.benluck.vms.mobifonedataseller.webapp.RestModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 2/21/17
 * Time: 12:58
 * To change this template use File | Settings | File Templates.
 */
public class PackageListRestModel {
    private List<PackageRestModel> packageList;

    public List<PackageRestModel> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<PackageRestModel> packageList) {
        this.packageList = packageList;
    }
}
