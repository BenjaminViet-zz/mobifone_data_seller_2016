package com.benluck.vms.mobifonedataseller.common.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.sql.Clob;

public class DatabaseUtils {
	private static final Logger logger = Logger.getLogger(DatabaseUtils.class);
	public static String clobStringConversion(Clob clb)
    {
      if (clb == null)
     return  "";
            
      StringBuffer str = new StringBuffer();
      String strng;
              
      try {
    	  BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());
	      while ((strng=bufferRead .readLine())!=null) {
	       str.append(strng);
	      }
      }catch (Exception e) {
    	  logger.error(e.getMessage(), e);
      }
   
      return str.toString();
    }  
	public static String clobObjectStringConversion(Object clb)
    {
      if (clb == null) {
    	  return  "";
      }
      if(! (clb instanceof Clob)) {
    	  return clb.toString();
      }
      Clob _clb = (Clob)clb;
      
      StringBuffer str = new StringBuffer();
      String strng;
      try {
    	  BufferedReader bufferRead = new BufferedReader(_clb.getCharacterStream());
	      while ((strng=bufferRead .readLine())!=null) {
	       str.append(strng);
	      }
      }catch (Exception e) {
    	  logger.error(e.getMessage(), e);
      }
      return str.toString();
    }  
}
