<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<ul class="headermenu">
    <li>
        <div class="btn-group">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                <security:authorize ifAnyGranted="ADMIN">
                    <img src="<c:url value="/themes/admin/images/photos/admin_logged.png" />" alt="" />
                </security:authorize>
                <security:authorize ifAnyGranted="CHINHANH,NHANVIEN,TONGDAI">
                    <img src="<c:url value="/themes/admin/images/photos/user_logged.png" />" alt="" />
                </security:authorize>
                <%=SecurityUtils.getPrincipal().getDisplayName()%>
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                <security:authorize ifAnyGranted="ADMIN">
                    <li><a href="<c:url value="/admin/thongtincanhan.html" />"><i class="glyphicon glyphicon-log-out"></i>Thông tin cá nhân</a></li>
                </security:authorize>
                <security:authorize ifAnyGranted="NHANVIEN">
                    <li><a href="<c:url value="/cuahangmobifone/thongtincanhan.html" />"><i class="glyphicon glyphicon-log-out"></i>Thông tin cá nhân</a></li>
                </security:authorize>
                <security:authorize ifAnyGranted="CHINHANH">
                    <li><a href="<c:url value="/chinhanh/thongtincanhan.html" />"><i class="glyphicon glyphicon-log-out"></i>Thông tin cá nhân</a></li>
                </security:authorize>
                <security:authorize ifAnyGranted="TONGDAI">
                    <li><a href="<c:url value="/tongdai/thongtincanhan.html" />"><i class="glyphicon glyphicon-log-out"></i>Thông tin cá nhân</a></li>
                </security:authorize>
                <li><a href="logout.jsp"><i class="glyphicon glyphicon-log-out"></i>Logout</a></li>
            </ul>
        </div>
    </li>
</ul>