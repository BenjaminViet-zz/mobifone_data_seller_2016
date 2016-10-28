package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.GiftDeliveryThueBaoHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDeliveryThueBaoDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDeliveryThueBaoHistoryDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserDTO;
import com.benluck.vms.mobifonedataseller.domain.GiftDeliveryThueBaoHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.ActionLogLocalBean;
import com.benluck.vms.mobifonedataseller.session.GiftDeliveryThueBaoHistoryLocalBean;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 12:58 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "GiftDeliveryThueBaoHistoryManagementSessionEJB")
public class GiftDeliveryThueBaoHistoryManagementSessionBean implements GiftDeliveryThueBaoHistoryManagementLocalBean{
    @EJB
    private GiftDeliveryThueBaoHistoryLocalBean giftDeliveryThueBaoHistoryLocalBean;
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;
    @EJB
    private ActionLogLocalBean actionLogLocalBean;

    public GiftDeliveryThueBaoHistoryManagementSessionBean() {
    }

    @Override
    public Object[] reportByKHCN_tdcg(GiftDeliveryThueBaoHistoryDTO dto, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        Object[] resultObject = this.giftDeliveryThueBaoHistoryLocalBean.report4KHCN_tdcg(dto.getGiftDeliveryThueBao() != null ? dto.getGiftDeliveryThueBao().getThueBao() : null, dto.getFromDate(), dto.getToDate(), offset, limit, sortExpression, sortDirection);
        List<GiftDeliveryThueBaoHistoryDTO> dtoList = new ArrayList<GiftDeliveryThueBaoHistoryDTO>();
        for (GiftDeliveryThueBaoHistoryEntity entity : (List<GiftDeliveryThueBaoHistoryEntity>)resultObject[1]){
            dtoList.add(DozerSingletonMapper.getInstance().map(entity, GiftDeliveryThueBaoHistoryDTO.class));
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] reportByKHCN_qStudent(GiftDeliveryThueBaoHistoryDTO dto, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        Map<String, Object> properties = new HashMap<String, Object>();
        if(dto.getGiftDeliveryThueBao() != null && dto.getGiftDeliveryThueBao().getThueBao() != null){
            properties.put("soThueBao", dto.getGiftDeliveryThueBao().getThueBao());
        }
        if(dto.getFromDate() != null){
            properties.put("fromDateTime", dto.getFromDate());
        }
        if(dto.getToDate() != null){
            properties.put("toDateTime", dto.getToDate());
        }

        Object[] resultObject = this.actionLogLocalBean.report4KHCN_qStudent(properties, offset, limit, sortExpression, sortDirection);

        List<GiftDeliveryThueBaoHistoryDTO> dtoList = new ArrayList<GiftDeliveryThueBaoHistoryDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            dto = new GiftDeliveryThueBaoHistoryDTO();

            GiftDeliveryThueBaoDTO giftDeliveryThueBaoDTO = new GiftDeliveryThueBaoDTO();
            giftDeliveryThueBaoDTO.setThueBao(tmpArr[0] != null ? tmpArr[0].toString() : null);
            giftDeliveryThueBaoDTO.setDeliveryTime(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);

            UserDTO userDTO = new UserDTO();
            userDTO.setDisplayName(tmpArr[2] != null ? tmpArr[2].toString() : null);
            giftDeliveryThueBaoDTO.setNvGiao(userDTO);

            dto.setGiftDeliveryThueBao(giftDeliveryThueBaoDTO);

            GiftDTO giftDTO = new GiftDTO();
            giftDTO.setName(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setGift(giftDTO);

            dto.setMa_phieu(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setTenCuaHangGiaoQua(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setQuantity(1);
            dtoList.add(dto);

        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public List<GiftDTO> findGiftList() {
        List resultSet = this.giftDeliveryThueBaoHistoryLocalBean.findGiftList();
        List<GiftDTO> giftDTOList = new ArrayList<>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            GiftDTO dto = new GiftDTO();
            dto.setGiftId(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setCode(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setName(tmpArr[2] != null ? tmpArr[2].toString() : null);
            giftDTOList.add(dto);
        }
        return  giftDTOList;
    }
}
