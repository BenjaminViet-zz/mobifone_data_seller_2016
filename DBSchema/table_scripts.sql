CREATE TABLE MOBI_DATA_PERMISSION
(
   PermissionID           NUMBER(24,0)                  NOT NULL PRIMARY KEY,
   Code                   VARCHAR(50)                   NOT NULL,
   Description            VARCHAR2(250 CHAR)            NULL,
   CONSTRAINT "MOBI_DATA_PERMISSION_UQ" UNIQUE (Code)
)  TABLESPACE "USERS";

CREATE TABLE MOBI_DATA_USERGROUP
(
   UserGroupID            NUMBER(24,0)                  NOT NULL PRIMARY KEY,
   Code                   VARCHAR(50)                   NOT NULL,
   Description            VARCHAR2(250 CHAR)            NULL,
   CONSTRAINT "MOBI_DATA_USERGROUP_UQ" UNIQUE (Code)
)  TABLESPACE "USERS";

CREATE TABLE MOBI_DATA_USERGROUPPERMISSION
(
   UserGroupPermissionID  NUMBER(24, 0) NOT NULL PRIMARY KEY,
   UserGroupID            NUMBER(24, 0) NOT NULL,
   PermissionID           NUMBER(24, 0) NOT NULL,
   CONSTRAINT "MOBI_DATA_UGP_UG_FK" FOREIGN KEY(UserGroupID) REFERENCES MOBI_DATA_USERGROUP(UserGroupId),
   CONSTRAINT "MOBI_DATA_UGP_PER_FK" FOREIGN KEY(PermissionID) REFERENCES MOBI_DATA_PERMISSION(PermissionID),
   CONSTRAINT "MOBI_DATA_UGP_PERMISSION_UQ" UNIQUE(UserGroupID, PermissionID)
)  TABLESPACE "USERS";

CREATE TABLE MOBI_DATA_USER
(
   UserID                 NUMBER(24,0)                  NOT NULL PRIMARY KEY,
   Username               VARCHAR(100)                  NOT NULL,
   Password               VARCHAR(50)                   NULL,
   DisplayName            VARCHAR2(250 CHAR)            NOT NULL,
   Status                 NUMBER(24,0)                  NULL,
   UserGroupID            NUMBER(24,0)                  NOT NULL,
   CreatedDate            TIMESTAMP                     NOT NULL,
   LastModified           TIMESTAMP,
   LastLogin              TIMESTAMP,
   IsLDAP                 INTEGER                       NOT NULL,
   CONSTRAINT "MOBI_DATA_USER_USERNAME_UQ" UNIQUE (Username),
   CONSTRAINT "MOBI_DATA_USER_USERGROUP_FK" FOREIGN KEY(UserGroupID) REFERENCES MOBI_DATA_USERGROUP(UserGroupID)
)  TABLESPACE "USERS";

CREATE TABLE MOBI_DATA_PACKAGE_DATA
(
   PackageDataID          NUMBER(24,0)                 NOT NULL PRIMARY KEY,
   Name                   Varchar2(100)                 NOT NULL,
   Value                  Float                         NOT NULL,
   Volume                 Varchar(500)                  NOT NULL,
   DurationText           Varchar2(200 Char)            NOT NULL,
   Duration               Integer                       NOT NULL,
   NumberOfExtend         Integer                       NOT NULL,
   TK                     Varchar(50)                   NOT NULL,
   CONSTRAINT "MOBI_DATA_PACK_DATA_Name_UQ" UNIQUE(Name)
)  TABLESPACE "USERS";

CREATE TABLE MOBI_DATA_KHDN
(
   KHDNID                 NUMBER(24,0)                  NOT NULL PRIMARY KEY,
   Name                   VARCHAR2(200)                 NOT NULL,
   MST                    VARCHAR(100)                  NOT NULL,
   GPKD                   VARCHAR(100)                  NOT NULL,
   IssuedContractDate     DATE                          NOT NULL,
   STB_VAS                VARCHAR(50)                   NOT NULL,
   CONSTRAINT "MOBI_DATA_KHDN_MST_UQ" UNIQUE(MST),
   CONSTRAINT "MOBI_DATA_KHDN_GPKD_UQ" UNIQUE(GPKD)
)  TABLESPACE "USERS";

CREATE TABLE MOBI_DATA_ORDER
(
   OrderID                NUMBER(24,0)                  NOT NULL PRIMARY KEY,
   KHDNID                 NUMBER(24,0)                  NOT NULL,
   PackageDataID          NUMBER(24,0)                  NOT NULL,
   Quantity               Integer                       NOT NULL,
   UnitPrice              Float                         NOT NULL,
   IssuedDate             TIMESTAMP                     NOT NULL,
   ShippingDate           TIMESTAMP                     NOT NULL,
   CreatedDate            TIMESTAMP                     NOT NULL,
   LastModified           TIMESTAMP                     NULL,
   CreatedBy              NUMBER(24,0)                  NOT NULL,
   OrderStatus            INTEGER                       NOT NULL,
   ActiveStatus           INTEGER                       NOT NULL,
   CONSTRAINT "MOBI_DATA_ORDER_CREATED_BY_FK" FOREIGN KEY(CREATEDBY) REFERENCES MOBI_DATA_USER(USERID),
   CONSTRAINT "MOBI_DATA_ORDER_KHDN_FK" FOREIGN KEY(KHDNID) REFERENCES MOBI_DATA_KHDN(KHDNID),
   CONSTRAINT "MOBI_DATA_ORDER_PACKAGEDATA_FK" FOREIGN KEY(KHDNID) REFERENCES MOBI_DATA_PACKAGE_DATA(PACKAGEDATAID)
)  TABLESPACE "USERS";

CREATE TABLE MOBI_DATA_ORDER_DATA_CODE
(
   OrderDataCodeID      NUMBER(24,0)                    NOT NULL PRIMARY KEY,
   OrderID              NUMBER(24,0)                    NOT NULL,
   Serial               NUMBER(24,0)                    NOT NULL,
   DataCode             NUMBER(24,0)                          NOT NULL,
   ExpiredDate          TIMESTAMP                       NOT NULL,
   CONSTRAINT "ORDER_DATA_CODE_ORDER_FK" FOREIGN KEY(OrderID) REFERENCES MOBI_DATA_ORDER(OrderID),
   CONSTRAINT "ORDER_DATA_CODE_UQ" UNIQUE(DataCode)
)  TABLESPACE "USERS";

CREATE TABLE MOBI_DATA_ORDER_HISTORY
(
   OrderHistoryID         NUMBER(24,0)                  NOT NULL PRIMARY KEY,
   OrderID                NUMBER(24,0)                  NOT NULL,
   KHDNID                 NUMBER(24,0)                  NOT NULL,
   PackageDataID          NUMBER(24,0)                  NOT NULL,
   Operator               INTEGER                       NOT NULL,
   Quantity               Integer                       NOT NULL,
   UnitPrice              Float                         NOT NULL,
   IssuedDate             TIMESTAMP                     NOT NULL,
   ShippingDate           TIMESTAMP                     NOT NULL,
   OrderStatus            INTEGER                       NOT NULL,
   CreatedDate            TIMESTAMP                     NOT NULL,
   CreatedBy              NUMBER(24,0)                  NOT NULL,
   CONSTRAINT "MOBI_DATA_OH_CREATEDBY_FK" FOREIGN KEY (CREATEDBY) REFERENCES MOBI_DATA_USER(USERID),
   CONSTRAINT "MOBI_DATA_ORDER_HIS_ORDER_FK" FOREIGN KEY(OrderID) REFERENCES MOBI_DATA_ORDER(OrderID),
   CONSTRAINT "MOBI_DATA_ORDERHIS_KHDN_FK" FOREIGN KEY(KHDNID) REFERENCES MOBI_DATA_KHDN(KHDNID),
   CONSTRAINT "MOBI_DATA_ORDERHIS_PACKDATA_FK" FOREIGN KEY(KHDNID) REFERENCES MOBI_DATA_PACKAGE_DATA(PACKAGEDATAID)
)  TABLESPACE "USERS";

CREATE TABLE MOBI_DATA_PACKAGEDATA_CODE_GEN
(
   PackageDataCodeGenId   NUMBER(24,0)                  NOT NULL PRIMARY KEY,
   PackageDataID          NUMBER(24,0)                  NOT NULL,
   Year                   INTEGER                       NOT NULL,
   CreatedDate            TIMESTAMP                     NOT NULL,
   Batch1_Remaining       INTEGER                       NOT NULL,
   Batch2_Remaining       INTEGER                       NOT NULL,
   Batch3_Remaining       INTEGER                       NOT NULL,
   Batch4_Remaining       INTEGER                       NOT NULL,
   Batch5_Remaining       INTEGER                       NOT NULL,
   Batch6_Remaining       INTEGER                       NOT NULL,
   Batch7_Remaining       INTEGER                       NOT NULL,
   Batch8_Remaining       INTEGER                       NOT NULL,
   Batch9_Remaining       INTEGER                       NOT NULL,
   CONSTRAINT "PACKAGE_DATA_CODE_UQ" UNIQUE(PackageDataID, YEAR),
   CONSTRAINT "PACKAGE_DATA_FK" FOREIGN KEY(PackageDataID) REFERENCES MOBI_DATA_PACKAGE_DATA(PACKAGEDATAID)
)  TABLESPACE "USERS";

CREATE SEQUENCE  MOBI_DATA_USERGROUP_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 4 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  MOBI_DATA_PERMISSION_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 5 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  MOBI_DATA_USER_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 2 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  MOBI_DATA_USERGROUPPER_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  MOBI_DATA_PACKAGE_DATA_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 9 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  MOBI_DATA_KHDN_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  MOBI_DATA_ORDER_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  MOBI_DATA_ORDER_DATA_CODE_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  MOBI_DATA_ORDER_HISTORY_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  MOBI_DATA_PDCGEN_PKD_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

commit;


