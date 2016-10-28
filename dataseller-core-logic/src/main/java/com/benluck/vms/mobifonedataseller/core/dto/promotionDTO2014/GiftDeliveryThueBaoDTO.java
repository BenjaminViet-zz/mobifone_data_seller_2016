package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 12:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class GiftDeliveryThueBaoDTO implements Serializable {
    private static final long serialVersionUID = 3535311811046522643L;

    private Long giftDeliveryThueBaoId;
    private UserDTO nvGiao;
    private String thueBao;
    private Timestamp deliveryTime;
    private List<GiftDeliveryThueBaoHistoryDTO> giftDeliveryThueBaoHistoryList;

    public Long getGiftDeliveryThueBaoId() {
        return giftDeliveryThueBaoId;
    }

    public void setGiftDeliveryThueBaoId(Long giftDeliveryThueBaoId) {
        this.giftDeliveryThueBaoId = giftDeliveryThueBaoId;
    }

    public UserDTO getNvGiao() {
        return nvGiao;
    }

    public void setNvGiao(UserDTO nvGiao) {
        this.nvGiao = nvGiao;
    }

    public String getThueBao() {
        return thueBao;
    }

    public void setThueBao(String thueBao) {
        this.thueBao = thueBao;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public List<GiftDeliveryThueBaoHistoryDTO> getGiftDeliveryThueBaoHistoryList() {
        return giftDeliveryThueBaoHistoryList;
    }

    public void setGiftDeliveryThueBaoHistoryList(List<GiftDeliveryThueBaoHistoryDTO> giftDeliveryThueBaoHistoryList) {
        this.giftDeliveryThueBaoHistoryList = giftDeliveryThueBaoHistoryList;
    }
}
