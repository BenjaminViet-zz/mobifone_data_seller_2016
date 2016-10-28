create table BRANCH(
BRANCH_ID number(10,0) not null primary key,
BRANCH_CODE varchar2(10 char) not null,
BRANCH_NAME varchar2(200 char) not null,
ADDRESS varchar2(500 char)
);

create table DISTRICT(
DISTRICT_ID NUMBER(10,0) NOT NULL PRIMARY KEY,
DISTRICT_CODE varchar2(5 char) not null,
PROVINCE_ID number(10,0),
BRANCH_ID number(10,0),
DISTRICT_NAME varchar2(100 char),
DTTD_ID varchar2(20 char)
);

create TABLE RETAIL_DEALER(
DEALER_ID number(10,0) not null primary key,
DEALER_CODE varchar2(20 char),
DEALER_NAME varchar2(200 char),
DISTRICT_ID number(10,0),
CONTACT_NAME varchar2(200 char),
EMAIL varchar2(200 char),
ADDRESS varchar2(500 char)
);

create table DEALER_PROPERTIES(
DEALER_ID number(10,0) not null,
PROPERTIES_CODE varchar2(20 char),
PROPERTIES_VALUE varchar2(20 char),
IMPORT_DATETIME date
);

create table EZ_REG_SMS(
EZ_ISDN number(20,0) not null,
DEALER_CODE varchar2(20),
DISTRICT_CODE varchar2(20 char),
REG_DATE date,
REG_STATUS varchar2(1),
REG_LOG varchar2(200 char)
);

create table PROM_PACKAGE(
PACKAGE_ID number(10,0) not null,
PACKAGE_CODE varchar2(20 char),
PACKAGE_NAME varchar2(20 char),
PACKAGE_TYPE varchar2(20 char),
PRICE number(10,0),
CREATE_DATETIME date,
CREATE_USER number(10,0),
DESCRIPTION varchar2(200 char),
PROM_AMOUNT number(10,0)
);

create table REGISTRATION_TRANS(
TRANS_ID number(10,0) not null,
DEALER_ID number(10,0),
EZ_ISDN varchar2(20 char),
PACKAGE_ID number(10,0),
PACKAGE_CODE number(10,0),
CUSTOMER_ISDN varchar2(20 char),
TRANS_DATE date,
IMPORT_DATE date,
PROM_CONDITION_STATUS varchar2(1 char),
PROM_CONDITION_ERROR varchar2(500 char),
REG_POSITION varchar2(20 char),
PROM_AMOUNT number(10,0)
);