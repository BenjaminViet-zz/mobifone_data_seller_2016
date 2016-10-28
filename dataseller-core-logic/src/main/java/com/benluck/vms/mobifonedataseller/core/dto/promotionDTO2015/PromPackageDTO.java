package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 2/11/15
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class PromPackageDTO implements Serializable{
    private static final long serialVersionUID = 8619417654012533347L;

    private Long package_Id;
    private String package_Code;
    private String package_Name;
    private String package_Type;
    private Double price;
    private Timestamp created_DateTime;
    private Long create_User;
    private String description;
    private Double prom_Amount;

    public Long getPackage_Id() {
        return package_Id;
    }

    public void setPackage_Id(Long package_Id) {
        this.package_Id = package_Id;
    }

    public String getPackage_Code() {
        return package_Code;
    }

    public void setPackage_Code(String package_Code) {
        this.package_Code = package_Code;
    }

    public String getPackage_Name() {
        return package_Name;
    }

    public void setPackage_Name(String package_Name) {
        this.package_Name = package_Name;
    }

    public String getPackage_Type() {
        return package_Type;
    }

    public void setPackage_Type(String package_Type) {
        this.package_Type = package_Type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getCreated_DateTime() {
        return created_DateTime;
    }

    public void setCreated_DateTime(Timestamp created_DateTime) {
        this.created_DateTime = created_DateTime;
    }

    public Long getCreate_User() {
        return create_User;
    }

    public void setCreate_User(Long create_User) {
        this.create_User = create_User;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getProm_Amount() {
        return prom_Amount;
    }

    public void setProm_Amount(Double prom_Amount) {
        this.prom_Amount = prom_Amount;
    }
}
