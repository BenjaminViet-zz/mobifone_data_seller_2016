package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/26/16
 * Time: 09:08
 * To change this template use File | Settings | File Templates.
 */
public class UsedCardCodeDTO implements Serializable{
    private static final long serialVersionUID = -2110956630451259717L;

    private Long usedCardCodeId;
    private String cardCode;
    private String errorMessage;

    public UsedCardCodeDTO() {
    }

    public UsedCardCodeDTO(String cardCode) {
        this.cardCode = cardCode;
    }

    public Long getUsedCardCodeId() {
        return usedCardCodeId;
    }

    public void setUsedCardCodeId(Long usedCardCodeId) {
        this.usedCardCodeId = usedCardCodeId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
