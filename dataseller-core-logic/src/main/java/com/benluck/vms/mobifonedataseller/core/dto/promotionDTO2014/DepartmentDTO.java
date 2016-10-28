package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class DepartmentDTO implements Serializable {
    private Long departmentId;
    private String code;
    private String name;
    private String description;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String departmentType;
    private String address;
    private String tel;
    private String contactName;

    private Long chiNhanhId;
    private String tenChiNhanh;

    private Integer soLuongNhap;
    private Integer soLuongPhieuDaDoi;
    private Integer ton;
    private ShopCodeChiNhanhDTO shopCode;
    private GiftDTO gift;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(Integer soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public Integer getSoLuongPhieuDaDoi() {
        return soLuongPhieuDaDoi;
    }

    public void setSoLuongPhieuDaDoi(Integer soLuongPhieuDaDoi) {
        this.soLuongPhieuDaDoi = soLuongPhieuDaDoi;
    }

    public Integer getTon() {
        return ton;
    }

    public void setTon(Integer ton) {
        this.ton = ton;
    }

    public Long getChiNhanhId() {
        return chiNhanhId;
    }

    public void setChiNhanhId(Long chiNhanhId) {
        this.chiNhanhId = chiNhanhId;
    }

    public String getTenChiNhanh() {
        return tenChiNhanh;
    }

    public void setTenChiNhanh(String tenChiNhanh) {
        this.tenChiNhanh = tenChiNhanh;
    }

    public ShopCodeChiNhanhDTO getShopCode() {
        return shopCode;
    }

    public void setShopCode(ShopCodeChiNhanhDTO shopCode) {
        this.shopCode = shopCode;
    }

    public GiftDTO getGift() {
        return gift;
    }

    public void setGift(GiftDTO gift) {
        this.gift = gift;
    }
}
