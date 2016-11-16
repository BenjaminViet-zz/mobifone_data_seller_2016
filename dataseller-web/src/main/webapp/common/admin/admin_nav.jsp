<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize access="hasAnyAuthority('KHDN')">
    <c:set var="prefix" value="/khdn" />
</security:authorize>

<div class="col-md-3 left_col">
    <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
            <%--<a href="index.html" class="site_title"><i class="fa fa-paw"></i> <span>MOBIFONE</span></a>--%>
                <a href="index.html" class="site_title"><img src="/themes/mobifonedata/images/logo.png" style="width:180px"></a>
        </div>

        <div class="clearfix"></div>

        <br/>

        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">

                <ul class="nav side-menu">
                    <%-- NEW UPDATED --%>
                    <li><a><i class="fa fa-user" aria-hidden="true"></i> <fmt:message key="left_nav_management_system" /> <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="<c:url value="${prefix}/notification.html" />"><fmt:message key="left_nav_notification" /></a></li>
                            <security:authorize access="hasAnyAuthority('ADMIN', 'USER_MANAGER')">
                                <li><a><fmt:message key="left_nav_user_management" /><span class="fa fa-chevron-down"></span></a>
                                    <ul class="nav child_menu">
                                        <li><a href="<c:url value="${prefix}/user/add.html"/>"><fmt:message key="left_nav_user_add" /></a></li>
                                        <li><a href="<c:url value="${prefix}/user/list.html" />"><fmt:message key="left_nav_user_research" /></a></li>
                                    </ul>
                                </li>
                            </security:authorize>
                            <security:authorize access="hasAnyAuthority('ADMIN', 'USER_GROUP_MANAGER')">
                                <li><a><fmt:message key="left_nav_access_permission" /><span class="fa fa-chevron-down"></span></a>
                                    <ul class="nav child_menu">
                                        <li><a href="<c:url value="${prefix}/usergroup/add.html"/>"><fmt:message key="left_nav_permission_add" /></a></li>
                                        <li><a href="<c:url value="${prefix}/usergroup/list.html" />"><fmt:message key="left_nav_permission_research" /></a></li>
                                    </ul>
                                </li>
                            </security:authorize>
                            <security:authorize access="hasAnyAuthority('ADMIN', 'VMS_USER', 'PACKAGE_DATA_MANAGER')">
                                <li><a href="<c:url value="${prefix}/package_data/list.html" />"><fmt:message key="left_nav_package_category" /></a></li>
                            </security:authorize>
                            <security:authorize access="hasAnyAuthority('ADMIN', 'VMS_USER', 'KHDN_MANAGER')">
                                <li><a><fmt:message key="left_nav_KHDN_category" /><span class="fa fa-chevron-down"></span></a>
                                    <ul class="nav child_menu">
                                        <li><a href="<c:url value="${prefix}/khdn/add.html"/>"><fmt:message key="left_nav_KHDN_add"/></a></li>
                                        <li><a href="<c:url value="${prefix}/khdn/list.html" />"><fmt:message key="left_nav_KHDN_list"/></a></li>
                                        <li><a href="<c:url value="${prefix}/khdn/import.html" />"><fmt:message key="khdn.import.manager"/></a></li>
                                    </ul>
                                </li>
                            </security:authorize>
                        </ul>
                    </li>
                    <security:authorize access="hasAnyAuthority('ADMIN', 'VMS_USER', 'ORDER_MANAGER', 'GENERATE_CARD_CODE_MANAGER')">
                    <li><a><i class="fa fa-files-o" aria-hidden="true"></i> <fmt:message key="left_nav_order_management" /> <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="<c:url value="${prefix}/order/add.html"/>"><fmt:message key="left_nav_order_create" /></a></li>
                            <li><a href="<c:url value="${prefix}/order/list.html"/>"><fmt:message key="left_nav_order_research" /></a></li>
                            <li><a href="<c:url value="${prefix}/packagedatacodegen/list.html" />"><fmt:message key="left_nav_data_code_research" /></a></li>
                        </ul>
                    </li>
                    </security:authorize>

                    <security:authorize access="hasAnyAuthority('ADMIN', 'VMS_USER', 'EXPENSE_MANAGER')">
                        <li><a><i class="fa fa-money" aria-hidden="true"></i> <fmt:message key="cost.manager" /> <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                    <%--<li><a href=""><fmt:message key="left_nav_solve_history" /></a></li>--%>
                                <li><a href="<c:url value="${prefix}/payment/management.html"/> "><fmt:message key="left_nav_payment_fee" /> </a></li>
                                <li><a href="<c:url value="${prefix}/payment/history.html"/> "><fmt:message key="left_nav_payment_history" /></a></li>
                            </ul>
                        </li>
                    </security:authorize>

                    <security:authorize access="hasAnyAuthority('ADMIN', 'VMS_USER', 'REPORT_MANAGER')">
                        <li><a><i class="fa fa-pie-chart" aria-hidden="true"></i> <fmt:message key="report.manager" /> <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                <li><a href="<c:url value="${prefix}/reportGeneralExpense/list.html"/> "><fmt:message key="report.baocaotonghopchiphiphattrien_duytri" /></a></li>
                                <li><a href="<c:url value="${prefix}/reportDetailExpense/list.html"/> "><fmt:message key="report.baocaochitietchiphiphattrien_duytri" /></a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </ul>
            </div>
        </div>
        <!-- /sidebar menu -->
    </div>
</div>