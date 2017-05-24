<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/taglibs.jsp"%>

<security:authorize access="hasAuthority('ADMIN')">
    <c:redirect url="/admin/notification.html"/>
</security:authorize>
<security:authorize access="hasAuthority('VMS_USER')">
    <c:redirect url="/user/notification.html"/>
</security:authorize>
<security:authorize access="hasAuthority('KHDN')">
    <c:redirect url="/khdn/notification.html"/>
</security:authorize>
<security:authorize access="hasAuthority('NOT_GRANTTED_USER')">
    <c:redirect url="/notgrantted_user/notification.html"/>
</security:authorize>

<c:redirect url="/notsupport_user/notification.html"/>


