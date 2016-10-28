
package com.benluck.vms.mobifonedataseller.common;

import java.math.BigDecimal;

public class Constants {
    /* *
     * Bcc recipients delimiter
     */
    public static final String DELIMITER = ",";
    public static final String EMAIL_DELIMITER = ";";
    public static final String SEPERATE = ":";
    /**
     * Sort direction constants
     */
    public static final String SORT_ASC = "2";
    public static final String SORT_DESC = "1";

    public static final int MAXPAGEITEMS = 20;

    public static final int REPORT_MAXPAGEITEMS = 100;


    //~ Static fields/initializers =============================================

    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String VI_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final String DB_DATE_FORMAT = "YYYY-MM-DD";
    /**
     * The Alphabet number for search query
     */
    public static final String ALPHABET_SEARCH_PREFIX = "^$^";
    /**
     * The name of the ResourceBundle used in this application
     */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /**
     * File separator from System properties
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * User home from System properties
     */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";

    /**
     * Session scope attribute that holds the locale set by the user. By setting this key
     * to the same one that Struts uses, we get synchronization in Struts w/o having
     * to do extra work or have two session-level variables.
     */
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

    /**
     * The request scope attribute that holds the list form
     */
    public static final String LIST_MODEL_KEY = "items";

    /**
     * The request scope attribute that holds the form
     */
    public static final String FORM_MODEL_KEY = "item";


    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "ADMIN";

    public static final String ACCOUNT_ROLE = "ACCOUNT";

    public static final String PAYMENT_ROLE = "PAYMENT";

    public static final String LOGON_ROLE = "LOGON";

    public static final String NHANVIEN_ROLE = "NHANVIEN";

    public static final String TONGDAI_ROLE = "TONGDAI";

    public static final String CHINHANH_ROLE = "CHINHANH";

    public static final String BAOCAO_ROLE = "BAOCAO";





    /**
     * The name of the available roles list, common.a request-scoped attribute
     * when adding/editing common.a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * The name of the CSS Theme setting.
     */
    public static final String CSS_THEME = "csstheme";


    /**
     * Acegi security constants
     */

    public static final String ACEGI_SECURITY_FORM_USERNAME_KEY = "j_username";
    public static final String ACEGI_SECURITY_FORM_PASSWORD_KEY = "j_password";
    public static final String ACEGI_SECURITY_LAST_USERNAME_KEY = "ACEGI_SECURITY_LAST_USERNAME";


    /**
     * Cookie for web and content security
     */
    public static final String LOGIN_USER_ID_COOKIE = "j_loggined_userid";
    public static final String LOGIN_CHECKSUM = "j_loginned_checksum";
    public static final String LOGIN_ROLE_COOKIE = "j_role";



    /**
     * User status constants
     */
    public static final int USER_ACTIVE = 1;
    public static final int USER_INACTIVE = 2;
    public static final int USER_DISABLED = 3;

    public static final String GLOBAL_META_ROLE_PREFIX = "role_";
    public static final String PROMOTION_REMEMBER_ME_COOKIE_KEY = "PROMOTION_REMEMBER_ME_COOKIE_KEY";
    public static final String CHECKSUM_SECURITY_HASH = "VMS_SEC_HASH";

    public static final String MESSAGE_RESPONSE_MODEL_KEY = "messageResponse";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_INSERT = "insert";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_SEARCH = "search";
    public static final String ACTION_EXPORT = "export";

    public static final String DEPARTMENT_TYPE_SHOP = "SHOP";
    public static final String DEPARTMENT_TYPE_CN = "CN";
    public static final String DEPARTMENT_TYPE_DAILY = "DAILY";

    public static final String USERGROUP_TB = "THUEBAO";
    public static final String USERGROUP_NV = "NHANVIEN";
    public static final String USERGROUP_TD = "TONGDAI";
    public static final String USERGROUP_KPP = "KPP";
    public static final String USERGROUP_CN = "CHINHANH";
    public static final String USERGROUP_ADMIN = "ADMIN";
    public static final String USERGROUP_BAOCAO = "BAOCAO";

    public static final int USERPASSWORD_IS_USED= 1;
    public static final int USERPASSWORD_NOT_YET_USE = 2;

    public static final String ALERT_TYPE = "alertType";
    public static final String SHOP_STATUS_DOIQUA = "updateStatus";

    public static final Integer THUEBAO_DOIQUA = 1;
    public static final Integer NV_DOIQUA = 2;
    public static final Integer HUY_DOIQUA = 0;

    public static final String GIFTCODE_PMH = "PMH";

    public static final Integer DELIVERIED_STOCK = 1;

    public static final Integer CHUA_XAC_NHAN_DOI_QUA = 0;
    public static final Integer DA_XAC_NHAN_DOI_QUA = 1;
    public static final Integer DA_GIAO_QUA = 2;

    public static final Integer UNIT_SCORE = 1000;

    public static final Integer TOTAL_SO_LUONG_QUA_CT = 20000;

    public static final String MOBIFONE_DK = "MOBIFONE DK";
    public static final String MOBIFONE_HUY = "MOBIFONE HUY";
    public static final String MOBIFONE_DP = "MOBIFONE DP";
    public static final String MOBIFONE_KT = "MOBIFONE KT";

    public static final String DK_MOBIFONE = "DK MOBIFONE";
    public static final String HUY_MOBIFONE = "HUY MOBIFONE";
    public static final String DP_MOBIFONE = "DP MOBIFONE";
    public static final String KT_MOBIFONE = "KT MOBIFONE";

    public static final String LOAITINNHAN_1 = "1";
    public static final String LOAITINNHAN_2_1 = "2.1";
    public static final String LOAITINNHAN_2_2 = "2.2";
    public static final String LOAITINNHAN_3 = "3";
    public static final String LOAITINNHAN_4 = "4";
    public static final String LOAITINNHAN_4_1 = "4.1";
    public static final String LOAITINNHAN_4_2 = "4.2";
    public static final String LOAITINNHAN_5 = "5";
    public static final String LOAITINNHAN_6 = "6";
    public static final String LOAITINNHAN_7_1 = "7.1";
    public static final String LOAITINNHAN_7_2 = "7.2";
    public static final String LOAITINNHAN_8 = "8";
    public static final String LOAITINNHAN_9 = "9";

    public static final String PREFIX_KPP_USERNAME = "KPP_";

    public static final Integer PASSWORD_TYPE_TB = 0;
    public static final Integer PASSWORD_TYPE_KPP = 1;

    public static final String PSC_TYPE_INPUT_PTM = "PTM";
    public static final String PSC_TYPE_INPUT_PSC = "PSC";

    public static final String DEALER_ACCONT_ACTION_DA_CHOT_KY = "1";
    public static final String DEALER_ACCONT_ACTION_CHUA_CHOT_KY = "0";

    public static final String DEALER_ACCOUNT_ACTION_DA_DOI_TIEN = "1";
    public static final String DEALER_ACCOUNT_ACTION_CHUA_DOI_TIEN = "0";

    public static final String DEALER_ACCOUNT_ACTION_DAT_DK_DOI_TIEN = "1";
    public static final String DEALER_ACCOUNT_ACTION_KG_DAT_DOI_TIEN = "0";

    public static final Integer KPP_MA_THUONG_CHUA_QUAY_SO = 0;
    public static final Integer KPP_MA_THUONG_KHONG_TRUNG_THUONG = 1;
    public static final Integer KPP_MA_THUONG_TRUNG_THUONG = 2;

    public static final Integer DU_DIEU_KIEN = 1;
    public static final Integer KHONG_DIEU_KIEN = 0;

    public static final String KPP_WINNING_STATUS_KHONG_TRUNG_THUONG = "2";
    public static final String KPP_WINNING_STATUS_TRUNG_THUONG = "1";
    public static final String KPP_WINNING_STATUS_CHUA_QUAY_SO = "0";

    public static final String EXCHANGE_GIFT_STATUS_CHUA_NHAN = "0";
    public static final String EXCHANGE_GIFT_STATUS_DA_NHAN = "1";

    public static final Long ITEM_ID_1 = new Long(1);
    public static final Long ITEM_ID_2 = new Long(2);
    public static final Long ITEM_ID_3 = new Long(3);
    public static final Long ITEM_ID_4 = new Long(4);
    public static final Long ITEM_ID_5 = new Long(5);
    public static final Long ITEM_ID_6 = new Long(6);
    public static final Long ITEM_ID_7 = new Long(7);

    public static final String TRANG_THAI_TRA_THUONG_CHUA_CHI_TRA = "0";
    public static final String TRANG_THAI_TRA_THUONG_DA_CHI_TRA = "1";
    public static final String TRANG_THAI_TRA_THUONG_KHONG_DAT = "2";

    public static final String TRANG_THAI_CHUA_CHI_TRA = "0";
    public static final String TRANG_THAI_DA_CHI_TRA = "1";

    public static final String THANH_CONG_DUOC_CHI_TRA = "1";
    public static final String THANH_CONG_KHONG_DUOC_CHI_TRA = "0";

    public static final String TINH_TRANG_THANH_CONG = "1";
    public static final String TINH_TRANG_KHONG_THANH_CONG = "0";

    public static final String TINH_TRANG_CHUA_XET = "0";
    public static final String TINH_TRANG_DUOC_CHI_TRA = "1";
    public static final String TINH_TRANG_KHONG_DUOC_CHI_TRA = "2";

    public static final String TRANG_THAI_DOI_TIEN_DAT_KPP = "1";
    public static final String TRANG_THAI_DOI_TIEN_KHONG_DAT_KPP = "0";

    public static final String STORE_REQUEST_PARAMS_STR = "STORE_REQUEST_PARAMS_STR_";
    public static final String PARAM_ITEM_ID = "item_Id";
    public static final String PARAM_SO_EZ = "soEZ";
    public static final String PARAM_DEALER_ID = "dealer_Id";
    public static final String PARAM_FROM_DATE = "fromDate";
    public static final String PARAM_TO_DATE = "toDate";

    public static final Integer REPORT_DANHGIAKETQUATHUCHIENCT_SUMMARY = 1;
    public static final Integer REPORT_DANHGIAKETQUATHUCHIENCT_THEONGAY = 0;

    //  Bao cao tong hop ket qua chuong tring KPP
    public static final Integer BC_TONG_HOP_KQ_CHUONG_TRINH_KPP_TRANG_THAI_TRA_THUONG_KHONG_DAT = 0;
    public static final Integer BC_TONG_HOP_KQ_CHUONG_TRINH_KPP_TRANG_THAI_TRA_THUONG_DAT = 1;

    // promotion_type_configure
    public static final String CT_TICH_DIEM_CUOC_GOI_2014 = "";
    public static final String CT_THUE_BAO_PTM = "THUEBAO_PTM";

    // REGISTRATION TRANS
    public static final String REGISTRATION_TRANS_PROM_CONDITION_STATUS_NO_SATISFY = "0";
    public static final String REGISTRATION_TRANS_PROM_CONDITION_STATUS_SATISFY = "1";
    public static final String REGISTRATION_TRANS_PROM_CONDITION_STATUS_OVER_RULE = "2";

    public static final Integer REGISTRATION_TRANS_TRANS_STATUS_FAILURE = 0;
    public static final Integer REGISTRATION_TRANS_TRANS_STATUS_SUCCESS = 1;

    public static final Integer PROM_CONDITION_STATUS_NULL = null;
    public static final String PROM_CONDITION_STATUS_0 = "0";
    public static final String PROM_CONDITION_STATUS_1 = "1";
    public static final String PROM_CONDITION_STATUS_2 = "2";

    public static final String PROM_CONDITION_STATUS_NULL_MESS = "Chưa xét";
    public static final String PROM_CONDITION_STATUS_0_MESS = "Không hợp lệ";
    public static final String PROM_CONDITION_STATUS_1_MESS = "Hợp lệ";
    public static final String PROM_CONDITION_STATUS_2_MESS = "Vượt quy định";

    public static final Integer TB_PTM_2015_REG_PAYMENT_STATUS_DA_CHI = 1;
    public static final Integer TB_PTM_2015_REG_PAYMENT_STATUS_CHUA_CHI = 0;

    public static final String DEALER_HAVE_DOC = "HAVE_DOC";

    public static final Integer REPORT_CHITRAKHUYENKICH_SUMMARY = 1;
    public static final Integer REPORT_CHITRAKHUYENKICH_THEONGAY = 0;

    public static final Integer MAX_EXPORT_RECORDS = 64000;
}
