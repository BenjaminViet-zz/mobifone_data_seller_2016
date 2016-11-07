<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/taglibs.jsp"%>

<security:authorize access="hasAuthority('ADMIN')">
    <%--<c:redirect url="/admin/dashboard.html"/>--%>
    <c:redirect url="/admin/packagedatacodegen/list.html"/>
</security:authorize>
<c:redirect url="/dashboard.html"/>


