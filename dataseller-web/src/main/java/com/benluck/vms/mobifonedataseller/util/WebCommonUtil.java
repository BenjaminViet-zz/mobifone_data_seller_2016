package com.benluck.vms.mobifonedataseller.util;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA
 * Date: 4/10/13
 * Time: 6:49 PM
 */
public class WebCommonUtil {
    private static transient final Logger log = Logger.getLogger(WebCommonUtil.class);

    public static String getBaseFolder() {
        return "/files/";
    }

    public static String getTempFolderName() {
        return "temp";
    }

    /*
      * get extension of file
      */
    public static String getExtension(String fileName) {
        return (fileName.indexOf(".") < fileName.length()) ? fileName
                .substring(fileName.lastIndexOf(".") + 1) : "";
    }

    /*
      * get fileName without extension
      */
    public static String getNameWithoutExtension(String fileName) {
        return (fileName.indexOf(".") > 0) ? fileName.substring(0, fileName
                .lastIndexOf(".")) : fileName;
    }

    private static final Pattern EMAIL_P = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern ZIP_P = Pattern.compile("\\d{5}(-\\d{4})?");
    private static final Pattern USERNAME_P = Pattern.compile("^[A-Za-z0-9_-]{3,25}");


    public static boolean isValidEmail(String email) {
        Matcher m = EMAIL_P.matcher(email);
        return m.matches();
    }

    public static boolean isValidZip(String email) {
        Matcher m = ZIP_P.matcher(email);
        return m.matches();
    }

    public static boolean isValidUsername(String username) {
        Matcher m = USERNAME_P.matcher(username);
        return m.matches();
    }



    public static String getSessionWithoutSuffix(String jSession) {
        int index = jSession.indexOf(".");
        return index > 0 ? jSession.substring(0, index) : jSession;
    }



    public static Integer compare2Double(Double value, Double compareValue){
        return new Integer(value.compareTo(compareValue));
    }

    public static CellValue[] addNgayXuatBaoCao(Integer totalCell){
        CellValue[] resValue = new CellValue[totalCell];
        for(int i = 0; i < totalCell; i++){
            resValue[i] = new CellValue();
        }
        return resValue;
    }

    public static String[] splitUsernameAndPassword(String input) {
        String username = "";
        String password = "";
        String[] tmp = input.split(Pattern.quote(Constants.SECURITY_CREDENTIAL_DELIMITER));
        username = tmp[0];
        if (tmp.length > 1) {
            password = tmp[1];
        }

        return new String[] {username, password};
    }
}
