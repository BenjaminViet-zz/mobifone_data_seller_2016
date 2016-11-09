<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>

<div class="col-md-3 left_col">
    <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
            <%--<a href="index.html" class="site_title"><i class="fa fa-paw"></i> <span>MOBIFONE</span></a>--%>
                <a href="index.html" class="site_title"><img src="/themes/mobifonedata/images/logo.png" style="width:180px"></a>
        </div>

        <div class="clearfix"></div>

        <!-- menu profile quick info -->
        <div class="profile">
            <div class="profile_pic">
                <img src="<c:url value="/themes/newteample/content/images/img.jpg"/>" alt="..." class="img-circle profile_img">
            </div>
            <div class="profile_info">
                <span><fmt:message key="label.welcome" /></span>
            </div>
        </div>
        <!-- /menu profile quick info -->

        <br/>

        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">
                <h3><%=SecurityUtils.getPrincipal().getDisplayName()%></h3>
                <ul class="nav side-menu">
                    <security:authorize access="hasAuthority('ADMIN')">
                        <li><a><i class="fa fa-credit-card" aria-hidden="true"></i> <fmt:message key="packagedatacodegen.admin_nav.manager" /> <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                <li><a href="<c:url value="${prefix}/packagedatacodegen/list.html" />"><fmt:message key="packagedatacodegen.admin_nav.generation" /></a></li>
                            </ul>
                        </li>
                        <li><a><i class="fa fa-user-o" aria-hidden="true"></i> <fmt:message key="user.manager" /> <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                <li><a href="<c:url value="${prefix}/user/list.html" />"><fmt:message key="user.manager.list" /></a></li>
                            </ul>
                        </li>
                        <li><a><i class="fa fa-lock" aria-hidden="true"></i> <fmt:message key="usergroup.manager" /> <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                <li><a href="<c:url value="${prefix}/usergroup/list.html" />"><fmt:message key="usergroup.manager.list" /></a></li>
                            </ul>
                        </li>
                        <li><a><i class="fa fa-database" aria-hidden="true"></i> <fmt:message key="package_data.manager" /> <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                <li><a href="<c:url value="${prefix}/package_data/list.html" />"><fmt:message key="package_data.manager.list" /></a></li>
                            </ul>
                        </li>
                        <li><a><i class="fa fa-users" aria-hidden="true"></i> <fmt:message key="khdn.manager"/> <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                <li><a href="<c:url value="${prefix}/vendor/list.html"/> "><fmt:message key="khdn.manager.list"/></a></li>
                            </ul>
                        </li>
                        <li><a><i class="fa fa-bar-chart-o"></i> <fmt:message key="cost.manager" /> <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                <li><a href="<c:url value="${prefix}/cost/list.html"/> "><fmt:message key="cost.manager.list" /> </a></li>
                            </ul>
                        </li>
                        <li><a><i class="fa fa-clone"></i> <fmt:message key="order.manager" /> <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                <li><a href="<c:url value="${prefix}/order/list.html"/> "><fmt:message key="order.manager.list" /></a></li>
                            </ul>
                        </li>
                    </security:authorize>
                    <li><a><i class="fa fa-pie-chart" aria-hidden="true"></i> <fmt:message key="report.manager" /> <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="<c:url value="${prefix}/report/tong_hop_chi_phi_phat_trien_duy_tri.html"/> "><fmt:message key="report.baocaotonghopchiphiphattrien_duytri" /></a></li>
                            <li><a href="<c:url value="${prefix}/report/chi_tiet_chi_phi_phat_trien_duy_tri.html"/> "><fmt:message key="report.baocaochitietchiphiphattrien_duytri" /></a></li>
                            <li><a href="<c:url value="${prefix}/report/theo_doi_khach_hang.html"/> "><fmt:message key="report.theodoihopdongkhachang" /></a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <!-- /sidebar menu -->

        <!-- /menu footer buttons -->
        <div class="sidebar-footer hidden-small">

        </div>
        <!-- /menu footer buttons -->
    </div>
</div>