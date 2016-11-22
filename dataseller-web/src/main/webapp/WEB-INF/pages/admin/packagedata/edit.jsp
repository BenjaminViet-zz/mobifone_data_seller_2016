<%--
  Created by IntelliJ IDEA.
  User: thaihoang
  Date: 10/31/2016
  Time: 10:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="titlePage" value="admin.packagedata.add.heading_page" />
<c:if test="${not empty item.pojo.packageDataId}">
    <c:set var="titlePage" value="admin.packagedata.edit.heading_page" />
</c:if>
<head>
    <title><fmt:message key="${titlePage}" /></title>
    <meta name="menu" content="<fmt:message key="${titlePage}" />"/>
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>

<c:url var="formUrl" value="${prefix}/package_data/add.html" />
<c:if test="${not empty item.pojo.packageDataId}">
    <c:url var="formUrl" value="${prefix}/package_data/edit.html" />
</c:if>
<c:url var="backUrl" value="${prefix}/package_data/list.html" />

<div class="clearfix"></div>
<div id="message_section">
    <c:if test ="${not empty messageResponse}">
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_content">
                        <div class="alert alert-${alertType} no-bottom">
                            <a class="close" data-dismiss="alert" href="#">&times;</a>
                                ${messageResponse}
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="formEdit" action="${formUrl}" method="post" validate="validate">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name"><fmt:message key="packagedata.label.tenGoiCuoc" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="name" path="pojo.name" class="form-control required" />
                            <form:errors for="name" path="pojo.name" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="value"><fmt:message key="packagedata.label.giaGoiCuoc" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="value" type="text" name="pojo.value" min="1" class="form-control required money" value="<fmt:formatNumber type="number" maxFractionDigits="0" value="${item.pojo.value}" /> " />
                            <form:errors for="value" path="pojo.value" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="customPrefixUnitPrice"><fmt:message key="packagedata.label.prefix_card_code" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="customPrefixUnitPrice" type="text" name="pojo.customPrefixUnitPrice" min="0" max="99" class="form-control money" value="<fmt:formatNumber type="number" maxFractionDigits="0" value="${item.pojo.customPrefixUnitPrice}" /> " />
                            <form:errors for="customPrefixUnitPrice" path="pojo.customPrefixUnitPrice" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="volume"><fmt:message key="packagedata.label.dungLuongMienPhi" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="volume" path="pojo.volume" class="form-control required" />
                            <form:errors for="volume" path="pojo.volume" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="duration"><fmt:message key="packagedata.label.thoiGianSuDung" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="duration" path="pojo.duration" class="form-control required number" />
                            <form:errors for="duration" path="pojo.duration" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="numberOfExtend" ><fmt:message key="packagedata.label.soLanGiaHan" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="numberOfExtend" path="pojo.numberOfExtend" min="0" class="form-control required number" />
                            <form:errors for="numberOfExtend" path="pojo.numberOfExtend" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="tk" ><fmt:message key="packagedata.label.tk" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="tk" path="pojo.tk" class="form-control required" />
                            <form:errors for="tk" path="pojo.tk" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <c:if test="${empty item.pojo.packageDataId || (not empty item.pojo.packageDataId && item.pojo.generatedCardCode eq false)}">
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="generatedCardCode" ><fmt:message key="packagedata.label.isGeneratedCardCode" />
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <form:select cssClass="form-control" id="generatedCardCode" path="pojo.generatedCardCode" cssStyle="width: 150px;">
                                    <option <c:if test="${item.pojo.generatedCardCode eq false}">selected="true"</c:if> value="false"><fmt:message key="packagedata.label.no" /></option>
                                    <option <c:if test="${item.pojo.generatedCardCode eq true}">selected="true"</c:if> value="true"><fmt:message key="packagedata.label.yes" /></option>
                                </form:select>
                            </div>
                        </div>
                    </c:if>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a href="${backUrl}" class="btn btn-success"><i class="fa fa-times" aria-hidden="true"></i> <fmt:message key="label.huy" /></a>&nbsp;
                            <a id="btnSave" class="btn btn-primary"><i class="fa fa-floppy-o" aria-hidden="true"></i>
                                <c:choose>
                                    <c:when test="${not empty item.pojo.packageDataId}"><fmt:message key="label.update" /></c:when>
                                    <c:otherwise><fmt:message key="label.save" /></c:otherwise>
                                </c:choose>
                            </a>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />
                    <form:hidden id="packageDataId" path="pojo.packageDataId" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        jQueryMaskNonThousandFormat();
        bindKey();
        bindEvent();
    });

    function bindEvent(){
        $("#btnSave").click(function(){
            if($('#formEdit').valid()){
                var $generatedCardCodeEl = $('#generatedCardCode');
                if($generatedCardCodeEl.length && $generatedCardCodeEl.val() === 'true'){
                    bootbox.confirm('<fmt:message key="packagedata.popup.title" />', '<fmt:message key="packagedata.popup.content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                        if(r){
                            $("#formEdit").submit();
                        }
                    });
                }else{
                    $("#formEdit").submit();
                }
            }
        });
    }

    function bindKey(){
        $('#value').keyup(function() {
            $('#value').val( numberWithCommas( $('#value').val().replace(/\,/g, '') )  );
        });
    }
</script>