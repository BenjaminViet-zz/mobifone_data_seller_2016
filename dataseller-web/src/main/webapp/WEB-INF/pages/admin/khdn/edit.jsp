<%--
  Created by IntelliJ IDEA.
  User: thaihoang
  Date: 11/5/2016
  Time: 12:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<head>
    <title>
        <c:choose>
            <c:when test="${not empty item.pojo.KHDNId}">
                <fmt:message key="admin.edit_khdn.label.edit_khdn" />
            </c:when>
            <c:otherwise><fmt:message key="admin.edit_khdn.label.add_khdn" /></c:otherwise>
        </c:choose>
    </title>
    <meta name="menu" content="<fmt:message key="admin.edit_user_group.edit.heading_page" />"/>
</head>

<c:url var="backUrl" value="/admin/vendor/list.html"/>
<c:url var="formUrl" value="/admin/vendor/add.html"/>

<c:if test="${pojo.KHDNID != null}">
    <c:url var="formUrl" value="/admin/vendor/edit.html"/>
</c:if>

<div class="page-title">
    <div class="title_left">
        <h3>
            <c:choose>
                <c:when test="${not empty item.pojo.KHDNId}">
                    <fmt:message key="admin.edit_khdn.label.edit_khdn" />
                </c:when>
                <c:otherwise><fmt:message key="admin.edit_khdn.label.add_khdn" /></c:otherwise>
            </c:choose>
        </h3>
    </div>
</div>
<div class="clearfix"></div>

<c:if test="${!empty messageResponse}">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_content">
                    <div class="alert alert-${alertType} no-bottom">
                        <a class="close" data-dismiss="alert" href="#">&times;</a>
                            ${messageResponse}
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="formEdit" action="${formUrl}" method="post" validate="validate">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">
                            <fmt:message key="admin.khdn.name" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="name" path="pojo.name" cssClass="required nohtml form-control"></form:input>
                            <form:errors for="name" path="pojo.name" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="mst">
                            <fmt:message key="admin.khdn.label.mst" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="mst" path="pojo.mst" cssClass="required nohtml form-control"></form:input>
                            <form:errors for="mst" path="pojo.mst" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="gpkd">
                            <fmt:message key="admin.khdn.gpkd" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="gpkd" path="pojo.gpkd" cssClass="required nohtml form-control"></form:input>
                            <form:errors for="gpkd" path="pojo.gpkd" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="stb_vas">
                            <fmt:message key="admin.khdn.stb_vas" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="stb_vas" path="pojo.stb_vas" cssClass="required nohtml form-control money"></form:input>
                            <form:errors for="stb_vas" path="pojo.stb_vas" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="issuedContractDate">
                            <fmt:message key="admin.khdn.issued_contract_date" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12 xdisplay_inputx form-group has-feedback">
                            <input type="text" class="form-control has-feedback-left data_picker" value="<fmt:formatDate pattern="dd/MM/yyyy" dateStyle="short" value="${item.pojo.issuedContractDate}" />" name="issuedContractDate" id="issuedContractDate" aria-describedby="inputSuccess2Status4">
                            <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                                <%--<span id="inputSuccess2Status4" class="sr-only">(success)</span>--%>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="custId">
                            <fmt:message key="admin.khdn.custId" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="custId" path="pojo.custId" cssClass="required nohtml form-control"></form:input>
                            <form:errors for="gpkd" path="pojo.custId" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a href="${backUrl}" class="btn btn-success"><fmt:message key="label.huy" /></a>&nbsp;
                            <button id="btnSave" class="btn btn-primary">
                                <c:choose>
                                    <c:when test="${not empty item.pojo.KHDNId}"><fmt:message key="label.update" /></c:when>
                                    <c:otherwise><fmt:message key="label.save" /></c:otherwise>
                                </c:choose>
                            </button>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />
                    <form:hidden path="pojo.KHDNId" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        jQueryMaskNonThousandFormat();

        $("#btnSave").click(function(){
            if($('#formEdit').valid()){
                $("#formEdit").submit();
            }
        });
    });
</script>
