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
                        <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center"><div style="width: 50px;">${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</div></display:column>
                        <display:column headerClass="table_header text-center" sortable="false" sortName="khdn.name" titleKey="admin.donhang.label.DN">
                            <div style="width: 250px;">${tableList.khdn.name}</div>
                        </display:column>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="packageData.name" class="text-center" titleKey="admin.donhang.label.tenGoiCuoc">
                            <div style="width: 150px;">${tableList.packageData.name}</div>
                        </display:column>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="quantity" class="text-center" titleKey="admin.donhang.label.quantity">
                            <div style="width: 150px;">
                                <fmt:formatNumber type="number" value="${tableList.quantity}" maxFractionDigits="0" />
                            </div>
                        </display:column>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="unitPrice" class="text-center" titleKey="admin.donhang.label.UnitPrice">
                            <div style="width: 150px;">
                                <fmt:formatNumber type="number" value="${tableList.unitPrice}" maxFractionDigits="0" />
                            </div>
                        </display:column>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="issuedDate" class="text-center" titleKey="admin.donhang.label.issueDate">
                            <div style="width: 150px;">
                                <fmt:formatDate value="${tableList.issuedDate}" pattern="${datePattern}" />
                            </div>
                        </display:column>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="shippingDate" class="text-center" titleKey="admin.donhang.label.shippingDate">
                            <div style="width: 200px;">
                                <fmt:formatDate value="${tableList.shippingDate}" pattern="${datePattern}" />
                            </div>
                        </display:column>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="createdDate" class="text-center" titleKey="admin.donhang.label.modifiedDate">
                            <div style="width: 200px;">
                                <fmt:formatDate value="${tableList.createdDate}" pattern="${datePattern}" />
                            </div>
                        </display:column>
                        <display:column headerClass="table_header text-center" sortable="true" sortName="createdBy.displayName" class="text-center" titleKey="label.modifiedBy">
                            <div style="width: 150px;">${tableList.createdBy.displayName}</div>
                        </display:column>
                        <display:column headerClass="table_header text-center" titleKey="label.operation" class="text-center">
                            <div style="width: 200px;">
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
                            </div>
                        </display:column>
                        <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.orderhistory" /></display:setProperty>
                        <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.orderhistory" /></display:setProperty>
                    </display:table>
                </div>
            </div>
        </div>
    </div>
</div>

<c:if test="${cardCodeItem.cardCodeList != null && cardCodeItem.cardCodeList.size() > 0}">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_title">
                <h2><fmt:message key="order.card_code_list" /></h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_panel">
                <div class="x_content">
                    <div id="tableListContainer2" style="width: 100%; height: 700px;">
                        <display:table name="cardCodeItem.cardCodeList" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                       partialList="true" sort="external" size="${cardCodeItem.totalCardCodeItems}" defaultsort="0"
                                       id="tableList2" pagesize="${cardCodeItem.cardCodeMaxPageItems}" export="false"
                                       class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">
                        <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center width_50_px" style="width:5%;" >${tableList2_rowNum + (page * cardCodeItem.cardCodeMaxPageItems)}</display:column>
                        <display:column headerClass="table_header text-center" sortable="false" class="text-center width_400_px" property="dataCode" titleKey="order.card_code" style="width: 95%;"/>
                        <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.card_code" /></display:setProperty>
                        <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.card_code" /></display:setProperty>
                    </display:table>
                    </div>
                    <div id="pagelinksContainer2">

                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    var $tableContainer2 = $('#tableListContainer2');
    var $tableList2 = $('#tableList2');

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

        if($tableContainer2.length){
            if($(window).width() >= mobile_screen_width){
                $tableList2.height(39 + ${cardCodeItem.cardCodeList.size() * 38});
                $tableContainer2.mCustomScrollbar({axis:"y"});
            }else{
                $tableList2.addClass('mobile').width(450).height(39 + ${cardCodeItem.cardCodeList.size() * 38});
                $tableContainer2.mCustomScrollbar({axis:"yx"});
            }

            movePageLinks();
        }
    }

    function movePageLinks(){
        $tableContainer2.find('.pagelinks').appendTo($('#pagelinksContainer2'));
        $tableContainer2.find('.pagebanner').appendTo($('#pagelinksContainer2'));
    }
</script>