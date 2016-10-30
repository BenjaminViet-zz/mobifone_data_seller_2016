package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;

public class LDAPUserDTO implements Serializable{
    private static final long serialVersionUID = 7558830537830867900L;
    private String commonName;
    private String displayName;
    private String userName;
    private String password;
    private String lastName;
    private String firstName;
    private String middleName;
    private String mail;
    private String mailfile;
    private String department;
    private String subDepartment;
    private Integer status;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMailfile() {
        return mailfile;
    }

    public void setMailfile(String mailfile) {
        this.mailfile = mailfile;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSubDepartment() {
        return subDepartment;
    }

    public void setSubDepartment(String subDepartment) {
        this.subDepartment = subDepartment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String toString() {
        StringBuffer userDTOStr = new StringBuffer("Person=[");
        userDTOStr.append(" Common Name = " + commonName);
        userDTOStr.append(", User Name = " + userName);
        userDTOStr.append(", password = " + password);
        userDTOStr.append(", Display Name = " + displayName);
        userDTOStr.append(", lastname = " + lastName);
        userDTOStr.append(", firstname = " + firstName);
        userDTOStr.append(", email = " + mail);
        userDTOStr.append(", mailfile = " + mailfile);
        userDTOStr.append(" ]");
        return userDTOStr.toString();
    }
}
