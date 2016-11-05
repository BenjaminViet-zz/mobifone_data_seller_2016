<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="admin.orderhistory.label.heading" /></title>
    <meta name="menu" content="<fmt:message key="admin.orderhistory.label.heading" />"/>
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="backUrl" value="${prefix}/order/list.html"/>
<c:url var="formUrl" value="${prefix}/orderhistory/list.html"/>

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
                            <a class="btn btn-success" href="${backUrl}" ><fmt:message key="label.back" /></a>
                            <a class="btn btn-success" onclick="javacsript: resetForm();" ><fmt:message key="label.reset" /></a>
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="search" />
                    <input type="hidden" name="pojo.order.orderId" value="${item.pojo.order.orderId}" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                               id="tableList" pagesize="${items.maxPageItems}" export="false"
                               class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">
                    <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center" style="width: 3%" >${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</display:column>
                    <display:column headerClass="table_header text-center" property="khdn.name" titleKey="admin.donhang.label.DN" style="width: 20%"/>
                    <display:column headerClass="table_header text-center" property="packageData.name" class="text-center" titleKey="admin.donhang.label.tenGoiCuoc" style="width: 10%"/>
                    <display:column headerClass="table_header text-center" class="text-center" titleKey="admin.donhang.label.quantity" style="width: 8%" >
                        <fmt:formatNumber type="number" value="${tableList.quantity}" />
                    </display:column>
                    <display:column headerClass="table_header text-center" class="text-center" titleKey="admin.donhang.label.UnitPrice" style="width: 8%">
                        <fmt:formatNumber type="number" value="${tableList.unitPrice}" />
                    </display:column>
                    <display:column headerClass="table_header text-center" class="text-center" titleKey="admin.donhang.label.issueDate" style="width: 9%">
                        <fmt:formatDate value="${tableList.issuedDate}" pattern="${datePattern}" />
                    </display:column>
                    <display:column headerClass="table_header text-center" class="text-center" titleKey="admin.donhang.label.shippingDate" style="width: 9%">
                        <fmt:formatDate value="${tableList.shippingDate}" pattern="${datePattern}" />
                    </display:column>
                    <display:column headerClass="table_header text-center" class="text-center" titleKey="admin.donhang.label.modifiedDate" style="width: 9%">
                        <fmt:formatDate value="${tableList.createdDate}" pattern="${datePattern}" />
                    </display:column>
                    <display:column headerClass="table_header text-center" property="createdBy.displayName" class="text-center" titleKey="label.modifiedBy" style="width: 10%" />
                    <display:column headerClass="table_header text-center" titleKey="label.operation" class="text-center" style="width: 14%">
                        <c:choose>
                            <c:when test="${tableList.operator eq Constants.ORDER_HISTORY_OPERATOR_CREATED}">
                                <fmt:message key="orderhistory.operator.created" />
                            </c:when>
                            <c:when test="${tableList.operator eq Constants.ORDER_HISTORY_OPERATOR_UPDATED}">
                                <fmt:message key="orderhistory.operator.updated" />
                            </c:when>
                            <c:when test="${tableList.operator eq Constants.ORDER_HISTORY_OPERATOR_DELETED}">
                                <fmt:message key="orderhistory.operator.deleted" />
                            </c:when>
                        </c:choose>
                    </display:column>
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.orderhistory" /></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.orderhistory" /></display:setProperty>
                </display:table>
            </div>
        </div>
    </div>
</div>


<script language="javascript" type="text/javascript">
    function resetForm(){
        $("input[type='text']").val('');
    }

    function submitForm(){
        $('#listForm').submit();
    }
</script>