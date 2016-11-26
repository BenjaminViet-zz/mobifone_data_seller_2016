package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/26/16
 * Time: 09:05
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_USEDCARDCODE")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class UsedCardCodeEntity {
    private Long usedCardCodeId;           
    private String cardCode;

    @Column(name = "USEDCARDCODEID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_CARDCODE_SEQ")
    @SequenceGenerator(name="MOBI_DATA_CARDCODE_SEQ", sequenceName="MOBI_DATA_CARDCODE_SEQ", allocationSize=1)
    public Long getUsedCardCodeId() {
        return usedCardCodeId;
    }

    public void setUsedCardCodeId(Long usedCardCodeId) {
        this.usedCardCodeId = usedCardCodeId;
    }

    @Column(name = "CARDCODE")
    @Basic
    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    @Override
    public int hashCode() {
        int result = usedCardCodeId != null ? usedCardCodeId.hashCode() : 0;
        result = 31 * result + (cardCode != null ? cardCode.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsedCardCodeEntity that = (UsedCardCodeEntity) o;

        if (usedCardCodeId != null ? !usedCardCodeId.equals(that.usedCardCodeId) : that.usedCardCodeId != null) return false;
        if (cardCode != null ? !cardCode.equals(that.cardCode) : that.cardCode != null) return false;

        return true;
    }
}
