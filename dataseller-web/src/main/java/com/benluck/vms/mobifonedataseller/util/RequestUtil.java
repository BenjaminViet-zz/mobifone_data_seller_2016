package com.benluck.vms.mobifonedataseller.util;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.dto.AbstractSearchDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Convenience class for setting and retrieving cookies.
 */
public final class RequestUtil {
	private static final Logger log = Logger.getLogger(RequestUtil.class);

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private RequestUtil() {
    }

    /**
     * Convenience method to set common.a cookie
     *
     * @param response the current response
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param path the path to set it on
     */
    public static void setCookie(HttpServletResponse response, String name,
                                 String value, String path) {
        if (log.isDebugEnabled()) {
            log.debug("Setting cookie '" + name + "' on path '" + path + "'");
        }

        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(false);
        cookie.setPath(path);
        cookie.setMaxAge(3600 * 24 * 30); // 30 days

        response.addCookie(cookie);
    }

    /**
     * Convenience method to get common.a cookie by name
     *
     * @param request the current request
     * @param name the name of the cookie to find
     *
     * @return the cookie (if found), null if not found
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        Cookie returnCookie = null;

        if (cookies == null) {
            return returnCookie;
        }

        for (final Cookie thisCookie : cookies) {
            if (thisCookie.getName().equals(name) && !"".equals(thisCookie.getValue())) {
                returnCookie = thisCookie;
                break;
            }
        }

        return returnCookie;
    }

    /**
     * Convenience method for deleting common.a cookie by name
     *
     * @param response the current web response
     * @param cookie the cookie to delete
     * @param path the path on which the cookie was set
     */
    public static void deleteCookie(HttpServletResponse response,
                                    Cookie cookie, String path) {
        if (cookie != null) {
            // Delete the cookie by setting its maximum age to zero
            cookie.setMaxAge(0);
            cookie.setPath(path);
            response.addCookie(cookie);
        }
    }
    
    public static void deleteCookie(HttpServletResponse response, String cookieName, String path) {
    	deleteCookie(response, new Cookie(cookieName, null), path);
    }

    /**
     * Convenience method to get the application's URL based on request
     * variables.
     * 
     * @param request the current request
     * @return URL to application
     */
    public static String getAppURL(HttpServletRequest request) {
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
        url.append(request.getContextPath());
        return url.toString();
    }

    /**
     * Convenience method to get the server's URL based on request
     * variables not contain context path.
     *
     * @param request the current request
     * @return URL to server
     */
    public static String getServerURL(HttpServletRequest request) {
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
        return url.toString();
    }

    /**
     * init the SearchBean from request for pageing user list.
     * @param request HttpServletRequest
     * @param bean AbstractCommand
     */
    public static void initSearchBean(HttpServletRequest request, AbstractCommand bean) {
        if (bean != null){
            String sortExpression = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String sPage = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer page = 1;
            if (StringUtils.isNotBlank(sPage)) {
                try {
                    page = Integer.valueOf(sPage);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            bean.setPage(page);
            bean.setFirstItem((bean.getPage() - 1) * bean.getMaxPageItems());
            bean.setSortExpression(sortExpression);
            bean.setSortDirection(sortDirection);
        }
    }


    public static void initSearchBean4report(HttpServletRequest request, AbstractCommand bean) {
        if (bean != null){
            String sortExpression = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String sPage = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer page = 1;
            if (StringUtils.isNotBlank(sPage)) {
                try {
                    page = Integer.valueOf(sPage);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            bean.setPage(page);
            bean.setFirstItem((bean.getPage() - 1) * Constants.REPORT_MAXPAGEITEMS);
            bean.setSortExpression(sortExpression);
            bean.setSortDirection(sortDirection);
        }
    }

    public static void initSearchBean(HttpServletRequest request, AbstractSearchDTO bean) {
        if (bean != null){
            String sortExpression = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String sPage = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer page = 1;
            if (StringUtils.isNotBlank(sPage)) {
                try {
                    page = Integer.valueOf(sPage);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            bean.setPage(page);
            bean.setFirstItem((bean.getPage() - 1) * bean.getMaxPageItems());
            bean.setSortExpression(sortExpression);
            bean.setSortDirection(sortDirection);
        }
    }
    
	public static String encodeUTF8(String s) {
		String result = null;

		try {
			result = URLEncoder.encode(s, "UTF-8");
		}

		// This exception should never occur.
		catch (UnsupportedEncodingException e) {
			result = s;
		}

		return result;
	}


    public static void saveLastReportURL(HttpServletRequest request, String varName){
        request.getSession().setAttribute(varName, request.getServletPath());
        request.getSession().setAttribute(varName + "QueryString", request.getQueryString());
    }
    public static void saveLastReportParams(HttpServletRequest request, String varName){
        request.getSession().setAttribute(varName, request.getQueryString());
    }
    public static String getLastReportParams(HttpServletRequest request, String varName){
        return (String)request.getSession().getAttribute(varName);
    }
    public static void saveLastUrlWithParams(HttpServletRequest request, String varName, Map<String,String> params){
        request.getSession().setAttribute(varName, request.getServletPath());
        request.getSession().setAttribute(varName + "QueryString", request.getQueryString());
        request.getSession().setAttribute(varName + "Params", params);
    }

    public static void clearLastReportURL(HttpServletRequest request, String varName){
        request.getSession().removeAttribute(varName);
        request.getSession().removeAttribute(varName + "QueryString");
    }

    public static String getClusterSessionId(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        int index = sessionId.indexOf(".");
        String partOfKey = index > 0 ? sessionId.substring(0, index) : sessionId;
        return partOfKey;
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (StringUtils.isNotBlank(ip) && ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static String getServerIP(HttpServletRequest request) {
        if (request == null) return "";
        return request.getLocalAddr();
    }

}
