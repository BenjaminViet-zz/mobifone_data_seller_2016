package com.benluck.vms.mobifonedataseller.webapp.RestModel;

import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 2/21/17
 * Time: 12:59
 * To change this template use File | Settings | File Templates.
 */
public class PackageListRestBeanUtil {
    public static PackageListRestModel dto2Model(List<PackageDataDTO> packageDataDTOList){
        PackageListRestModel model = new PackageListRestModel();
        if (packageDataDTOList != null && packageDataDTOList.size() > 0){
            List<PackageRestModel> packageRestModelList = new ArrayList<PackageRestModel>();
            for (PackageDataDTO packageDataDTO : packageDataDTOList){
                PackageRestModel packageRestModel = new PackageRestModel();
                packageRestModel.setPackageName(packageDataDTO.getName());
                packageRestModel.setPrice(packageDataDTO.getValue());
                packageRestModel.setVolumn(packageDataDTO.getVolume().replace("{delimiter_line}", "|"));
                packageRestModel.setDuration(packageDataDTO.getDuration());
                packageRestModel.setExtend(packageDataDTO.getNumberOfExtend());
                packageRestModelList.add(packageRestModel);
            }
            model.setPackageList(packageRestModelList);
        }
        return model;
    }
}
