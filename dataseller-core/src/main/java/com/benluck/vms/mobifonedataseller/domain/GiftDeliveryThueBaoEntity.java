package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 12:43 AM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "VMS_GIFT_DELIVERY_THUEBAO")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class GiftDeliveryThueBaoEntity {
    private Long giftDeliveryThueBaoId;
    private UserEntity nvGiao;
    private String thueBao;
    private Timestamp deliveryTime;
    private List<GiftDeliveryThueBaoHistoryEntity> giftDeliveryThueBaoHistoryList;

    @Column(name = "GIFTDELIVERYTHUEBAOID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_GIFT_DELIVERY_THUEBAO_SEQ")
    @SequenceGenerator(name="VMS_GIFT_DELIVERY_THUEBAO_SEQ", sequenceName="VMS_GIFT_DELIVERY_THUEBAO_SEQ", allocationSize=1)
    public Long getGiftDeliveryThueBaoId() {
        return giftDeliveryThueBaoId;
    }

    public void setGiftDeliveryThueBaoId(Long giftDeliveryThueBaoId) {
        this.giftDeliveryThueBaoId = giftDeliveryThueBaoId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NVGIAOID", referencedColumnName = "USERID")
    public UserEntity getNvGiao() {
        return nvGiao;
    }

    public void setNvGiao(UserEntity nvGiao) {
        this.nvGiao = nvGiao;
    }

    @Column(name = "THUE_BAO")
    @Basic
    public String getThueBao() {
        return thueBao;
    }

    public void setThueBao(String thueBao) {
        this.thueBao = thueBao;
    }

    @Column(name = "DELIVERTIME")
    @Basic
    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @OneToMany(cascade=CascadeType.REMOVE, orphanRemoval = false)
    @JoinColumn(name="GIFTDELIVERYTHUEBAOID", insertable = false, updatable = false)
    public List<GiftDeliveryThueBaoHistoryEntity> getGiftDeliveryThueBaoHistoryList() {
        return giftDeliveryThueBaoHistoryList;
    }

    public void setGiftDeliveryThueBaoHistoryList(List<GiftDeliveryThueBaoHistoryEntity> giftDeliveryThueBaoHistoryList) {
        this.giftDeliveryThueBaoHistoryList = giftDeliveryThueBaoHistoryList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftDeliveryThueBaoEntity that = (GiftDeliveryThueBaoEntity) o;

        if (giftDeliveryThueBaoId!= null ? !giftDeliveryThueBaoId.equals(that.giftDeliveryThueBaoId) : that.giftDeliveryThueBaoId != null) return false;
        if (thueBao!= null ? !thueBao.equals(that.thueBao) : that.thueBao!= null) return false;
        if (deliveryTime!= null ? !deliveryTime.equals(that.deliveryTime) : that.deliveryTime != null) return false;
        return  true;
    }

    @Override
    public int hashCode() {
        int result = giftDeliveryThueBaoId != null ? giftDeliveryThueBaoId.hashCode() : 0;
        result = 31 * result + (thueBao != null ? thueBao.hashCode() : 0);
        result = 31 * result + (deliveryTime != null ? deliveryTime.hashCode() : 0);
        return result;
    }
}
