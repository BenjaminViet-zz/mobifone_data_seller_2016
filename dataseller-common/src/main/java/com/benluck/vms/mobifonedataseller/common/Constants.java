
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

    /**
     * Spring Credential delimiter.
     */
    public static final String SECURITY_CREDENTIAL_DELIMITER = "${SEC_CRED}";


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

    public static final String ACCOUNT_ROLE = "KHDN";

    public static final String LOGON_ROLE = "LOGON";


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
    public static final int USER_INACTIVE = 0;
    public static final int USER_ACTIVE = 1;

    public static final int USER_NOT_LDAP = 0;
    public static final int USER_LDAP = 1;

    public static final String MOBI_DATA_REMEMBER_ME_COOKIE_KEY = "MOBI_DATA_REMEMBER_ME_COOKIE_KEY";

    public static final String MESSAGE_RESPONSE_MODEL_KEY = "messageResponse";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_INSERT = "insert";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_SEARCH = "search";
    public static final String ACTION_EXPORT = "export";
    public static final String ACTION_UPLOAD = "upload";
    public static final String ACTION_IMPORT = "import";

    public static final String USERGROUP_ADMIN = "ADMIN";
    public static final String USERGROUP_KHDN = "KHDN";
    public static final String USERGROUP_VMS_USER = "VMS_USER";

    public static final String ALERT_TYPE = "alertType";

//    Order Constants
    public static final Integer ORDER_STATUS_PROCESSING = 0;
    public static final Integer ORDER_STATUS_FINISH = 1;

    public static final Integer ORDER_CARD_CODE_NOT_START_STATUS = 0;
    public static final Integer ORDER_CARD_CODE_PROCESSING_STATUS = 1;
    public static final Integer ORDER_CARD_CODE_COMPLETED_STATUS = 2;
    public static final Integer ORDER_CARD_CODE_FAILED_STATUS = 3;

    public static final Integer ORDER_ACTIVE_STATUS_ALIVE = 0;
    public static final Integer ORDER_ACTIVE_STATUS_DIE = 1;

    public static final Integer ADMIN_EXPORT_4_KHDN = 0;
    public static final Integer ADMIN_EXPORT_4_MOBIFONE = 1;

    // Import Constants
    public static final String ORDER_IMPORT_FILE_CACHE_KEY = "ORDER_IMPORT_FILE_CACHE_KEY";

    public static final Integer IMPORT_ORDER_STEP_1_CHOOSE_FILE = 0;
    public static final Integer IMPORT_ORDER_STEP_2_UPLOAD = 1;
    public static final Integer IMPORT_ORDER_STEP_3_IMPORT = 2;

//    Order History Constants
    public static final Integer ORDER_HISTORY_OPERATOR_CREATED = 0;
    public static final Integer ORDER_HISTORY_OPERATOR_UPDATED = 1;
    public static final Integer ORDER_HISTORY_OPERATOR_DELETED = 2;

//    Order Data Code prefix
    public static final String DATAT_CODE_VMS_PREFIX = "2";

//    Used Data Code Key and HashKey
    public static final String KEY_USED_2016 = DATAT_CODE_VMS_PREFIX + "16";
    public static final String HAS_KEY_USED_2016UNIT_PRICE_10 = "10_USED";

    public static final int ORDER_DATA_CODE_SERIAL_OFFSET = 15000;

//    Package Data Code Gen Status
    public static final Integer PACKAGE_DATA_CODE_GEN_ALREADY = 1;
    public static final Integer PACKAGE_DATA_CODE_GEN_NOT_YET = 0;

    public static final Integer PACKAGE_DATA_CODE_GEN_STATUS_PROCESSING = 0;
    public static final Integer PACKAGE_DATA_CODE_GEN_STATUS_FAILED = 1;
    public static final Integer PACKAGE_DATA_CODE_GEN_STATUS_SUCCESS = 2;

    public static final String USED_UNIT_PRICE_CODE = "10";

    // Notification Constants
    public static final Integer NOTIFICATION_NOT_YET_READ = 0;
    public static final Integer NOTIFICATION_READ_ALREADY = 1;

    public static final Integer MAX_NOTIFICATION_MESSAGE_POPUP = 5;

    public static final String GENERATE_CARD_CODE_FINISH_SUCCESS = "GEN_CARD_SUCCESS";
    public static final String GENERATE_CARD_CODE_FINISH_FAILED = "GEN_CARD_FAILED";
    public static final String TAKE_CARD_CODE_4_ORDER_SUCCESS = "ORDER_CARD_SUCCESS";
    public static final String TAKE_CARD_CODE_4_ORDER_FAILED = "ORDER_CARD_FAILED";

    public static final String REDIS_CACHE_KEY_CARD_CODE_TAKING = "REDIS_CACHE_KEY_CARD_CODE_TAKING";

    // Code History
    public static final String COST_PAYMENT_NOT_PAID = "0";
    public static final String COST_PAYMENT_PAID = "1";

    // Permission Code
    public static final String USER_MANAGER = "USER_MANAGER";
    public static final String USER_GROUP_MANAGER = "USER_GROUP_MANAGER";
    public static final String KHDN_MANAGER = "KHDN_MANAGER";
    public static final String PACKAGE_DATA_MANAGER = "PACKAGE_DATA_MANAGER";
    public static final String GENERATE_CARD_CODE_MANAGER = "GENERATE_CARD_CODE_MANAGER";
    public static final String EXPENSE_MANAGER = "EXPENSE_MANAGER";
    public static final String ORDER_MANAGER = "ORDER_MANAGER";
    public static final String REPORT_MANAGER = "REPORT_MANAGER";

    // Code History
    public static final Double PACKAGE_KHDN_DATA_VALUE = 100000000D;

    //
    public static final String USED_CARD_CODE_PREFIX = "10";

    // Used Card Code Cache Key
    public static final String USED_CARD_CODE_CACHE_KEY = "USED_CARD_CODE_CACHE_KEY";
    public static final Integer IMPORT_CARD_CODE_STEP_1_CHOOSE_FILE = 0;
    public static final Integer IMPORT_CARD_CODE_STEP_2_UPLOAD = 1;
    public static final String IMPORT_USED_CARD_CODE_SESSION_CACHE_KEY = "IMPORT_USED_CARD_CODE_SESSION_CACHE_KEY";
}
