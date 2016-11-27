<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="admin.orderhistory.label.heading" /></title>
    <meta name="menu" content="<fmt:message key="admin.orderhistory.label.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">

    <style>
        #tablelist2.mobile td.width_50_px{
            width: 50px;
        }
        #tablelist2.mobile td.width_400_px{
            width: 400px;
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
<c:url var="backUrl" value="${prefix}/order/list.html"/>
<c:url var="formUrl" value="${prefix}/orderhistory/list.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="admin.orderhistory.label.heading" /></h3>
    </div>

    <div class="title_right">
        <div class="action-bar">
            <a class="btn btn-success" href="${backUrl}" ><i class="fa fa-arrow-left" aria-hidden="true"></i> <fmt:message key="label.back" /></a>
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
                <div id="tableListContainer" style="width: 100%;">
                    <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                   partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                   id="tableList" pagesize="${items.maxPageItems}" export="false"
                                   class="table table-striped table-bordered" style="width: 1650px; margin: 1em 0 1.5em;">
                        <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center" style="width: 50px;" >${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</display:column>
                        <display:column headerClass="table_header text-center" sortable="false" sortName="khdn.name" property="khdn.name" titleKey="admin.donhang.label.DN" style="width: 250px;"/>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="packageData.name" property="packageData.name" class="text-center" titleKey="admin.donhang.label.tenGoiCuoc" style="width: 150px;"/>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="quantity" class="text-center" titleKey="admin.donhang.label.quantity" style="width: 150px;" >
                            <fmt:formatNumber type="number" value="${tableList.quantity}" />
                        </display:column>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="unitPrice" class="text-center" titleKey="admin.donhang.label.UnitPrice" style="width: 150px;">
                            <fmt:formatNumber type="number" value="${tableList.unitPrice}" />
                        </display:column>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="issuedDate" class="text-center" titleKey="admin.donhang.label.issueDate" style="width: 150px;">
                            <fmt:formatDate value="${tableList.issuedDate}" pattern="${datePattern}" />
                        </display:column>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="shippingDate" class="text-center" titleKey="admin.donhang.label.shippingDate" style="width: 200px;">
                            <fmt:formatDate value="${tableList.shippingDate}" pattern="${datePattern}" />
                        </display:column>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="createdDate" class="text-center" titleKey="admin.donhang.label.modifiedDate" style="width: 200px;">
                            <fmt:formatDate value="${tableList.createdDate}" pattern="${datePattern}" />
                        </display:column>                        s
                        <display:column headerClass="table_header text-center" sortable="true" sortName="createdBy.displayName" property="createdBy.displayName" class="text-center" titleKey="label.modifiedBy" style="width: 150px;" />
                        <display:column headerClass="table_header text-center" titleKey="label.operation" class="text-center" style="width: 200px;">
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
</div>

<c:if test="${cardCodeList != null && cardCodeList.size() > 0}">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_title">
                <h2><fmt:message key="order.card_code_list" /></h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_panel">
                <div class="x_content">
                    <div id="tableListContainer2" style="width: 100%;">
                        <table id="tableList2" class="table table-striped table-bordered" cellspacing="0" cellpadding="0" >
                            <thead>
                            <tr>
                                <th class="table_header text-center width_50_px"><fmt:message key="label.stt" /></th>
                                <th class="table_header text-center width_400_px"><fmt:message key="order.card_code" /></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${cardCodeList}" var="cardCode" varStatus="status">
                                <tr>
                                    <td class="text-center">${status.count}</td>
                                    <td class="text-center">${cardCode}</td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="2" class="text-right">${cardCodeList.size()} <fmt:message key="order.total_card_code" /></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        initScrollablePane();
    });

    function resetForm(){
        $("input[type='text']").val('');
    }

    function submitForm(){
        $('#listForm').submit();
    }

    function initScrollablePane(){
        $('#tableListContainer').mCustomScrollbar({axis:"x"});

        if($(window).width() >= mobile_screen_width){
            return;
        }

        var $tableContainer2 = $('#tableListContainer2');
        if($tableContainer2.length){
            $('#tableList2').addClass('mobile').width(450);
            $tableContainer2.mCustomScrollbar({axis:"x"});
        }
    }
</script>