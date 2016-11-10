package com.benluck.vms.mobifonedataseller.utils;

import vn.mobifone.sercurity.MobiFoneSercurity;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/10/16
 * Time: 20:53
 * To change this template use File | Settings | File Templates.
 */
public class MobiFoneSecurityBase64Util {

    public static String encode(String code){
        return MobiFoneSercurity.encode(code);
    }

    public static String decode(String code){
        return MobiFoneSercurity.decode(code);
    }

    public static HashSet<String> decodeFromHS(HashSet<String> encodedHS){
        HashSet<String> decodedHS = new HashSet<String>();
        Iterator<String> ito = encodedHS.iterator();
        while (ito.hasNext()){
            decodedHS.add(decode(ito.next()));
        }
        return decodedHS;
    }
}
