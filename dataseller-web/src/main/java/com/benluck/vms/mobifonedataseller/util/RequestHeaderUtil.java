/**
 * 
 */
package com.benluck.vms.mobifonedataseller.util;

import com.benluck.vms.mobifonedataseller.common.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author benluck
 *
 */
public class RequestHeaderUtil {
	public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static String getRefererURL(HttpServletRequest request) {
        return request.getHeader("Referer");
    }
	
	public static String getScreensize(HttpServletRequest request) {
		return request.getHeader("UA-Pixels");
	}

	public static String getPageURL(HttpServletRequest request) {
        if (request == null) return "";
        
        StringBuffer url = new StringBuffer();
        int port = request.getServerPort();
        if (port < 0) {
            port = 80; // Work around java.net.URL bug
        }
        String scheme = request.getScheme();
        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(request.getRequestURI());
        return url.toString();
    }
	
}
