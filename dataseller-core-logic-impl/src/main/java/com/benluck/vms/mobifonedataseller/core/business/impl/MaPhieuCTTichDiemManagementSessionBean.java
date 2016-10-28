package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import com.benluck.vms.mobifonedataseller.core.business.MaPhieuCTTichDiemManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.ShopStockGoodsDTO;
import com.benluck.vms.mobifonedataseller.domain.*;
import com.benluck.vms.mobifonedataseller.session.*;
import org.apache.commons.lang.StringUtils;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@javax.ejb.Stateless(name = "MaPhieuCTTichDiemManagementSessionEJB")
public class MaPhieuCTTichDiemManagementSessionBean implements MaPhieuCTTichDiemManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    @EJB
    private GiftDeliveryThueBaoLocalBean giftDeliveryThueBaoLocalBean;
    @EJB
    private GiftDeliveryThueBaoHistoryLocalBean giftDeliveryThueBaoHistoryLocalBean;
    @EJB
    private GiftLocalBean giftLocalBean;
    @EJB
    private StockAgentLocalBean stockAgentLocalBean;
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;
    @EJB
    private DMChuongTrinhLocalBean dmChuongTrinhLocalBean;
    @EJB
    private ShopStockGoodsLocalBean shopStockGoodsLocalBean;
    @EJB
    private ActionLogLocalBean actionLogLocalBean;

    public MaPhieuCTTichDiemManagementSessionBean() {
    }


    @Override
    public Object[] searchByMaPhieu_tdcg(String maPhieu, Integer da_doi_qua, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        return ctTichDiemLocalBean.searchByMaPhieu(maPhieu, da_doi_qua, offset, limit, sortExpression, sortDirection);
    }

    @Override
    public Object[] searchByMaPhieu_qStudent(String maPhieu, Integer da_doi_qua, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        return this.thueBaoPhatTrienMoiLocalBean.searchByMaPhieu(maPhieu, da_doi_qua, offset, limit, sortExpression, sortDirection);
    }

    @Override
    public Object[] searchByThueBao_tdcg(String thueBao, Integer da_doi_qua, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        return this.ctTichDiemLocalBean.searchByThueBao(thueBao, da_doi_qua, offset, limit, sortExpression, sortDirection);
    }

    @Override
    public Object[] searchByThueBao_qStudent(String soThueBao, Integer da_doi_qua, Integer offset, Integer limit, String sortDirection, String sortExpression) {
        return this.thueBaoPhatTrienMoiLocalBean.searchByThueBao(soThueBao, da_doi_qua, offset, limit, sortExpression, sortDirection);
    }

    @Override
    public void shopUserGiaoQua_tdcg(Long nvGiaoQuaId, String[] dsMaPhieus, String shopCode, String userName)throws ObjectNotFoundException, DuplicateKeyException{
        List<String> maPhieus = new ArrayList<String>();
        for (String maPhieu : dsMaPhieus){
            maPhieus.add(maPhieu);
        }
        ctTichDiemLocalBean.shopUserGiftExchange(maPhieus, shopCode, userName);

        logGiftDeliveryThueBao(nvGiaoQuaId, maPhieus);
    }

    @Override
    public void shopUserGiaoQua_qStudent(Long nvGiaoQuaId, String[] dsMaPhieus, String shopCode, String userName, Long giftId) throws ObjectNotFoundException, DuplicateKeyException {
        List<String> maPhieus = new ArrayList<String>();
        StringBuilder maPhieuString = new StringBuilder();
        for (String maPhieu : dsMaPhieus){
            maPhieus.add(maPhieu);
            maPhieuString.append(StringUtils.isNotBlank(maPhieuString.toString()) ? "#" : "").append(maPhieu );
        }
        List resultSet = this.shopStockGoodsLocalBean.findShopStockByShopCode(shopCode,giftId);
        List<ShopStockGoodsDTO> shopStockGoodsList = new ArrayList<>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            ShopStockGoodsDTO dto = new ShopStockGoodsDTO();
            dto.setStockId(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            GiftDTO giftDTO = new GiftDTO();
            giftDTO.setGiftId(tmpArr[1] != null ? Long.valueOf(tmpArr[1].toString()) : null);
            dto.setGift(giftDTO);
            dto.setQuantity(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            shopStockGoodsList.add(dto);
        }
        thueBaoPhatTrienMoiLocalBean.shopUserGiaoQua(maPhieus, shopCode, userName);
        addGiftHistory(nvGiaoQuaId, maPhieuString.toString(), shopCode, userName, shopStockGoodsList, giftId);

        updateStockAgentInventoryQuantityForNVAgent_qStudent(maPhieus.size(),shopStockGoodsList);

    }

    private void updateStockAgentInventoryQuantityForNVAgent_qStudent(Integer deliveriedQuantity,List<ShopStockGoodsDTO> shopStockGoodsList ) {
        Map<String, Object> properties = new HashMap<String, Object>();
        if(shopStockGoodsList.get(0).getGift() != null && shopStockGoodsList.get(0).getGift().getGiftId() != null){
            properties.put("giftId", shopStockGoodsList.get(0).getGift().getGiftId());
        }
        if(shopStockGoodsList.get(0).getStockId() != null){
            properties.put("stockId", shopStockGoodsList.get(0).getStockId());
        }
        if(shopStockGoodsList.get(0).getQuantity() != null){
            properties.put("quantity", shopStockGoodsList.get(0).getQuantity() - deliveriedQuantity);
        }
        this.thueBaoPhatTrienMoiLocalBean.updateShopStock_qStudent(properties);
    }

    private void addGiftHistory(Long nvGiaoQuaId, String maPhieuString, String shopCode, String userName, List<ShopStockGoodsDTO> shopStockGoodsList, Long giftId) throws DuplicateKeyException {

        ActionLogEntity entity = new ActionLogEntity();
        DMChuongTrinhEntity dmChuongTrinhEntity = null;
        try{
            dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode());
        }catch (ObjectNotFoundException oe){}

        entity.setChuongTrinh(dmChuongTrinhEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(nvGiaoQuaId);
        entity.setNguoiThaoTac(userEntity);

        entity.setTableLog("QSV_2015_SHOP_STOCK_GOODS");
        entity.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        entity.setColumnIdLogName("STOCK_ID");
        entity.setColumnIdLogValue(shopStockGoodsList.get(0).getStockId());
        entity.setSupportData("maPhieus=" + maPhieuString + ";shopCode=" + shopCode + ";userName=" + userName + ";quantity=" + shopStockGoodsList.get(0).getQuantity() + ";giftId=" + giftId);
        this.actionLogLocalBean.save(entity);
    }

    private void logGiftDeliveryThueBao(Long nvGiaoQuaId, List<String> maPhieus)throws ObjectNotFoundException, DuplicateKeyException{
        if(maPhieus != null && maPhieus.size() > 0){
            try{
                String thueBaoNhanQua = this.ctTichDiemLocalBean.findUserPhoneNumberByMaPhieu(maPhieus.get(0));
                if(StringUtils.isBlank(thueBaoNhanQua)){
                    throw new ObjectNotFoundException("Khong tim thay so thue bao nhan qua theo ma phieu: " + maPhieus.get(0));
                }
                GiftDeliveryThueBaoEntity entity = new GiftDeliveryThueBaoEntity();
                UserEntity nvGiaoQuaEntity = new UserEntity();
                nvGiaoQuaEntity.setUserId(nvGiaoQuaId);
                entity.setNvGiao(nvGiaoQuaEntity);
                entity.setThueBao(thueBaoNhanQua);
                entity.setDeliveryTime(new Timestamp(System.currentTimeMillis()));
                entity = this.giftDeliveryThueBaoLocalBean.save(entity);
                logGiftDeliveryThueBaoHistory(entity, maPhieus);

                updateStockAgentInventoryQuantityForNVAgent(nvGiaoQuaId, maPhieus.size());
            }catch (DuplicateKeyException de){
                throw  new DuplicateKeyException("Duplicaite key for log GiftDeliveryThueBao.\n Details: " +de.getMessage());
            }
        }
    }

    private void logGiftDeliveryThueBaoHistory(GiftDeliveryThueBaoEntity giftDeliveryThueBaoEntity, List<String> maPhieus) throws ObjectNotFoundException, DuplicateKeyException{
        GiftEntity giftEntity = this.giftLocalBean.findByCode(Constants.GIFTCODE_PMH);
        if(giftEntity == null){
            throw new ObjectNotFoundException("Object not found for Gift entity with code: " + Constants.GIFTCODE_PMH);
        }
        for(String ma_phieu : maPhieus){
            try{
                GiftDeliveryThueBaoHistoryEntity entity = new GiftDeliveryThueBaoHistoryEntity();
                entity.setGiftDeliveryThueBao(giftDeliveryThueBaoEntity);
                entity.setMa_phieu(ma_phieu);
                entity.setGift(giftEntity);
                entity.setQuantity(1);
                entity.setCreatedTime(new Timestamp(System.currentTimeMillis()));
                entity = this.giftDeliveryThueBaoHistoryLocalBean.save(entity);
            }catch (DuplicateKeyException de){
                throw new DuplicateKeyException("Duplicate key for log GiftDeliveryThueBaoHistory for ma_phieu: " + ma_phieu);
            }
        }
    }

    private void updateStockAgentInventoryQuantityForNVAgent(Long nvGiaoQuaId, Integer deliveriedQuantity) throws DuplicateKeyException{
        try{
            StockAgentEntity entity = this.stockAgentLocalBean.findByShopIdOfDelivererId(nvGiaoQuaId);
            entity.setTotal(entity.getTotal() - deliveriedQuantity);
            entity = this.stockAgentLocalBean.update(entity);
        }catch (DuplicateKeyException de){
            throw new DuplicateKeyException("Duplicate keky for update inventory for shopUserId: " + nvGiaoQuaId);
        }
    }

    @Override
    public Object[] search_tdcg(String soThueBao, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        return this.ctTichDiemLocalBean.searchDanhSachMaPhieu(soThueBao, offset, limit, sortExpression, sortDirection);
    }

    @Override
    public Object[] search_qStudent(String soThueBao, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        return this.thueBaoPhatTrienMoiLocalBean.searchMaPhieuListByProperties(soThueBao, offset, limit, sortExpression, sortDirection);
    }

    @Override
    public Integer getTotalOgExchangedMaPhieu_qStudent(String phoneNumber) {
        return this.thueBaoPhatTrienMoiLocalBean.countExchangedMaPhieus(phoneNumber);
    }

    @Override
    public Integer getTotalOfExchangedMaPhieu_tdcg() {
        return this.ctTichDiemLocalBean.getTotalOfExchangedMaPhieu();
    }

    @Override
    public String[] validateMaPhieuNeedToExchanged_tdcg(String[] maPhieuList) {
        return this.ctTichDiemLocalBean.validateMaPhieuNeedToExchanged(maPhieuList);
    }

    @Override
    public String[] getExchangedMaPhieuListByList_qStudent(String[] maPhieuList) {
        return this.thueBaoPhatTrienMoiLocalBean.getExchangedMaPhieuListByList(maPhieuList);
    }

    @Override
    public void cancelGiftExchange(Long nvGiaoQuaId, String[] dsMaPhieu, String shopCode, String userNameShop, Long giftId) {
        List<String> maPhieus = new ArrayList<String>();
        for (String maPhieu : dsMaPhieu){
            maPhieus.add(maPhieu);
        }

        thueBaoPhatTrienMoiLocalBean.updateMaPhieuStatus2NoExchange(maPhieus);
        updateShopStockAndRemoveExchangeHistory(maPhieus, maPhieus.get(0));
    }

    /**
     * Update quantity of items in shop stock and delete history of exchanged for each Ma Phieu.
     * @param maPhieus
     * @param maPhieu
     */
    private void updateShopStockAndRemoveExchangeHistory(List<String> maPhieus, String maPhieu) {
        List resultSet = this.actionLogLocalBean.findByMaPhieu(maPhieu);
        List list = new ArrayList<>();
        Integer total = maPhieus.size();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            list.add(tmpArr[0]);
            list.add(tmpArr[1]);
        }
        String complexStr = list.get(1).toString();

        Long giftId = Long.valueOf(complexStr.split("\\;")[4].substring(complexStr.split("\\;")[4].indexOf("=") +1,complexStr.split("\\;")[4].length()));
        Integer quantityLog = Integer.valueOf(complexStr.split("\\;")[3].substring(complexStr.split("\\;")[3].indexOf("=") +1,complexStr.split("\\;")[3].length()));
        Long stockId = Long.valueOf(list.get(0).toString());

        List shopStockList = this.shopStockGoodsLocalBean.findShopStockByIdAndStockId(stockId, giftId);

        List<ShopStockGoodsDTO> shopStockGoodsList = new ArrayList<>();
        for (Object object : shopStockList){
            Object[] tmpArr = (Object[])object;
            ShopStockGoodsDTO dto = new ShopStockGoodsDTO();
            dto.setStockId(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            GiftDTO giftDTO = new GiftDTO();
            giftDTO.setGiftId(tmpArr[1] != null ? Long.valueOf(tmpArr[1].toString()) : null);
            dto.setGift(giftDTO);
            dto.setQuantity(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            shopStockGoodsList.add(dto);
        }

        Map<String, Object> properties = new HashMap<String, Object>();
        if(giftId != null){
            properties.put("giftId", giftId);
        }
        if(stockId != null){
            properties.put("stockId", stockId);
        }
        if(quantityLog != null && total != null){
            properties.put("quantity", shopStockGoodsList.get(0).getQuantity() + total);
        }

        this.thueBaoPhatTrienMoiLocalBean.updateShopStock_qStudent(properties);
        removeGiftExchangeHistory(maPhieus);
    }

    /**
     * Delete history of gift exchange that related to list of Ma Phieu.
     * @param dsMaPhieu
     */
    private void removeGiftExchangeHistory(List<String> dsMaPhieu) {
        for (String maPhieu : dsMaPhieu){
            if(this.actionLogLocalBean.checkLastMaPhieu(maPhieu)){
                this.actionLogLocalBean.removeMaPhieuGiftHistory(maPhieu);
            } else {
                this.actionLogLocalBean.removeGiftHistory(maPhieu);
            }
        }
    }
}
