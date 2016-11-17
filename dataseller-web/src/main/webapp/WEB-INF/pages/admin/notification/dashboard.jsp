<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="dashboard.notification.heading" /></title>
    <meta name="menu" content="<fmt:message key="dashboard.notification.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">

    <style>
        #tablelist.mobile td.width_50_px{
            width: 50px;
        }
        #tablelist.mobile td.width_150_px{
            width: 150px;
        }
        #tablelist.mobile td.width_500_px{
            width: 500px;
        }
        #tablelist.mobile td.width_110_px{
            width: 110px;
        }
    </style>
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize access="hasAnyAuthority('KHDN')">
    <c:set var="prefix" value="/khdn" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/notification.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="dashboard.notification.page_title" /></h3>
    </div>
</div>
<div class="clearfix"></div>
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

<form:form commandName="item" cssClass="form-horizontal form-label-left" id="listForm" action="${formUrl}" method="post" autocomplete="off" name="listForm">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_content">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="status"><fmt:message key="dashboard.notification.status" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select cssClass="form-control" id="status" path="pojo.read" >
                                <option <c:if test="${item.pojo.read eq Constants.NOTIFICATION_NOT_YET_READ}">selected="true"</c:if> value="${Constants.NOTIFICATION_NOT_YET_READ}"><fmt:message key="dashboard.notification.message_not_yet_read" /></option>
                                <option <c:if test="${item.pojo.read eq Constants.NOTIFICATION_READ_ALREADY}">selected="true"</c:if> value="${Constants.NOTIFICATION_READ_ALREADY}"><fmt:message key="dashboard.notification.message_read_already" /></option>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javascript: resetForm();" ><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset" /></a>
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><i class="fa fa-search" aria-hidden="true"></i> <fmt:message key="label.search" /></a>
                            <c:if test="${not empty item.listResult && item.listResult.size() > 0}">
                                <a id="btnUpdate" class="btn btn-primary" onclick="javascript: updateNotification();"><i class="fa fa-floppy-o" aria-hidden="true"></i> <fmt:message key="label.update_notification" /></a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_content">
                    <c:choose>
                        <c:when test="${not empty item.listResult && item.listResult.size() > 0}">
                            <div id="tableListContainer" style="width: 100%;">
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formlUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false"
                                               class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">
                                    <display:column headerClass="table_header text-center" title="<input style=\"width: 15px;\" type=\"checkbox\" class=\"\" name=\"allCheck\" id=\"allCheck\" onclick=\"checkAll('listForm', 'checkList', this)\">" sortable="false" class="text-center width_50_px" style="width: 5%;" >
                                        <input type="checkbox" id="${tableList.notificationId}" class="" style="width: 15px;" name="checkList" value="${tableList.notificationId}" onclick="checkAllIfOne('listForm', 'checkList', this, 'allCheck')" />
                                    </display:column>
                                    <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center width_50_px" style="width: 5%;" >
                                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                    </display:column>
                                    <display:column headerClass="table_header text-center" titleKey="dashboard.notification.status" class="text-center width_150_px" style="width: 15%">
                                        <c:choose>
                                            <c:when test="${tableList.read eq Constants.NOTIFICATION_NOT_YET_READ}">
                                                <fmt:message key="dashboard.notification.message_not_yet_read" />
                                            </c:when>
                                            <c:when test="${tableList.read eq Constants.NOTIFICATION_READ_ALREADY}">
                                                <fmt:message key="dashboard.notification.message_read_already" />
                                            </c:when>
                                        </c:choose>
                                    </display:column>
                                    <display:column headerClass="table_header" property="message" titleKey="dashboard.notification.message_content" class="width_500_px" style="width: 70%"/>
                                    <display:column headerClass="table_header text-center nowrap" class="text-center width_110_px" titleKey="dashboard.notification.created_date" style="width: 10%;" >
                                        <fmt:formatDate value="${tableList.createdDate}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.notification" /></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.notification" /></display:setProperty>
                                </display:table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="dashboard.notification.no_message_found" />
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <input id="crudaction" type="hidden" name="crudaction" value="<%=Constants.ACTION_SEARCH%>"/>
</form:form>

<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    var $tableListCheckbox = $('#tableList input[type="checkbox"]');
    var $buttonUpdate = $('#btnUpdate');

    $(document).ready(function(){
        initScrollablePane();

        if($buttonUpdate.length){
            enableOrDisableButtonUpdate();

            $( $tableListCheckbox ).change(function(){
                enableOrDisableButtonUpdate();
            });
        }
    });

    function enableOrDisableButtonUpdate(){
        if ( $tableListCheckbox.is(":checked") ) {
            $buttonUpdate.removeClass("disabled")
                    .css('pointer-events', 'auto');
        } else {
            $buttonUpdate.addClass("disabled")
                    .css('pointer-events', 'visible');
        }
    }

    function initScrollablePane(){
        if($(window).width() >= mobile_screen_width){
            return;
        }

        var $tableContainer = $('#tableListContainer');
        if($tableContainer.length){
            $('#tableList').addClass('mobile').width(860);
            $tableContainer.mCustomScrollbar({axis:"x"});
        }
    }

    function submitForm(){
        $('#crudaction').val('<%=Constants.ACTION_SEARCH%>');
        $('#listForm').submit();
    }

    function resetForm(){
        $("input[type='text']").val('');
        selectFirstItemSelect2('#status');
    }

    <c:if test="${not empty item.listResult && item.listResult.size() > 0}">
    function updateNotification(){
        var $notificationEls = $("input[name='checkList']:checked");

        if($notificationEls.length > 0){
            $('#crudaction').val('<%=Constants.ACTION_UPDATE%>');
            $('#listForm').submit();
        }
    }
    </c:if>
</script>