<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2/25/15
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fnt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="cuahanggiaodich.thongtinthuebao.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="cuahanggiaodich.thongtinthuebao.heading_page" />"/>
    <style>
        .text-form{
            top: 5px;
        }
    </style>
</head>

<c:set var="prefix" value="/cuahangmobifone" />
<security:authorize ifAnyGranted="TONGDAI">
    <c:set var="prefix" value="/tongdai" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/thuebaophattrienmoi/tracuuthongtinthuebao.html"/>
<div class="ajax-progress"></div>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="cuahanggiaodich.thongtinthuebao.heading_page" /></h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">

        <div class="panel-body panel-body-nopadding">
            <div style="clear: both;" ></div>
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="searchForm" action="${formUrl}" method="post" autocomplete="off">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <c:if test ="${not empty messageResponse}">
                                        <div class="alert alert-${alertType}">
                                            <a class="close" data-dismiss="alert" href="#">&times;</a>
                                                ${messageResponse}
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.so_thue_bao" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="sothuebao" path="thue_bao" cssClass="required nohtml validate_phone_number form-control" />
                                        <form:errors for="sothuebao" path="thue_bao" cssClass="error-inline-validate" />
                                        <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <input type="hidden" name="crudaction" value="search" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#menuThongTinThueBaoTab', '#traCuuThongTinThueBao2015');

        jQuery.validator.addClassRules('validate_phone_number', {
            validatePhoneNumber: true
        });

        jQuery.validator.addMethod("validatePhoneNumber", function () {
            return validatePhoneNumber($.trim($('#sothuebao').val()));
        }, "<fmt:message key="user.msg.invalid_phone_number"/>");
    });

    function submitForm(){
        if($('#searchForm').valid()){
            $('#searchForm').submit();
        }
    }

</script>
