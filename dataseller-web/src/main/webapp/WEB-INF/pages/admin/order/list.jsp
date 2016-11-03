<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="admin.donhang.label.heading" /></title>
    <meta name="menu" content="<fmt:message key="admin.donhang.label.heading" />"/>
</head>

<c:set var="prefix" value="/user" />
<security:authorize ifAnyGranted="ADMIN">
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
            <a class="btn btn-primary" href="${editUrl}"> <fmt:message key="label.button.them"/></a>
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
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javacsript: resetForm();" ><fmt:message key="label.reset" /></a>
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="search" />
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
                        <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                       partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                       id="tableList" pagesize="${items.maxPageItems}" export="false"
                                       class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">
                            <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center" style="width: 3%" >${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</display:column>
                            <display:column headerClass="table_header text-center" property="khdn.name" sortName="khdn.name" titleKey="admin.donhang.label.DN" style="width: 20%"/>
                            <display:column headerClass="table_header text-center" property="packageData.name" class="text-center" titleKey="admin.donhang.label.tenGoiCuoc" style="width: 8%"/>
                            <display:column headerClass="table_header text-center" sortable="true" class="text-center" sortName="quantity" titleKey="admin.donhang.label.quantity" style="width: 7%" >
                                <fmt:formatNumber type="number" value="${tableList.quantity}" />
                            </display:column>
                            <display:column headerClass="table_header text-center" sortable="true" class="text-center" sortName="unitPrice" titleKey="admin.donhang.label.UnitPrice" style="width: 7%">
                                <fmt:formatNumber type="number" value="${tableList.unitPrice}" />
                            </display:column>
                            <display:column headerClass="table_header text-center" sortable="true" class="text-center" sortName="issuedDate" titleKey="admin.donhang.label.issueDate" style="width: 7%">
                                <fmt:formatDate value="${tableList.issuedDate}" pattern="${datePattern}" />
                            </display:column>
                            <display:column headerClass="table_header text-center" sortable="true" class="text-center" sortName="shippingDate" titleKey="admin.donhang.label.shippingDate" style="width: 7%">
                                <fmt:formatDate value="${tableList.shippingDate}" pattern="${datePattern}" />
                            </display:column>
                            <display:column headerClass="table_header text-center" sortable="true" property="createdBy.displayName" sortName="createdDate" class="text-center" titleKey="admin.donhang.label.createdBy" style="width: 12%" />
                            <display:column headerClass="table_header text-center" titleKey="admin.donhang.label.status" style="width: 9%">
                                <c:choose>
                                    <c:when test="${tableList.orderStatus eq Constants.ORDER_STATUS_PROCESSING}">
                                        <fmt:message key="label.in_progress" />
                                    </c:when>
                                    <c:when test="${tableList.orderStatus eq Constants.ORDER_STATUS_FINISH}">
                                        <fmt:message key="label.finish" />
                                    </c:when>
                                </c:choose>
                            </display:column>
                            <display:column headerClass="table_header  text-center" class="text-center" titleKey="label.action" style="width:20%;">
                                <a href="${historyUrl}?pojo.order.orderId=${tableList.orderId}" class="tip-top" title="<fmt:message key="label.view_history" />"><fmt:message key="label.view_history" /></a>
                                <c:if test="${tableList.orderStatus ne Constants.ORDER_STATUS_FINISH}">
                                    | <a href="${editUrl}?pojo.orderId=${tableList.orderId}" class="tip-top" title="<fmt:message key="label.edit" />"><fmt:message key="label.edit" /></a>
                                    | <a class="tip-top" onclick="javascript: deleteOrder(${tableList.orderId});"><fmt:message key="label.delete" /></a>
                                </c:if>
                            </display:column>
                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.order" /></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.order" /></display:setProperty>
                        </display:table>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="admin.donhang.label.list_not_search_yet" />
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>


<script language="javascript" type="text/javascript">
    function resetForm(){
        $("input[type='text']").val('');
        selectFirstItemSelect2('#userGroupMenu');
    }

    function submitForm(){
        $('#listForm').submit();
    }

    function deleteOrder(userId){
        bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
            if(r){
                document.location.href = '${formUrl}?pojo.orderId=' + userId + '&crudaction=delete';
            }
        });
    }
</script>