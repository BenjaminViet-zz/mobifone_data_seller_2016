package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 12:47 AM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "VMS_GIFT_DELIVERY_THUEBAO_HIS")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class GiftDeliveryThueBaoHistoryEntity {
    private Long giftDeliveryThueBaoHisId;
    private GiftDeliveryThueBaoEntity giftDeliveryThueBao;
    private String ma_phieu;
    private GiftEntity gift;
    private Integer quantity;
    private Timestamp createdTime;

    @Column(name = "GIFTDELIVERYTHUEBAOHISID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_GIFT_DEL_THUEBAO_HIS_SEQ")
    @SequenceGenerator(name="VMS_GIFT_DEL_THUEBAO_HIS_SEQ", sequenceName="VMS_GIFT_DEL_THUEBAO_HIS_SEQ", allocationSize=1)
    public Long getGiftDeliveryThueBaoHisId() {
        return giftDeliveryThueBaoHisId;
    }

    public void setGiftDeliveryThueBaoHisId(Long giftDeliveryThueBaoHisId) {
        this.giftDeliveryThueBaoHisId = giftDeliveryThueBaoHisId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GIFTDELIVERYTHUEBAOID", referencedColumnName = "GIFTDELIVERYTHUEBAOID")
    public GiftDeliveryThueBaoEntity getGiftDeliveryThueBao() {
        return giftDeliveryThueBao;
    }

    public void setGiftDeliveryThueBao(GiftDeliveryThueBaoEntity giftDeliveryThueBao) {
        this.giftDeliveryThueBao = giftDeliveryThueBao;
    }

    @Column(name = "MA_PHIEU")
    @Basic
    public String getMa_phieu() {
        return ma_phieu;
    }

    public void setMa_phieu(String ma_phieu) {
        this.ma_phieu = ma_phieu;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GIFTID", referencedColumnName = "GIFTID")
    public GiftEntity getGift() {
        return gift;
    }

    public void setGift(GiftEntity gift) {
        this.gift = gift;
    }

    @Column(name = "CREATEDTIME")
    @Basic
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "QUANTITY")
    @Basic
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftDeliveryThueBaoHistoryEntity that = (GiftDeliveryThueBaoHistoryEntity) o;

        if (giftDeliveryThueBaoHisId!= null ? !giftDeliveryThueBaoHisId.equals(that.giftDeliveryThueBaoHisId) : that.giftDeliveryThueBaoHisId != null) return false;
        if (ma_phieu!= null ? !ma_phieu.equals(that.ma_phieu) : that.ma_phieu != null) return false;
        if (quantity!= null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (createdTime!= null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        return  true;
    }

    @Override
    public int hashCode() {
        int result = giftDeliveryThueBaoHisId != null ? giftDeliveryThueBaoHisId.hashCode() : 0;
        result = 31 * result + (ma_phieu != null ? ma_phieu.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (createdTime!= null ? createdTime.hashCode() : 0);
        return result;
    }
}
