<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="admin.baocaothongtinthitruong.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="admin.baocaothongtinthitruong.heading_page" />"/>
    <link href="<c:url value="/themes/promotion/css/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>

<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/baocaothongtinthitruongtheochinhanh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.baocaothongtinthitruong.heading_page" /></h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">

        <div class="panel-body panel-body-nopadding">
            <c:if test ="${not empty messageResponse}">
                <div class="alert alert-${alertType}">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                        ${messageResponse}
                </div>
            </c:if>
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="searchForm" action="${formUrl}" method="post" autocomplete="off">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="branchMenu" path="branchId" cssStyle="width: 250px;" >
                                            <security:authorize ifAnyGranted="ADMIN,BAOCAO">
                                                <option value=""><fmt:message key="label.all" /></option>
                                            </security:authorize>
                                            <c:forEach items="${branchList}" var="branch">
                                                <option <c:if test="${item.branchId eq branch.branch_Id}">selected="true"</c:if> value="${branch.branch_Id}">${branch.branch_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.tu_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="fromDate" id="fromDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.fromDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="fromDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.den_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="toDate" id="toDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.toDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="toDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="panel-body">
                                <div class="form-group">
                                    <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                    <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                    <c:if test="${not empty items.listResult}">
                                        <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty items.listResult}">
                        <div id="reportTableContainer" style="width: 100%;">
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                           partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                           id="tableList" pagesize="${items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 1160px;">
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 50px;" >
                                    ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" property="branch_Name" titleKey="" class="nowrap" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" titleKey="SL bán sim" class="text-right nowrap" style="width: 100px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.soLuongBanSimViettel}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="Giá sim" class="text-right nowrap" style="width: 100px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.giaSimViettel}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="Doanh thu thẻ" class="text-right nowrap" style="width: 120px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.soLuongBanTheViettel}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="SL bán sim" class="text-right nowrap" style="width: 100px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.soLuongBanSimMobi}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="Giá sim" class="text-right nowrap" style="width: 100px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.giaSimMobi}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="Doanh thu thẻ" class="text-right nowrap" style="width: 120px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.soLuongBanTheMobi}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="SL bán sim" class="text-right nowrap" style="width: 100px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.soLuongBanSimVina}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="Giá sim" class="text-right nowrap" style="width: 100px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.giaSimVina}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="Doanh thu thẻ" class="text-right nowrap" style="width: 120px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.soLuongBanTheVina}" />
                                </display:column>
                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                            </display:table>
                        </div>
                        <div id="linksContainer">

                        </div>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#tdcg2014BCChiTietHangMucPSCTab');

        var fromDateVar = $("#fromDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    fromDateVar.hide();
                }).data('datepicker');

        $('#fromDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        var toDateVar = $("#toDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    toDateVar.hide();
                }).data('datepicker');

        $('#toDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        rebuildTableHeaders();
        $('#reportTableContainer').mCustomScrollbar({axis:"x"});
        checkMovePageLinks();
    });

    function checkMovePageLinks(){
        if(${not empty items.listResult}){
            $('#reportTableContainer .pagebanner').appendTo('#linksContainer');
            $('#reportTableContainer .pagelinks').appendTo('#linksContainer');
        }
    }

    function rebuildTableHeaders(){
        $('#tableList thead tr').remove();
        $('#tableList thead').html("<tr>" +
                "<th rowspan='2' class='table_header_center '>STT</th>" +
                "<th rowspan='2' class='table_header_center '>Chi nhánh</th>" +
                "<th colspan='3' class='table_header_center '>Viettel</th>" +
                "<th colspan='3' class='table_header_center '>Mobifone</th>" +
                "<th colspan='3' class='table_header_center '>VinaPhone</th>" +
                "</tr>" +
                "tr>" +
                "<th class='table_header_center '>SL bán sim</th>" +
                "<th class='table_header_center '>Giá sim</th>" +
                "<th class='table_header_center '>Doanh thu thẻ</th>" +
                "<th class='table_header_center '>SL bán sim</th>" +
                "<th class='table_header_center '>Giá sim</th>" +
                "<th class='table_header_center '>Doanh thu thẻ</th>" +
                "<th class='table_header_center '>SL bán sim</th>" +
                "<th class='table_header_center '>Giá sim</th>" +
                "<th class='table_header_center '>Doanh thu thẻ</th>" +
                "</tr>");
    }

    function submitForm(){
        $('#crudaction').val('search');
        $('#searchForm').submit();
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#searchForm').submit();
    }

    function reset(){
        selectFirstItemSelect2('#branchMenu');
        $('#fromDate').val('');
        $('#toDate').val('');
    }
</script>
</body>