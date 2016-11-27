<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/taglibs.jsp"%>

<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:redirect url="/admin/notification.html"/>
</security:authorize>
<security:authorize access="hasAnyAuthority('VMS_USER')">
    <c:redirect url="/user/notification.html"/>
</security:authorize>
<security:authorize access="hasAnyAuthority('KHDN')">
    <c:redirect url="/khdn/notification.html"/>
</security:authorize>
<c:redirect url="/custom_user/notification.html"/>


