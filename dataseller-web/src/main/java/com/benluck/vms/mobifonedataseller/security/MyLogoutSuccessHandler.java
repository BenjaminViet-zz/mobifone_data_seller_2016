/**
 * 
 */
package com.benluck.vms.mobifonedataseller.security;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author benluck
 *
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    private transient final Logger logger = Logger.getLogger(getClass());
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private String logoutSuccessUrl = "/login.html?action=logout";

	/**
	 * @param logoutSuccessUrl the logoutSuccessUrl to set
	 */
	public void setLogoutSuccessUrl(String logoutSuccessUrl) {
		this.logoutSuccessUrl = logoutSuccessUrl;
	}
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
        String myLocalLogoutSuccessUrl = this.logoutSuccessUrl;
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals(Constants.USERGROUP_KHDN)) {
                myLocalLogoutSuccessUrl = "/tich-diem-cuoc-goi-nhan-voucher/xem-diem-doi-qua.html";
                break;
            }
        }

		Cookie terminate = new Cookie(Constants.MOBI_DATA_REMEMBER_ME_COOKIE_KEY, null);
		String contextPath = request.getContextPath();
		terminate.setPath(contextPath != null && contextPath.length() > 0 ? contextPath : "/");
		terminate.setMaxAge(0);
		response.addCookie(terminate);
		/**
		 * Delete user's cookies for content security 
		 */
		RequestUtil.deleteCookie(response, new Cookie(Constants.LOGIN_USER_ID_COOKIE, null), "/");
		RequestUtil.deleteCookie(response, new Cookie(Constants.LOGIN_CHECKSUM, null), "/");
		/**
		 * Invalidate session
		 */
		if (request.getSession(false) != null) {
			request.getSession(false).invalidate();
		}
        SecurityContextHolder.clearContext();
        request.getSession(true); //Create new session
		redirectStrategy.sendRedirect(request, response, myLocalLogoutSuccessUrl);
	}

}
