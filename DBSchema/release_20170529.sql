ALTER TABLE MOBI_DATA_PAYMENT_HISTORY ADD ModifiedBy NUMBER(24,0);
ALTER TABLE MOBI_DATA_PAYMENT_HISTORY ADD LastModifiedDate TIMESTAMP;
ALTER TABLE MOBI_DATA_PAYMENT_HISTORY ADD CONSTRAINTS "PAYMENT_HIS_MODIFIEDBY_FK" FOREIGN KEY(MODIFIEDBY) REFERENCES MOBI_DATA_USER(USERID);
ALTER TABLE MOBI_DATA_PAYMENT DROP CONSTRAINTS "PAYMENT_KHDN_FK";
ALTER TABLE MOBI_DATA_PAYMENT DROP CONSTRAINTS "PAYMENT_KHDN_ORDER_UQ";
ALTER TABLE MOBI_DATA_PAYMENT ADD CONSTRAINTS "PAYMENT_KHDN_ORDER_UQ" UNIQUE(ORDERID);
ALTER TABLE MOBI_DATA_PAYMENT DROP COLUMN KHDNID;
ALTER TABLE MOBI_DATA_PAYMENT_HISTORY MODIFY Amount FLOAT NOT NULL;
ALTER TABLE MOBI_DATA_PAYMENT_HISTORY MODIFY PaymentDate DATE NOT NULL;
commit;