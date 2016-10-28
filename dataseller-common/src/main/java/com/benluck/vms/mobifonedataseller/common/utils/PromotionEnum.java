package com.benluck.vms.mobifonedataseller.common.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/5/15
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public enum PromotionEnum {
    THUE_BAO_PTM_2015("TB_PTM_2015"),
    Q_STUDENT_Q_TEEN_2015("QS_QT_2015");

    private final String code;

    private PromotionEnum(final String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
