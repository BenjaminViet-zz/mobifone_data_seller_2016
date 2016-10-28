package com.benluck.vms.mobifonedataseller.util;

import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 * Overriding PropertyConfigurer to make the application switchable using properties files
 *
 * @author benluck
 */
public class MyPropertyConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {
    public MyPropertyConfigurer() {
        super();
        File configFile = CommonUtil.getConfigFile("vms/vms-dataseller.properties");
        setLocations(new Resource[]{ new FileSystemResource(configFile)});
    }
}
