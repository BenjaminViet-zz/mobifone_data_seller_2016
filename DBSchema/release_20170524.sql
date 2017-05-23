CREATE TABLE MOBI_DATA_USERTYPE
(
    USERTYPEID        NUMBER(24, 0)         PRIMARY KEY NOT NULL,
    CODE              VARCHAR2(30 CHAR)     NOT NULL,
    DESCRIPTION       VARCHAR2(200 CHAR)    NULL,
    CONSTRAINTS "USER_TYPE_CODE_UQ" UNIQUE(CODE)
);

CREATE SEQUENCE  MOBI_DATA_USERTYPE_SEQ  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

INSERT INTO MOBI_DATA_USERTYPE (USERTYPEID, CODE, DESCRIPTION) VALUES (MOBI_DATA_USERTYPE_SEQ.NEXTVAL, 'ADMIN', 'Quản trị hệ thống');
INSERT INTO MOBI_DATA_USERTYPE (USERTYPEID, CODE, DESCRIPTION) VALUES (MOBI_DATA_USERTYPE_SEQ.NEXTVAL, 'VMS_USER', 'Người dùng tại VMS');
INSERT INTO MOBI_DATA_USERTYPE (USERTYPEID, CODE, DESCRIPTION) VALUES (MOBI_DATA_USERTYPE_SEQ.NEXTVAL, 'KHDN', 'Khách hàng doanh nghiệp');

ALTER TABLE MOBI_DATA_USER ADD USERTYPEID NUMBER(24, 0);

--UPDATE MOBI_DATA_USER SET USERTYPEID = (SELECT ut.userTypeId FROM MOBI_DATA_USERTYPE ut WHERE ut.code = 'ADMIN');

ALTER TABLE MOBI_DATA_USER MODIFY USERTYPEID NUMBER(24, 0) NOT NULL;
ALTER TABLE MOBI_DATA_USER ADD CONSTRAINTS "MOBI_DATA_USERTYPE_FK" FOREIGN KEY(USERTYPEID) REFERENCES MOBI_DATA_USERTYPE(USERTYPEID);

--DELETE FROM MOBI_DATA_USERGROUP WHERE CODE != 'ADMIN';

UPDATE MOBI_DATA_PERMISSION SET CODE = 'GENERAL_EXPENSE_REPORT_MANAGER', DESCRIPTION = 'B/c tổng hợp chi phí phát triển và duy trì' WHERE CODE = 'REPORT_MANAGER';
INSERT INTO MOBI_DATA_PERMISSION (PERMISSIONID, CODE, DESCRIPTION) VALUES (MOBI_DATA_PERMISSION_SEQ.NEXTVAL, 'ORDER_STATUS_MANAGER', 'Cập nhật trạng thái đơn hàng');
INSERT INTO MOBI_DATA_PERMISSION (PERMISSIONID, CODE, DESCRIPTION) VALUES (MOBI_DATA_PERMISSION_SEQ.NEXTVAL, 'DETAILED_EXPENSE_REPORT_MANAGER', 'B/c chi tiết chi phí phát triển và duy trì');

INSERT INTO MOBI_DATA_USERGROUPPERMISSION(USERGROUPPERMISSIONID, USERGROUPID, PERMISSIONID) VALUES (MOBI_DATA_USERGROUPPER_SEQ.NEXTVAL, (SELECT ug.userGroupId FROM MOBI_DATA_USERGROUP ug WHERE ug.code = 'ADMIN'), (SELECT p.permissionID FROM MOBI_DATA_PERMISSION p WHERE p.code = 'USER_MANAGER'));
INSERT INTO MOBI_DATA_USERGROUPPERMISSION(USERGROUPPERMISSIONID, USERGROUPID, PERMISSIONID) VALUES (MOBI_DATA_USERGROUPPER_SEQ.NEXTVAL, (SELECT ug.userGroupId FROM MOBI_DATA_USERGROUP ug WHERE ug.code = 'ADMIN'), (SELECT p.permissionID FROM MOBI_DATA_PERMISSION p WHERE p.code = 'USER_GROUP_MANAGER'));
INSERT INTO MOBI_DATA_USERGROUPPERMISSION(USERGROUPPERMISSIONID, USERGROUPID, PERMISSIONID) VALUES (MOBI_DATA_USERGROUPPER_SEQ.NEXTVAL, (SELECT ug.userGroupId FROM MOBI_DATA_USERGROUP ug WHERE ug.code = 'ADMIN'), (SELECT p.permissionID FROM MOBI_DATA_PERMISSION p WHERE p.code = 'KHDN_MANAGER'));
INSERT INTO MOBI_DATA_USERGROUPPERMISSION(USERGROUPPERMISSIONID, USERGROUPID, PERMISSIONID) VALUES (MOBI_DATA_USERGROUPPER_SEQ.NEXTVAL, (SELECT ug.userGroupId FROM MOBI_DATA_USERGROUP ug WHERE ug.code = 'ADMIN'), (SELECT p.permissionID FROM MOBI_DATA_PERMISSION p WHERE p.code = 'PACKAGE_DATA_MANAGER'));
INSERT INTO MOBI_DATA_USERGROUPPERMISSION(USERGROUPPERMISSIONID, USERGROUPID, PERMISSIONID) VALUES (MOBI_DATA_USERGROUPPER_SEQ.NEXTVAL, (SELECT ug.userGroupId FROM MOBI_DATA_USERGROUP ug WHERE ug.code = 'ADMIN'), (SELECT p.permissionID FROM MOBI_DATA_PERMISSION p WHERE p.code = 'GENERATE_CARD_CODE_MANAGER'));
INSERT INTO MOBI_DATA_USERGROUPPERMISSION(USERGROUPPERMISSIONID, USERGROUPID, PERMISSIONID) VALUES (MOBI_DATA_USERGROUPPER_SEQ.NEXTVAL, (SELECT ug.userGroupId FROM MOBI_DATA_USERGROUP ug WHERE ug.code = 'ADMIN'), (SELECT p.permissionID FROM MOBI_DATA_PERMISSION p WHERE p.code = 'EXPENSE_MANAGER'));
INSERT INTO MOBI_DATA_USERGROUPPERMISSION(USERGROUPPERMISSIONID, USERGROUPID, PERMISSIONID) VALUES (MOBI_DATA_USERGROUPPER_SEQ.NEXTVAL, (SELECT ug.userGroupId FROM MOBI_DATA_USERGROUP ug WHERE ug.code = 'ADMIN'), (SELECT p.permissionID FROM MOBI_DATA_PERMISSION p WHERE p.code = 'ORDER_MANAGER'));
INSERT INTO MOBI_DATA_USERGROUPPERMISSION(USERGROUPPERMISSIONID, USERGROUPID, PERMISSIONID) VALUES (MOBI_DATA_USERGROUPPER_SEQ.NEXTVAL, (SELECT ug.userGroupId FROM MOBI_DATA_USERGROUP ug WHERE ug.code = 'ADMIN'), (SELECT p.permissionID FROM MOBI_DATA_PERMISSION p WHERE p.code = 'ORDER_STATUS_MANAGER'));
INSERT INTO MOBI_DATA_USERGROUPPERMISSION(USERGROUPPERMISSIONID, USERGROUPID, PERMISSIONID) VALUES (MOBI_DATA_USERGROUPPER_SEQ.NEXTVAL, (SELECT ug.userGroupId FROM MOBI_DATA_USERGROUP ug WHERE ug.code = 'ADMIN'), (SELECT p.permissionID FROM MOBI_DATA_PERMISSION p WHERE p.code = 'GENERAL_EXPENSE_REPORT_MANAGER'));
INSERT INTO MOBI_DATA_USERGROUPPERMISSION(USERGROUPPERMISSIONID, USERGROUPID, PERMISSIONID) VALUES (MOBI_DATA_USERGROUPPER_SEQ.NEXTVAL, (SELECT ug.userGroupId FROM MOBI_DATA_USERGROUP ug WHERE ug.code = 'ADMIN'), (SELECT p.permissionID FROM MOBI_DATA_PERMISSION p WHERE p.code = 'DETAILED_EXPENSE_REPORT_MANAGER'));

ALTER TABLE MOBI_DATA_PERMISSION ADD ORDERNO INTEGER DEFAULT 0 NOT NULL;

UPDATE MOBI_DATA_PERMISSION SET ORDERNO = 1, DESCRIPTION = 'Quản lý người dùng' WHERE CODE = 'USER_MANAGER';
UPDATE MOBI_DATA_PERMISSION SET ORDERNO = 2, DESCRIPTION = 'Quản lý nhóm quyền truy cập' WHERE CODE = 'USER_GROUP_MANAGER';
UPDATE MOBI_DATA_PERMISSION SET ORDERNO = 3 WHERE CODE = 'PACKAGE_DATA_MANAGER';
UPDATE MOBI_DATA_PERMISSION SET ORDERNO = 4 WHERE CODE = 'KHDN_MANAGER';
UPDATE MOBI_DATA_PERMISSION SET ORDERNO = 5 WHERE CODE = 'ORDER_MANAGER';
UPDATE MOBI_DATA_PERMISSION SET ORDERNO = 6 WHERE CODE = 'ORDER_STATUS_MANAGER';
UPDATE MOBI_DATA_PERMISSION SET ORDERNO = 7 WHERE CODE = 'GENERATE_CARD_CODE_MANAGER';
UPDATE MOBI_DATA_PERMISSION SET ORDERNO = 8 WHERE CODE = 'EXPENSE_MANAGER';
UPDATE MOBI_DATA_PERMISSION SET ORDERNO = 9 WHERE CODE = 'GENERAL_EXPENSE_REPORT_MANAGER';
UPDATE MOBI_DATA_PERMISSION SET ORDERNO = 10 WHERE CODE = 'DETAILED_EXPENSE_REPORT_MANAGER';

commit;