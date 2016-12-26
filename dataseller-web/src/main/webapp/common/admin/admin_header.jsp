<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize access="hasAnyAuthority('KHDN')">
    <c:set var="prefix" value="/khdn" />
</security:authorize>
<c:url var="notificationURL" value="${prefix}/notification.html" />

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
                        <%--<img src="<c:url value="/themes/newteample/content/images/img.jpg" />" alt="">--%><%=SecurityUtils.getPrincipal().getDisplayName()%>
                        <span class=" fa fa-angle-down"></span>
                    </a>
                    <ul class="dropdown-menu dropdown-usermenu pull-right">
                        <li><a href="<c:url value="${prefix}/profile.html"/>"> <fmt:message key="user.profile" /> </a></li>
                        <li><a href="<c:url value="${prefix}/dashboard.html"/>"><fmt:message key="user.dashboard_warning_page" /> </a></li>
                        <li><a href="<c:url value="/logout.jsp"/> "><i class="fa fa-sign-out pull-right"></i><fmt:message key="logout" /></a></li>
                    </ul>
                </li>

                <li role="presentation" class="dropdown">
                    <c:set var="notificationList" value="<%=SecurityUtils.getPrincipal().getNotificationList()%>" />

                    <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown"
                       aria-expanded="false">
                        <i class="fa fa-bell-o"></i>
                        <span class="badge bg-green">${notificationList.size()}</span>
                    </a>

                    <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                        <c:choose>
                            <c:when test="${notificationList.size() > 0}">
                                <c:forEach items="${notificationList}" var="notification" varStatus="status">
                                    <c:if test="${status.count <= Constants.MAX_NOTIFICATION_MESSAGE_POPUP - 1}">
                                        <li>
                                            <a>
                                                <span>
                                                    <span>
                                                        <c:choose>
                                                            <c:when test="${notification.messageType eq Constants.GENERATE_CARD_CODE_FINISH_SUCCESS || notification.messageType eq Constants.GENERATE_CARD_CODE_FINISH_FAILED}">
                                                                <fmt:message key="notification.popup.generate_card_code_message_type" />
                                                            </c:when>
                                                            <c:when test="${notification.messageType eq Constants.TAKE_CARD_CODE_4_ORDER_SUCCESS || notification.messageType eq Constants.TAKE_CARD_CODE_4_ORDER_FAILED}">
                                                                <fmt:message key="notification.popup.take_card_code_message_type" />
                                                            </c:when>
                                                            <c:when test="${notification.messageType eq Constants.IMPORT_USED_CARD_CODE_SUCCESS || notification.messageType eq Constants.IMPORT_USED_CARD_CODE_FAILED}">
                                                                <fmt:message key="notification.popup.generate_card_code_message_type" />
                                                            </c:when>
                                                        </c:choose>
                                                    </span>
                                                      <span class="time">
                                                          <fmt:formatDate value="${notification.createdDate}" pattern="${dateTimePattern}" />
                                                      </span>
                                                </span>
                                                <span class="message">
                                                  ${notification.message}
                                                </span>
                                            </a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${notificationList.size() >= Constants.MAX_NOTIFICATION_MESSAGE_POPUP}">
                                    <li>
                                        <div class="text-center">
                                            <a href="${notificationURL}">
                                                <strong><fmt:message key="dashboard.notification.see_all_notifications" /></strong>
                                                <i class="fa fa-angle-right"></i>
                                            </a>
                                        </div>
                                    </li>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <span class="message">
                                      <fmt:message key="dashboard.no_warning" />
                                    </span>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </li>
            </ul>
        </nav>
    </div>
</div>
<!-- /top navigation -->