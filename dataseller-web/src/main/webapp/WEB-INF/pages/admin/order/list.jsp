<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="admin.donhang.label.heading" /></title>
    <meta name="menu" content="<fmt:message key="admin.donhang.label.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="editUrl" value="${prefix}/order/add.html"/>
<c:url var="historyUrl" value="${prefix}/orderhistory/list.html"/>
<c:url var="formUrl" value="${prefix}/order/list.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="admin.donhang.label.list" /></h3>
    </div>

    <div class="title_right">
        <div class="action-bar">
            <a class="btn btn-primary" href="${editUrl}"><i class="fa fa-plus" aria-hidden="true"></i> <fmt:message key="label.button.them"/></a>
        </div>
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
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="listForm" action="${formUrl}" method="post" autocomplete="off" name="listForm">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="KHDN"><fmt:message key="admin.donhang.label.KHDN" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="KHDN" path="pojo.khdn.KHDNId" cssClass="form-control">
                                <option value=""><fmt:message key="label.all" /></option>
                                <c:forEach items="${KHDNList}" var="KHDN">
                                    <option <c:if test="${item.pojo.khdn.KHDNId eq KHDN.KHDNId}">selected="true"</c:if> value="${KHDN.KHDNId}">${KHDN.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="packageData"><fmt:message key="admin.donhang.label.tenGoiCuoc" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="packageData" path="pojo.packageData.packageDataId" cssClass="form-control">
                                <option value=""><fmt:message key="label.all" /></option>
                                <c:forEach items="${packageDataList}" var="packageData">
                                    <option <c:if test="${item.pojo.packageData.packageDataId eq packageData.packageDataId}">selected="true"</c:if> value="${packageData.packageDataId}">${packageData.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <security:authorize access="hasAnyAuthority('ADMIN', 'VMS_USER')">
                        <c:if test="${item.crudaction == 'search' && items.totalItems > 0}">
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="exportOptionType"><fmt:message key="admin.donhang.label.export_option" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:select id="exportOptionType" path="exportOptionType" cssClass="form-control">
                                        <option value=""><fmt:message key="label.all" /></option>
                                        <option <c:if test="${item.exportOptionType eq Constants.ADMIN_EXPORT_4_KHDN}">selected="true"</c:if> value="${Constants.ADMIN_EXPORT_4_KHDN}"><fmt:message key="admin.donhang.label.export_4_khdn" /></option>
                                        <option <c:if test="${item.exportOptionType eq Constants.ADMIN_EXPORT_4_MOBIFONE}">selected="true"</c:if> value="${Constants.ADMIN_EXPORT_4_MOBIFONE}"><fmt:message key="admin.donhang.label.export_4_he_thong_quan_ly_the" /></option>
                                    </form:select>
                                </div>
                            </div>
                        </c:if>
                    </security:authorize>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javascript: resetForm();" ><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset" /></a>
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><i class="fa fa-search" aria-hidden="true"></i> <fmt:message key="label.search" /></a>
                        </div>
                    </div>
                    <input id="crudaction" type="hidden" name="crudaction" value="<%=Constants.ACTION_SEARCH%>" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <c:choose>
                    <c:when test="${item.crudaction == 'search'}">
                        <div id="tableListContainer" style="width: 100%;">
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                           partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                           id="tableList" pagesize="${items.maxPageItems}" export="false"
                                           class="table table-striped table-bordered" style="margin: 1em 0 1.5em; width : 1850px;">
                                <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center" style="width: 50px;" >${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</display:column>
                                <display:column headerClass="table_header text-center" property="khdn.name" sortName="khdn.name" sortable="true" titleKey="admin.donhang.label.DN" style="width: 250px;"/>
                                <display:column headerClass="table_header text-center" property="packageData.name" sortName="packageData.name" class="text-center" sortable="true" titleKey="admin.donhang.label.tenGoiCuoc" style="width: 250px;"/>
                                <display:column headerClass="table_header text-center" sortable="true" class="text-center" sortName="quantity" titleKey="admin.donhang.label.quantity" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.quantity}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" sortable="true" class="text-center" sortName="unitPrice" titleKey="admin.donhang.label.UnitPrice" style="width: 150px;">
                                    <fmt:formatNumber type="number" value="${tableList.unitPrice}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" sortable="true" class="text-center" sortName="issuedDate" titleKey="admin.donhang.label.issueDate" style="width: 150px;">
                                    <fmt:formatDate value="${tableList.issuedDate}" pattern="${datePattern}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" sortable="true" class="text-center" sortName="shippingDate" titleKey="admin.donhang.label.shippingDate" style="width: 200px;">
                                    <fmt:formatDate value="${tableList.shippingDate}" pattern="${datePattern}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" sortable="true" property="createdBy.displayName" sortName="createdDate" class="text-center" titleKey="admin.donhang.label.createdBy" style="width: 200px;" />
                                <display:column headerClass="table_header text-center" sortable="true" sortName="orderStatus" class="text-center" titleKey="admin.donhang.label.status" style="width: 200px;">
                                    <c:choose>
                                       <c:when test="${tableList.orderStatus eq Constants.ORDER_STATUS_FINISH && tableList.cardCodeProcessStatus eq Constants.ORDER_CARD_CODE_PROCESSING_STATUS}">
                                           <fmt:message key="order.card_code_taking_in_progress" />
                                       </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${tableList.orderStatus eq Constants.ORDER_STATUS_PROCESSING}">
                                                    <fmt:message key="label.in_progress" />
                                                </c:when>
                                                <c:when test="${tableList.orderStatus eq Constants.ORDER_STATUS_FINISH}">
                                                    <c:choose>
                                                        <c:when test="${tableList.cardCodeProcessStatus eq  Constants.ORDER_CARD_CODE_FAILED_STATUS}">
                                                            <fmt:message key="label.failed_generate_card_code" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <fmt:message key="label.finish" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:when test="${tableList.orderStatus eq Constants.ORDER_STATUS_FINISH}">
                                                    <fmt:message key="label.finish" />
                                                </c:when>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column headerClass="table_header  text-center" class="text-center" titleKey="label.action" style="width: 250px;">
                                    <a href="${historyUrl}?pojo.order.orderId=${tableList.orderId}" class="tip-top action-group btn-primary" data-toggle="tooltip" title="<fmt:message key="label.view_history" />"><i class="fa fa-history" aria-hidden="true"></i></a>
                                    <c:if test="${tableList.orderStatus ne Constants.ORDER_STATUS_FINISH}">
                                         <a href="${editUrl}?pojo.orderId=${tableList.orderId}" class="tip-top action-group btn-primary" data-toggle="tooltip" title="<fmt:message key="label.edit" />"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                                         <a class="tip-top action-group btn-danger" data-toggle="tooltip" title="<fmt:message key="label.delete" />" onclick="javascript: deleteOrder(${tableList.orderId});"><i class="fa fa-trash-o" aria-hidden="true"></i></a>
                                    </c:if>
                                    <c:if test="${tableList.orderStatus eq Constants.ORDER_STATUS_FINISH && tableList.cardCodeProcessStatus ne  Constants.ORDER_CARD_CODE_FAILED_STATUS}">
                                         <a class="tip-top action-group btn-primary" data-toggle="tooltip" title="<fmt:message key="label.button.export" />" onclick="javascript: exportExcel(${tableList.orderId});"><i class="fa fa-file-excel-o" aria-hidden="true"></i></a>
                                    </c:if>
                                </display:column>
                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.order" /></display:setProperty>
                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.order" /></display:setProperty>
                            </display:table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="admin.donhang.label.list_not_search_yet" />
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        initScrollablePane();
    });

    function resetForm(){
        $("input[type='text']").val('');
        selectFirstItemSelect2('#userGroupMenu');
    }

    function initScrollablePane(){
        var $tableContainer = $('#tableListContainer');
        if($tableContainer.length){
            $tableContainer.mCustomScrollbar({axis:"x"});
        }
    }

    function submitForm(){
        $('#listForm').submit();
    }

    function deleteOrder(orderId){
        bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
            if(r){
                document.location.href = '${formUrl}?pojo.orderId=' + orderId + '&crudaction=<%=Constants.ACTION_DELETE%>';
            }
        });
    }

    function exportExcel(orderId){
        var url = '${formUrl}?pojo.orderId=' + orderId + '&crudaction=<%=Constants.ACTION_EXPORT%>';

        var $exportOptionType = $('#exportOptionType');
        if($exportOptionType.length){
            if($exportOptionType.val() == '${Constants.ADMIN_EXPORT_4_MOBIFONE}'){
                url += '&exportOptionType=${Constants.ADMIN_EXPORT_4_MOBIFONE}';
            }
        }

        document.location.href = url;
    }
</script>