package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.PackageDataCodeGenDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class PackageDataCodeGenCommand extends AbstractCommand<PackageDataCodeGenDTO>{
    public PackageDataCodeGenCommand(){
        this.pojo = new PackageDataCodeGenDTO();
    }

    private Integer totalPackageDataNotGenerateCardCode;
    private List<PackageDataDTO> packageDataNotGenerateCardCodeListResult;

    public Integer getTotalPackageDataNotGenerateCardCode() {
        return totalPackageDataNotGenerateCardCode;
    }

    public void setTotalPackageDataNotGenerateCardCode(Integer totalPackageDataNotGenerateCardCode) {
        this.totalPackageDataNotGenerateCardCode = totalPackageDataNotGenerateCardCode;
    }

    public List<PackageDataDTO> getPackageDataNotGenerateCardCodeListResult() {
        return packageDataNotGenerateCardCodeListResult;
    }

    public void setPackageDataNotGenerateCardCodeListResult(List<PackageDataDTO> packageDataNotGenerateCardCodeListResult) {
        this.packageDataNotGenerateCardCodeListResult = packageDataNotGenerateCardCodeListResult;
    }
}
