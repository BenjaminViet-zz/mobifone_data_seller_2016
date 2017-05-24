<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <%--<title><fmt:message key="admin.edit_user.heading_page" /></title>--%>
    <title>
        <c:choose>
            <c:when test="${not empty item.pojo.userId}">
                <fmt:message key="admin.edit_user.label.edit_user" />
            </c:when>
            <c:otherwise><fmt:message key="admin.edit_user.label.add_user" /></c:otherwise>
        </c:choose>
    </title>
    <meta name="menu" content="<fmt:message key="admin.edit_user.heading_page" />"/>
</head>

<c:set var="prefix" value="${vms:getPrefixUrl()}" />
<c:url var="backUrl" value="${prefix}/user/list.html"/>
<c:url var="formUrl" value="${prefix}/user/add.html"/>

<c:if test="${pojo.userId != null}">
    <c:url var="formUrl" value="${prefix}/user/edit.html"/>
</c:if>

<div class="page-title">
    <div class="title_left">
        <h3>
            <c:choose>
                <c:when test="${not empty item.pojo.userId}">
                    <fmt:message key="admin.edit_user.label.edit_user" />
                </c:when>
                <c:otherwise><fmt:message key="admin.edit_user.label.add_user" /></c:otherwise>
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
                    <c:choose>
                        <c:when test="${not empty item.pojo.userId}">
                            <form:hidden path="pojo.userType.userTypeId" />
                            <form:hidden path="pojo.userType.code" />
                            <c:choose>
                                <c:when test="${item.pojo.userType.code eq Constants.USER_TYPE_ADMIN}">
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="displayName">
                                            <fmt:message key="label.full_name" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:input id="displayName" path="pojo.displayName" cssClass="required nohtml form-control"></form:input>
                                            <form:errors for="displayName" path="pojo.displayName" cssClass="error-inline-validate"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userName">
                                            <fmt:message key="label.user_name" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:input id="userName" path="pojo.userName" cssClass="required nohtml form-control"></form:input>
                                            <form:errors for="userName" path="pojo.userName" cssClass="error-inline-validate"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="password">
                                            <fmt:message key="user.label.password" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <input id="password" type="password" name="pojo.password" class="required nohtml form-control has-attach-add-on" value="${item.pojo.password}" />
                                            <span class="add-on-attach" id="btnShowHidePassword">Xem</span>
                                            <form:errors for="password" path="pojo.password" cssClass="error-inline-validate"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userType">
                                            <fmt:message key="user.label.userType" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <span id="userType" class="form-control" disabled="true">${item.pojo.userType.description}</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userGroup">
                                            <fmt:message key="user.label.usergroup" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <span id="userGroup" class="form-control" disabled="true">${item.pojo.userGroup.description}</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="status">
                                            <fmt:message key="label.status" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:select cssClass="form-control" id="status" path="pojo.status" cssStyle="width: 150px;">
                                                <option <c:if test="${1 eq item.pojo.status}">selected="true"</c:if> value="1"><fmt:message key="label.active" /></option>
                                                <option <c:if test="${0 eq item.pojo.status}">selected="true"</c:if> value="0"><fmt:message key="label.inactive" /></option>
                                            </form:select>
                                        </div>
                                    </div>
                                    <form:hidden path="pojo.userGroup.userGroupId" />
                                </c:when>
                                <c:when test="${item.pojo.userType.code eq Constants.USER_TYPE_VMS_USER}">
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="displayName">
                                            <fmt:message key="label.full_name" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <span id="displayName" class="form-control" disabled="true">${item.pojo.displayName}</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userName">
                                            <fmt:message key="label.user_name" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <span id="userName" class="form-control" disabled="true">${item.pojo.userName}</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userType">
                                            <fmt:message key="user.label.userType" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <span id="userType" class="form-control" disabled="true">${item.pojo.userType.description}</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userGroup">
                                            <fmt:message key="user.label.usergroup" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:select id="userGroup" path="pojo.userGroup.userGroupId" cssClass="required nohtml form-control" cssStyle="width: 250px;">
                                                <form:option value=""><fmt:message key="label.select" /></form:option>
                                                <c:forEach items="${userGroups}" var="userGroup">
                                                    <option <c:if test="${userGroup.userGroupId eq item.pojo.userGroup.userGroupId}">selected="true"</c:if> value="${userGroup.userGroupId}">${userGroup.description}</option>
                                                </c:forEach>
                                            </form:select>
                                            <form:errors path="pojo.userGroup.userGroupId" for="userGroup" cssClass="error-inline-validate" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="status">
                                            <fmt:message key="label.status" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:select cssClass="form-control" id="status" path="pojo.status" cssStyle="width: 150px;">
                                                <option <c:if test="${1 eq item.pojo.status}">selected="true"</c:if> value="1"><fmt:message key="label.active" /></option>
                                                <option <c:if test="${0 eq item.pojo.status}">selected="true"</c:if> value="0"><fmt:message key="label.inactive" /></option>
                                            </form:select>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${item.pojo.userType.code eq Constants.USER_TYPE_KHDN}">
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="displayName">
                                            <fmt:message key="label.full_name" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:input id="displayName" path="pojo.displayName" cssClass="required nohtml form-control"></form:input>
                                            <form:errors for="displayName" path="pojo.displayName" cssClass="error-inline-validate"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userName">
                                            <fmt:message key="label.user_name" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:input id="userName" path="pojo.userName" cssClass="required nohtml form-control"></form:input>
                                            <form:errors for="userName" path="pojo.userName" cssClass="error-inline-validate"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="password">
                                            <fmt:message key="user.label.password" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <input id="password" type="password" name="pojo.password" class="required nohtml form-control has-attach-add-on" value="${item.pojo.password}" />
                                            <span class="add-on-attach" id="btnShowHidePassword">Xem</span>
                                            <form:errors for="password" path="pojo.password" cssClass="error-inline-validate"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userType">
                                            <fmt:message key="user.label.userType" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <span id="userType" class="form-control" disabled="true">${item.pojo.userType.description}</span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userGroup">
                                            <fmt:message key="user.label.usergroup" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:select id="userGroup" path="pojo.userGroup.userGroupId" cssClass="nohtml form-control" cssStyle="width: 250px;">
                                                <form:option value=""><fmt:message key="label.select" /></form:option>
                                                <c:forEach items="${userGroups}" var="userGroup">
                                                    <option <c:if test="${userGroup.userGroupId eq item.pojo.userGroup.userGroupId}">selected="true"</c:if> value="${userGroup.userGroupId}">${userGroup.description}</option>
                                                </c:forEach>
                                            </form:select>
                                            <form:errors path="pojo.userGroup.userGroupId" for="userGroup" cssClass="error-inline-validate" />
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="isdn">
                                            <fmt:message key="label.user_stb_vas" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:input id="isdn" path="pojo.isdn" cssClass="required nohtml form-control"></form:input>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="status">
                                            <fmt:message key="label.status" />
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:select cssClass="form-control" id="status" path="pojo.status" cssStyle="width: 150px;">
                                                <option <c:if test="${1 eq item.pojo.status}">selected="true"</c:if> value="1"><fmt:message key="label.active" /></option>
                                                <option <c:if test="${0 eq item.pojo.status}">selected="true"</c:if> value="0"><fmt:message key="label.inactive" /></option>
                                            </form:select>
                                        </div>
                                    </div>
                                </c:when>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="displayName">
                                    <fmt:message key="label.full_name" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input id="displayName" path="pojo.displayName" cssClass="required nohtml form-control"></form:input>
                                    <form:errors for="displayName" path="pojo.displayName" cssClass="error-inline-validate"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userName">
                                    <fmt:message key="label.user_name" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input id="userName" path="pojo.userName" cssClass="required nohtml form-control"></form:input>
                                    <form:errors for="userName" path="pojo.userName" cssClass="error-inline-validate"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="password">
                                    <fmt:message key="user.label.password" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input id="password" type="password" name="pojo.password" class="required nohtml form-control has-attach-add-on" value="${item.pojo.password}" />
                                    <span class="add-on-attach" id="btnShowHidePassword">Xem</span>
                                    <form:errors for="password" path="pojo.password" cssClass="error-inline-validate"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userTypeMenu">
                                    <fmt:message key="user.label.userType" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:select id="userTypeMenu" path="pojo.userType.userTypeId" cssClass="required form-control" cssStyle="width: 250px;" onchange="javascript: checkShowHideISDNField();">
                                        <form:option value=""><fmt:message key="label.select" /></form:option>
                                        <c:forEach items="${userTypes}" var="userType">
                                            <option data-code="${userType.code}" <c:if test="${userType.userTypeId eq item.pojo.userType.userTypeId}">selected="true"</c:if> value="${userType.userTypeId}">${userType.description}</option>
                                        </c:forEach>
                                    </form:select>
                                    <form:errors path="pojo.userType.userTypeId" for="userTypeMenu" cssClass="error-inline-validate" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userGroup">
                                    <fmt:message key="user.label.usergroup" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:select id="userGroup" path="pojo.userGroup.userGroupId" cssClass="required nohtml form-control" cssStyle="width: 250px;">
                                        <form:option value=""><fmt:message key="label.select" /></form:option>
                                        <c:forEach items="${userGroups}" var="userGroup">
                                            <option <c:if test="${userGroup.userGroupId eq item.pojo.userGroup.userGroupId}">selected="true"</c:if> value="${userGroup.userGroupId}">${userGroup.description}</option>
                                        </c:forEach>
                                    </form:select>
                                    <form:errors path="pojo.userGroup.userGroupId" for="userGroup" cssClass="error-inline-validate" />
                                </div>
                            </div>

                            <div id="isdnFormGroup" class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="isdn">
                                    <fmt:message key="label.user_stb_vas" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input id="isdn" path="pojo.isdn" cssClass="nohtml form-control"></form:input>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="status">
                                    <fmt:message key="label.status" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:select cssClass="form-control" id="status" path="pojo.status" cssStyle="width: 150px;">
                                        <option <c:if test="${1 eq item.pojo.status}">selected="true"</c:if> value="1"><fmt:message key="label.active" /></option>
                                        <option <c:if test="${0 eq item.pojo.status}">selected="true"</c:if> value="0"><fmt:message key="label.inactive" /></option>
                                    </form:select>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a href="${backUrl}" class="btn btn-success"><i class="fa fa-times" aria-hidden="true"></i>
                                 <fmt:message key="label.huy" /></a>&nbsp;
                            <a id="btnSave" class="btn btn-primary"><i class="fa fa-floppy-o" aria-hidden="true"></i>
                                <c:choose>
                                    <c:when test="${not empty item.pojo.userId}"><fmt:message key="label.update" /></c:when>
                                    <c:otherwise><fmt:message key="label.save" /></c:otherwise>
                                </c:choose>
                            </a>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />
                    <form:hidden path="pojo.userId" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var $isdn = $('#isdn'),
        $isdnFormGroup = $('#isdnFormGroup'),
        $userTypeMenu = $('#userTypeMenu'),
        $form = $("#formEdit"),
        $btnSave = $('#btnSave');

    $(document).ready(function(){
        storeUserTypeCode2Data();
        bindEvents();
    });

    function bindEvents(){
        $btnSave.click(function(){
            if($form.valid()){
                if ( $('#status option:selected').text() == '<fmt:message key="label.inactive" />' ) {
                    bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.user_status_inactive" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                        if( r ){
                            submitForm();
                        }
                    });

                } else {
                    submitForm();
                }
            }
        });
    }

    function submitForm(){

        if($userTypeMenu.length){
            $('<input type="hidden" name="pojo.userType.code" value="' + $userTypeMenu.find('option:selected').data('code') + '" />').appendTo($form);
        }

        $form.submit();
    }

    function storeUserTypeCode2Data(){
        if($userTypeMenu.length){
            $userTypeMenu.find('option:not(:first-child)').each(function(idx, el){
                var $option = $(el);
                if(typeof $option.attr('data-code') !== 'undefined'){
                    $option.data('code', $option.attr('data-code')).removeAttr('data-code');
                }
            });
        }
    }

    function checkShowHideISDNField(){
        if($userTypeMenu.length){
            if($userTypeMenu.find('option:selected').data('code') === '<%=Constants.USER_TYPE_KHDN%>'){
                $isdn.addClass('required');
                $isdnFormGroup.show();
            }else{
                $isdn.removeClass('required');
                $isdnFormGroup.hide();
            }
        }
    }
</script>