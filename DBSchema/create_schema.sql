SET DEFINE OFF;
CREATE USER mobifone_data_seller_2016 IDENTIFIED BY 123456 DEFAULT TABLESPACE USERS TEMPORARY TABLESPACE TEMP;
GRANT CREATE SESSION, RESOURCE, CREATE VIEW, CREATE MATERIALIZED VIEW, CREATE SYNONYM TO mobifone_data_seller_2016;
connect mobifone_data_seller_2016/123456