package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.MBDReportGeneralExpenseDTOBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.MBDCostManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.MBDReportDetailExpenseDTO;
import com.benluck.vms.mobifonedataseller.core.dto.MBDReportGeneralExpenseDTO;
import com.benluck.vms.mobifonedataseller.domain.MBDCostEntity;
import com.benluck.vms.mobifonedataseller.session.MBDCostLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "MBDCostManagementSessionEJB")
public class MBDCostManagementSessionBean implements MBDCostManagementLocalBean{

    @EJB
    private MBDCostLocalBean costService;

    public MBDCostManagementSessionBean() {
    }

    @Override
    public Object[] searchGeneralReportDataByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems) {
        Object[] resultObject = this.costService.search4GeneralExpenseReport(properties, sortExpression, sortDirection, firstItem, reportMaxPageItems);
        List listObject = (List)resultObject[1];
        Object[] tmpObjectArr = null;
        List<MBDReportGeneralExpenseDTO> dtoList = new ArrayList<MBDReportGeneralExpenseDTO>();
        for (Object tmpObject : listObject){
            tmpObjectArr = (Object[]) tmpObject;
            MBDReportGeneralExpenseDTO dto = new MBDReportGeneralExpenseDTO();
            dto.setShopCode(tmpObjectArr[0] != null ? tmpObjectArr[0].toString() : "");
            dto.setShopName(tmpObjectArr[1] != null ? tmpObjectArr[1].toString() : "");
            dto.setDevelopmentAmount1(tmpObjectArr[2] != null ? Double.valueOf(tmpObjectArr[2].toString()) : new Double(0));
            dto.setDevelopmentAmount2(tmpObjectArr[3] != null ? Double.valueOf(tmpObjectArr[3].toString()) : new Double(0));
            dto.setDevelopmentAmount3(tmpObjectArr[4] != null ? Double.valueOf(tmpObjectArr[4].toString()) : new Double(0));
            dto.setMaintainAmount1(tmpObjectArr[5] != null ? Double.valueOf(tmpObjectArr[5].toString()) : new Double(0));
            dto.setMaintainAmount2(tmpObjectArr[6] != null ? Double.valueOf(tmpObjectArr[6].toString()) : new Double(0));
            dto.setMaintainAmount3(tmpObjectArr[7] != null ? Double.valueOf(tmpObjectArr[7].toString()) : new Double(0));
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] searchDetailReportDataByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems) {
        Object[] resultObject = this.costService.search4DetailExpenseReport(properties, sortExpression, sortDirection, firstItem, reportMaxPageItems);
        List listObject = (List)resultObject[1];
        Object[] tmpObjectArr = null;
        List<MBDReportDetailExpenseDTO> dtoList = new ArrayList<MBDReportDetailExpenseDTO>();
        for (Object tmpObject : listObject){
            tmpObjectArr = (Object[]) tmpObject;
            MBDReportDetailExpenseDTO dto = new MBDReportDetailExpenseDTO();
            dto.setEmpCode(tmpObjectArr[0] != null ? tmpObjectArr[0].toString() : "");
            dto.setIsdn(tmpObjectArr[1] != null ? tmpObjectArr[1].toString() : "");
            dto.setEmpName(tmpObjectArr[2] != null ? tmpObjectArr[2].toString() : "");
            dto.setMaNVPhatTrien(tmpObjectArr[3] != null ? tmpObjectArr[3].toString() : "");
            dto.setLoaiHM(tmpObjectArr[4] != null ? tmpObjectArr[4].toString() : "");
            dto.setLoaiTB(tmpObjectArr[5] != null ? tmpObjectArr[5].toString() : "");
            dto.setLoaiKH(tmpObjectArr[6] != null ? tmpObjectArr[6].toString() : "");
            dto.setNgayDauNoi(tmpObjectArr[7] != null ? Timestamp.valueOf(tmpObjectArr[7].toString()) : null);
            dto.setNgayNopHoSo(tmpObjectArr[8] != null ? Timestamp.valueOf(tmpObjectArr[8].toString()) : null);
            dto.setTrangThaiChanCat(tmpObjectArr[9] != null ? tmpObjectArr[9].toString() : "");
            dto.setTrangThaiThueBao(tmpObjectArr[10] != null ? tmpObjectArr[10].toString() : "");
            dto.setChuKy(tmpObjectArr[11] != null ? tmpObjectArr[11].toString() : "");
            dto.setHoaHong(tmpObjectArr[12] != null ? Double.valueOf(tmpObjectArr[12].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
