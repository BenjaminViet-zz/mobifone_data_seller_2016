<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>

<c:set var="prefix" value="/user" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>

<div class="top_nav">
    <div class="nav_menu">
        <nav>
            <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
            </div>

            <ul class="nav navbar-nav navbar-right">
                <li class="">
                    <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown"
                       aria-expanded="false">
                        <img src="<c:url value="/themes/newteample/content/images/img.jpg" />" alt=""><%=SecurityUtils.getPrincipal().getDisplayName()%>
                        <span class=" fa fa-angle-down"></span>
                    </a>
                    <ul class="dropdown-menu dropdown-usermenu pull-right">
                        <li><a href="<c:url value="${prefix}/profile.html"/>"> <fmt:message key="user.profile" /> </a></li>
                        <security:authorize ifAnyGranted="ADMIN">
                            <li><a href="<c:url value="${prefix}/dashboard.html"/>"><fmt:message key="user.dashboard_warning_page" /> </a></li>
                        </security:authorize>
                        <li><a href="logout.jsp"><i class="fa fa-sign-out pull-right"></i><fmt:message key="logout" /></a></li>
                    </ul>
                </li>

                <li role="presentation" class="dropdown">
                    <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown"
                       aria-expanded="false">
                        <i class="fa fa-envelope-o"></i>
                        <span class="badge bg-green">0</span>
                    </a>
                    <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                        <li>
                            <span class="message">
                              <fmt:message key="dashboard.no_warning" />
                            </span>
                        </li>
                    </ul>
                </li>
            </ul>
        </nav>
    </div>
</div>
<!-- /top navigation -->