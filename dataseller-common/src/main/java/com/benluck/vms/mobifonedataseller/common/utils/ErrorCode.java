package com.benluck.vms.mobifonedataseller.common.utils;

/**
 * Created with IntelliJ IDEA.
 * User: nkhang
 * Date: 12/4/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ErrorCode{
    NOEXISTS_CONGVIEC(    "NE01", "Danh mục công việc thực hiện"),
    NOEXISTS_NVKEKHAI(    "NE02", "Nhân viên kê khai"),
    NOEXISTS_PHONGBAN(    "NE03", "Phòng ban"),
    NOEXISTS_DMNGHIEPVUKT("NE04", "Danh mục nghiệp vụ kế toán"),
    NOEXISTS_DMTHANHTOAN( "NE05", "Danh mục thanh toán"),

    EMPTY_CONGVIEC(       "E01", "Danh mục công việc thực hiện"),
    KHONG_CO_CHIPHI(      "E02", "Không có chi phí"),
    SAVE_ERROR(           "E03", ""),
    EMPTY_DMNGHIEPVUKT(   "E04", "Danh mục nghiệp vụ kế toán"),
    EMPTY_NVKEKHAI(       "E05", "Nhân viên kê khai"),
    EMPTY_DMCHIPHI(       "E06", "Danh mục chi phí"),
    EMPTY_NOIDUNG(        "E07", "Nội dung"),
    EMPTY_DMTHANHTOAN(    "E08", "Danh mục thanh toán"),
    ;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String key;

    private String value;

    private ErrorCode(String key, String value){
        this.key = key;
        this.value = value;
    }
}