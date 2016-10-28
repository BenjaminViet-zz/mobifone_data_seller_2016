package com.benluck.vms.mobifonedataseller.utils;

import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hieu
 * Date: 11/19/13
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebConfig extends Properties{
    private transient final Logger log = Logger.getLogger(getClass());

    private static WebConfig ourInstance = new WebConfig();
    public static WebConfig getInstance() {
        return ourInstance;
    }

    private WebConfig() {
        super();
        File configFile = CommonUtil.getConfigFile("vms/vms-dataseller.properties");
        if(configFile != null) {
            try {
                InputStream input = new FileInputStream(configFile);
                Properties serverProperties = new Properties();
                serverProperties.load(input);
                //override the web config
                Set<String> propertyNames = serverProperties.stringPropertyNames();
                for (String key: propertyNames) {
                    this.put(key,serverProperties.get(key));
                }
            } catch (FileNotFoundException e) {
                log.error("Not found payment.properties file from server configuration. Please check it");
            } catch (IOException e) {
                log.error("Could not load payment.properties file from server configuration. Please check it");
            }
        }
    }

}
