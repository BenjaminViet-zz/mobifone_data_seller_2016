package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoSoDiemChoThueBaoManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoSoDiemChoThueBaoDTO;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 5/4/15
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoSoDiemChoThueBaoManagementSessionEJB")
public class BaoCaoSoDiem4ThueBaoManagementSessionBean implements BaoCaoSoDiemChoThueBaoManagementLocalBean {
    public BaoCaoSoDiem4ThueBaoManagementSessionBean() {
    }

    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoSoDiemChoThueBao(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoSoDiemChoThueBaoDTO> dtoList = new ArrayList<BaoCaoSoDiemChoThueBaoDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoSoDiemChoThueBaoDTO dto = new BaoCaoSoDiemChoThueBaoDTO();
            dto.setSoEz(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setSoDiem(tmpArr[1] != null ? Integer.valueOf(tmpArr[1].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
