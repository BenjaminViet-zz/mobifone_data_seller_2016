package com.benluck.vms.mobifonedataseller.security.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/10/16
 * Time: 19:31
 * To change this template use File | Settings | File Templates.
 */
public class SHA256Util {
    private static Logger logger = Logger.getLogger(SHA256Util.class);

    public static String hash(String str){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());

            byte[] dataBytes = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < dataBytes.length; i++) {
                sb.append(Integer.toString((dataBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException nse){
            logger.error("Can not get instance of Algorithm SHA-256");
            return null;
        }
    }
}
