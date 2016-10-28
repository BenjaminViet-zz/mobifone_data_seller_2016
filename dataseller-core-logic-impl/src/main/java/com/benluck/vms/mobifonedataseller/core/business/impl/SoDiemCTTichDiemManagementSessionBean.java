package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.SoDiemCTTichDiemManagementlocalBean;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/13/14
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "SoDiemCTTichDiemManagementSessionEJB")
public class SoDiemCTTichDiemManagementSessionBean implements SoDiemCTTichDiemManagementlocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    public SoDiemCTTichDiemManagementSessionBean() {
    }

    @Override
    public Object[] statisticCumulativeScoresByDate_tdcg(Map<String, Object> properties, String soThueBao, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        String sqlQueryClause = buildWhereClause(properties, sortExpression, sortDirection);
        return this.ctTichDiemLocalBean.statisticCumulativeScoresByDate(soThueBao, sqlQueryClause, offset, limit);
    }

    @Override
    public Object[] statisticCumulativeScoresByDate_qStudent(Map<String, Object> properties, String soThueBao, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        String sqlQueryClause = buildWhereClause(properties, sortExpression, sortDirection);
        return this.thueBaoPhatTrienMoiLocalBean.statisticCumulativeScoresByDate(soThueBao, sqlQueryClause, offset, limit);
    }

    @Override
    public Object[] statisticCumulativeScoresByMonth_tdcg(String soThueBao) {
        return this.ctTichDiemLocalBean.statisticCumulativeScoresByMonth(soThueBao);
    }

    @Override
    public Object[] statisticCumulativeScoresByMonth_qStudent(String phoneNumber) {
        return this.thueBaoPhatTrienMoiLocalBean.statisticCumulativeScoresByMonth(phoneNumber);
    }

    @Override
    public Boolean checkIfAlreadyRegistered_tdcg(String phoneNumber)throws ObjectNotFoundException{
        return this.ctTichDiemLocalBean.checkIfAlreadyRegistered(phoneNumber);
    }

    @Override
    public Boolean checkIfAlreadyRegistered_qStudent(String phoneNumber) throws ObjectNotFoundException {
        return this.thueBaoPhatTrienMoiLocalBean.checkIfAlreadyRegister(phoneNumber);
    }

    @Override
    public Integer getCurrentScoreTotal_tdcg(String phoneNumber) {
        return this.ctTichDiemLocalBean.getCurrentPointTotal(phoneNumber);
    }

    @Override
    public Integer getCurrentScoreTotal_qStudent(String phoneNumber) {
        return this.thueBaoPhatTrienMoiLocalBean.getCurrentPointTotal(phoneNumber);
    }

    @Override
    public void exchangeGift_tdcg(String phoneNumber) throws ObjectNotFoundException {
        Integer totalPhieuChuaDoi = this.ctTichDiemLocalBean.countSoPhieuChuaDoi(phoneNumber);
        if(totalPhieuChuaDoi > 0){
            this.ctTichDiemLocalBean.updateExchangeStatus4Maphieu(phoneNumber);
        }else{
            throw new ObjectNotFoundException("0 ma phieu chua doi duoc tim thay cho thue bao so: " + phoneNumber);
        }
    }

    @Override
    public void exchangeGift_qStudent(String phoneNumber) throws ObjectNotFoundException {
        Integer totalPhieuChuaDoi = this.ctTichDiemLocalBean.countSoPhieuChuaDoi(phoneNumber);
        if(totalPhieuChuaDoi > 0){
            thueBaoPhatTrienMoiLocalBean.updateMaphieuStatus_tdcg(phoneNumber);
        }else{
            throw new ObjectNotFoundException("0 ma phieu chua doi duoc tim thay cho thue bao so: " + phoneNumber);
        }
    }

    private String buildWhereClause(Map<String, Object> properties, String sortExpression, String sortDirection){
        StringBuilder whereClause = new StringBuilder("where t.status = '1' and t.thue_bao like :soThueBao");
        if(properties.get("fromDate") != null){
            whereClause.append(" and trunc(t.ngay_ps) >= to_date(substr('").append(properties.get("fromDate").toString()).append("',1,10) ")
                    .append(", '").append(Constants.DB_DATE_FORMAT).append("')");
        }
        if(properties.get("toDate") != null){
            whereClause.append(" and trunc(t.ngay_ps) <= to_date(substr('").append(properties.get("toDate").toString()).append("',1,10) ")
                    .append(", '").append(Constants.DB_DATE_FORMAT).append("') ");

        }
        whereClause.append(" group by t.thue_bao, t.ngay_ps, t.type_input ");
        if(StringUtils.isNotBlank(sortExpression) && StringUtils.isNotBlank(sortDirection)){
            whereClause.append(" order by t.").append(sortExpression).append(sortDirection.equals(Constants.SORT_ASC) ? " asc " : "desc");
        }else{
            whereClause.append(" order by t.ngay_ps ");
        }
        return whereClause.toString();
    }
}
